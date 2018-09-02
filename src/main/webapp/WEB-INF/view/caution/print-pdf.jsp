<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<tiles:insertDefinition name="print">
    <tiles:putAttribute name="body">
        <div class="row">
            <div class="col-md-12 col-p-12">
                <div class="row">
                    <div class="col-md-12 col-p-12">
                        <div>
                            <h3>
                                <spring:message code="caution.liste" />
                            </h3>
                            <hr/>
                        </div>

                    </div>
                </div>

                <div class="row">
                    <div class="col-md-12 col-p-12">
                        <table class="table table-condensed table-hover table-bordered">
                            <thead class="text-center btn-primary" >
                                <tr>
                                    <th>
                                        <span class="btn">
                                            <spring:message code="caution.typeCaution" />
                                        </span>
                                    </th>
                                    <th>
                                        <span class="btn">
                                            <spring:message code="caution.dateDebut" />
                                        </span>
                                    </th>
                                    <th>
                                        <span class="btn">
                                            <spring:message code="caution.dateFin" />
                                        </span>
                                    </th>
                                    <th>
                                        <span class="btn">
                                            <spring:message code="caution.banque" />
                                        </span>
                                    </th>
                                    <th>
                                        <span class="btn">
                                            <spring:message code="caution.commercial" />
                                        </span>
                                    </th>
                                    <th>
                                        <span class="btn">
                                            <spring:message code="caution.montant" />
                                        </span>
                                    </th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:if test="${empty cautions}">
                                    <tr>
                                        <td class="text text-center label-danger" colspan="7">
                                            <spring:message  code="empty.data" />
                                        </td>
                                    </tr>

                                </c:if>
                                <c:if test="${not empty cautions}">
                                    <c:forEach items="${cautions}" var="caution">
                                        <c:if test="${caution.dateFin le todayDate}">
                                            <tr class="text-danger">

                                                <td>${caution.typeCaution.nom}</td>
                                                <td>
                                                    <fmt:formatDate type="date" value="${caution.dateDebut}" pattern="dd/MM/yyyy" />
                                                </td>
                                                <td><fmt:formatDate type="date" value="${caution.dateFin}" pattern="dd/MM/yyyy" /></td>
                                                <td>
                                                    ${caution.banque.libelle}
                                                </td>
                                                <td>
                                                    ${caution.commercial.user.nom}
                                                </td>
                                                <td>
                                                    <fmt:formatNumber value="${caution.montant}" pattern="#,##0" />
                                                </td>
                                            </tr>
                                        </c:if>
                                        <c:if test="${caution.dateFin gt todayDate}">
                                            <tr>
                                                <td>${caution.typeCaution.nom}</td>
                                                <td>
                                                    <fmt:formatDate type="date" value="${caution.dateDebut}" pattern="dd/MM/yyyy" />
                                                </td>
                                                <td>${caution.getTrueDate(caution.dateFin)} </td>
                                                <td>
                                                    ${caution.banque.libelle}
                                                </td>
                                                <td>
                                                    ${caution.commercial.user.nom}
                                                </td>
                                                <td>
                                                    <fmt:formatNumber value="${caution.montant}" pattern="#,##0" />
                                                </td>
                                            </tr>
                                        </c:if>

                                    </c:forEach>
                            </c:if>
                            </tbody>
                        </table>

                    </div>
                </div>

                
            </div>
        </div>

        <script src="<c:url value="/resources/js/jquery-ui.js" />"></script>
        <script type="text/javascript">
            $(function () {
                $("#dateDebut, #dateFin").datepicker({
                    changeMonth: true,
                    changeYear: true,
                    dateFormat: "dd/mm/yy",
                    showButtonPanel: false
                }).datepicker("option", "showAnim", "clip");
            });
        </script>
    </tiles:putAttribute>
</tiles:insertDefinition>
