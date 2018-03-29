package practicaltest01var02.eim.systems.cs.pub.ro.practicaltest01var02;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by ciprian on 3/29/2018.
 */

public class PracticalTest01Var02Service extends Service {

    private ProcessingThread prTh = null;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int arg1 = intent.getIntExtra("arg1", -1);
        int arg2 = intent.getIntExtra("arg2", -1);
        Log.d("[onStartCommand]", String.valueOf(arg1));

        prTh = new ProcessingThread(getApplicationContext(), arg1, arg2);

        prTh.start();

        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        prTh.stopThread();
    }
}
