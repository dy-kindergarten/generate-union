package com.reco.generate.bo.enumEntity;

public enum SortEnum {

    TEXT(1, "文本优先"),
    CHILD_NODE(2, "子节点优先");

    private int value;

    private String name;

    SortEnum(int value, String name) {
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

    public SortEnum getByValue(int value) {
        for (SortEnum sortEnum : SortEnum.values()) {
            if(sortEnum.getValue() == value) {
                return sortEnum;
            }
        }
        return TEXT;
    }
}
