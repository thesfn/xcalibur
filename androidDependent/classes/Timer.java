package xcalibur.androidDependent.classes;

import xcalibur.androidDependent.interfaces.XADCallback;

public class Timer
{

    private iTimer
            itmr;

    public Timer(long sleepDuration, XADCallback callback, int callbackValue)
    {
        itmr = new iTimer(sleepDuration, callback, callbackValue);
    }

    private class iTimer
    {
        int
                cbvalue;
        long
                duration;
        XADCallback
                cb;
        boolean
                timerend;
        private Thread
                worker = new Thread()
        {
            @Override
            public void run()
            {
                this.setPriority(Thread.NORM_PRIORITY);
                init();
            }
        };

        iTimer(long sleepDuration, XADCallback callback, int callbackValue)
        {
            duration = sleepDuration;
            cb = callback;
            cbvalue = callbackValue;
        }

        private void init()
        {
            try
            {
                Thread.sleep(duration);
                if(!timerend && cb != null)
                {
                    timerend = true;
                    cb.send(cbvalue);
                }
                worker.interrupt();
            }
            catch (InterruptedException e)
            {
                // do nothing
            }
        }
    }

    public void begin()
    {
        itmr.worker.start();
    }

    public void interrupt()
    {
        itmr.timerend = true;
        itmr.cb = null;
        itmr.worker.interrupt();
    }

}
