<%--
    Document   : edit
    Created on : 29 janv. 2015, 21:54:31
    Author     : fabrice
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<tiles:insertDefinition name="layout">
    <tiles:putAttribute name="body">
        <div class="row">
            <div class="col-md-12">
                <h4>
                    <spring:message code="materiel.modifier" /> : ${materiel.nom}
                </h4>
                <hr/>
            </div>
        </div>

        <div class="row">
            <div class="col-md-6 col-md-offset-3">
                <spring:url value="/materiel/update" var="materiel_update"
                            htmlEscape="true" />
                <form:form method="post" commandName="materiel" action="${materiel_update}">
                    <div class="panel panel-default">
                        <div class="panel-body">
                            <div class="form-group">
                                <form:label for="typeMateriel" path="">
                                    <spring:message code="materiel.typeMateriel" />
                                </form:label>
                                <form:select id="typeMateriel" path="typeMateriel.id" cssClass="form-control">
                                    <form:options  items="${typeMateriels}" />
                                </form:select>
                                <form:errors path="typeMateriel"  cssClass="text-danger"/>
                            </div>
                            <div class="form-group">
                                <form:label for="code" path="">
                                    <spring:message code="materiel.code" />
                                </form:label>
                                <form:input id="code" path="code" cssClass="form-control"/>
                                <form:errors path="code" cssClass="text-danger"/>
                            </div>
                            <div class="form-group">
                                <form:label for="nom" path="">
                                    <spring:message code="materiel.nom" /> :
                                </form:label>
                                <form:input id="nom" path="nom" cssClass="form-control"/>
                                <form:errors path="nom" cssClass="text-danger"/>
                            </div>
                            <div class="form-group">
                                <form:label for="description" path="">
                                    <spring:message code="materiel.description" /> :
                                </form:label>
                                <form:textarea id="description" path="description" cssClass="form-control"/>
                                <form:errors path="description" cssClass="text-danger"/>
                            </div>

                        </div>
                        <form:hidden path="id"/>
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        <div class="panel-footer">
                            <button type="submit" class="btn btn-primary btn-sm">
                                <span class="glyphicon glyphicon-save"></span>
                                <spring:message code="action.modifier" />
                            </button>
                            <spring:url value="/materiel/" htmlEscape="true"
                                        var="materiel_home" />
                            <a href="${materiel_home}" class="btn btn-default btn-sm">
                                <span class="glyphicon glyphicon-list"></span>
                                <spring:message code="materiel.liste" />
                            </a>
                        </div>

                    </div>
                </form:form>
            </div>
        </div>
    </tiles:putAttribute>
</tiles:insertDefinition>