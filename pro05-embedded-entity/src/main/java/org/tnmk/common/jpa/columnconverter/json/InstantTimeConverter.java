package org.tnmk.common.jpa.columnconverter.json;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.sql.Timestamp;
import java.time.Instant;

/**
 * Created by mick on 16/05/18.
 */
@Converter
public class InstantTimeConverter implements AttributeConverter<Instant, Timestamp> {
    @Override
    public Timestamp convertToDatabaseColumn(Instant instant) {
        if (instant != null) {
            return Timestamp.from(instant);
        }
        return null;
    }

    @Override
    public Instant convertToEntityAttribute(Timestamp timestamp) {
        if (timestamp != null) {
            return timestamp.toInstant();
        }
        return null;
    }
}
