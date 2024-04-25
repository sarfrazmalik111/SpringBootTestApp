package com.test.modal;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;
import lombok.Data;

@Data
@Entity
@Table(name = "Category")
public class CategoryDto implements Serializable {
	
	private static final long serialVersionUID = 12345L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotBlank(message = "Category name can not be blank")
	private String name;
	@NotBlank(message = "Description can not be blank")
	private String description;
	private String logoPath;
	private LocalDateTime createdOn;
	@Transient
	private MultipartFile logoFile;
	@Transient
	private Integer productCount = 0;

}
