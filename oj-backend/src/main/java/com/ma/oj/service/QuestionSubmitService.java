package com.ma.oj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ma.oj.model.dto.questionSubmit.QuestionSubmitAddRequest;
import com.ma.oj.model.entity.QuestionSubmit;
import com.ma.oj.model.entity.User;
import com.ma.oj.model.vo.QuestionSubmitVO;

import javax.servlet.http.HttpServletRequest;

/**
* @author mayixiang
* @description 针对表【question_submit(题目提交)】的数据库操作Service
* @createDate 2024-02-20 09:34:00
*/
public interface QuestionSubmitService extends IService<QuestionSubmit> {
    /**
     * 提交题目
     * @param questionSubmitAddRequest
     * @param loginUser
     * @return
     */
    Long doQuestionSubmit(QuestionSubmitAddRequest questionSubmitAddRequest,User loginUser);

    public QuestionSubmitVO getQuestionSubmitVO(QuestionSubmit questionSubmit, HttpServletRequest request);
}
