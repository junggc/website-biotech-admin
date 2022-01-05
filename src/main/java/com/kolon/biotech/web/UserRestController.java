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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Locale;

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
    public ResultJsonPagingDto loginSameCheck(@ModelAttribute Member member){
        log.debug("=========loginSameCheck============");
        ResultJsonPagingDto resultJsonPagingDto = new ResultJsonPagingDto();

        String[] idlist = {"adm", "admin", "manager", "master", "system", "root", "administrator"};
        boolean idcheck = false;

        for(String id : idlist){
            if(member.getLoginId().toLowerCase().contains(id)){
                idcheck = false;
                break;
            }else{
                idcheck = true;
            }
        }

        if(idcheck){
            Member _member = userService.getLoginId(member);

            boolean _bol = true;
            if(_member.getId() != null && _member.getId() != 0){
                if(_member.getLoginId() != null && !"".equals(_member.getLoginId())){
                    _bol = false;
                }
            }

            resultJsonPagingDto.setSuccess(_bol);
            if(_bol){
                resultJsonPagingDto.setMessage("사용가능한 아이디입니다.");
            }else{
                resultJsonPagingDto.setMessage("이미 사용중인 아이디입니다.");
            }
        }else{
            resultJsonPagingDto.setSuccess(false);
            resultJsonPagingDto.setMessage("사용할 수 없는 단어가 포함되어있습니다.\n사용불가 단어: adm, admin, manager, master, system, root, administrator");
        }


        return resultJsonPagingDto;
    }

    @PostMapping(value="/userListAjax")
    public Page<Member> userListAjax(@PageableDefault Pageable pageable, Model model){
        log.debug("=========userListAjax============");
        Page<Member> memberList = userService.getMemberList(pageable);

        return memberList;
    }

    @PostMapping(value="/userLoginReset")
    public ResultJsonPagingDto userLoginReset(@RequestParam(value="id") Integer id,@AuthenticationPrincipal MemberDto memberDto, HttpServletRequest request, HttpServletResponse response) throws Exception{
        log.debug("=========userLoginReset============");
        ResultJsonPagingDto resultJsonPagingDto = userService.userLoginReset(id, memberDto, request);
        return resultJsonPagingDto;
    }

    @PostMapping(value="/userChangePass")
    public ResultJsonPagingDto userChangePass(@AuthenticationPrincipal MemberDto memberDto,@ModelAttribute Member member, HttpServletRequest request, HttpServletResponse response) throws Exception{
        ResultJsonPagingDto resultJsonPagingDto = userService.userChangePass(memberDto, member, request);
        return resultJsonPagingDto;
    }

    @PostMapping(value="/superUserChangePass")
    public ResultJsonPagingDto superUserChangePass(@AuthenticationPrincipal MemberDto memberDto, @ModelAttribute Member member) throws Exception{
        ResultJsonPagingDto resultJsonPagingDto = userService.superUserChangePass(memberDto, member);
        return resultJsonPagingDto;
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

    //회원정보 수정 처리
    @PostMapping(value="/updateUserAjax")
    public ResultJsonPagingDto updateUser(@ModelAttribute Member user,@AuthenticationPrincipal MemberDto memberDto, HttpServletRequest request, HttpServletResponse response) throws Exception{
        ResultJsonPagingDto resultJsonPagingDto = userService.updateUser(user, memberDto, request);
        return resultJsonPagingDto;
    }

    @PostMapping("/createUserAjax")
    public ResultJsonPagingDto createUser(@ModelAttribute Member user,@AuthenticationPrincipal MemberDto memberDto, HttpServletRequest request, HttpServletResponse response) throws Exception{
        ResultJsonPagingDto resultJsonPagingDto = userService.joinUser(user, memberDto, request);
        return resultJsonPagingDto;
    }
}
