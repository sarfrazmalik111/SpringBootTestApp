package com.test.web;

import com.test.common.FileUploadUtils;
import com.test.common.AppConstants;
import com.test.modal.CategoryDto;
import com.test.modal.MobileDto;
import com.test.modalDT.LabelValueDT;
import com.test.service.MobileService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping({ "/mobile-store" })
public class MobileStoreController {

	@Autowired
	private MobileService mobileService;
	@Autowired
	private FileUploadUtils fileUploadUtils;
	@Autowired
	private HttpSession session;
	private String homePage = "pages/mobile_store/home";
	private String categoriesPage = "pages/mobile_store/categories";
	private String addCategoryPage = "pages/mobile_store/add_category";
	private String mobilesPage = "pages/mobile_store/mobiles";
	private String addMobilePage = "pages/mobile_store/add_mobile";
	private String searchPage = "pages/mobile_store/search_mobile";
	private Logger logger = LoggerFactory.getLogger(MobileStoreController.class);

	@RequestMapping("")
	public String homePage(Model model) {
		System.out.println("----mobile-store-Home-----");
		List<MobileDto> mobileList = mobileService.findAllMobiles();
		List<CategoryDto> categoryList = mobileService.findAllCategories();
		for(CategoryDto cat: categoryList) {
			int productCount = 0;
			for(MobileDto mobile: mobileList) {
				if(cat.getId() ==  mobile.getCategoryId()) productCount++;
			}
			cat.setProductCount(productCount);
		}
		model.addAttribute("categories", categoryList);
		session.setAttribute(AppConstants.MENU_ITEM, AppConstants.MENU_Mobile_Store);
		return homePage;
	}

	@GetMapping("/categories")
	public String categories(Model model) {
		model.addAttribute("categories", mobileService.findAllCategories());
		return categoriesPage;
	}
	
	@GetMapping({"/add-category", "/save-category"})
	public String addCategory(CategoryDto category, Model model) {
		model.addAttribute("category", category);
		return addCategoryPage;
	}

	@GetMapping("/edit-category/{id}")
	public String editCategory(@PathVariable Long id, Model model) {
		model.addAttribute("category", mobileService.findCategoryById(id));
		return addCategoryPage;
	}

	@PostMapping("/save-category")
	public String saveCategory(@Valid @ModelAttribute("category") CategoryDto category, BindingResult bindingResult, RedirectAttributes redAtt, Model model) {
		System.out.println("--------save-category-----------");
		if (bindingResult.hasErrors()) {
			model.addAttribute("category", category);
			return addCategoryPage;
		}
		try {
			if (category.getId() == null && category.getLogoFile().isEmpty()) {
				redAtt.addFlashAttribute(AppConstants.ALERT_ERROR, "Please upload logoFile");
				model.addAttribute(AppConstants.ALERT_ERROR, "Please upload logoFile");
				return addCategoryPage;
			}
			String outFilePath = fileUploadUtils.uploadFile(category.getLogoFile());
			if (outFilePath != null) {
				category.setLogoPath(outFilePath);
			}
			mobileService.saveCategory(category);
			redAtt.addFlashAttribute(AppConstants.ALERT_SUCCESS, "Category saved successfully!");
		} catch (Exception ex) {
			ex.printStackTrace();
			model.addAttribute("category", category);
			model.addAttribute(AppConstants.ALERT_ERROR, AppConstants.SOMETHING_WRONG_MSG);
		}
		return "redirect:/mobile-store/categories";
	}

	@GetMapping("/delete-category/{id}")
	public String deleteCategory(@PathVariable Long id, RedirectAttributes redAtt) {
		mobileService.deleteCategoryById(id);
		redAtt.addFlashAttribute(AppConstants.ALERT_ERROR, "Category deleted successfully!");
		return "redirect:/mobile-store/categories";
	}

//	======================================== Mobile-Service =====================================
	@GetMapping({ "/mobiles" })
	public String mobiles(Model model) {
		model.addAttribute("mobiles", mobileService.findAllMobiles());
		return mobilesPage;
	}
	
	@GetMapping({ "/add-mobile", "/save-mobile" })
	public String addMobile(MobileDto mobile, Model model) {
		model.addAttribute("categories", mobileService.findAllCategories());
		model.addAttribute("mobile", mobile);
		return addMobilePage;
	}

	@GetMapping("/edit-mobile/{id}")
	public String editMobile(@PathVariable Long id, Model model) {
		model.addAttribute("categories", mobileService.findAllCategories());
		model.addAttribute("mobile", mobileService.findMobileById(id));
		return addMobilePage;
	}

	@PostMapping("/save-mobile")
	public String saveCategory(@Valid @ModelAttribute("mobile") MobileDto mobile, BindingResult bindingResult, RedirectAttributes redAtt, Model model) {
		System.out.println("--------save-mobile-----------");
		model.addAttribute("categories", mobileService.findAllCategories());
		if (bindingResult.hasErrors()) {
			model.addAttribute("mobile", mobile);
			return addMobilePage;
		}
		try {
			if (mobile.getId() == null && mobile.getLogoFile().isEmpty()) {
				redAtt.addFlashAttribute(AppConstants.ALERT_ERROR, "Please upload logoFile");
				return addCategoryPage;
			}
			String outFilePath = fileUploadUtils.uploadFile(mobile.getLogoFile());
			if (outFilePath != null) {
				mobile.setLogoPath(outFilePath);
			}
			mobileService.saveMobile(mobile);
			redAtt.addFlashAttribute(AppConstants.ALERT_SUCCESS, "Mobile saved successfully!");
		} catch (Exception ex) {
			ex.printStackTrace();
			model.addAttribute("mobile", mobile);
			model.addAttribute(AppConstants.ALERT_ERROR, AppConstants.SOMETHING_WRONG_MSG);
		}
		return "redirect:/mobile-store/mobiles";
	}

	@GetMapping("/delete-mobile/{id}")
	public String deleteMobile(@PathVariable Long id, RedirectAttributes redAtt) {
		mobileService.deleteMobileById(id);
		redAtt.addFlashAttribute(AppConstants.ALERT_ERROR, "Mobile deleted successfully!");
		return "redirect:/mobile-store/mobiles";
	}

//	======================================== Mobile-Service =====================================
	@GetMapping({ "/search" })
	public String mobileSearchPage(Model model) {
		model.addAttribute("categories", mobileService.findAllCategories());
		return searchPage;
	}

	@ResponseBody
	@PostMapping("/search")
	public List<LabelValueDT> searchMobile(MobileDto mobileDto) {
		List<LabelValueDT> mobiles = new ArrayList<>();
		for(MobileDto mobile: mobileService.searchMobiles(mobileDto)) {
			mobiles.add(new LabelValueDT(mobile.getId(), mobile.getName(), mobile.getPrice().toString()));
		}
		return mobiles;
	}

	@ResponseBody
	@GetMapping("/get-mobile/{id}")
	public MobileDto getMobile(@PathVariable Long id) {
		return mobileService.findMobileById(id);
	}

}
