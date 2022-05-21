package ceng453.backend.services.helper;

import org.json.JSONException;

public interface IHelper {

    String getPayloadFromToken(String token);

    String getUsernameFromToken(String token) throws JSONException;
}
