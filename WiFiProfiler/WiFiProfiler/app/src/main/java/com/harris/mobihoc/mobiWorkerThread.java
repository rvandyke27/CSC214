package com.harris.mobihoc;
//java imports
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//android imports
import android.net.wifi.WifiManager;
import android.net.wifi.ScanResult;
import android.net.wifi.p2p.WifiP2pManager;
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
public class mobiWorkerThread implements Runnable{
    private int trial;
    private boolean randomTrial = false;//to control whether you want to have a random benchmark for each trail;l
    private int testNumber;
    private int N = 5;//controlls number of iterations for the benchmark
    private WifiManager wm;
    private int port;
    private mobiHoc activity;
    public mobiWorkerThread(int trial, WifiManager wm, boolean randomTrial, int testNumber, mobiHoc activity){
        this.trial = trial;
        this.wm = wm;
        this.randomTrial = randomTrial;
        this.testNumber = testNumber;
        this.activity = activity;
    }
    @Override
    public void run(){
        //get network information;
        //write trail and time of day to table of contents
        Thread trialThread = null;
        String fileName = null;
        if(randomTrial){
            switch(testNumber){
                case 1:
                    trialThread = new Thread(new ackermann(false, N));
                    fileName = "ackermann.txt";
                    break;
                case 2:
                    trialThread = new Thread(new fibo(false, N));
                    fileName = "fibo.txt";
                    break;
                case 3:
                    trialThread = new Thread(new hash(false, N));
                    fileName = "hash.txt";
                    break;
                case 4:
                    trialThread = new Thread(new hash2(false, N));
                    fileName = "hash2.txt";
                    break;
                case 5: 
                    trialThread = new Thread(new heapsort(false, N));
                    fileName = "heapsort.txt";
                    break;
                case 6:
                    trialThread = new Thread(new matrix(false, N));
                    fileName = "matrix.txt";
                    break;
                case 7:
                    trialThread = new Thread(new methcall(false, N));
                    fileName = "methcall.txt";
                    break;
                case 8:
                    trialThread = new Thread(new nestedloop(false, N));
                    fileName = "nestedloop.txt";
                    break;
                case 9:
                    trialThread = new Thread(new objinst(false, N));
                    fileName = "objinst.txt";
                    break;
                case 10:
                    trialThread = new Thread(new random(false, N));
                    fileName = "random.txt";
                    break;
                case 11:
                    trialThread = new Thread(new sieve(false, N));
                    fileName = "steve.txt";
                    break;
            }
        }else{
            trialThread = new Thread(new ackermann(true, N));
            fileName = "AllBenchMark.txt";
        }
        long start = System.nanoTime();    
        try{
            //set thread priority
            trialThread.start();
        }catch(Exception e){
            e.printStackTrace();
        }
        long end = System.nanoTime();
        
        //decide File to write To
        //printInfo(new File("/mnt/sdcard/DCIM/"+fileName), (end-start) + " ,  nanoSeconds at " + System.currentTimeMillis() + "\n", trial);
        printInfo(new File("/mnt/sdcard/DCIM/"+fileName), (end-start) + " ,  nanoSeconds at " + activity.getTimeSinceMidnight() + ", At time " + System.currentTimeMillis() +  " "+ "\n", trial);
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
