package com.sprint.sandbox.model.enums;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 提交语言枚举
 */
public enum LanguageEnum {
    JAVA("java", "java"),
    CPLUSPLUS("c++", "c++"),
    PYTHON("python", "python");
    private final String text;
    private final String value;
    // constructor
    LanguageEnum(String text, String value) {
        this.text = text;
        this.value = value;
    }
    /**
     * getValueList
     */
    public static List<String> getValueList() {
        return Arrays.stream(values()).map(item->item.value).collect(Collectors.toList());
    }
    public static LanguageEnum getEnumByValue(String value) {
        for (LanguageEnum anEnum : LanguageEnum.values()) {
            if (anEnum.value.equals(value)) {
                return anEnum;
            }
        }
        return null;
    }
    public String getValue() {
        return value;
    }

    public String getText() {
        return text;
    }

}
