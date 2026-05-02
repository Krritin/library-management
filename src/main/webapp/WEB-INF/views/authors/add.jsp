<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
    <title>Add Author</title>
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
    <h2>Add New Author</h2>

    <form:form method="post" action="${pageContext.request.contextPath}/authors/save"
               modelAttribute="author">
        <div class="form-group">
            <label for="name">Name</label>
            <form:input path="name" id="name" cssClass="form-control" placeholder="Author full name"/>
            <form:errors path="name" cssClass="error"/>
        </div>

        <div class="form-group">
            <label for="email">Email</label>
            <form:input path="email" id="email" type="email" cssClass="form-control" placeholder="author@example.com"/>
            <form:errors path="email" cssClass="error"/>
        </div>

        <div class="form-group">
            <label for="nationality">Nationality</label>
            <form:input path="nationality" id="nationality" cssClass="form-control" placeholder="e.g., Indian"/>
            <form:errors path="nationality" cssClass="error"/>
        </div>

        <button type="submit" class="btn btn-primary">Save Author</button>
        <a href="<c:url value='/authors'/>" class="btn btn-secondary">Cancel</a>
    </form:form>
</div>
</body>
</html>
