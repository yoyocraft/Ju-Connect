package com.juzi.model.dto.interface_info;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author codejuzi
 */
@Data
@NoArgsConstructor
public class InterfaceGatewayQueryRequest implements Serializable {

    private static final long serialVersionUID = -7090004539419332024L;

    private String apiUrl;
    private Integer apiMethod;

    public InterfaceGatewayQueryRequest(String apiUrl, Integer apiMethod) {
        this.apiUrl = apiUrl;
        this.apiMethod = apiMethod;
    }
}
