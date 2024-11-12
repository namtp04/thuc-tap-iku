package com.example.assignment2.repository;

import com.example.assignment2.entity.NhanVien;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface NhanVienRepository extends JpaRepository<NhanVien, UUID> {

    Boolean existsByMaAndAndMatKhau(String username, String password);

    @Query("select c from NhanVien c where c.ma=:ma and c.trangThai= 1")
    NhanVien getNhanVienAdmin(@Param("ma")String ma);

    NhanVien findNhanVienByMa(String ma);

    @Query("select c from NhanVien c where c.chucVu.id=:id")
    List<NhanVien> getByIdChucVu(@Param("id") UUID id);

    @Query("select c from NhanVien c where c.cuaHang.id=:id")
    List<NhanVien> getByIdCuaHang(@Param("id") UUID id);

    @Query("select c from NhanVien c where c.ten like :ten or c.ho like :ten or c.tenDem like :ten")
    Page<NhanVien> findByTen(@Param("ten") String ten, Pageable pageable);
}
