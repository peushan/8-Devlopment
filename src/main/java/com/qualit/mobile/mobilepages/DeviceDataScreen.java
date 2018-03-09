package com.qualit.mobile.mobilepages;

import com.qualit.mobile.common.BaseMobileAction;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.apache.log4j.Logger;
import org.openqa.selenium.support.PageFactory;

import java.util.concurrent.TimeUnit;

public class DeviceDataScreen extends BaseMobileAction {

    protected Logger log;

    @AndroidFindBy(id = "com.eightdevelopment.touchoneb.messagetracer:id/device_info_serial_number")
    MobileElement deviceInfo;

    public DeviceDataScreen(String testName) {
        PageFactory.initElements(new AppiumFieldDecorator(androidDriver, 90, TimeUnit.SECONDS), this);
        log = Logger.getLogger(DeviceDataScreen.class + " " + testName);
    }

    public void DeviceDataValidation(){
        log.info("Device Data Validation screen");
        log.info(deviceInfo.getText());
    }
}
