package com.example.assignment2.repository;

import com.example.assignment2.entity.KhachHang;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface KhachHangRepository extends JpaRepository<KhachHang, UUID> {
    KhachHang findKhachHangByMa(String ma);
    @Query("select c from KhachHang c where c.sdt=:sdt")
    Page<KhachHang> findBySDT(String sdt, Pageable pageable);
}
