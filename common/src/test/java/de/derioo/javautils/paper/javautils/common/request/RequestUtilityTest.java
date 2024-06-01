package de.derioo.javautils.paper.javautils.common.request;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class RequestUtilityTest {
    
    @Test
    public void testGetResponse() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            HttpResponse<String> response = RequestUtility
                    .getJsonResponse(
                            HttpRequest.newBuilder()
                                    .uri(new URI("https://catfact.ninja/fact"))
                                    .GET()
                    );
            String body = response.body();
            assertThat(body).isNotNull();
            assertThat(mapper.readValue(body, Response.class)).isNotNull();
        } catch (URISyntaxException | JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testBuildURIWithParams() throws URISyntaxException {
        URI uri = RequestUtility.buildUriWithParams("http://localhost:1080/testParams", Map.of("key1", "value1", "key2", "value2"));
        assertThat(uri.toString()).isIn("http://localhost:1080/testParams?key1=value1&key2=value2", "http://localhost:1080/testParams?key2=value2&key1=value1");
    }


    
}
