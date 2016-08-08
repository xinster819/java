package com.sohu.mpV2.service.third;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.sohu.mpV2.utils.HttpClientUtils;
import com.sohu.mpV2.utils.SignatureUtil;

@Service
public class RelationNotifyService {

    public static String RELATION_NOTIFY_NEW_ACCOUNT = "http://life.sohuno.com/api/activemp";

    public boolean notifyNewAccount(String passport, Integer mpId) {
        if (StringUtils.isEmpty(passport) || null == mpId || 0 >= mpId.intValue()) {
            return false;
        }
        String url = RELATION_NOTIFY_NEW_ACCOUNT + "/" + passport + "/" + mpId;
        Map<String, String> params = new HashMap<String, String>();
        params.put("passport", passport);
        params.put("mpid", String.valueOf(mpId));
        params.put("appkey", "30000006");
        String saltValue = "86195db5986695fd112fd9d09ccbc3c7";
        String sig = SignatureUtil.getAppSignature(params, saltValue);
        if (StringUtils.isEmpty(sig)) {
            return false;
        }
        params.put("so_sig", sig);
        try {
            String response = HttpClientUtils.post(url, params, null);
            if (StringUtils.isNotEmpty(response)) {
                JSONObject JsonRes = JSONObject.parseObject(response);
                if (null != JsonRes) {
                    if (null != JsonRes.getString("code") && "0".equalsIgnoreCase(JsonRes.getString("code"))) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
        }
        return false;
    }

}