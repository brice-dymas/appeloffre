<%--
    Document   : edit
    Created on : Dec 10, 2014, 9:44:15 AM
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
                    <spring:message code="typeCaution.modifier" />
                    : ${typeCaution.nom}
                </h4>
                <hr/>
            </div>
        </div>

        <div class="row">
            <div class="col-md-6 col-md-offset-3">
                <spring:url value="/typecaution/update" var="typecaution_update" htmlEscape="true"/>
                <form:form method="post" commandName="typeCaution" action="${typecaution_update}">
                    <form:errors path="*" />
                    <div class="panel panel-default">
                        <div class="panel-body">
                            <div class="form-group">
                                <form:label for="code" path="">
                                    <spring:message code="typeCaution.code" />
                                </form:label>
                                <form:input id="code" path="code" cssClass="form-control"/>
                                <form:errors path="code" cssClass="label label-danger"/>
                            </div>
                            <div class="form-group">
                                <form:label for="nom" path="">
                                    <spring:message code="typeCaution.nom" />
                                </form:label>
                                <form:input id="nom" path="nom" cssClass="form-control"/>
                                <form:errors path="nom" cssClass="label label-danger"/>
                            </div>
                            <div class="form-group">
                                <form:label for="pourcentage" path="">
                                    <spring:message code="typeCaution.pourcentage" />
                                </form:label>
                                <form:input id="pourcentage" path="pourcentage" cssClass="form-control"/>
                                <form:errors path="pourcentage" cssClass="label label-danger"/>
                            </div>
                        </div>

                        <form:hidden path="id"/>
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        <div class="panel-footer">

                            <button type="submit" class="btn btn-primary btn-sm">
                                <span class="glyphicon glyphicon-save"></span>
                                <spring:message code="action.enregistrer" />
                            </button>
                            <spring:url value="/typecaution/" htmlEscape="true"
                                        var="typecaution_home" />
                            <a href="${typecaution_home}" class="btn btn-default btn-sm">
                                <span class="glyphicon glyphicon-list"></span>
                                <spring:message code="typeCaution.liste" />
                            </a>
                        </div>
                    </div>
                </form:form>
            </div>
        </div>
    </tiles:putAttribute>
</tiles:insertDefinition>