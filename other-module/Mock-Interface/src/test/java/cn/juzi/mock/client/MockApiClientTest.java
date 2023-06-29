package cn.juzi.mock.client;


import com.juzi.sdk.client.MockApiClient;
import com.juzi.sdk.model.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * @author codejuzi
 */
@SpringBootTest
class MockApiClientTest {

    @Resource
    private MockApiClient mockApiClient;

    @Test
    void testClient() {

        String name = "橘子";

        String nameByGet = mockApiClient.getNameByGet(name);
        System.out.println("nameByGet = " + nameByGet);

        String nameByPost = mockApiClient.getNameByPost(name);
        System.out.println("nameByPost = " + nameByPost);

        User user = new User(name);
        String userByJson = mockApiClient.getUserByJson(user);
        System.out.println("userByJson = " + userByJson);
    }
}