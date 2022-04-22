package ceng453.backend;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class UserConfigs {

    static public String makeQuery(JSONObject user) {
        String query = "";
        Iterator<String> keys = user.keys();
        try {
            do {
                String key = keys.next().toString();
                query += "&" + key + "='" + user.get(key);
            } while (keys.hasNext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return query;
    }

    static public JSONObject user1() {
        JSONObject user1 = null;
        try {
            user1 = new JSONObject();
            user1.put("username", "user1");
            user1.put("password", "user1password");
            user1.put("email", "user1mail@gmail.com");
            user1.put("passwordReminder", "user1password");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user1;
    }

    static public JSONObject user2() {
        JSONObject user2 = null;
        try {
            user2 = new JSONObject();
            user2.put("username", "user2");
            user2.put("password", "user2password");
            user2.put("email", "user2mail@gmail.com");
            user2.put("passwordReminder", "user2password");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user2;

    }

    static public JSONObject user3() {
        JSONObject user3 = null;
        try {
            user3 = new JSONObject();
            user3.put("username", "user3");
            user3.put("password", "user3password");
            user3.put("email", "user3mail@gmail.com");
            user3.put("passwordReminder", "user3password");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return user3;
    }

    static public JSONObject user4() {
        JSONObject user4 = null;
        try {
            user4 = new JSONObject();
            user4.put("username", "user4");
            user4.put("password", "user4password");
            user4.put("email", "user4mail@gmail.com");
            user4.put("passwordReminder", "user4password");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return user4;
    }

    static public JSONObject user5() {
        JSONObject user5 = null;
        try {
            user5 = new JSONObject();
            user5.put("username", "user5");
            user5.put("password", "user5password");
            user5.put("email", "user5mail@gmail.com");
            user5.put("passwordReminder", "user5password");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return user5;
    }

    static public JSONObject loginRequest(JSONObject user) {
        try {
            user.remove("email");
            user.remove("passwordReminder");
            return user;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    static public Map<String, String> registerRequest(JSONObject user) {
        try {
            Map<String, String> map = new HashMap<>();
            map.put("username", user.getString("username"));
            map.put("password", user.getString("password"));
            map.put("email", user.getString("email"));
            map.put("passwordReminder", user.getString("passwordReminder"));
            return map;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    static public JSONObject passwordReminderRequest(JSONObject user) {
        try {
            user.remove("email");
            user.remove("password");
            user.remove("passwordReminder");
            return user;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
