package com.hao.socket.data;

import org.json.JSONException;
import org.json.JSONObject;


public class HandShakeBean extends DefaultSendBean {

    public HandShakeBean() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("cmd", 54);
            content = jsonObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}
