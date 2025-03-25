package com.ck.announcement.service;


import com.ck.announcement.model.Announcement;
import com.ck.announcement.repository.AnnouncementRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class AnnouncementService {

    @Autowired
    private AnnouncementRepository repository;

    public List<Announcement> getAll() {
        return repository.findAll();
    }

    public Optional<Announcement> getById(Integer id) {
        return repository.findById(id);
    }

    public Announcement save(Announcement announcement) {
        return repository.save(announcement);
    }

    public boolean deleteById(Integer id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    public Page<Announcement> getAnnouncements(Pageable pageable) {
        return repository.findAll(pageable);
    }
}
