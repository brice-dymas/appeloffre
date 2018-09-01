<%--
    Document   : show
    Created on : Dec 10, 2014, 9:48:58 AM
    Author     : sando
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<tiles:insertDefinition name="layout">
    <tiles:putAttribute name="body">
        <div class="row">
            <div class="col-md-12">
                <h4>
                    <spring:message code="typeMateriel.afficher" /> : <b> ${typeMateriel.nom} </b>
                </h4>
                <c:if test="${typeMateriel.deleted}" >
                    <div class="text-danger">
                        <spring:message code="element.desactive" />
                    </div>
                </c:if>
                <hr/>
            </div>
        </div>

        <div class="row">
            <div class="col-md-6 col-md-offset-2" id="table_show">
                <table class="table table-condensed">
                    <tbody>
                        <tr>
                            <th>
                                <spring:message code="typeMateriel.code" />
                            </th>
                            <td>${typeMateriel.code}</td>
                        </tr>
                        <tr>
                            <th>
                                <spring:message code="typeMateriel.nom" />
                            </th>
                            <td>${typeMateriel.nom}</td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>

        <div class="row">
            <div class="col-md-6 col-md-offset-2">
                <hr/>
                <spring:url value="/typemateriel/delete" var="typemateriel_delete"/>
                <form:form method="post" commandName="typeMateriel" action="${typemateriel_delete}">
                    <form:hidden path="id"/>
                    <spring:url value="/typemateriel/" var="typemateriel_home"/>
                    <a href="${typemateriel_home}" class="btn btn-primary">
                        <span class="glyphicon glyphicon-list"></span>
                        <spring:message code="typeMateriel.liste" />
                    </a>
                    <sec:authorize access="hasRole('ROLE_ADMIN')" >
                        <spring:url value="/typemateriel/${typeMateriel.id}/edit" var="typemateriel_edit"/>
                        <a href="${typemateriel_edit}" class="btn btn-warning">
                            <span class="glyphicon glyphicon-edit"></span>
                            <spring:message code="action.modifier" />
                        </a>
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        <c:if test="${not typeMateriel.deleted}" >
                            <button type="submit" class="btn btn-danger">
                                <span class="glyphicon glyphicon-trash"></span>
                                <spring:message code="action.effacer" />
                            </button>
                        </c:if>
                        <c:if test="${typeMateriel.deleted}" >
                            <button type="submit" class="btn btn-success">
                                <span class="glyphicon glyphicon-ok"></span>
                                <spring:message code="action.activer" />
                            </button>
                        </c:if>
                    </sec:authorize>
                </form:form>
            </div>
        </div>


    </tiles:putAttribute>
</tiles:insertDefinition>