// $Id: sumcol.java,v 1.2 2000/10/07 08:41:44 doug Exp $
// http://www.bagley.org/~doug/shootout/
package com.harris.mobihoc;

import java.io.*;
import java.util.*;
import java.text.*;

public class sumcol implements Runnable{
    private boolean contin = false;
    private int n = 10;
    public sumcol(boolean contin, int n){
        this.contin = contin;
        this.n = n;
    }
    @Override
    public void run(){
        int sum = 0;
        String line = "10";
        sum = sum + Integer.parseInt(line);
        /*
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
                while ((line = in.readLine()) != null) {
            sum = sum + Integer.parseInt(line);
                }
            } catch (IOException e) {
                System.err.println(e);
                return;
            }
        */
        System.out.println(Integer.toString(sum));
        if(contin){
            try{
                Thread trial = new Thread(new wc(contin, n));
                trial.start();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
}
