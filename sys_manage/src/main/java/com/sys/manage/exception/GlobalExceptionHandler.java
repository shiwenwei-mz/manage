package com.sys.manage.exception;

import com.alibaba.fastjson.JSONException;
import com.sys.manage.enums.StatusCodeEnum;
import com.sys.manage.utils.response.ResponseMsg;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.UnexpectedTypeException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Title: GlobalExceptionHandler
 * @Description: 全局异常处理
 * @author： sww
 * @date 2019/1/10
 **/
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = BindException.class)
    @ResponseBody
    public ResponseMsg handle(BindException e, BindingResult bindingResult) {
        e.printStackTrace();
        return new ResponseMsg(StatusCodeEnum.INVALID_REQUEST);
    }

    @ExceptionHandler(value = UnexpectedTypeException.class)
    @ResponseBody
    public ResponseMsg handle(UnexpectedTypeException e) {
        e.printStackTrace();
        return new ResponseMsg(StatusCodeEnum.INVALID_REQUEST);
    }

    @ExceptionHandler(value = JSONException.class)
    @ResponseBody
    public ResponseMsg handle(JSONException e) {
        e.printStackTrace();
        return new ResponseMsg(StatusCodeEnum.INVALID_REQUEST);
    }

  /*  @ExceptionHandler(value = AuthorizationException.class)
    @ResponseBody
    public ResponseMsg handle(AuthorizationException e) {
        return new ResponseMsg(StatusCodeEnum.FORBIDDEN);
    }
*/
    /**
     * 拦截系统异常
     *
     * @param e Exception
     * @return Result
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResponseMsg handle(Exception e) {
        e.printStackTrace();
        return new ResponseMsg(StatusCodeEnum.INTERNAL_SERVER_ERROR);
    }

    /**
     * 拦截捕捉自定义异常 RequestParameterException.class
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = RequestParameterException.class)
    public Map myErrorHandler(RequestParameterException ex) {
        Map map = new HashMap();
        map.put("code", ex.getCode());
        map.put("msg", ex.getMsg());
        return map;
    }
}
