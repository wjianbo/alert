package com.example.demo;

import java.time.LocalDate;
import java.util.List;

import javax.annotation.Resource;

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
        model.addAttribute("items", items);
        return "item/list";
    }

    @RequestMapping("/toAdd")
    public String toAdd(Model model) {
    	
    	model.addAttribute("today", LocalDate.now().plusDays(1));
    	
        return "item/itemAdd";
    }

    @RequestMapping("/add")
    public String add(Item item) {
        itemService.save(item);
        return "redirect:/list";
    }

    @RequestMapping("/toEdit")
    public String toEdit(Model model,Long id) {
        Item item=itemService.findItemById(id);
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