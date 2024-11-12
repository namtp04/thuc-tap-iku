package com.example.assignment2.controller;

import com.example.assignment2.entity.CuaHang;
import com.example.assignment2.repository.CuaHangRepository;
import com.example.assignment2.repository.NhanVienRepository;
import jakarta.validation.Valid;
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
@RequestMapping("/cua-hang")
public class CuaHangController {
    @Autowired
    private CuaHangRepository cuaHangRepository;

    @Autowired
    private NhanVienRepository nhanVienRepository;

    @GetMapping("list")
    public String hienThi(Model model, @RequestParam(value = "page",defaultValue = "0")Integer page){
        model.addAttribute("lstCH",phanTrang(page,model));
        return "/cuahang/list";
    }

    @GetMapping("view-add")
    public String viewAdd() {
        return "/cuahang/add";
    }

    @PostMapping("add")
    public String add(@ModelAttribute("ch") @Valid CuaHang cuaHang, BindingResult bindingResult, Model model) {
        CuaHang sp = cuaHangRepository.findCuaHangByMa(cuaHang.getMa());
        if(bindingResult.hasErrors()){
            model.addAttribute("errors",getErrorMessages(bindingResult));
            return "/cuahang/add";
        }
        if (sp != null) {
            model.addAttribute("error", "Mã đã tồn tại");
            return "/cuahang/add";
        }
        cuaHangRepository.save(cuaHang);
        return "redirect:/cua-hang/list";
    }

    @GetMapping("view-update/{ma}")
    public String viewUpdate(Model model, @PathVariable("ma") UUID ma) {
        model.addAttribute("ch", cuaHangRepository.findById(ma).get());
        return "/cuahang/update";
    }

    @PostMapping("update")
    public String update(@ModelAttribute("ch") @Valid CuaHang cuaHang, BindingResult bindingResult, Model model) {
        CuaHang sp = cuaHangRepository.findCuaHangByMa(cuaHang.getMa());
        if(bindingResult.hasErrors()){
            model.addAttribute("errors",getErrorMessages(bindingResult));
            model.addAttribute("ch", cuaHangRepository.findById(cuaHang.getId()).get());
            return "/cuahang/update";
        }
        if (sp != null && !sp.getId().equals(cuaHang.getId())) {
            model.addAttribute("error", "Mã đã tồn tại");
            return "/cuahang/update";
        }
        cuaHangRepository.save(cuaHang);
        return "redirect:/cua-hang/list";
    }

    @GetMapping("delete/{ma}")
    public String xoa(@PathVariable("ma") UUID ma,Model model, @RequestParam(value = "page",defaultValue = "0") Integer page) {
        if(nhanVienRepository.getByIdCuaHang(ma).size()!=0){
            model.addAttribute("lstCH", phanTrang(page,model));
            model.addAttribute("deleteFail","Cửa hàng này đang thuộc về 1 nhân viên quản lý không thể xóa");
            return "/cuahang/list";
        }
        cuaHangRepository.deleteById(ma);
        return "redirect:/cua-hang/list";
    }

    @RequestMapping("search")
    public String search(Model model, @RequestParam(value = "page",defaultValue = "0") Integer page,@RequestParam(value = "cuaHangSearchValue",required = false,defaultValue = "") String ten) {
        if(ten.trim().isEmpty()){
            model.addAttribute("lstCH", phanTrang(page,model));
        }else{
            model.addAttribute("lstCH", phanTrangSearch(page,model,ten));
        }
        return "/cuahang/list";
    }

    private List<CuaHang> phanTrang(Integer currentPage, Model model) {
        Pageable pageable = PageRequest.of(currentPage, 5);
        Page<CuaHang> cuaHangPage = cuaHangRepository.findAll(pageable);
        model.addAttribute("numpage", cuaHangPage.getTotalPages());
        model.addAttribute("currentPage", currentPage);
        return cuaHangPage.getContent();
    }

    private List<CuaHang> phanTrangSearch(Integer currentPage, Model model,String ten) {
        Pageable pageable = PageRequest.of(currentPage, 5);
        String timTen = "%"+ten.trim()+"%";
        Page<CuaHang> cuaHangPage = cuaHangRepository.findByTen(timTen,pageable);
        model.addAttribute("numpage", cuaHangPage.getTotalPages());
        model.addAttribute("currentPage", currentPage);
        return cuaHangPage.getContent();
    }

    public Map<String, String> getErrorMessages(BindingResult bindingResult) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return errors;
    }
}
