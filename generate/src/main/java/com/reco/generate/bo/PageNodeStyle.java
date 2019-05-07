package com.reco.generate.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageNodeStyle {

    private Integer width;

    private Integer height;

    private Integer left;

    private Integer top;

    private String imgName;
}
