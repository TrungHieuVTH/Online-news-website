package com.messi.king.messinews.utils;

import com.github.scribejava.apis.GoogleApi20;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth20Service;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class GoogleUtils {
    private static final String PROTECTED_RESOURCE_URL = "https://www.googleapis.com/oauth2/v3/userinfo";
    private static final String clientId = "561751142391-ule8ddkurnbtmpu4947m3vn223m4dfko.apps.googleusercontent.com";
    private static final String clientSecret = "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";

    private static final OAuth20Service service = new ServiceBuilder(clientId)
            .apiSecret(clientSecret)
            .defaultScope("https://www.googleapis.com/auth/userinfo.email")
            .callback("http://localhost:8080/MessiNews_war_exploded/Account/GLogin")
            .build(GoogleApi20.instance());
    public static String getAuthURL() {
        final Map<String, String> additionalParams = new HashMap<>();
        additionalParams.put("prompt", "consent");
        final String authorizationUrl = service.createAuthorizationUrlBuilder()
                .additionalParams(additionalParams)
                .build();
        return authorizationUrl;
    }
    public static List<String> getInfo(String code) throws IOException, ExecutionException, InterruptedException {
        OAuth2AccessToken accessToken = service.getAccessToken(code);
//        accessToken = service.refreshAccessToken(accessToken.getRefreshToken());
        final String requestUrl = PROTECTED_RESOURCE_URL;
        final OAuthRequest request = new OAuthRequest(Verb.GET, requestUrl);
        service.signRequest(accessToken, request);
        List <String> responseData = new ArrayList<>();
        try (Response response = service.execute(request)) {
            final JSONObject obj = new JSONObject(response.getBody());
            responseData.add(Integer.toString(response.getCode()));
            responseData.add((String)obj.get("email"));
            return responseData;
        }
    }
}