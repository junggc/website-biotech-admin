package com.kolon.biotech.web.dto;

import lombok.Data;

@Data
public class SearchDto {
    private String searchText;
    private String searchStartDate;
    private String searchEndDate;
    private String page;
    private String size;
    private String id;
}
