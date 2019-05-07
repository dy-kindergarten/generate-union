package com.reco.generate.bo.enumEntity;

public enum TargetTypeEnum {

    JAVA_CODE(1, "java程序代码、声明"),
    JAVA_PAGE(2, "编译器指令、表达式等");

    private int value;

    private String name;

    TargetTypeEnum(int value, String name) {
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

    public TargetTypeEnum getByValue(int value) {
        for (TargetTypeEnum typeEnum : TargetTypeEnum.values()) {
            if(typeEnum.getValue() == value) {
                return typeEnum;
            }
        }
        return JAVA_CODE;
    }
}
