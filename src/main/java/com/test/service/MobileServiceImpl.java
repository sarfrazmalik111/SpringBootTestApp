package com.test.service;

import com.test.common.MyUtility;
import com.test.dao.AppJdbcTemplate;
import com.test.dao.CategoryRepository;
import com.test.dao.MobileRepository;
import com.test.modal.CategoryDto;
import com.test.modal.MobileDto;
import com.test.modalDT.LabelValueDT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MobileServiceImpl implements MobileService {

	@Autowired
	private CategoryRepository categoryRepo;
	@Autowired
	private MobileRepository mobileRepo;
	@Autowired
	private MyUtility myUtility;
	@Autowired
	private AppJdbcTemplate jdbcTemplate;

	@Override
	public CategoryDto saveCategory(CategoryDto categoryDto) {
		if(categoryDto.getId() == null) categoryDto.setCreatedOn(LocalDateTime.now());
		return categoryRepo.save(categoryDto);
	}

	@Override
	public CategoryDto findCategoryById(Long id) {
		CategoryDto categoryDto = null;
		Optional<CategoryDto> list = categoryRepo.findById(id);
		if (list.isPresent()) {
			categoryDto = list.get();
		}
		return categoryDto;
	}

	@Override
	public List<CategoryDto> findAllCategories() {
		return categoryRepo.findAll();
	}

	@Override
	public Boolean deleteCategoryById(Long id) {
		Boolean flag = false;
		try {
			categoryRepo.deleteById(id);
			flag = true;
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		return flag;
	}

//	======================================== Mobile-Service ======================================
	@Override
	public MobileDto saveMobile(MobileDto mobileDto) {
		if(mobileDto.getId() == null) mobileDto.setCreatedOn(LocalDateTime.now());
		return mobileRepo.save(mobileDto);
	}

	@Override
	public MobileDto findMobileById(Long id) {
		MobileDto mobileDto = null;
		Optional<MobileDto> list = mobileRepo.findById(id);
		if (list.isPresent()) {
			mobileDto = list.get();
		}
		return mobileDto;
	}

	@Override
	public List<MobileDto> findAllMobiles() {
		return mobileRepo.findAll();
	}

	@Override
	public Boolean deleteMobileById(Long id) {
		Boolean flag = false;
		try {
			mobileRepo.deleteById(id);
			flag = true;
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		return flag;
	}

	@Override
	public List<MobileDto> searchMobiles(MobileDto mobileDto) {
		String categoryId = mobileDto.getCategoryId() != null ? mobileDto.getCategoryId().toString() : "";
		String name = myUtility.avoidNullableValue(mobileDto.getName());
		String price = mobileDto.getPrice() != null ? mobileDto.getPrice().toString() : "";
		String SQL = "SELECT * FROM Mobile WHERE categoryId like '"+categoryId+"%' AND name like '"+name+"%' AND price like '"+price+"%'";
		return jdbcTemplate.getRecordsByGivenSelectSQL(SQL, MobileDto.class);
	}
}
