package xcalibur.javaNative.classes;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class XJNUtilities
{
    public static boolean isURL(String url)
    {
        String[]
                strs = url.toLowerCase().split("://");
        return strs.length > 1 && (strs[0].equals("http") || strs[0].equals("https"));
    }

    public static boolean isEmail(String email)
    {
        boolean rval = false;
        String[]
                strs = new String[]
                {
                        email
                };
        strs = strs[0].split("@");
        if(strs.length > 1)
        {
            strs = strs[strs.length - 1].split("\\.");
            if(strs.length > 1) rval = true;
        }
        return rval;
    }

    public static boolean isNumeric(String numericalString)
    {
        boolean
                r = true;
        try
        {
            Long.valueOf(numericalString);
        }
        catch (Exception e)
        {
            r = false;
        }
        return  r;
    }

    public static boolean verifyDate(int year, int month, int day, ArrayList<IntegerValue> daysInMonth)
    {
        boolean
                rval = false;
        for(IntegerValue intval : daysInMonth)
        {
            if(intval.value == (month - 1))
            {
                if(intval.value == 1 && day == 29)
                {
                    float lpyr = ((float)year) / ((float)4);
                    if(lpyr - Math.floor(lpyr) == 0)
                    {
                        rval = true;
                        break;
                    }
                }
                else if(day < intval.integer)
                {
                    rval = true;
                    break;
                }
            }
        }
        return rval;
    }

    public static boolean isDuplicate(String[] strs, List<String[]> list)
    {
        boolean r = false;
        for(int i=0;i<list.size();i++)
        {
            if(strs.length == list.get(i).length)
            {
                int n = 0;
                for(int ii=0;ii<strs.length;ii++) if(strs[ii].equals(list.get(i)[ii])) n++;
                if(n == strs.length)
                {
                    r = true;
                    break;
                }
            }
        }
        return r;
    }

    public static boolean isDuplicate(String[] strings1, String[] strings2)
    {
        boolean r = false;
        for(int i=0;i<strings2.length;i++)
        {
            if(strings1.length == strings2.length)
            {
                int n = 0;
                for(int ii=0;ii<strings2.length;ii++) if(strings1[ii].equals(strings2[ii])) n++;
                if(n == strings1.length)
                {
                    r = true;
                    break;
                }
            }
        }
        return r;
    }

    public static boolean isDuplicate(String[] strings, String string)
    {
        boolean
                r = false;
        for(String str : strings)
        {
            if(str.equals(string))
            {
                r = true;
                break;
            }
        }
        return r;
    }

    public static class calendar
    {

        private static String fetchDate(String format)
        {
           return (new SimpleDateFormat(format, Locale.getDefault()).format(new Date()));
        }

        public static String currently(boolean is24HoursFormat)
        {
            String now = "yyyy-MM-dd-" + (is24HoursFormat ? "HH" : "hh") + "-mm-ss-a";
            now = fetchDate(now);
            return now;
        }

        public static String year(String format)
        {
            return (format.equals("yyyy") || format.equals("YYYY") ? fetchDate(format) : null);
        }

        public static String month(String format)
        {
            return (format.equals("MM") || format.equals("mm") ? fetchDate(format) : null);
        }

        public static String day(String format)
        {
            return (format.equals("dd") ? fetchDate(format) : null);
        }

        public static String hour(String format)
        {
            return (format.equals("HH") || format.equals("hh") ? fetchDate(format) : null);
        }

        public static String minute(String format)
        {
            return (format.equals("mm") ? fetchDate(format) : null);
        }

        public static String second(String format)
        {
            return (format.equals("ss") ? fetchDate(format) : null);
        }

        public static String timestamp(String patch)
        {
            if(patch == null) patch = "";
            return (
                            year("yyyy") +
                            patch +
                            month("MM") +
                            patch +
                            day("dd") +
                            patch +
                            hour("HH") +
                            patch +
                            minute("mm")+
                            patch +
                            second("ss")
            );
        }

        public static class gregorian
        {
            public static int findDayForADate(int year, int month, int day)
            {
                month = month < 3 ? (month += 10) : (month -= 2);
                if(month == 11 || month == 12) year -= 1;
                int d = Integer.valueOf(String.valueOf(year).substring(2,4));
                int c = Integer.valueOf(String.valueOf(year).substring(0,2));
                int e = (int) Math.floor(((float)((13*month)-1)) / 5);
                int f = (int) Math.floor(((float) d) / 4);
                int g = (int) Math.floor(((float) c) / 4);
                return ((day + e + d + f + g + (5 * c)  ) % 7);
            }
        }

    }

    public static long calculateTimedDifferencesInMilliSecond(
            int beginYear,
            int endYear,
            int beginMonth,
            int endMonth,
            ArrayList<IntegerValue> daysInMonth,
            int beginDay,
            int endDay,
            int beginHour,
            int endHour,
            int beginMinute,
            int endMinute
    )
    {
        return (
                XJNUtilities.getYearsInMillisecond(beginYear, endYear) +
                        XJNUtilities.getMonthsInMillisecond(beginMonth, endMonth, daysInMonth) +
                        XJNUtilities.getDaysInMillisecond(endDay - beginDay) +
                        XJNUtilities.getHoursInMillisecond(endHour - beginHour) +
                        XJNUtilities.getMinutesInMillisecond(endMinute - beginMinute)
         );
    }

    public static long getYearsInMillisecond(int beginYear, int endYear)
    {
        long rval = 0;
        if(endYear > beginYear) for(int i = beginYear; i <endYear; i++) rval += (((i / 4.0) - (Math.floor(i / 4.0)) == 0 ? 366 : 365) * (24 * (60 *(60 * .1))));
        rval *= 10000;
        return rval;
    }

    public static long getMonthsInMillisecond(int beginMonth, int endMonth, ArrayList<IntegerValue> daysInMonth)
    {
        long rval = 0;
        int n = endMonth - beginMonth;
        for(int i = (n > 0 ? beginMonth : endMonth); i < (n > 0 ? endMonth : beginMonth); i++)
        {
            for(IntegerValue intval : daysInMonth)
            {
                if((i - 1) == intval.value)
                {
                    rval += (intval.integer * (24 * (60 * (60 * .1))));
                    break;
                }
            }
        }
        if(n < 0) rval = 0 - rval;
        return (rval * 10000);
    }

    public static long getDaysInMillisecond(int days)
    {
        return ((long)(days * (24 * (60 * (60 * .1))))) * 10000;
    }

    public static long getHoursInMillisecond(int hour)
    {
        return ((long) (hour * (60 * (60 * .1)))) * 10000;
    }

    public static long getMinutesInMillisecond(int minute)
    {
        return ((long) (minute * (60 * 1000)));
    }

    public static float millisecondInSecond(long millisecond)
    {
        return (float)(millisecond / 1000);
    }

    public static float millisecondInMinute(long millisecond)
    {
        return (float)(millisecond / ((60 * .1) * 10000));
    }

    public static float millisecondInHour(long millisecond)
    {
        return (float)(millisecond / ((60 * (60  * .1)) * 10000));
    }

    public static float millisecondInDay(long millisecond)
    {
        return (float)(millisecond / ((24 * (60 * (60 * .1))) * 10000));
    }

    public static float millisecondInMonth(long millisecond)
    {
        return (float)(millisecond / ((30 * (24 * (60 * (60 * .1)))) * 10000));
    }

    public static float millisecondInYear(long millisecond)
    {
        return (float)(millisecond / ((365 * (24 * (60 * (60 * .1)))) * 10000));
    }

    public static long dateToLong(int year, int month, int day)
    {
        return Integer.valueOf(year + (month < 10 ? "0"+month : String.valueOf(month)) + (day < 10 ? "0"+day : String.valueOf(day)));
    }

    public static int getLayoutHeightBasedOnString(String string , int maxWidth, int textSize)
    {
        int
                height = 0,
                tmp;
        if(string != null && (tmp = string.length() * textSize) > maxWidth)
        {
            tmp = tmp / maxWidth;
            height = height * tmp;
        }
        return height;
    }

    public static int[] addToArray(int[] array, int intToAdd)
    {
        int[]
                tmp = new int[array.length + 1];
        System.arraycopy(array, 0, tmp, 0, array.length);
        tmp[tmp.length - 1] = intToAdd;
        return tmp;
    }

    public static String[] addToArray(String[] array, String stringToAdd)
    {
        String[]
                tmp = new String[array.length + 1];
        System.arraycopy(array, 0, tmp, 0, array.length);
        tmp[tmp.length - 1] = stringToAdd;
        return tmp;
    }

    public static int[] removeFromArray(int[] array, int intToRemove)
    {
        int n = 0;
        int[] tmp = new int[array.length - 1];
        for(int pos : array)
        {
            if(pos != intToRemove)
            {
                tmp[n] = pos;
                n++;
            }
        }
        return tmp;
    }

    public static String[] removeFromArray(String[] array, int positionToRemove)
    {
        int
                n = 0;
        String
                [] tmp = new String[array.length - 1];
        for(String pos : array)
        {
            if(n != positionToRemove)
            {
                tmp[n] = pos;
                n++;
            }
        }
        return tmp;
    }

    public static String[] combineArray(String[] array1, String[] array2)
    {
        String[]
                rval = new String[array1.length + array2.length];
        System.arraycopy(array1, 0, rval, 0, array1.length);
        System.arraycopy(array2, 0, rval, array1.length, array2.length);
        return rval;
    }

    public static String splitAndJoint(String string, String tokenToRemove, String tokenToJoint, int[] positionsToIgnore)
    {
        if(string != null && !string.isEmpty())
        {
            String[]
                    splits = string.split(tokenToRemove);
            StringBuilder
                    builder = new StringBuilder();
            if(splits.length > 1)
            {
                int
                        m = splits.length - 1;
                for(int i = 0; i < splits.length; i++)
                {
                    boolean
                            pass = false;
                    for(int ii = 0; ii < positionsToIgnore.length; i++)
                    {
                        if(i == positionsToIgnore[ii])
                        {
                            pass = true;
                            positionsToIgnore = removeFromArray(positionsToIgnore, ii);
                            break;
                        }
                    }
                    if(!pass)
                    {
                        builder.append(splits[i]);
                        if(i < m)builder.append(tokenToJoint);
                    }
                }
                string = builder.toString();
            }
        }
        return string;
    }

    public static String timeConverter(float hour, boolean convertTo24hours, boolean addTimePrefix)
    {
        String
                r = null;
        String[]
                hrs = String.valueOf(hour).split("\\.");
        int
                m = Integer.valueOf(hrs[1]);
        if(hour >= 0 && hour <= 24 && m > -1 && m < 60)
        {
            if(Integer.valueOf(hrs[0]) < 10) hrs[0] = "0"+hrs[0];
            if(m < 10) hrs[1] = "0"+hrs[1];
            if(!convertTo24hours)
            {
                if(hour >= 12)
                {
                    hour -= 12;
                    hour = hour == 0 ? 12 : hour;
                    r = "PM";
                }
                else
                {
                    hour = hour == 0 ? 12 : hour;
                    r = "AM";
                }
                hour = hour > 12 ? hour - 12 : hour;
            }
            hrs = new String[]
                    {
                            String.valueOf(hour).split("\\.")[0],
                            "."+hrs[1]
                    };
            hrs = new String[]
                    {
                            (Integer.valueOf(hrs[0]) < 10 && convertTo24hours ? "0"+hrs[0] : hrs[0]) + hrs[1]
                    };
            if(addTimePrefix && !convertTo24hours) hrs[0]+=(" "+r);
            r = hrs[0];
        }
        return r;
    }
}
