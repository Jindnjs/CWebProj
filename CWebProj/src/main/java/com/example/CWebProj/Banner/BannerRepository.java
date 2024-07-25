package com.example.CWebProj.Banner;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BannerRepository extends JpaRepository<Banner, Integer> {

    List<Banner> findAllByOrderByNumAsc();
}
