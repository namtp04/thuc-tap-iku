package com.example.assignment2.repository;

import com.example.assignment2.entity.GioHang;
import com.example.assignment2.entity.HoaDon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface GioHangRepository extends JpaRepository<GioHang, UUID> {
    @Query("select c from GioHang c where c.khachHang.id=:id")
    List<GioHang> getByIdKhachHang(@Param("id")UUID id);
}
