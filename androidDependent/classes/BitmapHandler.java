package xcalibur.androidDependent.classes;

import java.io.IOException;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.media.ExifInterface;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.View;

import xcalibur.androidDependent.interfaces.XADCallback;

public final class BitmapHandler
{

    public static class decode
    {

        String str;
        XADCallback cb;

        public decode(String uri, XADCallback callback)
        {
            str = uri;
            cb = callback;
            (new Thread()
            {
                @Override
                public void run()
                {
                    Bitmap
                            btmp = null;
                    if(new File(str).exists())
                    {
                        btmp = BitmapFactory.decodeFile(str);
                        btmp = rotate(btmp, orientation(str));
                    }
                    cb.send(btmp);
                    interrupt();
                }
            }).start();
        }
    }

    private static int insampleSizeCalculator(BitmapFactory.Options opt, int wdth, int hght)
    {
        final float btmpWdth = opt.outWidth;
        final float btmpHght = opt.outHeight;
        final float hSzW = btmpWdth/wdth;
        final float hSzH = btmpHght/hght;
        int dfltInSmpleSz = 1;
        if(btmpWdth>wdth || btmpHght>hght) dfltInSmpleSz = hSzW>hSzH ? Math.round(btmpWdth/wdth) : Math.round(btmpHght/hght);
        return dfltInSmpleSz;
    }

    public static Bitmap bitmapInsamplerSize(String uri, int wdth, int hgth,int ornttn)
    {
        final BitmapFactory.Options btmpFctrOpt = new BitmapFactory.Options();
        btmpFctrOpt.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(uri,btmpFctrOpt);
        btmpFctrOpt.inSampleSize = insampleSizeCalculator(btmpFctrOpt,wdth,hgth);
        btmpFctrOpt.inJustDecodeBounds = false;
        return rotate(BitmapFactory.decodeFile(uri,btmpFctrOpt),ornttn);
    }

