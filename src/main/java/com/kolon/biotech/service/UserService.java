package com.kolon.biotech.service;

import com.kolon.biotech.domain.subsidiary.Subsidiary;
import com.kolon.biotech.domain.user.MemberRepository;
import com.kolon.biotech.domain.user.Role;
import com.kolon.biotech.domain.user.Member;
import com.kolon.biotech.web.dto.MemberDto;
import com.kolon.biotech.web.dto.ResultJsonPagingDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class UserService implements UserDetailsService {

    @Autowired
    private MemberRepository memberRepository;

    @Transactional
    public Member joinUser(Member memberDto) {
        // 비밀번호 암호화
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        memberDto.setPassword(passwordEncoder.encode(memberDto.getPassword()));

        //권한 설정
        memberDto.setAuth("ROLE_USER");
        //비밀번호 설정
        memberDto.setPasswordChangeDate(LocalDateTime.now());

        return memberRepository.save(memberDto);
    }

    public Member getUser(Member memberDto){
        Member user;
        if(memberDto != null && memberDto.getId() != null){
            Optional<Member> userWrapper = memberRepository.findById(memberDto.getId());
            user = userWrapper.get();
        }else{
            user = new Member();
        }

        return user;
    }

    public Member getLoginId(Member memberDto){
        Member user;
        Optional<Member> userWrapper = memberRepository.findByLoginId(memberDto.getLoginId());
        if(userWrapper.isPresent()){
            user = userWrapper.get();
        }else{
            user = new Member();
        }


        return user;
    }

    public Page<Member> getMemberList(Pageable pageable){
        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() -1);
        pageable = PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC,"id"));
        return memberRepository.findAll(pageable);
    }

    @Transactional
    public void delete(List<Integer> deleteList){
        if(deleteList != null && !deleteList.isEmpty()){
            for(Integer id : deleteList){
                memberRepository.updateDelYn(id);
            }
        }
    }

    @Transactional
    public ResultJsonPagingDto userChangePass(MemberDto memberDto, Member member){
        //결과 값
        ResultJsonPagingDto _resultMap = new ResultJsonPagingDto();
        //직전 패스워드 검사
        Member r_member = memberRepository.findById(memberDto.getId()).orElseThrow(()->{
            return new IllegalArgumentException("회원정보 조회 실패");
        });

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        if(passwordEncoder.matches(member.getPassword(),r_member.getPassword())){
            _resultMap.setSuccess(false);
            _resultMap.setMessage("동일한 비밀번호로 변경할 수 없습니다.");
        }else{
            String newpassword = passwordEncoder.encode(member.getPassword());
            r_member.setPassword(newpassword);
            r_member.setPasswordChangeDate(LocalDateTime.now());

            _resultMap.setSuccess(true);
            _resultMap.setMessage("비밀번호 변경이 완료되었습니다.\\n 변경된 비밀번호로 다시 로그인하세요.");
        }

        return _resultMap;
    }

    @Transactional
    public ResultJsonPagingDto superUserChangePass(MemberDto memberDto, Member member){
        //결과 값
        ResultJsonPagingDto _resultMap = new ResultJsonPagingDto();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        //직전 패스워드 검사
        Member r_smember = memberRepository.findById(memberDto.getId()).orElseThrow(()->{
            return new IllegalArgumentException("회원정보 조회 실패");
        });

        if(passwordEncoder.matches(member.getMPassword(),r_smember.getPassword())){
            //직전 패스워드 검사
            Member r_member = memberRepository.findById(member.getId()).orElseThrow(()->{
                return new IllegalArgumentException("회원정보 조회 실패");
            });

            //직전 비밀번호 검사
            if(passwordEncoder.matches(member.getPassword(),r_member.getPassword())){
                _resultMap.setSuccess(false);
                _resultMap.setMessage("동일한 비밀번호로 변경할 수 없습니다.");
            }else{
                r_member.setPassword(passwordEncoder.encode(member.getPassword()));
                r_member.setPasswordChangeDate(LocalDateTime.now());
                _resultMap.setSuccess(true);
                _resultMap.setMessage("비밀번호 변경이 완료되었습니다.");
            }
        }else{
            _resultMap.setSuccess(false);
            _resultMap.setMessage("마스터 비밀번호를 확인해주세요.");
        }

        return _resultMap;
    }

    @Transactional
    public ResultJsonPagingDto userLoginReset(Integer id){

        ResultJsonPagingDto _resultMap = new ResultJsonPagingDto();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        Member r_member = memberRepository.findById(id).orElseThrow(()->{
            return new IllegalArgumentException("회원정보 조회 실패");
        });

        //r_member.setPassword(passwordEncoder.encode("a123456789"));
        //r_member.setPasswordChangeDate(LocalDateTime.now());
        r_member.setFailCount(0);
        r_member.setBlocked("false");

        _resultMap.setSuccess(true);
        _resultMap.setMessage("초기화에 성공하였습니다.");

        return _resultMap;
    }

    /**
     * Spring Security 필수 메소드 구현https://blog.javabom.com/minhee/session/spring-security-1/spring-security
     *
     * @param loginId 아이디
     * @return UserDetails
     * @throws UsernameNotFoundException 유저가 없을 때 예외 발생
     */
    @Override // 기본적인 반환 타입은 UserDetails, UserDetails를 상속받은 UserInfo로 반환 타입 지정 (자동으로 다운 캐스팅됨)
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException { // 시큐리티에서 지정한 서비스이기 때문에 이 메소드를 필수로 구현
        log.debug("============loadUserByUsername================");
        log.debug("###LoadUserParam>>"+loginId);
        Optional<Member> userWrapper = memberRepository.findByLoginId(loginId);
        if(!userWrapper.isPresent()){
            throw new UsernameNotFoundException("사용자 정보를 찾을 수 없습니다.");
        }
        Member user = userWrapper.get();
        log.debug("##loadUserByUsername##>>>"+user.getLoginId());
        log.debug("##loadUserByUsername##>>>"+user.getPassword());

        List<GrantedAuthority> authorities = new ArrayList<>();

//        if("admin".equals(loginId)){
//            authorities.add(new SimpleGrantedAuthority(Role.ADMIN.getValue()));
//        }else{
//            authorities.add(new SimpleGrantedAuthority(Role.USER.getValue()));
//        }

        authorities.add(new SimpleGrantedAuthority(user.getAuth()));

        MemberDto memberDto = new MemberDto();
        memberDto.setUsername(user.getLoginId());
        memberDto.setPassword(user.getPassword());
        memberDto.setMainAuthority(user.getMainAuthority());
        memberDto.setNoticeAuthority(user.getNoticeAuthority());
        memberDto.setAuthorities(authorities);
        memberDto.setEnabled(true);
        memberDto.setAccountNonLocked(true);
        memberDto.setAccountNonExpired(true);
        memberDto.setCredentialsNonExpired(true);

        return memberDto;
        //return new User(user.getLoginId(),user.getPassword(),authorities);


    }


}
