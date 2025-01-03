package com.example.xram.service;

import com.example.xram.model.EService;
import com.example.xram.repository.EServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
@Transactional
@Service
@RequiredArgsConstructor
public class EServiceService {
    private final EServiceRepository eServiceRepository;

    public List<EService> getAll() {
        return eServiceRepository.findAllByActiveOrderByOrderIndexAsc(true);
    }

    public EService getById(Long id) {
        return eServiceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Услуга с ID " + id + " не найдена"));
    }

    public EService save(EService service) {
        if (service.getCreatedAt() == null) {
            service.setCreatedAt(LocalDateTime.now());
        }
        if (service.getOrderIndex() == null) {
            service.setOrderIndex(getNextOrderIndex());
        }
        return eServiceRepository.save(service);
    }

    public void delete(Long id) {
        EService service = getById(id);
        service.setActive(false);
        eServiceRepository.save(service);
    }

    private Integer getNextOrderIndex() {
        return eServiceRepository.findTopByOrderByOrderIndexDesc()
                .map(service -> service.getOrderIndex() + 1)
                .orElse(1);
    }

    public EService saveWithImage(EService service, MultipartFile imageFile) throws IOException {
        service.setImageData(imageFile.getBytes());
        service.setImageType(imageFile.getContentType());
        service.setCreatedAt(LocalDateTime.now());
        return eServiceRepository.save(service);
    }

    public byte[] getImageById(Long serviceId) {
        EService service = getById(serviceId);
        return service.getImageData();
    }

    public String getImageType(Long serviceId) {
        EService service = getById(serviceId);
        return service.getImageType();
    }
}