    public static Bitmap rotate(Bitmap bitmap, int orientation)
    {
        Matrix mtrx = new Matrix();
        if(orientation != 0){
            mtrx.setRotate(orientation);
            bitmap = Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),mtrx,true);
        }
        return bitmap;
    }

    public static int orientation(String uri)
    {
        int n;
        try
        {
            n = (new ExifInterface((new File(uri)).getPath())).getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
        }
        catch (IOException e)
        {
            n = 0;
        }
        switch(n)
        {
            case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
                n = -90;
                break;
            case ExifInterface.ORIENTATION_FLIP_VERTICAL:
                n = -180;
                break;
            case ExifInterface.ORIENTATION_NORMAL:
                n = 0;
                break;
            case ExifInterface.ORIENTATION_ROTATE_90:
                n = 90;
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                n = 180;
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                n = 270;
                break;
            case ExifInterface.ORIENTATION_TRANSPOSE:
                n = 45;
                break;
            case ExifInterface.ORIENTATION_TRANSVERSE:
                n = -45;
                break;
            case ExifInterface.ORIENTATION_UNDEFINED:
                n = 0;
                break;
        }
        return n;
    }

    public static Bitmap rounded(Activity activity, Bitmap bitmap, int width, int height)
    {
        Bitmap b = Bitmap.createBitmap(width,height,Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        RoundedBitmapDrawable d = RoundedBitmapDrawableFactory.create(activity.getResources(),bitmap);
        d.setBounds(0,0,width,height);
        d.setCornerRadius((float) width);
        d.setAntiAlias(true);
        d.draw(c);
        return b;
    }

    public static Bitmap recreate(Bitmap bitmap, int width, int height, int orientation, boolean centerCrop)
    {
        int wh = bitmap.getWidth();
        int[] xy = new int[]{0,0};
        Matrix mtrx = new Matrix();
        mtrx.setRotate(orientation);
        if(centerCrop && bitmap.getWidth() != bitmap.getHeight())
        {
            if(bitmap.getWidth() > bitmap.getHeight())
            {
                wh = bitmap.getHeight();
                xy[0] = (bitmap.getWidth() - wh)/2;
            }
            else
            {
                xy[1] = (bitmap.getHeight() - wh)/2;
            }
        }
        bitmap = Bitmap.createBitmap(bitmap,xy[0],xy[1],wh,wh,mtrx,true);
        bitmap = Bitmap.createScaledBitmap(bitmap,width,height,false);
        return bitmap;
    }

    public final static class recreate
    {
        int
                x = 0,
                y = 0,
                srcwdth,
                srchght,
                wdth,
                hght,
                ornt,
                dvcrsltnwdth,
                dvcrsltnhght;
        float
                bndngrto;
        boolean
                smrtxy,
                lmt,
                cntr,
                rnd;
        Bitmap
                btmp;
        String
                path;
        Activity
                act;
        XADCallback
                cb;

        public recreate(Bitmap bitmap, int width, int height, int orientation, boolean centerCrop, XADCallback callback)
        {
            btmp = bitmap;
            wdth = width;
            hght = height;
            ornt = orientation;
            cntr = centerCrop;
            cb = callback;
            thrd.start();
        }

        public recreate(Bitmap bitmap, boolean limitSizeToDevice, boolean centerCrop, int deviceResolutionWidth, int deviceResolutionHeight, XADCallback callback)
        {
            btmp = bitmap;
            lmt = limitSizeToDevice;
            cntr = centerCrop;
            dvcrsltnwdth = deviceResolutionWidth;
            dvcrsltnhght = deviceResolutionHeight;
            cb = callback;
            thrd.start();
        }

        public recreate(String uri, int width, int height, int orientation, boolean centerCrop, XADCallback callback)
        {
            path = uri;
            wdth = width;
            hght = height;
            ornt = orientation;
            cntr = centerCrop;
            cb = callback;
            thrd.start();
        }

        public recreate(String uri, boolean limitSizeToDevice, boolean centerCrop, int deviceResolutionWidth, int deviceResolutionHeight, XADCallback callback)
        {
            path = uri;
            lmt = limitSizeToDevice;
            ornt = orientation(uri);
            cntr = centerCrop;
            dvcrsltnwdth = deviceResolutionWidth;
            dvcrsltnhght = deviceResolutionHeight;
            cb = callback;
            thrd.start();
        }

        public recreate(
                String uri,
                int srcX,
                int srcY,
                int srcWidth,
                int srcHeight,
                int width,
                int height,
                boolean centerCrop,
                boolean rounded,
                Activity activity,
                XADCallback callback
        )
        {
            x = srcX;
            y = srcY;
            path = uri;
            srcwdth = srcWidth;
            srchght = srcHeight;
            wdth = width;
            hght = height;
            ornt = orientation(uri);
            cntr = centerCrop;
            rnd = rounded;
            act = activity;
            cb = callback;
            thrd.start();
        }

        public recreate(
                String uri,
                float ratio,
                boolean limitToResolution,
                int maxWidth,
                int maxHeight,
                boolean smartXY,
                boolean centerCrop,
                boolean rounded,
                Activity activity,
                XADCallback callback
        )
        {
            path = uri;
            bndngrto = ratio;
            lmt = limitToResolution;
            dvcrsltnwdth = maxWidth;
            dvcrsltnhght = maxHeight;
            smrtxy = smartXY;
            cntr = centerCrop;
            rnd = rounded;
            act = activity;
            cb = callback;
            thrd.start();
        }

        private void doMath()
        {
            if(btmp == null)
            {
                File
                        fl = new File(path);
                if(fl.exists() && fl.isFile())
                {
                    btmp = BitmapFactory.decodeFile(path);
                    btmp = rotate(btmp, ornt)
                }
            }
            if(btmp != null)
            {
                boolean
                        scle = true;
                if(bndngrto > 0)
                {
                    float
                            rto = ((float)btmp.getWidth()) / ((float)btmp.getHeight());
                    srcwdth = btmp.getWidth();
                    srchght = (int)(srcwdth / bndngrto);
                    if(srchght > btmp.getHeight())
                    {
                        srchght = btmp.getHeight();
                        srcwdth = (int)(srchght * bndngrto);
                    }
                    if(smrtxy)
                    {
                        if(bndngrto == rto)
                        {
                            x = 0;
                            y = 0;
                        }
                        else if(bndngrto < rto)
                        {
                            bndngrto = ((float) 1.0) - (bndngrto / rto);
                            x = (int)(srcwdth * bndngrto);
                            y = 0;
                        }
                        else if(bndngrto > rto)
                        {
                            bndngrto = ((float) 1.0) - (rto / bndngrto);
                            x = 0;
                            y = (int)(srchght * bndngrto);
                        }
                    }
                }
                if(srcwdth > 0 && srchght > 0) btmp = Bitmap.createBitmap(btmp, x, y, srcwdth, srchght, null, false);
                if(cntr && btmp.getWidth() != btmp.getHeight())
                {
                    int
                            cwh = btmp.getWidth();
                    float
                            cx = btmp.getWidth() / ((float)2),
                            cy = btmp.getHeight() / ((float)2);
                    if(btmp.getWidth() > btmp.getHeight())
                    {
                        cwh = btmp.getHeight();
                        x = (int)(cx - (cwh / ((float)2)));
                        y = (int)(cy - (cwh / ((float)2)));
                    }
                    else if(btmp.getWidth() < btmp.getHeight())
                    {
                        cwh = btmp.getWidth();
                        x = (int)(cx - (cwh / ((float)2)));
                        y = (int)(cy - (cwh / ((float)2)));
                    }
                    btmp = Bitmap.createBitmap(btmp, x, y, cwh, cwh, null, true);
                }
                if(lmt)
                {
                    wdth = btmp.getWidth();
                    hght = btmp.getHeight();
                    if(wdth >= dvcrsltnwdth || hght >= dvcrsltnhght)
                    {
                        float
                                rto = ((float)(wdth)) / ((float)hght);
                        if(dvcrsltnwdth > dvcrsltnhght)
                        {
                            hght = dvcrsltnhght;
                            wdth = (int)(hght * rto);
                            if(wdth > dvcrsltnwdth)
                            {
                                wdth = dvcrsltnwdth;
                                hght = (int)(wdth / rto);
                            }
                        }
                        else
                        {
                            wdth = dvcrsltnwdth;
                            hght = (int)(wdth / rto);
                            if(hght > dvcrsltnhght)
                            {
                                hght = dvcrsltnhght;
                                wdth = (int)(hght * rto);
                            }
                        }
                    }
                    else
                    {
                        scle = false;
                    }
                }
                if(scle) btmp = Bitmap.createScaledBitmap(btmp,wdth,hght,false);
                if(rnd) btmp = rounded(act, btmp, btmp.getWidth(), btmp.getHeight());
            }
            cb.send(btmp);
            thrd.interrupt();
            btmp = null;
            thrd = null;
        }

        Thread thrd = new Thread()
        {
            @Override
            public void run()
            {
                doMath();
            }
        };
    }

    public static class cascadingDecoder implements XADCallback
    {
        private boolean
                working;
        private int
                n;
        private List<String>
                order;
        private XADCallback
                cllr;
        private Thread
                worker = new Thread()
        {
            @Override
            public void run()
            {
                super.run();
            }
        };

        public cascadingDecoder(XADCallback caller)
        {
            cllr = caller;
            working = false;
            n = -1;
            order = new ArrayList<>();
            worker.start();
        }

        public void add(String uri)
        {
            order.add(uri);
            if(!working) next();
        }

        private void next()
        {
            if((n += 1) < order.size())
            {
                working = true;
                new decode(order.get(n), this);
            }
            else
            {
                clean();
            }
        }

        private void clean()
        {
            order.clear();
            n = -1;
            working = false;
        }

        @Override
        public void send(boolean cb)
        { }

        @Override
        public void send(int cb)
        { }

        @Override
        public void send(int[] cb)
        { }

        @Override
        public void send(long cb)
        { }

        @Override
        public void send(long[] cb)
        { }

        @Override
        public void send(Integer cb)
        { }

        @Override
        public void send(Integer[] cb)
        { }

        @Override
        public void send(Long cb)
        { }

        @Override
        public void send(Long[] cb)
        { }

        @Override
        public void send(Float cb)
        { }

        @Override
        public void send(Float[] cb)
        { }

        @Override
        public void send(String cb)
        { }

        @Override
        public void send(String[] cb)
        { }

        @Override
        public void send(List<String[]> cb)
        { }

        @Override
        public void send(View cb)
        { }

        @Override
        public void send(Bitmap cb)
        {
            cllr.send(cb);
            next();
        }
    }

    public static class cascadingRecreate implements XADCallback
    {
        private boolean
                working;
        private int
                n;
        private List<recreateData>
                order;
        private Thread
                worker = new Thread()
        {
            @Override
            public void run()
            {
                super.run();
            }
        };

        public cascadingRecreate()
        {
            working = false;
            n = -1;
            order = new ArrayList<>();
            worker.start();
        }

        public void add(String uri, int width, int height, int orientation, boolean centerCrop, XADCallback caller)
        {
            order.add(
                    new recreateData(
                            uri,
                            width,
                            height,
                            orientation,
                            centerCrop,
                            caller
                    )
            );
            if(!working) next();
        }

        public void add(
                String uri,
                float ratio,
                boolean limitToResolution,
                int width,
                int height,
                boolean smartXY,
                boolean centerCrop,
                boolean rounded,
                Activity activity,
                XADCallback caller
        )
        {
            order.add(
                    new recreateData(
                            uri,
                            ratio,
                            limitToResolution,
                            width,
                            height,
                            smartXY,
                            centerCrop,
                            rounded,
                            activity,
                            caller
                    )
            );
            if(!working) next();
        }

        private void next()
        {
            if((n += 1) < order.size())
            {
                working = true;
                recreateData
                        dt = order.get(n);
                if(order.get(n).act == null)
                {
                    new recreate(
                            dt.uri,
                            dt.width,
                            dt.height,
                            dt.orientation,
                            dt.centercrop,
                            this
                    );
                }
                else
                {
                    new recreate(
                            dt.uri,
                            dt.rto,
                            dt.limittoresolution,
                            dt.width,
                            dt.height,
                            dt.smartxy,
                            dt.centercrop,
                            dt.rounded,
                            dt.act,
                            this
                    );
                }
            }
            else
            {
                clean();
            }
        }

        private void clean()
        {
            order.clear();
            n = -1;
            working = false;
        }

        private class recreateData
        {
            Activity
                    act;
            String
                    uri;
            int
                    width,
                    height,
                    orientation;
            float
                    rto;
            boolean
                    limittoresolution,
                    centercrop,
                    smartxy,
                    rounded;
            XADCallback
                    caller;

            recreateData(String bitmapUri, int recreateWidth, int recreateHeight, int recreateOrientation, boolean centerCropOnRecreate, XADCallback recreateCallback)
            {
                uri = bitmapUri;
                width = recreateWidth;
                height = recreateHeight;
                orientation = recreateOrientation;
                centercrop = centerCropOnRecreate;
                caller = recreateCallback;
            }

            recreateData(
                    String bitmapUri,
                    float ratio,
                    boolean limitToResolution,
                    int recreateWidth,
                    int recreateHeight,
                    boolean smartXY,
                    boolean centerCropOnRecreate,
                    boolean roundedOnRecreate,
                    Activity activity,
                    XADCallback recreateCallback
            )
            {
                uri = bitmapUri;
                rto = ratio;
                limittoresolution = limitToResolution;
                width = recreateWidth;
                height = recreateHeight;
                smartxy = smartXY;
                centercrop = centerCropOnRecreate;
                rounded = roundedOnRecreate;
                act = activity;
                caller = recreateCallback;
            }
        }

        @Override
        public void send(boolean cb)
        { }

        @Override
        public void send(int cb)
        { }

        @Override
        public void send(int[] cb)
        { }

        @Override
        public void send(long cb)
        { }

        @Override
        public void send(long[] cb)
        { }

        @Override
        public void send(Integer cb)
        { }

        @Override
        public void send(Integer[] cb)
        { }

        @Override
        public void send(Long cb)
        { }

        @Override
        public void send(Long[] cb)
        { }

        @Override
        public void send(Float cb)
        { }

        @Override
        public void send(Float[] cb)
        { }

        @Override
        public void send(String cb)
        { }

        @Override
        public void send(String[] cb)
        { }

        @Override
        public void send(List<String[]> cb)
        { }

        @Override
        public void send(View cb)
        { }

        @Override
        public void send(Bitmap cb)
        {
            order.get(n).caller.send(cb);
            next();
        }
    }
}