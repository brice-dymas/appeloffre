<%--
    Document   : new
    Created on : Dec 10, 2014, 9:20:13 AM
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
                <h3>
                    <spring:message code="appelOffre.nouveau" />
               	</h3>
                <hr/>
            </div>
        </div>

        <spring:url
            value="/appeloffre/create"
            var="appeloffre_create"
            htmlEscape="true" />


        <form:form method="post" enctype="multipart/form-data" commandName="appelOffreForm" action="${appeloffre_create}?${_csrf.parameterName}=${_csrf.token}">
            <div class="row">
                <div class="col-md-4">
                    <div class="form-group">
                        <form:label for="numero" path="">
                            <spring:message code="appelOffre.numero" />
                        </form:label>
                        <form:input id="numero" path="appelOffre.numero" cssClass="form-control input-sm"/>
                        <form:errors path="appelOffre.numero" cssClass="text-danger"/>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="form-group">
                        <form:label for="intitule" path="">
                            <spring:message code="appelOffre.intitule" />
                        </form:label>
                        <form:input id="intitule" path="appelOffre.intitule" cssClass="form-control input-sm"/>
                        <form:errors path="appelOffre.intitule" cssClass="text-danger"/>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="form-group">
                        <form:label for="dateDepot" path="">
                            <spring:message code="appelOffre.datedepot" />
                        </form:label>
                        <form:input id="dateDepot" path="appelOffre.dateDepot" cssClass="form-control input-sm"/>
                        <form:errors path="appelOffre.dateDepot" cssClass="text-danger"/>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-md-4">
                    <div class="form-group">
                        <form:label for="maitreDouvrage" path="">
                            <spring:message code="appelOffre.maitreDouvrage" />
                        </form:label>
                        <form:input id="maitreDouvrage" path="appelOffre.maitreDouvrage" cssClass="form-control input-sm"/>
                        <form:errors path="appelOffre.maitreDouvrage" cssClass="text-danger"/>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="form-group">
                        <form:label for="filiale" path="">
                            <spring:message code="appelOffre.filiale" />
                        </form:label>
                        <form:select id="filiale" path="appelOffre.filiale.id" cssClass="form-control">
                            <form:options items="${filiales}" />
                        </form:select>
                        <form:errors path="appelOffre.filiale" cssClass="text-danger" />
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="form-group">
                        <form:label for="delaiDeValidite" path="">
                            <spring:message code="appelOffre.delai" />
                        </form:label>
                        <form:input id="delaiDeValidite" path="appelOffre.delaiDeValidite" cssClass="form-control input-sm"/>
                        <form:errors path="appelOffre.delaiDeValidite" cssClass="text-danger"/>
                    </div>
                </div>
            </div>


            <hr/>
            
            <div class="row">

                <div class="col-md-12">
                    <fieldset>
                        <legend><spring:message code="appelOffre.listeMateriel" />  </legend>

                        <div id="materiel">
                            <table class="table table-bordered">
                                <thead>
                                    <tr class="btn-primary">
                                        <th><spring:message code="ligneMateriel.materiel" /></th>
                                        <th><spring:message code="ligneMateriel.prixUnitaire" /></th>
                                        <th><spring:message code="ligneMateriel.quantite" /></th>
                                        <th><spring:message code="action.effacer" /></th>
                                    </tr>
                                </thead>
                                <tbody data-size="${appelOffreForm.ligneAppels.size()}">

                                    <c:if test="${0 le appelOffreForm.ligneAppels.size()}">

                                        <c:forEach items="${appelOffreForm.ligneAppels}" varStatus="status" begin="0">

                                            <tr class="list-materiel">
                                                <td>
                                                    <spring:bind path="ligneAppels[${status.index}].materiel.id">
                                                        <form:select path="${status.expression}" cssClass="form-control input-sm" >
                                                            <form:options items="${materiels}" />
                                                        </form:select>
                                                        <form:errors path="${status.expression}" cssClass="text-danger"/>
                                                    </spring:bind>
                                                </td>
                                                <td>
                                                    <spring:bind path="ligneAppels[${status.index}].prixUnitaire">
                                                        <form:input path="${status.expression}" cssClass="form-control input-sm" />
                                                        <form:errors path="${status.expression}" cssClass="text-danger"/>
                                                    </spring:bind>
                                                </td>
                                                <td>
                                                    <spring:bind path="ligneAppels[${status.index}].quantite">
                                                        <form:input path="${status.expression}" cssClass="form-control input-sm" />
                                                        <form:errors path="${status.expression}" cssClass="text-danger"/>
                                                    </spring:bind>
                                                </td>
                                                <td class="row-align">

                                                    <button type="button" id="removeMaterielButton" class="btn btn-sm btn-default remove-materiel" >
                                                        <span class="glyphicon glyphicon-minus-sign"></span>
                                                    </button>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </c:if>

                                    <c:if test="${0 eq appelOffreForm.ligneAppels.size()}">

                                        <tr class="list-materiel">
                                            <td>
                                                <form:select path="ligneAppels[0].materiel.id" cssClass="form-control input-sm" >
                                                    <form:options items="${materiels}" />
                                                </form:select>
                                            </td>
                                            <td>
                                                <form:input path="ligneAppels[0].prixUnitaire" cssClass="form-control input-sm" />

                                            </td>
                                            <td>
                                                <form:input path="ligneAppels[0].quantite" cssClass="form-control input-sm" />

                                            </td>

                                            <td class="row-align">
                                                <button type="button" id="removeMaterielButton"  class="btn btn-sm btn-default remove-materiel" >
                                                    <span class="glyphicon glyphicon-minus-sign"></span>
                                                </button>
                                            </td>
                                        </tr>

                                    </c:if>

                                </tbody>
                            </table>

                            <button type="button" id="addMaterielButton" class="btn btn-sm btn-default add-materiel">
                                <span class="glyphicon glyphicon-plus-sign"></span>
                                <spring:message code="action.ajouter" />
                            </button>
                        </div>
                    </fieldset>
                </div>
            </div>

            <div class="row">
                <div class="col-md-12">
                    <hr class="invisible"/>
                    <fieldset>
                        <legend><spring:message code="appelOffre.cautions" /></legend>
                        <div id="caution">
                            <table class="table table-bordered">
                                <thead>
                                    <tr class="btn-primary">
                                        <th><spring:message code="caution.referenceMarche" /></th>
                                        <th><spring:message code="caution.montantMarche" /></th>
                                        <th><spring:message code="caution.typeCaution" /></th>
                                        <th><spring:message code="caution.numero" /></th>
                                        <th><spring:message code="caution.banque" /></th>
                                        <th><spring:message code="caution.commercial" /></th>
                                        <th><spring:message code="caution.montant" /></th>
                                        <th><spring:message code="caution.dateDebut" /></th>
                                        <th><spring:message code="caution.dateFin" /></th>
                                        <th><spring:message code="action.effacer" /></th>
                                    </tr>
                                </thead>
                                <tbody id="caution">



                                    <c:if test="${0 le appelOffreForm.cautions.size()}">
                                        <c:forEach items="${appelOffreForm.cautions}" varStatus="status" begin="0">

                                            <tr class="list-caution">
                                                <td>
                                                    <spring:bind path="cautions[${status.index}].referenceMarche">
                                                        <form:input path="${status.expression}" cssClass="form-control input-sm" />
                                                        <form:errors path="${status.expression}" cssClass="text-danger"/>
                                                    </spring:bind>
                                                </td>
                                                <td>
                                                    <spring:bind path="cautions[${status.index}].montantMarche">
                                                        <form:input path="${status.expression}" cssClass="form-control input-sm" />
                                                        <form:errors path="${status.expression}" cssClass="text-danger"/>
                                                    </spring:bind>
                                                </td>
                                                <td>
                                                    <spring:bind path="cautions[${status.index}].typeCaution.id">
                                                        <form:select  path="${status.expression}" cssClass="form-control input-sm" >
                                                            <form:options items="${typeCautions}" />
                                                        </form:select>
                                                    </spring:bind>
                                                </td>
                                                <td>
                                                    <spring:bind path="cautions[${status.index}].numero">
                                                        <form:input path="${status.expression}" cssClass="form-control input-sm" />
                                                        <form:errors path="${status.expression}" cssClass="text-danger"/>
                                                    </spring:bind>
                                                </td>
                                                <td>
                                                    <spring:bind path="cautions[${status.index}].banque.id">
                                                        <form:select  path="${status.expression}" cssClass="form-control input-sm" >
                                                            <form:options items="${banques}" />
                                                        </form:select>
                                                    </spring:bind>
                                                </td>
                                                <td>
                                                    <spring:bind path="cautions[${status.index}].commercial.id">
                                                        <form:select  path="${status.expression}" cssClass="form-control input-sm" >
                                                            <form:options items="${commerciaux}" />
                                                        </form:select>
                                                    </spring:bind>
                                                </td>

                                                <td>
                                                    <spring:bind path="cautions[${status.index}].montant">
                                                        <form:input path="${status.expression}" cssClass="form-control input-sm" />
                                                        <form:errors path="${status.expression}" cssClass="text-danger"/>
                                                    </spring:bind>
                                                </td>
                                                <td>
                                                    <spring:bind path="cautions[${status.index}].dateDebut">
                                                        <form:input path="${status.expression}" cssClass="form-control input-sm" />
                                                        <form:errors path="${status.expression}" cssClass="text-danger"/>
                                                    </spring:bind>
                                                </td>
                                                <td>
                                                    <spring:bind path="cautions[${status.index}].dateFin">
                                                        <form:input path="${status.expression}" cssClass="form-control input-sm" />
                                                        <form:errors path="${status.expression}" cssClass="text-danger"/>
                                                    </spring:bind>
                                                </td>
                                                <td class="row-align">
                                                    <button type="button" id="removeCautionButton" class="btn btn-sm btn-default remove-caution" >
                                                        <span class="glyphicon glyphicon-minus-sign"></span>
                                                    </button>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </c:if>

                                    <c:if test="${0 eq appelOffreForm.cautions.size()}">
                                        <tr class="list-caution">
                                            <td>
                                                <form:input path="cautions[0].referenceMarche" cssClass="form-control input-sm" />
                                            </td>
                                            <td>
                                                <form:input path="cautions[0].montantMarche" cssClass="form-control input-sm" />
                                            </td>
                                            <td>
                                                <form:select path="cautions[0].typeCaution.id" cssClass="form-control input-sm" >
                                                    <form:options items="${typeCautions}" />
                                                </form:select>
                                            </td>
                                            <td>
                                                <form:input path="cautions[0].numero" cssClass="form-control input-sm" />
                                            </td>
                                            <td>
                                                <form:select path="cautions[0].banque.id" cssClass="form-control input-sm" >
                                                    <form:options items="${banques}" />
                                                </form:select>

                                            </td>
                                            <td>
                                                <form:select path="cautions[0].commercial.id" cssClass="form-control input-sm" >
                                                    <form:options items="${commerciaux}" />
                                                </form:select>

                                            </td>
                                            <td>
                                                <form:input path="cautions[0].montant" cssClass="form-control input-sm" />

                                            </td>
                                            <td>
                                                <form:input  path="cautions[0].dateDebut" cssClass="form-control input-sm" />

                                            </td>
                                            <td>
                                                <form:input  path="cautions[0].dateFin" cssClass="form-control input-sm" />

                                            </td>

                                            <td class="row-align">
                                                <button type="button" id="removeCautionButton" class="btn btn-sm btn-default remove-caution" >
                                                    <span class="glyphicon glyphicon-minus-sign"></span>
                                                </button>
                                            </td>
                                        </tr>
                                    </c:if>


                                </tbody>
                            </table>
                            <button type="button" id="addCautionButton" class="btn btn-sm btn-default add-caution">
                                <span class="glyphicon glyphicon-plus-sign"></span>
                                <spring:message code="action.ajouter" />
                            </button>
                        </div>
                    </fieldset>
                </div>
            </div>

            <div class="row">
                <div class="col-md-12">
                    <hr/>
                    <button type="submit" class="btn btn-sm btn-danger" >
                        <span class="glyphicon glyphicon-save"></span>
                        <spring:message code="action.enregistrer" />
                    </button>
                    <spring:url value="/appeloffre/" htmlEscape="true"
                                var="appeloffre_home" />
                    <a href="${appeloffre_home}" class="btn btn-sm btn-default">
                        <span class="glyphicon glyphicon-list"></span>
                        <spring:message code="appelOffre.liste" />
                    </a>
                </div>
            </div>
        </form:form>

        <script src="<c:url value="/resources/js/jquery.dynamiclist.min.js" />"></script>
        <script src="<c:url value="/resources/js/jquery-ui.js" />"></script>
        <script src="<c:url value="/resources/js/bootstrap-filestyle.js" />"></script>
        <script type="text/javascript">

            $(document).ready(function () {

                function setOnCautionDatePicker() {
                    var i = 0;
                    $(".list-caution").each(function () {
                        $(this).on("focus", "#cautions" + i + "\\.dateDebut", function () {
                            $(this).datepicker({
                                changeMonth: true,
                                changeYear: true,
                                dateFormat: "dd/mm/yy",
                                showButtonPanel: false
                            });
                            return false;
                        });

                        $(".list-caution").on("focus", "#cautions" + i + "\\.dateFin", function () {
                            $(this).datepicker({
                                changeMonth: true,
                                changeYear: true,
                                dateFormat: "dd/mm/yy",
                                showButtonPanel: false
                            });
                            return false;
                        });
                        i++;
                    });


                }

                function setCautionDatePicker(item) {

                    $(item).on("focus", "input[name$='dateFin']", function () {
                        $(this).removeClass("hasDatepicker").datepicker({
                            changeMonth: true,
                            changeYear: true,
                            dateFormat: "dd/mm/yy",
                            showButtonPanel: false
                        });
                        return false;
                    });

                    $(item).on("focus", "input[name$='dateDebut']", function () {
                        $(this).removeClass("hasDatepicker").datepicker({
                            changeMonth: true,
                            changeYear: true,
                            dateFormat: "dd/mm/yy",
                            showButtonPanel: false
                        });
                        return false;
                    });

                }

                $("#materiel").dynamiclist({
                    itemClass: "list-materiel",
                    addClass: "add-materiel",
                    removeClass: "remove-materiel"
                });

                $("#caution").dynamiclist({
                    itemClass: "list-caution",
                    addClass: "add-caution",
                    removeClass: "remove-caution",
                    withEvents: true,
                    addCallbackFn: setCautionDatePicker

                });

                $("#dateDepot").datepicker({
                    changeMonth: true,
                    changeYear: true,
                    dateFormat: "dd/mm/yy",
                    showButtonPanel: false
                });

                setOnCautionDatePicker();

            });

        </script>


    </tiles:putAttribute>
</tiles:insertDefinition>