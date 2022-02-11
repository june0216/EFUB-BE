package EFUB.homepage.controller;

import EFUB.homepage.config.JwtTokenProvider;
import EFUB.homepage.domain.Admin;
import EFUB.homepage.dto.login.LoginReqDto;
import EFUB.homepage.exception.NoSuchAdminException;
import EFUB.homepage.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class LoginController {
    private final LoginService service;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/login")
    public String login(@Valid @RequestBody LoginReqDto loginReqDto) {
        Admin member = service.findUser(loginReqDto);
        if(member == null) throw new NoSuchAdminException("잘못된 아이디입니다.");
        if(!passwordEncoder.matches(loginReqDto.getPassword(), member.getPassword())) {
            throw new NoSuchAdminException("잘못된 비밀번호입니다.");
        }
        return jwtTokenProvider.createToken(member.getAdminId(), member.getRole());
    }
}
