package com.braintech.carpooling.common.utility;


import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

/**
 * Created by Braintech on 19-09-2017.
 */

public class DateUtils {

    public static String getCurrentTime() {
        Calendar cal = Calendar.getInstance(TimeZone.getDefault());
        Date currentLocalTime = cal.getTime();
        DateFormat date = new SimpleDateFormat("hh:mm a");
        date.setTimeZone(TimeZone.getDefault());
        String localTime = date.format(currentLocalTime);
        return localTime;
    }

    public static String getCurrentTimeWithDate() {
        Calendar cal = Calendar.getInstance(TimeZone.getDefault());
        Date currentLocalTime = cal.getTime();
        DateFormat date = new SimpleDateFormat("dd-MM-yyyy hh:mm a");
        date.setTimeZone(TimeZone.getDefault());
        String localTime = date.format(currentLocalTime);
        return localTime;
    }

    public static String getCurrentTimeWithMinExtra(int minutes) {
        Calendar cal = Calendar.getInstance(TimeZone.getDefault());
        cal.add(Calendar.MINUTE, minutes);
        Date currentLocalTime = cal.getTime();
        DateFormat date = new SimpleDateFormat("hh:mm a");
        date.setTimeZone(TimeZone.getDefault());
        String localTime = date.format(currentLocalTime);
        return localTime;
    }


    public static boolean compareTimes(String str1, String str2) {
        try {

            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");

            Date date1 = formatter.parse(str1);

            Date date2 = formatter.parse(str2);


            if (date1.compareTo(date2) < 0) {
                return false;
            } else {
                return true;

            }

        } catch (ParseException e1) {
            e1.printStackTrace();
        }
        return false;
    }

    public static String getCurrentDate() {
        DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
        Calendar cal = Calendar.getInstance();
        String date = dateFormat.format(cal.getTime());
        return date;
    }

