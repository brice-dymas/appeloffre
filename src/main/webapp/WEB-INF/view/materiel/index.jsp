<%--
    Document   : index
    Created on : 29 janv. 2015, 19:59:50
    Author     : fabrice
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<tiles:insertDefinition name="layout">
    <tiles:putAttribute name="body">
        <div class="row">
            <div class="col-md-9">

                <div>
                    <h3><spring:message code="materiel.liste" /></h3>
                    <hr/>
                </div>

                <div class="dropdown pull-right ">
                    <button class="btn btn-default dropdown-toggle" type="button" id="dropdownMenu1" data-toggle="dropdown" aria-expanded="true">
                        <spring:message code="search.taille" />
                        : ${size}&nbsp;
                        <span class="caret"></span>
                    </button>
                    <ul class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu1">
                        <li role="presentation"><a role="menuitem" tabindex="-1" href="?querytype=${materiel.typeMateriel.id}&querycode=${materiel.code}&querynom=${materiel.nom}&size=5">5</a></li>
                        <li role="presentation"><a role="menuitem" tabindex="-1" href="?querytype=${materiel.typeMateriel.id}&querycode=${materiel.code}&querynom=${materiel.nom}&size=10">10</a></li>
                        <li role="presentation"><a role="menuitem" tabindex="-1" href="?querytype=${materiel.typeMateriel.id}&querycode=${materiel.code}&querynom=${materiel.nom}&size=20">20</a></li>
                        <li role="presentation"><a role="menuitem" tabindex="-1" href="?querytype=${materiel.typeMateriel.id}&querycode=${materiel.code}&querynom=${materiel.nom}&size=30">30</a></li>
                        <li role="presentation"><a role="menuitem" tabindex="-1" href="?querytype=${materiel.typeMateriel.id}&querycode=${materiel.code}&querynom=${materiel.nom}&size=40">40</a></li>
                        <li role="presentation"><a role="menuitem" tabindex="-1" href="?querytype=${materiel.typeMateriel.id}&querycode=${materiel.code}&querynom=${materiel.nom}&size=50">50</a></li>
                    </ul>
                </div>
                <table class="table table-condensed table-hover table-bordered">
                    <thead class=" text-center btn-primary" >
                        <tr>
                            <th>
                                <span class="btn">
                                    <spring:message code="materiel.code" />
                                </span>
                            </th>

                            <th>
                                <span class="btn">
                                    <spring:message code="materiel.nom" />
                                </span>
                            </th>

                            <th>
                                <span class="btn">
                                    <spring:message code="materiel.typeMateriel" />
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
                        <c:if test="${materiels.size() eq 0}">
                            <tr>
                                <td class="text-center label-danger" colspan="4">
                                    <spring:message code="empty.data" />
                                </td>
                            </tr>
                        </tbody>

                    </table>

                    <div class="row">
                        <div class="col-lg-12">
                            <hr/>
                            <sec:authorize access="hasRole('ROLE_ADMIN')" >
                                <spring:url value="/materiel/new" htmlEscape="true" var="materiel_new" />
                                <a href="${materiel_new}" class="btn btn-primary btn-sm">
                                    <span class="glyphicon glyphicon-new-window"></span>
                                    <spring:message code="action.nouveau" />
                                </a>
                            </sec:authorize>
                            <div class="pull-right">
                                <ul class="pager">

                                    <li><a href="?query=${query}&page=0&size=${size}" class ="btn btn-sm disabled">
                                            <span class="glyphicon glyphicon-fast-backward"></span>
                                        </a></li>
                                    <li><a href="?query=${query}&page=${page-1}&size=${size}"class ="btn btn-sm disabled">
                                            <span class="glyphicon glyphicon-backward"></span>
                                        </a></li>
                                    <li><input type="text" class="pager_detail text-center" readonly value="0/0"/></li>
                                    <li><a href="?query=${query}&page=${page+1}&size=${size}" class ="btn btn-sm disabled">
                                            <span class="glyphicon glyphicon-forward"></span>
                                        </a></li>
                                    <li><a href="?query=${query}&page=${Totalpage-1}&size=${size}" class ="btn btn-sm disabled">
                                            <span class="glyphicon glyphicon-fast-forward"></span>
                                        </a></li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </c:if>
                <c:if test="${materiels.size() ne 0}">
                    <c:forEach items="${materiels}" var="materiel">
                        <c:if test="${materiel.deleted}" >
                            <tr class="text-danger">
                                <td>
                                    ${materiel.code}
                                </td>
                                <td>
                                    ${materiel.nom}
                                </td>
                                <td>
                                    ${materiel.typeMateriel.nom}
                                </td>
                                <td class="text-center">
                                    <sec:authorize access="hasRole('ROLE_ADMIN')" >
                                        <spring:url value="/materiel/${materiel.id}/edit" htmlEscape="true" var="materiel_edit" />
                                        <a href="${materiel_edit}" class="btn btn-primary btn-sm">
                                            <span class="glyphicon glyphicon-edit"></span>
                                            <spring:message code="action.modifier" />
                                        </a>
                                        &nbsp;&nbsp;
                                    </sec:authorize>
                                    <spring:url value="/materiel/${materiel.id}/show" htmlEscape="true" var="materiel_show" />
                                    <a href="${materiel_show}" class="btn btn-primary btn-sm">
                                        <span class="glyphicon glyphicon-open"></span>
                                        <spring:message code="action.detail" />
                                    </a>
                                </td>
                            </tr>
                        </c:if>
                        <c:if test="${not materiel.deleted}" >
                            <tr>
                                <td>
                                    ${materiel.code}
                                </td>
                                <td>
                                    ${materiel.nom}
                                </td>
                                <td>
                                    ${materiel.typeMateriel.nom}
                                </td>
                                <td class="text-center">
                                    <sec:authorize access="hasRole('ROLE_ADMIN')" >
                                        <spring:url value="/materiel/${materiel.id}/edit" htmlEscape="true" var="materiel_edit" />
                                        <a href="${materiel_edit}" class="btn btn-primary btn-sm">
                                            <span class="glyphicon glyphicon-edit"></span>
                                            <spring:message code="action.modifier" />
                                        </a>
                                        &nbsp;&nbsp;
                                    </sec:authorize>
                                    <spring:url value="/materiel/${materiel.id}/show" htmlEscape="true" var="materiel_show" />
                                    <a href="${materiel_show}" class="btn btn-primary btn-sm">
                                        <span class="glyphicon glyphicon-open"></span>
                                        <spring:message code="action.detail" />
                                    </a>
                                </td>
                            </tr>
                        </c:if>

                    </c:forEach>
                    </tbody>
                    </table>

                    <div class="row">
                        <div class="col-lg-12">
                            <hr/>
                            <sec:authorize access="hasRole('ROLE_ADMIN')" >
                                <spring:url value="/materiel/new" htmlEscape="true" var="materiel_new" />
                                <a href="${materiel_new}" class="btn btn-primary btn-sm">
                                    <span class="glyphicon glyphicon-new-window"></span>
                                    <spring:message code="action.nouveau" />
                                </a>
                            </sec:authorize>
                            <div class="pull-right">
                                <ul class="pager">

                                    <li><a href="?querytype=${materiel.typeMateriel.id}&querycode=${materiel.code}&querynom=${materiel.nom}&page=0&size=${size}" <c:if test="${page eq 0}">class ="btn btn-sm disabled"</c:if>>
                                                <span class="glyphicon glyphicon-fast-backward"></span>
                                            </a></li>
                                        <li><a href="?querytype=${materiel.typeMateriel.id}&querycode=${materiel.code}&querynom=${materiel.nom}&page=${page-1}&size=${size}" <c:if test="${page eq 0}">class ="btn btn-sm disabled"</c:if>>
                                                <span class="glyphicon glyphicon-backward"></span>
                                            </a></li>
                                        <li><input type="text" class="pager_detail text-center" readonly value="${page+1}/${Totalpage}"/></li>
                                    <li><a href="?querytype=${materiel.typeMateriel.id}&querycode=${materiel.code}&querynom=${materiel.nom}&page=${page+1}&size=${size}" <c:if test="${page+1 eq Totalpage}">class ="btn btn-sm disabled"</c:if>>
                                                <span class="glyphicon glyphicon-forward"></span>
                                            </a></li>
                                        <li><a href="?querytype=${materiel.typeMateriel.id}&querycode=${materiel.code}&querynom=${materiel.nom}&page=${Totalpage-1}&size=${size}" <c:if test="${page+1 eq Totalpage}">class ="btn btn-sm disabled"</c:if>>
                                                <span class="glyphicon glyphicon-fast-forward"></span>
                                            </a></li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                </c:if>
            </div>
            <div class="col-md-3">
                <div>
                    <h3><spring:message code="action.rechercher" /></h3>
                    <hr/>
                </div>
                <spring:url value="/materiel/" var="materiel_home"
                            htmlEscape="true" />
                <form:form method="get" commandName="materiel" action="${materiel_home}">
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
                            <spring:message code="materiel.typeMateriel" />
                        </label>

                        <select name="querytype" class="form-control input-sm">
                            <option value="">---</option>
                            <c:forEach var="typemateriel" items="${typeMateriels}">

                                <option value="${typemateriel.key}"
                                        <c:if test="${typemateriel.key eq materiel.typeMateriel.id}">
                                            selected
                                        </c:if>
                                        >
                                    ${typemateriel.value}
                                </option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="form-group">
                        <label>
                            <spring:message code="materiel.code" />
                        </label>
                        <input type="text" value="${materiel.code}" class="form-control input-sm" name="querycode"/>
                        <input type="hidden" value="${size}" name="size"/>
                    </div>
                    <div class="form-group">
                        <label>
                            <spring:message code="materiel.nom" />
                        </label>
                        <input type="text" value="${materiel.nom}" class="form-control input-sm" name="querynom"/>
                    </div>
                    <hr/>
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    <button class="btn btn-default btn-sm">
                        <span class="glyphicon glyphicon-search"></span> <spring:message code="search"/></button>
                        <spring:url value="/materiel/" htmlEscape="true" var="materiel_home" />
                    <a href="${materiel_home}" class="btn btn-default btn-sm">
                        <span class="glyphicon glyphicon-refresh"></span>
                        <spring:message code="search.delete" />
                    </a>

                </form:form>
            </div><!-- /.col-lg-6 -->
        </div>



    </tiles:putAttribute>
</tiles:insertDefinition>