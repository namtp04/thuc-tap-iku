package com.example.assignment2.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name="CuaHang")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CuaHang {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name="Id")
    private UUID id;

    @NotBlank(message = "Mã không được trống")
    @Column(name="Ma")
    private String ma;

    @NotBlank(message = "Tên không được để trống")
    @Column(name="Ten")
    private String ten;

    @NotBlank(message = "Địa chỉ không được để trống")
    @Column(name="DiaChi")
    private String diaChi;

    @NotBlank(message = "Thành phố không được để trống")
    @Column(name="ThanhPho")
    private String thanhPho;

    @NotBlank(message = "Quốc gia không được để trống")
    @Column(name="QuocGia")
    private String quocGia;
}
