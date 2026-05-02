<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
    <title>Add Book</title>
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
    <h2>Add New Book</h2>

    <form:form method="post" action="${pageContext.request.contextPath}/books/save"
               modelAttribute="book">
        <div class="form-group">
            <label for="title">Title</label>
            <form:input path="title" id="title" cssClass="form-control"/>
            <form:errors path="title" cssClass="error"/>
        </div>

        <div class="form-group">
            <label for="isbn">ISBN</label>
            <form:input path="isbn" id="isbn" cssClass="form-control" placeholder="978-xxxxxxxxxx"/>
            <form:errors path="isbn" cssClass="error"/>
        </div>

        <div class="form-group">
            <label for="price">Price</label>
            <form:input path="price" id="price" type="number" step="0.01" cssClass="form-control"/>
            <form:errors path="price" cssClass="error"/>
        </div>

        <div class="form-group">
            <label for="publishedYear">Published Year</label>
            <form:input path="publishedYear" id="publishedYear" type="number" cssClass="form-control"/>
            <form:errors path="publishedYear" cssClass="error"/>
        </div>

        <div class="form-group">
            <label for="authorId">Author</label>
            <select name="authorId" id="authorId" class="form-control">
                <option value="">-- Select Author --</option>
                <c:forEach var="a" items="${authors}">
                    <option value="${a.id}"><c:out value="${a.name}"/></option>
                </c:forEach>
            </select>
            <form:errors path="author" cssClass="error"/>
        </div>

        <button type="submit" class="btn btn-primary">Save Book</button>
        <a href="<c:url value='/books'/>" class="btn btn-secondary">Cancel</a>
    </form:form>
</div>
</body>
</html>
