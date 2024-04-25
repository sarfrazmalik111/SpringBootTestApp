package com.test.dao;

import com.test.modal.CategoryDto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<CategoryDto, Long> {

}
