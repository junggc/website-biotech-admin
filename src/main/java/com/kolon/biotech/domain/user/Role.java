package com.kolon.biotech.domain.user;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@AllArgsConstructor
@Getter
public enum  Role {

    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER");


    private String value;

}
