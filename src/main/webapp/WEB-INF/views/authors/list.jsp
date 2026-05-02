<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Authors</title>
    <link rel="stylesheet" href="<c:url value='/css/styles.css'/>"/>
</head>
<body>
<div class="navbar">
    <h1>Library Management System</h1>
    <div>
        <a href="<c:url value='/'/>">Home</a>
        <a href="<c:url value='/authors'/>">Authors</a>
        <a href="<c:url value='/books'/>">Books</a>
    </div>
</div>

<div class="container">
    <h2>Authors</h2>

    <c:if test="${not empty successMessage}">
        <div class="alert alert-success">${successMessage}</div>
    </c:if>

    <div class="actions">
        <span>Total: <strong>${authors.size()}</strong> author(s)</span>
        <a href="<c:url value='/authors/new'/>" class="btn btn-success">+ Add Author</a>
    </div>

    <table>
        <thead>
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Email</th>
            <th>Nationality</th>
            <th>Books</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="a" items="${authors}">
            <tr>
                <td>${a.id}</td>
                <td><c:out value="${a.name}"/></td>
                <td><c:out value="${a.email}"/></td>
                <td><c:out value="${a.nationality}"/></td>
                <td>${a.books.size()}</td>
                <td class="action-cell">
                    <a href="<c:url value='/authors/edit/${a.id}'/>" class="btn btn-warning">Edit</a>
                </td>
            </tr>
        </c:forEach>
        <c:if test="${empty authors}">
            <tr><td colspan="6" style="text-align:center;">No authors found.</td></tr>
        </c:if>
        </tbody>
    </table>
</div>
</body>
</html>
