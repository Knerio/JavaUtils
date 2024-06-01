package de.derioo.javautils.paper.javautils.common;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.experimental.UtilityClass;

@UtilityClass
public class JacksonUtility {

    /**
     * This is a basic writer for pretty printing
     */
    public final ObjectWriter PRETTY_PRINT_WRITER = new ObjectMapper()
            .writerWithDefaultPrettyPrinter();

    /**
     * Checks if the provided string is valid json
     *
     * @param json the json string
     * @return true if the json is valid, false otherwise
     */
    public boolean isValidJson(String json) {
        try (JsonParser parser = new ObjectMapper().getFactory()
                .createParser(json)) {
            while (parser.nextToken() != null) {
                //continue parsing
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }


}
