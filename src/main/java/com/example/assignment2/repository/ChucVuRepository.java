package com.example.assignment2.repository;

import com.example.assignment2.entity.ChucVu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ChucVuRepository extends JpaRepository<ChucVu, UUID> {

    ChucVu findChucVuByMa(String ma);

    @Query("select c from ChucVu c where c.ten like :ten")
    Page<ChucVu> findByTen(@Param("ten") String ten, Pageable pageable);
}
