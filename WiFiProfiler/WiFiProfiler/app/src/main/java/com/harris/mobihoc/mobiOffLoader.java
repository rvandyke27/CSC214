package com.harris.mobihoc;
//java imports
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;

//android imports
import android.net.wifi.WifiManager;
import android.net.wifi.ScanResult;
import android.net.wifi.p2p.WifiP2pManager;
import android.util.Log;
//statement of purpose 
//
//has a table of contents which maps trialThread number to time
//
//thread writes timing for the java benchmarks to a txt file. 
//each benchmark gets its own txt
//
//scans and records wifi networks 
//scans and records wifi direct groups
//this requires a wifidirect broadcast receiver. will steal from another project.
//
//
public class mobiOffLoader implements Runnable{
    private int trial;
    private boolean randomTrial = false;//to control whether you want to have a random benchmark for each trail;l
    private int testNumber;
    private int N = 5;//controlls number of iterations for the benchmark
    private String cloudIP = null;
    private WifiManager wm;
    private int port;
    private String fileName = "offloader.txt";
    private long commStart = 0, commEnd = 0, calcStart = 0, calcEnd = 0;
    private mobiHoc activity;
    public mobiOffLoader(int trial, WifiManager wm, boolean randomTrial, int testNumber, String cloudIP, int port, mobiHoc activity){
        this.trial = trial;
        this.wm = wm;
        this.randomTrial = randomTrial;
        this.testNumber = testNumber;
        this.cloudIP = cloudIP;
        this.port = port;
        this.activity = activity;
    }
    @Override
    public void run(){
        //get network information;
        //write trail and time of day to table of contents
        long start = System.nanoTime();    
        offLoad(randomTrial, testNumber); 
        long end = System.nanoTime();
        
        //decide File to write To
        //printInfo(new File("/mnt/sdcard/DCIM/"+cloudIP+fileName), (end-start) + ", " + (commEnd-commStart) + ", " + (calcEnd - calcStart) + ", " + (end - calcEnd) + ", " + System.currentTimeMillis() + "\n" , trial);
        printInfo(new File("/mnt/sdcard/DCIM/"+cloudIP+fileName), (end-start) + ", " + (commEnd-commStart) + ", " + (calcEnd - calcStart) + ", " + (end - calcEnd) + ", " + activity.getTimeSinceMidnight() + ", " + System.currentTimeMillis() +  ", "+ "\n" , trial);

    }
    private void offLoad(boolean randomTest, int testNumber){
        Socket client = null;
        DataOutputStream dos = null;
        DataInputStream dis = null;
        commStart = System.nanoTime();
        try{
            client = new Socket(InetAddress.getByName(cloudIP), port);
            dos = new DataOutputStream(client.getOutputStream());
            dis = new DataInputStream(client.getInputStream());
        }catch(Exception e){
            e.printStackTrace();
        }
        if(client != null){
            try{
                dos.writeBoolean(randomTest);
            }catch(Exception e){
                e.printStackTrace();
            }
            if(randomTrial){
                try{
                    dos.writeInt(testNumber);
                    commEnd = System.nanoTime() - commStart;
                        switch(testNumber){
                            case 1:
                                dis.readInt();
                                break;
                            case 2:
                                dis.readInt();
                                break;
                            case 3:
                                dis.readInt();
                                break;
                            case 4:
                                dis.readInt();
                                dis.readInt();
                                dis.readInt();
                                dis.readInt();
                                break;
                            case 5: 
                                dis.readUTF();
                                break;
                            case 6:
                                dis.readInt();
                                dis.readInt();
                                dis.readInt();
                                dis.readInt();
                                break;
                            case 7:
                                dis.readUTF();
                                break;
                            case 8:
                                dis.readInt();
                                break;
                            case 9:
                                dis.readUTF();
                                break;
                            case 10:
                                dis.readUTF();
                                break;
                            case 11:
                                dis.readInt();
                                break;
                        }

                    }catch(Exception e){e.printStackTrace();}
                }else{
                    commEnd = System.nanoTime();
                    try{
                        calcStart = System.nanoTime();
                        Log.d("Colin", "Starting0");
                        //1
                        dis.readInt();
                        Log.d("Colin", "Starting1");
                        //2
                        dis.readInt();
                        Log.d("Colin", "Starting2");
                        //3
                        dis.readInt();
                        Log.d("Colin", "Starting3");
                        //4
                        dis.readInt();
                        Log.d("Colin", "Starting41");
                        dis.readInt();
                        Log.d("Colin", "Starting42");
                        dis.readInt();
                        Log.d("Colin", "Starting43");
                        dis.readInt();
                        Log.d("Colin", "Starting44");
                        //5
                        dis.readUTF();
                        Log.d("Colin", "Starting5");
                        //6
                        dis.readInt();
                        Log.d("Colin", "Starting61");
                        dis.readInt();
                        Log.d("Colin", "Starting62");
                        dis.readInt();
                        Log.d("Colin", "Starting63");
                        dis.readInt();
                        Log.d("Colin", "Starting64");
                        //7
                        dis.readUTF();
                        Log.d("Colin", "Starting7");
                        //8
                        dis.readInt();
                        Log.d("Colin", "Starting8");
                        //9
                        dis.readUTF();
                        Log.d("Colin", "Starting9");
                        //10
                        dis.readUTF();
                        Log.d("Colin", "Starting10");
                        //11
                        dis.readInt();
                        Log.d("Colin", "Starting11");
                        calcEnd = System.nanoTime();
                    }catch(Exception e){e.printStackTrace();}
                }
            try{
                dos.close();
                dis.close(); 
                client.close();
                Log.d("Colin", "ENDED");
            }catch(Exception e){
                e.printStackTrace();
            }
        }
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
}
