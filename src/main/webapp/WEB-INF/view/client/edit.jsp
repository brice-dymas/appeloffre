<%--
    Document   : new
    Created on : Dec 10, 2014, 9:20:13 AM
    Author     : sando
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<tiles:insertDefinition name="layout">
    <tiles:putAttribute name="body">
        <div class="row">
            <div class="col-md-12">
                <h1>Formulaire de Modification d'un Client</h1>
            </div>
        </div>
        <spring:url value="/client/update" var="client_save"
                    htmlEscape="true" />
        <form:form method="post" commandName="client" action="${client_save}">

            <div class="row">

                <div class="col-md-4">
                    <div class="form-group">
                        <form:label for="nom" path="">Nom(s) et Prénom(s):</form:label>
                        <form:input id="nom" path="nom" cssClass="form-control" />
                        <form:errors path="nom" cssClass="text-danger" />
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="form-group">
                        <form:label for="matricule" path="">Code Client :</form:label>
                        <form:input id="matricule" path="matricule"
                                    cssClass="form-control" />
                        <form:errors path="matricule" cssClass="text-danger" />
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="form-group">
                        <form:label for="adresse" path="">Adresse :</form:label>
                        <form:input id="adresse" path="adresse"
                                    cssClass="form-control" />
                        <form:errors path="adresse"  cssClass="text-danger" />
                    </div>
                </div>

            </div>
            <div class="row">

                <div class="col-md-4">
                    <div class="form-group">
                        <form:label for="telephone" path="">Téléphone :</form:label>
                        <form:input id="telephone" path="telephone" cssClass="form-control" />
                        <form:errors path="telephone" cssClass="text-danger" />
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="form-group">
                        <form:label for="email" path="">Adresse Mail :</form:label>
                        <form:input id="email" path="email"
                                    cssClass="form-control" />
                        <form:errors path="email"  cssClass="text-danger"/>
                    </div>
                </div>
             </div>
           
            <br/>
            <div class="row">
                <div class="col-md-4">
                    <input type="submit" value="Enregistrer" />
                    <spring:url value="/client/" htmlEscape="true"
                                var="client_home" />
                    <a href="${client_home}" class="btn">Acceuil</a>
                </div>
            </div>
        </form:form>
    </tiles:putAttribute>
</tiles:insertDefinition>