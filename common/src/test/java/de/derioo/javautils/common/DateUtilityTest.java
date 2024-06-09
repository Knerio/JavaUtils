package de.derioo.javautils.common;

import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static de.derioo.javautils.common.DateUtility.*;
import static org.assertj.core.api.Assertions.*;
public class DateUtilityTest {

    @Test
    public void testDefault() throws ParseException {
        Date date = new Date(1717943040000L);
        assertThat(parseDefaultTime("09.06.2024, 16:24:00")).isEqualTo(date);
    }

    @Test
    public void testHoursAndMinutes() throws ParseException {
         SimpleDateFormat format = new SimpleDateFormat("HH:mm", Locale.GERMAN);
        Date date = new Date(System.currentTimeMillis());
        assertThat(parseHoursAndMinutes(format.format(date))).isEqualTo(date);
    }

    @Test
    public void testDynamic() throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm", Locale.GERMAN);
        SimpleDateFormat longFormat = new SimpleDateFormat("dd.MM.yyyy, HH:mm", Locale.GERMAN);
        Date date = new Date(System.currentTimeMillis());
        assertThat(parseDynamic(format.format(date))).isEqualTo(date);
        assertThat(parseDynamic(format.format(longFormat))).isEqualTo(date);
    }

}
