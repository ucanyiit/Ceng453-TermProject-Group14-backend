package ceng453.backend.services.helper;

import org.json.JSONException;

import java.util.HashSet;

public interface IHelper {

    String getPayloadFromToken(String token);

    String getUsernameFromToken(String token) throws JSONException;

    Integer getIntegerNotUsed(HashSet<Integer> usedNumbers, Integer limit);
}
