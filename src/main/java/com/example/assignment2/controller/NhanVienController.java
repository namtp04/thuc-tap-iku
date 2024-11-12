package com.example.assignment2.controller;

import com.example.assignment2.entity.NhanVien;
import com.example.assignment2.repository.ChucVuRepository;
import com.example.assignment2.repository.CuaHangRepository;
import com.example.assignment2.repository.HoaDonRepository;
import com.example.assignment2.repository.NhanVienRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
@RequestMapping("/nhan-vien")
@RequiredArgsConstructor
public class NhanVienController {


    private final NhanVienRepository nhanVienRepository;
    private final ChucVuRepository chucVuRepository;
    private final CuaHangRepository cuaHangRepository;
    private final HoaDonRepository hoaDonRepository;

    @GetMapping("list")
    public String hienThi(Model model, @RequestParam(value = "page", defaultValue = "0") Integer page) {
        model.addAttribute("lstNV", phanTrang(page, model));
        return "/nhanvien/list";
    }

    @GetMapping("view-add")
    public String viewAdd(Model model) {
        model.addAttribute("lstCV", chucVuRepository.findAll());
        model.addAttribute("lstCH", cuaHangRepository.findAll());
        return "/nhanvien/add";
    }

    @PostMapping("add")
    public String add(@ModelAttribute("nv") @Valid NhanVien nhanVien, BindingResult bindingResult, Model model) {
        NhanVien sp = nhanVienRepository.findNhanVienByMa(nhanVien.getMa());
        model.addAttribute("lstCV", chucVuRepository.findAll());
        model.addAttribute("lstCH", cuaHangRepository.findAll());
        if(bindingResult.hasErrors()){
            model.addAttribute("errors",getErrorMessages(bindingResult));
            return "/nhanvien/add";
        }
        if (sp != null) {
            model.addAttribute("error", "Mã đã tồn tại");
            return "/nhanvien/add";
        }
        nhanVienRepository.save(nhanVien);
        return "redirect:/nhan-vien/list";
    }

    @GetMapping("view-update/{ma}")
    public String viewUpdate(Model model, @PathVariable("ma") UUID ma) {
        model.addAttribute("lstCV", chucVuRepository.findAll());
        model.addAttribute("lstCH", cuaHangRepository.findAll());
        model.addAttribute("nv", nhanVienRepository.findById(ma).get());
        return "/nhanvien/update";
    }

    @PostMapping("update")
    public String update(@ModelAttribute("nv") @Valid NhanVien nhanVien, BindingResult bindingResult, Model model) {
        NhanVien sp = nhanVienRepository.findNhanVienByMa(nhanVien.getMa());
        model.addAttribute("lstCV", chucVuRepository.findAll());
        model.addAttribute("lstCH", cuaHangRepository.findAll());
        if(bindingResult.hasErrors()){
            model.addAttribute("errors",getErrorMessages(bindingResult));
            model.addAttribute("nv", nhanVienRepository.findById(nhanVien.getId()).get());
            return "/nhanvien/update";
        }
        if (sp != null && !sp.getId().equals(nhanVien.getId())) {
            model.addAttribute("error", "Mã đã tồn tại");
            return "/nhanvien/update";
        }
        nhanVienRepository.save(nhanVien);
        return "redirect:/nhan-vien/list";
    }

    @GetMapping("delete/{ma}")
    public String xoa(@PathVariable("ma") UUID ma,Model model, @RequestParam(value = "page",defaultValue = "0") Integer page) {
        if(hoaDonRepository.getByIdNhanVien(ma).size()!=0){
            model.addAttribute("lstNV", phanTrang(page,model));
            model.addAttribute("deleteFail","Nhân viên này đang quản lý 1 hóa đơn không thể xóa");
            return "/nhanvien/list";
        }
        nhanVienRepository.deleteById(ma);
        return "redirect:/nhan-vien/list";
    }

    @RequestMapping("search")
    public String search(Model model, @RequestParam(value = "page",defaultValue = "0") Integer page,@RequestParam(value = "nhanVienSearchValue",required = false,defaultValue = "") String ten) {
        if(ten.trim().isEmpty()){
            model.addAttribute("lstNV", phanTrang(page,model));
        }else{
            model.addAttribute("lstNV", phanTrangSearch(page,model,ten));
        }
        return "/nhanvien/list";
    }

    private List<NhanVien> phanTrang(Integer currentPage, Model model) {
        Pageable pageable = PageRequest.of(currentPage, 5);
        Page<NhanVien> nhanVienPage = nhanVienRepository.findAll(pageable);
        model.addAttribute("numpage", nhanVienPage.getTotalPages());
        model.addAttribute("currentPage", currentPage);
        return nhanVienPage.getContent();
    }

    private List<NhanVien> phanTrangSearch(Integer currentPage, Model model,String ten) {
        Pageable pageable = PageRequest.of(currentPage, 5);
        String timTen = "%"+ten.trim()+"%";
        Page<NhanVien> nhanVienPage = nhanVienRepository.findByTen(timTen,pageable);
        model.addAttribute("numpage", nhanVienPage.getTotalPages());
        model.addAttribute("currentPage", currentPage);
        return nhanVienPage.getContent();
    }

    public Map<String, String> getErrorMessages(BindingResult bindingResult) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return errors;
    }
}
