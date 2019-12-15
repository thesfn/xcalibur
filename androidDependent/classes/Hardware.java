package xcalibur.androidDependent.classes;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Vibrator;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import xcalibur.androidDependent.interfaces.XADCallback;

public class Hardware
{

    public final static class sim
    {
        public static String countryISO(Context context)
        {
            return ((TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE)).getSimCountryIso();
        }

        public static boolean onNetwork(Context context)
        {
            NetworkInfo netinf = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
            return (netinf != null && netinf.isConnected());
        }
    }

    public static final class modem
    {
        public static boolean isInternetAvailable()
        {
            // must be execute off the main thread to avoid exception
            try
            {
                InetAddress
                        inetaddress = InetAddress.getByName("www.google.com");
                return  !inetaddress.toString().equals("");
            }catch (Exception e)
            {
                return  false;
            }
        }
    }


    public final static class screen
    {

        public final static class resolution
        {

            private static Integer
                    width,
                    height;

            public static int width(Activity activity)
            {
                if(width == null)
                {
                    DisplayMetrics dm = new DisplayMetrics();
                    activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
                    width = dm.widthPixels;
                }
                return width;

            }

            public static int height(Activity activity)
            {
                if(height == null)
                {
                    DisplayMetrics dm = new DisplayMetrics();
                    activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
                    height = dm.heightPixels;
                }
                return height;
            }

            public static int[] getResolution(Activity activity)
            {
                DisplayMetrics dm = new DisplayMetrics();
                activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
                if(width == null) width = dm.widthPixels;
                if(height == null) height = dm.heightPixels;
                return new int[]{width, height};
            }

        }

    }

    public final static class vibrator
    {

        private static Vibrator v;

        public static void vibrate(Activity activity, long[] duration, int repeat)
        {
            execute(activity,duration,repeat);
        }

        public static void vibrate(Context context, long[] duration, int repeat)
        {
            execute(context,duration,repeat);
        }

        private static void execute(Context context, long[] duration, int repeat)
        {
            v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
            if(v != null) v.vibrate(duration,repeat);
        }

        protected static void cancel()
        {
            if(v != null) v.cancel();
        }

    }

    public static final class storage
    {

        public static final class read
        {

            Context cntx;
            static Uri path;
            static String[] prjt;
            static String slct;
            static String[] args;
            static String sort;
            static XADCallback clbk;

            public read(Context context, Uri uri, String[] projection, String selection, String[] selectionArguments, String sortOrder, XADCallback callback)
            {
                cntx = context;
                path = uri;
                prjt = projection;
                slct = selection;
                args = selectionArguments;
                sort = sortOrder;
                clbk = callback;
                digest.start();
            }

            Thread digest = new Thread()
            {

                @Override
                public void run() {
                    execute(cntx,path,prjt,slct,args,sort,clbk);
                }

            };

        }

        private static void execute(Context context, Uri uri, String[] projection, String selection, String[] selectionArguments, String sortOrder, XADCallback callback)
        {
            List<String[]> l = new ArrayList<>();
            Cursor c = context.getContentResolver().query(uri,projection,selection,selectionArguments,sortOrder);
            if(c != null)
            {
                while(c.moveToNext())
                {
                    String[] d = new String[c.getColumnCount()];
                    for(int i=0; i<c.getColumnCount() ;i++)
                    {
                        if(c.getType(i) != Cursor.FIELD_TYPE_STRING)
                        {
                            switch (c.getType(i)){
                                case Cursor.FIELD_TYPE_INTEGER:
                                    d[i] = String.valueOf(c.getInt(i));
                                    break;
                                case Cursor.FIELD_TYPE_FLOAT:
                                    d[i] = String.valueOf(c.getFloat(i));
                                    break;
                            }
                        }
                        else
                            {
                            d[i] = c.getString(i);
                        }
                    }
                    l.add(d);
                }
                c.close();
            }
            if(callback != null) callback.send(l);
        }

    }
}
