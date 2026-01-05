package io.wulfcodes.serdes.util.serdes;

import java.io.IOException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class EmailSerializer extends JsonSerializer<String> {

    @Override
    public void serialize(String value, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (value == null) {
            jsonGenerator.writeNull();
            return;
        }

        String[] emailSegments = value.split("@");
        String username = emailSegments[0];

        String maskedUsername = username.replace(username.substring(1), "*****");
        String maskedEmail = value.replace(username, maskedUsername);

        jsonGenerator.writeString(maskedEmail);
    }
}
