package com.reco.generate.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseRecordBo implements Serializable {

    private Integer songId;

    private String songName;

    private Long purchaseCount;
}
