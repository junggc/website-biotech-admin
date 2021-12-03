package com.kolon.biotech.web.dto;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;

@Data
public class MemberDto implements UserDetails {
    private String username;
    private String password;
    private boolean isEnabled;
    private boolean isAccountNonExpired;
    private boolean isAccountNonLocked;
    private boolean isCredentialsNonExpired;
    private Collection<? extends GrantedAuthority> authorities;
    private Integer id;
    private String auth;
    private String mainAuthority;
    private String noticeAuthority;
    private String logAuthority;
    private LocalDateTime loginDate;

}
