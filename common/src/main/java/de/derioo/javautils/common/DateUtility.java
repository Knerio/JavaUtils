package de.derioo.javautils.common;

import lombok.experimental.UtilityClass;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

@UtilityClass
public class DateUtility {

    public final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy, HH:mm", Locale.GERMAN);
    public final SimpleDateFormat HOUR_AND_MINUTES_FORMAT = new SimpleDateFormat("HH:mm", Locale.GERMAN);


    /**
     * Parses default dates like "16:06, 09.06.2024"
     * @param time the time string
     * @return the date
     * @throws ParseException the exception if any
     */
    public Date parseDefaultTime(String time) throws ParseException {
        return DATE_FORMAT.parse(time);
    }

    /**
     * Parses dynamic dates like "16:06, 09.06.2024" and "16:06"
     * @param time the time string
     * @return the date
     * @throws ParseException the exception if any
     */
    public Date parseDynamic(String time) throws ParseException {
        Date date;
        try {
            date = DATE_FORMAT.parse(time);
        } catch (ParseException ignored) {
            date = parseHoursAndMinutes(time);
        }
        return date;
    }

    /**
     * Parses dates like "16:06"
     * @param time the time string
     * @return the date
     * @throws ParseException the exception if any
     */
    public Date parseHoursAndMinutes(String time) throws ParseException {
        Date parsedTime = HOUR_AND_MINUTES_FORMAT.parse(time);

        Calendar calendar = Calendar.getInstance();

        Calendar timeCalendar = Calendar.getInstance();
        timeCalendar.setTime(parsedTime);
        int hours = timeCalendar.get(Calendar.HOUR_OF_DAY);
        int minutes = timeCalendar.get(Calendar.MINUTE);

        calendar.set(Calendar.HOUR_OF_DAY, hours);
        calendar.set(Calendar.MINUTE, minutes);

        return calendar.getTime();
    }

}
