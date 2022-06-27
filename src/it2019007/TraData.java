/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it2019007;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 *
 * @author isuru
 */
class TraData {
    
    private int primaryKey;
    private String string0;
    private String string1;
    private String string2;
    private String string3;
    private String string4;
    private String string5;
    private String string6;
    private String string7;
    private String string8;
    private String string9;
    private String string10;
    private int money;
    
    
    TraData(int uniNo, String date, String time, String handdleBy, String method, int amount, String accNumber){
        
        this.primaryKey = uniNo;
        this.string0 = date;
        this.string1 = time;
        this.string2 = handdleBy;
        this.string3 = method;
        this.money = amount;
        this.string4 = accNumber;    
    }
    
    TraData(String acNo, String name, String age, String gender, String telephone, String address,String State, String acType, int money){
        
        this.string0 = acNo;
        this.string1 = name; 
        this.string2 = age;
        this.string3 = gender;
        this.string4 = telephone;
        this.string5 = address;
        this.string6 = State;   
        this.string7 = acType; 
        this.money = money;
        
        
    }

    
    TraData(String s1, String s2, String s3, String s4){
        
        this.string0 = s1;
        this.string1 = s2;
        this.string2 = s3;
        this.string3 = s4;   
    }
    
    public int getprimaryKey(){
       return this.primaryKey;
    }
    
    public String getString0(){
       return this.string0;
    }
    
    public String getString1(){
       return this.string1;
    }
    
    public String getString2(){
       return this.string2;
    }
    
    public String getString3(){
       return this.string3;
    }
    
    public String getString4(){
       return this.string4;
    }
    
    public String getString5(){
       return this.string5;
    }
    
    public String getString6(){
       return this.string6;
    }
    
    public String getString7(){
       return this.string7;
    }
    
    public String getString8(){
       return this.string8;
    }
    
    public String getString9(){
       return this.string9;
    }
    
    public int getAmount(){
       return this.money;
    }
    
    
    
   
    
}
