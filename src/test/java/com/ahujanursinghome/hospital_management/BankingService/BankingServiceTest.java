package com.ahujanursinghome.hospital_management.BankingService;


import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.ahujanursinghome.hospital_management.BankAccount.BankAccountService;



public class BankingServiceTest {
    @AfterAll 
    public static void checkOut(){
        System.out.println("closing all instance and releasing all resources");
    }
    @BeforeAll 
    public static void checkIn(){
        System.out.println("setting up the details and variable");
    }
    // test method of add teo number using junit
    @Test 
    public void sumTest() {
        System.out.println("running the test after setting details");
        BankAccountService b = new BankAccountService();
        int sum = b.sum(10, 30);
        int expectedResult = 40;

        Assertions.assertEquals(expectedResult,sum,"pass the test");
    }

}
