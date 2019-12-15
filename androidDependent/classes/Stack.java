package xcalibur.androidDependent.classes;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.RelativeLayout;
import java.util.ArrayList;
import java.util.List;
import xcalibur.androidDependent.interfaces.XADCallback;

public class Stack
{

    private static boolean anmt = false;

    private static int n = -1;

    private static boolean wrk = false;

    private final static List<View> stck = new ArrayList<>();

    private static List<View> temp = null;

    private static XADCallback endanicb;

    protected static void register(View view)
    {
        if(wrk)
        {
            if(temp != null)
            {
                temp.add(view);
            }
            else
            {
                temp = new ArrayList<>();
                temp.add(view);
            }
        }
        else
        {
            stck.add(view);
        }
    }

    public static void unregister(Activity activity)
    {
        n++;
        if(!wrk) cleaner(activity);
    }

    protected static void setCallbackAfterEndAnimation(XADCallback callbackAfterEndAnimation)
    {
        endanicb = callbackAfterEndAnimation;
    }

    private static void cleaner(Activity activity)
    {
        wrk = true;
        if(n > -1 && stck.size()-1 > -1)
        {
            if(!anmt)
            {
                anmt = true;
                View fl = stck.get(stck.size()-1);
                Animate ani = new Animate();
                ani.view = fl;
                ani.activity = activity;
                ani.enableAlpha = true;
                ani.alpha = (float) 0;
                ani.speed = (float).1;
                ani.multiplier = (float)5;
                ani.callback = new endAnimation(fl, activity);
                ani.execute();
                stck.remove(stck.size()-1);
                n--;
            }
        }
    }

    public static int total()
    {
        return stck.size();
    }

    private static class endAnimation implements XADCallback
    {

        View vw;
        Activity act;

        endAnimation(View view, Activity activity)
        {
            vw = view;
            act = activity;
        }

        @Override
        public void send(boolean cb)
        {
            if(endanicb != null) endanicb.send(true);
            if(vw.getParent() != null) ((RelativeLayout)vw.getParent()).removeView(vw);
            anmt = false;
            if(n > -1)
            {
                cleaner(act);
            }
            else
            {
                wrk = false;
                if(temp != null)
                {
                    stck.addAll(temp);
                    temp = null;
                }
            }
        }

        @Override
        public void send(int cb)
        { }

        @Override
        public void send(int[] cb)
        { }

        @Override
        public void send(Integer cb)
        { }

        @Override
        public void send(Integer[] cb)
        { }

        @Override
        public void send(long cb)
        { }

        @Override
        public void send(long[] cb)
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
        public void send(Bitmap cb)
        { }

        @Override
        public void send(View cb)
        { }
    }

}
