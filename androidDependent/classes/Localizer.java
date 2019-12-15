package xcalibur.androidDependent.classes;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.util.DisplayMetrics;
import java.util.Locale;

public class Localizer
{

    public static void localize(Context cntx, String languageCode)
    {
        String[] s = languageCode.split("_");
        Locale lcl = new Locale(s[0],s[1].toUpperCase());
        Locale.setDefault(lcl);
        Resources res = cntx.getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration cnf = res.getConfiguration();
        if(Build.VERSION.SDK_INT > 23)
        {
            cnf.setLocale(lcl);
        }
        else
        {
            cnf.locale = lcl;
        }
        res.updateConfiguration(cnf,dm);
    }
}
