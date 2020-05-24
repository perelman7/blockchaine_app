package com.ether.web3test.service.util.timeconvertor;

import lombok.extern.slf4j.Slf4j;

import java.math.BigInteger;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Slf4j
public class TimeConverter {

    private static DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy hh:mm:ss");

    public static String convert(BigInteger milliseconds) {
        String result = null;
        try {
            LocalDateTime date = Instant
                    .ofEpochMilli(milliseconds.longValue())
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime();
            result = date.format(FORMATTER);
        } catch (Exception e) {
            log.error("Convert date error, message: {}", e.getMessage());
        }
        return result;
    }
}
