package com.example.assignment2.controller;

import com.example.assignment2.entity.DongSanPham;
import com.example.assignment2.repository.ChiTietSanPhamRepositpry;
import com.example.assignment2.repository.DongSanPhamRepository;
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
@RequestMapping("/product-type")
public class DongSanPhamController {
    @Autowired
    private DongSanPhamRepository dongSanPhamRepository;

    @Autowired
    private ChiTietSanPhamRepositpry chiTietSanPhamRepositpry;

    @GetMapping("list")
    public String hienThi(Model model, @RequestParam(value = "page",defaultValue = "0") Integer page){
        model.addAttribute("lstLSP", phanTrang(page,model));
        return "/dongsanpham/list";
    }

    @RequestMapping("search")
    public String search(Model model, @RequestParam(value = "page",defaultValue = "0") Integer page,@RequestParam(value = "productTypeSearchValue",required = false,defaultValue = "") String ten) {
        if(ten.trim().isEmpty()){
            model.addAttribute("lstLSP", phanTrang(page,model));
        }else{
            model.addAttribute("lstLSP", phanTrangSearch(page,model,ten));
        }
        return "/dongsanpham/list";
    }

    @GetMapping("view-add")
    public String viewAdd() {
        return "/dongsanpham/add";
    }

    @PostMapping("add")
    public String add(@ModelAttribute("productType") @Valid DongSanPham dongSanPham, BindingResult bindingResult, Model model) {
        DongSanPham sp = dongSanPhamRepository.findDongSanPhamByMa(dongSanPham.getMa());
        if(bindingResult.hasErrors()){
            model.addAttribute("errors",getErrorMessages(bindingResult));
            return "/dongsanpham/add";
        }
        if (sp != null) {
            model.addAttribute("error", "Mã đã tồn tại");
            return "/dongsanpham/add";
        }
        dongSanPhamRepository.save(dongSanPham);
        return "redirect:/product-type/list";
    }

    @GetMapping("delete/{ma}")
    public String xoa(@PathVariable("ma") UUID ma,Model model, @RequestParam(value = "page",defaultValue = "0") Integer page) {
        if(chiTietSanPhamRepositpry.getByIdDongSanPham(ma).size()!=0){
            model.addAttribute("lstLSP", phanTrang(page,model));
            model.addAttribute("deleteFail","Dòng sản phẩm này đang có 1 chi tiết sản phẩm không thể xóa");
            return "/dongsanpham/list";
        }
        dongSanPhamRepository.deleteById(ma);
        return "redirect:/product-type/list";
    }

    @GetMapping("view-update/{ma}")
    public String viewUpdate(Model model, @PathVariable("ma") UUID ma) {
        model.addAttribute("productType", dongSanPhamRepository.findDongSanPhamById(ma));
        return "/dongsanpham/update";
    }

    @PostMapping("update")
    public String update(@ModelAttribute("productType") @Valid DongSanPham dongSanPham, BindingResult bindingResult, Model model) {
        DongSanPham sp = dongSanPhamRepository.findDongSanPhamByMa(dongSanPham.getMa());
        if(bindingResult.hasErrors()){
            model.addAttribute("errors",getErrorMessages(bindingResult));
            model.addAttribute("productType", dongSanPhamRepository.findDongSanPhamById(dongSanPham.getId()));
            return "/dongsanpham/update";
        }
        if (sp != null && !sp.getId().equals(dongSanPham.getId())) {
            model.addAttribute("error", "Mã đã tồn tại");
            return "/dongsanpham/update";
        }
        dongSanPhamRepository.save(dongSanPham);
        return "redirect:/product-type/list";
    }

    private List<DongSanPham> phanTrang(Integer currentPage, Model model) {
        Pageable pageable = PageRequest.of(currentPage, 5);
        Page<DongSanPham> dongSanPhamPage = dongSanPhamRepository.findAll(pageable);
        model.addAttribute("numpage", dongSanPhamPage.getTotalPages());
        model.addAttribute("currentPage", currentPage);
        return dongSanPhamPage.getContent();
    }

    private List<DongSanPham> phanTrangSearch(Integer currentPage, Model model,String ten) {
        Pageable pageable = PageRequest.of(currentPage, 5);
        String timTen = "%"+ten.trim()+"%";
        Page<DongSanPham> dongSanPhamPage = dongSanPhamRepository.findByTen(timTen,pageable);
        model.addAttribute("numpage", dongSanPhamPage.getTotalPages());
        model.addAttribute("currentPage", currentPage);
        return dongSanPhamPage.getContent();
    }

    public Map<String, String> getErrorMessages(BindingResult bindingResult) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return errors;
    }
}
