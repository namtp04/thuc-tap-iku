<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <title>Admin Product Detail</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet"/>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">


    <link href="https://cdn.lineicons.com/4.0/lineicons.css" rel="stylesheet"/>
    <%--    <link rel="stylesheet" type="text/css"--%>
    <%--          href="${pageContext.request.contextPath}/src/main/webapp/css/style.css"/>--%>
</head>

<body>
<div class="offcanvas offcanvas-start" data-bs-scroll="true" data-bs-backdrop="false"
     tabindex="-1"
     id="offcanvasScrolling" aria-labelledby="offcanvasScrollingLabel" style="background-color: #0e2238">
    <div class="offcanvas-header">
        <h5 class="offcanvas-title" id="offcanvasScrollingLabel">
            <jsp:include page="../menu/navbar.jsp"></jsp:include>
        </h5>
        <button type="button" class="btn-close bg-white" data-bs-dismiss="offcanvas"
                aria-label="Close"></button>
    </div>
    <div class="offcanvas-body">
        <jsp:include page="../menu/menu.jsp"></jsp:include>
    </div>
</div>
<nav class="navbar navbar-expand-sm bg-body-tertiary">
    <div class="container-fluid">
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav">
                <li class="nav-item">
                    <div class="container-fluid">
                        <aside id="sidebar">
                            <h4 data-bs-toggle="offcanvas"
                                data-bs-target="#offcanvasScrolling"
                                aria-controls="offcanvasScrolling" style="padding-top: 5px;"><i
                                    class="lni lni-text-align-left"></i></h4>
                        </aside>
                    </div>
                </li>
            </ul>
        </div>
    </div>
