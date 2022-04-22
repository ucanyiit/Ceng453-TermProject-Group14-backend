package ceng453.backend;

import ceng453.backend.models.responses.BaseResponse;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest
class BackendApplicationTests {


    @Value("${server.address.development}")
    private String baseUrl;

    private RestService restService;


    @Test
    void contextLoads() {
        restService = new RestService(new RestTemplateBuilder());
        assert restService != null : "RestService is null";
    }

    @Test
    void apiTestPing() {
        restService = new RestService(new RestTemplateBuilder());
        ResponseEntity<BaseResponse> response = restService.getBaseResponse(baseUrl);
        System.out.println(response.getStatusCode());
        assert response.getStatusCode() == HttpStatus.OK: "Response is not OK";
        System.out.println(response.getBody().getMessage());
    }

    /**
     * Test the Register Endpoint. It uses the UserConfigs class to get the user data and tries to save the user to the database.
     * Nothing else is tested.
     */
    @Test
    void apiTestRegister() {
        JSONObject userJson = UserConfigs.user5();
        restService = new RestService(new RestTemplateBuilder());
        ResponseEntity<BaseResponse> response = restService.postRequest(
                baseUrl + "auth/register", UserConfigs.registerRequest(userJson));
        System.out.println(response.getStatusCode());
        assert response.getStatusCode() == HttpStatus.OK: "Response is not OK";
    }

    /**
     * Test the Login Endpoint. It uses the UserConfigs class to get the user data and tries to login the user.
     * Nothing else is tested.
     */
    @Test
    void apiLoginTest() {
        JSONObject userJson = UserConfigs.user5();
        restService = new RestService(new RestTemplateBuilder());
        ResponseEntity<BaseResponse> response = restService.postRequest(
                baseUrl + "auth/login", UserConfigs.loginRequest(userJson));
        System.out.println(response.getStatusCode());
        assert response.getStatusCode() == HttpStatus.OK: "Response is not OK";
    }

    /**
     * Test the Remind Password Endpoint. It uses the UserConfigs class to get the user data and tries to retrieve the
     * reminder password string of the user.
     * Nothing else is tested.
     */
    @Test
    void apiRemindPasswordTest() {
        JSONObject userJson = UserConfigs.user5();
        restService = new RestService(new RestTemplateBuilder());
        ResponseEntity<BaseResponse> response = restService.postRequest(
                baseUrl + "auth/remind-password", UserConfigs.passwordReminderRequest(userJson));
        System.out.println(response.getStatusCode());
        assert response.getStatusCode() == HttpStatus.OK: "Response is not OK";
    }

    /**
     * Test the Reset Password Endpoint. It uses the UserConfigs class to get the user data and tries to reset the
     * password of the user by sending a token to its email.
     */
    @Test
    void apiRequestPasswordReset() {
        JSONObject userJson = UserConfigs.user5();
        restService = new RestService(new RestTemplateBuilder());
        ResponseEntity<BaseResponse> response = restService.postRequest(
                baseUrl + "auth/request-password-reset", UserConfigs.passwordReminderRequest(userJson));
        System.out.println(response.getStatusCode());
        assert response.getStatusCode() == HttpStatus.OK: "Response is not OK";
    }

}
