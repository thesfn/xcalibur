package xcalibur.androidDependent.classes;

import java.util.List;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import xcalibur.androidDependent.classes.view.HorizontalView;
import xcalibur.androidDependent.classes.view.ScalableView;
import xcalibur.androidDependent.interfaces.XADCallback;

public final class GuiPart
{

    public final static int
            SCROLL_VIEW = 0,
            LIST_VIEW = 1,
            GRID_VIEW = 2,
            FRAME_LAYOUT = 3,
            RELATIVE_LAYOUT = 4,
            TEXT_VIEW = 5,
            IMAGE_VIEW = 6,
            EDIT_TEXT = 7,
            BUTTON = 8,
            PROGRESS_BAR = 9,
            HORIZONTAL_SCROLL_VIEW = 10,
            HORIZONTAL_VIEW = 11,
            SCALABLE_VIEW = 12;
    public final static int
            FRAMELAYOUT_PARAM = 0,
            RELATIVELAYOUT_PARAM = 1,
            GRIDVIEW_PARAM = 2,
            LISTVIEW_PARAM = 3,
            SCROLLVIEW_PARAM = 4,
            HORIZONTALSCROLLVIEW_PARAM = 5,
            HORIZONTALVIEW_PARAM = 6;

    public static View create(
            Context cntx,
            int viewRequest,
            Integer paramType,
            Integer widthParameter,
            Integer heightParameter,
            Integer gravity,
            int[] margin,
            List<int[]> rule,
            boolean clickable
    )
    {
        RelativeLayout.LayoutParams rp = null;
        FrameLayout.LayoutParams fp = null;
        GridView.LayoutParams gp = null;
        ListView.LayoutParams lp = null;
        ScrollView.LayoutParams sp = null;
        HorizontalScrollView.LayoutParams hp = null;
        View vw = null;
        if(widthParameter != null && heightParameter != null)
        {
            switch (paramType)
            {
                case FRAMELAYOUT_PARAM:
                    fp = new FrameLayout.LayoutParams(widthParameter, heightParameter);
                    if(margin != null)fp.setMargins(margin[0],margin[1],margin[2],margin[3]);
                    if(gravity != null) fp.gravity = gravity;
                    break;
                case RELATIVE_PARAM:
                    rp = new RelativeLayout.LayoutParams(widthParameter, heightParameter);
                    if(margin != null)rp.setMargins(margin[0],margin[1],margin[2],margin[3]);
                    break;
                case GRIDVIEW_PARAM:
                    gp = new GridView.LayoutParams(widthParameter, heightParameter);
                    break;
                case LISTVIEW_PARAM:
                    lp = new ListView.LayoutParams(widthParameter, heightParameter);
                    break;
                case SCROLLVIEW_PARAM:
                    sp = new ScrollView.LayoutParams(widthParameter, heightParameter);
                    if(margin != null) sp.setMargins(margin[0],margin[1],margin[2],margin[3]);
                    if(gravity != null) sp.gravity = gravity;
                    break;
                case HORIZONTALSCROLLVIEW_PARAM:
                    hp = new HorizontalScrollView.LayoutParams(widthParameter,heightParameter);
                    break;
            }
        }
        if(rule != null)for(int[] i : rule)
        {
            if(rp != null)
            {
                if(i.length > 1)
                {
                    rp.addRule(i[0],i[1]);
                }
                else
                {
                    rp.addRule(i[0]);
                }
            }
        }
        switch (viewRequest)
        {
            case SCROLL_VIEW:
                vw = new ScrollView(cntx);
                break;
            case LIST_VIEW:
                vw = new ListView(cntx);
                break;
            case GRID_VIEW:
                vw = new GridView(cntx);
                break;
            case FRAME_LAYOUT:
                vw = new FrameLayout(cntx);
                break;
            case RELATIVE_LAYOUT:
                vw = new RelativeLayout(cntx);
                break;
            case TEXT_VIEW:
                vw = new TextView(cntx);
                break;
            case IMAGE_VIEW:
                vw = new ImageView(cntx);
                break;
            case EDIT_TEXT:
                vw = new EditText(cntx);
                break;
            case BUTTON:
                vw = new Button(cntx);
                break;
            case PROGRESS_BAR:
                vw = new ProgressBar(cntx);
                break;
            case HORIZONTAL_SCROLL_VIEW:
                vw = new HorizontalScrollView(cntx);
                break;
            case HORIZONTAL_VIEW:
                vw = new HorizontalView(cntx);
                break;
            case SCALABLE_VIEW:
                vw = new ScalableView(cntx);
                break;

        }
        if(vw != null)
        {
            if(fp != null)
            {
                vw.setLayoutParams(fp);
            }
            else if(rp != null)
            {
                vw.setLayoutParams(rp);
            }
            else if(gp != null)
            {
                vw.setLayoutParams(gp);
            }
            else if(lp != null)
            {
                vw.setLayoutParams(lp);
            }
            else if(sp != null)
            {
                vw.setLayoutParams(sp);
            }
            else if(hp != null)
            {
                vw.setLayoutParams(hp);
            }
            vw.setClickable(clickable);
            vw.setFocusable(clickable);
        }
        return vw;
    }

    public static class arrayAdapter extends ArrayAdapter<View>
    {

        List<View>
                vw;
        boolean
                ref;
        Activity
                act;
        int
                vwid;

        public arrayAdapter(Activity activity, int baseLayout, List<View> views, boolean refreshBitmapIfContained, int constantViewTag)
        {
            super(activity, baseLayout, views);
            vw = views;
            ref = refreshBitmapIfContained;
            act = activity;
            vwid = constantViewTag;
        }

        @Override
        public int getCount()
        {
            return super.getCount();
        }

        @Override
        public int getPosition(@Nullable View item)
        {
            return super.getPosition(item);
        }

        @Nullable
        @Override
        public View getItem(int position)
        {
            return super.getItem(position);
        }

        @Override
        public long getItemId(int position)
        {
            return super.getItemId(position);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
        {
            View view = vw.get(position);
            if(ref && (((RelativeLayout)view).getChildAt(0) instanceof ImageView))
            {
                String
                        pth = (String)view.getTag(vwid);
                Bitmap
                        btmp = Memory.get(pth);
                if(btmp == null)
                {
                    new bitmapDecode(act,pth,(ImageView)((RelativeLayout)view).getChildAt(0));
                }
                else
                {
                    ((ImageView)((RelativeLayout)view).getChildAt(0)).setImageBitmap(btmp);
                }
            }
            return view;
        }
    }

    private static class bitmapDecode implements XADCallback
    {

        String pth;
        ImageView iv;
        Activity act;

        bitmapDecode(Activity activity, String path, ImageView imageView)
        {
            pth = path;
            iv = imageView;
            act = activity;
            new BitmapHandler.decode(pth,this);
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
        public void send(final Bitmap cb)
        {
            if(cb != null)
            {
                Memory.add(pth,cb);
                act.runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        iv.setImageBitmap(cb);
                    }
                });
            }
        }

        @Override
        public void send(View cb)
        { }
    }
}
