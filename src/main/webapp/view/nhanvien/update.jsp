<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Create Chức Vụ</title>
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
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css"
          integrity="sha512-SnH5WK+bZxgPHs44uWIX+LLJAJ9/2PkPKZ5QiAj6Ta86w+fsb2TkcmfRyVX3pBnMFcV7oQPJkl9QevSCWr3W6A=="
          crossorigin="anonymous" referrerpolicy="no-referrer"/>
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
<div class="container">
    <h1 class="text-center mb-3 mt-5">Update nhân viên</h1>
    <div class="d-flex justify-content-center">
        <div class="card w-50 d-flex justify-content-center">
            <div class="card-body">
                <form action="/nhan-vien/update" method="post">
                    <input type="text" class="form-control" value="${nv.id}" name="id" style="display: none ">
                    <div class="mb-3">
                        <label class="form-label">Mã</label>
                        <input type="text" class="form-control" value="${nv.ma}" name="ma">
                        <c:if test="${not empty errors}">
                            <span class="text-danger">${errors['ma']}</span>
                        </c:if>
                        <span class="text-danger">${error}</span>
                    </div>

                    <div class="mb-3">
                        <label class="form-label">Họ</label>
                        <input type="text" class="form-control" value="${nv.ho}" name="ho">
                        <c:if test="${not empty errors}">
                            <span class="text-danger">${errors['ho']}</span>
                        </c:if>
                    </div>

                    <div class="mb-3">
                        <label class="form-label">Tên đệm</label>
                        <input type="text" class="form-control" value="${nv.tenDem}" name="tenDem">
                        <c:if test="${not empty errors}">
                            <span class="text-danger">${errors['tenDem']}</span>
                        </c:if>
                    </div>

                    <div class="mb-3">
                        <label class="form-label">Tên</label>
                        <input type="text" class="form-control" value="${nv.ten}" name="ten">
                        <c:if test="${not empty errors}">
                            <span class="text-danger">${errors['ten']}</span>
                        </c:if>
                    </div>

                    <div class="row mb-3 mt-4">
                        <label class="col-2 form-label">Giới tính:</label>
                        <div class="col-2 form-check">
                            <input type="radio" class="form-check-input ms-3" value="Nam" checked
                                   name="gioiTinh" ${nv.gioiTinh=="Nam"?"checked":""}>
                            <label class="form-check-label">Nam</label>
                        </div>
                        <div class="col-2 form-check">
                            <input type="radio" class="form-check-input" value="Nữ"
                                   name="gioiTinh" ${nv.gioiTinh=="Nữ"?"checked":""}>
                            <label class="form-check-label">Nữ</label>
                        </div>
                    </div>

                    <div class="mb-3">
                        <label class="form-label">Ngày sinh</label>
                        <input type="date" class="form-control" value="${nv.ngaySinh}" name="ngaySinh">
                        <c:if test="${not empty errors}">
                            <span class="text-danger">${errors['ngaySinh']}</span>
                        </c:if>
                    </div>

                    <div class="mb-3">
                        <label class="form-label">Địa chỉ</label>
                        <input type="text" class="form-control" value="${nv.diaChi}" name="diaChi">
                        <c:if test="${not empty errors}">
                            <span class="text-danger">${errors['diaChi']}</span>
                        </c:if>
                    </div>

                    <div class="mb-3">
                        <label class="form-label">SĐT</label>
                        <input type="text" class="form-control" value="${nv.sdt}" name="sdt">
                        <c:if test="${not empty errors}">
                            <span class="text-danger">${errors['sdt']}</span>
                        </c:if>
                    </div>

                    <div class="mb-3">
                        <label class="form-label">Mật khẩu</label>
                        <input type="password" class="form-control" value="${nv.matKhau}" name="matKhau">
                        <c:if test="${not empty errors}">
                            <span class="text-danger">${errors['matKhau']}</span>
                        </c:if>
                    </div>

                    <div class="mb-3">
                        <label class="form-label">Chức vụ</label>
                        <select name="chucVu" class="form-select" aria-label="Default select example">
                            <option value="">-Choose Chức vụ-</option>
                            <c:forEach items="${lstCV}" var="n">
                                <option value="${n.id}" ${n.id == nv.chucVu.id ? 'selected' : ''}>
                                        ${n.ten}
                                </option>
                            </c:forEach>
                        </select>
                        <c:if test="${not empty errors}">
                            <span class="text-danger">${errors['chucVu']}</span>
                        </c:if>
                    </div>

                    <div class="mb-3">
                        <label class="form-label">Cửa hàng</label>
                        <select name="cuaHang" class="form-select" aria-label="Default select example">
                            <option value="">-Choose Cửa hàng-</option>
                            <c:forEach items="${lstCH}" var="n">
                                <option value="${n.id}" ${n.id == nv.cuaHang.id ? 'selected' : ''}>
                                        ${n.ten}
                                </option>
                            </c:forEach>
                        </select>
                        <c:if test="${not empty errors}">
                            <span class="text-danger">${errors['chucVu']}</span>
                        </c:if>
                    </div>

                    <div class="row mb-3 mt-4">
                        <label class="col-2 form-label">Trạng thái:</label>
                        <div class="col-3 form-check">
                            <input type="radio" class="form-check-input ms-3" value="1" checked
                                   name="trangThai" ${nv.trangThai==1?"checked":""}>
                            <label class="form-check-label">ADMIN</label>
                        </div>
                        <div class="col-3 form-check">
                            <input type="radio" class="form-check-input" value="0"
                                   name="trangThai" ${nv.trangThai==0?"checked":""}>
                            <label class="form-check-label">STAFF</label>
                        </div>
                    </div>

                    <div class="text-center">
                        <a href="/nhan-vien/list" class="btn btn-secondary">Back</a>
                        <button type="submit" class="btn btn-outline-success"
                                onclick="return confirm('Are you sure want to update this item?')">Update
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>
