package ceng453.backend.models;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class BaseResponse {
    public boolean status;
    public String message;
    public String response;

    public BaseResponse(boolean status, String message, String response) {
        this.status = status;
        this.message = message;
        this.response = response;
    }

    public boolean getStatus() {
        return this.status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getResponse() {
        return this.response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public ResponseEntity<BaseResponse> prepareResponse(HttpStatus status) {
        return new ResponseEntity<>(this, status);
    }

}

