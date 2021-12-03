package com.kolon.biotech.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "Result Json Set Dto")
public class ResultJsonPagingDto {
    @ApiModelProperty(value = "서비스 성공 여부", required = true)
    private boolean success;

    private String message;

    private int recordsTotal;

    private int recordsFiltered;

    @ApiModelProperty(value = "처리 결과 DTO")
    private Object data;

    public ResultJsonPagingDto() {
        this.success = false;
        this.recordsTotal = 0;
        this.recordsFiltered = 0;
    }
}
