package com.weiwei.jixieche.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName ShortMsgVo
 * @Description TODO
 * @Author houji
 * @Date 2019/8/23 16:38
 * @Version 1.0.1
 **/
@Data
@EqualsAndHashCode
@ApiModel(value="消息记录修改Vo")
public class JpushRecordUpdateVo implements Serializable {

    @ApiModelProperty("修改的记录id集合")
    private List<Integer> recordIdList;

}
