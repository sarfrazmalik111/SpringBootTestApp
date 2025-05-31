package com.test.modal;

import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class TestData implements Serializable {
    int id;
    String testName;
    String testAddress;
    String testCity;
    String testState;
    int testAId;
    LocalDateTime createdOn;

}
