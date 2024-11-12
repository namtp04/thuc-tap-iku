package com.example.assignment2.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name="ChiTietSP")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ChiTietSanPham {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "Id")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "IdSP",referencedColumnName = "Id")
    @NotNull(message = "Sản phẩm không được trống")
    private SanPham sanPham;

    @ManyToOne
    @JoinColumn(name = "IdNsx",referencedColumnName = "Id")
    @NotNull(message = "Nhà sản xuất không được trống")
    private NhaSanXuat nhaSanXuat;

    @ManyToOne
    @JoinColumn(name = "IdMauSac",referencedColumnName = "Id")
    @NotNull(message = "Màu sắc không được trống")
    private MauSac mauSac;

    @ManyToOne
    @JoinColumn(name = "IdDongSP",referencedColumnName = "Id")
    @NotNull(message = "Dòng sản phẩm không được trống")
    private DongSanPham dongSanPham;

    @Column(name = "NamBH")
    @NotNull(message = "Năm bán hàng không được trống")
    private Integer namBH;

    @Column(name = "MoTa")
    private String moTa;

    @Column(name = "SoLuongTon")
    @NotNull(message = "Số lượng tồn không được trống")
    private Integer soLuongTon;

    @Column(name = "GiaNhap")
    @NotNull(message = "Giá nhập không được trống")
    private BigDecimal giaNhap;

    @Column(name = "GiaBan")
    @NotNull(message = "Giá bán không được trống")
    private BigDecimal giaBan;
}
