<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib tagdir="/WEB-INF/tags" prefix="template"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<c:set var="base_url" value="<%=request.getContextPath()%>" />

<template:base>
    <jsp:attribute name="title">
        Registrierung
    </jsp:attribute>

    <jsp:attribute name="head">

    </jsp:attribute>

    <jsp:attribute name="menu">

    </jsp:attribute>

    <jsp:attribute name="main">
        <div class="container">
            <div class="card card-register mx-auto mt-5">
                <div class="card-header">Registrieren</div>
                <div class="card-body">
                    <form method="post" class="stacked">
                        <%-- Eingabefelder --%>
                        <div class="input-group form-group">
                            <div class="input-group-prepend">
                                <span class="input-group-text"><i class="fas fa-user"></i></span>
                            </div>
                            <input type="text" class="form-control" name="username" value="${signUp_form.values["username"][0]}" placeholder="Benutzername" required="required" autofocus="autofocus">
                        </div>
                        <div class="input-group form-group">
                            <div class="input-group-prepend">
                                <span class="input-group-text"><i class="fas fa-key"></i></span>
                            </div>
                            <input type="password" class="form-control mr-1" name="password" value="${signUp_form.values["password"][0]}" placeholder="Passwort" required="required">
                            <div class="input-group-prepend">
                                <span class="input-group-text"><i class="fas fa-key"></i></span>
                            </div>
                            <input type="password" class="form-control" name="passwordConfirm" value="${signUp_form.values["passwordConfirm"][0]}" placeholder="Passwort wiederholen" required="required">
                        </div>
                        <div class="input-group form-group">
                            <div class="input-group-prepend">
                                <span class="input-group-text"><i class="fas fa-at"></i></span>
                            </div>
                            <input type="email" class="form-control" name="email" value="${signUp_form.values["email"][0]}" placeholder="E-Mail" required="required">
                        </div>
                        <%-- Button zum Abschicken --%>
                        <input class="btn btn-primary btn-block" type="submit" value="Registrieren">
                        <small id="activationHelp" class="form-text text-muted">Nach der Registrierung erhälst du eine E-Mail mit welcher du deinen Account aktivieren kannst.</small>
                    </form>
                </div>

                <%-- Fehlermeldungen --%>
                <c:if test="${!empty signUp_form.errors}">
                    <ul class="errors">
                        <c:forEach items="${signUp_form.errors}" var="error">
                            <li>${error}</li>
                            </c:forEach>
                    </ul>
                </c:if>
            </div>
        </div>

    </jsp:attribute>
</template:base>
