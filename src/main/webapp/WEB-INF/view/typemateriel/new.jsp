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
                <h4>
                    <spring:message code="typeMateriel.nouveau" />
                </h4>
                <hr/>
            </div>
        </div>

        <div class="row">
            <div class="col-md-6 col-md-offset-2">
                <spring:url value="/typemateriel/create" var="typemateriel_create"
                            htmlEscape="true" />
                <form:form method="post" commandName="typeMateriel" action="${typemateriel_create}">
                    <div class="panel panel-default">
                        <div class="panel-body">

                            <div class="form-group">
                                <form:label for="code" path="">
                                    <spring:message code="typeMateriel.code" /> :
                                </form:label>
                                <form:input id="code" path="code" cssClass="form-control"/>
                                <form:errors path="code" cssClass="text-danger"/>
                            </div>


                            <div class="form-group">
                                <form:label for="nom" path="">
                                    <spring:message code="typeMateriel.nom" /> :
                                </form:label>
                                <form:input id="nom" path="nom" cssClass="form-control"/>
                                <form:errors path="nom" cssClass="text-danger"/>
                            </div>
                        </div>
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        <div class="panel-footer">

                            <button type="submit" class="btn btn-success btn-sm">
                                <span class="glyphicon glyphicon-save"></span>
                                <spring:message code="action.enregistrer" />
                            </button>
                            <spring:url value="/typemateriel/" htmlEscape="true"
                                        var="typemateriel_home" />
                            <a href="${typemateriel_home}" class="btn btn-danger btn-sm">
                                <span class="glyphicon glyphicon-list"></span>
                                <spring:message code="typeMateriel.liste" />
                            </a>
                        </div>
                    </div>
                </form:form>
            </div>
        </div>

    </tiles:putAttribute>
</tiles:insertDefinition>