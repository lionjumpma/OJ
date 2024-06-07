package com.ma.oj.model.dto.questionSubmit;

import lombok.Data;

@Data
public class JudgeInfo {
    private String message;
    private Long memoryLimit;
    //timeLimit
    private Long timeLimit;
}
