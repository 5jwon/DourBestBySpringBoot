package com.won.dourbest.seller.dao.jiho;

import com.won.dourbest.common.dto.CategoryDTO;
import com.won.dourbest.seller.dto.FundingOptionDTO;
import com.won.dourbest.seller.dto.PlanDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface SellerFundingMapper {
    // 플랜 조회
    List<PlanDTO> allPlan();

    // 태그 select
    Integer selectTag(String tag);

    // 태그 insert
    int insertTag(String tag);

    // 펀딩 카테고리 조회
    List<CategoryDTO> getFundCategory();

    // 펀딩 Insert
    int insertFunding(Map<String, Object> tossMap);

    // 펀딩 메인 자료 Insert
    int insertMainFile(Map<String, Object> tossMap);

    // 펀딩 top 사진 Insert
    int insertTopFile(Map<String, Object> tossMap);

    // 펀딩 본문 사진 Insert
    int insertContentFile(Map<String, Object> tossMap);

    // 옵션 insert
    int insertOption(FundingOptionDTO option);

    // 옵션 삭제
    int deleteOption(Map<String, String> map);

    // 판매자 업데이트
    int updateSeller(Map<String, Object> map);

    // 펀딩 업데이트
    int updateFunding(Map<String, Object> map);

    // 태그 리스트
    Integer insertTagList(int num);

    int selectSeller(Map<String, Object> tossMap);

    int insertStatusList();
}
