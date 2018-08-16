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
                                <spring:message code="cautiondouane.liste" />
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
                                            <spring:message code="cautiondouane.numero" />
                                        </span>
                                    </th>
                                    <th>
                                        <span class="btn">
                                            <spring:message code="cautiondouane.libelle" />
                                        </span>
                                    </th>
                                    <th>
                                        <span class="btn">
                                            <spring:message code="cautiondouane.dateDebut" />
                                        </span>
                                    </th>
                                    <th>
                                        <span class="btn">
                                            <spring:message code="cautiondouane.dateFin" />
                                        </span>
                                    </th>
                                    <th>
                                        <span class="btn">
                                            <spring:message code="cautiondouane.montant" />
                                        </span>
                                    </th>
                                    <th>
                                        <span class="btn">
                                            <spring:message code="cautiondouane.banque" />
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
                                <c:if test="${empty cautiondouanes}">
                                    <tr>
                                        <td class="text text-center label-danger" colspan="7">
                                            <spring:message  code="empty.data" />
                                        </td>
                                    </tr>
                                </c:if>
                                <c:if test="${not empty cautiondouanes}">
                                    <c:forEach items="${cautiondouanes}" var="cautiondouane">
                                        <c:if test="${cautiondouane.dateFin le todayDate}">
                                            <tr class="text-danger">
                                                <td>${cautiondouane.numero}</td>
                                                <td>${cautiondouane.libelle}</td>
                                                <td>
                                                    <fmt:formatDate type="date" value="${cautiondouane.dateDebut}" pattern="dd/MM/yyyy" />
                                                </td>
                                                <td><fmt:formatDate type="date" value="${cautiondouane.dateFin}" pattern="dd/MM/yyyy" /></td>
                                                <td>
                                                    <fmt:formatNumber value="${cautiondouane.montant}" pattern="#,##0" />
                                                </td>
                                                <td>
                                                    ${cautiondouane.banque.libelle}
                                                </td>
                                                <td class="text-center">
                                                    <spring:url value="/cautiondouane/${cautiondouane.id}/show" htmlEscape="true" var="cautiondouane_show" />
                                                    <a href="${cautiondouane_show}" class="btn btn-primary btn-sm">
                                                        <span class="glyphicon glyphicon-open">
                                                        </span>
                                                        <spring:message code="action.detail" />
                                                    </a>
                                                </td>
                                            </tr>
                                        </c:if>
                                        <c:if test="${cautiondouane.dateFin gt todayDate}">
                                            <tr>
                                                <td>${cautiondouane.numero}</td>
                                                <td>${cautiondouane.libelle}</td>
                                                <td>
                                                    <fmt:formatDate type="date" value="${cautiondouane.dateDebut}" pattern="dd/MM/yyyy" />
                                                </td>
                                                <td>${cautiondouane.getTrueDate(cautiondouane.dateFin)} </td>
                                                <td>
                                                    <fmt:formatNumber value="${cautiondouane.montant}" pattern="#,##0" />
                                                </td>
                                                <td>
                                                    ${cautiondouane.banque.libelle}
                                                </td>
                                                <td class="text-center">
                                                    <spring:url value="/cautiondouane/${cautiondouane.id}/show" htmlEscape="true" var="cautiondouane_show" />
                                                    <a href="${cautiondouane_show}" class="btn btn-primary btn-sm">
                                                        <span class="glyphicon glyphicon-open">
                                                        </span>
                                                        <spring:message code="action.detail" />
                                                    </a>
                                                </td>
                                            </tr>
                                        </c:if>
                                    </c:forEach>
                                    <c:if test="${not empty cautiondouanes}">
                                    <div class="dropdown" style="display: inline-block !important">
                                        <button class="btn btn-default dropdown-toogle" id="dropdown-user" data-toggle="dropdown">
                                            <i class="glyphicon glyphicon-print"></i>
                                            <spring:message code="print.message" />
                                            <i class="caret"></i>
                                        </button>
                                        <ul class="dropdown-menu" role="menu" aria-labeledby="dropdown-user">
                                            <li>
                                                <spring:url htmlEscape="true" var="xls_print" value="/cautiondouane.xls" />
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
                                        <li role="presentation"><a role="menuitem" tabindex="-1" href="?&querybanque=${cautiondouane.banque.id}&size=5&querydebutperiode=${querydebutperiode}&queryfinperiode=${queryfinperiode}&querymontant=${cautiondouane.montant}">5</a></li>
                                        <li role="presentation"><a role="menuitem" tabindex="-1" href="?&querybanque=${cautiondouane.banque.id}&size=10&querydebutperiode=${querydebutperiode}&queryfinperiode=${queryfinperiode}&querymontant=${cautiondouane.montant}">10</a></li>
                                        <li role="presentation"><a role="menuitem" tabindex="-1" href="?&querybanque=${cautiondouane.banque.id}&size=20&querydebutperiode=${querydebutperiode}&queryfinperiode=${queryfinperiode}&querymontant=${cautiondouane.montant}">20</a></li>
                                        <li role="presentation"><a role="menuitem" tabindex="-1" href="?&querybanque=${cautiondouane.banque.id}&size=30&querydebutperiode=${querydebutperiode}&queryfinperiode=${queryfinperiode}&querymontant=${cautiondouane.montant}">30</a></li>
                                        <li role="presentation"><a role="menuitem" tabindex="-1" href="?&querybanque=${cautiondouane.banque.id}&size=40&querydebutperiode=${querydebutperiode}&queryfinperiode=${queryfinperiode}&querymontant=${cautiondouane.montant}">40</a></li>
                                        <li role="presentation"><a role="menuitem" tabindex="-1" href="?&querybanque=${cautiondouane.banque.id}&size=50&querydebutperiode=${querydebutperiode}&queryfinperiode=${queryfinperiode}&querymontant=${cautiondouane.montant}">50</a></li>
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
                        <spring:url value="/cautiondouane/stats" var="cautiondouane_stats"
                                    htmlEscape="true" />
                        <a class="btn btn-primary btn-sm" href="${cautiondouane_stats}">
                            <span class="glyphicon glyphicon-stats"></span> <spring:message code="action.stats"/>
                        </a>
                        <hr/>
                        <sec:authorize access="hasRole('ROLE_ADMIN')" >
                            <spring:url value="/cautiondouane/new" htmlEscape="true" var="cautiondouane_new" />
                            <a href="${cautiondouane_new}" class="btn btn-primary btn-sm">
                                <span class="glyphicon glyphicon-new-window"></span>
                                <spring:message code="action.nouveau" />
                            </a>
                        </sec:authorize>
                        <div class="pull-right">
                            <ul class="pager">
                                <li><a href="?&querybanque=${cautiondouane.banque.id}&size=${size}&querydebutperiode=${querydebutperiode}&queryfinperiode=${queryfinperiode}&querymontant=${cautiondouane.montant}&page=0" <c:if test="${page eq 0}">class ="btn btn-sm disabled"</c:if>>
                                            <span class="glyphicon glyphicon-fast-backward"></span>
                                        </a></li>
                                    <li><a href="?&querybanque=${cautiondouane.banque.id}&size=${size}&querydebutperiode=${querydebutperiode}&queryfinperiode=${queryfinperiode}&querymontant=${cautiondouane.montant}&page=${page-1}" <c:if test="${page eq 0}">class ="btn btn-sm disabled"</c:if>>
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
                                    <a href="?&querybanque=${cautiondouane.banque.id}&size=${size}&querydebutperiode=${querydebutperiode}&queryfinperiode=${queryfinperiode}&querymontant=${cautiondouane.montant}&page=${page+1}"
                                       <c:choose>
                                           <c:when test="${page+1 eq Totalpage}">class ="btn btn-sm disabled"</c:when>
                                           <c:when test="${Totalpage eq 0}">class ="btn btn-sm disabled"</c:when>
                                       </c:choose>
                                       >
                                        <span class="glyphicon glyphicon-forward"></span>
                                    </a>
                                </li>
                                <li><a href="?querybanque=${cautiondouane.banque.id}&size=${size}&querydebutperiode=${querydebutperiode}&queryfinperiode=${queryfinperiode}&querymontant=${cautiondouane.montant}&page=${Totalpage-1}"
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
                <spring:url value="/cautiondouane/" var="cautiondouane_home"
                            htmlEscape="true" />

                <form:form method="get" commandName="cautiondouane" action="${cautiondouane_home}">
                    <div class="form-group">
                        <label>
                            <spring:message code="cautiondouane.banque" />
                        </label>
                        <select name="querybanque" class="form-control input-sm">
                            <option value="">---</option>
                            <c:forEach var="banque" items="${banques}">

                                <option value="${banque.key}"
                                        <c:if test="${banque.key eq cautiondouane.banque.id}">
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
                            <spring:message code="cautiondouane.debutPeriodeEcheance" />
                        </label>
                        <input id="dateDebut" type="text" value="${querydebutperiode}" class="form-control input-sm" name="querydebutperiode"/>
                    </div>
                    <div class="form-group">
                        <label>
                            <spring:message code="cautiondouane.finPeriodeEcheance" />
                        </label>
                        <input id="dateFin" type="text" value="${queryfinperiode}" class="form-control input-sm" name="queryfinperiode"/>
                    </div>

                    <div class="form-group">
                        <label>
                            <spring:message code="cautiondouane.montant" />
                        </label>
                        <input id="montant" type="number" value="${querymontant}" class="form-control input-sm" name="querymontant"/>
                    </div>
                    <hr/>
                    <button class="btn btn-default btn-sm">
                        <span class="glyphicon glyphicon-search"></span> <spring:message code="action.rechercher"/>
                    </button>
                    <spring:url value="/cautiondouane/" htmlEscape="true" var="cautiondouane_home" />
                    <a href="${cautiondouane_home}" class="btn btn-default btn-sm">
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
