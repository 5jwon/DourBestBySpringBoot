package com.won.dourbest.seller.service;

import com.won.dourbest.seller.dto.*;
import com.won.dourbest.user.dto.*;

import java.util.List;

public interface SellerService {


    public Integer registSeller(SellerDTO seller, MemberDTO member);

    FundingOptionDTO selectProductName(int optionCode);

    MemberDTO selectMember(String memberId);

    AddressDTO selectAddress(int memberId);

    List<CouponDTO> selectCouponList(String memberId);

    OrderDTO selectDelivery();

    ProductDTO registCoupon(String choiceCoupon, int optionCode);

    ProductDTO selectProduct(int optionCode);

    ProductDTO selectPoint(String memberCode);

    ProductDTO totalPrice(int totalPrice);


    OrderDTO insertOrder(OrderDTO order, int memberId);

    PaymentDTO insertPayment(PaymentDTO payment);

    int insertFundingCreditList(int paymentCode);


    int updateCoupon(DisCountDTO dc, int memberCode);

    ProductDTO selectUserPoint(String memberId, int usePoint);
}
