package com.example.assignment2.controller;

import com.example.assignment2.entity.MauSac;
import com.example.assignment2.repository.ChiTietSanPhamRepositpry;
import com.example.assignment2.repository.MauSacRepository;
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
@RequestMapping("/color")
public class MauSacController {
    @Autowired
    private MauSacRepository mauSacRepository;

    @Autowired
    private ChiTietSanPhamRepositpry chiTietSanPhamRepositpry;

    @GetMapping("list")
    public String hienThi(Model model, @RequestParam(value = "page",defaultValue = "0") Integer page) {
        model.addAttribute("lstCL", phanTrang(page,model));
        return "/mausac/list";
    }

    @GetMapping("view-add")
    public String viewAdd() {
        return "/mausac/add";
    }

    @GetMapping("view-update/{ma}")
    public String viewUpdate(Model model, @PathVariable("ma") UUID ma) {
        model.addAttribute("color", mauSacRepository.findMauSacById(ma));
        return "/mausac/update";
    }

    @PostMapping("add")
    public String add(@ModelAttribute("color") @Valid MauSac color, BindingResult bindingResult, Model model) {
        MauSac cl = mauSacRepository.findMauSacByMa(color.getMa());
        if(bindingResult.hasErrors()){
            model.addAttribute("errors",getErrorMessages(bindingResult));
            return "/mausac/add";
        }
        if (cl != null) {
            model.addAttribute("error", "Mã đã tồn tại");
            return "/mausac/add";
        }
        mauSacRepository.save(color);
        return "redirect:/color/list";
    }

    @PostMapping("update")
    public String update(@ModelAttribute("color") @Valid MauSac mauSac, BindingResult bindingResult, Model model) {
        MauSac cl = mauSacRepository.findMauSacByMa(mauSac.getMa());
        if(bindingResult.hasErrors()){
            model.addAttribute("errors",getErrorMessages(bindingResult));
            model.addAttribute("color", mauSacRepository.findMauSacById(mauSac.getId()));
            return "/mausac/update";
        }
        if (cl != null && !cl.getId().equals(mauSac.getId())) {
            model.addAttribute("error", "Mã đã tồn tại");
            return "/mausac/update";
        }
        mauSacRepository.save(mauSac);
        return "redirect:/color/list";
    }

    @GetMapping("delete/{ma}")
    public String xoa(@PathVariable("ma") UUID ma,Model model, @RequestParam(value = "page",defaultValue = "0") Integer page) {
        if(chiTietSanPhamRepositpry.getByIdMauSac(ma).size()!=0){
            model.addAttribute("lstCL", phanTrang(page,model));
            model.addAttribute("deleteFail","Màu sắc này đang có 1 chi tiết sản phẩm không thể xóa");
            return "/mausac/list";
        }
        mauSacRepository.deleteById(ma);
        return "redirect:/color/list";
    }

    @RequestMapping("search")
    public String search(Model model,
                         @RequestParam(value = "page",defaultValue = "0") Integer page,
                         @RequestParam(value = "colorSearchValue",required = false,defaultValue = "") String ten) {
        if(ten.trim().isEmpty()){
            model.addAttribute("lstCL", phanTrang(page,model));
        }else{
            model.addAttribute("lstCL", phanTrangSearch(page,model,ten));
        }
        return "/mausac/list";
    }

    private List<MauSac> phanTrang(Integer currentPage, Model model) {
        Pageable pageable = PageRequest.of(currentPage, 5);
        Page<MauSac> mauSacPage = mauSacRepository.findAll(pageable);
        model.addAttribute("numpage", mauSacPage.getTotalPages());
        model.addAttribute("currentPage", currentPage);
        return mauSacPage.getContent();
    }

    private List<MauSac> phanTrangSearch(Integer currentPage, Model model,String ten) {
        Pageable pageable = PageRequest.of(currentPage, 5);
        String timTen = "%"+ten.trim()+"%";
        Page<MauSac> mauSacPage = mauSacRepository.findByTen(timTen,pageable);
        model.addAttribute("numpage", mauSacPage.getTotalPages());
        model.addAttribute("currentPage", currentPage);
        return mauSacPage.getContent();
    }

    public Map<String, String> getErrorMessages(BindingResult bindingResult) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return errors;
    }

}
