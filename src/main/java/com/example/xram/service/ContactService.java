package com.example.xram.service;

import com.example.xram.model.ContactRequest;
import com.example.xram.repository.ContactRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ContactService {
    private final ContactRequestRepository repository;

    public ContactRequest save(ContactRequest contactRequest) {
        contactRequest.setCreatedAt(LocalDateTime.now());
        return repository.save(contactRequest);
    }
    public List<ContactRequest> findAll() {
        return repository.findAll();
    }
}
