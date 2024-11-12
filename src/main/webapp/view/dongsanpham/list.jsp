<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>List Product types</title>
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
<div class="container">
    <h1 class="mb-3 mt-5">Product types Management</h1>
    <a class="btn btn-outline-success" href="/product-type/view-add">Add</a>
    <div class="d-flex justify-content-end">
        <form id="colorSearchForm" action="/product-type/search" method="post">
            <div class="input-group">
                <input name="productTypeSearchValue" type="text" class="form-control" placeholder="Enter name product-type"
                       aria-label="Search customer" value="${param.productTypeSearchValue}">
                <button class="btn btn-outline-primary" type="submit" id="button-addon2">Search</button>
            </div>
        </form>
    </div>
    <table class="table">
        <thead>
        <tr>
            <th>Mã</th>
            <th>Tên</th>
            <th colspan="2">Action</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${lstLSP}" var="cl">
            <tr>
                <td>${cl.ma}</td>
                <td>${cl.ten}</td>
                <td>
                    <a class="btn btn-outline-warning" href="/product-type/view-update/${cl.id}">Update</a>
                    <a onclick="return confirm('Do you want to delete this item?')" class="btn btn-outline-danger"
                       href="/product-type/delete/${cl.id}">Delete</a>
                </td>
            </tr>
        </c:forEach>
        <c:if test="${lstLSP.size()==0}">
            <tr>
                <td colspan="4">Dữ liệu trống</td>
            </tr>
        </c:if>
        </tbody>
    </table>
</div>
<ul class="pagination justify-content-center">
    <c:if test="${currentPage==0}">
        <li class="page-item disabled">
            <a class="page-link">Previous</a>
        </li>
    </c:if>
    <c:if test="${currentPage>0}">
        <li class="page-item">
            <a class="page-link" href="?page=${currentPage-1}">Previous</a>
        </li>
    </c:if>
    <c:forEach begin="1" end="${numpage}" var="trang">
        <li class="page-item">
            <a class="page-link <c:if test="${currentPage == trang-1}">active</c:if>"
               href="?page=${trang-1}">${trang}</a>
        </li>
    </c:forEach>
    <c:if test="${currentPage==numpage-1}">
        <li class="page-item disabled">
            <a class="page-link">Next</a>
        </li>
    </c:if>
    <c:if test="${currentPage<numpage-1}">
        <li class="page-item">
            <a class="page-link" href="?page=${currentPage+1}">Next</a>
        </li>
    </c:if>
</ul>

<script>
    window.onload = function() {
        <c:if test="${not empty deleteFail}">
        alert('${deleteFail}');
        window.location.href='/product-type/list';
        </c:if>
    };
</script>
</body>
</html>
