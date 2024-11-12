<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css"
      integrity="sha512-SnH5WK+bZxgPHs44uWIX+LLJAJ9/2PkPKZ5QiAj6Ta86w+fsb2TkcmfRyVX3pBnMFcV7oQPJkl9QevSCWr3W6A=="
      crossorigin="anonymous" referrerpolicy="no-referrer"/>
<div class="container">
    <ul class="navbar-nav text-white" style="list-style-type: none;">
        <li class="nav-item mb-3 mt-1">
            <a href="/ban-hang/hien-thi" class="navbar-brand">
                <i class="fa-solid fa-cart-shopping"></i>
                <span>Quản lý bán hàng</span>
            </a>
        </li>
        <li class="nav-item mb-3 mt-1">
            <a href="/product/list" class="navbar-brand">
                <i class="fa-solid fa-shirt"></i>
                <span>Quản lý sản phẩm</span>
            </a>
        </li>
        <li class="nav-item mb-3 mt-1">
            <a href="/color/list" class="navbar-brand">
                <i class="fa-solid fa-palette"></i>
                <span>Quản lý màu sắc</span>
            </a>
        </li>
        <li class="nav-item mb-3 mt-1">
            <a href="/NSX/list" class="navbar-brand">
                <i class="fa-solid fa-ruler"></i>
                <span>Quản lý nhà sản xuất</span>
            </a>
        </li>
        <li class="nav-item mb-3 mt-1">
            <a href="/product-type/list" class="navbar-brand">
                <i class="fa-brands fa-product-hunt"></i>
                <span>Quản lý loại sản phẩm</span>
            </a>
        </li>
        <li class="nav-item mb-3 mt-1">
            <a href="/product-detail/list" class="navbar-brand">
                <i class="fa-solid fa-circle-info"></i>
                <span>Quản lý chi tiết sản phẩm</span>
            </a>
        </li>
        <c:if test="${sessionScope.role=='ADMIN'}">
            <li class="nav-item mb-3 mt-1">
                <a href="/chuc-vu/list" class="navbar-brand">
                    <i class="fa-solid fa-circle-info"></i>
                    <span>Quản lý chức vụ</span>
                </a>
            </li>
        </c:if>
        <c:if test="${sessionScope.role=='ADMIN'}">
            <li class="nav-item mb-3 mt-1">
                <a href="/nhan-vien/list" class="navbar-brand">
                    <i class="fa-solid fa-person"></i>
                    <span>Quản lý nhân viên</span>
                </a>
            </li>
        </c:if>
        <c:if test="${sessionScope.role=='ADMIN'}">
            <li class="nav-item mb-3 mt-1">
                <a href="/cua-hang/list" class="navbar-brand">
                    <i class="fa-solid fa-money-bill"></i>
                    <span>Quản lý cửa hàng</span>
                </a>
            </li>
        </c:if>
        <li class="nav-item mb-3 mt-1">
            <a href="/customer/list" class="navbar-brand">
                <i class="fa-solid fa-bars-progress"></i>
                <span>Quản lý khách hàng</span>
            </a>
        </li>

        <li class="nav-item mb-3 mt-1">
            <a href="/bill/list" class="navbar-brand">
                <i class="fa-solid fa-money-bill"></i>
                <span>Quản lý hóa đơn</span>
            </a>
        </li>
    </ul>
    <div class="text-white">
        <c:if test="${not empty sessionScope.username}">
            <a href="/auth/logout" class="navbar-brand d-flex justify-content-start mt-4"
               onclick="return confirm('Are you sure want to logout?')">
                <i class="lni lni-exit"></i>
                <span>Logout</span>
            </a>
        </c:if>
        <c:if test="${empty sessionScope.username}">
            <a href="/auth/login" class="navbar-brand d-flex justify-content-end mt-4">
                <i class="fa-solid fa-arrow-right-to-bracket"></i>
                <span>Login</span>
            </a>
        </c:if>
        <a href="#" class="navbar-brand d-flex justify-content-center">
            <span>Trần Phương Nam</span>
        </a>
    </div>


</div>

