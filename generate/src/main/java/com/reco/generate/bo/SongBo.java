package com.reco.generate.bo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class SongBo implements Serializable {

    private static final long serialVersionUID = 8832855149147451194L;

    private Integer index;

    private String songId;

    private Integer width;

    private Integer height;

    private Integer laft;

    private Integer top;

    private String imgName;
}
