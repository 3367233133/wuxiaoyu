package com.niuma.rainapiorder.model.dto;

import com.niuma.rainapicommon.common.PageRequest;
import lombok.Data;

import java.io.Serializable;

/**
 * @author niuma
 * @create 2023-05-07 20:30
 */
@Data
public class OrderQueryRequest extends PageRequest implements Serializable {
    private String type;
}
