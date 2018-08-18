<%--
    Document   : new
    Created on : 16 aug. 2018, 22:00:59
    Author     : sando
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
                    <spring:message code="cautiondouane.nouveau" />
                </h4>
                <hr/>
            </div>
        </div>

        <spring:url value="/cautiondouane/create" var="cautiondouane_create"
                    htmlEscape="true" />
        <form:form method="post" commandName="cautiondouane" action="${cautiondouane_create}">
            <div class="row">
                <div class="col-md-12">
                    <form:errors path="*"  cssClass="text-danger" />
                </div>
            </div>
            <div class="row">
                <div class="col-md-4">
                    <div class="form-group">
                        <form:label for="banque" path="">
                            <spring:message code="cautiondouane.banque" />
                        </form:label>
                        <form:select id="banque" path="banque.id" cssClass="form-control">
                            <form:options  items="${banques}" />
                        </form:select>
                        <form:errors path="banque"  cssClass="text-danger" />
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="form-group">
                        <form:label for="numero" path="">
                            <spring:message code="cautiondouane.numero" /> :
                        </form:label>
                        <form:input id="numero" path="numero" cssClass="form-control"/>
                        <form:errors path="numero" cssClass="text-danger"/>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="form-group">
                        <form:label for="libelle" path="">
                            <spring:message code="cautiondouane.libelle" /> :
                        </form:label>
                        <form:input id="libelle" path="libelle" cssClass="form-control"/>
                        <form:errors path="libelle" cssClass="text-danger"/>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-md-4">
                    <div class="form-group">
                        <form:label for="montant" path="">
                            <spring:message code="cautiondouane.montant" /> :
                        </form:label>
                        <form:input id="montant" path="montant" cssClass="form-control"/>
                        <form:errors path="montant" cssClass="text-danger"/>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="form-group">
                        <form:label for="dateDebut" path="">
                            <spring:message code="cautiondouane.dateDebut" /> :
                        </form:label>
                        <form:input id="dateDebut" path="dateDebut" cssClass="form-control"/>
                        <form:errors path="dateDebut" cssClass="text-danger"/>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="form-group">
                        <form:label for="dateFin" path="">
                            <spring:message code="cautiondouane.dateFin" /> :
                        </form:label>
                        <form:input id="dateFin" path="dateFin" cssClass="form-control"/>
                        <form:errors path="dateFin" cssClass="text-danger"/>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-md-11">
                    <div class="form-group">
                        <form:label for="divers" path="">
                            <spring:message code="cautiondouane.divers" /> :
                        </form:label>
                        <form:textarea cols="90" rows="6" id="divers" path="divers" cssClass="form-control"/>
                        <form:errors path="divers" cssClass="text-danger"/>
                    </div>
                </div>
                <div class="col-md-4">
                </div>
                <div class="col-md-4">
                </div>
            </div>

            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <div class="panel-footer">
                <button type="submit" class="btn btn-primary btn-sm">
                    <span class="glyphicon glyphicon-save"></span>
                    <spring:message code="action.enregistrer" />
                </button>&nbsp;&nbsp;
                <spring:url value="/cautiondouane/" htmlEscape="true"
                            var="cautiondouane_home" />
                <a href="${cautiondouane_home}" class="btn btn-default btn-sm">
                    <span class="glyphicon glyphicon-list"></span>
                    <spring:message code="cautiondouane.liste" />
                </a>
            </div>
        </div>
    </form:form>

    <script src="<c:url value="/resources/js/jquery.dynamiclist.min.js" />"></script>
    <script src="<c:url value="/resources/js/jquery-ui.js" />"></script>
    <script src="<c:url value="/resources/js/bootstrap-filestyle.js" />"></script>
    <script type="text/javascript">
        $(document).ready(function () {
            $("#dateDebut").datepicker({
                changeMonth: true,
                changeYear: true,
                dateFormat: "dd/mm/yy",
                showButtonPanel: false
            });
            $("#dateFin").datepicker({
                changeMonth: true,
                changeYear: true,
                dateFormat: "dd/mm/yy",
                showButtonPanel: false
            });
        });
    </script>

</tiles:putAttribute>
</tiles:insertDefinition>