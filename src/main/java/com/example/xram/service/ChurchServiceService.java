package com.example.xram.service;

import com.example.xram.dto.ChurchServiceDto;
import com.example.xram.model.ChurchService;
import com.example.xram.repository.ChurchServiceRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChurchServiceService {

    private final ChurchServiceRepository churchServiceRepository;

    public List<ChurchService> getServicesByDate(LocalDate date) {
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(LocalTime.MAX);
        return churchServiceRepository.findByStartTimeBetweenAndIsActiveOrderByStartTime(
                startOfDay, endOfDay, true);
    }

    public List<ChurchService> getUpcomingServices() {
        return churchServiceRepository.findUpcomingServices();
    }

    @Transactional
    public ChurchService createService(ChurchServiceDto serviceDto) {
        if (serviceDto.getStartTime().isAfter(serviceDto.getEndTime())) {
            throw new IllegalArgumentException("Время начала должно быть раньше времени окончания");
        }

        ChurchService service = new ChurchService();
        service.setName(serviceDto.getName());
        service.setStartTime(serviceDto.getStartTime());
        service.setEndTime(serviceDto.getEndTime());
        service.setPriestName(serviceDto.getPriestName());
        service.setDescription(serviceDto.getDescription());
        service.setActive(true);

        return churchServiceRepository.save(service);
    }

    @Transactional
    public ChurchService updateService(Long id, ChurchServiceDto serviceDto) {
        ChurchService service = churchServiceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Служба не найдена"));

        service.setName(serviceDto.getName());
        service.setStartTime(serviceDto.getStartTime());
        service.setEndTime(serviceDto.getEndTime());
        service.setPriestName(serviceDto.getPriestName());
        service.setDescription(serviceDto.getDescription());

        return churchServiceRepository.save(service);
    }

    @Transactional
    public void deleteService(Long id) {
        ChurchService service = churchServiceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Служба не найдена"));
        service.setActive(false);
        churchServiceRepository.save(service);
    }

    public ChurchService getServiceById(Long id) {
        return churchServiceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Служба не найдена"));
    }
}