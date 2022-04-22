package ceng453.backend;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import ceng453.backend.models.responses.BaseResponse;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;

import ceng453.backend.RestService;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import ceng453.backend.UserConfigs;
import org.springframework.http.ResponseEntity;

@SpringBootTest
class BackendApplicationTests {


    @Value("${server.address.development}")
    private String baseUrl;

    private RestService restService;


    @Test
    void contextLoads() {
        restService = new RestService(new RestTemplateBuilder());
        assert restService != null: "RestService is null";
    }

    @Test
    void apiTestPing() {
        try {
            restService = new RestService(new RestTemplateBuilder());
            ResponseEntity<BaseResponse> response = restService.getBaseResponse(baseUrl);
            System.out.println(response.getStatusCode());
            assert response.getStatusCode() == HttpStatus.OK: "Response is not OK";
            System.out.println(response.getBody().getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    @Test
    void apiTestRegister() {
        try {
            JSONObject userJson = UserConfigs.user5();
            restService = new RestService(new RestTemplateBuilder());
            ResponseEntity<BaseResponse> response = restService.postRequest(
                    baseUrl + "auth/register", UserConfigs.registerRequest(userJson));
            System.out.println(response.getStatusCode());
            assert response.getStatusCode() == HttpStatus.OK: "Response is not OK";
            System.out.println(response.getBody().getResponse());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
//
//    @Test
//    void apiLoginTest1() {
//        try {
//            JSONObject userJson = UserConfigs.user1();
//            JSONObject loginJson = new JSONObject();
//            loginJson.put("username", userJson.get("username"));
//            loginJson.put("password", userJson.get("password"));
//
//            URL url = new URL(baseUrl + "auth/login" + UserConfigs.makeQuery(loginJson));
//            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//            conn.setRequestProperty("accept", "application/json");
//            conn.setRequestProperty("Content-Type", "application/json");
//            conn.setRequestMethod("POST");
//            conn.setDoOutput(true);
//
//            InputStream responseStream = conn.getInputStream();
//
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }
//    }
//
//    @Test
//    void apiRemindPasswordTest1() {
//        try {
//            JSONObject userJson = UserConfigs.user1();
//            JSONObject remindJson = new JSONObject();
//            remindJson.put("username", userJson.get("username"));
//
//            URL url = new URL(baseUrl + "auth/remind-password" + UserConfigs.makeQuery(remindJson));
//            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//            conn.setRequestProperty("accept", "application/json");
//            conn.setRequestProperty("Content-Type", "application/json");
//            conn.setRequestMethod("POST");
//            conn.setDoOutput(true);
//
//            InputStream responseStream = conn.getInputStream();
//
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }
//    }
//
//    @Test
//    void apiRequestPasswordReset() {
//        try {
//            JSONObject userJson = UserConfigs.user1();
//            JSONObject remindJson = new JSONObject();
//            remindJson.put("username", userJson.get("username"));
//
//            URL url = new URL(baseUrl + "auth/request-password-reset" + UserConfigs.makeQuery(remindJson));
//            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//            conn.setRequestProperty("accept", "application/json");
//            conn.setRequestProperty("Content-Type", "application/json");
//            conn.setRequestMethod("POST");
//            conn.setDoOutput(true);
//
//            InputStream responseStream = conn.getInputStream();
//
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }
//    }
//
//    @Test
//    void apiResetPassword() {
//        try {
//            JSONObject userJson = UserConfigs.user1();
//            JSONObject remindJson = new JSONObject();
//            remindJson.put("username", userJson.get("username"));
//            remindJson.put("password", userJson.get("password"));
//
//            URL url = new URL(baseUrl + "auth/reset-password" + UserConfigs.makeQuery(remindJson));
//            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//            conn.setRequestProperty("accept", "application/json");
//            conn.setRequestProperty("Content-Type", "application/json");
//            conn.setRequestMethod("GET");
//            conn.setDoOutput(true);
//
//            InputStream responseStream = conn.getInputStream();
//
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }
//    }

}
