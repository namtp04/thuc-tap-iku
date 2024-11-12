package com.example.assignment2.controller;

import com.example.assignment2.entity.ChiTietSanPham;
import com.example.assignment2.repository.ChiTietSanPhamRepositpry;
import com.example.assignment2.repository.DongSanPhamRepository;
import com.example.assignment2.repository.MauSacRepository;
import com.example.assignment2.repository.NhaSanXuatRepository;
import com.example.assignment2.repository.SanPhamRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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
import java.util.stream.Collectors;

@Controller
@RequestMapping("/product-detail")
@RequiredArgsConstructor
public class ChiTietSanPhamController {

    private final ChiTietSanPhamRepositpry chiTietSanPhamRepositpry;
    private final SanPhamRepository sanPhamRepository;
    private final MauSacRepository mauSacRepository;
    private final DongSanPhamRepository dongSanPhamRepository;
    private final NhaSanXuatRepository nhaSanXuatRepository;

    @GetMapping("list")
    public String hienThi(Model model, @RequestParam(value = "page", defaultValue = "0") Integer page) {
        model.addAttribute("lstSPCT", phanTrang(page, model));
        model.addAttribute("listProduct", sanPhamRepository.findAll());
        model.addAttribute("listProductType", dongSanPhamRepository.findAll());
        model.addAttribute("listColor", mauSacRepository.findAll());
        return "/productDetail/list";
    }

    @RequestMapping("filter")
    public String filter(Model model,
                         @RequestParam(value = "page", defaultValue = "0") Integer page,
                         @RequestParam(value = "searchProduct", required = false) UUID idSP,
                         @RequestParam(value = "searchProductType", required = false) UUID idDSP,
                         @RequestParam(value = "searchColor", required = false) UUID idCL,
                         HttpSession session
    ) {
        if (idSP == null) {
            session.removeAttribute("selectedProduct");
        }
        if (idDSP == null) {
            session.removeAttribute("selectedProductType");
        }
        if (idCL == null) {
            session.removeAttribute("selectedColor");
        }
        model.addAttribute("lstSPCT", phanTrangFilter(page, model, idSP, idCL, idDSP, session).getContent());
        model.addAttribute("listProduct", sanPhamRepository.findAll());
        model.addAttribute("listProductType", dongSanPhamRepository.findAll());
        model.addAttribute("listColor", mauSacRepository.findAll());
        return "/productDetail/list";
    }

    @GetMapping("delete/{ma}")
    public String xoa(@PathVariable("ma")UUID ma){
        chiTietSanPhamRepositpry.deleteById(ma);
        return "redirect:/product-detail/list";
    }

    @GetMapping("view-update/{ma}")
    public String viewUpdate(Model model, @PathVariable("ma") UUID ma) {
        model.addAttribute("listProduct", sanPhamRepository.findAll());
        model.addAttribute("listProductType", dongSanPhamRepository.findAll());
        model.addAttribute("listColor", mauSacRepository.findAll());
        model.addAttribute("listProducer", nhaSanXuatRepository.findAll());
        model.addAttribute("product", chiTietSanPhamRepositpry.findById(ma).get());
        return "/productDetail/update";
    }

    @GetMapping("view-add")
    public String viewAdd(Model model){
        model.addAttribute("listProduct", sanPhamRepository.findAll());
        model.addAttribute("listProductType", dongSanPhamRepository.findAll());
        model.addAttribute("listColor", mauSacRepository.findAll());
        model.addAttribute("listProducer", nhaSanXuatRepository.findAll());
        return "/productDetail/add";
    }

    @PostMapping("add")
    public String add(@ModelAttribute("product")@Valid ChiTietSanPham chiTietSanPham,BindingResult bindingResult, Model model) {
        model.addAttribute("listProduct", sanPhamRepository.findAll());
        model.addAttribute("listProductType", dongSanPhamRepository.findAll());
        model.addAttribute("listColor", mauSacRepository.findAll());
        model.addAttribute("listProducer", nhaSanXuatRepository.findAll());
        if(bindingResult.hasErrors()){
            model.addAttribute("errors",getErrorMessages(bindingResult));
            return "/productDetail/add";
        }
        chiTietSanPhamRepositpry.save(chiTietSanPham);
        return "redirect:/product-detail/list";
    }

    @PostMapping("update")
    public String update(@ModelAttribute("product")@Valid ChiTietSanPham chiTietSanPham,BindingResult bindingResult, Model model) {
        model.addAttribute("listProduct", sanPhamRepository.findAll());
        model.addAttribute("listProductType", dongSanPhamRepository.findAll());
        model.addAttribute("listColor", mauSacRepository.findAll());
        model.addAttribute("listProducer", nhaSanXuatRepository.findAll());
        if(bindingResult.hasErrors()){
            model.addAttribute("errors",getErrorMessages(bindingResult));
            model.addAttribute("product", chiTietSanPhamRepositpry.findById(chiTietSanPham.getId()).get());
            return "/productDetail/update";
        }
        chiTietSanPhamRepositpry.save(chiTietSanPham);
        return "redirect:/product-detail/list";
    }

    private List<ChiTietSanPham> phanTrang(Integer currentPage, Model model) {
        Pageable pageable = PageRequest.of(currentPage, 5);
        Page<ChiTietSanPham> chiTietSanPhamPage = chiTietSanPhamRepositpry.findAll(pageable);
        model.addAttribute("numpage", chiTietSanPhamPage.getTotalPages());
        model.addAttribute("currentPage", currentPage);
        return chiTietSanPhamPage.getContent();
    }

    private Page<ChiTietSanPham> phanTrangFilter(Integer currentPage, Model model, UUID product, UUID color, UUID productType, HttpSession session) {
        List<ChiTietSanPham> lstFilter = chiTietSanPhamRepositpry.findAll();
        if (product != null) {
            lstFilter = lstFilter.stream()
                    .filter(pd -> pd.getSanPham() != null && product.equals(pd.getSanPham().getId()))
                    .collect(Collectors.toList());
            session.setAttribute("selectedProduct", product);
        }

        if (color != null) {
            lstFilter = lstFilter.stream()
                    .filter(cl -> cl.getMauSac() != null && color.equals(cl.getMauSac().getId()))
                    .collect(Collectors.toList());
            session.setAttribute("selectedColor", color);
        }

        if (productType != null) {
            lstFilter = lstFilter.stream()
                    .filter(pd -> pd.getDongSanPham() != null && productType.equals(pd.getDongSanPham().getId()))
                    .collect(Collectors.toList());
            session.setAttribute("selectedProductType", productType);
        }
        Pageable pageable = PageRequest.of(currentPage, 5);
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), lstFilter.size());
        List<ChiTietSanPham> subList = lstFilter.subList(start, end);
        Page<ChiTietSanPham> hehe = new PageImpl<>(subList, pageable, lstFilter.size());

        model.addAttribute("numpage", hehe.getTotalPages());
        model.addAttribute("currentPage", currentPage);
        return hehe;
    }

    public Map<String, String> getErrorMessages(BindingResult bindingResult) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return errors;
    }
}
