package de.derioo.request;

import lombok.NoArgsConstructor;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

public class RequestUtility {

    public static HttpResponse<String> getJsonResponse(HttpRequest.@NotNull Builder builder) {
        try (
                HttpClient client = HttpClient.newHttpClient();
        ) {
            builder.header("Content-Type", "application/json");
            return client.send(builder.build(), HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static @NotNull CompletableFuture<HttpResponse<String>> getJsonResponseAsync(HttpRequest.@NotNull Builder builder) {
        return CompletableFuture.supplyAsync(() -> getJsonResponse(builder));
    }

}
