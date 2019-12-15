package xcalibur.androidDependent.classes;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.List;
import java.util.ArrayList;
import javax.net.ssl.HttpsURLConnection;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import xcalibur.androidDependent.interfaces.XADCallback;
import xcalibur.javaNative.classes.FileHandler;
import xcalibur.javaNative.classes.JsonHandler;

public class Network
{

    public static String urlEncode(String string)
    {
        String
                rvalue;
        try
        {
            rvalue = URLEncoder.encode(string, "utf-8");
        }
        catch (Exception e)
        {
            rvalue = null;
        }
        return rvalue;
    }

    private static boolean isHttps(String url)
    {
        boolean
                bool = false;
        String[]
                strs = url.split("://");
        if(strs.length > 1) bool = strs[0].equals("https");
        return bool;
    }

    public final static class download
    {

        public final static class universal extends AsyncTask<String[],Void,File>
        {

            boolean
                    overwrite = false;
            File
                    fl;
            String
                    pth;
            XADCallback
                    cb;

            public universal(String pathToFile, String url, boolean overwriteExistingFile, XADCallback callback)
            {
                overwrite = overwriteExistingFile;
                cb = callback;
                looper(pathToFile,url,1);
            }

            private void looper(String pathToFile, String url, int loopCount)
            {
                // pathToFile : Path to target file
                // url : Url to download object
                // loopCount : Ensure each downloaded object is unique to avoid overwriting file with similar name
                pth = pathToFile;
                fl = new File(pth);
                if(fl.exists() && fl.isFile())
                {
                    if(overwrite)
                    {
                        fl.delete();
                        FileHandler.createFile(pathToFile);
                        execute(new String[]{url});
                    }
                    else
                    {
                        StringBuilder
                                bldr = new StringBuilder();
                        String[]
                                strs = pathToFile.split("/");
                        String[]
                                flNm = strs[strs.length-1].split("\\.");
                        for(int i=1; i<strs.length-1; i++)
                        {
                            bldr.append("/");
                            bldr.append(strs[i]);
                        }
                        bldr.append("/");
                        if(flNm.length <= 1)
                        {
                            bldr.append(flNm[0]);
                            bldr.append(loopCount);
                        }
                        else
                        {
                            bldr.append(flNm[0]);
                            bldr.append(loopCount);
                            bldr.append(".");
                            bldr.append(flNm[1]);
                        }
                        pth = bldr.toString();
                        fl = new File(pth);
                        if(fl.exists() && fl.isFile())
                        {
                            looper(pathToFile,url,loopCount+1);
                        }
                        else
                        {
                            execute(new String[]{url});
                        }
                    }
                }
                else
                {
                    FileHandler.createFile(pathToFile);
                    execute(new String[]{url});
                }
            }