</nav>
<main class="content px-3 py-4">
    <div class="container-fluid">
        <div class="mb-3">
            <h1 class="text-center mb-4 mt-1">Bán hàng</h1>
            <div class="row">
                <div class="col-8">
                    <div class="row">
                        <div class="col-12">
                            <div class="card">
                                <div class="card-header">
                                    <h5 class="card-title">Hóa đơn chờ</h5>
                                </div>
                                <div class="card-body">
                                    <div class="row">
                                        <div class="col-6 mt-2">

                                            <a class="mx-2"
                                               href="/ban-hang/tao-hoa-don-cho">
                                                <button class="btn btn-outline-success">Tạo hóa đơn chờ</button>
                                            </a>
                                            <c:if test="${empty idOrderChoose}">
                                                <a href="#">
                                                    <button disabled class="btn btn-outline-danger">Hủy hóa đơn
                                                    </button>
                                                </a>
                                            </c:if>
                                            <c:if test="${not empty idOrderChoose }">
                                                <a href="/ban-hang/huy-hoa-don/${idOrderChoose}">
                                                    <button class="btn btn-outline-danger">Hủy hóa đơn</button>
                                                </a>
                                            </c:if>

                                        </div>
                                        <div class="col-6 mt-2">
                                            <h6>
                                                Hóa đơn hiện tại : <span>
                                                                        <strong>
                                                                            ${not empty idOrderChoose ? idOrderChoose : '0'}
                                                                        </strong>
                                                                      </span>
                                            </h6>
                                        </div>
                                    </div>
                                    <table class="table mt-2">
                                        <thead>
                                        <tr class="item-row">
                                            <th scope="col">#</th>
                                            <th scope="col">Mã nhân viên</th>
                                            <th scope="col">Mã khách hàng</th>
                                            <th scope="col">Số điện thoại</th>
                                            <th scope="col">Tổng tiền</th>
                                            <th scope="col">Ngày mua</th>
                                            <th scope="col">Trạng thái</th>
                                            <th scope="col">Action</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <c:forEach var="n" varStatus="loop" items="${lstHD}">
                                            <tr>
                                                <td>${loop.count}</td>
                                                <td>${n.nhanVien.ma}</td>
                                                <td>${n.khachHang.ma}</td>
                                                <td>${n.khachHang.sdt}</td>
                                                <td></td>
                                                <td>${n.ngayTao}</td>
                                                <td
                                                        <c:if test="${n.tinhTrang==1}">class="text-success" </c:if>
                                                        <c:if test="${n.tinhTrang==2}">class="text-danger" </c:if>
                                                        <c:if test="${n.tinhTrang==0}">class="text-warning"</c:if>>${n.tinhTrang==0?"Chưa thanh toán":n.tinhTrang==1?"Đã thanh toán":"Đã hủy"}</td>
                                                <td>
                                                    <a class="btn btn-warning" href="/ban-hang/chon-hoa-don/${n.id}">Choose</a>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row mt-5">
                        <div class="col-12">
                            <div class="card">
                                <div class="card-header">
                                    <div class="row">
                                        <div class="col-6">
                                            <h5 class="card-title">Giỏ hàng</h5>
                                        </div>
                                        <div class="col-6">
                                            <div class=" d-flex justify-content-end ">
                                                <c:if test="${empty sessionScope.idOrderChoose}">
                                                    <%--                                                            <a  href="#" > <button class="btn btn-danger" disabled >Clear All Cart</button></a>--%>
                                                </c:if>
                                                <c:if test="${not empty sessionScope.idOrderChoose}">
                                                    <a href="/ban-hang/clear-all"
                                                       class="btn btn-danger">Xóa toàn bộ giỏ hàng</a>
                                                </c:if>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="card-body">
                                    <table class="table">
                                        <thead>
                                        <tr>
                                            <th scope="col">#</th>
                                            <th scope="col">Mã CTSP</th>
                                            <th scope="col">Sản phẩm</th>
                                            <th scope="col">Màu sắc</th>
                                            <th scope="col">Dòng sản phẩm</th>
                                            <th scope="col">Giá bán</th>
                                            <th scope="col">Số lượng</th>
                                            <th scope="col">Thành tiền</th>
                                            <th scope="col">Action</th>
                                        </tr>
                                        </thead>
                                        <tbody>

                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row mt-5">
                        <div class="col-12">
                            <div>

                                <div class="row">
                                    <div class="col-4">

                                    </div>
                                    <div class="col-8">
                                        <form method="post"
                                              action="/ban-hang/san-pham-filter">
                                            <div class="row">
                                                <div class="col-lg-3">
                                                    <span>Product</span>
                                                    <select name="searchProduct" class="form-select"
                                                            aria-label="Default select example">
                                                        <option value="" ${not empty sessionScope.selectedProduct ? '' : 'selected'}>
                                                            All
                                                        </option>
                                                        <c:forEach items="${listProduct}" var="product">
                                                            <option value="${product.id}" ${product.id == sessionScope.selectedProduct ? 'selected' : ''}>
                                                                    ${product.ten}
                                                            </option>
                                                        </c:forEach>
                                                    </select>
                                                </div>

                                                <div class="col-lg-3" style="">
                                                    <span>Product Type</span>
                                                    <select name="searchProductType" class="form-select"
                                                            aria-label="Default select example">
                                                        <option value="" ${not empty sessionScope.selectedProductType ? '' : 'selected'}>
                                                            All
                                                        </option>
                                                        <c:forEach items="${listProductType}" var="productType">
                                                            <option value="${productType.id}" ${productType.id == sessionScope.selectedProductType ? 'selected' : ''}>
                                                                    ${productType.ten}
                                                            </option>
                                                        </c:forEach>
                                                    </select>
                                                </div>

                                                <div class="col-lg-3">
                                                    <span>Color</span>
                                                    <select name="searchColor" class="form-select"
                                                            aria-label="Default select example">
                                                        <option value="" ${not empty sessionScope.selectedColor ? '' : 'selected'}>
                                                            All
                                                        </option>
                                                        <c:forEach items="${listColor}" var="color">
                                                            <option value="${color.id}" ${color.id == sessionScope.selectedColor ? 'selected' : ''}>
                                                                    ${color.ten}
                                                            </option>
                                                        </c:forEach>
                                                    </select>
                                                </div>
                                                <div class="col-lg-3">
                                                    <button type="submit" class="btn btn-outline-primary mt-4">Filter
                                                    </button>
                                                </div>
                                            </div>

                                        </form>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-12">
                            <div class="card">
                                <div class="card-header">
                                    <h5 class="card-title">Product</h5>
                                </div>
                                <div class="card-body">

                                    <%--                                            <!-- ModalProductDetail -->--%>
                                    <div class="modal fade" id="modalProduct" tabindex="-1"
                                         aria-labelledby="exampleModalLabel" aria-hidden="true">
                                        <div class="modal-dialog" role="document">
                                            <div class="modal-content">
                                                <div class="modal-header">
                                                    <h5 class="modal-title" id="exampleModalLabel">Product</h5>
                                                    <button type="button" class="btn-close"
                                                            data-bs-dismiss="modal" aria-label="Close"></button>
                                                </div>
                                                <div class="modal-body">
                                                    <div class="row">
                                                        <div class="col-12">
                                                            <form action=""
                                                                  method="post">
                                                                <div class="row">
                                                                    <div class="col-12 mt-2">
                                                                        <label
                                                                                class="form-label">Mã SP</label>
                                                                        <input type="text" class="form-control"
                                                                               name="code" readonly
                                                                               value="${sp.samPham.ma}">
                                                                    </div>
                                                                    <div class="col-12 mt-2">
                                                                        <label
                                                                                class="form-label">Tên SP</label>
                                                                        <input type="text" class="form-control"
                                                                               name="product" disabled
                                                                               value="${sp.samPham.ten}">
                                                                    </div>
                                                                    <div class="col-12 mt-2">
                                                                        <label
                                                                                class="form-label">Màu sắc</label>
                                                                        <input type="text" class="form-control"
                                                                               name="mauSac" disabled
                                                                               value="${sp.mauSac.ten}">
                                                                    </div>
                                                                    <div class="col-12 mt-2">
                                                                        <label
                                                                                class="form-label">Dòng sản phẩm</label>
                                                                        <input type="text" class="form-control"
                                                                               name="dongSanPham" disabled
                                                                               value="${sp.dongSanPham.ten}">
                                                                    </div>
                                                                    <div class="col-12 mt-2">
                                                                        <label
                                                                                class="form-label">Số lượng</label>
                                                                        <input type="number"
                                                                               class="form-control"
                                                                               name="soLuongTon"
                                                                               value="${sp.soLuongTon}">
                                                                    </div>
                                                                    <div class="col-12 mt-2">
                                                                        <label
                                                                                class="form-label">Giá bán</label>
                                                                        <input type="number"
                                                                               class="form-control"
                                                                               name="giaBan" disabled
                                                                               value="${sp.giaBan}">
                                                                    </div>
                                                                    <div class="col-12 mt-2">
                                                                        <label class="form-label">Thành tiền</label>
                                                                        <input type="number"
                                                                               class="form-control"
                                                                               name="totalMoney" readonly>
                                                                    </div>
                                                                    <div class="col-12 mt-3 d-flex justify-content-center">
                                                                        <button class="btn btn-success"
                                                                                type="submit">Thêm vào giỏ
                                                                        </button>
                                                                    </div>
                                                                </div>
                                                            </form>
                                                        </div>

                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <%--                                            <!-- ModalProductDetail -->--%>
                                    <table class="table">
                                        <thead>
                                        <tr>
                                            <th scope="col">#</th>
                                            <th scope="col">Mã SP</th>
                                            <th scope="col">Sản phẩm</th>
                                            <th scope="col">Màu</th>
                                            <th scope="col">Dòng sản phẩm</th>
                                            <th scope="col">Số lượng</th>
                                            <th scope="col">Giá bán</th>
                                            <th scope="col">Action</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <c:forEach items="${lstSPCT}" var="sp" varStatus="loop">
                                            <tr>
                                                <td>${loop.count}</td>
                                                <td>${sp.sanPham.ma}</td>
                                                <td>${sp.sanPham.ten}</td>
                                                <td>${sp.mauSac.ten}</td>
                                                <td>${sp.dongSanPham.ten}</td>
                                                <td>${sp.soLuongTon}</td>
                                                <td>${sp.giaBan}</td>
                                                <td>
                                                    <c:if test="${empty sessionScope.idOrderChoose}">
                                                        <button disabled type="button"
                                                                class="btn btn-primary"
                                                                data-bs-toggle="#"
                                                                data-bs-target="#modalProduct">
                                                            Choose
                                                        </button>
                                                    </c:if>
                                                    <c:if test="${not empty sessionScope.idOrderChoose}">
                                                        <button type="button" class="btn btn-primary"
                                                                data-bs-toggle="modal"
                                                                data-bs-target="#modalProduct"
                                                        >
                                                            Choose
                                                        </button>
                                                    </c:if>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                        <c:if test="${lstSPCT.size()==0}">
                                            <tr>
                                                <td colspan="10">Dữ liệu trống</td>
                                            </tr>
                                        </c:if>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-4">
                    <div class="card">
                        <div class="card-header">
                            <h5 class="card-title">Order - Payment</h5>
                        </div>
                        <div class="card-body">
                            <div class="card">
                                <div class="card-header">
                                    <h5 class="card-title">Thông tin khách hàng</h5>
                                </div>
                                <div class="card-body">
                                    <%-- modal--%>
                                    <div class="modal fade" id="changeModal" tabindex="-1"
                                         aria-labelledby="changeModalLabel" aria-hidden="true">
                                        <div class="modal-dialog modal-lg">
                                            <div class="modal-content">
                                                <div class="modal-header">
                                                    <h5 class="modal-title" id="changeModalLabel">Change
                                                        Information</h5>
                                                    <button type="button" class="btn-close"
                                                            data-bs-dismiss="modal" aria-label="Close"></button>
                                                </div>
                                                <div class="modal-body">
                                                    <!-- Nav tabs -->
                                                    <ul class="nav nav-tabs" id="myTab" role="tablist">
                                                        <li class="nav-item">
                                                            <button class="nav-link active" id="tab1-tab"
                                                                    data-bs-toggle="tab" data-bs-target="#tab1"
                                                                    type="button" role="tab"
                                                                    aria-controls="tab1" aria-selected="true">
                                                                Customer List
                                                            </button>
                                                        </li>
                                                        <li class="nav-item">
                                                            <button class="nav-link" id="tab2-tab"
                                                                    data-bs-toggle="tab" data-bs-target="#tab2"
                                                                    type="button" role="tab"
                                                                    aria-controls="tab2" aria-selected="false">
                                                                Add New Customer
                                                            </button>
                                                        </li>
                                                    </ul>

                                                    <!-- Tab panes -->
                                                    <div class="tab-content" id="myTabContent">
                                                        <div class="tab-pane fade show active" id="tab1"
                                                             role="tabpanel" aria-labelledby="tab1-tab">
                                                            <table class="table">
                                                                <thead>
                                                                <tr>
                                                                    <th>Mã</th>
                                                                    <th>Tên</th>
                                                                    <th>Ngày sinh</th>
                                                                    <th>SĐT</th>
                                                                    <th>Địa chỉ</th>
                                                                    <th colspan="2">Action</th>
                                                                </tr>
                                                                </thead>
                                                                <tbody>
                                                                <c:forEach items="${lstKH}" var="cl">
                                                                    <tr>
                                                                        <td>${cl.ma}</td>
                                                                        <td>${cl.ho} ${cl.tenDem} ${cl.ten}</td>
                                                                        <td>${cl.ngaySinh}</td>
                                                                        <td>${cl.sdt}</td>
                                                                        <td>${cl.diaChi}</td>
                                                                        <td>
                                                                            <a class="btn btn-warning"
                                                                               href="/ban-hang/chon-khach-hang/${cl.id}">Choose</a>
                                                                        </td>
                                                                    </tr>
                                                                </c:forEach>
                                                                <c:if test="${lstKH.size()==0}">
                                                                    <tr>
                                                                        <td colspan="7">Dữ liệu trống</td>
                                                                    </tr>
                                                                </c:if>
                                                                </tbody>
                                                            </table>

                                                        </div>
                                                        <div class="tab-pane fade" id="tab2" role="tabpanel"
                                                             aria-labelledby="tab2-tab">
                                                            <!-- Content for Tab 2 -->
                                                            <div class="mb-3 mt-2">
                                                                <form action="/ban-hang/them-khach-hang"
                                                                      method="post">
                                                                    <div class="mb-3">
                                                                        <label class="form-label">Mã</label>
                                                                        <input type="text" class="form-control"
                                                                               value="${kh.ma}" name="ma">
                                                                        <c:if test="${not empty errors}">
                                                                            <span class="text-danger">${errors['ma']}</span>
                                                                        </c:if>
                                                                        <span class="text-danger">${error}</span>
                                                                    </div>

                                                                    <div class="mb-3">
                                                                        <label class="form-label">Họ</label>
                                                                        <input type="text" class="form-control"
                                                                               value="${kh.ho}" name="ho">
                                                                        <c:if test="${not empty errors}">
                                                                            <span class="text-danger">${errors['ho']}</span>
                                                                        </c:if>
                                                                    </div>

                                                                    <div class="mb-3">
                                                                        <label class="form-label">Tên đệm</label>
                                                                        <input type="text" class="form-control"
                                                                               value="${kh.tenDem}" name="tenDem">
                                                                        <c:if test="${not empty errors}">
                                                                            <span class="text-danger">${errors['tenDem']}</span>
                                                                        </c:if>
                                                                    </div>

                                                                    <div class="mb-3">
                                                                        <label class="form-label">Tên</label>
                                                                        <input type="text" class="form-control"
                                                                               value="${kh.ten}" name="ten">
                                                                        <c:if test="${not empty errors}">
                                                                            <span class="text-danger">${errors['ten']}</span>
                                                                        </c:if>
                                                                    </div>

                                                                    <div class="mb-3">
                                                                        <label class="form-label">Ngày sinh</label>
                                                                        <input type="date" class="form-control"
                                                                               value="${kh.ngaySinh}" name="ngaySinh">
                                                                        <c:if test="${not empty errors}">
                                                                            <span class="text-danger">${errors['ngaySinh']}</span>
                                                                        </c:if>
                                                                    </div>

                                                                    <div class="mb-3">
                                                                        <label class="form-label">Địa chỉ</label>
                                                                        <input type="text" class="form-control"
                                                                               value="${kh.diaChi}" name="diaChi">
                                                                        <c:if test="${not empty errors}">
                                                                            <span class="text-danger">${errors['diaChi']}</span>
                                                                        </c:if>
                                                                    </div>

                                                                    <div class="mb-3">
                                                                        <label class="form-label">SĐT</label>
                                                                        <input type="text" class="form-control"
                                                                               value="${kh.sdt}" name="sdt">
                                                                        <c:if test="${not empty errors}">
                                                                            <span class="text-danger">${errors['sdt']}</span>
                                                                        </c:if>
                                                                    </div>

                                                                    <div class="mb-3">
                                                                        <label class="form-label">Mật khẩu</label>
                                                                        <input type="password" class="form-control"
                                                                               value="${kh.matKhau}" name="matKhau">
                                                                        <c:if test="${not empty errors}">
                                                                            <span class="text-danger">${errors['matKhau']}</span>
                                                                        </c:if>
                                                                    </div>

                                                                    <div class="mb-3">
                                                                        <label class="form-label">Thành phố</label>
                                                                        <input type="text" class="form-control"
                                                                               value="${kh.thanhPho}" name="thanhPho">
                                                                        <c:if test="${not empty errors}">
                                                                            <span class="text-danger">${errors['thanhPho']}</span>
                                                                        </c:if>
                                                                    </div>

                                                                    <div class="mb-3">
                                                                        <label class="form-label">Quốc gia</label>
                                                                        <input type="text" class="form-control"
                                                                               value="${kh.quocGia}" name="quocGia">
                                                                        <c:if test="${not empty errors}">
                                                                            <span class="text-danger">${errors['quocGia']}</span>
                                                                        </c:if>
                                                                    </div>

                                                                    <div class="text-center">
                                                                        <button type="submit"
                                                                                class="btn btn-outline-success"
                                                                                onclick="return confirm('Are you sure want to create this item?')">
                                                                            Add
                                                                        </button>
                                                                    </div>
                                                                </form>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="modal-footer">
                                                    <button type="button" class="btn btn-secondary"
                                                            data-bs-dismiss="modal">Close
                                                    </button>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <%-- modal--%>

                                    <form>
                                        <div class="row">
                                            <div class="col-12">
                                                <label class="form-label">SĐT</label>
                                                <input value="${customerInfo.sdt}" type="text"
                                                       class="form-control" name="sdt"
                                                       disabled>
                                            </div>
                                            <div class="col-12">
                                                <label for="fullName" class="form-label">Tên</label>
                                                <input value="${customerInfo.ten}" type="text"
                                                       class="form-control" id="fullName"
                                                       name="ten" disabled>
                                            </div>

                                            <div class="col-12 mt-2 d-flex justify-content-center">

                                                <a class="btn btn-primary" type="button"
                                                   data-bs-toggle="modal" data-bs-target="#changeModal"
                                                   href="/ban-hang/them-khach-hang">
                                                    Choose
                                                </a>
                                            </div>
                                        </div>
                                    </form>
                                </div>
                            </div>
                            <form action="/sell-manager/pay" method="post">
                                <div class="row">
                                    <div class="col-12 mt-2">
                                        <label>Mã NV</label>
                                        <input disabled value="${staffNames[bill.idStaff]}" type="text"
                                               class="form-control" name="staffName">
                                    </div>
                                    <div class="col-12 mt-2">
                                        <label>Ngày mua</label>

                                        <input disabled value="${bill.dateBuy}"
                                               type="datetime-local" class="form-control" id="dateBuy"
                                               name="dateBuy">
                                    </div>
                                    <div class="col-12 mt-2">
                                        <label>Tổng tiền</label>
                                        <input id="total" disabled value="${bill.total}" type="number"
                                               class="form-control" name="totalMoney">
                                    </div>
                                    <div class="col-12 mt-2">
                                        <label>Tiền khách trả</label>
                                        <c:if test="${empty sessionScope.idOrderChose}">
                                            <input disabled min="1" max="99999999" id="cash" type="number"
                                                   class="form-control" name="totalMoney">
                                        </c:if>

                                        <c:if test="${ not empty sessionScope.idOrderChose}">
                                            <input min="1" max="99999999" id="cash" type="number" class="form-control"
                                                   name="totalMoney">
                                        </c:if>
                                        <span id="error-cash" style="color: red" class=" text-center fw-bold"></span>
                                    </div>
                                    <div class="col-12 mt-2">
                                        <label>Tiền thừa</label>
                                        <input id="refund" disabled type="number" class="form-control"
                                               name="totalMoney">
                                    </div>

                                </div>
                                <div class="col-12 mt-2 d-flex justify-content-center">
                                    <button type="submit" class="btn btn-success">Thanh toán</button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</main>
</div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL"
        crossorigin="anonymous"></script>

<script>
    window.onload = function () {
        <c:if test="${not empty openModal}">
        alert('Thêm khách hàng thất bại\nVui lòng kiểm tra dữ liệu đầu vào');
        window.location.href = '/ban-hang/hien-thi';
        </c:if>
    };
</script>

</body>
</html>