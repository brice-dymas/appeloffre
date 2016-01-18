<%--
    Document   : show
    Created on : 29 janv. 2015, 22:07:40
    Author     : fabrice
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
                    ${filiale.nom} - <spring:message code="filiale.afficher" />
                </h4>
                <c:if test="${filiale.deleted}" >
                    <div class="text-danger">
                        <spring:message code="element.desactive" />
                    </div>
                </c:if>
                <hr/>
            </div>
        </div>
        <div class="row">
            <div class="col-md-4 col-md-offset-4" id="table_show">
                <table class="table table-condensed">
                    <tbody>
                        <tr>
                            <th><spring:message code="filiale.code" /></th>
                            <td>${filiale.code}</td>
                        </tr>
                        <tr>
                            <th><spring:message code="filiale.nom" /></th>
                            <td>${filiale.nom}</td>
                        </tr>
                        <tr>
                            <th><spring:message code="filiale.agence" /></th>
                            <td>${filiale.agence}</td>
                        </tr>
                    </tbody>
                </table>

            </div>
        </div>

        <div class="row">
            <div class="col-md-4 col-md-offset-4">

                <hr/>



                <spring:url value="/filiale/delete" var="filiale_delete"/>
                <form:form method="post" commandName="filiale" action="${filiale_delete}">
                    <spring:url value="/filiale/" var="filiale_home"/>
                    <a href="${filiale_home}" class="btn btn-primary  btn-sm">
                        <span class="glyphicon glyphicon-list"></span>
                        <spring:message code="filiale.liste" />
                    </a>
                    <sec:authorize access="hasRole('ROLE_ADMIN')" >
                        <form:hidden path="id"/>
                        <spring:url value="/filiale/${filiale.id}/edit" var="filiale_edit"/>
                        <a href="${filiale_edit}" class="btn btn-default  btn-warning">
                            <span class="glyphicon glyphicon-edit"></span>
                            <spring:message code="action.modifier" />
                        </a>
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        <c:if test="${not filiale.deleted}" >
                            <button type="submit" class="btn btn-default  btn-danger">
                                <span class="glyphicon glyphicon-trash"></span>
                                <spring:message code="action.effacer" />
                            </button>
                        </c:if>
                        <c:if test="${filiale.deleted}" >
                            <button type="submit" class="btn btn-default  btn-success">
                                <span class="glyphicon glyphicon-trash"></span>
                                <spring:message code="action.activer" />
                            </button>
                        </c:if>

                    </sec:authorize>
                </form:form>


            </div>
        </div>

    </tiles:putAttribute>
</tiles:insertDefinition>