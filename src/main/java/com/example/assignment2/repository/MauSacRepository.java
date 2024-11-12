package com.example.assignment2.repository;

import com.example.assignment2.entity.MauSac;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MauSacRepository extends JpaRepository<MauSac, UUID> {
    MauSac findMauSacById(UUID id);

    MauSac findMauSacByMa(String ma);

    @Query("select c from MauSac c where c.ten like :ten")
    Page<MauSac> findByTen(@Param("ten") String ten, Pageable pageable);
}
