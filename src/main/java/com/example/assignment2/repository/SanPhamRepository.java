package com.example.assignment2.repository;

import com.example.assignment2.entity.SanPham;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface SanPhamRepository extends JpaRepository<SanPham, UUID> {
    SanPham findSanPhamById (UUID id);

    SanPham findSanPhamByMa (String ma);

    @Query("select c from SanPham c where c.ten like :ten")
    Page<SanPham> findByTen(@Param("ten")String ten, Pageable pageable);



}
