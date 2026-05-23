package com.ouremr.product.emrbean;

public class EMRResponseBean {

    Object data;
    String status;

    public void setData(Object data){
        this.data = data;
    }
    public Object getData(){
        return data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