    public static String getCurrentDateOrders() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
        Calendar cal = Calendar.getInstance();
        String date = dateFormat.format(cal.getTime());
        return date;
    }

    public static long getDuration(String currentTime, String time) {

        long duration = 0;

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy hh:mm a");

        try {
            Date date1 = formatter.parse(currentTime);

            Date date2 = formatter.parse(time);

            long durationMilliSeconds = date2.getTime() - date1.getTime();
            duration = TimeUnit.MILLISECONDS.toMinutes(durationMilliSeconds);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return duration;
    }


    public static int getWeeksBetween(Date a, Date b) {

        if (b.before(a)) {
            return -getWeeksBetween(b, a);
        }
        a = resetTime(a);
        b = resetTime(b);

        Calendar cal = new GregorianCalendar();
        cal.setTime(a);
        int weeks = 0;
        while (cal.getTime().before(b)) {
            // add another week
            cal.add(Calendar.WEEK_OF_YEAR, 1);
            weeks++;
        }
        return weeks;
    }

    public static Date resetTime(Date d) {
        Calendar cal = new GregorianCalendar();
        cal.setTime(d);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    public static int monthsBetweenDates(Date startDate, Date endDate) {

        Calendar start = Calendar.getInstance();
        start.setTime(startDate);

        Calendar end = Calendar.getInstance();
        end.setTime(endDate);

        int monthsBetween = 0;
        int dateDiff = end.get(Calendar.DAY_OF_MONTH) - start.get(Calendar.DAY_OF_MONTH);

        if (dateDiff < 0) {
            int borrrow = end.getActualMaximum(Calendar.DAY_OF_MONTH);
            dateDiff = (end.get(Calendar.DAY_OF_MONTH) + borrrow) - start.get(Calendar.DAY_OF_MONTH);
            monthsBetween--;

            if (dateDiff > 0) {
                monthsBetween++;
            }
        } else {
            monthsBetween++;
        }
        monthsBetween += end.get(Calendar.MONTH) - start.get(Calendar.MONTH);
        monthsBetween += (end.get(Calendar.YEAR) - start.get(Calendar.YEAR)) * 12;
        return monthsBetween;
    }

    private static int truncateData(long value) {
        int resultValue = 0;
        if (value != 0) {
            String convertedValue = String.valueOf(value);
            String truncatedValue = convertedValue.substring(1, convertedValue.length());

            resultValue = Integer.parseInt(truncatedValue);
        }

        return resultValue;

    }

    public static int getCurrentDay(String givenDate) {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

        Date date1 = null;
        int day = -1;
        try {
            date1 = formatter.parse(givenDate);

            Calendar cal1 = Calendar.getInstance();
            Calendar cal2 = Calendar.getInstance();

            cal1.setTime(date1);
            cal2.setTime(new Date());

            if (cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)) {
                if (cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH)) {
                    day = cal2.get(Calendar.DAY_OF_MONTH);
                    // the date falls in current month
                    return day;
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return day;
    }

    public static int getCurrentWeek(String givenDate) {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

        Date date1 = null;
        int week = -1;
        try {
            date1 = formatter.parse(givenDate);

            Calendar cal1 = Calendar.getInstance();
            Calendar cal2 = Calendar.getInstance();

            cal1.setTime(date1);
            cal2.setTime(new Date());

            if (cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)) {
                {

                    if (cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH)) {
                        week = cal1.get(Calendar.WEEK_OF_MONTH);
                        // the date falls in current month
                        return week;
                    }
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return week;
    }

    public static boolean isToday(String givenDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

        Date date1 = null;
        try {
            date1 = formatter.parse(givenDate);

            Calendar cal1 = Calendar.getInstance();
            Calendar cal2 = Calendar.getInstance();

            cal1.setTime(date1);
            cal2.setTime(new Date());

            if (cal1.get(Calendar.DATE) == cal2.get(Calendar.DATE)) {
                {

                    return true;
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static int isCurrentYear(String givenDate) {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

        Date date1 = null;
        int month = -1;
        try {
            date1 = formatter.parse(givenDate);

            Calendar cal1 = Calendar.getInstance();
            Calendar cal2 = Calendar.getInstance();

            cal1.setTime(date1);
            cal2.setTime(new Date());

            if (cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)) {
                month = cal1.get(Calendar.MONTH);

                // the date falls in current month
                return month;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return month;
    }

    public static int isCurrentMonth(String givenDate) {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

        Date date1 = null;
        int month = -1;
        try {
            date1 = formatter.parse(givenDate);

            Calendar cal1 = Calendar.getInstance();
            Calendar cal2 = Calendar.getInstance();

            cal1.setTime(date1);
            cal2.setTime(new Date());

            if (cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)) {
                if (cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH))
                month = cal1.get(Calendar.MONTH);
                // the date falls in current month
                return month;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return month;
    }

    public static String convertDateFormat(String date, String inputFormat, String outputFormat) {
        SimpleDateFormat sdf = new SimpleDateFormat(inputFormat);
        Date testDate = null;
        String newFormat = "N/A";
        try {
            testDate = sdf.parse(date);
            SimpleDateFormat formatter = new SimpleDateFormat(outputFormat);
            newFormat = formatter.format(testDate);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return newFormat;
    }

    public static ArrayList<String> getMonthList() {
        ArrayList<String> monthsList = new ArrayList<String>();
        String[] months = new DateFormatSymbols().getMonths();
        for (int i = 0; i < months.length; i++) {
            String month = convertDateFormat(months[i], "MMM", "MMM");
            monthsList.add(month);
        }
        return monthsList;
    }

    public static ArrayList<String> getNumberOfDays() {
        ArrayList<String> daysList = new ArrayList<String>();
        Calendar cal1 = Calendar.getInstance();

        cal1.setTime(new Date());
        int days = cal1.getActualMaximum(Calendar.DAY_OF_MONTH);


        for (int i = 1; i <= days; i++)
            daysList.add(String.valueOf(i));


        return daysList;
    }

    public static ArrayList<String> getHours() {
        ArrayList<String> hoursList = new ArrayList<String>();

        hoursList.add("24:00");

        for (int i = 1; i <= 23; i++)
            hoursList.add(convertDateFormat(String.valueOf(i), "HH", "HH:mm"));

        return hoursList;
    }

    public static ArrayList<String> getWeek() {
        ArrayList<String> weekList = new ArrayList<String>();

        Calendar cal = Calendar.getInstance();

        int maxWeeknumber = cal.getActualMaximum(Calendar.WEEK_OF_MONTH);


        for (int i = 1; i <= maxWeeknumber; i++) {
            weekList.add(String.valueOf(i));
        }

        return weekList;
    }

    public static ArrayList<String> getMinutes() {
        ArrayList<String> arrayListMinute = new ArrayList<>();

        String finalTime = getCurrentTime();


        Calendar now = Calendar.getInstance();
        //add minutes to current date using Calendar.add method
        now.add(Calendar.HOUR, -3);

        SimpleDateFormat df = new SimpleDateFormat("HH:mm");
        String startTime = df.format(now.getTime());

        String newTime = startTime;
        arrayListMinute.add(newTime);

        while (!newTime.equals(finalTime)) {
            SimpleDateFormat df1 = new SimpleDateFormat("HH:mm");
            Date d = null;
            try {
                d = df.parse(newTime);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Calendar cal = Calendar.getInstance();
            cal.setTime(d);
            cal.add(Calendar.MINUTE, 10);
            newTime = df.format(cal.getTime());

            arrayListMinute.add(newTime);

        }

        return arrayListMinute;


    }

    public static boolean isValidYear(String year) {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy");

        Date date1 = null;

        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();

        cal2.setTime(new Date());

        String yearCurrent = String.valueOf(cal2.get(Calendar.YEAR));

        String subStringYear = yearCurrent.substring(0, 2);

        String appendYear = subStringYear + year;

        try {
            date1 = formatter.parse(appendYear);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        cal1.setTime(date1);


        if (cal1.get(Calendar.YEAR) >= cal2.get(Calendar.YEAR))
            return true;
        else
            return false;

    }
}

