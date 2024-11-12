package com.example.assignment2.controller;

import com.example.assignment2.entity.ChiTietSanPham;
import com.example.assignment2.entity.HoaDon;
import com.example.assignment2.entity.KhachHang;
import com.example.assignment2.entity.NhanVien;
import com.example.assignment2.repository.ChiTietSanPhamRepositpry;
import com.example.assignment2.repository.DongSanPhamRepository;
import com.example.assignment2.repository.HoaDonRepository;
import com.example.assignment2.repository.KhachHangRepository;
import com.example.assignment2.repository.MauSacRepository;
import com.example.assignment2.repository.NhanVienRepository;
import com.example.assignment2.repository.SanPhamRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/ban-hang")
@RequiredArgsConstructor
public class BanHangController {

    private final HoaDonRepository hoaDonRepository;
    private final NhanVienRepository nhanVienRepository;
    private final KhachHangRepository khachHangRepository;
    private final ChiTietSanPhamRepositpry chiTietSanPhamRepositpry;
    private final SanPhamRepository sanPhamRepository;
    private final DongSanPhamRepository dongSanPhamRepository;
    private final MauSacRepository mauSacRepository;

    @GetMapping("hien-thi")
    public String hienThi(Model model) {
        model.addAttribute("lstKH", khachHangRepository.findAll());
        model.addAttribute("lstHD", hoaDonRepository.getHoaDonChuaThanhToan());
        model.addAttribute("lstSPCT",chiTietSanPhamRepositpry.findAll());
        model.addAttribute("listProduct", sanPhamRepository.findAll());
        model.addAttribute("listProductType", dongSanPhamRepository.findAll());
        model.addAttribute("listColor", mauSacRepository.findAll());
        return "/banhang/banhang";
    }

    @GetMapping("tao-hoa-don-cho")
    public String taoHoaDonCho(HttpSession session) {
        HoaDon hd = new HoaDon();
        NhanVien nv = nhanVienRepository.findNhanVienByMa((String) session.getAttribute("username"));
        hd.setMa(taoMaHD());
        hd.setTinhTrang(0);
        hd.setNhanVien(nv);
        hd.setKhachHang((KhachHang) session.getAttribute("customerInfo"));
        hd.setNgayTao(new Date());
        hoaDonRepository.save(hd);
        return "redirect:/ban-hang/hien-thi";
    }

    @GetMapping("/chon-khach-hang/{id}")
    public String choseCustomer(@PathVariable("id") UUID id, HttpSession session) {
        KhachHang khachHang = khachHangRepository.findById(id).get();
        session.setAttribute("customerInfo", khachHang);
        return "redirect:/ban-hang/hien-thi";
    }

    @PostMapping("/them-khach-hang")
    public String addCustomer(@ModelAttribute("kh") @Valid KhachHang khachHang, BindingResult bindingResult, Model model, HttpSession session) {
        KhachHang sp = khachHangRepository.findKhachHangByMa(khachHang.getMa());
        if(bindingResult.hasErrors()){
            model.addAttribute("errors",getErrorMessages(bindingResult));
            model.addAttribute("openModal", true);
            return "/banhang/banhang";
        }
        if (sp != null) {
            model.addAttribute("error", "Mã đã tồn tại");
            model.addAttribute("openModal", true);
            return "/banhang/banhang";
        }
        khachHangRepository.save(khachHang);
        session.setAttribute("customerInfo", khachHang);
        return "redirect:/ban-hang/hien-thi";
    }

    @GetMapping("/chon-hoa-don/{id}")
    public String chooseOder(@PathVariable("id") UUID id, HttpSession session) {
        HoaDon hoaDon = hoaDonRepository.findById(id).get();
        session.setAttribute("idOrderChoose", hoaDon.getId());
        session.setAttribute("hoaDon", hoaDon);
        return "redirect:/ban-hang/hien-thi";
    }

    @GetMapping("huy-hoa-don/{ma}")
    public String huyHoaDon(@PathVariable("ma")UUID ma,HttpSession session){
        HoaDon hoaDon = hoaDonRepository.findById(ma).get();
        hoaDon.setTinhTrang(2);
        hoaDonRepository.save(hoaDon);
        session.removeAttribute("idOrderChoose");
        session.removeAttribute("hoaDon");
        return "redirect:/ban-hang/hien-thi";
    }

    @RequestMapping("san-pham-filter")
    public String filter(Model model,
                         @RequestParam(value = "searchProduct", required = false) UUID idSP,
                         @RequestParam(value = "searchProductType", required = false) UUID idDSP,
                         @RequestParam(value = "searchColor", required = false) UUID idCL,
                         HttpSession session
    ) {
        model.addAttribute("lstKH", khachHangRepository.findAll());
        model.addAttribute("lstHD", hoaDonRepository.getHoaDonChuaThanhToan());
        List<ChiTietSanPham> lstFilter = chiTietSanPhamRepositpry.findAll();
        if (idSP != null) {
            lstFilter = lstFilter.stream()
                    .filter(pd -> pd.getSanPham() != null && idSP.equals(pd.getSanPham().getId()))
                    .collect(Collectors.toList());
            session.setAttribute("selectedProduct", idSP);
        }

        if (idCL != null) {
            lstFilter = lstFilter.stream()
                    .filter(cl -> cl.getMauSac() != null && idCL.equals(cl.getMauSac().getId()))
                    .collect(Collectors.toList());
            session.setAttribute("selectedColor", idCL);
        }

        if (idDSP != null) {
            lstFilter = lstFilter.stream()
                    .filter(pd -> pd.getDongSanPham() != null && idDSP.equals(pd.getDongSanPham().getId()))
                    .collect(Collectors.toList());
            session.setAttribute("selectedProductType", idDSP);
        }
        if (idSP == null) {
            session.removeAttribute("selectedProduct");
        }
        if (idDSP == null) {
            session.removeAttribute("selectedProductType");
        }
        if (idCL == null) {
            session.removeAttribute("selectedColor");
        }
        model.addAttribute("lstSPCT", lstFilter);
        model.addAttribute("listProduct", sanPhamRepository.findAll());
        model.addAttribute("listProductType", dongSanPhamRepository.findAll());
        model.addAttribute("listColor", mauSacRepository.findAll());
        return "/banhang/banhang";
    }

    @GetMapping("so-luong-them/{ma}")
    public String soLuongSPThem(@PathVariable("ma")UUID ma,Model model){
        ChiTietSanPham chiTietSanPham = chiTietSanPhamRepositpry.findById(ma).get();
        model.addAttribute("sp",chiTietSanPham);
        return "/banhang/banhang";
    }

    private String taoMaHD() {
        Random random = new Random();
        String maHD = "";
        for (int i = 0; i < 15; i++) {
            if (random.nextBoolean()) {
                maHD += random.nextInt(10);
            } else {
                int x = random.nextInt(26) + 65;
                maHD += (char) x;
            }
        }
        if (hoaDonRepository.findHoaDonByMa(maHD) != null) {
            return taoMaHD();
        }
        return maHD;
    }

    public Map<String, String> getErrorMessages(BindingResult bindingResult) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return errors;
    }

}
