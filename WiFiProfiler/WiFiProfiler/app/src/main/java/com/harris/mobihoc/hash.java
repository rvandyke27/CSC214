// $Id: hash.java,v 1.3 2001/03/02 02:17:29 doug Exp $
// http://www.bagley.org/~doug/shootout/

// this program is modified from:
//   http://cm.bell-labs.com/cm/cs/who/bwk/interps/pap.html
// Timing Trials, or, the Trials of Timing: Experiments with Scripting
// and User-Interface Languages</a> by Brian W. Kernighan and
// Christopher J. Van Wyk.
package com.harris.mobihoc;

import java.io.*;
import java.util.*;

public class hash implements Runnable{

    private boolean contin = false;
    private int n = 10;
    public hash(boolean contin, int n){
        this.contin = contin;
        this.n = n;
    }
    @Override
    public void run(){
        //int n = Integer.parseInt(args[0]);
        int i, c;
        String s = "";
        Integer ii;
        // the original program used:
        // Hashtable ht = new Hashtable();
        // John Olsson points out that Hashtable is for synchronized access
        // and we should use instead:
        HashMap ht = new HashMap();

        c = 0;
        for (i = 1; i <= n; i++)
            ht.put(Integer.toString(i, 16), new Integer(i));
        for (i = 1; i <= n; i++)
            // The original code converted to decimal string this way:
            // if (ht.containsKey(i+""))
            if (ht.containsKey(Integer.toString(i, 10)))
            c++;

        System.out.println(c);
        if(contin){
            try{
                Thread trial = new Thread(new hash2(contin, n));
                trial.start();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
}

