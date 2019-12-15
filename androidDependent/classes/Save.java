package xcalibur.androidDependent.classes;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import android.graphics.Bitmap;
import android.util.Log;
import xcalibur.androidDependent.interfaces.XADCallback;

public class Save
{

    public static final int
            RETURN_NOTHING = 0,
            RETURN_AS_FILE_TYPE = 1,
            RETURN_AS_PATH_STRING = 2;

    public Save(Bitmap bitmap, String path, Bitmap.CompressFormat compressionFormat, XADCallback callback, int saveReturnType)
    {
        new bitmap(bitmap, path, compressionFormat, callback, saveReturnType);
    }

    private final class bitmap
    {
        Bitmap.CompressFormat cp;
        Bitmap b;
        String p;
        XADCallback c;
        int r;

        bitmap(Bitmap bitmap, String path, Bitmap.CompressFormat compressionFormat, XADCallback callback, int saveReturnType)
        {
            b =  bitmap;
            p = path;
            cp = compressionFormat;
            c = callback;
            r = saveReturnType;
            new task().thread.start();
        }

        final class task
        {

            Thread thread = new Thread()
            {

                @Override
                public void run() {
                    String[]
                            dirs = p.split("/");
                    int
                            n = 0;
                    File
                            f;
                    p = "";
                    for(String name : dirs)
                    {
                        if(!name.isEmpty())
                        {
                            p = p+"/"+name;
                            if(n < (dirs.length - 1))
                            {
                                f = new File(p);
                                if(!f.exists()) f.mkdir();
                            }
                        }
                        n++;
                    }
                    if(b != null)
                    {
                        try
                        {
                            f = new File(p);
                            FileOutputStream fos = new FileOutputStream(f);
                            b.compress(cp,100,fos);
                            if(c != null)
                            {
                                switch (r)
                                {
                                    case RETURN_AS_FILE_TYPE:
                                        c.send(b);
                                        break;
                                    case RETURN_AS_PATH_STRING:
                                        c.send(p);
                                        break;
                                }
                            }
                            try
                            {
                                fos.flush();
                                fos.close();
                            }
                            catch (IOException e)
                            {
                                Log.e("Exception", "IOException @ save.bitmap.task.run()");
                            }
                        }
                        catch(FileNotFoundException e)
                        {
                            Log.e("Exception", "FileNotFoundException @ save.bitmap.task.run()");
                        }
                        finally
                        {
                            interrupt();
                        }
                    }
                }

            };

        }

    }

}
