package com.example.demo;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ItemController {

    @Resource
    ItemService itemService;


    @RequestMapping("/")
    public String index() {
        return "redirect:/list";
    }

    @RequestMapping("/list")
    public String list(Model model) {
        List<Item> items=itemService.getItemList();
        
    	UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	Map<Integer, String> map = new HashMap<>();
    	map.put(0, "No");
    	map.put(1, "Every day");
    	map.put(2, "Every week");
    	map.put(3, "Every month");
    	map.put(4, "Every year");
        model.addAttribute("items", items);
        model.addAttribute("list", map);
        model.addAttribute("name", userDetails.getUsername());

        return "item/list";
    }

    @RequestMapping("/toAdd")
    public String toAdd(Model model) {
    	
    	model.addAttribute("today", LocalDate.now().plusDays(1));
    	
    	Map<Integer, String> map = new HashMap<>();
    	map.put(0, "No");
    	map.put(1, "Every day");
    	map.put(2, "Every week");
    	map.put(3, "Every month");
    	map.put(4, "Every year");

    	model.addAttribute("list", map);

    	
        return "item/itemAdd";
    }

    @RequestMapping("/add")
    public String add(Item item) {
    	UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        item.setUser(userDetails.getUsername());
    	itemService.save(item);
        return "redirect:/list";
    }

    @RequestMapping("/toEdit")
    public String toEdit(Model model,Long id) {
        Item item=itemService.findItemById(id);
        
    	Map<Integer, String> map = new HashMap<>();
    	map.put(0, "No");
    	map.put(1, "Every day");
    	map.put(2, "Every week");
    	map.put(3, "Every month");
    	map.put(4, "Every year");
    	model.addAttribute("list", map);
        model.addAttribute("item", item);
        return "item/itemEdit";
    }

    @RequestMapping("/edit")
    public String edit(Item item) {
        itemService.edit(item);
        return "redirect:/list";
    }


    @RequestMapping("/delete")
    public String delete(Long id) {
        itemService.delete(id);
        return "redirect:/list";
    }
}