package ceng453.backend.models;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class BaseResponse {
    public boolean status;
    public String message;
    public String response;

    /**
     * The base response model for all the responses. If there will be any extension to this class, the child class should
     * be cast to BaseResponse.
     * @param status true if the request is successful, false otherwise
     * @param message the error message if the request is unsuccessful. Otherwise it will be emptry string.
     * @param response the response of the request as a string. This field can be converted into the required object.
     */
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

    /**
     * This method will return the response as a ResponseEntity object.
     * @param status The http status of the response.
     * @return The response as a ResponseEntity object.
     */
    public ResponseEntity<BaseResponse> prepareResponse(HttpStatus status) {
        return new ResponseEntity<>(this, status);
    }

}

