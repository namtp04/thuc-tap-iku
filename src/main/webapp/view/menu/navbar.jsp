<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
<ul class="nav ms-auto">
    <li class="nav-item">
        <c:if test="${not empty sessionScope.username}">
            <a class="nav-link text-white" aria-disabled="true" href="#"><i
                    class="bi bi-person-circle"></i>   ${sessionScope.username}</a>
        </c:if>
        <c:if test="${empty sessionScope.username}">
            <a class="nav-link text-white" aria-disabled="true" href="#"><i
                    class="bi bi-person-circle"></i>  Customer</a>
        </c:if>
    </li>
    <div class="nav-item">
        <c:if test="${not empty sessionScope.role}">
            <a class="nav-link disabled text-white" aria-disabled="true" href="#">ROLE: ${sessionScope.role}</a>
        </c:if>
    </div>

</ul>