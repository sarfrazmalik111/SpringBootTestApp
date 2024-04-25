package com.test.dao;

import com.test.modal.MobileDto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MobileRepository extends JpaRepository<MobileDto, Long> {

}
