//Note : HorizontalView cannot have sibling or will cause bug - not severe - to be fixed

package xcalibur.androidDependent.classes.view;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.widget.HorizontalScrollView;
import xcalibur.androidDependent.interfaces.XADCallback;

public class HorizontalView extends HorizontalScrollView
{

    public static float
            LEFT_SCROLL = 1,
            RIGHT_SCROLL = -1,
            ANIMATE_LEFT_SCROLL = 2,
            ANIMATE_RIGHT_SCROLL = -2,
            xy[] = new float[]{0,0},
            x = 0;
    private long
            strt;
    private XADCallback
            cb;
    private boolean
            disableTouch = false,
            rtrn = true;

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b)
    {
        View
                chld  = getChildAt(0);
        if(chld != null) chld.layout(l,t,chld.getMeasuredWidth(),b);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        int
                chldSt = 0;
        final View
                chld = getChildAt(0);
        if(chld != null)
        {
            measureChildWithMargins(chld,widthMeasureSpec,0,heightMeasureSpec,0);
            chldSt = combineMeasuredStates(chldSt, chld.getMeasuredState());
            setMeasuredDimension(
                    resolveSizeAndState(
                            chld.getMeasuredWidth(),
                            widthMeasureSpec,
                            chldSt
                    ),
                    resolveSizeAndState(
                            chld.getMeasuredHeight(),
                            heightMeasureSpec,
                            chldSt << MEASURED_HEIGHT_STATE_SHIFT
                    )
            );
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public boolean shouldDelayChildPressedState()
    {
        return super.shouldDelayChildPressedState();
    }

    @Override
    public void fling(int velocityX)
    { }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev)
    {
        if(!disableTouch && ev.getPointerCount() == 1)
        {
            Float[]
                    xo = new Float[]{(float)0,(float)0};
            float
                    posX = ev.getX();
            switch (ev.getAction())
            {
                case MotionEvent.ACTION_DOWN:
                    xy[0] = ev.getX();
                    xy[1] = ev.getY();
                    x = posX;
                    strt = System.nanoTime();
                    break;
                case MotionEvent.ACTION_MOVE:
                    if(rtrn)
                    {
                        if(posX < x)
                        {
                            xo[0] = x - posX;
                            xo[1] = LEFT_SCROLL;
                        }
                        else if(posX > x)
                        {
                            xo[0] = posX - x;
                            xo[1] = RIGHT_SCROLL;
                        }
                        x = posX;
                        cb.send(xo);
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    if(rtrn)
                    {
                        if((System.nanoTime() - strt) < 150000000)
                        {
                            if(posX < xy[0])
                            {
                                xo[0] = (float) -1;
                                xo[1] = ANIMATE_LEFT_SCROLL;
                            }
                            else if(posX > xy[0])
                            {
                                xo[0] = (float) -1;
                                xo[1] = ANIMATE_RIGHT_SCROLL;
                            }
                        }
                        else
                        {
                            if(posX < xy[0])
                            {
                                xo[0] = xy[0] - posX;
                                xo[1] = ANIMATE_LEFT_SCROLL;
                            }
                            else if(posX > xy[0])
                            {
                                xo[0] = posX - xy[0];
                                xo[1] = ANIMATE_RIGHT_SCROLL;
                            }
                        }
                        cb.send(xo);
                    }
                    else
                    {
                        rtrn = true;
                    }
                    break;
            }
        }
        else if(!disableTouch && ev.getPointerCount() == 2)
        {
            rtrn = false;
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        performClick();
        return !disableTouch && super.onTouchEvent(event);
    }

    @Override
    public boolean performClick()
    {
        return super.performClick();
    }

    public HorizontalView(Context context)
    {
        super(context);
    }

    public void setCallBackEvent(XADCallback callback)
    {
        cb = callback;
    }

    public void disableTouch(boolean disable)
    {
        disableTouch = disable;
    }

}