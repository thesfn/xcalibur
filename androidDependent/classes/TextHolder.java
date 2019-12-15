package xcalibur.androidDependent.classes;

import android.content.Context;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

import xcalibur.javaNative.classes.StringHandler;

public final class TextHolder
{

    private static List<TextView>
            txtVw = new ArrayList<>();
    private static List<EditText>
            edtTxt = new ArrayList<>();
    private static List<Button>
            bttn = new ArrayList<>();

    public static class textView
    {
        public TextView create(
                Context c,
                int width,
                int height,
                int[] margin,
                List<int[]> rule,
                boolean clickable,
                Integer stringResource,
                int constantViewTagIdentifier,
                String identifier,
                int constantViewTagTextSizeMultiplier,
                int textSize,
                float textSizeMultiplier,
                int constantViewTagLanguage,
                boolean isNew
        )
        {
            TextView
                    t = null;
            List<TextView>
                    tmp = new ArrayList<>(txtVw);
            for(TextView tv : tmp)
            {
                if(tv.getTag(constantViewTagIdentifier).equals(identifier))
                {
                    if(!isNew)t = tv; else txtVw.remove(tv);
                    break;
                }
            }
            if(t == null)
            {
                t = (TextView) GuiPart.create(c, GuiPart.TEXT_VIEW, GuiPart.RELATIVE_PARAM,width,height,null,margin,rule,clickable);
                t.setTag(constantViewTagIdentifier,identifier);
                txtVw.add(t);
            }
            if(t.getParent() != null)((RelativeLayout) t.getParent()).removeView(t);
            t.setTag(constantViewTagLanguage,stringResource);
            t.setTag(constantViewTagTextSizeMultiplier, textSizeMultiplier);
            t.setTextSize(textSize * textSizeMultiplier);
            t.setText(stringResource);
            tmp = null;
            return t;
        }

        public TextView create(
                Context c,
                int width,
                int height,
                int[] margin,
                List<int[]> rule,
                boolean clickable,
                String text,
                int constantViewTagIdentifier,
                String identifier,
                int constantViewTagTextSizeMultiplier,
                int textSize,
                float textSizeMultiplier,
                int constantViewTagLanguage,
                boolean isNew
        )
        {
            TextView
                    t = null;
            List<TextView>
                    tmp = new ArrayList<>(txtVw);
            for(TextView tv : tmp)
            {
                if(tv.getTag(constantViewTagIdentifier).equals(identifier))
                {
                    if(!isNew)t = tv; txtVw.remove(tv);
                    break;
                }
            }
            if(t == null)
            {
                t = (TextView) GuiPart.create(c, GuiPart.TEXT_VIEW, GuiPart.RELATIVE_PARAM,width,height,null,margin,rule,clickable);
                t.setTag(constantViewTagIdentifier,identifier);
                txtVw.add(t);
            }
            if(t.getParent() != null)
            {
                if(t.getParent() instanceof RelativeLayout)
                {
                    ((RelativeLayout) t.getParent()).removeView(t);
                }
                else if(t.getParent() instanceof FrameLayout)
                {
                    ((FrameLayout) t.getParent()).removeView(t);
                }
                else if(t.getParent() instanceof ScrollView)
                {
                    ((ScrollView) t.getParent()).removeView(t);
                }
                else if(t.getParent() instanceof ListView)
                {
                    ((ListView) t.getParent()).removeView(t);
                }
            }
            t.setTag(constantViewTagTextSizeMultiplier, textSizeMultiplier);
            t.setTag(constantViewTagLanguage,text);
            t.setTextSize(textSize * textSizeMultiplier);
            t.setText(text);
            tmp = null;
            return t;
        }

        public TextView create(
                Context c,
                int width,
                int height,
                int[] margin,
                List<int[]> rule,
                boolean clickable,
                Integer stringResource,
                int constantViewTagIdentifier,
                String identifier,
                int constantViewTagTextSizeMultiplier,
                int textSize,
                float textSizeMultiplier,
                int constantViewTagLanguage,
                boolean isNew,
                int constantViewTagUpperCasePosititon,
                int uppercasePosition
        )
        {
            TextView
                    t = null;
            List<TextView>
                    tmp = new ArrayList<>(txtVw);
            for(TextView tv : tmp)
            {
                if(tv.getTag(constantViewTagIdentifier).equals(identifier))
                {
                    if(!isNew)t = tv; else txtVw.remove(tv);
                    break;
                }
            }
            if(t == null)
            {
                t = (TextView) GuiPart.create(c, GuiPart.TEXT_VIEW, GuiPart.RELATIVE_PARAM,width,height,null,margin,rule,clickable);
                t.setTag(constantViewTagIdentifier,identifier);
                txtVw.add(t);
            }
            if(t.getParent() != null)((RelativeLayout) t.getParent()).removeView(t);
            t.setTag(constantViewTagLanguage,stringResource);
            t.setTag(constantViewTagTextSizeMultiplier, textSizeMultiplier);
            t.setTag(constantViewTagUpperCasePosititon, uppercasePosition);
            t.setTextSize(textSize * textSizeMultiplier);
            t.setText(StringHandler.uppercase(c.getResources().getString(stringResource),uppercasePosition));
            tmp = null;
            return t;
        }
    }

