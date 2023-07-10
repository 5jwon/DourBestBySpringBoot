package com.won.dourbest.seller.controller;

import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import com.won.dourbest.seller.dto.FundingOptionDTO;
import com.won.dourbest.seller.dto.ProductDTO;
import com.won.dourbest.seller.dto.SellerDTO;
import com.won.dourbest.seller.service.SellerService;
import com.won.dourbest.seller.service.SellerServiceImpl;
import com.won.dourbest.user.dto.*;
import org.apache.ibatis.annotations.Param;
import org.apache.tomcat.util.json.JSONParser;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Logger;


@Controller
@RequestMapping("/seller")
public class SellerController {


    private final SellerServiceImpl service;
    private IamportClient api;


    public SellerController (SellerServiceImpl service, IamportClient api) {
        this.service = service;
        this.api = new IamportClient("5775185238551285","nOLrNJUiWs7mVQRrPVP3I7N44OZAk6VIDJ5SplkOKo6fWHK8gz2hgLf0pfY9v3mtPfPJkVLgnXnTG4lV");
    }


    @GetMapping("/application")
    public String seller() {

        return "seller/giwon_seller/seller_application";
    }

    @PostMapping("/application")
    public String seller(HttpServletRequest request , @ModelAttribute SellerDTO seller ,@ModelAttribute MemberDTO member){


        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");
        String license = request.getParameter("license");
        System.out.println("license : " + license);
        System.out.println(id);
        System.out.println(name);
        System.out.println(phone);
        System.out.println(email);

        member.setMemberId(id);
        member.setMemberName(name);
        member.setMemberPhone(phone);
        member.setMemberEmail(email);
        seller.setBusinessLicense(license);


        service.registSeller(seller, member);



        return "seller/giwon_seller/seller_application";
    }

    @GetMapping("/success")
    public String success() {

        return "seller/giwon_seller/seller_success";
    }

    // 상품명 조회
    @GetMapping("/payment")
    public String payment(Model model, HttpServletRequest request , @AuthenticationPrincipal MemberImpl id ) {

        FundingOptionDTO product = service.selectProductName();

        model.addAttribute("product", product);

        // 주문자 정보 조회
        MemberDTO member = service.selectMember(id.getMemberId());


        model.addAttribute("member", member);

        // 배송지 조회
        AddressDTO address = service.selectAddress(id.getMemberId());

        model.addAttribute("address", address);

        // 회원이 가지고 있는 쿠폰 목록

        List<CouponDTO> couponName = service.selectCouponList();

        model.addAttribute("couponName" , couponName);

        // 포인트 조회
        ProductDTO pointAmount = service.selectPoint(id.getMemberId());

        model.addAttribute("pointAmount", pointAmount );

        // 배송비

        OrderDTO delivery = service.selectDelivery();

        model.addAttribute("delivery" , delivery );

        System.out.println("memberId : " + id);

        // 최종 결제 금액




        return "seller/giwon_seller/payment_page";
    }

    @PostMapping("/coupon")
    @ResponseBody
    public Map<String, String> payment(@RequestParam("choiceCoupon") String choiceCoupon ,@RequestParam int optionCode) {

        Map<String, String> map = new HashMap<>();
        System.out.println("choiceCoupon = " + choiceCoupon);
        // 쿠폰 사용
        ProductDTO couponApply = service.registCoupon(choiceCoupon,optionCode);
        System.out.println("couponApply = " + couponApply);
        map.put("coupon" , String.valueOf(couponApply.getPointTotalAmount()));
        map.put("disCount", String.valueOf(couponApply.getDisCount()));


        return map;
    }

    @PostMapping("/point")
    @ResponseBody
    public String point(@RequestParam String usePoint,@AuthenticationPrincipal MemberImpl id ) {

        ProductDTO point = service.selectPoint(id.getMemberId());

        System.out.println("usePoint : " + usePoint);


        return usePoint;
    }

    @PostMapping("/option")
    @ResponseBody
    public ProductDTO option(@RequestParam int optionCode) {

        ProductDTO product = service.selectProduct(optionCode);

        return product;
    }

    @PostMapping("/totalPrice")
    @ResponseBody
    public int totalPrice(@RequestParam int totalPrice) {

        return totalPrice;
    }













//    @PostMapping("/credit")
//    @ResponseBody
//    public void memberCode(@RequestParam int memberCode){
//        service.selectMemberCode(memberCode);
//    }
//
//
//    @PostMapping("/verifyIamport/{imp_uid}")
//    @ResponseBody
//    public IamportResponse<Payment> Import(Model model, Locale locale, HttpSession session, @PathVariable(value = "imp_uid") String imp_uid) throws IamportResponseException, IOException {
//
//        return api.paymentByImpUid(imp_uid);
//    }




}
