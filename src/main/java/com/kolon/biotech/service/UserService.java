package com.kolon.biotech.service;

import com.kolon.biotech.domain.history.History;
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

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Slf4j
@Service
public class UserService implements UserDetailsService {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private HistoryService historyService;

    @Transactional
    public ResultJsonPagingDto joinUser(Member memberDto, MemberDto suser, HttpServletRequest request) throws Exception {
        // 비밀번호 암호화
        ResultJsonPagingDto _resultMap = new ResultJsonPagingDto();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        memberDto.setPassword(passwordEncoder.encode(memberDto.getPassword()));

        //권한 설정
        memberDto.setAuth("ROLE_USER");
        //비밀번호 설정
        memberDto.setPasswordChangeDate(LocalDateTime.now());

        Member _member = memberRepository.save(memberDto);

        History history = History.builder().userId(suser.getLoginId())
                .userName(suser.getUsername())
                .jobContent("관리자가 "+_member.getLoginId()+"을 신규생성 하였습니다.")
                .jobFlag("J")
                .requestDate(LocalDateTime.now())
                .jobUrl(request.getRequestURI())
                .requestIp(request.getRemoteAddr())
                .build();
        historyService.setWriteStroe(history);

        _resultMap.setSuccess(true);
        _resultMap.setMessage("계정생성이 완료되었습니다.");

        return _resultMap;
    }

