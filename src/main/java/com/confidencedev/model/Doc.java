package com.confidencedev.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Doc {
    private Integer userId;
    private Integer id;
    private String title;
    private String body;
}
