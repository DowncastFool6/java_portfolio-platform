<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Registar Utilizador</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

<div class="">
<div class="">
    <h2>Registar Novo Utilizador</h2>

    <c:if test="${not empty erro}">
        <p class="erro"><c:out value="${erro}"/></p>
    </c:if>

    <form action="${pageContext.request.contextPath}/register" method="post">
        <label>Nome:</label><br>
        <input type="text" name="nome" value="${param.nome}" required>
        <br><br>

        <label>Email:</label><br>
        <input type="email" name="email" value="${param.email}" required>
        <br><br>

        <label>Senha:</label><br>
        <input type="password" name="senha" required>
        <br><br>

        <button type="submit" class="btn-primary">Registar</button>
    </form>
</div>
</div>

</body>
</html>
