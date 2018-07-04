package com.aaamarkin.kingofthehill.util;

import com.aaamarkin.kingofthehill.objects.User;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class UserSerializer extends StdSerializer<User> {

    public UserSerializer() {
        this(null);
    }

    public UserSerializer(Class<User> t) {
        super(t);
    }

    @Override
    public void serialize(
            User value, JsonGenerator jgen, SerializerProvider provider)
            throws IOException, JsonProcessingException {

        jgen.writeStartObject();

        jgen.writeStringField("creationDate", value.getCreationDate());
        jgen.writeStringField("updateDate", value.getUpdateDate());
        jgen.writeNumberField("xCoordinate", value.getXCoordinate());
        jgen.writeNumberField("yCoordinate", value.getYCoordinate());

        jgen.writeEndObject();
    }
}