<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
    <title>Edit Author</title>
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
    <h2>Edit Author</h2>

    <form:form method="post" action="${pageContext.request.contextPath}/authors/update/${author.id}"
               modelAttribute="author">
        <form:hidden path="id"/>

        <div class="form-group">
            <label for="name">Name</label>
            <form:input path="name" id="name" cssClass="form-control"/>
            <form:errors path="name" cssClass="error"/>
        </div>

        <div class="form-group">
            <label for="email">Email</label>
            <form:input path="email" id="email" type="email" cssClass="form-control"/>
            <form:errors path="email" cssClass="error"/>
        </div>

        <div class="form-group">
            <label for="nationality">Nationality</label>
            <form:input path="nationality" id="nationality" cssClass="form-control"/>
            <form:errors path="nationality" cssClass="error"/>
        </div>

        <button type="submit" class="btn btn-primary">Update Author</button>
        <a href="<c:url value='/authors'/>" class="btn btn-secondary">Cancel</a>
    </form:form>
</div>
</body>
</html>
