//package com.harris.vis;
package com.harris.mobihoc;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.net.wifi.p2p.WifiP2pManager;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pManager.*;
import android.util.Log;

/*  COLIN FUNAI JULY 2015
 *  
 *  Receiver for all updates to wifidirect status
 *  include more intents for wifi connection and state?
*/

public class WiFiDirectBroadcastReceiver extends BroadcastReceiver{

    private WifiP2pManager manager;
    private Channel channel;
    private mobiHoc activity;
    
    
    public WiFiDirectBroadcastReceiver (WifiP2pManager manager, Channel channel, mobiHoc activity){

        super();
        this.manager = manager;
        this.channel = channel;
        this.activity = activity;
        Log.d("COLIN", "RECEIVER INITIATED");
    }

    @Override
    public void onReceive(Context context, Intent intent){
        
        String action = intent.getAction();
        Log.d("COLIN", "RECEIVED " + action);

        if(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION.equalsIgnoreCase(action)){
        
            if(intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE, -1) == WifiP2pManager.WIFI_P2P_STATE_ENABLED){        
                //activity.setIsWifiP2pEnabled(true);
            }else{
                //activity.setIsWifiP2pEnabled(false);
            }

            Log.d("WiFiBroadcastReceiver", "change IN Wifi p2p State");
        }else if(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION.equalsIgnoreCase(action)){
  
            Log.d("WifiBroadcastReceiver", "Change in the Peers");

            if(manager != null){
                //manager.requestGroupInfo(channel, (GroupInfoListener) activity);
                manager.requestPeers(channel, (PeerListListener) activity.P2P);
            }
        }else if(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION.equalsIgnoreCase(action)){

           Log.d("WifiBroadcastReceiver", "Change in the Connection");
           
           if(manager != null){

                //activity.P2P.Discover();
                
                manager.requestGroupInfo(channel, (GroupInfoListener)activity.P2P);
                NetworkInfo netInfo = (NetworkInfo)intent.getParcelableExtra(WifiP2pManager.EXTRA_NETWORK_INFO);
              
                //alert the visualization service as to our connection status
                if(netInfo.isConnected()){
                  
                    manager.requestConnectionInfo(channel, (ConnectionInfoListener) activity.P2P);
                    //activity.P2P.setGroupAvailible(true);
                    //activity.P2P.updateRole(true);
                    
                }else{
                    /*
                    if(activity.myPreferences.getBoolean("D2D", true)){//for WD reconnection
                        //activity.wdRecover.start(); 
                        Log.d("COLIN", "WD RECOVERY");
                    }
                    if(activity.myPreferences.getBoolean("LC", true)){//for LC reconnection
                        //activity.lcRecover.start();
                        Log.d("COLIN", "LC RECOVERY");
                    }
                    activity.P2P.initializeRoles();
                    activity.P2P.resetData();
                    activity.P2P.updateRole(false);
                    activity.P2P.setGroupAvailible(false);
                    activity.P2P.Discover();
                    */
                }
                
            }else{
                Log.d("WifiBroadcastReceiver", "WifiP2pManager is null please restart device");
            }
            
        }else if(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION.equalsIgnoreCase(action)){
        
        }
    }
}
