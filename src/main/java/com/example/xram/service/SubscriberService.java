package com.example.xram.service;

import com.example.xram.model.Subscriber;
import com.example.xram.repository.SubscriberRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SubscriberService {
    private final SubscriberRepository subscriberRepository;

    public Subscriber save(Subscriber subscriber) {
        return subscriberRepository.save(subscriber);
    }
    public List<Subscriber> findAll() {
        return subscriberRepository.findAll();
    }
}
