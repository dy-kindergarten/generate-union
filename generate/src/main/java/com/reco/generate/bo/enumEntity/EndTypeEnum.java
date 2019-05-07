package com.reco.generate.bo.enumEntity;

public enum EndTypeEnum {

    END_WITH_TERMINATOR(1, "结束符"),
    END_WITH_TARGET(2, "结束标签"),
    NO_END(3, "无结束符");

    private int value;

    private String name;

    EndTypeEnum(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public EndTypeEnum getByValue(int value) {
        for (EndTypeEnum typeEnum : EndTypeEnum.values()) {
            if(typeEnum.getValue() == value) {
                return typeEnum;
            }
        }
        return NO_END;
    }
}