            @Override
            protected File doInBackground(String[]... strs)
            {
                try
                {
                    int
                            rcode;
                    InputStream
                            inp;
                    URL
                            url = new URL(strs[0][0]);
                    HttpURLConnection
                            http = null;
                    HttpsURLConnection
                            https = null;
                    try
                    {
                        if(isHttps(strs[0][0]))
                        {
                            https = (HttpsURLConnection) url.openConnection();
                            https.setDoInput(true);
                            https.connect();
                            rcode = https.getResponseCode();
                            if(rcode == HttpsURLConnection.HTTP_MOVED_TEMP)
                            {
                                url = new URL(https.getHeaderField("Location"));
                                https = (HttpsURLConnection) url.openConnection();
                                https.setDoInput(true);
                                https.connect();
                                rcode = https.getResponseCode();
                            }
                            inp = new BufferedInputStream(https.getInputStream());
                        }
                        else
                        {
                            http = (HttpURLConnection) url.openConnection();
                            http.setDoInput(true);
                            http.connect();
                            rcode = http.getResponseCode();
                            if(rcode == HttpURLConnection.HTTP_MOVED_TEMP)
                            {
                                url = new URL(http.getHeaderField("Location"));
                                http = (HttpURLConnection) url.openConnection();
                                http.setDoInput(true);
                                http.connect();
                                rcode = http.getResponseCode();
                            }
                            inp = new BufferedInputStream(http.getInputStream());
                        }
                        if(rcode == HttpURLConnection.HTTP_OK)
                        {
                            BufferedInputStream rdr = new BufferedInputStream(inp);
                            OutputStream out = new FileOutputStream(fl);
                            try
                            {
                                int n ;
                                byte[] bytes = new byte[64*1024];
                                while ((n = rdr.read(bytes,0,64*1024)) > -1) out.write(bytes,0,n);
                            }
                            finally
                            {
                                out.flush();
                                out.close();
                                rdr.close();
                                if(cb != null) cb.send(pth);
                                pth = null;
                                fl = null;
                                cb = null;
                            }
                        }
                        else if(cb != null)
                        {
                            cb.send("");
                        }
                        inp.close();
                        if(http != null) http.disconnect();
                        if(https != null) https.disconnect();
                    }
                    catch (Exception e)
                    {
                        if(cb != null) cb.send("");
                        Log.e("Exception", "Exception @ androidDependent @ Network.java " + e.toString());
                    }
                }
                catch (Exception e)
                {
                    if(cb != null) cb.send("");
                    Log.d("Exception", "String provided was not an URL @ androidDependent @ Network.java "+ e.toString());
                }
                return null;
            }
        }

        protected final static class bitmap extends AsyncTask<String[],Void, Bitmap>
        {

            bitmap()
            {
                execute();
            }

            @Override
            protected Bitmap doInBackground(String[]... strs)
            {
                Bitmap btmp = null;
                try
                {
                    URL url = new URL(strs[0][0]);
                    try
                    {
                        if(isHttps(strs[0][0]))
                        {
                            HttpsURLConnection https = (HttpsURLConnection) url.openConnection();
                            btmp = BitmapFactory.decodeStream(https.getInputStream());
                            https.disconnect();
                        }
                        else
                        {
                            HttpURLConnection http = (HttpURLConnection) url.openConnection();
                            btmp = BitmapFactory.decodeStream(http.getInputStream());
                            http.disconnect();
                        }
                    }
                    catch (IOException e )
                    {
                        Log.d("IOException", "IOException @ androidDependent @ Network.java");
                    }
                }
                catch (MalformedURLException e)
                {
                    Log.d("MalformedURLException", "String provided was not an URL @ androidDependent @ Network.java");
                }
                return btmp;
            }
        }

    }

    public final static class request extends JsonHandler
    {

        public final static class formUrlEncoded extends AsyncTask<String[],Void,List<String[]>>
        {

            private XADCallback
                    c;
            private String
                    u;
            private boolean
                    decrypt,
                    cancel = false;

            public formUrlEncoded(String[] data, String url, boolean decryptedResponse, XADCallback callback)
            {
                u = url;
                c = callback;
                decrypt = decryptedResponse;
                execute(data);
            }

