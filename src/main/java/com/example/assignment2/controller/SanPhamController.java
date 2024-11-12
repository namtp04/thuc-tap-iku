package com.example.assignment2.controller;

import com.example.assignment2.entity.SanPham;
import com.example.assignment2.repository.ChiTietSanPhamRepositpry;
import com.example.assignment2.repository.SanPhamRepository;
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
@RequestMapping("/product")
public class SanPhamController {
    @Autowired
    private SanPhamRepository sanPhamRepository;

    @Autowired
    private ChiTietSanPhamRepositpry chiTietSanPhamRepositpry;

    @GetMapping("list")
    public String hienThi(Model model, @RequestParam(value = "page",defaultValue = "0") Integer page) {
        model.addAttribute("lstSP", phanTrang(page,model));
        return "/product/list";
    }

    @RequestMapping("search")
    public String search(Model model, @RequestParam(value = "page",defaultValue = "0") Integer page,@RequestParam(value = "productSearchValue",required = false,defaultValue = "") String ten) {
        if(ten.trim().isEmpty()){
            model.addAttribute("lstSP", phanTrang(page,model));
        }else{
            model.addAttribute("lstSP", phanTrangSearch(page,model,ten));
        }
        return "/product/list";
    }

    @GetMapping("view-add")
    public String viewAdd() {
        return "/product/form-add";
    }

    @GetMapping("view-update/{ma}")
    public String viewUpdate(Model model, @PathVariable("ma") UUID ma) {
        model.addAttribute("product", sanPhamRepository.findSanPhamById(ma));
        return "/product/form-update";
    }

    @PostMapping("add")
    public String add(@ModelAttribute("product") @Valid SanPham sanPham, BindingResult bindingResult, Model model) {
        SanPham sp = sanPhamRepository.findSanPhamByMa(sanPham.getMa());
        if(bindingResult.hasErrors()){
            model.addAttribute("errors",getErrorMessages(bindingResult));
            return "/product/form-add";
        }
        if (sp != null) {
            model.addAttribute("error", "Mã đã tồn tại");
            return "/product/form-add";
        }
        sanPhamRepository.save(sanPham);
        return "redirect:/product/list";
    }

    @PostMapping("update")
    public String update(@ModelAttribute("product") @Valid SanPham sanPham, BindingResult bindingResult, Model model) {
        SanPham sp = sanPhamRepository.findSanPhamByMa(sanPham.getMa());
        if(bindingResult.hasErrors()){
            model.addAttribute("errors",getErrorMessages(bindingResult));
            model.addAttribute("product", sanPhamRepository.findSanPhamById(sanPham.getId()));
            return "/product/form-update";
        }
        if (sp != null && !sp.getId().equals(sanPham.getId())) {
            model.addAttribute("error", "Mã đã tồn tại");
            return "/product/form-update";
        }
        sanPhamRepository.save(sanPham);
        return "redirect:/product/list";
    }

    @GetMapping("detail/{ma}")
    public String detail(Model model, @PathVariable("ma") UUID ma) {
        model.addAttribute("sp1", sanPhamRepository.findSanPhamById(ma));
        return "/product/detail";
    }

    @GetMapping("delete/{ma}")
    public String xoa(@PathVariable("ma") UUID ma,Model model, @RequestParam(value = "page",defaultValue = "0") Integer page) {
        if(chiTietSanPhamRepositpry.getByIdSanPham(ma).size()!=0){
            model.addAttribute("lstSP", phanTrang(page,model));
            System.out.println(chiTietSanPhamRepositpry.getByIdSanPham(ma));
            model.addAttribute("deleteFail","Sản phẩm này đang có 1 chi tiết sản phẩm không thể xóa");
            return "/product/list";
        }else{
            sanPhamRepository.deleteById(ma);
            return "redirect:/product/list";
        }
    }

    private List<SanPham> phanTrang(Integer currentPage, Model model) {
        Pageable pageable = PageRequest.of(currentPage, 5);
        Page<SanPham> sanPhamPage = sanPhamRepository.findAll(pageable);
        model.addAttribute("numpage", sanPhamPage.getTotalPages());
        model.addAttribute("currentPage", currentPage);
        return sanPhamPage.getContent();
    }

    private List<SanPham> phanTrangSearch(Integer currentPage, Model model,String ten) {
        Pageable pageable = PageRequest.of(currentPage, 5);
        String timTen = "%"+ten.trim()+"%";
        Page<SanPham> sanPhamPage = sanPhamRepository.findByTen(timTen,pageable);
        model.addAttribute("numpage", sanPhamPage.getTotalPages());
        model.addAttribute("currentPage", currentPage);
        return sanPhamPage.getContent();
    }

    public Map<String, String> getErrorMessages(BindingResult bindingResult) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return errors;
    }
}
