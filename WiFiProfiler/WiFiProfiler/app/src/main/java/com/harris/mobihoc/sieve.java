// $Id: sieve.java,v 1.8 2001/05/06 04:37:45 doug Exp $
// http://www.bagley.org/~doug/shootout/
package com.harris.mobihoc;

public class sieve implements Runnable{
    private boolean contin = false;
    private int NUM = 10;
    public sieve(boolean contin, int NUM){
        this.contin = contin;
        this.NUM = NUM;
    }
    @Override
    public void run(){
        //int NUM = Integer.parseInt(args[0]);
        boolean [] flags = new boolean[8192 + 1];
        int count = 0;
        while (NUM-- > 0) {
            count = 0;
            for (int i=2; i <= 8192; i++) {
            flags[i] = true;
            }
            for (int i=2; i <= 8192; i++) {
            if (flags[i]) {
                // remove all multiples of prime: i
                for (int k=i+i; k <= 8192; k+=i) {
                flags[k] = false;
                }
                count++;
            }
            }
        }  
        System.out.print("Count: " + count + "\n");
        /*
        if(contin){
            try{
                Thread trial = new Thread(new strcat(contin, NUM));
                trial.start();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        */
    }
}

