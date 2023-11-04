package com.github.nanoyou.akariyumetabackend.common.enumeration;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum TaskCategory {
    /**
     * 农业
     */
    AGRICULTURE("农业"),
    /**
     * 牧业
     */
    ANIMAL_HUSBANDRY("牧业"),
    /**
     * 语言
     */
    LANGUAGE("语言"),
    /**
     * 科学
     */
    SCIENCE("科学"),
    /**
     * 卫生
     */
    HYGIENE("卫生"),
    /**
     * 社会
     */
    SOCIETY("社会"),
    /**
     * 历史
     */
    HISTORY("历史"),
    /**
     * 政治
     */
    POLITICS("政治");

    public final String value;
}
