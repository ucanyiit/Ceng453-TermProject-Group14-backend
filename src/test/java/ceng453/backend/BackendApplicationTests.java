package ceng453.backend;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ceng453.backend.models.BaseResponse;
import org.springframework.http.HttpStatus;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import ceng453.backend.UserConfigs;

@SpringBootTest
class BackendApplicationTests {
    private String baseUrl = "http://localhost:8080/api/";


    @Test
    void contextLoads() {
        BaseResponse response = new BaseResponse(true, "Success", null);
        System.out.println(response.prepareResponse(HttpStatus.OK));
    }

    private String getStringFromInputStream(InputStream is) {

        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();

        String line;
        try {
            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();

    }

    @Test
    void apiTestHelloWorld() {
        try {
            URL url = new URL(baseUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("accept", "application/json");

            // This line makes the request
            InputStream responseStream = conn.getInputStream();

            String content = getStringFromInputStream(responseStream);
            JSONObject json = new JSONObject(content);
            // Finally we have the response
            assert json.get("status").equals(true);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    @Test
    void apiTestRegister1() {
        try {
            JSONObject userJson = UserConfigs.user1();

            URL url = new URL(baseUrl + "auth/register" + UserConfigs.makeQuery(userJson));
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("accept", "application/json");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            InputStream responseStream = conn.getInputStream();

            String content = getStringFromInputStream(responseStream);
            JSONObject json = new JSONObject(content);
            // Finally we have the response
            assert json.get("status").equals(true);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    void apiLoginTest1() {
        try {
            JSONObject userJson = UserConfigs.user1();
            JSONObject loginJson = new JSONObject();
            loginJson.put("username", userJson.get("username"));
            loginJson.put("password", userJson.get("password"));

            URL url = new URL(baseUrl + "auth/login" + UserConfigs.makeQuery(loginJson));
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("accept", "application/json");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            InputStream responseStream = conn.getInputStream();

            String content = getStringFromInputStream(responseStream);
            JSONObject json = new JSONObject(content);
            // Finally we have the response
            assert json.get("status").equals(true);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    void apiRemindPasswordTest1() {
        try {
            JSONObject userJson = UserConfigs.user1();
            JSONObject remindJson = new JSONObject();
            remindJson.put("username", userJson.get("username"));

            URL url = new URL(baseUrl + "auth/remind-password" + UserConfigs.makeQuery(remindJson));
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("accept", "application/json");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            InputStream responseStream = conn.getInputStream();

            String content = getStringFromInputStream(responseStream);
            JSONObject json = new JSONObject(content);
            // Finally we have the response
            assert json.get("status").equals(true);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    void apiRequestPasswordReset() {
        try {
            JSONObject userJson = UserConfigs.user1();
            JSONObject remindJson = new JSONObject();
            remindJson.put("username", userJson.get("username"));

            URL url = new URL(baseUrl + "auth/request-password-reset" + UserConfigs.makeQuery(remindJson));
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("accept", "application/json");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            InputStream responseStream = conn.getInputStream();

            String content = getStringFromInputStream(responseStream);
            JSONObject json = new JSONObject(content);
            // Finally we have the response
            assert json.get("status").equals(true);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    void apiResetPassword() {
        try {
            JSONObject userJson = UserConfigs.user1();
            JSONObject remindJson = new JSONObject();
            remindJson.put("username", userJson.get("username"));
            remindJson.put("password", userJson.get("password"));

            URL url = new URL(baseUrl + "auth/reset-password" + UserConfigs.makeQuery(remindJson));
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("accept", "application/json");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestMethod("GET");
            conn.setDoOutput(true);

            InputStream responseStream = conn.getInputStream();

            String content = getStringFromInputStream(responseStream);
            JSONObject json = new JSONObject(content);
            // Finally we have the response
            assert json.get("status").equals(true);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
