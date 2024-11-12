package com.example.assignment2.repository;

import com.example.assignment2.entity.DongSanPham;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DongSanPhamRepository extends JpaRepository<DongSanPham, UUID> {
    DongSanPham findDongSanPhamById (UUID id);
    DongSanPham findDongSanPhamByMa (String ma);
    @Query("select c from DongSanPham c where c.ten like :ten")
    Page<DongSanPham> findByTen(@Param("ten")String ten, Pageable pageable);
}
