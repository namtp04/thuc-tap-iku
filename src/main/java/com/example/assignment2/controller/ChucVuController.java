package com.example.assignment2.controller;

import com.example.assignment2.entity.ChucVu;
import com.example.assignment2.repository.ChucVuRepository;
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
@RequestMapping("/chuc-vu")
public class ChucVuController {
    @Autowired
    private ChucVuRepository chucVuRepository;

    @Autowired
    private NhanVienRepository nhanVienRepository;

    @GetMapping("list")
    public String hienThi(Model model, @RequestParam(value = "page",defaultValue = "0")Integer page){
        model.addAttribute("lstCV",phanTrang(page,model));
        return "/chucvu/list";
    }

    @GetMapping("view-add")
    public String viewAdd() {
        return "/chucvu/add";
    }

    @PostMapping("add")
    public String add(@ModelAttribute("chucVu") @Valid ChucVu chucVu, BindingResult bindingResult, Model model) {
        ChucVu sp = chucVuRepository.findChucVuByMa(chucVu.getMa());
        if(bindingResult.hasErrors()){
            model.addAttribute("errors",getErrorMessages(bindingResult));
            return "/chucvu/add";
        }
        if (sp != null) {
            model.addAttribute("error", "Mã đã tồn tại");
            return "/chucvu/add";
        }
        chucVuRepository.save(chucVu);
        return "redirect:/chuc-vu/list";
    }

    @GetMapping("view-update/{ma}")
    public String viewUpdate(Model model, @PathVariable("ma") UUID ma) {
        model.addAttribute("chucVu", chucVuRepository.findById(ma).get());
        return "/chucvu/update";
    }

    @PostMapping("update")
    public String update(@ModelAttribute("chucVu") @Valid ChucVu chucVu, BindingResult bindingResult, Model model) {
        ChucVu sp = chucVuRepository.findChucVuByMa(chucVu.getMa());
        if(bindingResult.hasErrors()){
            model.addAttribute("errors",getErrorMessages(bindingResult));
            model.addAttribute("chucVu", chucVuRepository.findById(chucVu.getId()).get());
            return "/chucvu/update";
        }
        if (sp != null && !sp.getId().equals(chucVu.getId())) {
            model.addAttribute("error", "Mã đã tồn tại");
            return "/chucvu/update";
        }
        chucVuRepository.save(chucVu);
        return "redirect:/chuc-vu/list";
    }

    @GetMapping("delete/{ma}")
    public String xoa(@PathVariable("ma") UUID ma,Model model, @RequestParam(value = "page",defaultValue = "0") Integer page) {
        if(nhanVienRepository.getByIdChucVu(ma).size()!=0){
            model.addAttribute("lstCV", phanTrang(page,model));
            model.addAttribute("deleteFail","Chức vụ này đang thuộc về 1 nhân viên không thể xóa");
            return "/chucvu/list";
        }
        chucVuRepository.deleteById(ma);
        return "redirect:/chuc-vu/list";
    }

    @RequestMapping("search")
    public String search(Model model, @RequestParam(value = "page",defaultValue = "0") Integer page,@RequestParam(value = "chucVuSearchValue",required = false,defaultValue = "") String ten) {
        if(ten.trim().isEmpty()){
            model.addAttribute("lstCV", phanTrang(page,model));
        }else{
            model.addAttribute("lstCV", phanTrangSearch(page,model,ten));
        }
        return "/chucvu/list";
    }

    private List<ChucVu> phanTrang(Integer currentPage, Model model) {
        Pageable pageable = PageRequest.of(currentPage, 5);
        Page<ChucVu> chucVuPage = chucVuRepository.findAll(pageable);
        model.addAttribute("numpage", chucVuPage.getTotalPages());
        model.addAttribute("currentPage", currentPage);
        return chucVuPage.getContent();
    }

    private List<ChucVu> phanTrangSearch(Integer currentPage, Model model,String ten) {
        Pageable pageable = PageRequest.of(currentPage, 5);
        String timTen = "%"+ten.trim()+"%";
        Page<ChucVu> chucVuPage = chucVuRepository.findByTen(timTen,pageable);
        model.addAttribute("numpage", chucVuPage.getTotalPages());
        model.addAttribute("currentPage", currentPage);
        return chucVuPage.getContent();
    }

    public Map<String, String> getErrorMessages(BindingResult bindingResult) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return errors;
    }
}
