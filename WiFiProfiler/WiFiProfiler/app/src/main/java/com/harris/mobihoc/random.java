// $Id: random.java,v 1.11 2001/07/31 16:38:37 doug Exp $
// http://www.bagley.org/~doug/shootout/
package com.harris.mobihoc;

import java.text.*;

public class random implements Runnable{

    public static final long IM = 139968;
    public static final long IA = 3877;
    public static final long IC = 29573;
    private boolean contin = false;
    private int N = 10;
    public random(boolean contin, int N){
        this.contin = contin;
        this.N = N;
    }

    @Override
    public void run(){
        //int N = Integer.parseInt(args[0]) - 1;
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(9);
        nf.setMinimumFractionDigits(9);
        nf.setGroupingUsed(false);

        while (N-- > 0) {
            gen_random(100);
        }
        System.out.println(nf.format(gen_random(100)));
        if(contin){
            try{
                Thread trial = new Thread(new sieve(contin, N));
                trial.start();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    public static long last = 42;
    public static double gen_random(double max) {
	    return( max * (last = (last * IA + IC) % IM) / IM );
    }
}
