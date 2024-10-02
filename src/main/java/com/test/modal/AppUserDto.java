package com.test.modal;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
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
    @Email(message="Invalid email address")
    private String emailId;
    @Pattern(regexp = "91\\d{10}", message="Invalid mobileNo")
    private String mobileNo;
    private String password;;
    private String address;
    private boolean active = true;
    private LocalDateTime createdOn;
    @Transient
    private String createdOnStr;

}