package com.ma.oj.model.enums;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum QuestionSubmitStatusEnum {
    WAITING("等待中", 0),
    RUNNING("判题中", 1),
    SUCCEED("成功", 2),
    FAILED("失败", 3);

    private final String text;
    private final Integer value;

    QuestionSubmitStatusEnum(String text, Integer value) {
        this.text = text;
        this.value = value;
    }

    /**
     * get values
     */
    public static List<Integer> getValues() {
        return Arrays.stream(values()).map(item->item.value).collect(Collectors.toList());
    }

    /**
     * get enum by value
     * @return
     */
    public static QuestionSubmitStatusEnum getEnumByValue(Integer value) {
        if(value == null) return null;
        for (QuestionSubmitStatusEnum item : values()) {
            if (item.value.equals(value)) {
                return item;
            }
        }
        return null;
    }

    public String getText() {
        return text;
    }
    public Integer getValue() {
        return value;
    }
}
