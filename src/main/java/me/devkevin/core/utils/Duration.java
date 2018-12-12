package me.devkevin.core.utils;

import java.text.*;
import java.util.concurrent.*;
import org.apache.commons.lang.time.*;

public class Duration
{
    public static DecimalFormat getDecimalFormat() {
        return new DecimalFormat ("0.0");
    }

    public static long parse(final String input) {
        if (input == null || input.isEmpty()) {
            return -1L;
        }
        long result = 0L;
        StringBuilder number = new StringBuilder();
        for (int i = 0; i < input.length(); ++i) {
            final char c = input.charAt(i);
            if (Character.isDigit(c)) {
                number.append(c);
            }
            else {
                final String str;
                if (Character.isLetter(c) && !(str = number.toString()).isEmpty()) {
                    result += convert(Integer.parseInt(str), c);
                    number = new StringBuilder();
                }
            }
        }
        return result;
    }

    private static long convert(final int value, final char unit) {
        switch (unit) {
            case 'y': {
                return value * TimeUnit.DAYS.toMillis(365L);
            }
            case 'M': {
                return value * TimeUnit.DAYS.toMillis(30L);
            }
            case 'd': {
                return value * TimeUnit.DAYS.toMillis(1L);
            }
            case 'h': {
                return value * TimeUnit.HOURS.toMillis(1L);
            }
            case 'm': {
                return value * TimeUnit.MINUTES.toMillis(1L);
            }
            case 's': {
                return value * TimeUnit.SECONDS.toMillis(1L);
            }
            default: {
                return -1L;
            }
        }
    }

    public static class IntegerCountdown
    {
        public static String setFormat(final Integer value) {
            final int remainder = value * 1000;
            final int seconds = remainder / 1000 % 60;
            final int minutes = remainder / 60000 % 60;
            final int hours = remainder / 3600000 % 24;
            return String.valueOf((hours > 0) ? String.format("%02d:", hours) : "") + String.format("%02d:%02d", minutes, seconds);
        }
    }

    public static class LongCountdown
    {
        public static String setFormat(final Long value) {
            if (value < TimeUnit.MINUTES.toMillis(1L)) {
                return String.valueOf(Duration.getDecimalFormat().format(value / 1000.0)) + "s";
            }
            return DurationFormatUtils.formatDuration((long)value, String.valueOf((value >= TimeUnit.HOURS.toMillis(1L)) ? "HH:" : "") + "mm:ss");
        }
    }

    public static String getRemaining(final long millis, final boolean milliseconds) {
        return getRemaining(millis, milliseconds, true);
    }

    public static String getRemaining(final long duration, final boolean milliseconds, final boolean trail) {
        if (milliseconds && duration < TimeUnit.MINUTES.toMillis (1L)) {
            return (trail ? DateTimeFormats.REMAINING_SECONDS_TRAILING : DateTimeFormats.REMAINING_SECONDS).get().format(duration * 0.001);
        }
        return DurationFormatUtils.formatDuration(duration, ((duration >= TimeUnit.HOURS.toMillis(1L)) ? "HH:" : "") + "mm:ss");
    }
}
