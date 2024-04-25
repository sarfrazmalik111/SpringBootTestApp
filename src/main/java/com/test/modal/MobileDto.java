package com.test.modal;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;
import lombok.Data;

@Data
@Entity
@Table(name = "Mobile")
public class MobileDto implements Serializable {
	
	private static final long serialVersionUID = 12345L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotNull(message = "Category can not be blank")
	private Long categoryId;
	@NotBlank(message = "Mobile name can not be blank")
	private String name;
	@NotNull(message = "Price can not be blank")
	@Min(value = 100, message = "Price can not be less than Rs.100")
	private Integer price;
	@NotBlank(message = "Description can not be blank")
	private String description;
	private String logoPath;
	private LocalDateTime createdOn;
	@Transient
	private MultipartFile logoFile;

}
