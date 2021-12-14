package com.kolon.biotech.web;

import com.kolon.biotech.domain.user.Member;
import com.kolon.biotech.service.UserService;
import com.kolon.biotech.web.dto.MemberDto;
import com.kolon.biotech.web.dto.ResultJsonPagingDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.LocaleResolver;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
public class UserRestController {

    @Autowired
    private MessageSourceAccessor messageSourceAccessor;

    @Autowired
    private LocaleResolver localeResolver;

    private final UserService userService;


    @PostMapping("/loginSameCheck")
    public boolean loginSameCheck(@ModelAttribute Member member){
        log.debug("=========loginSameCheck============");
        Member _member = userService.getLoginId(member);

        boolean _bol = true;
        if(member.getId() != null && member.getId() != 0){
            if(_member.getLoginId() != null && !"".equals(_member.getLoginId())){
                _bol = false;
            }
        }

        return _bol;
    }

    @PostMapping(value="/userListAjax")
    public Page<Member> userListAjax(@PageableDefault Pageable pageable, Model model){
        log.debug("=========userListAjax============");
        Page<Member> memberList = userService.getMemberList(pageable);

        return memberList;
    }

    @PostMapping(value="/userLoginReset")
    public Member userLoginReset(@RequestParam(value="id") Integer id){

        return null;
    }

    @PostMapping(value="/userChangePass")
    public ResultJsonPagingDto userChangePass(@AuthenticationPrincipal MemberDto memberDto,@ModelAttribute Member member){
        return null;
    }

    @PostMapping(value="/superUserChangePass")
    public ResultJsonPagingDto superUserChangePass(@AuthenticationPrincipal MemberDto memberDto, @ModelAttribute Member member){
        return null;
    }

    @PostMapping("/userDelete")
    public ResultJsonPagingDto delete(@RequestParam(value = "deleteList") List<Integer> deleteList){
        ResultJsonPagingDto dto = new ResultJsonPagingDto();
        try{
            userService.delete(deleteList);

            dto.setSuccess(true);
            dto.setMessage(messageSourceAccessor.getMessage("deleteok"));

        }catch(Exception e){
            log.error("delete",e.getMessage());
            dto.setSuccess(false);
            dto.setMessage(messageSourceAccessor.getMessage("deletefail"));

        }

        return dto;
    }
}
