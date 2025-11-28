package com.JavaWebmlzz.ad_management.service.impl;

import com.JavaWebmlzz.ad_management.entity.Advertisement;
import com.JavaWebmlzz.ad_management.repository.AdvertisementRepository;
import com.JavaWebmlzz.ad_management.service.AdvertisementService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdvertisementServiceImpl implements AdvertisementService {

    private final AdvertisementRepository advertisementRepository;

    @Override
    public Advertisement createAd(Advertisement ad) {
        return advertisementRepository.save(ad);
    }

    @Override
    public Advertisement updateAd(Advertisement ad) {
        return advertisementRepository.save(ad);
    }

    @Override
    public void deleteAd(Long id) {
        advertisementRepository.deleteById(id);
    }

    @Override
    public Optional<Advertisement> getAdById(Long id) {
        return advertisementRepository.findById(id);
    }

    @Override
    public List<Advertisement> getAllAds() {
        return advertisementRepository.findAll();
    }

    @Override
    public List<Advertisement> getAdsByStatus(Integer status) {
        return advertisementRepository.findByStatus(status);
    }

    @Override
    public boolean changeAdStatus(Long id, Integer status) {
        Optional<Advertisement> adOpt = advertisementRepository.findById(id);
        if (adOpt.isPresent()) {
            Advertisement ad = adOpt.get();
            ad.setStatus(status);
            advertisementRepository.save(ad);
            return true;
        }
        return false;
    }
}