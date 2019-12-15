package xcalibur.androidDependent.classes.view;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

public class ScalableView extends ViewGroup
{

    public ScalableView(Context context)
    {
        super(context);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b)
    {
        View
                chld  = getChildAt(0);
        chld.layout(l,t,chld.getMeasuredWidth(),chld.getMeasuredHeight());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int
                chldSt = 0;
        final
        View
                chld = getChildAt(0);
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
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}