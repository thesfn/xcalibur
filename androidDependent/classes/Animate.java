package xcalibur.androidDependent.classes;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import java.util.List;
import xcalibur.androidDependent.interfaces.XADCallback;

public class Animate
{

    public Activity
            activity;
    public View
            view;
    public Integer
            left,
            top,
            width,
            height,
            scrollX,
            scrollY,
            callbackResultOnComplete;
    public Float
            alpha,
            rotation;
    public List<int[]>
            relativePosition;
    public XADCallback
            callback;
    public int
            layoutcontainerid,
            repeat = 0,
            constrainHeight = 0,
            constrainWidth = 0;
    public boolean
            enableSize = false,
            enablePosition = false,
            enableAlpha = false,
            enableRotation = false,
            enableScroll = false,
            constrainSizeRelativeToParentRatio = false,
            constrainSizeRelativeToSelfRatio = false,
            constrainHeightToY = false,
            constrainWidthToX = false;
    public float
            speed = 1,
            multiplier = 1;
    private boolean
            t = false,
            end = false;
    private int
            n = 0,
            wdth,
            hght,
            scrlX,
            scrlY;
    private float
            lft,
            tp,
            rtn,
            alph,
            infiniteloopbreaker[] = new float[9];
    private threader
            threader;

    public void execute()
    {
        view.post(
            new Runnable()
            {
                @Overide
                public void run()
                {
                    n = 0;
                    if(activity != null  && view != null && multiplier > 0 && speed > 0) (threader = new threader()).animate.start();
                }
            }
        );
    }

    private boolean infiniteLoop()
    {
        boolean
                add = false;
        if(enablePosition && infiniteloopbreaker[1] == lft && infiniteloopbreaker[2] == tp) add = true;
        if(enableSize && infiniteloopbreaker[3] == wdth && infiniteloopbreaker[4] == hght) add = true;
        if(enableAlpha && infiniteloopbreaker[5] == alph) add = true;
        if(enableRotation && infiniteloopbreaker[6] == rtn) add = true;
        if(enableScroll && infiniteloopbreaker[7] == scrlX && infiniteloopbreaker[8] == scrollY) add = true;
        if(add) infiniteloopbreaker[0] += 1;
        infiniteloopbreaker[1] = lft;
        infiniteloopbreaker[2] = tp;
        infiniteloopbreaker[3] = wdth;
        infiniteloopbreaker[4] = hght;
        infiniteloopbreaker[5] = alph;
        infiniteloopbreaker[6] = rtn;
        infiniteloopbreaker[7] = scrlX;
        infiniteloopbreaker[8] = scrlY;
        return infiniteloopbreaker[0] == 6;
    }

