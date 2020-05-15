package com.example.demo;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SchedulerTask {

    @Autowired
    private EmailService mailService;
    
    @Autowired
    private ItemService itemService;

    /**
     * 表示每隔6秒发送一次邮件
     */
    @Scheduled(cron = "*/600 * * * * ?")
    private void proces(){
    	List<Item> items=itemService.getItemList();
    	for (Item item : items) {
    		if (item.getDate().toString().equals(LocalDate.now().toString())) {
    			
    	        String content="本日のアラーム事項：\n\n"+(item.getMessage())+"\n\nhttp://34.64.231.12:8060";
    	        mailService.sendSimpleMessage(item.getEmail(),"アラーム事項："+item.getMessage(),content);
    	        System.out.println("发送定时邮件成功");
    			
    		}
    		
    	}
    	
    }
}
//
//————————————————
//版权声明：本文为CSDN博主「灰太狼_cxh」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
//原文链接：https://blog.csdn.net/weixin_39220472/java/article/details/80213276