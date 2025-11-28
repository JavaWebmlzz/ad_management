package com.JavaWebmlzz.ad_management.repository;

import com.JavaWebmlzz.ad_management.entity.Advertisement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AdvertisementRepository extends JpaRepository<Advertisement, Long> {

    List<Advertisement> findByStatus(Integer status);

    List<Advertisement> findByAdSpaceIdAndStatus(Long adSpaceId, Integer status);

    @Query("SELECT a FROM Advertisement a WHERE a.status = 1 AND a.startTime <= :now AND (a.endTime IS NULL OR a.endTime >= :now)")
    List<Advertisement> findActiveAdvertisements(@Param("now") LocalDateTime now);

    @Query("SELECT a FROM Advertisement a WHERE a.adSpaceId = :adSpaceId AND a.status = 1 AND a.startTime <= :now AND (a.endTime IS NULL OR a.endTime >= :now)")
    List<Advertisement> findActiveAdvertisementsBySpace(@Param("adSpaceId") Long adSpaceId, @Param("now") LocalDateTime now);
}
