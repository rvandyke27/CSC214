// $Id: hash2.java,v 1.3 2001/05/31 20:37:44 doug Exp $
// http://www.bagley.org/~doug/shootout/
package com.harris.mobihoc;

import java.util.*;

class Val {
    int val;
    Val(int init) { val = init; }
}

public class hash2 implements Runnable{
    private boolean contin = false;
    private int n = 10;
    public hash2(boolean contin, int n){
        this.contin = contin;
        this.n = n;
    }
    @Override
    public void run(){
        //int n = Integer.parseInt(args[0]);
        HashMap hash1 = new HashMap(10000);
        HashMap hash2 = new HashMap(n);

        for(int i = 0; i < 10000; i++)
            hash1.put("foo_" + Integer.toString(i, 10), new Val(i));
        for(int i = 0; i < n; i++) {
            Iterator it = hash1.entrySet().iterator();
            while(it.hasNext()) {
                Map.Entry h1 = (Map.Entry)it.next();
                String key = (String)h1.getKey();
                int v1 = ((Val)h1.getValue()).val;
                if (hash2.containsKey(key))
                    ((Val)hash2.get(key)).val += v1;
                else
                    hash2.put(key, new Val(v1));
            }
        }

        System.out.print(((Val)hash1.get("foo_1")).val    + " " +
                         ((Val)hash1.get("foo_9999")).val + " " +
                         ((Val)hash2.get("foo_1")).val    + " " +
                         ((Val)hash2.get("foo_9999")).val + "\n");
        if(contin){
            try{
                Thread trial = new Thread(new heapsort(contin, n));
                trial.start();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
}
