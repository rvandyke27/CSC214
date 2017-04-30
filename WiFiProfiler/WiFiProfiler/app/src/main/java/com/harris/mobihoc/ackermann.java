// $Id: ackermann.java,v 1.5 2001/11/17 17:20:39 doug Exp $
// http://www.bagley.org/~doug/shootout/
package com.harris.mobihoc;

public class ackermann implements Runnable{
    private boolean contin = false;
    private int num = 10;
    public ackermann(boolean contin, int num){
        this.contin = contin;
        this.num = num;
    }
    @Override
    public void run(){
        //int num = Integer.parseInt(args[0]);
        System.out.println("Ack(3," + num + "): " + Ack(3, num));
        if(contin){
            try{
                Thread trial = new Thread(new fibo(contin, num));
                trial.start();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
    public static int Ack(int m, int n) {
        return (m == 0) ? (n + 1) : ((n == 0) ? Ack(m-1, 1) :
                         Ack(m-1, Ack(m, n - 1)));
    }
}
