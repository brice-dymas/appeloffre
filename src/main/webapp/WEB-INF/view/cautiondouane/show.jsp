<%--
    Document   : show
    Created on : Dec 10, 2014, 9:48:58 AM
    Author     : sando
--%>
<%--
    Document   : show
    Updated on : Aug 16, 2018, 6:55:58 PM
    Author     : sando
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<tiles:insertDefinition name="layout">
    <tiles:putAttribute name="body">
        <div class="row">
            <div class="col-md-12">
                <h4>
                    <spring:message code="cautiondouane.afficher" />
                </h4>
                <c:if test="${cautiondouane.deleted}" >
                    <div class="text-danger">
                        <spring:message code="element.desactive" />
                    </div>
                </c:if>
                <hr/>
            </div>
        </div>
        <div class="row">
            <div class="col-md-6 col-md-offset-3" id="table_show">
                <table class="table table-bordered">
                    <tbody>
                        <tr>
                            <th><spring:message code="cautiondouane.numero" /></th>
                            <td>${cautiondouane.numero}</td>
                        </tr>
                        <tr>
                            <th><spring:message code="cautiondouane.libelle" /></th>
                            <td>${cautiondouane.libelle}</td>
                        </tr>
                        <tr>
                            <th><spring:message code="cautiondouane.montant" /></th>
                            <td><fmt:formatNumber value="${cautiondouane.montant}" pattern="#,##0" /></td>
                        </tr>
                        <tr>
                            <th><spring:message code="cautiondouane.dateDebut" /></th>
                            <td><fmt:formatDate value="${cautiondouane.dateDebut}" pattern="dd/MM/yyyy" /></td>
                        </tr>
                        <tr>
                            <th><spring:message code="cautiondouane.dateFin" /></th>
                                <c:if test="${cautiondouane.dateFin le todayDate}">
                                <td  class="text-danger">
                                    <fmt:formatDate type="date" value="${cautiondouane.dateFin}" pattern="dd/MM/yyyy" />
                                </td>
                            </c:if>
                            <c:if test="${cautiondouane.dateFin gt todayDate}">
                                <td><fmt:formatDate type="date" value="${cautiondouane.dateFin}" pattern="dd/MM/yyyy" /> </td>
                            </c:if>
                        </tr>
                        <tr>
                            <th><spring:message code="cautiondouane.divers" /></th>
                            <td>${cautiondouane.divers}</td>
                        </tr>
                        <tr>
                            <th><spring:message code="cautiondouane.banque" /></th>
                            <td>${cautiondouane.banque.libelle}</td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>

        <div class="row">
            <div class="col-md-12 text-center">
                <hr/>
                <spring:url value="/cautiondouane/" var="cautiondouane_home"/>
                <a href="${cautiondouane_home}" class="btn btn-primary  btn-sm">
                    <span class="glyphicon glyphicon-th-list"></span>
                    <spring:message code="cautiondouane.liste" />
                </a>
            </div>
        </div>
    </tiles:putAttribute>
</tiles:insertDefinition>