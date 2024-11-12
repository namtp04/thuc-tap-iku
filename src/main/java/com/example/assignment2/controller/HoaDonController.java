package com.example.assignment2.controller;

import com.example.assignment2.entity.ChiTietSanPham;
import com.example.assignment2.entity.HoaDon;
import com.example.assignment2.entity.HoaDonChiTiet;
import com.example.assignment2.repository.HoaDonChiTietRepository;
import com.example.assignment2.repository.HoaDonRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/bill")
public class HoaDonController {
    @Autowired
    private HoaDonRepository hoaDonRepository;

    @Autowired
    private HoaDonChiTietRepository hoaDonChiTietRepository;

    @GetMapping("/list")
    public String hienThi(Model model, @RequestParam(value = "page", defaultValue = "0") Integer page,HttpSession session) {
        session.removeAttribute("trangThai");
        model.addAttribute("lstHD", phanTrang(page, model));
        return "/hoadon/list";
    }

    @GetMapping("/detail/{ma}")
    public String detail(@PathVariable("ma") UUID ma, Model model) {
        List<HoaDonChiTiet> lstHDCT = hoaDonChiTietRepository.findByIdHoaDon(ma);
        model.addAttribute("hd", hoaDonRepository.findById(ma).get());
        model.addAttribute("lstSP", lstHDCT);
        BigDecimal tongTien = BigDecimal.ZERO;
        for (HoaDonChiTiet hoaDonChiTiet : lstHDCT) {
            if (hoaDonChiTiet.getDonGia() != null) {
                tongTien = tongTien.add(hoaDonChiTiet.getDonGia());
            }
        }
        model.addAttribute("tongTien", tongTien);
        return "/hoadon/detail";
    }

    @RequestMapping("update/{ma}")
    public String update(@PathVariable("ma") UUID ma){
        HoaDon hd = hoaDonRepository.findById(ma).get();
        hd.setTinhTrang(1);
        hoaDonRepository.save(hd);
        return "redirect:/bill/list";
    }

    @RequestMapping("search")
    public String filter(Model model,
                         @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                         @RequestParam(value = "filterTrangThai", required = false) Integer trangThai,
                         HttpSession session
    ) {
        if (trangThai == null) {
            session.removeAttribute("trangThai");
        }
        model.addAttribute("lstHD", phanTrangFilter(page, model, trangThai, session).getContent());
        return "/hoadon/list";
    }

    private List<HoaDon> phanTrang(Integer currentPage, Model model) {
        Pageable pageable = PageRequest.of(currentPage, 5);
        Page<HoaDon> hoaDonPage = hoaDonRepository.findAll(pageable);
        model.addAttribute("numpage", hoaDonPage.getTotalPages());
        model.addAttribute("currentPage", currentPage);
        return hoaDonPage.getContent();
    }

    private Page<HoaDon> phanTrangFilter(Integer currentPage, Model model, Integer trangThai, HttpSession session) {
        List<HoaDon> lstFilter = hoaDonRepository.findAll();
        if (trangThai != null) {
            lstFilter = lstFilter.stream()
                    .filter(pd -> trangThai.equals(pd.getTinhTrang()))
                    .collect(Collectors.toList());
            session.setAttribute("trangThai", trangThai);

        }
        Pageable pageable = PageRequest.of(currentPage, 5);
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), lstFilter.size());
        List<HoaDon> subList = lstFilter.subList(start, end);
        Page<HoaDon> hoaDonPage = new PageImpl<>(subList, pageable, lstFilter.size());

        model.addAttribute("numpage", hoaDonPage.getTotalPages());
        model.addAttribute("currentPage", currentPage);
        return hoaDonPage;
    }
}
