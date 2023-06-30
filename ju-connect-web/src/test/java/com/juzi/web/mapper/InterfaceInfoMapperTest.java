package com.juzi.web.mapper;

import com.juzi.common.constants.InterfaceInfoConstants;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author codejuzi
 */
@SpringBootTest
class InterfaceInfoMapperTest {

    @Resource
    private InterfaceInfoMapper interfaceInfoMapper;

    @Test
    void setApiStatusBoolean() {
        Long id = 1L;
        boolean updateRes = interfaceInfoMapper.setApiStatusBoolean(id, InterfaceInfoConstants.ON_LINE);
        assertTrue(updateRes);
    }
}