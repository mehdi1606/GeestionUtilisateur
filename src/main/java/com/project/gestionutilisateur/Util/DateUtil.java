package com.project.gestionutilisateur.Util;


import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

@Component
public class DateUtil {

    private static final DateTimeFormatter DEFAULT_FORMATTER =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    private static final DateTimeFormatter DATE_ONLY_FORMATTER =
            DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private static final DateTimeFormatter TIME_ONLY_FORMATTER =
            DateTimeFormatter.ofPattern("HH:mm:ss");

    public String formatDateTime(LocalDateTime dateTime) {
        if (dateTime == null) return null;
        return dateTime.format(DEFAULT_FORMATTER);
    }

    public String formatDate(LocalDateTime dateTime) {
        if (dateTime == null) return null;
        return dateTime.format(DATE_ONLY_FORMATTER);
    }

    public String formatTime(LocalDateTime dateTime) {
        if (dateTime == null) return null;
        return dateTime.format(TIME_ONLY_FORMATTER);
    }

    public long getMinutesBetween(LocalDateTime start, LocalDateTime end) {
        if (start == null || end == null) return 0;
        return ChronoUnit.MINUTES.between(start, end);
    }

    public long getHoursBetween(LocalDateTime start, LocalDateTime end) {
        if (start == null || end == null) return 0;
        return ChronoUnit.HOURS.between(start, end);
    }

    public long getDaysBetween(LocalDateTime start, LocalDateTime end) {
        if (start == null || end == null) return 0;
        return ChronoUnit.DAYS.between(start, end);
    }

    public boolean isToday(LocalDateTime dateTime) {
        if (dateTime == null) return false;
        LocalDateTime now = LocalDateTime.now();
        return dateTime.toLocalDate().equals(now.toLocalDate());
    }

    public boolean isThisWeek(LocalDateTime dateTime) {
        if (dateTime == null) return false;
        LocalDateTime now = LocalDateTime.now();
        return getDaysBetween(dateTime, now) <= 7;
    }

    public String getTimeAgo(LocalDateTime dateTime) {
        if (dateTime == null) return "Jamais";

        LocalDateTime now = LocalDateTime.now();
        long minutes = getMinutesBetween(dateTime, now);

        if (minutes < 1) return "À l'instant";
        if (minutes < 60) return minutes + " minute(s)";

        long hours = getHoursBetween(dateTime, now);
        if (hours < 24) return hours + " heure(s)";

        long days = getDaysBetween(dateTime, now);
        if (days < 30) return days + " jour(s)";
        if (days < 365) return (days / 30) + " mois";

        return (days / 365) + " an(s)";
    }
}
