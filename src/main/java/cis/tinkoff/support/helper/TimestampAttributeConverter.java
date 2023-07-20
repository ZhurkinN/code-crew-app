package cis.tinkoff.support.helper;

import jakarta.inject.Singleton;
import jakarta.persistence.AttributeConverter;

import java.sql.Timestamp;
import java.util.Objects;

@Singleton
public class TimestampAttributeConverter implements AttributeConverter<Long, Timestamp> {

    @Override
    public Timestamp convertToDatabaseColumn(Long attribute) {
        return Objects.isNull(attribute)
                ? null
                : new Timestamp(attribute);
    }

    @Override
    public Long convertToEntityAttribute(Timestamp dbData) {
        return Objects.isNull(dbData)
                ? null
                : dbData.getTime();
    }
}
