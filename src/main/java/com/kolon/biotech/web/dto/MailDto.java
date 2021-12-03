package com.kolon.biotech.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MailDto {
    private String fromAddress;
    private String toAddress;
    private String title;
    private String message;
}
