package com.test.modalDT;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LabelValueDT {
    private Long value;
    private String label;
    private String desc;
}
