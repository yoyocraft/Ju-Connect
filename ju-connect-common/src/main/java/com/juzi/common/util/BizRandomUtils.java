package com.juzi.common.util;

import com.juzi.common.constants.UserConstants;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

/**
 * @author codejuzi
 */
public class BizRandomUtils {

    private static final String[] AVATAR_ARR = {
            "https://regengbaike.com/uploads/20230222/1bff61de34bdc7bf40c6278b2848fbcf.jpg",
            "https://img.58tg.com/up/allimg/tx29/08151048279757843.jpg",
            "https://img.58tg.com/up/allimg/tx29/07010018221735575.jpg",
            "https://upen.caup.net/ai_pics_mj/202303/1677952366325269.jpg",
            "https://p.qqan.com/up/2022-9/16635499767793089.jpg",
            "https://photo.16pic.com/00/53/26/16pic_5326745_b.jpg",
            "https://img01.yohoboys.com/contentimg/2018/11/22/13/0187be5a52edcdc999f749b9e24c7815fb.jpg",
    };

    public static String genRandomNickName() {
        return UserConstants.DEFAULT_NICK_PREFIX +
                RandomStringUtils.randomAlphabetic(UserConstants.DEFAULT_NICK_SUFFIX_LEN);
    }

    public static String genRandomAvatar() {
        return AVATAR_ARR[RandomUtils.nextInt(0, AVATAR_ARR.length - 1)];
    }

    public static String genRandomSalt() {
        return RandomStringUtils.randomAlphabetic(UserConstants.SALT_LEN);
    }

    public static Integer genRandomGender() {
        return RandomUtils.nextInt(0, 2);
    }


}
