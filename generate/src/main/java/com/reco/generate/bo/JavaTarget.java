package com.reco.generate.bo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class JavaTarget extends PageTarget implements Serializable {

    private static final long serialVersionUID = -1465365299424889228L;

    @Override
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < level; i++) {
            stringBuffer.append("\t");
        }
        if (StringUtils.isNotBlank(name)) {
            stringBuffer.append("<%").append(name);
            if (null != attributeMap && attributeMap.entrySet().size() > 0) {
                for (String key : attributeMap.keySet()) {
                    stringBuffer.append(" ").append(key).append("=\"").append(attributeMap.get(key)).append("\"");
                }
            }
            if(StringUtils.isNotBlank(text)) {
                stringBuffer.append(text).append(" ");
            }
            stringBuffer.append("%>");
        } else {
            stringBuffer.append("<%");
            if(StringUtils.isNotBlank(text)) {
                stringBuffer.append(text).append(" ");
            }
            stringBuffer.append("%>");
        }
        return stringBuffer.toString();
    }
}
