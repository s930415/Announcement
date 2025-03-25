package com.ck.announcement.controller;

import com.ck.announcement.model.Announcement;
import com.ck.announcement.service.AnnouncementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/announcements")
public class AnnouncementController {

    @Autowired
    private AnnouncementService service;

    @Value("${file.upload-dir}")
    private String uploadDir;

    @GetMapping(value = "/list")
    public String listAnnouncements(Model model,@RequestParam(defaultValue = "0") int page,@RequestParam (defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Announcement> announcementPage = service.getAnnouncements(pageable);
        model.addAttribute("announcementPage", announcementPage);
        return "announcement-list";
    }

    @GetMapping(value = "/add")
    public String addAnnouncements(Model model) {
        return "announcement-add";
    }

    // 顯示公告列表
    @GetMapping("/edit/{id}")
    public String addAnnouncements(@PathVariable Integer id, Model model) {
        Optional<Announcement> announcement = service.getById(id);
        if (announcement.isPresent()) {
            model.addAttribute("announcement", announcement.get());
            return "announcement-edit";
        }
        return "redirect:/announcements/list";
    }

    @PostMapping("/save")
    public String saveAnnouncement(@ModelAttribute Announcement announcement, @RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) throws IOException {
        try {
            if (!file.isEmpty()) {
                // 設定上傳目錄
                String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                Path filePath = Paths.get(uploadDir, fileName);  // ✅ 修正 Paths.get() 寫法
                Files.createDirectories(filePath.getParent());  // ✅ 防止 getParent() 為 null
                file.transferTo(filePath.toFile());
                announcement.setFilePath(fileName);
            }
            service.save(announcement);
            redirectAttributes.addFlashAttribute("successMessage", "公告新增成功！");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errorMessage", "檔案上傳失敗：" + e.getMessage());
        }

        return "redirect:/announcements/list";
    }

    @PostMapping("/delete/{id}")
    public String saveAnnouncement(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("successMessage", "公告刪除成功！");
        service.deleteById(id);
        return "redirect:/announcements/list";
    }
}
