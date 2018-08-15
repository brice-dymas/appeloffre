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
                    <spring:message code="banque.afficher" /> : ${banque.libelle}
                </h4>
                <c:if test="${banque.deleted}" >
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
                                <spring:message code="banque.code" />
                            </th>
                            <td>${banque.code}</td>
                        </tr>
                        <tr>
                            <th>
                                <spring:message code="banque.libelle" />
                            </th>
                            <td>${banque.libelle}</td>
                        </tr>
                    </tbody>
                </table>

            </div>
        </div>


        <div class="row">
            <div class="col-md-4 col-md-offset-4">

                <hr/>




                <spring:url value="/banque/delete" var="banque_delete"/>
                <form:form method="post" commandName="banque" action="${banque_delete}">
                    <spring:url value="/banque/" var="banque_home"/>
                    <a href="${banque_home}" class="btn btn-primary  btn-sm">
                        <span class="glyphicon glyphicon-list"></span>
                        <spring:message code="banque.liste" />
                    </a>
                    <sec:authorize access="hasRole('ROLE_ADMIN')" >
                        <form:hidden path="id"/>
                        <spring:url value="/banque/${banque.id}/edit" var="banque_edit"/>
                        <a href="${banque_edit}" class="btn btn-default  btn-warning">
                            <span class="glyphicon glyphicon-edit"></span>
                            <spring:message code="action.modifier" />
                        </a>
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        <c:if test="${banque.deleted}" >
                            <button type="submit" class="btn btn-default  btn-success">
                                <span class="glyphicon glyphicon-thumbs-up"></span>
                                <spring:message code="action.activer" />
                            </button>
                        </c:if>
                        <c:if test="${not banque.deleted}" >
                            <button type="submit" class="btn btn-default  btn-danger">
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