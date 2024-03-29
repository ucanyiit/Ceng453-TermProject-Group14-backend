package ceng453.backend.services.helper;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.HashSet;

@Service
public class Helper implements IHelper {
    @Override
    public String getPayloadFromToken(String token) {
        String[] chunks = token.split("\\."); // split jwt token into its header and payload
        Base64.Decoder decoder = Base64.getUrlDecoder();

        return new String(decoder.decode(chunks[1]));
    }

    @Override
    public String getUsernameFromToken(String token) throws JSONException {
        JSONObject json = new JSONObject(this.getPayloadFromToken(token));
        return (String) json.get("sub");
    }

    @Override
    public Integer getIntegerNotUsed(HashSet<Integer> usedNumbers, Integer limit) {
        while (true) {
            int randomNumber = (int) (Math.random() * limit);
            if (!usedNumbers.contains(randomNumber)) {
                return randomNumber;
            }
        }
    }
}
