<%--
    Document   : edit
    Created on : Aug 30, 2018, 05:27:15 PM
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
                    <spring:message code="caution.modifier" />
                    : ${caution.referenceMarche}
                </h4>
                <hr/>
            </div>
        </div>
        <div class="col-md-12">
            <spring:url value="/caution/update" var="caution_update" htmlEscape="true"/>
            <form:form method="post" commandName="caution" action="${caution_update}">
                <div class="panel panel-default">
                    <div class="panel-body">
                        <div class="row">
                            <div class="col-md-3">
                                <div class="form-group">
                                    <form:label for="numero" path="">
                                        <spring:message code="caution.numero" /> :
                                    </form:label>
                                    <form:input id="numero" path="numero" cssClass="form-control"/>
                                    <form:errors path="numero" cssClass="text-danger"/>
                                </div>
                            </div>
                            <div class="col-md-3">
                                <div class="form-group">
                                    <form:label for="referenceMarche" path="">
                                        <spring:message code="caution.referenceMarche" />
                                    </form:label>
                                    <form:input id="referenceMarche" path="referenceMarche" cssClass="form-control input-sm"/>
                                    <form:errors path="referenceMarche" cssClass="text-danger"/>
                                </div>
                            </div>
                            <div class="col-md-3">
                                <div class="form-group">
                                    <form:label for="dateDebut" path="">
                                        <spring:message code="caution.dateDebut" />
                                    </form:label>
                                    <form:input id="dateDebut" path="dateDebut" cssClass="form-control input-sm"/>
                                    <form:errors path="dateDebut" cssClass="text-danger"/>
                                </div>
                            </div>
                            <div class="col-md-3">
                                <div class="form-group">
                                    <form:label for="dateFin" path="">
                                        <spring:message code="caution.dateFin" />
                                    </form:label>
                                    <form:input id="dateFin" path="dateFin" cssClass="form-control input-sm"/>
                                    <form:errors path="dateFin" cssClass="text-danger"/>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-3">
                                <div class="form-group">
                                    <form:label for="typeCaution" path="">
                                        <spring:message code="caution.typeCaution" />
                                    </form:label>
                                    <form:select id="typeCaution" path="typeCaution.id" cssClass="form-control">
                                        <form:options items="${typeCautions}" />
                                    </form:select>
                                    <form:errors path="typeCaution" cssClass="text-danger" />
                                </div>
                            </div>
                            <div class="col-md-3">
                                <div class="form-group">
                                    <form:label for="montant" path="">
                                        <spring:message code="caution.montant" />
                                    </form:label>
                                    <form:input id="montant" path="montant" cssClass="form-control input-sm"/>
                                    <form:errors path="montant" cssClass="text-danger"/>
                                </div>
                            </div>
                            <div class="col-md-3">
                                <div class="form-group">
                                    <form:label for="montantMarche" path="">
                                        <spring:message code="caution.montantMarche" />
                                    </form:label>
                                    <form:input id="montantMarche" path="montantMarche" cssClass="form-control input-sm"/>
                                    <form:errors path="montantMarche" cssClass="text-danger"/>
                                </div>
                            </div>
                            <div class="col-md-3">
                                <div class="form-group">
                                    <form:label for="banque" path="">
                                        <spring:message code="caution.banque" />
                                    </form:label>
                                    <form:select id="banque" path="banque.id" cssClass="form-control">
                                        <form:options items="${banques}" />
                                    </form:select>
                                    <form:errors path="banque" cssClass="text-danger" />
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-3">
                                <div class="form-group">
                                    <form:label for="commercial" path="">
                                        <spring:message code="caution.commercial" />
                                    </form:label>
                                    <form:select id="commercial" path="commercial.id" cssClass="form-control">
                                        <form:options items="${commercials}" />
                                    </form:select>
                                    <form:errors path="commercial" cssClass="text-danger" />
                                </div>
                            </div>
                            <div class="col-md-3">
                                <div class="form-group">
                                    <form:label for="commissionTrimestrielle" path="">
                                        <spring:message code="caution.commissionTrimestrielle" />
                                    </form:label>
                                    <form:input id="commissionTrimestrielle" path="commissionTrimestrielle" cssClass="form-control input-sm"/>
                                    <form:errors path="commissionTrimestrielle" cssClass="text-danger"/>
                                </div>
                            </div>
                            <div class="col-md-3">
                                <div class="form-group">
                                    <form:label for="legende" path="">
                                        <spring:message code="caution.legende" />
                                    </form:label>
                                    <form:select id="legende" path="legende.id" cssClass="form-control">
                                        <form:option value="-1" label="+++---Choisir la legende---+++"/>
                                        <form:options items="${legendes}" />
                                    </form:select>
                                    <form:errors path="legende" cssClass="text-danger" />
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="form-group">
                                <form:label for="statut" path="">
                                    <spring:message code="caution.statut" />
                                </form:label>
                                <form:textarea cols="100" rows="8" id="statut" path="statut" cssClass="form-control input-sm"/>
                                <form:errors path="statut" cssClass="text-danger"/>
                            </div>
                        </div>
                        <form:hidden path="id"/>
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

                        <div class="panel-footer">
                            <button type="submit" class="btn btn-success btn-sm">
                                <span class="glyphicon glyphicon-save"></span>
                                <spring:message code="action.enregistrer" />
                            </button>
                            <spring:url value="/caution/" htmlEscape="true"
                                        var="caution_home" />
                            <a href="${caution_home}" class="btn btn-danger btn-sm">
                                <span class="glyphicon glyphicon-list"></span>
                                <spring:message code="caution.liste" />
                            </a>
                        </div>
                    </div>
                </div>
            </form:form>
        </div>
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