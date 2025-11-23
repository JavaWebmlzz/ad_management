package com.JavaWebmlzz.ad_management.service;

import com.JavaWebmlzz.ad_management.entity.Advertisement;
import java.util.List;
import java.util.Optional;

public interface AdvertisementService {

    Advertisement createAd(Advertisement ad);

    Advertisement updateAd(Advertisement ad);

    void deleteAd(Long id);

    Optional<Advertisement> getAdById(Long id);

    List<Advertisement> getAllAds();

    List<Advertisement> getAdsByStatus(Integer status);

    boolean changeAdStatus(Long id, Integer status);
}