            @Override
            protected List<String[]> doInBackground(String[]... strs)
            {
                List<String[]> ls = new ArrayList<>();
                BufferedInputStream is = null;
                try
                {
                    int rCode;
                    HttpURLConnection http;
                    HttpsURLConnection https;
                    URL url = new URL(u);
                    StringBuilder req = new StringBuilder();
                    for (int i=0; i<strs[0].length; i++)
                    {
                        req.append("DATA");
                        req.append(String.valueOf(i));
                        req.append("=");
                        req.append(URLEncoder.encode(strs[0][i],"utf-8"));
                        if(i < (strs[0].length - 1)) req.append("&&");
                    }
                    byte[] obj = req.toString().getBytes(Charset.forName("UTF-8"));
                    int objLen = obj.length;
                    if(isHttps(u))
                    {
                        https = (HttpsURLConnection) url.openConnection();
                        https.setReadTimeout(7500);
                        https.setConnectTimeout(7500);
                        https.setRequestMethod("POST");
                        https.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                        https.setRequestProperty("charset", "utf-8");
                        https.setRequestProperty("Content-Length", Integer.toString(objLen));
                        https.setUseCaches(false);
                        https.setDoInput(true);
                        https.setDoOutput(true);
                        OutputStream os = https.getOutputStream();
                        DataOutputStream author = new DataOutputStream(new BufferedOutputStream(os));
                        author.write(obj);
                        author.flush();
                        author.close();
                        https.connect();
                        rCode = https.getResponseCode();
                        Log.d("ServerResponseCode","Server response code is "+rCode);
                        is = new BufferedInputStream(https.getInputStream());
                        if(rCode == HttpsURLConnection.HTTP_OK) ls = decrypt ? decryptedInputStream(is) : readIt(is);
                        https.disconnect();
                    }
                    else
                    {
                        http = (HttpURLConnection) url.openConnection();
                        http.setReadTimeout(7500);
                        http.setConnectTimeout(7500);
                        http.setRequestMethod("POST");
                        http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                        http.setRequestProperty("charset", "utf-8");
                        http.setRequestProperty("Content-Length", Integer.toString(objLen));
                        http.setUseCaches(false);
                        http.setDoInput(true);
                        http.setDoOutput(true);
                        OutputStream os = http.getOutputStream();
                        DataOutputStream author = new DataOutputStream(new BufferedOutputStream(os));
                        author.write(obj);
                        author.flush();
                        author.close();
                        http.connect();
                        rCode = http.getResponseCode();
                        Log.d("ServerResponseCode","Server response code is "+rCode);
                        is = new BufferedInputStream(http.getInputStream());
                        if(rCode == HttpURLConnection.HTTP_OK) ls = decrypt ? decryptedInputStream(is) : readIt(is);
                        http.disconnect();
                    }
                    return ls;
                }
                catch (IOException e)
                {
                    return ls;
                }
                finally
                {
                    try
                    {
                        if(is != null) is.close();
                    }
                    catch (IOException e)
                    {
                        Log.d("DEBUG_LOG", "InputStream was not initialized @ androidDependent @ Network.java");
                    }
                }
            }

            @Override
            protected void onPreExecute()
            {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(List<String[]> strs)
            {
                super.onPostExecute(strs);
                if(!cancel && c != null)c.send(strs);
            }

            private List<String[]> readIt(BufferedInputStream stream)
            {
                return decode(stream);
            }

        }

        public final static class multiPart extends AsyncTask<Void,Void,List<String[]>>
        {
            private XADCallback
                    cb;
            private String
                    u;
            private String
                    filename;
            private boolean
                    decrypt;
            private String[]
                    s;
            private byte[]
                    bytes;

            public multiPart(String url, File file, String fileName, String[] strings, boolean decryptResponse, XADCallback callback)
            {
                try
                {
                    FileInputStream
                            fis = new FileInputStream(file);
                    try
                    {
                        bytes = new byte[(int)file.length()];
                        if(fis.read(bytes) == -1) Log.e("ERROR", "fileInputStream.read return -1 @network.java @line 350");
                    }
                    catch (IOException e)
                    {
                        Log.e("ERROR", e.toString());
                    }
                    finally
                    {
                        try
                        {
                            fis.close();
                        }
                        catch (IOException e)
                        {
                            Log.e("ERROR", e.toString());
                        }
                    }
                }catch (FileNotFoundException e)
                {
                    Log.e("ERROR", e.toString());
                }
                u = url;
                cb = callback;
                s = strings;
                filename = fileName;
                decrypt = decryptResponse;
                execute();
            }

            @Override
            protected void onPreExecute()
            {
                super.onPreExecute();
            }

