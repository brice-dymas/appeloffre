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
            <div class="col-md-9">
                <div class="row">
                    <div class="col-md-12">
                        <div>
                            <h3>
                                <spring:message code="appelOffre.liste" />
                            </h3>
                            <hr/>
                        </div>
                        <div class="dropdown pull-right ">
                            <button class="btn btn-default dropdown-toggle" type="button" id="dropdownMenu1" data-toggle="dropdown" aria-expanded="true">
                                <spring:message code="search.taille" />
                                : ${size}&nbsp;
                                <span class="caret"></span>
                            </button>
                            <ul class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu1">
                                <li role="presentation"><a role="menuitem" tabindex="-1" href="?querynumero=${appeloffre.numero}&queryintitule=${appeloffre.intitule}&querydebutperiode=${querydebutperiode}&queryfinperiode=${queryfinperiode}&querydebutperiode=${querydebutperiode}&queryfinperiode=${queryfinperiode}&querymaitredouvrage=${appeloffre.maitreDouvrage}&queryfiliale=${appeloffre.filiale.id}&querydeleted=${appeloffre.deleted}&size=5">5</a></li>
                                <li role="presentation"><a role="menuitem" tabindex="-1" href="?querynumero=${appeloffre.numero}&queryintitule=${appeloffre.intitule}&querydebutperiode=${querydebutperiode}&queryfinperiode=${queryfinperiode}&querymaitredouvrage=${appeloffre.maitreDouvrage}&queryfiliale=${appeloffre.filiale.id}&querydeleted=${appeloffre.deleted}&size=10">10</a></li>
                                <li role="presentation"><a role="menuitem" tabindex="-1" href="?querynumero=${appeloffre.numero}&queryintitule=${appeloffre.intitule}&querydebutperiode=${querydebutperiode}&queryfinperiode=${queryfinperiode}&querymaitredouvrage=${appeloffre.maitreDouvrage}&queryfiliale=${appeloffre.filiale.id}&querydeleted=${appeloffre.deleted}&size=20">20</a></li>
                                <li role="presentation"><a role="menuitem" tabindex="-1" href="?querynumero=${appeloffre.numero}&queryintitule=${appeloffre.intitule}&querydebutperiode=${querydebutperiode}&queryfinperiode=${queryfinperiode}&querymaitredouvrage=${appeloffre.maitreDouvrage}&queryfiliale=${appeloffre.filiale.id}&querydeleted=${appeloffre.deleted}&size=30">30</a></li>
                                <li role="presentation"><a role="menuitem" tabindex="-1" href="?querynumero=${appeloffre.numero}&queryintitule=${appeloffre.intitule}&querydebutperiode=${querydebutperiode}&queryfinperiode=${queryfinperiode}&querymaitredouvrage=${appeloffre.maitreDouvrage}&queryfiliale=${appeloffre.filiale.id}&querydeleted=${appeloffre.deleted}&size=40">40</a></li>
                                <li role="presentation"><a role="menuitem" tabindex="-1" href="?querynumero=${appeloffre.numero}&queryintitule=${appeloffre.intitule}&querydebutperiode=${querydebutperiode}&queryfinperiode=${queryfinperiode}&querymaitredouvrage=${appeloffre.maitreDouvrage}&queryfiliale=${appeloffre.filiale.id}&querydeleted=${appeloffre.deleted}&size=50">50</a></li>
                            </ul>
                        </div>
                        <table class="table table-condensed table-hover table-bordered">
                            <thead class="text-center btn-primary" >
                                <tr>
                                    <th>
                                        <span class="btn">
                                            <spring:message code="appelOffre.numero" />
                                        </span>
                                    </th>
                                    <th>
                                        <span class="btn">
                                            <spring:message code="appelOffre.intitule" />
                                        </span>
                                    </th>
                                    <th>
                                        <span class="btn">
                                            <spring:message code="appelOffre.filiale" />
                                        </span>
                                    </th>
                                    <th>
                                        <span class="btn">
                                            <spring:message code="appelOffre.dateDepot" />
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
                                <c:if test="${empty appelOffres}">
                                    <tr>
                                        <td colspan="7" class="label-danger text-center" >
                                            <spring:message code="empty.data" />
                                        </td>
                                    </tr>
                                </c:if>
                                <c:if test="${not empty appelOffres }">
                                    <c:forEach items="${appelOffres}" var="appelOffre">
                                        <c:if test="${not appelOffre.deleted}" >
                                            <tr>
                                                <td>
                                                    ${appelOffre.numero}
                                                </td>
                                                <td>
                                                    ${appelOffre.intitule}
                                                </td>
                                                <td>
                                                    ${appelOffre.filiale.nom}
                                                </td>
                                                <td>
                                                    <fmt:formatDate type="date" value="${appelOffre.dateDepot}" pattern="dd/MM/yyyy" />
                                                </td>
                                                <td class="text-center">
                                                    <spring:url value="/appeloffre/${appelOffre.id}/edit" htmlEscape="true" var="appeloffre_edit" />
                                                    <a href="${appeloffre_edit}" class="btn btn-primary btn-sm">
                                                        <span class="glyphicon glyphicon-edit">
                                                            <spring:message code="action.modifier" />
                                                        </span>

                                                    </a>
                                                    &nbsp;&nbsp;
                                                    <spring:url value="/appeloffre/${appelOffre.id}/show" htmlEscape="true" var="appeloffre_show" />
                                                    <a href="${appeloffre_show}" class="btn btn-primary btn-sm">
                                                        <span class="glyphicon glyphicon-open">
                                                            <spring:message code="action.detail" />
                                                        </span>
                                                    </a>
                                                </td>
                                            </tr>
                                        </c:if>
                                        <c:if test="${appelOffre.deleted}" >
                                            <tr class="text-danger">
                                                <td>
                                                    ${appelOffre.numero}
                                                </td>
                                                <td>
                                                    ${appelOffre.intitule}
                                                </td>
                                                <td>
                                                    ${appelOffre.filiale.nom}
                                                </td>
                                                <td>
                                                    <fmt:formatDate type="date" value="${appelOffre.dateDepot}" pattern="dd/MM/yyyy" />
                                                </td>
                                                <td class="text-center">
                                                    <spring:url value="/appeloffre/${appelOffre.id}/edit" htmlEscape="true" var="appeloffre_edit" />
                                                    <a href="${appeloffre_edit}" class="btn btn-primary btn-sm">
                                                        <span class="glyphicon glyphicon-edit">
                                                            <spring:message code="action.modifier" />
                                                        </span>

                                                    </a>
                                                    &nbsp;&nbsp;
                                                    <spring:url value="/appeloffre/${appelOffre.id}/show" htmlEscape="true" var="appeloffre_show" />
                                                    <a href="${appeloffre_show}" class="btn btn-primary btn-sm">
                                                        <span class="glyphicon glyphicon-open">
                                                            <spring:message code="action.detail" />
                                                        </span>
                                                    </a>
                                                </td>
                                            </tr>
                                        </c:if>

                                    </c:forEach>
                                </c:if>
                            </tbody>

                        </table>

                    </div>
                </div>
                <div class="row">
                    <div class="col-lg-12">
                        <hr/>
                        <sec:authorize access="hasRole('ROLE_ADMIN')" >
                            <spring:url value="/appeloffre/new" htmlEscape="true" var="appeloffre_new" />
                            <a href="${appeloffre_new}" class="btn btn-primary btn-sm">
                                <span class="glyphicon glyphicon-new-window"></span>
                                <spring:message code="action.nouveau" />
                            </a>
                        </sec:authorize>

                        <div class="pull-right">
                            <ul class="pager">

                                <li>
                                    <a href="?querynumero=${appeloffre.numero}&queryintitule=${appeloffre.intitule}&querydebutperiode=${querydebutperiode}&queryfinperiode=${queryfinperiode}&querymaitredouvrage=${appeloffre.maitreDouvrage}&queryfiliale=${appeloffre.filiale.id}&page=0&size=${size}" <c:if test="${page eq 0}">class ="btn btn-sm disabled"</c:if>>
                                            <span class="glyphicon glyphicon-fast-backward"></span>
                                        </a>
                                    </li>
                                    <li>
                                        <a href="?querynumero=${appeloffre.numero}&queryintitule=${appeloffre.intitule}&querydebutperiode=${querydebutperiode}&queryfinperiode=${queryfinperiode}&querymaitredouvrage=${appeloffre.maitreDouvrage}&queryfiliale=${appeloffre.filiale.id}&page=${page-1}&size=${size}" <c:if test="${page eq 0}">class ="btn btn-sm disabled"</c:if>>
                                            <span class="glyphicon glyphicon-backward"></span>
                                        </a>
                                    </li>
                                    <li>
                                        <input type="text" size="5" class="pager_detail text-center" readonly
                                        <c:choose>
                                            <c:when test="${Totalpage eq 0}">value="${page+1}/${Totalpage+1}"</c:when>
                                            <c:otherwise>value="${page+1}/${Totalpage}"</c:otherwise>
                                        </c:choose>
                                        />
                                </li>
                                <li>
                                    <a href="?querynumero=${appeloffre.numero}&queryintitule=${appeloffre.intitule}&querydebutperiode=${querydebutperiode}&queryfinperiode=${queryfinperiode}&querymaitredouvrage=${appeloffre.maitreDouvrage}&queryfiliale=${appeloffre.filiale.id}&page=${page+1}&size=${size}"
                                       <c:choose>
                                           <c:when test="${page+1 eq Totalpage}">class ="btn btn-sm disabled"</c:when>
                                           <c:when test="${Totalpage eq 0}">class ="btn btn-sm disabled"</c:when>
                                       </c:choose>
                                       >
                                        <span class="glyphicon glyphicon-forward"></span>
                                    </a>
                                </li>
                                <li>
                                    <a href="?querynumero=${appeloffre.numero}&queryintitule=${appeloffre.intitule}&querydebutperiode=${querydebutperiode}&queryfinperiode=${queryfinperiode}&querymaitredouvrage=${appeloffre.maitreDouvrage}&queryfiliale=${appeloffre.filiale.id}&page=${Totalpage-1}&size=${size}"
                                       <c:choose>
                                           <c:when test="${page+1 eq Totalpage}">class ="btn btn-sm disabled"</c:when>
                                           <c:when test="${Totalpage eq 0}">class ="btn btn-sm disabled"</c:when>
                                       </c:choose>
                                       >
                                        <span class="glyphicon glyphicon-fast-forward"></span>
                                    </a>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-md-3">
                <div>
                    <h3>
                        <spring:message code="action.rechercher" />
                    </h3>
                    <hr/>
                </div>
                <spring:url value="/appeloffre/" var="appeloffre_home"
                            htmlEscape="true" />
                <form:form method="get" commandName="appelOffre" action="${appeloffre_home}">
                    <div class="form-group">
                        <label>
                            <spring:message code="element.statut" />
                        </label>

                        <select name="querydeleted" class="form-control input-sm">
                            <option value="">---</option>
                            <c:forEach var="deleted" items="${etats}">

                                <option value="${deleted.key}" >
                                    ${deleted.value}
                                </option>
                            </c:forEach>
                        </select>
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
                            <spring:message code="appelOffre.numero" />
                            :</label>
                        <input type="text" value="${appelOffre.numero}" class="form-control input-sm" name="querynumero"/>
                        <input type="hidden" value="${size}" name="size"/>
                    </div>
                    <div class="form-group">
                        <label>
                            <spring:message code="appelOffre.intitule" />
                            :</label>
                        <input type="text" value="${appelOffre.intitule}" class="form-control input-sm" name="queryintitule"/>
                    </div>
                    <div class="form-group">
                        <label>
                            <spring:message code="appelOffre.debutPeriode" />
                        </label>
                        <input id="dateDebut" type="text" value="${appelOffre.dateDepot}" class="form-control input-sm" name="querydebutperiode"/>
                    </div>
                    <div class="form-group">
                        <label>
                            <spring:message code="appelOffre.finPeriode" />
                        </label>
                        <input id="dateFin" type="text" value="${materiel.nom}" class="form-control input-sm" name="queryfinperiode"/>
                    </div>

                    <div class="form-group">
                        <label>
                            <spring:message code="appelOffre.filiale" />
                        </label>

                        <select name="queryfiliale" class="form-control input-sm">
                            <option value="">---</option>
                            <c:forEach var="filiale" items="${filiales}">

                                <option value="${filiale.key}"
                                        <c:if test="${filiale.key eq appelOffre.filiale.id}">
                                            selected
                                        </c:if>
                                        >
                                    ${filiale.value}
                                </option>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="form-group">
                        <label>
                            <spring:message code="appelOffre.maitreDouvrage" />
                        </label>
                        <input type="text" value="${appelOffre.maitreDouvrage}" class="form-control input-sm" name="querymaitredouvrage"/>
                    </div>
                    <hr/>
                    <button class="btn btn-default btn-sm">
                        <span class="glyphicon glyphicon-search">

                        </span> <spring:message code="action.rechercher"/>
                    </button>
                    <spring:url value="/appeloffre/" htmlEscape="true" var="appeloffre_home" />
                    <a href="${appeloffre_home}" class="btn btn-default btn-sm">
                        <span class="glyphicon glyphicon-refresh"></span>
                        <spring:message code="search.delete" />
                    </a>

                </form:form>
            </div>
        </div>


        <script src="<c:url value="/resources/js/jquery.dynamiclist.min.js" />"></script>
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