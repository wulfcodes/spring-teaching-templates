package io.wulfcodes.serdes.util.serdes;

import java.io.IOException;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class UsernameDeserializer extends JsonDeserializer<String> {
    @Override
    public String deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        String usernameRaw = jsonParser.getText();
        String[] segments = usernameRaw.split(" ");
        StringBuilder stringBuilder = new StringBuilder();
        for (String segment : segments)
            stringBuilder.append(segment.toLowerCase().trim());
        return stringBuilder.toString();
    }
}
