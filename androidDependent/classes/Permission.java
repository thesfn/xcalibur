package xcalibur.androidDependent.classes;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

public final class Permission
{

    public static final int PERMISSION_GRANTED = 1;
    public static String READ_PHONE_STATE = Manifest.permission.READ_PHONE_STATE;
    public static String READ_EXTERNAL_STORAGE = Manifest.permission.READ_EXTERNAL_STORAGE;
    public static String WRITE_EXTERNAL_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE;

    public static boolean isPermited(Context cntx)
    {
        return Build.VERSION.SDK_INT < 23 || (
                (
                        /*cntx.checkSelfPermission(Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED &&*/
                        cntx.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                        cntx.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                )
        );
    }

    public static boolean get(Context cntx, String permission)
    {
        return (Build.VERSION.SDK_INT < 23  || cntx.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED);
    }

    public static void modify(Activity act, String permission, int permissionState)
    {
        if(Build.VERSION.SDK_INT > 22 && (act.checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED))
        {
            act.requestPermissions(new String[]{permission},permissionState);
            act.onRequestPermissionsResult(1, new String[]{permission}, new int[]{permissionState});
        }

    }

    public static void modify(Activity act, String[] permission, int permissionState)
    {
        if(Build.VERSION.SDK_INT > 22)
        {
            act.requestPermissions(permission, permissionState);
            act.onRequestPermissionsResult(1, permission, new int[]{permissionState});
        }

    }
}
