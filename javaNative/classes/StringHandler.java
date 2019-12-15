package xcalibur.javaNative.classes;

import android.content.Context;

import java.util.Random;
import java.util.regex.Pattern;

public final class StringHandler
{

    public static String illegalChars0 = "[!@#$%&* +=\\{\\}\\[\\]|'\"\\:;\\<\\>/?`~^,.-]";

    public static String illegalChars1 = "[[A-Za-z]!@#$%&* +=\\{\\}\\[\\]|'\"\\:;\\<\\>/?`~^,.-]";

    public static String regexChars0 = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public static String random(final String possibleChars, final int length)
    {
        StringBuilder bdlr = new StringBuilder();
        Random rndm = new Random();
        for(int i = 0; i < length; i++) bdlr.append(possibleChars.charAt(rndm.nextInt(possibleChars.length())));
        return bdlr.toString();
    }

    public static boolean find(final CharSequence charSequence, final String regex)
    {
        return ((Pattern.compile(regex)).matcher(charSequence)).find();
    }

    public static String uppercase(String string, int position)
    {
        StringBuilder bldr = new StringBuilder();
        for(int i= 0; i < string.length(); i++) if(i == position)bldr.append(string.substring(i,i+1).toUpperCase()); else bldr.append(string.substring(i, i+1));
        return bldr.toString();
    }

    public static String createStringFromResources(Context context, int[] res)
    {
        StringBuilder bldr = new StringBuilder();
        for(int r : res)
        {
            bldr.append(context.getResources().getString(r));
        }
        return bldr.toString().trim();
    }
}