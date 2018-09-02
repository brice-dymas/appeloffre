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
                    <spring:message code="legende.afficher" /> : ${legende.libelle}
                </h4>
                <c:if test="${legende.deleted}" >
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
                            <th>
                                <spring:message code="legende.libelle" />
                            </th>
                            <td>${legende.libelle}</td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>

        <div class="row">
            <div class="col-md-4 col-md-offset-4">
                <hr/>
                <spring:url value="/legende/delete" var="legende_delete"/>
                <form:form method="post" commandName="legende" action="${legende_delete}">
                    <spring:url value="/legende/" var="legende_home"/>
                    <a href="${legende_home}" class="btn btn-primary">
                        <span class="glyphicon glyphicon-list"></span>
                        <spring:message code="legende.liste" />
                    </a>
                    <sec:authorize access="hasRole('ROLE_ADMIN')" >
                        <form:hidden path="id"/>
                        <spring:url value="/legende/${legende.id}/edit" var="legende_edit"/>
                        <a href="${legende_edit}" class="btn btn-warning">
                            <span class="glyphicon glyphicon-edit"></span>
                            <spring:message code="action.modifier" />
                        </a>
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        <c:if test="${legende.deleted}" >
                            <button type="submit" class="btn btn-success">
                                <span class="glyphicon glyphicon-ok"></span>
                                <spring:message code="action.activer" />
                            </button>
                        </c:if>
                        <c:if test="${not legende.deleted}" >
                            <button type="submit" class="btn btn-danger">
                                <span class="glyphicon glyphicon-remove-sign"></span>
                                <spring:message code="action.effacer" />
                            </button>
                        </c:if>
                    </sec:authorize>
                </form:form>
            </div>
        </div>
    </tiles:putAttribute>
</tiles:insertDefinition>