package com.springboot.animelist.util;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class DataUtil {
    public String formatDateTimeToDatabaseStyle(LocalDateTime localDateTime){
        return DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:ss").format(localDateTime);
    }
}