            @Override
            protected List<String[]> doInBackground(Void... voids)
            {
                List<String[]>
                        ls = new ArrayList<>();
                BufferedInputStream
                        is = null;
                String
                        le = "\r\n";
                String
                        dbHyphen = "--";
                String
                        boundary = "*****";
                try
                {
                    URL url = new URL (u);
                    StringBuilder bldr = new StringBuilder();
                    for(int i = 0; i < s.length; i++)
                    {
                        bldr.append(dbHyphen);bldr.append(boundary);bldr.append(le);
                        bldr.append("Content-Disposition: form-data; name=\"DATA");bldr.append(i);bldr.append("\"");bldr.append(le);
                        bldr.append(le);
                        bldr.append(URLEncoder.encode(s[i],"utf-8"));bldr.append(le);
                    }
                    bldr.append(dbHyphen);bldr.append(boundary);bldr.append(le);
                    bldr.append("Content-Disposition: form-data; name=\"FILE\"; filename=\"");bldr.append(filename);bldr.append("\"");bldr.append(le);
                    bldr.append("Content-Type: *");bldr.append(le);
                    bldr.append("Content-Transfer-Encoding: binary");bldr.append(le);
                    bldr.append(le);
                    byte[] cntn = bldr.toString().getBytes();
                    byte[] clsr = (le+dbHyphen+boundary+dbHyphen+le).getBytes();
                    int cntnLngth = cntn.length+bytes.length+clsr.length;
                    if(isHttps(u))
                    {
                        HttpsURLConnection https = (HttpsURLConnection) url.openConnection();
                        https.setReadTimeout(7500);
                        https.setConnectTimeout(60000);
                        https.setDoInput(true);
                        https.setDoOutput(true);
                        https.setUseCaches(false);
                        https.setRequestMethod("POST");
                        https.setRequestProperty("Connection","Keep-Alive");
                        https.setRequestProperty("Content-Length",String.valueOf(cntnLngth));
                        https.setRequestProperty("Content-Type","multipart/form-data;boundary="+boundary);
                        DataOutputStream author = new DataOutputStream(new BufferedOutputStream(https.getOutputStream()));
                        author.write(cntn);
                        author.write(bytes);
                        author.write(clsr);
                        author.flush();
                        author.close();
                        https.connect();
                        is = new BufferedInputStream(https.getInputStream());
                        int rCode = https.getResponseCode();
                        Log.d("ServerResponseCode","Server response code is "+rCode);
                        if(rCode == HttpsURLConnection.HTTP_OK) ls = decrypt ? decryptedInputStream(is) : readIt(is);
                        https.disconnect();
                    }
                    else
                    {
                        HttpURLConnection http = (HttpURLConnection) url.openConnection();
                        http.setReadTimeout(7500);
                        http.setConnectTimeout(60000);
                        http.setDoInput(true);
                        http.setDoOutput(true);
                        http.setUseCaches(false);
                        http.setRequestMethod("POST");
                        http.setRequestProperty("Connection", "Keep-Alive");
                        http.setRequestProperty("Content-Length",String.valueOf(cntnLngth));
                        http.setRequestProperty("Content-Type","multipart/form-data;boundary="+boundary);
                        DataOutputStream author = new DataOutputStream(new BufferedOutputStream(http.getOutputStream()));
                        author.write(cntn);
                        author.write(bytes);
                        author.write(clsr);
                        author.flush();
                        author.close();
                        http.connect();
                        is = new BufferedInputStream(http.getInputStream());
                        int rCode = http.getResponseCode();
                        Log.d("ServerResponseCode","Server response code is "+rCode);
                        if(rCode == HttpsURLConnection.HTTP_OK) ls = decrypt ? decryptedInputStream(is) : readIt(is);
                        http.disconnect();
                    }
                    return ls;
                }
                catch (IOException e)
                {
                    return ls;
                }
                finally
                {
                    try
                    {
                        if(is != null) is.close();
                    }
                    catch (IOException e)
                    {
                        Log.d(
                                "DEBUG_LOG",
                                "InputStream was not initialized @ androidDependent @ Network.java @ doBackground @ multipart" + "\n"+ e.toString()
                        );
                    }
                }
            }

            @Override
            protected void onPostExecute(List<String[]> strs)
            {
                super.onPostExecute(strs);
                if(cb != null) cb.send(strs);
            }

            private List<String[]>readIt(BufferedInputStream stream)
            {
                return decode(stream);
            }
        }
    }
}