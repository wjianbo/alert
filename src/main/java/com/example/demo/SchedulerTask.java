package com.example.demo;

import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SchedulerTask {

    @Autowired
    private EmailService mailService;

    @Autowired
    private ItemService itemService;
    
    @Autowired
    private UserRepository userRepository;

    /**
     * 表示每天7点执行
     */
    @Scheduled(cron = "0 0 7 * * ?")
    private void proces(){
        List<Item> items=itemService.getItemList();
        
    	Map<Integer, String> map = new HashMap<>();
    	map.put(0, "繰り返し事項ではないため、本メッセージは自動的にシステムから削除されました。");
    	map.put(1, "繰り返し事項であるため、本メッセージは自動的に明日に設定されました。");
    	map.put(2, "繰り返し事項であるため、本メッセージは自動的に来週の同じ日に設定されました。");
    	map.put(3, "繰り返し事項であるため、本メッセージは自動的に来月の同じ月に設定されました。");
    	map.put(4, "繰り返し事項であるため、本メッセージは自動的に来年の同じ日に設定されました。");
        
        for (Item item : items) {
                if (item.getDate().toString().equals(LocalDate.now().toString())) {
                	
                	String email;
                	List<User> users = userRepository.findByName(item.getUser());
                	email = users.get(0).getEmail();
                	
                	String content="本日のアラーム事項：\n\n"+(item.getMessage())+"\n\n"+map.get(item.getRepeat())
                	+"アラーム事項の追加あるいは編集は、下記の URL から行ってください。\n\nhttp://34.64.231.12:8060";
	                mailService.sendSimpleMessage(email,"アラーム事項："+item.getMessage(),content);
	                System.out.println("发送定时邮件成功");
	                	                
	                switch (item.getRepeat()) {
	                case 0:
	                    itemService.delete(item.getId());
	                    break;
	                    
	                case 1:
	                	item.setDate(Date.valueOf(item.getDate().toLocalDate().plusDays(1)));
	                    itemService.edit(item);
	                    break;
	                    
	                case 2:
	                	item.setDate(Date.valueOf(item.getDate().toLocalDate().plusWeeks(1)));
	                    itemService.edit(item);
	                    break;
	                    
	                case 3:
	                	item.setDate(Date.valueOf(item.getDate().toLocalDate().plusMonths(1)));
	                    itemService.edit(item);
	                    break;
	                    
	                case 4:
	                	item.setDate(Date.valueOf(item.getDate().toLocalDate().plusYears(1)));
	                    itemService.edit(item);
	                    break;
	                }
	                

                }

        }

    }
}