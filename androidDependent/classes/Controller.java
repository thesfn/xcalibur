package xcalibur.androidDependent.classes;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import xcalibur.androidDependent.interfaces.XADCallback;
import xcalibur.javaNative.classes.StringHandler;

public class Controller
{

    public final TextWatcher textWatcher(final EditText editText, final String regex, final XADCallback cb)
    {

        return new TextWatcher()
        {
            private int
                    n;
            private String
                    txt;
            private boolean
                    updt = true;
            private final String
                    rgx = regex;
            private final EditText
                    et = editText;

            @Override
            public void beforeTextChanged(CharSequence charS, int i, int i1, int i2)
            {
                if(updt) n = charS.toString().length() == 1 ? 1 : et.getSelectionStart();
                if(rgx != null && charS != null && !charS.toString().isEmpty() && StringHandler.find(charS,rgx))
                {
                    txt = txt != null && StringHandler.find(txt,rgx) ? null : txt;
                    if(!updt) updt = true;
                }
                else if(charS != null)
                {
                    txt = charS.toString();
                }
                //if(rgx != null && charS != null && !charS.toString().trim().isEmpty() && string.find(charS,rgx)) txt = txt != null && string.find(txt,rgx) ? null : txt; else if(charS != null)  txt = charS.toString().trim();
            }

            @Override
            public void onTextChanged(CharSequence charS, int i, int i1, int i2)
            {
                if(rgx != null && charS != null && !charS.toString().isEmpty() && StringHandler.find(charS,rgx))
                {
                    updt = false;
                    et.setText(txt);
                }
                else if(charS != null)
                {
                    if(charS.toString().length() == 0)
                    {
                        n = 0;
                    }
                    else
                    {
                        if(txt != null && charS.toString().length() > txt.length() )n += 1; else if(charS.toString().length() < txt.length()) n -= 1;
                    }
                    txt = charS.toString();
                }
                //if(rgx != null && charS != null && !charS.toString().trim().isEmpty() && string.find(charS,rgx)) et.setText(txt); else if(charS != null) txt = charS.toString().trim();
            }

            @Override
            public void afterTextChanged(Editable editable)
            {
                if(updt)
                {
                    et.setSelection(n > txt.length() || n < 0 ? 0 : n);
                    if(cb != null) cb.send(txt.trim());
                }
            }

        };
    }
}
