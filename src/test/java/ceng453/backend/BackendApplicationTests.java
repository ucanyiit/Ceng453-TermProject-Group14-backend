package ceng453.backend;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ceng453.backend.models.BaseResponse;
import org.springframework.http.HttpStatus;
import sun.net.www.http.HttpClient;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

@SpringBootTest
class BackendApplicationTests {
    private String baseUrl = "http://localhost:8080/";
    @Test
    void contextLoads() {
        BaseResponse response = new BaseResponse(true, "Success", null);
        System.out.println(response.prepareResponse(HttpStatus.OK));
    }

    @Test
    void apiTestHelloWorld() {
        try {
            URL url = new URL(baseUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("accept", "application/json");

            // This line makes the request
            InputStream responseStream = conn.getInputStream();

            ObjectMapper mapper = new ObjectMapper();
            BaseResponse apod = mapper.readValue(responseStream, BaseResponse.class);

            StringBuilder textBuilder = new StringBuilder();
            try (Reader reader = new BufferedReader(new InputStreamReader
                    (responseStream, Charset.forName(StandardCharsets.UTF_8.name())))) {
                int c = 0;
                while ((c = reader.read()) != -1) {
                    textBuilder.append((char) c);
                }
            }
            // Finally we have the response
            System.out.println(textBuilder.toString());
            System.out.println(apod.response);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
