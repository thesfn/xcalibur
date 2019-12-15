package xcalibur.androidDependent.classes;

import android.view.View;
import android.widget.RelativeLayout;

public class XADUtilities
{
    public static void moveView(View view, float oldX, float oldY, float newX, float newY, float minX, float minY, float maxX, float maxY)
    {
        float[]
                viewxy = new float[]
                {
                        view.getX(),
                        view.getY()
                };
        int
                newx = (int)(viewxy[0] + (newX - oldX)),
                newy = (int)(viewxy[1] + (newY - oldY));
        newx = (int)(newx < minX ? minX : newx > maxX ? maxX : newx);
        newy = (int)(newy < minY ? minY : newy > maxY ? maxY : newy);
        view.setX(newx);
        view.setY(newy);
    }

    public static void resizeView(
            View view,
            float oldP0X,
            float oldP0Y,
            float oldP1X,
            float oldP1Y,
            float newP0X,
            float newP0Y,
            float newP1X,
            float newP1Y,
            int minX,
            int minY,
            int maxX,
            int maxY,
            int minWidth,
            int minHeight,
            int maxWidth,
            int maxHeight
    )
    {
        float
                oldwidth = oldP1X > oldP0X ? oldP1X - oldP0X : oldP0X - oldP1X,
                oldheight = oldP1Y > oldP0Y ? oldP1Y - oldP0Y : oldP0Y - oldP1Y,
                newwidth = newP1X > newP0X ? newP1X - newP0X : newP0X - newP1X,
                newheight = newP1Y > newP0Y ? newP1Y - newP0Y : newP0Y - newP1Y,
                deltax = newwidth - oldwidth,
                deltay = newheight - oldheight,
                viewxy[] = new float[]
                {
                        view.getX(),
                        view.getY()
                },
                viewwh[] = new float[]
                {
                        view.getWidth(),
                        view.getHeight()
                },
                viewratio = ((float)minWidth) / ((float)minHeight);
        int
                newx = (int)(viewxy[0]),
                newy = (int)(viewxy[1]),
                neww = (int)(viewwh[0]),
                newh = (int)(viewwh[1]);
        if(deltax != 0)
        {
            neww += deltax;
            neww = neww < minWidth ? minWidth : neww > maxWidth ? maxWidth : neww;
            newh = (int)(neww / viewratio);
            deltay = newh - viewwh[1];
            newx = (int)(viewxy[0] - (deltax / 2));
            newy = (int)(viewxy[1] - (deltay / 2));
        }
        else if(deltay != 0)
        {
            newh += deltay;
            newh = newh < minHeight ? minHeight : neww > maxHeight ? maxHeight : newh;
            neww = (int)(newh * viewratio);
            deltax = neww - viewwh[0];
            newx = (int)(viewxy[0] - (deltax / 2));
            newy = (int)(viewxy[1] - (deltay / 2));
        }
        newx = newx < minX ? minX : (newx > maxX ? maxX : newx);
        newy = newy < minY ? minY : (newy > maxY ? maxY : newy);
        if(newx == maxX) newy = maxY;
        if(newx == minX) newy = minY;
        if(newy == maxY) newx = maxX;
        if(newy == minY) newx = minX;
        view.setLayoutParams(new RelativeLayout.LayoutParams(neww, newh));
        view.setX(newx);
        view.setY(newy);
    }
}
