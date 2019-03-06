<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib tagdir="/WEB-INF/tags" prefix="template"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>



<template:base>
    <jsp:attribute name="title">
        Detailansicht des ToDo
    </jsp:attribute>

    <jsp:attribute name="head">
        <link rel="stylesheet" href="<c:url value="/css/login.css"/>" />
    </jsp:attribute>

    <jsp:attribute name="menu">

    </jsp:attribute>

    <jsp:attribute name="main">
        <div class="container">
            <div class="card card-register mx-auto mt-5">
                <div class="card-header">
                    <div class="row">
                        <div class="col-md-9 float-left"> <output type="titel" name="titel">${todo.name}</div>
                        <form method="post" class="stacked">
                            <div class="side-by-side float-right">
                                <button type="submit" class="btn btn-labeled btn-dark" name="action" value="edit">
                                    <span class="btn-label"><i class="fas fa-edit"></i></span>  Edit
                                </button>
                                <button type="submit" class="btn btn-labeled btn-danger" name="action" value="delete">
                                    <span class="btn-label"><i class="fas fa-trash-alt"></i></span>  Delete
                                </button>
                            </div>
                        </form>
                    </div>
                </div>
                <%-- Inhalt ToDo --%>
                <div class="card-body">
                    <%-- Ausgabefelder --%>
                    <div  class="form-group">
                        <div class="form-label-group">
                            <output type="titel" name="titel"> Bearbeiter: </output>
                                <c:forEach items="${users}" var="user">
                                <output type="titel" name="titel"> ${user.username},</output>
                                </c:forEach>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="form-label-group">
                            <output type="titel" name="titel">Fälligkeitsdatum: ${todo.dueDate}</output>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="form-label-group">
                            <output type="titel" name="titel">Beschreibung: ${todo.description}</output>
                        </div>
                    </div>
                </div>
                <%-- Fehlermeldungen --%>
                <c:if test="${!empty change_form.errors}">
                    <ul class="errors">
                        <c:forEach items="${change_form.errors}" var="error">
                            <li>${error}</li>
                            </c:forEach>
                    </ul>
                </c:if>
            </div>
        </div>

        <%-- Kommentare --%>
        <div class="container chat-container mt-7">
            <div class="chat">
                <p>Fremder Kommentar</p>
                <span class="time"><i class="far fa-clock mr-1"></i>11:00</span>
            </div>

            <div class="chat mine">
                <p>Mein Kommentar</p>
                <span class="time"><i class="far fa-clock mr-1"></i>11:00</span>
            </div>
        </div>





    </jsp:attribute>
</template:base>