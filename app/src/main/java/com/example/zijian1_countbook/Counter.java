package com.example.zijian1_countbook;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by zijian on 2017-09-29.
 */

/**
 * This class is the counter book
 * with name,init value, current value and comment
 */
public class Counter {
    private String name;
    private Date date;
    private int init_value;
    private int current_value;
    private String comment;

    /**
     * constructor
     * @param Name the name of book
     * @param n initial value
     * @param comment   comments for book
     */
    public Counter(String Name,int n,String comment) {
        this.name = Name;
        this.date = Calendar.getInstance().getTime();
        this.comment = comment;
        this.init_value = n;
        this.current_value = n;
    }

    /**
     * Increase the current value by 1
     */

    public void increase(){
        this.date = Calendar.getInstance().getTime();
        this.current_value += 1;
    }

    /**
     * Decrease the current value by 1
     */
    public void decrease(){
        if (this.current_value > 0) {
            this.date = Calendar.getInstance().getTime();

            this.current_value -= 1;
        }
    }

    /**
     * Set the initial value to N
     * @param N
     */
    public void setInit(int N){
        this.date = Calendar.getInstance().getTime();
        this.init_value = N;
    }

    /**
     * Set the name of the counter to N
     * @param N
     */

    public void setName(String N){
        this.date = Calendar.getInstance().getTime();
        this.name=N;
    }

    /**
     * Set the comment of the counter to N
     * @param N
     */
    public void setComment( String N){
        this.date = Calendar.getInstance().getTime();
        this.comment=N;
    }

    /**
     * Set the current value to N
     * @param N
     */

    public void setValue(int N){
        this.date = Calendar.getInstance().getTime();
        this.current_value = N;
    }
    public String getName(){
        return this.name;
    }
    public int getInit(){
        return this.init_value;
    }
    public int getCurr(){
        return this.current_value;
    }
    public String getComment(){
        return this.comment;
    }


    public String toString() {
        return "Name: "+this.name+" \n" +
                "initial: "+this.init_value+" \n" +
                "current value: "+this.current_value+" \n" +
                "last modified: "+this.date + " \n" +
                "comment: "+ this.comment;
    }
}
