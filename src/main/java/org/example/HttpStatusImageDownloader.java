package org.example;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class HttpStatusImageDownloader {
    private HttpStatusChecker checker;

    public HttpStatusImageDownloader() {
        checker = new HttpStatusChecker();
    }

    public void downloadStatusImage(int code) throws Exception {
        String imageUrl = checker.getStatusImage(code);
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(imageUrl).build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                try (InputStream inputStream = response.body().byteStream();
                     FileOutputStream outputStream = new FileOutputStream(code + ".jpg")) {
                    byte[] buffer = new byte[2048];
                    int bytesRead;
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                    }
                }
            } else {
                throw new Exception("Failed to download image for HTTP status " + code);
            }
        } catch (IOException e) {
            throw new Exception("Error downloading status image: " + e.getMessage());
        }
    }
}
