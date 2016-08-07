package cn.lfy.base.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexView {

	@RequestMapping("index")
    public String index() {
        return "index";
    }
}
