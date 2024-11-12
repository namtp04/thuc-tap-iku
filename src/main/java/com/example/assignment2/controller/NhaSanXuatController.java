package com.example.assignment2.controller;

import com.example.assignment2.entity.NhaSanXuat;
import com.example.assignment2.repository.ChiTietSanPhamRepositpry;
import com.example.assignment2.repository.NhaSanXuatRepository;
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
@RequestMapping("/NSX")
public class NhaSanXuatController {
    @Autowired
    private NhaSanXuatRepository nhaSanXuatRepository;

    @Autowired
    private ChiTietSanPhamRepositpry chiTietSanPhamRepositpry;

    @GetMapping("list")
    public String hienThi(Model model, @RequestParam(value = "page", defaultValue = "0") Integer page) {
        model.addAttribute("lstNSX", phanTrang(page, model));
        return "/nhasanxuat/list";
    }

    @GetMapping("view-add")
    public String viewAdd() {
        return "/nhasanxuat/add";
    }

    private List<NhaSanXuat> phanTrang(Integer currentPage, Model model) {
        Pageable pageable = PageRequest.of(currentPage, 5);
        Page<NhaSanXuat> mauSacPage = nhaSanXuatRepository.findAll(pageable);
        model.addAttribute("numpage", mauSacPage.getTotalPages());
        model.addAttribute("currentPage", currentPage);
        return mauSacPage.getContent();
    }

    @PostMapping("add")
    public String add(@ModelAttribute("nsx") @Valid NhaSanXuat nsx, BindingResult bindingResult, Model model) {
        NhaSanXuat sp = nhaSanXuatRepository.findNhaSanXuatByMa(nsx.getMa());
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", getErrorMessages(bindingResult));
            return "/nhasanxuat/add";
        }
        if (sp != null) {
            model.addAttribute("error", "Mã đã tồn tại");
            return "/nhasanxuat/add";
        }
        nhaSanXuatRepository.save(nsx);
        return "redirect:/NSX/list";
    }

    @GetMapping("delete/{ma}")
    public String xoa(@PathVariable("ma") UUID ma,Model model, @RequestParam(value = "page",defaultValue = "0") Integer page) {
        if(chiTietSanPhamRepositpry.getByIdNhaSanXuat(ma).size()!=0){
            model.addAttribute("lstNSX", phanTrang(page,model));
            model.addAttribute("deleteFail","Nhà sản xuất này đang có 1 chi tiết sản phẩm không thể xóa");
            return "/nhasanxuat/list";
        }
        nhaSanXuatRepository.deleteById(ma);
        return "redirect:/NSX/list";
    }

    @GetMapping("view-update/{ma}")
    public String viewUpdate(Model model, @PathVariable("ma") UUID ma) {
        model.addAttribute("nsx", nhaSanXuatRepository.findNhaSanXuatById(ma));
        return "/nhasanxuat/update";
    }

    @PostMapping("update")
    public String update(@ModelAttribute("nsx") @Valid NhaSanXuat nsx, BindingResult bindingResult, Model model) {
        NhaSanXuat sp = nhaSanXuatRepository.findNhaSanXuatByMa(nsx.getMa());
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", getErrorMessages(bindingResult));
            model.addAttribute("nsx", nhaSanXuatRepository.findNhaSanXuatById(nsx.getId()));
            return "/nhasanxuat/update";
        }
        if (sp != null && !sp.getId().equals(nsx.getId())) {
            model.addAttribute("error", "Mã đã tồn tại");
            return "/nhasanxuat/update";
        }
        nhaSanXuatRepository.save(nsx);
        return "redirect:/NSX/list";
    }

    @RequestMapping("search")
    public String search(Model model,
                         @RequestParam(value = "page", defaultValue = "0") Integer page,
                         @RequestParam(value = "nsxSearchValue", required = false, defaultValue = "") String ten) {
        if (ten.trim().isEmpty()) {
            model.addAttribute("lstNSX", phanTrang(page, model));
        } else {
            model.addAttribute("lstNSX", phanTrangSearch(page, model, ten));
        }
        return "/nhasanxuat/list";
    }

    private List<NhaSanXuat> phanTrangSearch(Integer currentPage, Model model,String ten) {
        Pageable pageable = PageRequest.of(currentPage, 5);
        String timTen = "%"+ten.trim()+"%";
        Page<NhaSanXuat> nhaSanXuatPage = nhaSanXuatRepository.findByTen(timTen,pageable);
        model.addAttribute("numpage", nhaSanXuatPage.getTotalPages());
        model.addAttribute("currentPage", currentPage);
        return nhaSanXuatPage.getContent();
    }

    public Map<String, String> getErrorMessages(BindingResult bindingResult) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return errors;
    }
}
