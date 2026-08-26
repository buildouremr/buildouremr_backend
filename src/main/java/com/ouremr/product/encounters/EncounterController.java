package com.ouremr.product.encounters;

import com.ouremr.product.dto.EncounterDTO;
import com.ouremr.product.dto.EncounterResponseDTO;
import com.ouremr.product.emrbean.EMRResponseBean;
import com.ouremr.product.security.JWTUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/encounters")
public class EncounterController {

    private static final Logger log = LoggerFactory.getLogger(EncounterController.class);

    @Autowired
    private EncounterService encounterService;

    @Autowired
    private JWTUtil jwtUtil;

    private Long getUserIdFromRequest(HttpServletRequest request) {
        String token = jwtUtil.extractTokenFromRequest(request);
        if (token != null && jwtUtil.validateToken(token)) {
            return jwtUtil.extractUserId(token);
        }
        throw new RuntimeException("Unauthorized: Valid token is required");
    }

    @PostMapping("/start")
    public ResponseEntity<EMRResponseBean> startEncounter(
            @RequestBody EncounterDTO request, 
            HttpServletRequest httpRequest) {
        
        EMRResponseBean response = new EMRResponseBean();
        try {
            Long userId = getUserIdFromRequest(httpRequest);
            if (userId == null) {
                response.setStatus("FAILED");
                response.setData("Unauthorized");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }
            
            EncounterResponseDTO data = encounterService.startOrGetActiveEncounter(request.getPatientId(), userId);
            response.setData(data);
            response.setStatus("SUCCESS");
            return ResponseEntity.ok(response);
            
        } catch (IllegalArgumentException e) {
            response.setStatus("FAILED");
            response.setData(e.getMessage());
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
            log.error("Error starting encounter", e);
            response.setStatus("FAILED");
            response.setData(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping("/{encounterId}/sign")
    public ResponseEntity<EMRResponseBean> signEncounter(
            @PathVariable Long encounterId,
            HttpServletRequest httpRequest) {
        
        EMRResponseBean response = new EMRResponseBean();
        try {
            Long userId = getUserIdFromRequest(httpRequest);
            if (userId == null) {
                response.setStatus("FAILED");
                response.setData("Unauthorized");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }
            
            EncounterResponseDTO data = encounterService.signEncounter(encounterId, userId);
            response.setData(data);
            response.setStatus("SUCCESS");
            return ResponseEntity.ok(response);
            
        } catch (IllegalArgumentException e) {
            response.setStatus("FAILED");
            response.setData(e.getMessage());
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
            log.error("Error signing encounter", e);
            response.setStatus("FAILED");
            response.setData(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
