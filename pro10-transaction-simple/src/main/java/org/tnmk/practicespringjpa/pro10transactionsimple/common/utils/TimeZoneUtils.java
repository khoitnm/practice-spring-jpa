package org.tnmk.practicespringjpa.pro10transactionsimple.common.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public interface TimeZoneUtils {
    String DEFAULT_DATE_TIME_PATTERN = "yyyy-MM-dd'T'HH:mm:ss";
    String PRETTY_DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    static ZonedDateTime atZoneId(long epochSecond, String zoneIdString) {
        Instant instant = Instant.ofEpochSecond(epochSecond);
        return atZoneId(instant, zoneIdString);
    }

    /**
     * @param instant
     * @param zoneIdString example America/Toronto
     * @return
     */
    static ZonedDateTime atZoneId(Instant instant, String zoneIdString) {
        ZoneId zoneId = ZoneId.of(zoneIdString);
        ZonedDateTime dateTimeAtExchangeTimeZone = ZonedDateTime.ofInstant(instant, zoneId);
        return dateTimeAtExchangeTimeZone;
    }

    static ZonedDateTime atZoneId(ZonedDateTime zonedDateTime, ZoneId zoneId) {
        ZonedDateTime dateTimeAtSpecificZoneId = zonedDateTime.withZoneSameInstant(zoneId);
        return dateTimeAtSpecificZoneId;
    }

    static ZonedDateTime atUTC(ZonedDateTime zonedDateTime) {
        ZonedDateTime dateTimeAtUTC = zonedDateTime.withZoneSameInstant(ZoneOffset.UTC);
        return dateTimeAtUTC;
    }

    static ZonedDateTime atUTC(LocalDateTime dateTimeAtUTC) {
        return ZonedDateTime.of(dateTimeAtUTC, ZoneOffset.UTC);
    }

    static ZonedDateTime atLocalZoneId(ZonedDateTime zonedDateTime) {
        return atZoneId(zonedDateTime, ZoneId.systemDefault());
    }

    static String formatAtLocalZoneId(LocalDateTime localDateTime) {
        ZonedDateTime zonedDateTime = atUTC(localDateTime);
        return formatAtLocalZoneId(zonedDateTime);
    }

    static String formatPrettyAtLocalZoneId(ZonedDateTime zonedDateTime) {
        return formatAtLocalZoneId(zonedDateTime, PRETTY_DATE_TIME_PATTERN);
    }

    static String formatAtLocalZoneId(ZonedDateTime zonedDateTime) {
        return formatAtLocalZoneId(zonedDateTime, DEFAULT_DATE_TIME_PATTERN);
    }

    static String formatAtLocalZoneId(ZonedDateTime zonedDateTime, String pattern) {
        ZonedDateTime atLocalZoneId = atLocalZoneId(zonedDateTime);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        String formattedString = atLocalZoneId.format(formatter);
        return formattedString;
    }

    static ZonedDateTime parseZonedDateTimeAtUTC(String dateTimeString, String dateTimePattern){
        LocalDateTime date = LocalDateTime.parse(dateTimeString, DateTimeFormatter.ofPattern(dateTimePattern));
        return ZonedDateTime.of(date, ZoneOffset.UTC);
    }

    static OffsetDateTime parseOffsetDateTimeAtUTC(String dateTimeString, String dateTimePattern) {
        LocalDateTime date = LocalDateTime.parse(dateTimeString, DateTimeFormatter.ofPattern(dateTimePattern));
        return OffsetDateTime.of(date, ZoneOffset.UTC);
    }
}
