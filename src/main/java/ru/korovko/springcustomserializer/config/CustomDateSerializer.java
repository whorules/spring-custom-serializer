package ru.korovko.springcustomserializer.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.boot.jackson.JsonComponent;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;

import static java.util.Objects.isNull;

@JsonComponent
public class CustomDateSerializer {

    @PostConstruct
    public void init() {
        System.err.println("Serializer has been initialized");
    }

    public static class LocalDateTimeSerializer extends JsonSerializer<LocalDateTime> {

        private static final String UTC_0_OFFSET_ID = "Z";
        private static final String UTC_0_TIMEZONE = "+00:00";

        @Override
        public void serialize(LocalDateTime localDateTime, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            if (!isNull(localDateTime)) {
                String date;
                OffsetDateTime timeUtc = localDateTime.atOffset(ZoneOffset.systemDefault().getRules().getOffset(LocalDateTime.now()));
                if (UTC_0_OFFSET_ID.equals(timeUtc.getOffset().getId())) {
                    date = timeUtc.toString().replace(UTC_0_OFFSET_ID, UTC_0_TIMEZONE);
                } else {
                    date = timeUtc.toString();
                }
                jsonGenerator.writeString(date);
            }
        }
    }

    public static class LocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {

        @Override
        public LocalDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            String date = jsonParser.getText();
            if (date.isEmpty()) {
                return null;
            }
            try {
                ZonedDateTime userDateTime = ZonedDateTime.parse(date);
                ZonedDateTime serverTime = userDateTime.withZoneSameInstant(ZoneId.systemDefault());
                return serverTime.toLocalDateTime();
            } catch (DateTimeParseException e) {
                try {
                    return LocalDateTime.parse(date);
                } catch (DateTimeParseException ex) {
                    throw new IllegalArgumentException("Error while parsing date", ex);
                }
            }
        }
    }
}