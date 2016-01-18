<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<tiles:insertDefinition name="layout">
    <tiles:putAttribute name="body">
        <div class="row">
            <div class="col-md-10">
                <div class="row">
                    <div class="col-md-12">
                        <div>
                            <h3>
                                <spring:message code="caution.liste" />
                            </h3>
                            <hr/>
                        </div>

                    </div>
                </div>

                <div class="row">
                    <div class="col-md-12">
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

                                    <th>
                                        <span class="btn">
                                            <spring:message code="action.titre" />
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
                                                <td class="text-center">
                                                    <spring:url value="/appeloffre/${caution.appelOffre.id}/show" htmlEscape="true" var="appeloffre_show" />
                                                    <a href="${appeloffre_show}" class="btn btn-primary btn-sm">
                                                        <span class="glyphicon glyphicon-open">
                                                            <spring:message code="caution.appelOffre" />
                                                        </span>
                                                    </a>
                                                    &nbsp;&nbsp;
                                                    <spring:url value="/caution/${caution.id}/show" htmlEscape="true" var="caution_show" />
                                                    <a href="${caution_show}" class="btn btn-primary btn-sm">
                                                        <span class="glyphicon glyphicon-open">

                                                        </span>
                                                        <spring:message code="action.detail" />
                                                    </a>
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
                                                    ${caution.montant}
                                                </td>
                                                <td class="text-center">
                                                    <spring:url value="/appeloffre/${caution.appelOffre.id}/show" htmlEscape="true" var="appeloffre_show" />
                                                    <a href="${appeloffre_show}" class="btn btn-primary btn-sm">
                                                        <span class="glyphicon glyphicon-open">
                                                            <spring:message code="caution.appelOffre" />
                                                        </span>
                                                    </a>
                                                    &nbsp;&nbsp;
                                                    <spring:url value="/caution/${caution.id}/show" htmlEscape="true" var="caution_show" />
                                                    <a href="${caution_show}" class="btn btn-primary btn-sm">
                                                        <span class="glyphicon glyphicon-open">

                                                        </span>
                                                        <spring:message code="action.detail" />
                                                    </a>
                                                </td>
                                            </tr>
                                        </c:if>

                                    </c:forEach>
                                    <c:if test="${not empty cautions}">
                                    <div class="dropdown" style="display: inline-block !important">
                                        <button class="btn btn-default dropdown-toogle" id="dropdown-user" data-toggle="dropdown">
                                            <i class="glyphicon glyphicon-print"></i>
                                            <spring:message code="print.message" />
                                            <i class="caret"></i>
                                        </button>
                                        <ul class="dropdown-menu" role="menu" aria-labeledby="dropdown-user">

                                            <li>
                                                <spring:url htmlEscape="true" var="xls_print" value="/caution.xls" />
                                                <a href="${xls_print}">
                                                    <spring:message code="print.xls" />
                                                </a>
                                            </li>
                                        </ul>
                                    </div>
                                </c:if>
                                <div class="dropdown pull-right ">
                                    <button class="btn btn-default dropdown-toggle" type="button" id="dropdownMenu1" data-toggle="dropdown" aria-expanded="true">
                                        <spring:message code="search.taille" />
                                        : ${size}&nbsp;
                                        <span class="caret"></span>
                                    </button>
                                    <ul class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu1">
                                        <li role="presentation"><a role="menuitem" tabindex="-1" href="?&querybanque=${caution.banque.id}&size=5&querydebutperiode=${querydebutperiode}&queryfinperiode=${queryfinperiode}&querytypecaution=${caution.typeCaution.id}">5</a></li>
                                        <li role="presentation"><a role="menuitem" tabindex="-1" href="?&querybanque=${caution.banque.id}&size=10&querydebutperiode=${querydebutperiode}&queryfinperiode=${queryfinperiode}&querytypecaution=${caution.typeCaution.id}">10</a></li>
                                        <li role="presentation"><a role="menuitem" tabindex="-1" href="?&querybanque=${caution.banque.id}&size=20&querydebutperiode=${querydebutperiode}&queryfinperiode=${queryfinperiode}&querytypecaution=${caution.typeCaution.id}">20</a></li>
                                        <li role="presentation"><a role="menuitem" tabindex="-1" href="?&querybanque=${caution.banque.id}&size=30&querydebutperiode=${querydebutperiode}&queryfinperiode=${queryfinperiode}&querytypecaution=${caution.typeCaution.id}">30</a></li>
                                        <li role="presentation"><a role="menuitem" tabindex="-1" href="?&querybanque=${caution.banque.id}&size=40&querydebutperiode=${querydebutperiode}&queryfinperiode=${queryfinperiode}&querytypecaution=${caution.typeCaution.id}">40</a></li>
                                        <li role="presentation"><a role="menuitem" tabindex="-1" href="?&querybanque=${caution.banque.id}&size=50&querydebutperiode=${querydebutperiode}&queryfinperiode=${queryfinperiode}&querytypecaution=${caution.typeCaution.id}">50</a></li>
                                    </ul>
                                </div>
                            </c:if>
                            </tbody>
                        </table>

                    </div>
                </div>

                <div class="row">
                    <div class="col-md-12">
                        <hr/>
                        <spring:url value="/caution/stats" var="caution_stats"
                                    htmlEscape="true" />
                        <a class="btn btn-primary btn-sm" href="${caution_stats}">
                            <span class="glyphicon glyphicon-stats"></span> <spring:message code="action.stats"/>
                        </a>
                        <div class="pull-right">
                            <ul class="pager">
                                <li><a href="?&querybanque=${caution.banque.id}&size=${size}&querydebutperiode=${querydebutperiode}&queryfinperiode=${queryfinperiode}&querytypecaution=${caution.typeCaution.id}&page=0" <c:if test="${page eq 0}">class ="btn btn-sm disabled"</c:if>>
                                            <span class="glyphicon glyphicon-fast-backward"></span>
                                        </a></li>
                                    <li><a href="?&querybanque=${caution.banque.id}&size=${size}&querydebutperiode=${querydebutperiode}&queryfinperiode=${queryfinperiode}&querytypecaution=${caution.typeCaution.id}&page=${page-1}" <c:if test="${page eq 0}">class ="btn btn-sm disabled"</c:if>>
                                            <span class="glyphicon glyphicon-backward"></span>
                                        </a></li>
                                    <li><input type="text" class="pager_detail text-center" readonly
                                        <c:choose>
                                            <c:when test="${Totalpage eq 0}">value="${page+1}/${Totalpage+1}"</c:when>
                                            <c:otherwise>value="${page+1}/${Totalpage}"</c:otherwise>
                                        </c:choose>
                                        />
                                </li>
                                <li>
                                    <a href="?&querybanque=${caution.banque.id}&size=${size}&querydebutperiode=${querydebutperiode}&queryfinperiode=${queryfinperiode}&querytypecaution=${caution.typeCaution.id}&page=${page+1}"
                                       <c:choose>
                                           <c:when test="${page+1 eq Totalpage}">class ="btn btn-sm disabled"</c:when>
                                           <c:when test="${Totalpage eq 0}">class ="btn btn-sm disabled"</c:when>
                                       </c:choose>
                                       >
                                        <span class="glyphicon glyphicon-forward"></span>
                                    </a>
                                </li>
                                <li><a href="?querybanque=${caution.banque.id}&size=${size}&querydebutperiode=${querydebutperiode}&queryfinperiode=${queryfinperiode}&querytypecaution=${caution.typeCaution.id}&page=${Totalpage-1}"
                                       <c:choose>
                                           <c:when test="${page+1 eq Totalpage}">class ="btn btn-sm disabled"</c:when>
                                           <c:when test="${Totalpage eq 0}">class ="btn btn-sm disabled"</c:when>
                                       </c:choose>
                                       >
                                        <span class="glyphicon glyphicon-fast-forward"></span>
                                    </a></li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>

            <div class="col-md-2">
                <div>
                    <h3>
                        <spring:message code="action.rechercher" />
                    </h3>
                    <hr/>
                </div>
                <spring:url value="/caution/" var="caution_home"
                            htmlEscape="true" />

                <form:form method="get" commandName="caution" action="${caution_home}">
                    <div class="form-group">
                        <label>
                            <spring:message code="caution.banque" />
                        </label>
                        <select name="querybanque" class="form-control input-sm">
                            <option value="">---</option>
                            <c:forEach var="banque" items="${banques}">

                                <option value="${banque.key}"
                                        <c:if test="${banque.key eq caution.banque.id}">
                                            selected
                                        </c:if>
                                        >
                                    ${banque.value}
                                </option>
                            </c:forEach>
                        </select>
                        <input type="hidden" value="${size}" name="size"/>
                    </div>
                    <div class="form-group">
                        <label>
                            <spring:message code="caution.debutPeriodeEcheance" />
                        </label>
                        <input id="dateDebut" type="text" value="${querydebutperiode}" class="form-control input-sm" name="querydebutperiode"/>
                    </div>
                    <div class="form-group">
                        <label>
                            <spring:message code="caution.finPeriodeEcheance" />
                        </label>
                        <input id="dateFin" type="text" value="${queryfinperiode}" class="form-control input-sm" name="queryfinperiode"/>
                    </div>

                    <div class="form-group">
                        <label>
                            <spring:message code="caution.typeCaution" />
                        </label>

                        <select name="querytypecaution" class="form-control input-sm">
                            <option value="">---</option>
                            <c:forEach var="typeCaution" items="${typeCautions}">

                                <option value="${typeCaution.key}"
                                        <c:if test="${typeCaution.key eq caution.typeCaution.id}">
                                            selected
                                        </c:if>
                                        >
                                    ${typeCaution.value}
                                </option>
                            </c:forEach>
                        </select>
                    </div>
                    <hr/>
                    <button class="btn btn-default btn-sm">
                        <span class="glyphicon glyphicon-search"></span> <spring:message code="action.rechercher"/>
                    </button>
                    <spring:url value="/caution/" htmlEscape="true" var="caution_home" />
                    <a href="${caution_home}" class="btn btn-default btn-sm">
                        <span class="glyphicon glyphicon-refresh"></span>
                        <spring:message code="search.delete" />
                    </a>
                </form:form>
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
