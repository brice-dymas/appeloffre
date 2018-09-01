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
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<tiles:insertDefinition name="layout">
    <tiles:putAttribute name="body">
        <div class="row">
            <div class="col-md-12">
                <h4>
                    <spring:message code="caution.afficher" />
                </h4>
                <c:if test="${caution.deleted}" >
                    <div class="text-danger">
                        <spring:message code="element.desactive" />
                    </div>
                </c:if>
                <hr/>
            </div>
        </div>
        <div class="row">
            <div class="col-md-6" id="table_show">
                <table class="table table-bordered">
                    <tbody>
                        <tr>
                            <th><spring:message code="caution.numero" /></th>
                            <td>${caution.numero}</td>
                        </tr>
                        <tr>
                            <th><spring:message code="caution.dateDebut" /></th>
                            <td><fmt:formatDate value="${caution.dateDebut}" pattern="dd/MM/yyyy" /></td>
                        </tr>
                        <tr>
                            <th><spring:message code="caution.dateFin" /></th>
                                <c:if test="${caution.dateFin le todayDate}">
                                <td  class="text-danger">
                                    <fmt:formatDate type="date" value="${caution.dateFin}" pattern="dd/MM/yyyy" />
                                </td>
                            </c:if>
                            <c:if test="${caution.dateFin  gt todayDate}">
                                <td><fmt:formatDate type="date" value="${caution.dateFin}" pattern="dd/MM/yyyy" /> </td>
                            </c:if>
                        </tr>
                        <tr>
                            <th><spring:message code="caution.montantMarche" /></th>
                            <td>
                                <fmt:formatNumber value="${caution.montantMarche}" pattern="#,##0" /> &nbsp; F CFA
                            </td>
                        </tr>
                        <tr>
                            <th><spring:message code="caution.referenceMarche" /></th>
                            <td>${caution.referenceMarche}</td>
                        </tr>
                        <tr>
                            <th><spring:message code="caution.banque" /></th>
                            <td>${caution.banque.libelle}</td>
                        </tr>
                        <%--</c:if>--%>
                    </tbody>
                </table>
            </div>
            <div class="col-md-6" id="table_show">
                <table class="table table-bordered">
                    <tbody>
                        <tr>
                            <th><spring:message code="caution.montant" /></th>
                            <td><fmt:formatNumber value="${caution.montant}" pattern="#,##0" />  &nbsp; F CFA</td>
                        </tr>
                        <tr>
                            <th><spring:message code="caution.commercial" /></th>
                            <td>${caution.commercial.user.nom}</td>
                        </tr>
                        <tr>
                            <th><spring:message code="caution.typeCaution" /></th>
                            <td>${caution.typeCaution.nom}</td>
                        </tr>
                        <%--<c:if test="${caution.commissionTrimestrielle}">--%>
                        <tr>
                            <th><spring:message code="caution.commissionTrimestrielle" /></th>
                            <td>
                                <fmt:formatNumber value="${caution.commissionTrimestrielle}" pattern="#,##0" /> &nbsp; F CFA
                            </td>
                        </tr>
                        <%--</c:if>--%>
                        <%--<c:if test="${!empty caution.statut}">--%>
                        <%--</c:if>--%>
                        <%--<c:if test="${caution.legende}">--%>
                        <tr>
                            <th><spring:message code="caution.legende" /></th>
                            <td>${caution.legende.libelle}</td>
                        </tr>
                        <tr>
                            <th><spring:message code="caution.dateModification" /></th>
                            <td><fmt:formatDate value="${caution.dateModification}" pattern="dd/MM/yyyy" /></td>
                        </tr>
                    </tbody>
                </table>
            </div>
            <div class="col-md-12" id="table_show">
                <table class="table table-bordered">
                    <tbody>
                        <tr>
                            <th class="col-md-3"><spring:message code="caution.statut" /></th>
                            <td class="col-md-9">${caution.statut}</td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>

        <div class="row">
            <div class="col-md-12 text-center">
                <hr/>
                <spring:url value="/caution/" var="caution_home"/>
                <a href="${caution_home}" class="btn btn-primary  btn-sm">
                    <span class="glyphicon glyphicon-th-list"></span>
                    <spring:message code="caution.liste" />
                </a>
                <spring:url value="/appeloffre/${caution.appelOffre.id}/show" var="caution_appel"/>
                <a href="${caution_appel}" class="btn btn-success  btn-sm">
                    <span class="glyphicon glyphicon-briefcase"></span>
                    <spring:message code="caution.appelOffre" />
                </a>
                <spring:url value="/caution/${caution.id}/edit" htmlEscape="true" var="caution_edit" />
                <a href="${caution_edit}" class="btn btn-warning btn-sm">
                    <span class="glyphicon glyphicon-edit">

                    </span>
                    <spring:message code="action.modifier" />
                </a>
            </div>
        </div>
    </tiles:putAttribute>
</tiles:insertDefinition>