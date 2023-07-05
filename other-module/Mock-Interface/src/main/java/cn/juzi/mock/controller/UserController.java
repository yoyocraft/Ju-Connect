package cn.juzi.mock.controller;

import com.juzi.sdk.model.entity.User;
import org.springframework.web.bind.annotation.*;



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
    public String getUserByJson(@RequestBody User user) {
        return "【POST && JSON】用户名称 " + user.getName();
    }

}
