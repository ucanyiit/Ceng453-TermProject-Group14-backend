package ceng453.backend.models.responses;


import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
@Setter
public class BaseResponse {
    public boolean status;
    public String message;

    /**
     * The base response model for all the responses. If there will be any extension to this class, the child class should
     * be cast to BaseResponse.
     * @param status true if the request is successful, false otherwise
     * @param message the error message if the request is unsuccessful. Otherwise it will be emptry string.
     */
    public BaseResponse(boolean status, String message) {
        this.status = status;
        this.message = message;
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

