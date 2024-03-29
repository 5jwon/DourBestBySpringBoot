package com.won.dourbest.user.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.won.dourbest.user.dto.AddressDTO;
import com.won.dourbest.user.dto.MemberDTO;
//import com.won.dourbest.user.service.MemberServiceImpl;

import com.won.dourbest.user.dto.MemberImpl;
import com.won.dourbest.user.service.MemberService;
import com.won.dourbest.user.service.MemberServiceImpl;
import lombok.Getter;
import org.apache.ibatis.annotations.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/user")   //기본페이지
public class MemberController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final MemberService service;
    private final PasswordEncoder passwordEncoder;


    // 의존성주입
    public MemberController(MemberService service, PasswordEncoder passwordEncoder) {

        this.service = service;
        this.passwordEncoder = passwordEncoder;
    }


    // 회원가입페이지 이동 메소드
    @GetMapping("/signup")    //이동할 페이지
    public String signup(){

        return "user/signup";
    }

    @PostMapping("/")
    public String defaultLocation(){
        return "redirect:/";
    }

    @GetMapping("/login")    //이동할 페이지
    public String login(){

        return "user/login";
    }

    @PostMapping("/login/error")    //이동할 페이지
    public String loginError(Model model){
        model.addAttribute("loginErrorMsg","아이디 또는 비밀번호를 확인해주세요");
        return "user/login";
    }


    // 중복아이디 체크
    @PostMapping("checkId")
    @ResponseBody  //
    public String checkId(@RequestParam String memberId){
        System.out.println("memberId = " + memberId);
        String result = service.idCheck(memberId) == false? "success" : "fail";
        return result;
    }

    // 중복 이메일 체크
    @PostMapping("checkEmail")
    @ResponseBody  //
    public String checkEmail(@RequestParam String memberEmail){
        System.out.println("memberEmail = " + memberEmail);
        String result = service.emailCheck(memberEmail) == false? "success" : "fail";  // false면 중복값이 없으므로 success
        return result;
    }


//     회원 가입 페이지에서 값이 들어가는지 확인하는 메소드
    @PostMapping("/signup")
    public String signupMember(@ModelAttribute @Valid MemberDTO member, @ModelAttribute @Valid AddressDTO address, BindingResult bindingResult, HttpServletRequest request) {

        // DTO의 값을 받으면 컨트롤러에서 Map으로 전달 받은 후에  서비스로 전달을 해준다.
        Map<String, Object> map = new HashMap<>();
        member.setMemberPhone(member.getMemberPhone().replace("-", "").replace("+82","0"));  // +82 -> 0변경 하이픈제외
        member.setMemberPwd(passwordEncoder.encode(member.getMemberPwd()));  // 비밀번호를 암호화해준다.
        map.put("member", member);
        map.put("address", address);
        service.registMember(map);

        // 회원가입이 성공하면 -> 회원에게 쿠폰을 insert 로 담아줘야한다.

        return "redirect:/user/signup-success";
    }


    @GetMapping("/modify")
    public String modifyMemvberInfo(@AuthenticationPrincipal MemberImpl member, Model model){

        MemberDTO findMember = service.findUser(member.getMemberId()).orElseThrow();
        boolean result = passwordEncoder.matches("iYk6786w", member.getPassword());

        log.info("member={}",findMember);
        System.out.println("result = " + result);

        model.addAttribute("member", findMember);


        return "redirect:/";
    }

    @PostMapping("/modi")
    public String infoModifytest(@AuthenticationPrincipal MemberImpl user, @RequestParam String userId, @RequestParam String pwd){
        log.info("member={}",user);
        log.info("memberid-{}", user.getMemberId());
        log.info("userpwd={}", user.getPassword());

        //확인완료
        boolean result = passwordEncoder.matches(pwd, user.getPassword());

        log.info("result={}",result);

        return "redirect:/"; //수정하는페이지로이동
    }

    // 회원정보 수정 전에 정보 회원 확인
    @PostMapping("/checkMember")
    @Transactional(rollbackFor = { Exception.class })
    public String checkMember(@AuthenticationPrincipal MemberImpl user, @RequestParam String pwd){

        boolean result = passwordEncoder.matches( pwd , user.getPassword());

        if(result) {
            return "redirect:/mypage/changeInfo";
        } else {
//
            return "redirect:/mypage/checkMember";
        }
    }


    // 비밀번호 수정 전에 정보 회원 확인
    @PostMapping("/checkMemberPw")
    @Transactional(rollbackFor = { Exception.class })
    public String checkMemberPw(@AuthenticationPrincipal MemberImpl user, @RequestParam String pwd){

        boolean result = passwordEncoder.matches( pwd , user.getPassword());

        if(result) {
            return "redirect:/mypage/changePwd";
        } else {

            return "redirect:/mypage/checkMemberPw";
        }
    }

    // 탈퇴 전 회원 확인

    @PostMapping("/beforequitMember")
    public String checkMemberDelete(@AuthenticationPrincipal MemberImpl user, @RequestParam String pwd,Model model){

        MemberDTO findMember = service.findUser(user.getMemberId()).orElseThrow();
        model.addAttribute("user", user);
        model.addAttribute( "mypageInfo", findMember);
//        log.info("user = " +  user);
//        log.info("pwd = " +  pwd);
        boolean result = passwordEncoder.matches( pwd , user.getPassword());
//        log.info("result = " + result);
        if(result) {
            return "redirect:/mypage/quitMember";
        } else {
//
            return "redirect:/mypage/beforequitMember";
        }
    }

    // 회원탈퇴 성공
    @GetMapping("/quitMember-success")    //이동할 페이지
    public String quitMemberSuccess() {

//        System.out.println("user = " + user);
//        MemberDTO mypageInfo = service.findUser(user.getMemberId()).orElseThrow();
//        model.addAttribute("mypageInfo", mypageInfo);  //멤버 배송지 모두 담겨있음.


        return "user/mypage/quitMemberSuc";
    }

    // 회원가입 성공 페이지
    @GetMapping("/signup-success")    //이동할 페이지
    public String signupSuccess() {

//        System.out.println("user = " + user);
//        MemberDTO mypageInfo = service.findUser(user.getMemberId()).orElseThrow();
//        model.addAttribute("mypageInfo", mypageInfo);  //멤버 배송지 모두 담겨있음.


        return "user/signupSuc";
    }


}
