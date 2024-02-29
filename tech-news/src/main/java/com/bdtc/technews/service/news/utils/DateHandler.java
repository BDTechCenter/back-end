package com.bdtc.technews.service.news.utils;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

@Component
public class DateHandler {

    public LocalDateTime getCurrentDateTime() {
        return LocalDateTime.now();
    }

    public String formatDate(LocalDateTime dateNow) {
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT);
        return dateNow.format(formatter);
    }

}
