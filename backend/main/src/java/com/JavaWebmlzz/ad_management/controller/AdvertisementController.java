package com.JavaWebmlzz.ad_management.controller;

import com.JavaWebmlzz.ad_management.dto.ApiResponse;
import com.JavaWebmlzz.ad_management.entity.Advertisement;
import com.JavaWebmlzz.ad_management.service.AdvertisementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/ads")
@RequiredArgsConstructor
public class AdvertisementController {

    private final AdvertisementService advertisementService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Advertisement>>> getAllAds() {
        List<Advertisement> ads = advertisementService.getAllAds();
        return ResponseEntity.ok(ApiResponse.success(ads));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Advertisement>> getAdById(@PathVariable Long id) {
        Optional<Advertisement> ad = advertisementService.getAdById(id);
        return ad.map(advertisement -> ResponseEntity.ok(ApiResponse.success(advertisement)))
                .orElseGet(() -> ResponseEntity.badRequest().body(ApiResponse.error("广告不存在")));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Advertisement>> createAd(@RequestBody Advertisement ad) {
        Advertisement newAd = advertisementService.createAd(ad);
        return ResponseEntity.ok(ApiResponse.success(newAd));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Advertisement>> updateAd(@PathVariable Long id, @RequestBody Advertisement ad) {
        ad.setId(id);
        Advertisement updatedAd = advertisementService.updateAd(ad);
        return ResponseEntity.ok(ApiResponse.success(updatedAd));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteAd(@PathVariable Long id) {
        advertisementService.deleteAd(id);
        return ResponseEntity.ok(ApiResponse.success("删除成功"));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<ApiResponse<String>> changeAdStatus(@PathVariable Long id, @RequestParam Integer status) {
        boolean success = advertisementService.changeAdStatus(id, status);
        if (success) {
            return ResponseEntity.ok(ApiResponse.success("状态更新成功"));
        }
        return ResponseEntity.badRequest().body(ApiResponse.error("状态更新失败"));
    }
}