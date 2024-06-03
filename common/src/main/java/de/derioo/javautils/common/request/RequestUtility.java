package de.derioo.javautils.common.request;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@UtilityClass
public class RequestUtility {

    /**
     * Used to get a json response by a request builder <b>sync</b>
     * @param builder the builder
     * @return the response
     * @see RequestUtility#getJsonResponseAsync(HttpRequest.Builder)
     */
    public HttpResponse<String> getJsonResponse(HttpRequest.@NotNull Builder builder) throws IOException, InterruptedException {
        try (HttpClient client = HttpClient.newHttpClient()) {
            builder.header("Content-Type", "application/json");
            return client.send(builder.build(), HttpResponse.BodyHandlers.ofString());
        }
    }

    /**
     * Used to get a json response by a request builder <b>async</b>
     * @param builder the builder
     * @return the response
     * @see RequestUtility#getJsonResponse(HttpRequest.Builder)
     */
    public @NotNull CompletableFuture<HttpResponse<String>> getJsonResponseAsync(HttpRequest.@NotNull Builder builder) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return getJsonResponse(builder);
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }

    /**
     * Build a URI with query parameters
     * @param baseUri the base URI
     * @param queryParams the query parameters
     * @return the URI with query parameters
     * @throws URISyntaxException if URI syntax is incorrect
     */
    public URI buildUriWithParams(String baseUri, Map<String, String> queryParams) throws URISyntaxException {
        StringBuilder uriBuilder = new StringBuilder(baseUri);
        if (queryParams != null && !queryParams.isEmpty()) {
            uriBuilder.append("?");
            queryParams.forEach((key, value) -> uriBuilder.append(key).append("=").append(value).append("&"));
            uriBuilder.deleteCharAt(uriBuilder.length() - 1);
        }
        return new URI(uriBuilder.toString());
    }

}
