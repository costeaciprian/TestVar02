package practicaltest01var02.eim.systems.cs.pub.ro.practicaltest01var02;

import android.content.Context;
import android.content.Intent;

/**
 * Created by ciprian on 3/29/2018.
 */

public class ProcessingThread extends Thread {

    private Context context;
    private int arg1, arg2;
    private boolean isRunning = true;
    private int cnt = 0;

    public ProcessingThread(Context context, int arg1, int arg2) {
        this.context = context;
        this.arg1 = arg1;
        this.arg2 = arg2;
    }

    @Override
    public void run() {
        super.run();

        while(isRunning) {
            sendMessage();
            sleep();
        }
    }

    private void sleep() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException interr) {
            interr.printStackTrace();
        }
    }

    public void sendMessage() {
        Intent intent = new Intent();
        intent.setAction(Constants.actions[cnt % 2]);
        String message;
        int res;
        if(cnt % 2 == 0) {
            // make sum
            res = arg1 + arg2;
            message = "The sum is: ";
        }
        else {
            // make difference
            res = arg1 - arg2;
            message = "The difference is: ";
        }
        message += String.valueOf(res);
        intent.putExtra("msg", message);
        cnt++;
        context.sendBroadcast(intent);
    }

    public void stopThread() {
        isRunning = false;
    }
}
