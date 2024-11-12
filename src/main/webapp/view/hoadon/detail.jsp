<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>List Bill</title>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
            crossorigin="anonymous"></script>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js"
            integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r"
            crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.min.js"
            integrity="sha384-0pUGZvbkm6XF6gxjEnlmuGrJXVbNuzT9qBBavbLwCsOGabYfZo0T0to5eqruptLy"
            crossorigin="anonymous"></script>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet"/>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">
    <link href="https://cdn.lineicons.com/4.0/lineicons.css" rel="stylesheet"/>
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
<a class="btn btn-outline-secondary mt-3 mx-3" href="/bill/list">Back</a>
<div class="container">
    <h1 class="mb-3 mt-5">Bill Detail</h1>

    <div class="row mt-3 mb-3">
        <div class="col-6 mt-3">
        <h4>MÃ HÓA ĐƠN: ${hd.ma}</h4>
        </div>
        <div class="col-3"></div>
        <div class="col-3">
        <h5>NGƯỜI BÁN: ${hd.nhanVien.ma}</h5>
        <h5>KHÁCH HÀNG: ${hd.khachHang.ma}</h5>
        <h5>NGÀY TẠO: ${hd.ngayTao}</h5>
        </div>
    </div>
    <table class="table table-bordered">
        <thead>
        <tr>
            <th>Mã SP</th>
            <th>Tên sản phẩm</th>
            <th>Số lượng</th>
            <th>Thành tiền</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${lstSP}" var="cl">
            <tr>
                <td>${cl.chiTietSanPham.sanPham.ma}</td>
                <td>${cl.chiTietSanPham.sanPham.ten}</td>
                <td>${cl.soLuong}</td>
                <td>${cl.donGia}</td>
            </tr>
        </c:forEach>
        <c:if test="${lstSP.size()==0}">
            <tr>
                <td colspan="4">Dữ liệu trống</td>
            </tr>
        </c:if>
        </tbody>
    </table>
    <div class="row mt-5">
        <div class="col-6 mt-5">
            <h5>TRẠNG THÁI: <span <c:if test="${hd.tinhTrang==1}">class="text-success"</c:if><c:if test="${hd.tinhTrang==2}">class="text-danger"</c:if><c:if test="${hd.tinhTrang==0}">class="text-warning"</c:if>>${hd.tinhTrang==0?"Chưa thanh toán":hd.tinhTrang==1?"Đã thanh toán":"Đã hủy"}</span></h5>
        </div>
        <div class="col-3"></div>
        <div class="col-3 mt-5">
            <h5>TỔNG TIỀN: ${tongTien}</h5>

        </div>
    </div>
</div>
</body>
</html>
