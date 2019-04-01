// Copyright (C) 2018 Meituan
// All rights reserved
package design;

/**
 * @author manatea
 * @version 1.0
 * @created 2018/2/5 PM5:52
 **/
public class Reconstruct {

    private final static int[] BASE={0,0,70,14,225};
    private final static double[] RADIO={0,0.14,0.15,0.16,0.17};
    private final static int[] INCOME_THRESHOLD={2200,2700,3200,3700,4200};
    private final static int[] BASE_THRESHOLD={0,2200,2700,3200,3700};

    private double getTax(int income){
        int handleCase = getHandleCase(income);
        System.out.println(BASE[handleCase] +" + "+ "" + RADIO[handleCase]+"*"+ income+"-" +BASE_THRESHOLD[handleCase]);
        return BASE[handleCase] + (RADIO[handleCase]*(income-BASE_THRESHOLD[handleCase]));
    }

    private int getHandleCase(int income){
        for(int i = 0 ;i<INCOME_THRESHOLD.length;i++){
            if(income<=INCOME_THRESHOLD[i]){
                return i;
            }
        }
        return INCOME_THRESHOLD.length-1;
    }

    public static void main(String[] args){
        Reconstruct r = new Reconstruct();
        System.out.println(r.getTax(2900));
    }

}