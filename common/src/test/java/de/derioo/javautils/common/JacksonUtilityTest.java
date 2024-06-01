package de.derioo.javautils.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.derioo.javautils.common.request.Response;
import de.derioo.javautils.paper.javautils.common.JacksonUtility;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class JacksonUtilityTest {

    @Test
    public void testJsonValidator() throws JsonProcessingException {
        Response response = new Response("", 2);
        Assertions.assertThat(JacksonUtility.isValidJson(new ObjectMapper().writeValueAsString(response))).isTrue();
        Assertions.assertThat(JacksonUtility.isValidJson("[asd]")).isFalse();
    }

    @Test
    public void testPrettyPrinting() throws JsonProcessingException {
        Response response = new Response("asd", 2);
        String notPrettyJson = "{\"fact\":\"asd\",\"length\":2}";
        Assertions.assertThat(JacksonUtility.PRETTY_PRINT_WRITER.writeValueAsString(response))
                .isNotEqualTo(notPrettyJson);
        assertThat(new ObjectMapper().writeValueAsString(response))
                .isEqualTo(notPrettyJson);
    }

}
