package com.won.dourbest.seller.dto;

import com.won.dourbest.common.dto.CategoryDTO;
import lombok.*;

import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class FundingDTO {

    private int fundingCode;
    private String fundingTitle;
    private String fundingSummary;
    private String fundingContents;
    private int views;
    private int likes;
    private Date applicationDate;
    private Date startDate;
    private Date endDate;
    private int fundingGoalMoney;
    private String returnRole;
    // 판매자 코드
    private int sellerCode;
    // 카테고리 코드
    private int categoryCode;
    // 요금제 코드
    private int planCode;

    // 펀딩 태그 리스트
    private List<FundingTagDTO> tagList;
    // 펀딩 옵션 리스트
    private List<FundingOptionDTO> optionList;
    // 펀딩 파일 리스트
    private List<FundingFileDTO> fileList;
    // 펀딩 공지사항 리스트
    private List<FundingNoticeDTO> noticeList;
    // 펀딩 신고 리스트
    private List<FundingReportDTO> reportList;
    // 후기 리스트
    private List<ReviewDTO> reviewList;
    // 판매자 문의 리스트
    private List<SellerInquiryDTO> sellerInquiry;
    // 펀딩 상태 이력
    private List<FundingStatusListDTO> fundingStatusList;
    // 주문 총 금액
    private int orderTotalPrice;
    // 주문한 수
    private int orderNumber;
}
