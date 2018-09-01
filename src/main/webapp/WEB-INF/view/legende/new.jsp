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
                    <spring:message code="legende.nouveau" />
                </h4>
                <hr/>
            </div>
        </div>

        <div class="row">
            <div class="col-md-6 col-md-offset-3">
                <spring:url value="/legende/create" var="legende_create"
                            htmlEscape="true" />
                <form:form method="post" commandName="legende" action="${legende_create}">
                    <div class="panel panel-default">
                        <div class="panel-body">
                            <div class="form-group">
                                <form:label for="libelle" path="">
                                    <spring:message code="legende.libelle" /> :
                                </form:label>
                                <form:input id="libelle" path="libelle" cssClass="form-control"/>
                                <form:errors path="libelle" cssClass="text-danger"/>
                            </div>
                        </div>
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        <div class="panel-footer">

                            <button type="submit" class="btn btn-success btn-sm">
                                <span class="glyphicon glyphicon-save"></span>
                                <spring:message code="action.enregistrer" />
                            </button>
                            <spring:url value="/legende/" htmlEscape="true"
                                        var="legende_home" />
                            <a href="${legende_home}" class="btn btn-danger btn-sm">
                                <span class="glyphicon glyphicon-list"></span>
                                <spring:message code="legende.liste" />
                            </a>
                        </div>
                    </div>
                </form:form>
            </div>
        </div>

    </tiles:putAttribute>
</tiles:insertDefinition>