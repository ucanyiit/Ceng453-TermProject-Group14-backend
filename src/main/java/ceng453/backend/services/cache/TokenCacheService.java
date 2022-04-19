package ceng453.backend.services.cache;

import java.util.HashMap;
import java.util.Map;

public interface TokenCacheService {
    static final String TOKEN_CACHE_NAME = "tokenCache";
    static final String TOKEN_CACHE_KEY = "token";
    static final Map<String, String> TOKEN_CACHE = new HashMap<>();

    static void cacheToken(String username, String token) {
        TOKEN_CACHE.put(username, token);
    }

    static String getToken(String username) {
        return TOKEN_CACHE.get(username);
    }

    static void removeToken(String username) {
        TOKEN_CACHE.remove(username);
    }
}

