// $Id: heapsort.java,v 1.6 2001/05/08 02:46:59 doug Exp $
// http://www.bagley.org/~doug/shootout/
package com.harris.mobihoc;

import java.text.*;
import java.lang.reflect.Array;

public class heapsort implements Runnable{

    public static final long IM = 139968;
    public static final long IA =   3877;
    public static final long IC =  29573;

    private boolean contin = false;
    private int N = 10;

    public heapsort(boolean contin, int N){
        this.contin = contin;
        this.N = N;
    }
    @Override
    public void run(){
        //int N = Integer.parseInt(args[0]);
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(10);
        nf.setMinimumFractionDigits(10);
        nf.setGroupingUsed(false);
        double []ary = (double[])Array.newInstance(double.class, N+1);
        for (int i=1; i<=N; i++) {
            ary[i] = gen_random(1);
        }
        heapsort(N, ary);
        System.out.print(nf.format(ary[N]) + "\n");
        if(contin){
            try{
                Thread trial = new Thread(new matrix(contin, N));
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

    public static void heapsort(int n, double ra[]) {
        int l, j, ir, i;
        double rra;

        l = (n >> 1) + 1;
        ir = n;
        for (;;) {
            if (l > 1) {
            rra = ra[--l];
            } else {
            rra = ra[ir];
            ra[ir] = ra[1];
            if (--ir == 1) {
                ra[1] = rra;
                return;
            }
            }
            i = l;
            j = l << 1;
            while (j <= ir) {
            if (j < ir && ra[j] < ra[j+1]) { ++j; }
            if (rra < ra[j]) {
                ra[i] = ra[j];
                j += (i = j);
            } else {
                j = ir + 1;
            }
            }
            ra[i] = rra;
        }
    }
}
