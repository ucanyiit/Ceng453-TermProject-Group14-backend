package ceng453.backend;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ceng453.backend.models.BaseResponse;
import org.springframework.http.HttpStatus;

@SpringBootTest
class BackendApplicationTests {

    @Test
    void contextLoads() {
        BaseResponse response = new BaseResponse(true, "Success", null);
        System.out.println(response.prepareResponse(HttpStatus.OK));
    }
    public static int main(String[] args) {
        return 0;
    }
}
