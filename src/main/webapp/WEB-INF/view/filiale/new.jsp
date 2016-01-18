<%--
    Document   : new
    Created on : 29 janv. 2015, 22:00:59
    Author     : fabrice
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
                    <spring:message code="filiale.nouveau" />
                </h4>
                <hr/>
            </div>
        </div>

        <div class="row">
            <div class="col-md-6 col-md-offset-3">
                <spring:url value="/filiale/create" var="filiale_create"
                            htmlEscape="true" />
                <form:form method="post" commandName="filiale" action="${filiale_create}">
                    <div class="panel panel-default">
                        <div class="panel-body">

                            <div class="form-group">
                                <form:label for="code" path="">
                                    <spring:message code="filiale.code" />
                                </form:label>
                                <form:input id="code" path="code" cssClass="form-control"/>
                                <form:errors path="code" cssClass="text-danger"/>
                            </div>

                            <div class="form-group">
                                <form:label for="nom" path="">
                                    <spring:message code="filiale.nom" />
                                </form:label>
                                <form:input id="nom" path="nom" cssClass="form-control"/>
                                <form:errors path="nom" cssClass="text-danger"/>
                            </div>

                            <div class="form-group">
                                <form:label for="agence" path="">
                                    <spring:message code="filiale.agence" />
                                </form:label>
                                <form:input id="agence" path="agence" cssClass="form-control"/>
                                <form:errors path="agence" cssClass="text-danger"/>
                            </div>

                        </div>
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        <div class="panel-footer">

                            <button type="submit" class="btn btn-primary btn-sm">
                                <span class="glyphicon glyphicon-save"></span>
                                <spring:message code="action.enregistrer" />
                            </button>
                            <spring:url value="/filiale/" htmlEscape="true"
                                        var="filiale_home" />
                            <a href="${filiale_home}" class="btn btn-default btn-sm">
                                <span class="glyphicon glyphicon-list"></span>
                                <spring:message code="filiale.liste" />
                            </a>
                        </div>

                    </div>
                </form:form>
            </div>
        </div>
    </tiles:putAttribute>
</tiles:insertDefinition>