package com.qualit.mobile.common;

import io.appium.java_client.MobileElement;
import io.appium.java_client.SwipeElementDirection;
import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class BaseMobileAction extends BaseMobile {

    protected final static Logger log = Logger.getLogger(BaseMobileAction.class);

    public String currentActivity() throws Exception {
        log.info("Getting current activity name...");

        try {
            return androidDriver.currentActivity();
        } catch (final NoSuchSessionException e) {
            throw new Exception("Server Session has been stopped.", e);
        }
    }

    public boolean isLocked() throws Exception {
        log.info("Checking if device is locked...");
        try {
            return androidDriver.isLocked();
        } catch (final NoSuchSessionException e) {
            throw new Exception("Server Session has been stopped.", e);
        }
    }

    public void captureScreenshot(final String path) throws Exception {
        final String msg = "Capturing screenshot and saving at...";
        log.info(String.format(msg, path));
        try {
            final File srcFiler = ((TakesScreenshot) androidDriver).getScreenshotAs(OutputType.FILE);
            try {
                FileUtils.copyFile(srcFiler, new File(path));
            } catch (final IOException e) {
                log.error("Error occurred while capturing screensshot..." + e);

            }
        } catch (final NoSuchSessionException e) {
            throw new Exception("Server Session has been stopped.", e);
        }
    }

    public void enterText(MobileElement element, String text) throws Exception {
        checkDeviceElementEnabled(element);
        try {
            element.clear();
            element.sendKeys(text);
        } catch (final NoSuchSessionException e) {
            throw new Exception("Server Session has been stopped.", e);

        }
    }

    public void checkDeviceElementEnabled(MobileElement element) {
        if (!element.isEnabled()) {
            final String msg = "Device element " + element + " is disabled.";
            log.info(msg);
        }
    }

    protected void tapOn(MobileElement element) {
        try {
            if (element.isDisplayed() && element.isEnabled()) {
                element.click();
            } else {
                sleep(3);
                element.click();
            }
        } catch (NoSuchElementException f) {
            log.error("\nElement not visible in screen : " + f.getMessage());
            throw new ElementNotVisibleException("Element not displayed in screen.");
        } catch (WebDriverException e) {
            try {
                element.click();
            } catch (Exception d) {
                log.error("click(element) failed with error : " + d.getMessage());
            }
        } catch (Exception g) {
            log.error("click(element) failed with error : " + g.getMessage());
        }
    }

    protected void sleep(double seconds) {
        try {
            Thread.sleep((long) (seconds * 1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void swipe(MobileElement element, final SwipeElementDirection direction, final int delay) throws Exception {
        checkDeviceElementEnabled(element);
        log.info(String.format("Swiping on element with ms delay...", direction, delay));
        try {
            element.swipe(direction, delay);
        } catch (final NoSuchSessionException e) {
            throw new Exception("Server Session has been stopped.", e);
        }
    }

    public String text(MobileElement element) throws Exception {
        log.info(String.format("Getting text on element..."));
        try {
            return element.getText();
        } catch (final NoSuchSessionException e) {
            throw new Exception("Server Session has been stopped.", e);
        }
    }

}
