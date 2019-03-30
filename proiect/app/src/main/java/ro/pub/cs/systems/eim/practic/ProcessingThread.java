package ro.pub.cs.systems.eim.practic;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Date;
import java.util.Random;

public class ProcessingThread extends Thread {
    private double arithmeticMean;
    private double geometricMean;
    private boolean isRunning = true;
    private Context context = null;
    private Random random = new Random();

    public ProcessingThread(Context context, int firstNumber, int secondNumber) {
        this.context = context;

        arithmeticMean = (firstNumber + secondNumber) / 2;
        geometricMean = Math.sqrt(firstNumber * secondNumber);
    }

    @Override
    public void run() {
        Log.d("[ProcessingThread]", "Thread has started!");
        while (isRunning) {
            sendMessage();
            sleep();
        }
        Log.d("[ProcessingThread]", "Thread has stopped!");
    }

    private void sendMessage() {
        Intent intent = new Intent();

        String[] actions = {"1", "2", "3"};

        intent.setAction(actions[random.nextInt(actions.length)]);
        intent.putExtra("message", new Date(System.currentTimeMillis()) + " " + arithmeticMean + " " + geometricMean);
        context.sendBroadcast(intent);
    }

    private void sleep() {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
    }


    public void stopThread() {
        isRunning = false;
    }
}
