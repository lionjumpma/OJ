package com.ma.oj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ma.oj.mapper.QuestionSubmitMapper;
import com.ma.oj.model.dto.questionSubmit.QuestionSubmitAddRequest;
import com.ma.oj.model.entity.QuestionSubmit;
import com.ma.oj.model.entity.User;
import com.ma.oj.model.enums.QuestionSubmitStatusEnum;
import com.ma.oj.model.vo.QuestionSubmitVO;
import com.ma.oj.service.QuestionSubmitService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
* @author mayixiang
* @description 针对表【question_submit(题目提交)】的数据库操作Service实现
* @createDate 2024-02-20 09:34:00
*/
@Service
public class QuestionSubmitServiceImpl extends ServiceImpl<QuestionSubmitMapper, QuestionSubmit>
    implements QuestionSubmitService {

    @Resource
    private QuestionSubmitMapper questionSubmitMapper;
    @Override
    public Long doQuestionSubmit(QuestionSubmitAddRequest questionSubmitAddRequest, User loginUser) {
     String language = questionSubmitAddRequest.getLanguage();
     String code = questionSubmitAddRequest.getCode();
     Long questionId = questionSubmitAddRequest.getQuestionId();
        // 校验
        if (questionId == null || questionId <= 0 || loginUser == null || loginUser.getId() <= 0) {
            return 0L;
        }
        QuestionSubmit questionSubmit = new QuestionSubmit();
        questionSubmit.setLanguage(language);
        questionSubmit.setCode(code);
        questionSubmit.setJudgeInfo("{}");
        questionSubmit.setStatus(QuestionSubmitStatusEnum.WAITING.getValue());
        questionSubmit.setQuestionId(questionId);
        questionSubmit.setUserId(loginUser.getId());
        boolean save = save(questionSubmit);
        if (!save) {
            throw new RuntimeException("题目提交失败");
        }

        Long submitId = questionSubmit.getId();


        return submitId;
    }


    @Override
    public QuestionSubmitVO getQuestionSubmitVO(QuestionSubmit questionSubmit, HttpServletRequest request) {
        return null;
    }

}




