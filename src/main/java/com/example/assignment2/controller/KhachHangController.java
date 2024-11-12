package com.example.assignment2.controller;

import com.example.assignment2.entity.DongSanPham;
import com.example.assignment2.entity.KhachHang;
import com.example.assignment2.entity.NhanVien;
import com.example.assignment2.repository.GioHangRepository;
import com.example.assignment2.repository.HoaDonRepository;
import com.example.assignment2.repository.KhachHangRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/customer")
@RequiredArgsConstructor
public class KhachHangController {

    private final KhachHangRepository khachHangRepository;
    private final HoaDonRepository hoaDonRepository;
    private final GioHangRepository gioHangRepository;

    @GetMapping("/list")
    public String hienThi(Model model, @RequestParam(value = "page",defaultValue = "0") Integer page){
        model.addAttribute("lstKH",phanTrang(page,model));
        return"/khachhang/list";
    }

    @GetMapping("view-add")
    public String viewAdd() {
        return "/khachhang/add";
    }

    @PostMapping("add")
    public String add(@ModelAttribute("kh") @Valid KhachHang khachHang, BindingResult bindingResult, Model model) {
        KhachHang sp = khachHangRepository.findKhachHangByMa(khachHang.getMa());
        if(bindingResult.hasErrors()){
            model.addAttribute("errors",getErrorMessages(bindingResult));
            return "/khachhang/add";
        }
        if (sp != null) {
            model.addAttribute("error", "Mã đã tồn tại");
            return "/khachhang/add";
        }
        khachHangRepository.save(khachHang);
        return "redirect:/customer/list";
    }

    @GetMapping("view-update/{ma}")
    public String viewUpdate(Model model, @PathVariable("ma") UUID ma) {
        model.addAttribute("kh", khachHangRepository.findById(ma).get());
        return "/khachhang/update";
    }

    @PostMapping("update")
    public String update(@ModelAttribute("kh") @Valid KhachHang khachHang, BindingResult bindingResult, Model model) {
        KhachHang sp = khachHangRepository.findKhachHangByMa(khachHang.getMa());
        if(bindingResult.hasErrors()){
            model.addAttribute("errors",getErrorMessages(bindingResult));
            model.addAttribute("nv", khachHangRepository.findById(khachHang.getId()).get());
            return "/khachhang/update";
        }
        if (sp != null && !sp.getId().equals(khachHang.getId())) {
            model.addAttribute("error", "Mã đã tồn tại");
            return "/khachhang/update";
        }
        khachHangRepository.save(khachHang);
        return "redirect:/customer/list";
    }

    @GetMapping("delete/{ma}")
    public String xoa(@PathVariable("ma") UUID ma,Model model, @RequestParam(value = "page",defaultValue = "0") Integer page) {
        if(hoaDonRepository.getByIdKhachHang(ma).size()!=0){
            model.addAttribute("lstKH", phanTrang(page,model));
            model.addAttribute("deleteFail","Khách hàng này đang có 1 hóa đơn không thể xóa");
            return "/khachhang/list";
        }
        if(gioHangRepository.getByIdKhachHang(ma).size()!=0){
            model.addAttribute("lstKH", phanTrang(page,model));
            model.addAttribute("deleteFail","Khách hàng này đang có 1 giỏ hàng không thể xóa");
            return "/khachhang/list";
        }
        khachHangRepository.deleteById(ma);
        return "redirect:/customer/list";
    }

    @RequestMapping("search")
    public String search(Model model, @RequestParam(value = "page",defaultValue = "0") Integer page,@RequestParam(value = "customerSearchValue",required = false,defaultValue = "") String ten) {
        if(ten.trim().isEmpty()){
            model.addAttribute("lstKH", phanTrang(page,model));
        }else{
            model.addAttribute("lstKH", phanTrangSearch(page,model,ten));
        }
        return "/khachhang/list";
    }

    private List<KhachHang> phanTrang(Integer currentPage, Model model) {
        Pageable pageable = PageRequest.of(currentPage, 5);
        Page<KhachHang> khachHangPage = khachHangRepository.findAll(pageable);
        model.addAttribute("numpage", khachHangPage.getTotalPages());
        model.addAttribute("currentPage", currentPage);
        return khachHangPage.getContent();
    }

    private List<KhachHang> phanTrangSearch(Integer currentPage, Model model,String ten) {
        Pageable pageable = PageRequest.of(currentPage, 5);
        Page<KhachHang> khachHangPage = khachHangRepository.findBySDT(ten.trim(),pageable);
        model.addAttribute("numpage", khachHangPage.getTotalPages());
        model.addAttribute("currentPage", currentPage);
        return khachHangPage.getContent();
    }

    public Map<String, String> getErrorMessages(BindingResult bindingResult) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return errors;
    }
}