    @Transactional(rollbackFor = Exception.class)
    public ResultJsonPagingDto updateUser(Member memberDto, MemberDto suser, HttpServletRequest request) throws Exception {

        //결과 값
        ResultJsonPagingDto _resultMap = new ResultJsonPagingDto();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        //이력
        History history = null;
        //패스워드를 입력받았으면
        //직전 패스워드 검사
        Member r_member = memberRepository.findById(memberDto.getId()).orElseThrow(()->{
            return new IllegalArgumentException("회원정보 조회 실패");
        });

        if(memberDto.getPassword() != null && !"".equals(memberDto.getPassword())){
            //직전 패스워드 검사
            log.debug("##########관리자 id값 >>>"+ suser.getId());
            Member r_smember = memberRepository.findById(suser.getId()).orElseThrow(()->{
                return new IllegalArgumentException("회원정보 조회 실패");
            });

            if(passwordEncoder.matches(memberDto.getMPassword(),r_smember.getPassword())){
                //직전 비밀번호 검사
                if(passwordEncoder.matches(memberDto.getPassword(),r_member.getPassword())){
                    _resultMap.setSuccess(false);
                    _resultMap.setMessage("동일한 비밀번호로 변경할 수 없습니다.");

                    history = History.builder().userId(suser.getLoginId())
                            .userName(suser.getUsername())
                            .jobContent("동일한 비밀번호로 변경할 수 없습니다.")
                            .jobFlag("J")
                            .requestDate(LocalDateTime.now())
                            .jobUrl(request.getRequestURI())
                            .requestIp(request.getRemoteAddr())
                            .build();
                    historyService.setWriteStroe(history);

                    return _resultMap;
                }else{
                    r_member.setPassword(passwordEncoder.encode(memberDto.getPassword()));
                    r_member.setPasswordChangeDate(LocalDateTime.now());
                    r_member.setBlocked("false");
                    r_member.setFailCount(0);
                    _resultMap.setSuccess(true);
                    _resultMap.setMessage("비밀번호 변경이 완료되었습니다.");

                    history = History.builder().userId(suser.getLoginId())
                            .userName(suser.getUsername())
                            .jobContent("관리자가 "+r_member.getLoginId()+"의 비밀번호를 변경하였습니다.")
                            .jobFlag("J")
                            .requestDate(LocalDateTime.now())
                            .jobUrl(request.getRequestURI())
                            .requestIp(request.getRemoteAddr())
                            .build();
                    historyService.setWriteStroe(history);

                }
            }else{
                _resultMap.setSuccess(false);
                _resultMap.setMessage("마스터 비밀번호를 확인해주세요.");

                history = History.builder().userId(suser.getLoginId())
                        .userName(suser.getUsername())
                        .jobContent("마스터 비밀번호를 확인해주세요.")
                        .jobFlag("J")
                        .requestDate(LocalDateTime.now())
                        .jobUrl(request.getRequestURI())
                        .requestIp(request.getRemoteAddr())
                        .build();
                historyService.setWriteStroe(history);

                return _resultMap;
            }
        }

        //회원정보 변경
        if(!memberDto.getName().equals(r_member.getName())){
            r_member.setName(memberDto.getName());
        }
        if(!memberDto.getEmailId().equals(r_member.getEmailId())){
            r_member.setEmailId(memberDto.getEmailId());
        }
        if(!memberDto.getEmailDomain().equals(r_member.getEmailDomain())){
            r_member.setEmailDomain(memberDto.getEmailDomain());
        }
        if(!memberDto.getPnum().equals(r_member.getPnum())){
            r_member.setPnum(memberDto.getPnum());
        }
        if(!memberDto.getRank().equals(r_member.getRank())){
            r_member.setRank(memberDto.getRank());
        }
        if(!memberDto.getUseYn().equals(r_member.getUseYn())){
            r_member.setUseYn(memberDto.getUseYn());
        }

        history = History.builder().userId(suser.getLoginId())
                .userName(suser.getUsername())
                .jobContent("관리자가 "+r_member.getLoginId()+"의 정보를 변경하였습니다.")
                .jobFlag("J")
                .requestDate(LocalDateTime.now())
                .jobUrl(request.getRequestURI())
                .requestIp(request.getRemoteAddr())
                .build();
        historyService.setWriteStroe(history);

        String smainauth = memberDto.getMainAuthority() == null ? "N" : memberDto.getMainAuthority();
        String dmainauth = r_member.getMainAuthority() == null ? "N" : r_member.getMainAuthority();
        if(!smainauth.equals(dmainauth)){
            r_member.setMainAuthority(memberDto.getMainAuthority());

            if("Y".equals(memberDto.getMainAuthority())){
                history = History.builder().userId(suser.getLoginId())
                        .userName(suser.getUsername())
                        .jobContent("관리자가 "+r_member.getLoginId()+"의 메인관리권한을 부여하였습니다.")
                        .jobFlag("G")
                        .requestDate(LocalDateTime.now())
                        .jobUrl(request.getRequestURI())
                        .requestIp(request.getRemoteAddr())
                        .build();
            }else{
                history = History.builder().userId(suser.getLoginId())
                        .userName(suser.getUsername())
                        .jobContent("관리자가 "+r_member.getLoginId()+"의 메인관리권한을 회수하였습니다.")
                        .jobFlag("G")
                        .requestDate(LocalDateTime.now())
                        .jobUrl(request.getRequestURI())
                        .requestIp(request.getRemoteAddr())
                        .build();
            }

            historyService.setWriteStroe(history);
        }

        String snotiauth = memberDto.getNoticeAuthority() == null ? "N" : memberDto.getNoticeAuthority();
        String dnotiauth = r_member.getNoticeAuthority() == null ? "N" : r_member.getNoticeAuthority();
        if(!snotiauth.equals(dnotiauth)){
            r_member.setNoticeAuthority(memberDto.getNoticeAuthority());

            if("Y".equals(memberDto.getNoticeAuthority())){
                history = History.builder().userId(suser.getLoginId())
                        .userName(suser.getUsername())
                        .jobContent("관리자가 "+r_member.getLoginId()+"의 공지사항관리권한을 부여하였습니다.")
                        .jobFlag("G")
                        .requestDate(LocalDateTime.now())
                        .jobUrl(request.getRequestURI())
                        .requestIp(request.getRemoteAddr())
                        .build();
            }else{
                history = History.builder().userId(suser.getLoginId())
                        .userName(suser.getUsername())
                        .jobContent("관리자가 "+r_member.getLoginId()+"의 공지사항관리권한을 회수하였습니다.")
                        .jobFlag("G")
                        .requestDate(LocalDateTime.now())
                        .jobUrl(request.getRequestURI())
                        .requestIp(request.getRemoteAddr())
                        .build();
            }

            historyService.setWriteStroe(history);
        }

        String slogauth = memberDto.getLogAuthority() == null ? "N" : memberDto.getLogAuthority();
        String dlogauth = r_member.getLogAuthority() == null ? "N" : r_member.getLogAuthority();
        if(!slogauth.equals(dlogauth)){
            r_member.setLogAuthority(memberDto.getLogAuthority());

            if("Y".equals(memberDto.getLogAuthority())){
                history = History.builder().userId(suser.getLoginId())
                        .userName(suser.getUsername())
                        .jobContent("관리자가 "+r_member.getLoginId()+"의 로그관리권한을 부여하였습니다.")
                        .jobFlag("G")
                        .requestDate(LocalDateTime.now())
                        .jobUrl(request.getRequestURI())
                        .requestIp(request.getRemoteAddr())
                        .build();
            }else{
                history = History.builder().userId(suser.getLoginId())
                        .userName(suser.getUsername())
                        .jobContent("관리자가 "+r_member.getLoginId()+"의 로그관리권한을 회수하였습니다.")
                        .jobFlag("G")
                        .requestDate(LocalDateTime.now())
                        .jobUrl(request.getRequestURI())
                        .requestIp(request.getRemoteAddr())
                        .build();
            }

            historyService.setWriteStroe(history);
        }

        _resultMap.setSuccess(true);
        _resultMap.setMessage("정보변경이 완료되었습니다.");

        return _resultMap;
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
                //memberRepository.updateDelYn(id);
                memberRepository.deleteById(id);
            }
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public ResultJsonPagingDto userChangePass(MemberDto memberDto, Member member, HttpServletRequest request) throws Exception{
        //결과 값
        ResultJsonPagingDto _resultMap = new ResultJsonPagingDto();
        //직전 패스워드 검사
        Member r_member = memberRepository.findById(memberDto.getId()).orElseThrow(()->{
            return new IllegalArgumentException("회원정보 조회 실패");
        });

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        if(passwordEncoder.matches(member.getMPassword(),r_member.getPassword())){
            if(passwordEncoder.matches(member.getPassword(),r_member.getPassword())){
                _resultMap.setSuccess(false);
                _resultMap.setMessage("동일한 비밀번호로 변경할 수 없습니다.");

                History history = History.builder().userId(r_member.getLoginId())
                        .jobContent("동일한 비밀번호로 변경할 수 없습니다.")
                        .jobFlag("J")
                        .requestDate(LocalDateTime.now())
                        .jobUrl(request.getRequestURI())
                        .requestIp(request.getRemoteAddr())
                        .build();
                historyService.setWriteStroe(history);

            }else{
                String newpassword = passwordEncoder.encode(member.getPassword());
                r_member.setPassword(newpassword);
                r_member.setPasswordChangeDate(LocalDateTime.now());
                r_member.setBlocked("false");

                History history = History.builder().userId(r_member.getLoginId())
                        .jobContent("비밀번호를 변경하였습니다.")
                        .jobFlag("J")
                        .requestDate(LocalDateTime.now())
                        .jobUrl(request.getRequestURI())
                        .requestIp(request.getRemoteAddr())
                        .build();
                historyService.setWriteStroe(history);

                _resultMap.setSuccess(true);
                //_resultMap.setMessage("비밀번호 변경이 완료되었습니다.\\n 변경된 비밀번호로 다시 로그인하세요.");
                _resultMap.setMessage("비밀번호 변경이 완료되었습니다.");
            }
        }else{
            _resultMap.setSuccess(false);
            _resultMap.setMessage("비밀번호가 다릅니다.");

            History history = History.builder().userId(r_member.getLoginId())
                    .jobContent("비밀번호가 다릅니다.")
                    .jobFlag("J")
                    .requestDate(LocalDateTime.now())
                    .jobUrl(request.getRequestURI())
                    .requestIp(request.getRemoteAddr())
                    .build();
            historyService.setWriteStroe(history);
        }



        return _resultMap;
    }

