package com.frank.cache.entity;

import lombok.Data;

/**
 * @author 小石潭记
 * @date 2020/6/26 11:32
 * @Description: ${todo}
 */
@Data
public class ResultVo {
    private int code;
    private String message;
    private Object data;

    public static ResultVo success(Object object){
        ResultVo resultVo = new ResultVo();
        resultVo.setCode(200);
        resultVo.setMessage("success");
        resultVo.setData(object);
        return resultVo;
    }

    public static ResultVo fail(){
        ResultVo resultVo = new ResultVo();
        resultVo.setCode(404);
        resultVo.setMessage("fail");
        resultVo.setData(null);
        return resultVo;
    }
}
