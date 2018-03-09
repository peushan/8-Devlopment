package com.qualit.mobile.mobilepages;

import com.qualit.mobile.common.BaseMobileAction;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.apache.log4j.Logger;
import org.openqa.selenium.support.PageFactory;

import java.util.concurrent.TimeUnit;

public class DashBoard extends BaseMobileAction {

    protected Logger log;
    String testName;

    // Locators
    @AndroidFindBy(xpath = "//android.widget.TextView[@text='KEYPAD']")
    MobileElement tabKeyPad;
    @AndroidFindBy(xpath = "//android.widget.TextView[@text='DEVICE DATA']")
    MobileElement tabDeviceData;
    @AndroidFindBy(xpath = "//android.widget.TextView[@text='MESSAGE SENDER']")
    MobileElement tabMessageSender;
    @AndroidFindBy(xpath = "//android.widget.ImageButton[@index='0']")
    MobileElement navigationDrawer;
    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Exit']")
    MobileElement btnExit;
    @AndroidFindBy(xpath = "//android.widget.TextView[@index='3']")
    MobileElement txtMessager;
    @AndroidFindBy(id = "com.eightdevelopment.touchoneb.messagetracer:id/btn_4")
    MobileElement btn4;
    @AndroidFindBy(id = "com.eightdevelopment.touchoneb.messagetracer:id/btn_5")
    MobileElement btn5;
    @AndroidFindBy(id = "com.eightdevelopment.touchoneb.messagetracer:id/btn_6")
    MobileElement btn6;
    @AndroidFindBy(id = "com.eightdevelopment.touchoneb.messagetracer:id/btn_7")
    MobileElement btn7;
    @AndroidFindBy(id = "com.eightdevelopment.touchoneb.messagetracer:id/btn_8")
    MobileElement btn8;
    @AndroidFindBy(id = "com.eightdevelopment.touchoneb.messagetracer:id/btn_send")
    MobileElement btnSend;

    public DashBoard(String testName) {
        PageFactory.initElements(new AppiumFieldDecorator(androidDriver, 90, TimeUnit.SECONDS), this);
        log = Logger.getLogger(DashBoard.class + " " + testName);
        this.testName = testName;
    }

    public void navigateTabs() throws Exception {

        tapOn(tabDeviceData);
        log.info("Tap on the Device Data");
        tapOn(tabKeyPad);
        log.info("Tap on the KeyPad");
        tapOn(tabMessageSender);
        log.info("Tap on the MessageSender");
        tapOn(navigationDrawer);
        log.info("Tap on the Navigation Drawer");
        //tapOn(btnExit);
        log.info("Tap on the Exit button");

    }

    public void typeHexaDecimal() throws Exception {

        btn4.tap(1, 2);
        log.info("Tap on the button 4");
        btn5.tap(1, 2);
        log.info("Tap on the button 5");
        btn6.tap(1, 2);
        log.info("Tap on the button 6");
        btnSend.tap(1, 2);
        log.info("Tap on the Send");

        log.info(text(txtMessager));

        btn7.tap(1, 2);
        log.info("Tap on the button 7");
        btn8.tap(1, 2);
        log.info("Tap on the button 8");
        btnSend.tap(1, 2);
        log.info("Tap on the Send");
    }

    public boolean sendButtonDisplayed() {
        return btnSend.getText().equals("SEND");
    }

    public DeviceDataScreen navigateToDeviceData(){
        tapOn(tabDeviceData);
        log.info("Tap on the Device Data");
        return new DeviceDataScreen(testName);
    }
}
