package com.won.dourbest.main.model.service;

import com.won.dourbest.common.dto.SearchCriteria;
import com.won.dourbest.main.model.dto.CategoryFundingDTO;
import com.won.dourbest.user.dto.LikeFundingDTO;

import java.util.List;

public interface MainService {

  public List<CategoryFundingDTO> categoryList(SearchCriteria searchCriteria);


  public int totalCount(SearchCriteria searchCriteria);

  List<LikeFundingDTO> getSlideImages();

  List<LikeFundingDTO> ToplikeFundings();

  List<LikeFundingDTO> OpenFundings();

  List<LikeFundingDTO> EndFundings();



}
