package com.ck.announcement.controller;

import com.ck.announcement.service.AnnouncementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/announcements")
public class AnnouncementRestController {

    @Autowired
    private AnnouncementService service;

    // 刪除公告（回傳 JSON 給前端）
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAnnouncement(@PathVariable Integer id) {
        boolean deleted = service.deleteById(id);
        if (deleted) {
            return ResponseEntity.ok("公告刪除成功");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("公告不存在");
        }
    }
}
