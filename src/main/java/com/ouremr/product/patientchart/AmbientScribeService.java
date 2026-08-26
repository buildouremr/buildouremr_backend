package com.ouremr.product.patientchart;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AmbientScribeService {

    @Value("${groq.api.key}")
    private String groqApiKey;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public String processAudio(MultipartFile audioFile) throws Exception {
        // Step 1: Transcribe the audio using Groq Whisper API
        String transcript = transcribeAudio(audioFile);

        // Step 2: Pass transcript to Groq Llama 3 to structure the medical data
        return extractMedicalData(transcript);
    }

    private String transcribeAudio(MultipartFile audioFile) throws Exception {
        String url = "https://api.groq.com/openai/v1/audio/transcriptions";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.setBearerAuth(groqApiKey);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        // Convert MultipartFile to a Resource that RestTemplate can handle correctly
        ByteArrayResource resource = new ByteArrayResource(audioFile.getBytes()) {
            @Override
            public String getFilename() {
                return audioFile.getOriginalFilename() != null ? audioFile.getOriginalFilename() : "audio.webm";
            }
        };
        
        body.add("file", resource);
        body.add("model", "whisper-large-v3");
        body.add("response_format", "json");

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(url, requestEntity, String.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            JsonNode rootNode = objectMapper.readTree(response.getBody());
            return rootNode.path("text").asText();
        } else {
            throw new RuntimeException("Failed to transcribe audio: " + response.getStatusCode());
        }
    }

    private String extractMedicalData(String transcript) throws Exception {
        String url = "https://api.groq.com/openai/v1/chat/completions";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(groqApiKey);

        Map<String, Object> body = new HashMap<>();
        body.put("model", "llama-3.3-70b-versatile");
        body.put("temperature", 0.1);
        
        // Ensure the model outputs JSON
        Map<String, String> responseFormat = new HashMap<>();
        responseFormat.put("type", "json_object");
        body.put("response_format", responseFormat);

        String systemPrompt = "You are an expert medical scribe. Analyze the following transcript between a doctor and a patient. " +
                "Extract the information into the following strict JSON format without any markdown wrappers or additional text:\n" +
                "{\n" +
                "  \"reasonForVisit\": \"...\",\n" +
                "  \"symptoms\": \"...\",\n" +
                "  \"examination\": \"...\",\n" +
                "  \"treatmentPlan\": \"...\",\n" +
                "  \"diagnosis\": \"...\",\n" +
                "  \"prescriptions\": [\n" +
                "    {\n" +
                "      \"drugName\": \"...\",\n" +
                "      \"frequency\": \"...\",\n" +
                "      \"duration\": \"...\",\n" +
                "      \"instruction\": \"...\"\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        List<Map<String, String>> messages = new ArrayList<>();
        Map<String, String> systemMessage = new HashMap<>();
        systemMessage.put("role", "system");
        systemMessage.put("content", systemPrompt);
        messages.add(systemMessage);

        Map<String, String> userMessage = new HashMap<>();
        userMessage.put("role", "user");
        userMessage.put("content", transcript);
        messages.add(userMessage);

        body.put("messages", messages);

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(url, requestEntity, String.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            JsonNode rootNode = objectMapper.readTree(response.getBody());
            String jsonContent = rootNode.path("choices").get(0).path("message").path("content").asText();
            return jsonContent; // This is the structured JSON string
        } else {
            throw new RuntimeException("Failed to extract medical data: " + response.getStatusCode());
        }
    }
}
