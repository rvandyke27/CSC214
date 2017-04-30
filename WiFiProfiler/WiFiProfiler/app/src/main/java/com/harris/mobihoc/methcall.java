// $Id: methcall.java,v 1.3 2000/12/24 22:04:51 doug Exp $
// http://www.bagley.org/~doug/shootout/
// Collection class code is from my friend Phil Chu, Thanks Phil!
package com.harris.mobihoc;

import java.io.*;
import java.util.*;
import java.text.*;

class Toggle {
    boolean state = true;
    public Toggle(boolean start_state) {
	    this.state = start_state;
    }
    public boolean value() {
	    return(this.state);
    }
    public Toggle activate() {
        this.state = !this.state;
        return(this);
    }
}

class NthToggle extends Toggle {
    int count_max = 0;
    int counter = 0;

    public NthToggle(boolean start_state, int max_counter) {
        super(start_state);
        this.count_max = max_counter;
        this.counter = 0;
    }
    public Toggle activate() {
        this.counter += 1;
        if (this.counter >= this.count_max) {
            this.state = !this.state;
            this.counter = 0;
        }
        return(this);
    }
}

public class methcall implements Runnable{
    private boolean contin = false;
    private int n = 10;
    public methcall(boolean contin, int n){
        this.contin = contin;
        this.n = n;
    }
    @Override
    public void run(){
        //int n = Integer.parseInt(args[0]);

        boolean val = true;
        Toggle toggle = new Toggle(val);
        for (int i=0; i<n; i++) {
            val = toggle.activate().value();
        }
        System.out.println((val) ? "true" : "false");

        val = true;
        NthToggle ntoggle = new NthToggle(true, 3);
        for (int i=0; i<n; i++) {
            val = ntoggle.activate().value();
        }
        System.out.println((val) ? "true" : "false");
        if(contin){
            try{
                Thread trial = new Thread(new nestedloop(contin, n));
                trial.start();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
}
