package xcalibur.javaNative.classes;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Encryption
{

    private static String toSha1(String string) throws NoSuchAlgorithmException
    {
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        byte[] strB = string.getBytes(StandardCharsets.ISO_8859_1);
        md.update(strB,0,strB.length);
        return hex(md.digest());
    }

    private static String hex(byte[] bytes)
    {
        StringBuilder bldr = new StringBuilder();
        for(byte b : bytes)
        {
            int i = (b >>> 4) & 0x0F;
            int n = 0;
            do
            {
                bldr.append(0 <= i  && i <= 9 ? (char)('0'+i) : (char)('a'+(i-10)));
                i = b & 0x0F;
            }
            while(n++ < 1);
        }
        return bldr.toString();
    }

    public static String sha1(String string)
    {
        try
        {
            string = toSha1(string);
        }
        catch (Exception e)
        {
            // do nothing
        }
        return string;
    }

    public static String toBinary(String string)
    {
        String
                tmp;
        StringBuilder
                bldr = new StringBuilder();
        for(int i = 0; i < string.length(); i++)
        {
            tmp = Integer.toBinaryString(string.charAt(i));
            float n = tmp.length();
            int m = (int)(8 * (Math.ceil(n / (float)8.0)));
            // extra 0 will be added if binary length is not base on eight or a multiple of eight
            for(int ii = tmp.length(); ii < m ; ii++)  tmp = "0"+tmp;
            bldr.append(tmp);
            bldr.append(" ");
        }
        return bldr.toString().trim();
    }

}