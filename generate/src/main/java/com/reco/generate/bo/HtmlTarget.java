package com.reco.generate.bo;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.reco.generate.bo.enumEntity.EndTypeEnum;
import com.reco.generate.bo.enumEntity.SortEnum;
import com.reco.generate.bo.enumEntity.TargetTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class HtmlTarget extends PageTarget implements Serializable {

    private static final long serialVersionUID = -1465365299424889228L;

    private int endIndentCount;

    private EndTypeEnum endType = EndTypeEnum.END_WITH_TARGET;

    private SortEnum sort = SortEnum.TEXT;

    private List<PageTarget> childrenList = Lists.newArrayList();

    @Override
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        if (StringUtils.isNotBlank(name)) {
            if (level == 0) {
                stringBuffer.append("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">\n");
            }
            // 缩进
            for (int i = 0; i < level; i++) {
                stringBuffer.append("\t");
            }
            stringBuffer.append("<").append(name);
            if (null != attributeMap && attributeMap.entrySet().size() > 0) {
                for (String key : attributeMap.keySet()) {
                    stringBuffer.append(" ").append(key).append("=\"").append(attributeMap.get(key)).append("\"");
                }
            }
            Boolean endTarget = false;
            switch (endType) {
                case NO_END:
                    stringBuffer.append(">");
                    break;
                case END_WITH_TERMINATOR:
                    stringBuffer.append(" />");
                    break;
                case END_WITH_TARGET:
                    stringBuffer.append(">");
                    endTarget = true;
                    break;
                default:
                    break;
            }

            switch (sort) {
                case TEXT:
                    if (StringUtils.isNotBlank(text)) {
                        stringBuffer.append(text);
                    }
                    if (childrenList.size() > 0) {
                        stringBuffer.append("\n");
                        for (PageTarget target : childrenList) {
                            stringBuffer.append(target.toString()).append("\n");
                        }
                    }
                    break;
                case CHILD_NODE:
                    if (childrenList.size() > 0) {
                        stringBuffer.append("\n");
                        for (PageTarget target : childrenList) {
                            stringBuffer.append(target.toString()).append("\n");
                        }
                        stringBuffer.append("\n");
                    }
                    if (StringUtils.isNotBlank(text)) {
                        stringBuffer.append(text);
                    }
                    break;
                default:
                    break;
            }

            if (endTarget) {
                for (int i = 0; i < endIndentCount; i++) {
                    stringBuffer.append("\t");
                }
                stringBuffer.append("</").append(name).append(">");
            }
        }
        return stringBuffer.toString();
    }


}
