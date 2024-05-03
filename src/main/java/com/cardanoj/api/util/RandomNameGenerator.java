package com.cardanoj.api.util;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;
@Component
public class RandomNameGenerator {

	public String generate() {
		LocalDateTime now = LocalDateTime.now();

        // Format the date and time as a string
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
        String formattedDateTime = now.format(formatter);

        String randomString = formattedDateTime ;

        return randomString;
    }


}
