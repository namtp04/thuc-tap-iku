package com.example.assignment2.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name="KhachHang")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class KhachHang {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name="Id")
    private UUID id;

    @Column(name="Ma")
    @NotBlank(message = "Mã không được để trống")
    private String ma;

    @Column(name="Ten")
    @NotBlank(message = "Tên không được để trống")
    private String ten;

    @Column(name="TenDem")
    @NotBlank(message = "Tên đệm không được để trống")
    private String tenDem;

    @Column(name="Ho")
    @NotBlank(message = "Họ không được để trống")
    private String ho;

    @Column(name = "NgaySinh")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Ngày sinh không được để trống")
    private Date ngaySinh;

    @Column(name="DiaChi")
    @NotBlank(message = "Địa chỉ không được để trống")
    private String diaChi;

    @Column(name="Sdt")
    @NotBlank(message = "SĐT không được để trống")
    private String sdt;

    @Column(name="ThanhPho")
    @NotBlank(message = "Thành phố không được để trống")
    private String thanhPho;

    @Column(name="QuocGia")
    @NotBlank(message = "Quốc gia không được để trống")
    private String quocGia;

    @Column(name="MatKhau")
    @NotBlank(message = "Mật khẩu không được để trống")
    private String matKhau;

}
