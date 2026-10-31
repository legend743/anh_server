package com.ahujanursinghome.hospital_management.BankAccount;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.springframework.stereotype.Component;
@Component 
public class BankcAccountProcess {
private int creditAmount=1000;
private final Lock lock=new ReentrantLock();
public void withdraw(int amt){
    System.out.println(Thread.currentThread().getName()+"Cuurent Thread Name");
    try{
        if(lock.tryLock()){
            System.out.println("Lock is free hence withraw is in process...");
            creditAmount-=amt;
            System.out.println("current amount"+creditAmount);
        }else{
            System.out.println("thread is not free please come after some time....");
        }
    }catch(Exception e){

    }
}

}
