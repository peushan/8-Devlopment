package com.qualit.mobile.utils.OpenSTF;

import com.google.gson.*;
import okhttp3.*;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * Connect / disconnect device using STF api
 */

public class DeviceConnectSTF {
    private OkHttpClient client;
    private JsonParser jsonParser;
    private STFService stfService;
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static final Logger LOGGER = Logger.getLogger(Class.class.getName());

    public DeviceConnectSTF(STFService stfService) {
        this.client = new OkHttpClient();
        this.jsonParser = new JsonParser();
        this.stfService = stfService;
    }

    public boolean connectDevice(String deviceSerial) {
        Request request = new Request.Builder()
                .addHeader("Authorization", "Bearer " + stfService.getAuthToken())
                .url(stfService.getStfUrl() + "devices/" + deviceSerial)
                .build();
        Response response;
        try {
            response = client.newCall(request).execute();
            JsonObject jsonObject = jsonParser.parse(response.body().string()).getAsJsonObject();

            if (!isDeviceFound(jsonObject)) {
                return false;
            }

            JsonObject deviceObject = jsonObject.getAsJsonObject("device");
            JsonElement ownerElement = deviceObject.get("owner");
            boolean owner = !(ownerElement instanceof JsonNull);

            if (owner) {
                LOGGER.severe("The Device :<<" + deviceSerial + ">> is currently using by :" + ((JsonObject) ownerElement).get("name"));
                return false;
            }
            return addDeviceToUser(deviceSerial);
        } catch (IOException e) {
            throw new IllegalArgumentException("STF service is unreachable", e);
        }
    }

    private boolean addDeviceToUser(String deviceSerial) {
        RequestBody requestBody = RequestBody.create(JSON, "{\"serial\": \"" + deviceSerial + "\"}");
        Request request = new Request.Builder()
                .addHeader("Authorization", "Bearer " + stfService.getAuthToken())
                .url(stfService.getStfUrl() + "user/devices")
                .post(requestBody)
                .build();
        Response response;
        try {
            response = client.newCall(request).execute();
            JsonObject jsonObject = jsonParser.parse(response.body().string()).getAsJsonObject();

            if (!isDeviceAdded(jsonObject)) {
                return false;
            }
            return userInformation(deviceSerial);
        } catch (IOException e) {
            throw new IllegalArgumentException("STF service is unavailable", e);
        }
    }

    private boolean userInformation(String deviceSerial) {

        Request request = new Request.Builder()
                .addHeader("Authorization", "Bearer " + stfService.getAuthToken())
                .url(stfService.getStfUrl() + "user/devices/" + deviceSerial)
                .build();
        Response response;
        try {
            response = client.newCall(request).execute();
            JsonObject jsonObject = jsonParser.parse(response.body().string()).getAsJsonObject();

            if (!isUserFound(jsonObject)) {
                return false;
            }
            JsonObject deviceObject = jsonObject.getAsJsonObject("device");
            JsonElement model = deviceObject.get("model");
            JsonElement ownerElement = deviceObject.get("owner");

            LOGGER.info("The User : " + ((JsonObject) ownerElement).get("name") + " has locked the device <<" + deviceSerial + ">> successfully");
            return true;

        } catch (IOException e) {
            throw new IllegalArgumentException("STF service is unavailable", e);
        }

    }

    public boolean releaseDevice(String deviceSerial) {
        Request request = new Request.Builder()
                .addHeader("Authorization", "Bearer " + stfService.getAuthToken())
                .url(stfService.getStfUrl() + "user/devices/" + deviceSerial)
                .delete()
                .build();
        Response response;
        try {
            response = client.newCall(request).execute();
            JsonObject jsonObject = jsonParser.parse(response.body().string()).getAsJsonObject();

            if (!isDeviceFound(jsonObject)) {
                return false;
            }

            LOGGER.info("The device <" + deviceSerial + "> is released successfully");
            return true;
        } catch (IOException e) {
            throw new IllegalArgumentException("STF service is unreachable", e);
        }
    }

    private boolean isDeviceFound(JsonObject jsonObject) {
        if (!jsonObject.get("success").getAsBoolean()) {
            LOGGER.severe("Device not found");
            return false;
        }
        return true;
    }

    private boolean isDeviceAdded(JsonObject jsonObject) {
        if (!jsonObject.get("success").getAsBoolean()) {
            LOGGER.severe("Device is not added to User");
            return false;
        }
        return true;
    }

    private boolean isUserFound(JsonObject jsonObject) {
        if (!jsonObject.get("success").getAsBoolean()) {
            LOGGER.severe("User is not Available");
            return false;
        }
        return true;
    }


}
