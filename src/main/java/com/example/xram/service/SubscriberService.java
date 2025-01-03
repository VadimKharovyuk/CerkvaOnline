package com.example.xram.service;

import com.example.xram.model.Subscriber;
import com.example.xram.repository.SubscriberRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SubscriberService {
    private final SubscriberRepository subscriberRepository;

    public Subscriber save(Subscriber subscriber) {
        return subscriberRepository.save(subscriber);
    }
}
