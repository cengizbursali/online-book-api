package com.getir.onlinebookapi.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Clock {

    private static boolean isFrozen;
    private static LocalDateTime timeSet;

    public static synchronized void freeze() {
        isFrozen = true;
    }

    public static synchronized void freeze(LocalDateTime date) {
        freeze();
        setTime(date);
    }

    public static synchronized void freeze(Date date) {
        freeze();
        setTime(DateUtils.convertDateToLocalDateTime(date));
    }

    public static synchronized void unfreeze() {
        isFrozen = false;
        timeSet = null;
    }

    public static LocalDateTime now() {
        LocalDateTime dateTime = LocalDateTime.now();
        if (isFrozen) {
            if (timeSet == null) {
                timeSet = dateTime;
            }
            return timeSet;
        }

        return dateTime;
    }

    public static synchronized void setTime(LocalDateTime date) {
        timeSet = date;
    }
}