    private void handler()
    {
        try
        {
            if(!t)
            {
                t = true;
                int rValue = 1;
                float aValue = (float)0.1;
                lft = view.getX();
                tp = view.getY();
                wdth = view.getWidth();
                hght = view.getHeight();
                scrlX = view.getScrollX();
                scrlY = view.getScrollY();
                alph = view.getAlpha();
                rtn = view.getRotation();
                lft = Math.round(
                        left == null ? lft :
                                (left > lft ? ( (lft+(rValue*multiplier)) >= left ? left : (lft+(rValue*multiplier))) :
                                        ( (lft-(rValue*multiplier)) <= left ? left : (lft-(rValue*multiplier))))
                );
                tp = Math.round(
                        top == null ? tp :
                                (top > tp ? ( (tp+(rValue*multiplier)) >= top ? top : (tp+(rValue*multiplier))) :
                                        ( (tp-(rValue*multiplier)) <= top ? top : (tp-(rValue*multiplier))))
                );
                wdth = Math.round(
                        width == null ? wdth :
                                (width > wdth ? ( (wdth+(rValue*multiplier)) >= width ? width : (wdth+(rValue*multiplier))) :
                                        ( (wdth-(rValue*multiplier)) <= width ? width : (wdth-(rValue*multiplier))))
                );
                hght = Math.round(
                        height == null ? hght :
                                (height > hght ? ( (hght+(rValue*multiplier)) >= height ? height : (hght+(rValue*multiplier))) :
                                        ( (hght-(rValue*multiplier)) <= height ? height : (hght-(rValue*multiplier))))
                );
                scrlX = Math.round(
                        scrollX == null ? scrlX :
                                (scrollX > scrlX ? ( (scrlX+(rValue*multiplier)) >= scrollX ? scrollX : (scrlX+(rValue*multiplier))) :
                                        ( (scrlX-(rValue*multiplier)) <= scrollX ? scrollX : (scrlX-(rValue*multiplier))))
                );
                scrlY = Math.round(
                        scrollY == null ? scrlY :
                                (scrollY > scrlY ? ( (scrlY+(rValue*multiplier)) >= scrollY ? scrollY : (scrlY+(rValue*multiplier))) :
                                        ( (scrlY-(rValue*multiplier)) <= scrollY ? scrollY : (scrlY-(rValue*multiplier))))
                );
                alph = alpha == null ? alph :
                        (alpha > alph ? ( (alph+(aValue*multiplier)) >= alpha ? alpha : (alph+(aValue*multiplier))) :
                                ( (alph-(aValue*multiplier)) <= alpha ? alpha : (alph-(aValue*multiplier))));
                rtn = rotation == null ? rtn :
                        (rotation == rtn ? rotation :
                                (rotation > rtn ? ((rtn + (rValue*multiplier)) > rotation ? rotation : rtn + (rValue*multiplier)) :
                                        (rtn-(rValue*multiplier) < rotation ? rotation : rtn-(rValue*multiplier))));
                if(constrainSizeRelativeToParentRatio)
                {
                    View vw = (View) view.getParent();
                    float rto = (float)vw.getWidth() / (float)vw.getHeight();
                    if(width != null)
                    {
                        wdth = left != null || left != lft ? (int)(vw.getWidth() - lft) : wdth;
                        hght = wdth == width && height != null ? height : Math.round(wdth / rto);
                        tp = (vw.getHeight() - hght);
                    }
                    else if(height != null)
                    {
                        hght = top != null || top != tp ? (int)(vw.getHeight() - tp) : hght;
                        wdth = hght == height && width != null ? width : Math.round(hght * rto);
                        left = (vw.getWidth() - wdth);
                    }
                    if(left != null && ((view.getX() < left && lft > left) || (view.getX() > left && lft < left))) lft = left;
                    if(top != null && ((view.getY() < top && tp > top) || (view.getY() > top && tp < top))) tp = top;
                }
                else if(constrainSizeRelativeToSelfRatio)
                {
                    float rto = (float)view.getWidth() / (float)view.getHeight();
                    if(width != null)
                    {
                        wdth = left != null || left != lft ? (int)(view.getWidth() - lft) : wdth;
                        hght = wdth == width && height != null ? height : Math.round(wdth / rto);
                        tp = (view.getHeight() - hght);
                    }
                    else if(height != null)
                    {
                        hght = top != null || top != tp ? (int)(view.getHeight() - tp) : hght;
                        wdth = hght == height && width != null ? width : Math.round(hght * rto);
                        left = (view.getWidth() - wdth);
                    }
                    if(left != null && ((view.getX() < left && lft > left) || (view.getX() > left && lft < left))) lft = left;
                    if(top != null && ((view.getY() < top && tp > top) || (view.getY() > top && tp < top))) tp = top;
                }
                if(height != null && constrainHeightToY && hght != height && constrainHeight > 0) hght = (int)(constrainHeight - tp);
                if(width != null && constrainWidthToX && wdth != width && constrainWidth > 0) wdth = (int)(constrainWidth - lft);
                activity.runOnUiThread(
                        new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                animator();
                            }
                        }
                );
            }
            threader.animate.sleep((long)(speed*1000));
            if(!infiniteLoop())
            {
                handler();
            }
            else
            {
                if(enableSize)
                {
                    if(width != null) wdth = width;
                    if(height != null) hght = height;
                }
                if(enablePosition)
                {
                    if(left != null) lft = left;
                    if(top != null) tp = top;
                }
                if(enableAlpha)
                {
                    if(alpha != null)alph = alpha;
                }
                if(enableRotation)
                {
                    if(rotation != null) rtn = rotation;
                }
                if(enableScroll)
                {
                    if(scrollX != null) scrlX = scrollX;
                    if(scrollY != null) scrlY = scrollY;
                }
                activity.runOnUiThread(
                        new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                animator();
                            }
                        }
                );
            }
        }
        catch (InterruptedException e)
        {
           Log.e("InterruptedException","InterruptedException @ animate.handler");*/
        }
    }

    private final class threader
    {
        private final Thread animate = new Thread()
        {
            @Override
            public void run()
            {
                setPriority(MAX_PRIORITY);
                handler();
            }
        };
    }

    private void animator()
    {
        if(enableSize)
        {
            if(view.getParent() instanceof RelativeLayout)
            {
                RelativeLayout.LayoutParams vgp = new RelativeLayout.LayoutParams(wdth,hght);
                if(relativePosition != null) for(int[] pos : relativePosition) if(pos.length > 1) vgp.addRule(pos[0],pos[1]); else vgp.addRule(pos[0]);
                view.setLayoutParams(vgp);
            }
            else if(view.getParent() instanceof FrameLayout)
            {
                FrameLayout.LayoutParams vgp = new FrameLayout.LayoutParams(wdth,hght);
                view.setLayoutParams(vgp);
            }
            else if(view .getParent() instanceof ListView)
            {
                ListView.LayoutParams vgp = new ListView.LayoutParams(wdth,hght);
                view.setLayoutParams(vgp);
            }
        }
        if(enablePosition)
        {
            view.setX(lft);
            view.setY(tp);
        }
        if(enableAlpha)view.setAlpha(alph);
        if(enableRotation)view.setRotation(rtn);
        if(enableScroll) view.scrollTo(scrlX,scrlY);
        end = (left == null || left == lft) &&
                (top == null || top == tp) &&
                (width == null || width == wdth) &&
                (height == null || height == hght) &&
                (alpha == null || alpha == alph) &&
                (rotation == null || rotation == rtn) &&
                (scrollX == null || scrollX == scrlX) &&
                (scrollY == null || scrollY == scrlY);
        if(end)
        {
            view.setRotation(0);
            if(repeat > -1)
            {
                if(n == repeat || repeat == 0)
                {
                    if(callback != null)
                    {
                        view.post(new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                if(callbackResultOnComplete != null) callback.send(callbackResultOnComplete); else callback.send(end);
                            }
                        });

                    }
                }
                else
                {
                    (threader = new threader()).animate.start();
                    end = false;
                }
            }
            else
            {
                isOnView(view);
                end = false;
            }
            n++;
            threader.animate.interrupt();
        }
        t = false;
    }

    private void isOnView(View view)
    {

        boolean pass = false;
        View vw = (View)view.getParent();
        if(vw != null && vw.getId() != layoutcontainerid)
        {
            isOnView(vw);
        }
        else if(vw != null && vw.getId() == layoutcontainerid)
        {
            pass = true;
        }
        if(pass)new looper().send(true);

    }

    private class looper implements XADCallback
    {

        @Override
        public void send(boolean cb)
        {
            (threader = new threader()).animate.start();
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