    public static class editText
    {
        public EditText create(
                Context c,
                int width,
                int height,
                int[] margin,
                List<int[]> rule,
                boolean clickable,
                Integer stringResource,
                int constantViewTagIdentifier,
                String identifier,
                int constantViewTagTextSizeMultiplier,
                int textSize,
                float textSizeMultiplier,
                int constantViewTagLanguage,
                boolean isNew
        )
        {
            EditText
                    e = null;
            List<EditText>
                    tmp = new ArrayList<>(edtTxt);
            for(EditText et : tmp)
            {
                if(et.getTag(constantViewTagIdentifier).equals(identifier))
                {
                    if(!isNew) e = et; else edtTxt.remove(et);
                    break;
                }
            }
            if(e == null)
            {
                e = (EditText) GuiPart.create(c, GuiPart.EDIT_TEXT, GuiPart.RELATIVE_PARAM,width,height,null,margin,rule,clickable);
                e.setTag(constantViewTagIdentifier,identifier);
                edtTxt.add(e);
            }
            if(e.getParent() != null)((RelativeLayout) e.getParent()).removeView(e);
            e.setTag(constantViewTagTextSizeMultiplier, textSizeMultiplier);
            e.setTag(constantViewTagLanguage, stringResource);
            e.setTextSize(textSize * textSizeMultiplier);
            e.setText(stringResource);
            return e;
        }

        public EditText create(
                Context c,
                int width,
                int height,
                int[] margin,
                List<int[]> rule,
                boolean clickable,
                String stringResource,
                int constantViewTagIdentifier,
                String identifier,
                int constantViewTagTextSizeMultiplier,
                int textSize,
                float textSizeMultiplier,
                int constantViewTagLanguage,
                boolean isNew
        )
        {
            EditText
                    e = null;
            List<EditText>
                    tmp = new ArrayList<>(edtTxt);
            for(EditText et : tmp)
            {
                if(et.getTag(constantViewTagIdentifier).equals(identifier))
                {
                    if(!isNew) e = et; else edtTxt.remove(et);
                    break;
                }
            }
            if(e == null)
            {
                e = (EditText) GuiPart.create(c, GuiPart.EDIT_TEXT, GuiPart.RELATIVE_PARAM,width,height,null,margin,rule,clickable);
                e.setTag(constantViewTagIdentifier,identifier);
                edtTxt.add(e);
            }
            if(e.getParent() != null)((RelativeLayout) e.getParent()).removeView(e);
            e.setTag(constantViewTagTextSizeMultiplier, textSizeMultiplier);
            e.setTag(constantViewTagLanguage, stringResource);
            e.setTextSize(textSize * textSizeMultiplier);
            e.setText(stringResource == null || stringResource.isEmpty() ? "" : stringResource);
            return e;
        }
    }

    public static Button button(
            Context c,
            int width,
            int height,
            int[] margin,
            List<int[]> rule,
            boolean clickable,
            Integer stringResource,
            int constantViewTagIdentifier,
            String identifier,
            int constantViewTagTextSizeMultiplier,
            int textSize,
            float textSizeMultiplier,
            int constantViewTagLanguage
    )
    {
        Button b = null;
        for(Button bt : bttn)
        {
            if(bt.getTag(constantViewTagIdentifier).equals(identifier))
            {
                b = bt;
                break;
            }
        }
        if(b == null)
        {
            b = (Button) GuiPart.create(c, GuiPart.BUTTON, GuiPart.RELATIVE_PARAM,width,height,null,margin,rule,clickable);
            b.setTag(constantViewTagIdentifier, identifier);
            bttn.add(b);
        }
        if(b.getParent() != null)((RelativeLayout) b.getParent()).removeView(b);
        b.setTag(constantViewTagTextSizeMultiplier, textSizeMultiplier);
        b.setTag(constantViewTagLanguage,stringResource);
        b.setTextSize(textSize * textSizeMultiplier);
        if(stringResource != null) b.setText(stringResource);
        return b;
    }

    public final static class get
    {

        static List<TextView> textViews()
        {
            return txtVw;
        }

        static List<EditText> editTexts()
        {
            return edtTxt;
        }

        static List<Button> buttons()
        {
            return bttn;
        }

    }

    public final static class update
    {

        static void textViews(TextView textView)
        {
            txtVw.add(textView);
        }

        static void editTexts(EditText editText)
        {
            edtTxt.add(editText);
        }

        static void buttons(Button button)
        {
            bttn.add(button);
        }

    }

    public static void refresh(Context context, int constantViewTag, int constantViewTagUppercasePosition)
    {
        for(TextView vw : txtVw)
        {
            if(vw.getTag(constantViewTag) instanceof Integer)
            {
                if(vw.getTag(constantViewTagUppercasePosition) != null)
                {
                    vw.setText(StringHandler.uppercase(context.getResources().getString((int)vw.getTag(constantViewTag)), (int)vw.getTag(constantViewTagUppercasePosition)));
                }
                else
                {
                    vw.setText((int)vw.getTag(constantViewTag));
                }
            }
            else if((vw.getTag(constantViewTag) instanceof  String))
            {
                vw.setText((String) vw.getTag(constantViewTag));
            }
        }
        for(EditText vw : edtTxt)
        {
            if(vw.getTag(constantViewTag) instanceof Integer) vw.setText((int)vw.getTag(constantViewTag));
            else if((vw.getTag(constantViewTag) instanceof  String)) vw.setText((String) vw.getTag(constantViewTag));
        }
        for(Button vw : bttn)
        {
            if(vw.getTag(constantViewTag) instanceof Integer) vw.setText((int)vw.getTag(constantViewTag));
            else if((vw.getTag(constantViewTag) instanceof  String)) vw.setText((String) vw.getTag(constantViewTag));
        }
    }

}
