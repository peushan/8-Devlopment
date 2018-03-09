package com.qualit.mobile.common;

import com.qualit.mobile.utils.OpenSTF.DeviceConnectSTF;
import com.qualit.mobile.utils.OpenSTF.STFService;
import io.appium.java_client.android.AndroidDriver;
import org.apache.log4j.Logger;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestContext;
import org.testng.annotations.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;

public class BaseMobile {

    public static AndroidDriver androidDriver;
    public Logger log;
    private DesiredCapabilities capabilities;
    private ArrayList<String> list = new ArrayList<>();
    private DeviceConnectSTF deviceConnectSTF;
    private String Stf_URL = "http://192.168.1.204:7100";
    private String Token = "d6629861d92d453ca9d61e79a083c6a7afc209c704614c4d980c5e239a2f9f32";

    private void connectToStfDevice() throws IOException, URISyntaxException {
        STFService stfService = new STFService(Stf_URL, Token);
        deviceConnectSTF = new DeviceConnectSTF(stfService);
        for (int i = 0; i < list.size(); i++) {
            deviceConnectSTF.connectDevice(list.get(i));
        }
    }

    @BeforeClass
    @Parameters({"Port", "UDID"})
    public void appiumNodeSetup(String port, String udid, ITestContext ctx) {

        String testName = ctx.getCurrentXmlTest().getName();
        log = Logger.getLogger(BaseMobile.class + " " + testName);

        synchronized (this) {
            try {
                capabilities = new DesiredCapabilities();
                capabilities.setCapability("app", "Downloads/MobilePOC/src/test/resources/MessageTracer-58.apk");
                capabilities.setCapability("app-package", "com.eightdevelopment.touchoneb.messagetracer");
                capabilities.setCapability("app-activity", "com.eightdevelopment.touchoneb.messagetracer/.MainActivity");
                capabilities.setCapability("platformName", "Android");
                capabilities.setCapability("deviceName", "Mini");
                capabilities.setCapability("udid", udid);

                list.add(udid);
                //connectToStfDevice();

                androidDriver = new AndroidDriver(new URL("http://127.0.0.1:" + port + "/wd/hub"), capabilities);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            //} catch (URISyntaxException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            log.info("Appium Node Server has Started on Port number : " + port + " and Attached to Device : " + udid);
            log.info(androidDriver.getSessionId());
        }

    }

    @AfterClass
    public void releaseDevice() {
        for (int i = 0; i < list.size(); i++) {
            //deviceConnectSTF.releaseDevice(list.get(i));
        }
    }

    @AfterSuite
    public void teardown() {
        if (androidDriver != null) {
            androidDriver.quit();
        }
    }


}
