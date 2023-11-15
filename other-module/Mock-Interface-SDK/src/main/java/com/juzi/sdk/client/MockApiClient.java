package com.juzi.sdk.client;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.juzi.sdk.model.entity.User;
import com.juzi.sdk.utils.SignUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * @author codejuzi
 */
public class MockApiClient {

    private final String accessKey;
    private final String secretKey;

    public MockApiClient(String accessKey, String secretKey) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
    }

    //    private static final String URL_PREFIX = "http://localhost:8111/api";
    private static final String GATEWAY_PREFIX = "http://localhost:10000/api";

    public String getNameByGet(String name) {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("name", name);

        return HttpUtil.get(GATEWAY_PREFIX + "/user/name", paramMap);
    }

    public String getNameByPost(String name) {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("name", name);

        return HttpUtil.post(GATEWAY_PREFIX + "/user/name", paramMap);
    }

    @SuppressWarnings("resource")
    public String getUserByJson(User user) {
        String json = JSONUtil.toJsonStr(user);
        HttpResponse httpResponse = HttpRequest.post(GATEWAY_PREFIX + "/user")
                .addHeaders(getHeaders(json))
                .body(json)
                .execute();
        return httpResponse.body();
    }

    private Map<String, String> getHeaders(String body) {
        Map<String, String> headers = new HashMap<>();
        headers.put("accessKey", accessKey);
        headers.put("nonce", RandomUtil.randomNumbers(20));
        try {
            headers.put("body", URLEncoder.encode(body, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        headers.put("timestamp", String.valueOf(System.currentTimeMillis() / 1000));
        headers.put("sign", SignUtils.genSign(body, secretKey));
        return headers;
    }
}
