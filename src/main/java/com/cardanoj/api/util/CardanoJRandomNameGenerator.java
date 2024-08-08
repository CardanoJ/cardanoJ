package com.cardanoj.api.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;

/**
 * Utility class for generating random names based on the current date and time.
 * <p>
 * This component generates a string representation of the current date and time, formatted to include
 * year, month, day, hour, minute, second, and millisecond, which can be used as a unique name or identifier.
 * </p>
 */
@Component
public class CardanoJRandomNameGenerator {

    /**
     * Generates a unique string based on the current date and time.
     * <p>
     * The string is formatted to include year, month, day, hour, minute, second, and millisecond, resulting
     * in a timestamp-like string that can be used as a random name or identifier.
     * </p>
     *
     * @return a {@link String} representing the current date and time formatted as "yyyyMMddHHmmssSSS"
     */
    public String generate() {
        LocalDateTime now = LocalDateTime.now();

        // Format the date and time as a string
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
        String formattedDateTime = now.format(formatter);

        String randomString = formattedDateTime;

        return randomString;
    }
}
