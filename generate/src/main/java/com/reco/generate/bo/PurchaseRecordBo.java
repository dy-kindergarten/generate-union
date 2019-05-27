package com.reco.generate.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseRecordBo implements Serializable {

    private String userId;

    private Date payTime;

    private String songName;

    private Integer songId;
}
