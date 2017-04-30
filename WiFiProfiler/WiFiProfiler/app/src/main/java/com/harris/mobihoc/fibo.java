// $Id: fibo.java,v 1.2 2000/12/24 19:10:50 doug Exp $
// http://www.bagley.org/~doug/shootout/
package com.harris.mobihoc;

public class fibo implements Runnable{
    private boolean contin = false;
    private int N = 10;
    public fibo(boolean contin, int N){
        this.contin = contin;
        this.N = N;
    }
    @Override
    public void run(){
        //int N = Integer.parseInt(args[0]);
        System.out.println(fib(N));
        if(contin){
            try{
                Thread trial = new Thread(new hash(contin, N));
                trial.start();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
    public static int fib(int n) {
        if (n < 2) return(1);
        return( fib(n-2) + fib(n-1) );
    }
}
