package com.test.service;

import com.test.modal.CategoryDto;
import com.test.modal.MobileDto;
import java.util.List;

public interface MobileService {

	CategoryDto saveCategory(CategoryDto categoryDto);
	Boolean deleteCategoryById(Long id);
	CategoryDto findCategoryById(Long id);
	List<CategoryDto> findAllCategories();

	MobileDto saveMobile(MobileDto mobileDto);
	Boolean deleteMobileById(Long id);
	MobileDto findMobileById(Long id);
	List<MobileDto> findAllMobiles();
	List<MobileDto> searchMobiles(MobileDto mobileDto);
}
