package com.harris.mobihoc;

import android.content.Context;
import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pGroup;
import android.net.wifi.p2p.WifiP2pInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.net.wifi.p2p.WifiP2pManager.ConnectionInfoListener;
import android.net.wifi.p2p.WifiP2pManager.GroupInfoListener;
import android.net.wifi.p2p.WifiP2pManager.DnsSdServiceResponseListener;
import android.net.wifi.p2p.WifiP2pManager.DnsSdTxtRecordListener;
import android.net.wifi.p2p.WifiP2pManager.ActionListener;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pManager.Channel;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pManager.ChannelListener;
import android.net.wifi.p2p.WifiP2pManager.PeerListListener;
import android.net.wifi.p2p.nsd.WifiP2pServiceInfo;
import android.net.wifi.p2p.nsd.WifiP2pDnsSdServiceInfo;
import android.net.wifi.p2p.nsd.WifiP2pServiceRequest;
import android.net.wifi.p2p.nsd.WifiP2pDnsSdServiceRequest;
import android.net.wifi.WpsInfo;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
//import com.ucb.gemcloudsaint2.DeviceListFragment.DeviceActionListener;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Hashtable;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
public class WifiP2p implements PeerListListener, ChannelListener, DnsSdTxtRecordListener, DnsSdServiceResponseListener, GroupInfoListener, ConnectionInfoListener{
    private WifiP2pManager manager;
    private static String GOAdd = "192.168.49.1";
    private Channel channel;
    private Context context;
    private final IntentFilter intentFilter = new IntentFilter();
    private boolean retryChannel = false;
    private static boolean Connected = false;
    private static boolean D2D_enabled = false;
    private static boolean isGO = false;
    private mobiHoc activity;
    private Hashtable<String, WifiP2pConfig> cache = new Hashtable<String, WifiP2pConfig>();
    public WiFiDirectBroadcastReceiver receiver;
    private String TAG = "NSD TEST";
    private WifiP2pDnsSdServiceInfo serviceInfo = null;
    public static String SSID = null;
    public static String psk = null;
    
