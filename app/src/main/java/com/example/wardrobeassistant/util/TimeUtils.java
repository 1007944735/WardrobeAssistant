package com.example.wardrobeassistant.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtils {
    public static String format(long millis) {
        return format(millis, "yyyy-MM-dd HH:mm:ss");
    }

    public static String format(long millis, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(millis));
    }
}
