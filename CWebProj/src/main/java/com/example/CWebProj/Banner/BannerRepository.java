package com.example.CWebProj.Banner;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BannerRepository extends JpaRepository<Banner, Integer> {

    List<Banner> findAllByOrderByNumAsc();
}
