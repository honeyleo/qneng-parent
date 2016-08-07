package cn.lfy.base.web;

import java.util.Date;

import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import cn.lfy.common.framework.DateEditor;

/**
 * @author Robert HG (254963746@qq.com) on 5/9/15.
 */
public class AbstractMVC {

    @InitBinder
    protected void initBinder(ServletRequestDataBinder binder) throws Exception {
        //对于需要转换为Date类型的属性，使用DateEditor进行处理
        binder.registerCustomEditor(Date.class, new DateEditor());
    }

}
