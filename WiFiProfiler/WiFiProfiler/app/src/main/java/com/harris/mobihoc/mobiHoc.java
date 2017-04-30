package com.harris.mobihoc;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.http.AndroidHttpClient;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.net.wifi.p2p.WifiP2pManager;
import android.net.wifi.p2p.WifiP2pManager.Channel;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.android.AndroidAuthSession;
import com.dropbox.client2.exception.DropboxException;
import com.dropbox.client2.session.AppKeyPair;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.FileEntity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class mobiHoc extends Activity{
    mobiHocService benchmarkService;
    private PowerManager pm;
    public WifiManager wm;
    private WifiManager.WifiLock wifi_lock;
    private PowerManager.WakeLock lock;
    public WifiP2p P2P = null;
    public WifiP2pManager manager;
    private ConnectivityManager cm;
    public Channel channel;
    public boolean lightMode = false;
    private IntentFilter intentFilter = new IntentFilter();
    //private DropboxAPI<AndroidAuthSession> mDBApi;//global variable

    private int[] networkTypes = new int[] {ConnectivityManager.TYPE_BLUETOOTH, ConnectivityManager.TYPE_ETHERNET, ConnectivityManager.TYPE_MOBILE, ConnectivityManager.TYPE_MOBILE_DUN, ConnectivityManager.TYPE_WIFI, ConnectivityManager.TYPE_WIMAX};

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        pm = (PowerManager)getSystemService(Context.POWER_SERVICE);
        lock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "Benchmark Service");
        lock.acquire();
        wm = (WifiManager)getSystemService(Context.WIFI_SERVICE);
        wifi_lock = wm.createWifiLock("BenchMark");
        wifi_lock.acquire();

        manager = (WifiP2pManager)getSystemService(Context.WIFI_P2P_SERVICE);
        channel = manager.initialize(this, getMainLooper(), null);
        P2P = new WifiP2p(this, manager, channel);

        cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

        Button uploadButton = (Button) findViewById(R.id.button);
        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getWifiNetworks(false);
            }
        });

                //P2P.restart();

                //P2P.registerService();
                //P2P.findTDP();
    }



    public class AsyncHttpPostTask extends AsyncTask<File, Void, String> {

        private final String TAG = AsyncHttpPostTask.class.getSimpleName();
        private String server;

        public AsyncHttpPostTask(final String server) {
            this.server = server;
        }

        @Override
        protected String doInBackground(File... params) {
            Log.d(TAG, "doInBackground");
            HttpClient http = AndroidHttpClient.newInstance("MyApp");
            HttpPost method = new HttpPost(this.server);
            method.setEntity(new FileEntity(params[0], "text/plain"));
            try {
                HttpResponse response = http.execute(method);
                BufferedReader rd = new BufferedReader(new InputStreamReader(
                        response.getEntity().getContent()));
                final StringBuilder out = new StringBuilder();
                String line;
                try {
                    while ((line = rd.readLine()) != null) {
                        out.append(line);
                    }
                } catch (Exception e) {}
                // wr.close();
                try {
                    rd.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                // final String serverResponse = slurp(is);
                Log.d(TAG, "serverResponse: " + out.toString());
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
    
    @Override
    public void onDestroy(){
        super.onDestroy();
        unBindServce();
        if(benchmarkService != null){
            lock.release();
            wifi_lock.release();
            benchmarkService.stop();
        }
    }
    public long getTimeSinceMidnight(){
        Calendar c = Calendar.getInstance();
        long now = c.getTimeInMillis();
        //normalize to midnight
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return (now - c.getTimeInMillis());
    }

    public void recordActiveNetworks(){
        NetworkInfo netInfo = null;
        for(int type : networkTypes){
            netInfo = cm.getNetworkInfo(type); 
            if(netInfo != null){
                //printInfo(new File("/mnt/sdcard/DCIM/OtherNetworkInfo.txt") , "At time " + System.currentTimeMillis() + " " + netInfo.getTypeName() + " has information " + netInfo.toString() + "\n" );
                printInfo(new File("/mnt/sdcard/DCIM/OtherNetworkInfo.txt") , "At time " + getTimeSinceMidnight() + ", At time " + System.currentTimeMillis() +  ", " + netInfo.getTypeName() + " has information " + netInfo.toString() + "\n" );
            }
        }
    }

    public void getWifiNetworks(boolean automatic){
        List<ScanResult> availableNetworks = new ArrayList<ScanResult>();
        availableNetworks = wm.getScanResults();

        File networks;
        if (automatic) {
            networks = new File("/mnt/sdcard/DCIM/AvailableNetworks.txt");
        } else {
            networks = new File("/mnt/sdcard/DCIM/AvailableNetworksOnDemand.txt");
        }

        //write scanresult
        //printInfo(networks, "At " + System.currentTimeMillis() + " Found the following\n", trial);
        if(lightMode){
            printInfo(networks, "At " + getTimeSinceMidnight() + ", At time " + System.currentTimeMillis() +  ", "+ " Found " + availableNetworks.size()+ " Networks\n");
        }else{
            printInfo(networks, "At " + getTimeSinceMidnight() + ", At time " + System.currentTimeMillis() +  ", "+ " Found the following\n");
            for(ScanResult network : availableNetworks){
                printInfo(networks, network.toString()+"\n");
            }
        }
        if (!automatic) printInfo(networks, "----------------------------------\n");
        availableNetworks.clear();
        availableNetworks = null;
    }


    public boolean isWifiAvailable(){
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        Log.d("COLIN", "TYPE IS " + netInfo.getType());
        if((netInfo.getType() == 13)|| (netInfo.getType() == ConnectivityManager.TYPE_WIFI)){
            return true;
        }else{
            return false;
        }
    }
    private void printInfo(File file, String Info){
        BufferedWriter fos = null;
        //create fileOutputStream;
        try{
            fos = new BufferedWriter(new FileWriter(file, true));
            fos.write(Info);
            fos.flush();
            fos.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        //write data
    }
    @Override
    public void onResume(){
        super.onResume();
        //intentFilter = new IntentFilter();
        P2P = new WifiP2p(this, manager, channel);
        //P2P.restart();
        //P2P.Discover();
        //this breaks it

//        startService(new Intent(this, mobiHocService.class));
//        doBindService();


    }
    @Override
    public void onPause(){
        super.onPause();
    }
    /////////////////////////////////service binding stuff////////////////////////////////////

    void unBindServce(){

        stopService(new Intent(mobiHoc.this, mobiHocService.class));
        unbindService(ServiceConnector);
    }

    void doBindService(){

        bindService(new Intent(mobiHoc.this, mobiHocService.class), ServiceConnector, Context.BIND_AUTO_CREATE);
    }

    ServiceConnection ServiceConnector = new ServiceConnection(){

        @Override
        public void onServiceConnected(ComponentName className, IBinder binder){

            //might want to do seomthing there
            benchmarkService = ((mobiHocService.LocalBinder) binder).getService();
            benchmarkService.setMainActivity(mobiHoc.this);
            //need to check for connection status
            benchmarkService.turnOn();
        }

        @Override
        public void onServiceDisconnected(ComponentName className){

            stopService(new Intent(mobiHoc.this, mobiHocService.class));
        }

    };
    //1) create a service and bind a service
}
