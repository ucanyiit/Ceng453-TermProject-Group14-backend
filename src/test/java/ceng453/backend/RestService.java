package ceng453.backend;

import ceng453.backend.models.BaseResponse;
import org.json.JSONObject;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Map;

@Service
public class RestService {

    private final RestTemplate restTemplate;

    public RestService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public String getPostsPlainJSON(String url) {
        return this.restTemplate.getForObject(url, String.class);
    }

    public ResponseEntity<BaseResponse> getBaseResponse(String url) {
        ResponseEntity<BaseResponse> response = this.restTemplate.getForEntity(url, BaseResponse.class);
        return response;
    }

    public ResponseEntity<BaseResponse> postRequest(String url, Map<String, String> object) {
        // create headers
        HttpHeaders headers = new HttpHeaders();
        // set `content-type` header
        headers.setContentType(MediaType.APPLICATION_JSON);
        // set `accept` header
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        HttpEntity<Map<String, String>> entity = new HttpEntity<>(object, headers);
        ResponseEntity<BaseResponse> response = this.restTemplate.postForEntity(url, entity, BaseResponse.class);
        return response;
    }
}