package com.test.modal;

import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Data
@Entity
public class CompanyDto implements Serializable {

    private static final long serialVersionUID = 12345L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private Long userId;
    private String companyName;
    private String address;
    private LocalDateTime createdOn;
    @Transient
    private String createdOnStr;

}