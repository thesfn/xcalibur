package xcalibur.androidDependent.interfaces;

import java.util.List;
import android.graphics.Bitmap;
import android.view.View;

public interface XADCallback
{
    void send(boolean cb);
    void send(int cb);
    void send(int[] cb);
    void send(Integer cb);
    void send(Integer[] cb);
    void send(long cb);
    void send(long[] cb);
    void send(Long cb);
    void send(Long[] cb);
    void send(Float cb);
    void send(Float[] cb);
    void send(String cb);
    void send(String[] cb);
    void send(List<String[]> cb);
    void send(Bitmap cb);
    void send(View cb);
}
