package xcalibur.androidDependent.classes;

import android.graphics.Bitmap;
import android.util.LruCache;

public class Memory
{

    private static LruCache<String,Bitmap> mmry;
    private static boolean init = false;
    private static final int cacheSz = ((int) Runtime.getRuntime().maxMemory()/1024)/3;

    private static void init()
    {
        init = true;
        mmry = new LruCache<String, Bitmap>(cacheSz)
        {
            protected int sizeOf(String key, Bitmap btmp)
            {
                return btmp.getByteCount()/1024;
            }
        };
    }

    public static void add(String key,Bitmap btmp)
    {
        if(!init) init();
        if(get(key) == null) mmry.put(key,btmp);
    }

    public static Bitmap get(String key)
    {
        if(!init) init();
        return mmry.get(key);
    }

    public static void trim(int i){
        if(init && i < mmry.size() && mmry.size()>(mmry.maxSize()-i)) mmry.trimToSize(mmry.size()-i);
    }

    public static int size()
    {
        return mmry.size();
    }

}
