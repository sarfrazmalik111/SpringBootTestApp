package com.test.modal;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.test.common.AppConstants;
import lombok.Data;

@Data
@Entity
@Table(name = "AppUser")
public class AppUserDto implements Serializable {
	
	private static final long serialVersionUID = 12345L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotBlank
	@Size(min = 3, max = 30)
	private String userName;
	@Column(unique = true)
	@Email(regexp=AppConstants.EMAIL_REGEX, message="Invalid email")
	private String email;
	@Pattern(regexp=AppConstants.MOBILE_REGEX, message="Invalid mobileNo")
	private String mobileNo;
	private String address;
	private String accessToken;
	private boolean deleted = false;
	private LocalDateTime createdOn;
	@Transient
	private String createdOnStr;

}
