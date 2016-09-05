package cn.lfyun.wx.service;

import java.util.Random;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import cn.lfyun.wx.annotation.CircuitBreaker;

@Service
@Scope("singleton")
public class RandomBean {
 
 
    private final Random random;
 
 
    public RandomBean() {
        random = new Random();
    }
 
 
    @CircuitBreaker
    public String random() {
    	try {
			Thread.sleep(15);
		} catch (InterruptedException e) {
			
		}
        return "success + Random " + random.nextInt();
    }
}
