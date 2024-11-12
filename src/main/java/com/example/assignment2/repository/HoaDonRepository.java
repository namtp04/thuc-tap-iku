package com.example.assignment2.repository;

import com.example.assignment2.entity.HoaDon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface HoaDonRepository extends JpaRepository<HoaDon, UUID> {
    @Query("select c from HoaDon c where c.nhanVien.id=:id")
    List<HoaDon> getByIdNhanVien(@Param("id")UUID id);

    @Query("select c from HoaDon c where c.khachHang.id=:id")
    List<HoaDon> getByIdKhachHang(@Param("id")UUID id);

    @Query("select c from HoaDon c where c.tinhTrang=0")
    List<HoaDon> getHoaDonChuaThanhToan();

    HoaDon findHoaDonByMa(String ma);
}
