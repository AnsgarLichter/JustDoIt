<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib tagdir="/WEB-INF/tags" prefix="template"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<c:set var="base_url" value="<%=request.getContextPath()%>" />

<template:base>
    <jsp:attribute name="title">
        Login
    </jsp:attribute>

    <jsp:attribute name="head">
        <link rel="stylesheet" href="<c:url value="/css/login.css"/>" />
    </jsp:attribute>

    <jsp:attribute name="menu">
        <li class="nav-item">
            <a class="nav-link" href="<c:url value="/"/>">Home</a>
        </li>
        <li class="nav-item dropdown">
            <a class="nav-link dropdown-toggle" href="#" id="navbarDropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                Benutzerdaten ändern
            </a>
            <div class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
                <a class="dropdown-item" href="<c:url value="/changemail/"/>">E-Mail ändern</a>
                <a class="dropdown-item" href="<c:url value="/changepw/"/>">Passwort ändern</a>
            </div>
        </li>
    </jsp:attribute>

    <jsp:attribute name="main">
        <div class="container">
            <div class="card card-login mx-auto mt-5">
                <div class="card-header">Login</div>
                <div class="card-body">
                    <form action="j_security_check" method="post" class="stacked">
                        <%-- Eingabefelder --%>
                        <div class="form-group">
                            <div class="form-label-group">
                                <input type="text" class="form-control" name="j_username" placeholder="Benutzername" required="required" autofocus="autofocus">
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="form-label-group">
                                <input type="password" class="form-control" name="j_password" placeholder="Passwort" required="required">
                            </div>
                        </div>
                        <%-- Button zum Abschicken --%>
                        <input class="btn btn-primary btn-block" type="submit" value="Einloggen">
                    </form>
                    <div class="text-center">
                        <a class="d-block small mt-3" href="<c:url value="/signup/"/>">Registrieren</a>
                        <a class="d-block small" href="<c:url value="/resetpw/"/>">Passwort vergessen?</a>
                    </div>
                </div>
            </div>
        </div>
    </jsp:attribute>
</template:base>