package com.lyne.common;

import javax.persistence.AttributeConverter;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * 关于Spring Boot JPA使用LocalDateTime时序列化失败的问题：
 * http://blog.takwolf.com/2016/06/16/spring-data-support-jsr-310-datetime/index.html
 *
 * @author nn_liu
 * @Created 2017-11-15-17:09
 */

public class LocalDateTimeConcerter implements AttributeConverter<LocalDateTime, Timestamp> {

    @Override
    public Timestamp convertToDatabaseColumn(LocalDateTime localDateTime) {
        return Optional.ofNullable(localDateTime)
                .map(Timestamp::valueOf)
                .orElse(null);
    }
    @Override
    public LocalDateTime convertToEntityAttribute(Timestamp timestamp) {
        return Optional.ofNullable(timestamp)
                .map(Timestamp::toLocalDateTime)
                .orElse(null);
    }
}
