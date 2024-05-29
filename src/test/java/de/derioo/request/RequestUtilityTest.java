package de.derioo.request;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

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


    
}
