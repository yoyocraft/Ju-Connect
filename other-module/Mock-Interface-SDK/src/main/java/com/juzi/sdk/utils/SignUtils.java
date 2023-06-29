package com.juzi.sdk.utils;

import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.crypto.digest.Digester;

/**
 * @author codejuzi
 */
public class SignUtils {

    // 定义分隔符
    private static final String SEPARATOR = "_";

    public static String genSign(String body, String secretKey) {
        Digester digester = DigestUtil.digester(DigestAlgorithm.SHA256);
        String content = body + SEPARATOR + secretKey;
        return digester.digestHex(content);
    }
}
