package de.derioo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.derioo.request.Response;
import org.junit.jupiter.api.Test;

import static de.derioo.JacksonUtility.PRETTY_PRINT_WRITER;
import static de.derioo.JacksonUtility.isValidJson;
import static org.assertj.core.api.Assertions.assertThat;

public class JacksonUtilityTest {

    @Test
    public void testJsonValidator() throws JsonProcessingException {
        Response response = new Response("", 2);
        assertThat(isValidJson(new ObjectMapper().writeValueAsString(response))).isTrue();
        assertThat(isValidJson("[asd]")).isFalse();
    }

    @Test
    public void testPrettyPrinting() throws JsonProcessingException {
        Response response = new Response("asd", 2);
        String notPrettyJson = "{\"fact\":\"asd\",\"length\":2}";
        assertThat(PRETTY_PRINT_WRITER.writeValueAsString(response))
                .isNotEqualTo(notPrettyJson);
        assertThat(new ObjectMapper().writeValueAsString(response))
                .isEqualTo(notPrettyJson);
    }

}
