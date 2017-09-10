package cn.lfy.base.service;

import org.springframework.stereotype.Service;

import com.meizu.yard.spring.YardSpringConfigTextListener;

@Service
public class TextListener extends YardSpringConfigTextListener {

	@Override
	protected void receiveConfigText(String content) {
		System.out.println("更新了Text内容：" + content);
	}

	@Override
	public String getPath() {
		return "game-center/order2/V1.4";
	}
	
	

}
