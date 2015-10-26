package com.yimayhd.base;

import com.yimayhd.constant.ResponseStatus;
import com.yimayhd.exception.NoticeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.InvalidPropertyException;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author wenfeng zhang
 */
public class BaseController {

    private static final Logger logger = LoggerFactory.getLogger(BaseController.class);

    @ExceptionHandler(Exception.class)
    @ResponseBody
    protected ResponseVo handleException(Exception e) {
        logger.error(e.getMessage(), e);
        if (e instanceof HttpMessageNotReadableException ||
            e instanceof NumberFormatException ||
            e instanceof InvalidPropertyException) {
            return new ResponseVo(ResponseStatus.DATA_PARSE_ERROR.VALUE, ResponseStatus.DATA_PARSE_ERROR.MESSAGE + e.getLocalizedMessage());
        } else if (e instanceof NoticeException) {
            return new ResponseVo(ResponseStatus.FORBIDDEN.VALUE, e.getMessage());
        }
        return new ResponseVo(ResponseStatus.ERROR.VALUE, ResponseStatus.ERROR.MESSAGE + e.getLocalizedMessage());
    }
    @InitBinder
    public void initBinder(ServletRequestDataBinder binder){
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"),
                true));
    }

}
