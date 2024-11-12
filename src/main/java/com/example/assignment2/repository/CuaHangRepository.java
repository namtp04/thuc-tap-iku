package com.example.assignment2.repository;

import com.example.assignment2.entity.CuaHang;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CuaHangRepository extends JpaRepository<CuaHang, UUID> {
    CuaHang findCuaHangByMa(String ma);

    @Query("select c from CuaHang c where c.ten like :ten")
    Page<CuaHang> findByTen(@Param("ten") String ten, Pageable pageable);
}
