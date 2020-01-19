package tin.thurein.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtils {
    /**
     * format date to string
     *
     * @param format
     * @param date
     * @return date string in format
     */
    public static String formatDate(String format, Date date) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.format(date);
        } catch (Exception e) {
            System.err.println("While formatting date : " + e.getMessage());
            return null;
        }
    }

    /**
     * parse string to date
     *
     * @param format
     * @param dateStr
     * @return date from string in format
     */
    public static Date parseToDate(String format, String dateStr) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.parse(dateStr);
        } catch (Exception ex) {
            System.err.println("While parsing to date : " + ex.getMessage());
            return null;
        }
    }

    /**
     * change date in from format to to format
     *
     * @param fromFormat
     * @param toFormat
     * @param dateStr
     * @return
     */
    public static String changeDateFormat(String fromFormat, String toFormat, String dateStr) {
        try {
            SimpleDateFormat from = new SimpleDateFormat(fromFormat);
            Date date = from.parse(dateStr);
            SimpleDateFormat to = new SimpleDateFormat(toFormat);
            return to.format(date);
        } catch (Exception e) {
            System.err.println("While changing date format : " + e.getMessage());
            return null;
        }
    }

    /**
     * format date to string including locale
     *
     * @param format
     * @param date
     * @param locale
     * @return
     */
    public static String formatDate(String format, Date date, Locale locale) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format, locale);
            return sdf.format(date);
        } catch (Exception e) {
            System.err.println("While formatting date : " + e.getMessage());
            return null;
        }
    }

    /**
     * parse string in format to date including locale
     *
     * @param format
     * @param dateStr
     * @param locale
     * @return
     */
    public static Date parseToDate(String format, String dateStr, Locale locale) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format, locale);
            return sdf.parse(dateStr);
        } catch (Exception ex) {
            System.err.println("While parsing to date : " + ex.getMessage());
            return null;
        }
    }

    /**
     * change date string from from-format to to-format including locale
     *
     * @param fromFormat
     * @param toFormat
     * @param dateStr
     * @param locale
     * @return
     */
    public static String changeDateFormat(String fromFormat, String toFormat, String dateStr, Locale locale) {
        try {
            SimpleDateFormat from = new SimpleDateFormat(fromFormat, locale);
            Date date = from.parse(dateStr);
            SimpleDateFormat to = new SimpleDateFormat(toFormat, locale);
            return to.format(date);
        } catch (Exception e) {
            System.err.println("While changing date format : " + e.getMessage());
            return null;
        }
    }
}
