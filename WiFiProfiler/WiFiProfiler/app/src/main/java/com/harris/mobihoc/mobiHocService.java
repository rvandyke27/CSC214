package com.harris.mobihoc;
//java imports
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
//android imports
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
//service to randomly start a thread which will run the computation
//
//should have a bool to control whether the computation is randomly chosen or does all sequentially.
//
//need to have thread scan for available wifi network and wifi direct groups 
public class mobiHocService extends Service{
    private mobiHoc activity;
    private IBinder localBinder = new LocalBinder();
    private boolean running = false;
    private int maxValue = 1000000;
    private Thread trial;
    public boolean hasWifi = false;
    private boolean randomTrial = false;
    @Override
    public IBinder onBind(Intent intent){
        return localBinder;
    }
    @Override
    public boolean onUnbind(Intent intent){
        activity = null;
        return false;
    }
    public void turnOn(){
        activity.P2P.restart();
        activity.P2P.registerService();
        Thread go = new Thread(new trialThread());
        go.setPriority(Thread.MAX_PRIORITY);
        go.start();
    }
    class trialThread implements Runnable {
        @Override
        public void run(){
            running = true; 
            int sleepyTime = 0;
            int trialNumber = 1;
            int testNumber = 1;
            
            while(running){
                try{
                    sleepyTime = getRandomTime();//get a random time(ms) to sleep for
                    testNumber = sleepyTime%13;
                    activity.getWifiNetworks(true);
                    //wd service
                    activity.P2P.findTDP();
                    //get active network information
                    //available comm interface 
                    activity.recordActiveNetworks();
                    //all wd peers
                    activity.P2P.Discover();
                    //activity.P2P.findServices();

                    //do all of them at once
/*                    if(activity.isWifiAvailable()){
                        if(hasActiveInternetConnection(activity, "euca.ee.rochester.edu")){ //check EUCA
                            //trial = new Thread(new mobiOffLoader(trialNumber++, activity.wm, randomTrial, testNumber, "euca.ee.rochester.edu", 51749));
                            trial = new Thread(new mobiOffLoader(trialNumber++, activity.wm, randomTrial, testNumber, "babbage.ee.rochester.edu", 8080, activity));
                            trial.start();
                        }
                        if(hasActiveInternetConnection(activity, "www.google.com")){//check aws
                            trial = new Thread(new mobiOffLoader(trialNumber++, activity.wm, randomTrial, testNumber, "52.26.119.61", 8080, activity));
                            trial.start();
                        }
                    }
                    if(running){//do on device
                        //activity.P2P.findServices();
                        trial = new Thread(new mobiWorkerThread(trialNumber++, activity.wm, randomTrial, testNumber, activity));
                        trial.start();
                    }
                    */

                    System.out.println("Sleeping for " + sleepyTime);
                    Thread.sleep((long)5*60000);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    private boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivity == null) {
            return false;
        } else {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    private boolean hasActiveInternetConnection(Context context, String host) {
        if (isNetworkAvailable(context)) {
            try {
                //super hack with sockets
                /*
                Socket socket = new Socket(InetAddress.getByName(host), 8080);
                socket.close();
                Log.d("COLIN", "CONNECTED TO " + host);
                return true;
                */
                //checks via ping, works for both, but cang ping aws need google.com
                String command = "ping -c 1 " + host;
                Process p1 = java.lang.Runtime.getRuntime().exec(command);
                int returnVal = p1.waitFor();
                boolean reachable = (returnVal==0);
                Log.d("COLIN", host + " is reachable: " + reachable);
                return reachable;
                /*
                */
                //only works with checking aws
                /*
                HttpURLConnection urlc = (HttpURLConnection) (new URL(host).openConnection());
                urlc.setRequestProperty("User-Agent", "Test");
                urlc.setRequestProperty("Connection", "close");
                urlc.setConnectTimeout(1500); 
                urlc.connect();
                return (urlc.getResponseCode() == 200);
                */
            } catch (Exception e) {
                Log.d("COLIN", "CANT CONNECTED TO " + host);
                return false;
            }
        } else {
            Log.d("COLIN", "CANT CONNECTED TO " + host);
            return false;
        }
    }
    public void stop(){
        running = false;
        stopSelf();
    }
    private int getRandomTime(){
        Random rand = new Random();
        return rand.nextInt(maxValue);//1000 multiplier because of ms conversion;
    }
    public void setMainActivity(mobiHoc activity){
        this.activity = activity;
    }
    public class LocalBinder extends Binder{
        mobiHocService getService(){return mobiHocService.this;}
    }
}
