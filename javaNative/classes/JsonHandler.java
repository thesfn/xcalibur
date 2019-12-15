package xcalibur.javaNative.classes;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.net.URLDecoder;

public class JsonHandler
{

    private static List<String[]> digest(BufferedInputStream stream) throws IOException
    {
        BufferedReader
                Reader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8));
        List<String[]>
                list = new ArrayList<>();
        StringBuilder
                bldr = new StringBuilder();
        String
                ln ;
        while ((ln =  Reader.readLine()) != null){bldr.append(ln);}
        try
        {
            JSONObject
                    jsonObj = new JSONObject(bldr.toString());
            for(int i=0;i<jsonObj.length();i++)
            {
                String
                        v = String.valueOf(i);
                list.add(new String[]{jsonObj.get(v).toString()});
            }
        }
        catch (JSONException e)
        {
            //do nothing
        }
        return list;
    }

    private static List<String[]> digest (String string)
    {
        List<String[]>
                listObj = new ArrayList<>();
        try
        {
            JSONObject
                    jsonObj = new JSONObject(string);
            for(int i=0; i<jsonObj.length() ;i++)
            {
                boolean
                        error = false;
                String
                        v = String.valueOf(i);
                try
                {
                    jsonObj.getJSONArray(v);
                }
                catch (Exception e)
                {
                    error = true;
                }
                if(!error)
                {
                    String[]
                            strObj = new String[jsonObj.getJSONArray(v).length()];
                    for(int ii=0; ii<jsonObj.getJSONArray(v).length(); ii++)
                    {
                        strObj[ii] = jsonObj.getJSONArray(v).get(ii) == null ? "" : String.valueOf(jsonObj.getJSONArray(v).get(ii));
                    }
                    listObj.add(strObj);
                }
                else
                {
                    listObj.add(
                            new String[]
                                    {
                                            String.valueOf(jsonObj.get(v))
                                    }
                    );
                }
            }
        }
        catch (Exception e)
        {
            //do nothing
        }
        return listObj;
    }

    private static String digestEncryptedStream(BufferedInputStream stream) throws Exception
    {
        BufferedReader
                Reader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8));
        StringBuilder
                bldr = new StringBuilder();
        String
                ln ;
        while ((ln =  Reader.readLine()) != null) bldr.append(ln);
        return Encryption.kalenEncryption.decrypt(
                URLDecoder.decode(
                        bldr.toString(),
                        "utf-8"
                )
        );
    }

    public static List<String[]> decryptedInputStream(BufferedInputStream stream)
    {
        List<String[]>
                list = new ArrayList<>();
        try
        {
            list = decode(digestEncryptedStream(stream));
        }
        catch (Exception e)
        {
            /*do nothing*/
        }
        return list;
    }

    public static List<String[]> decode(BufferedInputStream stream)
    {
        List<String[]>
                list = new ArrayList<>();
        try
        {
            list = digest(stream);
        }
        catch (IOException e)
        {
            /*do nothing*/
        }
        return list;
    }

    public static List<String[]> decode(String string)
    {
        List<String[]>
                list = new ArrayList<>();
        try
        {
            list = digest(string);
        }
        catch (Exception e)
        {
            /*do nothing*/
        }
        return list;
    }
}
