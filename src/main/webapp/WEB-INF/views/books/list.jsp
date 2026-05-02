<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>Books</title>
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
    <h2>Books (with Authors via INNER JOIN)</h2>

    <c:if test="${not empty successMessage}">
        <div class="alert alert-success">${successMessage}</div>
    </c:if>

    <div class="actions">
        <span>Total: <strong>${books.size()}</strong> book(s)</span>
        <a href="<c:url value='/books/new'/>" class="btn btn-success">+ Add Book</a>
    </div>

    <table>
        <thead>
        <tr>
            <th>ID</th>
            <th>Title</th>
            <th>ISBN</th>
            <th>Price</th>
            <th>Year</th>
            <th>Author</th>
            <th>Nationality</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="b" items="${books}">
            <tr>
                <td>${b.bookId}</td>
                <td><c:out value="${b.title}"/></td>
                <td><c:out value="${b.isbn}"/></td>
                <td><fmt:formatNumber value="${b.price}" type="currency" currencySymbol="₹"/></td>
                <td>${b.publishedYear}</td>
                <td><c:out value="${b.authorName}"/></td>
                <td><c:out value="${b.authorNationality}"/></td>
                <td class="action-cell">
                    <a href="<c:url value='/books/edit/${b.bookId}'/>" class="btn btn-warning">Edit</a>
                </td>
            </tr>
        </c:forEach>
        <c:if test="${empty books}">
            <tr><td colspan="8" style="text-align:center;">No books found.</td></tr>
        </c:if>
        </tbody>
    </table>
</div>
</body>
</html>
