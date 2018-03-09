package com.qualit.mobile.utils.Reports;

import org.testng.annotations.*;

public class testngtest {

    @Test
    public void test1(){
        System.out.println("Test");
    }

    @BeforeClass
    public void Beforeclass(){
        System.out.println("Beforeclass");
    }

    @AfterClass
    public void Afterclass(){
        System.out.println("Afterclass");
    }

    @AfterSuite
    public void AfterSuite(){
        System.out.println("AfterSuite");
    }

    @BeforeSuite
    public void BeforeSuite(){
        System.out.println("BeforeSuite");
    }

    @AfterTest
    public void AfterTest(){
        System.out.println("AfterTest");
    }

    @BeforeTest
    public void BeforeTest(){
        System.out.println("BeforeTest");
    }

    @BeforeMethod
    public void BeforeMethod(){
        System.out.println("BeforeMethod");
    }

    @AfterMethod
    public void AfterMethod(){
        System.out.println("AfterMethod");
    }


}
