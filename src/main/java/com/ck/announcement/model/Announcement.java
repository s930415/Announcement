package com.ck.announcement.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "announcement")
public class Announcement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    private String content;
    private String author;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate publishDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate expireDate;
    private String filePath;

    // 確保截止日期大於發布日期
    public boolean isValidDateRange() {
        return expireDate == null || publishDate == null || expireDate.isAfter(publishDate);
    }
}
