package ceng453.backend;

import org.json.JSONObject;

public class UserConfigs {

    public JSONObject user1() {
        JSONObject user1 = null;
        try {
        user1 = new JSONObject();
        user1.put("username", "user1");
        user1.put("password", "user1");
        user1.put("email", "test@gmail.com");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user1;
    }
}
