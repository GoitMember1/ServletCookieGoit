package org.example;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import java.io.IOException;

public class HttpStatusChecker {
    private OkHttpClient client;

    public HttpStatusChecker() {
        client = new OkHttpClient();
    }

    public String getStatusImage(int code) throws Exception {
        String url = "https://http.cat/" + code + ".jpg";
        Request request = new Request.Builder().url(url).build();
        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                return url;
            } else {
                throw new Exception("Image not found for HTTP status " + code);
            }
        } catch (IOException e) {
            throw new Exception("Error checking status image: " + e.getMessage());
        }
    }
}