    private static void setRole(boolean status){
        isGO = !status; 
    }
    public static String getOwnerAddress(){
        return GOAdd;
    }
    public static boolean getRole(){
        return isGO;
    }
    public WifiP2p(mobiHoc activity, WifiP2pManager manager, Channel channel){
        this.activity = activity;
        /*
        */
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);
        //manager = (WifiP2pManager) activity.getSystemService(Context.WIFI_P2P_SERVICE);
        //channel = manager.initialize(activity, activity.getMainLooper(), null);
        this.manager = manager;
        this.channel = channel;
        disconnect();
        registerService();
        //Create();
    }
    private final String decoder(int value){
        String reason = null;
        switch(value){
            case 0:
                reason = "ERROR";
            case 1:
                reason = "P2P UNSUPPORTED";
            case 2:
                reason = "BUSY";
            default:
                reason = "UNKNOWN";
        }
        return reason;
    }
    public void Discover(){
        Log.d("COLIN", "SEARCHING");
        manager.discoverPeers(channel, new WifiP2pManager.ActionListener(){
            @Override
            public void onSuccess(){
                Log.d("WIFIDIRECT", "Currently Looking for peers");
            }
            @Override
            public void onFailure(int reasonCode){
                String reason = decoder(reasonCode);
                Log.d("WIFIDIRECT", "Failed to Discover peers. Reason: "+reason);
            }
        });
    }
    @Override
    public void onDnsSdServiceAvailable(String instanceName, String registrationType, WifiP2pDevice srcDevice) {
        //Log.d(TAG, "SERVICE FOUND " + srcDevice.deviceAddress);
        Log.d(TAG + " SERVICE", "FOUND " + instanceName);
        Log.d(TAG + " SERVICE", "FOUND " + registrationType);
        printInfo(new File("/mnt/sdcard/DCIM/WDScan.txt"), "Found Service on " + srcDevice.deviceName + ", At " + activity.getTimeSinceMidnight() + ", At time " + System.currentTimeMillis() +  ", "+ " device details: " + srcDevice.toString() + "\n" , 0);
    }
    private void printInfo(File file, String Info, int trial){
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
    public void onPeersAvailable(WifiP2pDeviceList peerList){
        //call public method to populate the list we see
        //this method will also display what nodes are in our group (hopefully)
        List<WifiP2pDevice> ListenerList = new ArrayList<WifiP2pDevice>(peerList.getDeviceList());
        Log.d("COLIN", "PEERS IS " + peerList.getDeviceList().size());
        Log.d("COLIN", "List is " + ListenerList.size());
        boolean noService = true;
        if(activity.lightMode){
            printInfo(new File("/mnt/sdcard/DCIM/WiFiDirect_peers.txt"), "At " + activity.getTimeSinceMidnight() + ", At time " + System.currentTimeMillis() +  ", "+ " found " + ListenerList.size()+ " peers\n", 0);
        }else{
            for(WifiP2pDevice device : ListenerList){
                printInfo(new File("/mnt/sdcard/DCIM/WiFiDirect_peers.txt"), "At " + activity.getTimeSinceMidnight() + ", At time " + System.currentTimeMillis() +  ", "+ " found the following peers " + device.toString() + "\n", 0);
                if(device.isGroupOwner()){
                    noService = !noService;
                    break;
                }
            }
        }
        /*
        if(ListenerList.size() == 0){
            Create();
        }
        */
        //list.UpdatePeerList(ListenerList);
    }
    @Override
    public void onDnsSdTxtRecordAvailable(String fullDomainName, Map<String, String> record, WifiP2pDevice device) {
        Log.d(TAG, device.deviceName + " is " + record.toString());
        if((!Connected) && (record.get("GEMCLOUD").equalsIgnoreCase("SAINT2"))){
            //printInfo(new File("/mnt/sdcard/DCIM/WDServiceScan.txt"), "Found Service on " + device.deviceName + " At " + System.currentTimeMillis() + " device details: " + device.toString() + "\n", 0);
            printInfo(new File("/mnt/sdcard/DCIM/WDServiceScan.txt"), "Found Service on " + device.deviceName + " At " + activity.getTimeSinceMidnight() + ", At time " + System.currentTimeMillis() +  ", "+ " device details: " + device.toString() + "\n", 0);
            //Discover();
            //connect(device);
        }
    }
    private void clearService(){
        manager.clearServiceRequests(channel, new WifiP2pManager.ActionListener(){
            @Override
            public void onSuccess(){}
            @Override
            public void onFailure(int code){Log.d("COLIN", "COULD NOT CLEAR SERVICE REQUESTS");}
        });
    }
    //private void discoverService() {
    public void findTDP() {

        /*
         * Register listeners for DNS-SD services. These are callbacks invoked
         * by the system when a service is actually discovered.
         */
        Log.i("GEMCloudService", "LOOKING FOR SERVICE");
        /*
        manager.setDnsSdResponseListeners(channel, this, this);
        //Discover();
        // After attaching listeners, create a service request and initiate
        // discovery.
        WifiP2pDnsSdServiceRequest serviceRequest = WifiP2pDnsSdServiceRequest.newInstance();
        //why
        manager.addServiceRequest(channel, serviceRequest, new ActionListener() {
            @Override
            public void onSuccess() {}
            @Override
            public void onFailure(int arg0) {Log.d(TAG+" SERVICE", "FAILED TO REGISTER SERVICE LISTENER " + arg0);}
        });
        //end why
        /*
        */
        startService();
        manager.discoverServices(channel, new ActionListener() {
            @Override
            public void onSuccess() {}
            @Override
            public void onFailure(int arg0) {Log.d(TAG, "FAILED TO REGISTER LISTENER " + arg0);}
        });
    }
    public void findServices(){
        manager.setDnsSdResponseListeners(channel, this, this);
        manager.discoverServices(channel, new ActionListener() {
            @Override
            public void onSuccess() {}
            @Override
            public void onFailure(int arg0) {Log.d(TAG, "FAILED TO REGISTER LISTENER " + arg0);}
        });
    }
    /*
    public void unregisterService(){
        if(serviceInfo != null){
            manager.removeLocalService(channel, serviceInfo, new ActionListener(){
                @Override
                public void onSuccess(){}
                @Override
                public void onFailure(int code){Log.d(TAG, "FAILED to remove service because of " + code);}
            });
        }
    }
    */
    public void registerService(){
        //test method and stuffs will change once verified it is working;
        Map record = new HashMap();
        record.put("listenport", String.valueOf(50000));
        record.put("GEMCLOUD", "SAINT2");
        record.put("buddyName", "John Doe");
        record.put("available", "visible");
        serviceInfo = WifiP2pDnsSdServiceInfo.newInstance("_test", "_presence._tcp", record);
        manager.addLocalService(channel, serviceInfo, new ActionListener(){
            @Override
            public void onSuccess(){}
            @Override
            public void onFailure(int code){Log.d("WIFIDIRECT", "FAILED REGISTER SERVICE REASON: " + code);}
        });
        Log.d("WIFIDIRECT", "GEMCLOUD SERVICE REGISTERED");
    }
    public void removeService(){
        manager.removeLocalService(channel, serviceInfo, new ActionListener(){
            @Override
            public void onSuccess(){}
            @Override
            public void onFailure(int code){Log.d("WIFIDIRECT", "FAILED TO REMOVE SERVICE REASON: " + code);}
        });
    }
    public void Add(String position){
        //get current wifiP2p Config or grroup owner mac 
        
        //make wifiP2pConfig
        WifiP2pConfig config = new WifiP2pConfig();
        config.deviceAddress = " ";
        config.wps.setup = WpsInfo.PBC;
        //add to cache
        cache.put(position, config);
        //for checking purposes
        printTable();
    }
    public void printTable(){
        for(int i = 0; i < cache.size(); i++){
            Log.d("MultiHop", "The Current Status of the table is " + cache.get(i).deviceAddress + "\n");
        }
    }
    //see netmon deviceListFragment for more details on implementation
    //create a function to enable and disable auto scan and connect
    //probably can directly call activity.wm
    /*
    public static List<ScanResult> APScan(){
        List<ScanResult> ApScan = new ArrayList<ScanResult>();
        if(GEMCloudService.wm.startScan()){
            ApScan = GEMCloudService.wm.getScanResults();
        }
        return findDirect(ApScan);
    }
    */
    private static List<ScanResult> findDirect(List<ScanResult> ActiveAps){
        List<ScanResult> DirectAps = new ArrayList<ScanResult>(); 
        String[] SSID=null;
        String Direct = "DIRECT";
        int j = 0;
        for(int i = 0; i < ActiveAps.size(); i++){
            SSID = ActiveAps.get(i).SSID.split("-");
            if(SSID[0].equals(Direct)){
                DirectAps.add(j++, ActiveAps.get(i));
            }
        }
        ActiveAps.clear();
        return DirectAps;
    }
    public void Create(){
        //startService();
        Log.d("Multi", "Creating a group");
        manager.createGroup(channel, new WifiP2pManager.ActionListener(){
            @Override
            public void onSuccess(){
                Log.d("WIFIDIRECT", "SUCCESSED IN MAKING GROUP");
            }
            @Override
            public void onFailure(int reasonCode){
                String reason = decoder(reasonCode);
                Log.d("WIFIDIRECT", "Failed to Create Group. Reason: "+reason);
                Log.d("WIFIDIRECT_PROXY", "TRIED TO RECREATE GROUP");
            }
        });
        
        setRole(false);
    }
    public void startService(){
        manager.setDnsSdResponseListeners(channel, this, this);
        WifiP2pDnsSdServiceRequest serviceRequest = WifiP2pDnsSdServiceRequest.newInstance();
        //why
        manager.addServiceRequest(channel, serviceRequest, new ActionListener() {
            @Override
            public void onSuccess() {}
            @Override
            public void onFailure(int arg0) {Log.d(TAG+" SERVICE", "FAILED TO REGISTER SERVICE LISTENER " + arg0);}
        });
        /*
        */
    }
    @Override
    public void onConnectionInfoAvailable(final WifiP2pInfo info){
        if(info != null){
            if(info.groupOwnerAddress != null){
                GOAdd = info.groupOwnerAddress.getHostAddress();
            }
        }
    }
    @Override
    public void onGroupInfoAvailable(final WifiP2pGroup group){
        if(group != null){
            SSID = group.getNetworkName();
            psk = group.getPassphrase();
            Log.d("Multi", SSID + " " + psk);
        }else{
            Log.d("Multi", "GROUP IS DNE"); 
        }
    }
    private WifiP2pConfig getConfig(String address){
        if(cache.containsKey(address)){
            return cache.get(address);
        }else{
            WifiP2pConfig config = new WifiP2pConfig();
            config.deviceAddress = address;
            config.wps.setup = WpsInfo.PBC;
            return config;
        }
    }
    public void connect(WifiP2pDevice device){
        WifiP2pConfig config = new WifiP2pConfig();
        config.deviceAddress = device.deviceAddress;
        config.wps.setup = WpsInfo.PBC;
        connect(config);
    }
    public void connect(String Address){
        WifiP2pConfig config = getConfig(Address);
        connect(config);
    }
    public void setConnected(boolean status){
        Connected = status;
    }
    public void setD2D(boolean status){
        D2D_enabled = status;
    }
    private void connect(WifiP2pConfig config){
        final String location = config.deviceAddress;
        manager.connect(channel, config, new ActionListener(){
            @Override
            public void onSuccess(){
                Log.d("WIFIDIRECT", "TRYING TO CONNECT TO " + location);
            }
            @Override
            public void onFailure(int reasonCode){
                String reason = decoder(reasonCode);
                Log.d("WIFIDIRECT", "Failed to Connect. Reason: " + reason);
            }
        });
    }
    public void disconnect(){
        manager.removeGroup(channel, new ActionListener(){
            @Override
            public void onSuccess(){
                Log.d("WIFIDIRECT", "Successfully disconnected from prior group");
            }
            @Override
            public void onFailure(int reasonCode){
                String reason = decoder(reasonCode);
                Log.d("WIFIDIRECT", "FAILED to disconnect. Reason: " + reason);
            }
        });
    }
    public void showDetails(WifiP2pDevice device){
        setRole(device.isGroupOwner());
    }
    public void cancelDisconnect(){
    }
    public void resetData(){
    }
    @Override
    public void onChannelDisconnected(){
        if(manager != null && !retryChannel){
            Log.d("WIFIDIRECT", "CHANNEL LOST");
            resetData();
            retryChannel = true;
        }else{
            Log.d("WIFIDIRECT", "CHANNEL IS PERMANTLY LOST");
        }
    }
    public void restart() {
        receiver = new WiFiDirectBroadcastReceiver(manager, channel, activity);
        activity.registerReceiver(receiver, intentFilter);
        //activity.lock.acquire();
        //activity.wifi_lock.acquire();
    }

    public void stop() {
        activity.unregisterReceiver(receiver);
        //unregisterService();
        disconnect();
        //activity.lock.release();
        //activity.wifi_lock.release();
    }
}
