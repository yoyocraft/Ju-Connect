package com.juzi.model.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author codejuzi
 */
@Data
@NoArgsConstructor
public class UserSignVO implements Serializable {

    private static final long serialVersionUID = 3007773209827695968L;

    private Long id;
    private String accessKey;
    private String secretKey;

    public UserSignVO(Long id, String accessKey, String secretKey) {
        this.id = id;
        this.accessKey = accessKey;
        this.secretKey = secretKey;
    }
}
