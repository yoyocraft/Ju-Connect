package com.juzi.model.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author codejuzi
 */
@Data
public class SingleIdRequest implements Serializable {

    private static final long serialVersionUID = 2526147245724638256L;

    private Long id;
}
