// $Id: nestedloop.java,v 1.1 2000/12/30 21:42:57 doug Exp $
// http://www.bagley.org/~doug/shootout/
package com.harris.mobihoc;

import java.io.*;
import java.util.*;

public class nestedloop implements Runnable{
    private boolean contin = false;
    private int n = 10;
    public nestedloop(boolean contin, int n){
        this.contin = contin;
        this.n = n;
    }
    @Override
    public void run(){
        //int n = Integer.parseInt(args[0]);
        int x = 0;
        for (int a=0; a<n; a++)
            for (int b=0; b<n; b++)
            for (int c=0; c<n; c++)
                for (int d=0; d<n; d++)
                for (int e=0; e<n; e++)
                    for (int f=0; f<n; f++)
                    x++;
        System.out.println(x);
        if(contin){
            try{
                Thread trial = new Thread(new objinst(contin, n));
                trial.start();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
}
