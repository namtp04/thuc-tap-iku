package com.example.assignment2.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name="NhanVien")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NhanVien {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name="Id")
    private UUID id;

    @Column(name="Ma")
    @NotBlank(message = "Mã không được trống")
    private String ma;

    @Column(name="Ten")
    @NotBlank(message = "Tên không được trống")
    private String ten;

    @Column(name="TenDem")
    @NotBlank(message = "Tên đệm không được trống")
    private String tenDem;

    @Column(name="Ho")
    @NotBlank(message = "Họ không được trống")
    private String ho;

    @Column(name="GioiTinh")
    private String gioiTinh;

    @Column(name="NgaySinh")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Ngày sinh không được trống")
    private Date ngaySinh;

    @Column(name="DiaChi")
    @NotBlank(message = "Địa chỉ không được trống")
    private String diaChi;

    @Column(name="Sdt")
    @NotBlank(message = "SĐT không được trống")
    private String sdt;

    @Column(name="MatKhau")
    @NotBlank(message = "Mật khẩu không được trống")
    private String matKhau;

    @ManyToOne
    @JoinColumn(name = "IdCH",referencedColumnName = "id")
    @NotNull(message = "Cửa hàng không được trống")
    private CuaHang cuaHang;

    @ManyToOne
    @JoinColumn(name = "IdCV",referencedColumnName = "id")
    @NotNull(message = "Chức vụ không được trống")
    private ChucVu chucVu;

    @Column(name = "TrangThai")
    private Integer trangThai;
}
