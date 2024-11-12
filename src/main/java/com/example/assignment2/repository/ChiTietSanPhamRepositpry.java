package com.example.assignment2.repository;

import com.example.assignment2.entity.ChiTietSanPham;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ChiTietSanPhamRepositpry extends JpaRepository<ChiTietSanPham, UUID> {
    @Query("select c from ChiTietSanPham c where c.sanPham.id=:idSP and c.dongSanPham.id=:idDSP and c.mauSac.id=:idCL")
    Page<ChiTietSanPham> filter(@Param("idSP")UUID idSP, @Param("idDSP")UUID idDSP, @Param("idCL")UUID idCL, Pageable pageable);

    @Query("select c from ChiTietSanPham c where c.sanPham.id=:id")
    List<ChiTietSanPham> getByIdSanPham(@Param("id") UUID id);

    @Query("select c from ChiTietSanPham c where c.mauSac.id=:id")
    List<ChiTietSanPham> getByIdMauSac(@Param("id") UUID id);

    @Query("select c from ChiTietSanPham c where c.nhaSanXuat.id=:id")
    List<ChiTietSanPham> getByIdNhaSanXuat(@Param("id") UUID id);

    @Query("select c from ChiTietSanPham c where c.dongSanPham.id=:id")
    List<ChiTietSanPham> getByIdDongSanPham(@Param("id") UUID id);
}
