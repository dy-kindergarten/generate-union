package com.reco.generate.bo;

import com.google.common.collect.Maps;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Map;

@Data
@NoArgsConstructor
public class PageTarget implements Serializable {

    private static final long serialVersionUID = 2622408440765440641L;

    protected String name;

    protected String text;

    protected int level;

    protected Map<String, String> attributeMap = Maps.newHashMap();
}
