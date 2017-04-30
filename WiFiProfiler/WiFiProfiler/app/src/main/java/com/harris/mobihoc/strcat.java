// $Id: strcat.java2.java,v 1.4 2001/01/31 03:38:54 doug Exp $
// http://www.bagley.org/~doug/shootout/
package com.harris.mobihoc;

import java.io.*;
import java.util.*;

public class strcat implements Runnable{
    private boolean contin = false;
    private int n = 10;
    public strcat(boolean contin, int n){
        this.contin = contin;
        this.n = n;
    }
    @Override
    public void run(){
        //int n = Integer.parseInt(args[0]);
        StringBuffer str = new StringBuffer();

        for (int i=0; i<n; i++) {
            str.append("hello\n");
        }

        System.out.println(str.length());
        /*
        if(contin){
            try{
                Thread trial = new Thread(new wc(contin, n));
                trial.start();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        if(contin){
            try{
                Thread trial = new Thread(new sumcol(contin, n));
                trial.start();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        */
    }
}
