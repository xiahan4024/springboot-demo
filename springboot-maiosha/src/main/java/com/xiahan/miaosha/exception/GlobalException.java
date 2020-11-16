package com.xiahan.miaosha.exception;

import com.xiahan.miaosha.result.CodeMsg;

/**
 * @Auther: xiahan
 * @Date: 2020/11/10 21:26
 * @Description: 自定义异常
 */
public class GlobalException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    private CodeMsg cm;

    public GlobalException(CodeMsg cm) {
        super(cm.toString());
        this.cm = cm;
    }

    public CodeMsg getCm() {
        return cm;
    }

}

