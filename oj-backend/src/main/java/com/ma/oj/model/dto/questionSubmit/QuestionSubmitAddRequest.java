package com.ma.oj.model.dto.questionSubmit;

import lombok.Data;

import java.io.Serializable;

/**
 * 帖子点赞请求

 */
@Data
public class QuestionSubmitAddRequest implements Serializable {
    /**
     * language
     */
    private String language;
    /**
     * code
     */
    private String code;

    /**
     * 帖子 id
     */
    private Long questionId;


    private static final long serialVersionUID = 1L;
}