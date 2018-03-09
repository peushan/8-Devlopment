package com.qualit.mobile;

import com.qualit.mobile.common.BaseMobile;
import com.qualit.mobile.mobilepages.DashBoard;
import com.qualit.mobile.mobilepages.DeviceDataScreen;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.*;


@Epic("Test Bosch Touch 1 DashBoard")
@Feature("DashBoard")

public class TraceMessangerTest1 extends BaseMobile {

    @Test(priority = 1, description = "Verify the Tab navigation on Bosch Touch 1 App")
    @Severity(SeverityLevel.MINOR)
    @Description("Test Description : Verify the Tab navigation on Bosch Touch 1 App")
    @Story("Bosch Tab Navigation")
    public void testing1(ITestContext ctx) throws Exception {
        String testName = ctx.getCurrentXmlTest().getName();

        DashBoard dashBoard = new DashBoard(testName);
        dashBoard.navigateTabs();
        DeviceDataScreen deviceDataScreen = dashBoard.navigateToDeviceData();
        deviceDataScreen.DeviceDataValidation();
        //Assert.assertEquals(1, 2);

    }

    @Test(priority = 2, description = "Verify the Hexadecimal Character entering on Bosch Touch 1 App")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test Description : Verify the Hexadecimal Character entering on Bosch Touch 1 App")
    @Story("Bosch Hexadecimal Character Entering ")
    public void testing2(ITestContext ctx) throws Exception {
        String testName = ctx.getCurrentXmlTest().getName();

        DashBoard dashBoard = new DashBoard(testName);
        dashBoard.typeHexaDecimal();
        Assert.assertTrue(dashBoard.sendButtonDisplayed(), "Send button is not Displayed");


    }


}