<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Library Management - Home</title>
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
    <h2>Welcome</h2>
    <p>This is a Spring Boot + JPA + JSP application that manages <strong>Authors</strong> and the
       <strong>Books</strong> they have written. You can create, view, and update both entities.</p>

    <div class="home-cards">
        <div class="card">
            <h3>Authors</h3>
            <p>View, add and update author information including name, email and nationality.</p>
            <a href="<c:url value='/authors'/>">Manage Authors &rarr;</a>
        </div>
        <div class="card">
            <h3>Books</h3>
            <p>View, add and update books along with their authors. Listing uses an INNER JOIN query.</p>
            <a href="<c:url value='/books'/>">Manage Books &rarr;</a>
        </div>
        <div class="card">
            <h3>H2 Console</h3>
            <p>Inspect the in-memory database directly for verification and debugging.</p>
            <a href="<c:url value='/h2-console'/>" target="_blank">Open H2 Console &rarr;</a>
        </div>
    </div>
</div>
</body>
</html>
