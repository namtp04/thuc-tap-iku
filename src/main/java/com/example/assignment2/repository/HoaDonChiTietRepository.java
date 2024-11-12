package com.example.assignment2.repository;

import com.example.assignment2.entity.HoaDonChiTiet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface HoaDonChiTietRepository extends JpaRepository<HoaDonChiTiet, UUID> {
    @Query("select c from HoaDonChiTiet c where c.hoaDon.id=:id")
    List<HoaDonChiTiet> findByIdHoaDon(@Param("id") UUID id);
}
