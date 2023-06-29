package cn.juzi.mock.controller;

import com.juzi.sdk.model.entity.User;
import com.juzi.sdk.utils.SignUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.Instant;


/**
 * @author codejuzi
 */
@RequestMapping("/user")
@RestController
public class UserController {

    @GetMapping("/name")
    public String getNameByGet(String name) {
        return "【GET】你的名字是" + name;
    }

    @PostMapping("/name")
    public String getNameByPost(@RequestParam String name) {
        return "【POST】你的名字是" + name;
    }

    @PostMapping
    public String getUserByJson(@RequestBody User user, HttpServletRequest request) {
        // 从请求头中获取参数
        String accessKey = request.getHeader("accessKey");
        String nonce = request.getHeader("nonce");
        String timestamp = request.getHeader("timestamp");
        String body = URLDecoder.decode(request.getHeader("body"), StandardCharsets.UTF_8);
        String sign = request.getHeader("sign");

        // 实际上是根据ak去数据库查询是否分配给用户
        if (!"aaa".equals(accessKey)) {
            throw new RuntimeException("无权限");
        }

        // 校验随机数，实际上还要查看服务器端是否有这个随机数
        if (nonce.length() != 20) {
            throw new RuntimeException("无权限");
        }

        // 校验时间戳，和当前时间相差5分钟以内
        if (!validateTimestamp(timestamp)) {
            throw new RuntimeException("无权限");
        }

        // 校验sign， 实际上的sk是从数据库中查出来的
        String serverSign = SignUtils.genSign(body, "bbb");
        if (!serverSign.equals(sign)) {
            throw new RuntimeException("无权限");
        }

        return "【POST && JSON】用户名称 " + user.getName();
    }

    private boolean validateTimestamp(String timestamp) {
        long currentTime = Instant.now().getEpochSecond();
        long inputTime = Long.parseLong(timestamp);

        long timeDifference = currentTime - inputTime;
        long timeDifferenceInMinutes = Math.abs(timeDifference) / 60;

        return timeDifferenceInMinutes <= 5;
    }
}