    @Transactional(rollbackFor = Exception.class)
    public ResultJsonPagingDto superUserChangePass(MemberDto memberDto, Member member) throws Exception{
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

    @Transactional(rollbackFor = Exception.class)
    public ResultJsonPagingDto userLoginReset(Integer id, MemberDto suser, HttpServletRequest request) throws Exception {

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

        History history = History.builder().userId(suser.getLoginId())
                .userName(suser.getUsername())
                .jobContent("관리자가 "+r_member.getLoginId()+"을 초기화 하였습니다.")
                .jobFlag("J")
                .requestDate(LocalDateTime.now())
                .jobUrl(request.getRequestURI())
                .requestIp(request.getRemoteAddr())
                .build();
        historyService.setWriteStroe(history);

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
        memberDto.setId(user.getId());
        memberDto.setLoginId(user.getLoginId());
        memberDto.setUsername(user.getName());
        memberDto.setPassword(user.getPassword());
        memberDto.setMainAuthority(user.getMainAuthority());
        memberDto.setNoticeAuthority(user.getNoticeAuthority());
        memberDto.setLogAuthority(user.getLogAuthority());
        LocalDateTime pwdDate = user.getPasswordChangeDate() != null ? user.getPasswordChangeDate() : user.getRegDtime();
        if(ChronoUnit.DAYS.between(pwdDate,LocalDateTime.now()) >= 90){
            user.setBlocked("true");
        }
        memberDto.setBlocked(user.getBlocked() == null ? "false" : user.getBlocked());
        memberDto.setAuthorities(authorities);
        memberDto.setEnabled(true);
        memberDto.setAccountNonLocked(true);
        memberDto.setAccountNonExpired(true);
        memberDto.setCredentialsNonExpired(true);

        return memberDto;
        //return new User(user.getLoginId(),user.getPassword(),authorities);


    }


}
