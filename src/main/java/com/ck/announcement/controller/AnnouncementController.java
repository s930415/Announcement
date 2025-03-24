package com.ck.announcement.controller;

import com.ck.announcement.model.Announcement;
import com.ck.announcement.service.AnnouncementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/announcements")
public class AnnouncementController {

    @Autowired
    private AnnouncementService service;

    // 顯示公告列表
    @GetMapping(value = "/list")
    public String listAnnouncements(Model model) {
        List<Announcement> announcements = service.getAll();
        model.addAttribute("announcements", announcements);
        return "announcement-list";  // Thymeleaf 會載入 templates/announcement-list.html
    }

    @GetMapping(value = "/add")
    public String addAnnouncements(Model model) {
        List<Announcement> announcements = service.getAll();
        model.addAttribute("announcements", announcements);
        return "announcement-add";  // Thymeleaf 會載入 templates/announcement-list.html
    }

    // 顯示公告列表
    @GetMapping("/edit/{id}")
    public String addAnnouncements(@PathVariable Integer id, Model model) {
        Optional<Announcement> announcement = service.getById(id);
        if (announcement.isPresent()) {
            model.addAttribute("announcement", announcement.get());
            return "announcement-edit";  // Thymeleaf 會載入 templates/announcement-edit.html
        }
        return "redirect:/announcements/list";  // 如果找不到公告，返回列表頁
    }

    @PostMapping("/save")
    public String saveAnnouncement(@ModelAttribute Announcement announcement, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("successMessage", "公告新增成功！");
        service.save(announcement);
        return "redirect:/announcements/list";  // 儲存後重新導向到公告列表
    }

    @PostMapping("/delete/{id}")
    public String saveAnnouncement(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("successMessage", "公告刪除成功！");
        service.deleteById(id);
        return "redirect:/announcements/list";  // 儲存後重新導向到公告列表
    }
}
