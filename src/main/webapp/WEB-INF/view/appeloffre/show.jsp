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
                    <spring:message code="appelOffre.afficher" />
                </h4>
                <div class="text text-danger">
                    <h4>
                        <c:if test="${appelOffre.deleted}" >
                            <spring:message code="appelOffre.disabled" />
                        </c:if>
                    </h4>
                </div>
                <hr/>
            </div>
        </div>
        <div class="row">
            <div class="col-xs-offset-1" id="table_show">
                <!--<div class="col-md-6 col-md-offset-3" id="table_show">-->
                <table class="table table-bordered">
                    <tbody>
                        <tr>
                            <th ><spring:message code="appelOffre.numero" /></th>
                            <td>${appelOffre.numero}</td>
                            <th><spring:message code="appelOffre.intitule" /></th>
                            <td>${appelOffre.intitule}</td>
                            <th><spring:message code="appelOffre.filiale" /></th>
                            <td>${appelOffre.filiale.nom}</td>
                        </tr>
                        <tr>
                            <th ><spring:message code="appelOffre.numeroAffaire" /></th>
                            <td>${appelOffre.numeroAffaire}</td>
                            <th><spring:message code="appelOffre.numeroChrono" /></th>
                            <td>${appelOffre.numeroChrono}</td>
                        </tr>

                        <tr>
                            <th><spring:message code="appelOffre.maitreDouvrage" /></th>
                            <td>${appelOffre.maitreDouvrage}</td>
                            <th><spring:message code="appelOffre.datedepot" /></th>
                            <td><fmt:formatDate type="date" value="${appelOffre.dateDepot}" pattern="dd/MM/yyyy" /></td>
                            <th><spring:message code="appelOffre.delai" /></th>
                            <td>${appelOffre.delaiDeValidite}</td>
                        </tr>
                        <tr class="text-danger">
                            <th><spring:message code="appelOffre.dateModification" /></th>
                            <td><fmt:formatDate value="${appelOffre.dateModification}" pattern="dd/MM/yyyy" /></td>
                            <th><spring:message code="modified.byNom" /></th>
                            <td>${user.user.nom}</td>
                            <th><spring:message code="modified.byUsername" /></th>
                            <td>${user.user.username}</td>
                        </tr>
                    </tbody>
                </table>

            </div>
        </div>


        <div class="row">
            <div class="col-md-12">
                <fieldset>
                    <legend><spring:message code="appelOffre.listeMateriel" /></legend>
                    <table class="table table-bordered">
                        <thead>
                            <tr class="btn-primary">
                                <th><spring:message code="ligneMateriel.materiel" /></th>
                                <th><spring:message code="ligneMateriel.prixUnitaire" /></th>
                                <th><spring:message code="ligneMateriel.quantite" /></th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="ligneAppel" items="${ligneAppels}">
                                <tr>
                                    <td>${ligneAppel.materiel.nom}</td>
                                    <td><fmt:formatNumber value="${ligneAppel.prixUnitaire}" pattern="#,##0" /> </td>
                                    <td>${ligneAppel.quantite} </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </fieldset>
            </div>
        </div>
        <div class="row">
            <div class="col-md-12">
                <fieldset>
                    <legend><spring:message code="appelOffre.cautions" /></legend>
                    <table class="table table-bordered">
                        <thead>
                            <tr class="btn-primary">
                                <th><spring:message code="caution.referenceMarche" /></th>
                                <th><spring:message code="caution.montantMarche" /></th>
                                <th><spring:message code="caution.commercial" /></th>
                                <th><spring:message code="caution.numero" /></th>
                                <th><spring:message code="caution.typeCaution" /></th>
                                <th><spring:message code="caution.banque" /></th>
                                <th><spring:message code="caution.montant" /></th>
                                <th><spring:message code="caution.dateDebut" /></th>
                                <th><spring:message code="caution.dateFin" /></th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="caution" items="${cautions}">
                                <c:if test="${caution.dateFin le todayDate}">
                                    <tr class="text-danger" >
                                        <td>${caution.referenceMarche} </td>
                                        <td><fmt:formatNumber value="${caution.montantMarche}" pattern="#,##0" /></td>
                                        <td>${caution.commercial.user.nom} </td>
                                        <td>${caution.numero} </td>
                                        <td>${caution.typeCaution.nom}</td>
                                        <td>${caution.banque.libelle} </td>
                                        <td><fmt:formatNumber value="${caution.montant}" pattern="#,##0" /></td>
                                        <td><fmt:formatDate type="date" value="${caution.dateDebut}" pattern="dd/MM/yyyy" /> </td>
                                        <td><fmt:formatDate type="date" value="${caution.dateFin}" pattern="dd/MM/yyyy" /> </td>
                                    </tr>
                                </c:if>
                                <c:if test="${caution.dateFin gt todayDate}">
                                    <tr>
                                        <td>${caution.referenceMarche} </td>
                                        <td> <fmt:formatNumber value="${caution.montantMarche}" pattern="#,##0" /> </td>
                                        <td>${caution.commercial.user.nom} </td>
                                        <td>${caution.numero} </td>
                                        <td>${caution.typeCaution.nom}</td>
                                        <td>${caution.banque.libelle} </td>
                                        <td><fmt:formatNumber value="${caution.montant}" pattern="#,##0" /></td>
                                        <td><fmt:formatDate type="date" value="${caution.dateDebut}" pattern="dd/MM/yyyy" /> </td>
                                        <td><fmt:formatDate type="date" value="${caution.dateFin}" pattern="dd/MM/yyyy" /> </td>
                                    </tr>
                                </c:if>

                            </c:forEach>
                        </tbody>
                    </table>
                </fieldset>
            </div>
        </div>

        <div class="row">
            <div class="col-md-12 text-center">

                <hr/>

                <spring:url value="/appeloffre/delete" var="appeloffre_delete"/>
                <form:form method="post" commandName="appelOffre" action="${appeloffre_delete}">
                    <spring:url value="/appeloffre/" var="appeloffre_home"/>
                    <a href="${appeloffre_home}" class="btn btn-primary btn-primary">
                        <span class="glyphicon glyphicon-list"></span>
                        <spring:message code="appelOffre.liste" />
                    </a>
                    <sec:authorize access="hasRole('ROLE_ADMIN')" >
                        <form:hidden path="id"/>
                        <spring:url value="/appeloffre/${appelOffre.id}/edit" var="appeloffre_edit"/>
                        <a href="${appeloffre_edit}" class="btn btn-default  btn-warning">
                            <span class="glyphicon glyphicon-edit"></span>
                            <spring:message code="action.modifier" />
                        </a>
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

                        <c:if test="${appelOffre.deleted}" >
                            <button type="submit" class="btn btn-default  btn-success">
                                <span class="glyphicon glyphicon-ok"></span>
                                <spring:message code="action.activer" />
                            </button>
                        </c:if>
                        <c:if test="${not appelOffre.deleted}" >
                            <button type="submit" class="btn btn-default  btn-danger">
                                <span class="glyphicon glyphicon-remove-sign"></span>
                                <spring:message code="action.effacer" />
                            </button>
                        </c:if>
                    </sec:authorize>
                    <div class="dropdown" style="display: inline-block !important">
                        <button class="btn btn-default dropdown-toogle btn-success" id="dropdown-user" data-toggle="dropdown">
                            <i class="glyphicon glyphicon-print"></i>
                            <spring:message code="print.message" />
                            <i class="caret"></i>
                        </button>
                        <ul class="dropdown-menu" role="menu" aria-labeledby="dropdown-user">
                            <li>
                                <spring:url htmlEscape="true" var="xls_print" value="/appeloffre/${appelOffre.id}/show.xls" />
                                <a href="${xls_print}" class="btn btn-success" >
                                    <i class="glyphicon glyphicon-calendar"> </i>
                                    <spring:message code="print.xls" />
                                </a>
                            </li>
                            <li>
                                <spring:url htmlEscape="true" var="print_pdf" value="/appeloffre/${appelOffre.id}/print-pdf" />
                                <a href="${print_pdf}" class="btn btn-success" target="_blank" >
                                    <i class="glyphicon glyphicon-calendar"> </i>
                                    <spring:message code="print.pdf" />
                                </a>
                            </li>
                        </ul>
                    </div>
                    <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#myModal">
                        <span class="glyphicon glyphicon-paperclip"></span>
                        <spring:message code="action.document" />
                    </button>
                </form:form>
                <!-- Button trigger modal -->


                <!-- Modal -->
                <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
                    <div class="modal-dialog" role="document">
                        <div class="modal-content">
                            <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                                <h4 class="modal-title" id="myModalLabel"><spring:message code="appelOffre.listeAttachedFiles" /></h4>
                            </div>
                            <div class="modal-body">
                                <!-- Start Content -->
                                <div class="container-fluid">


                                    <spring:url
                                        value="/file/${appelOffre.id}/upload.json"
                                        var="file_upload"
                                        htmlEscape="true" />

                                    <div class="row">
                                        <div class="col-md-2">
                                            <c:if test="${nbFile le 7 }">
                                                <input id="fileupload" class="filestyle" type="file" name="files[]" data-badge="false" data-iconName="glyphicon glyphicon-inbox" data-input="false" data-size="sm" data-buttonText= "Upload" data-buttonName="btn-primary" data-url="${file_upload}?${_csrf.parameterName}=${_csrf.token}">
                                            </c:if>
                                        </div>
                                        <div class="col-md-1 text-right">
                                            &nbsp;<span class="loader"></span>
                                        </div>

                                    </div>
                                </div>
                                <hr/>
                                <div id="alert"></div>
                                <!--                                <div class="progress">
                                                                    <div class="progress-bar" style="width: 0%;">
                                                                        <span class="sr-only">60% Complete</span>
                                                                    </div>
                                                                </div>-->

                                <table id="uploaded-files" class="table table-bordered">
                                    <thead>
                                        <tr>
                                            <th class="text-center">File</th>
                                            <th class="text-center">Download</th>
                                            <th class="text-center">Remove</th>

                                        </tr>
                                    </thead>
                                    <c:forEach var="file" items="${appelOffre.files}" varStatus="loop">
                                        <tr >
                                            <td>
                                                <a target="_blank" href=<c:url value="/resources/documents/"/>${file}>
                                                    ${file}
                                                </a>
                                            </td>
                                            <td>
                                                <a target="_blank" href=<c:url value="/resources/documents/"/>${file}>
                                                    <span class="glyphicon glyphicon-download"></span>
                                                </a>
                                            </td>
                                            <td>
                                                <a id="remove${loop.index}"  href='<c:url value="/file/remove/"/>${file}.json'>
                                                    <span class="glyphicon glyphicon-remove-circle"></span>
                                                </a>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </table>


                                <!-- End of content -->
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-default" data-dismiss="modal"><spring:message code="action.fermer" /></button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <script src="<c:url value="/resources/js/jquery-ui.min.js" />"></script>
        <script src="<c:url value="/resources/js/jquery.iframe-transport.js" />"></script>
        <script src="<c:url value="/resources/js/jquery.fileupload.js" />"></script>
        <script src="<c:url value="/resources/js/bootstrap-filestyle.js" />"></script>
        <script src="<c:url value="/resources/js/jquery.loader.min.js" />"></script>
        <script>
            $(function () {


                $('#fileupload').fileupload({
                    dataType: 'json',
                    start: function (e) {
                        $('.loader').loader('show');

                    },
                    done: function (e, data) {

                        $("#uploaded-files tr:has(td)").remove();
                        $.each(data.result, function (index, file) {


                            $("#uploaded-files").append(
                                    $('<tr/>')
                                    .append($('<td/>').html('<a target="_blank" href=<c:url value="/resources/documents/"/>' + file.fileName + '>' + file.fileName + '</a>'))
                                    .append($('<td/>').html('<a target="_blank" href=<c:url value="/resources/documents/"/>' + file.fileName + '><span class="glyphicon glyphicon-download"></span></a>'))
                                    .append($('<td/>').html('<a id="remove' + index + '" href=<c:url value="/file/remove/"/>' + file.fileName + '.json><span class="glyphicon glyphicon-remove-circle"></span></a>'))
                                    );//end $("#uploaded-files").append()

                            $('#remove' + index).on("click", {url: $('#remove' + index).attr("href")}, removeFile);
                        });
                        $('.loader').loader('hide');
                        $('#alert').html('<div id="alertSuccess" class="alert alert-success" role="alert">Upload with success</div>');
                        $('#alertSuccess').fadeOut(3000);
                    },
                    progressall: function (e, data) {

                        var progress = parseInt(data.loaded / data.total * 100, 10);
                        console.log(progress);
                        $('.progress .progress-bar').css(
                                'width',
                                progress + '%'
                                );

                    },
                    dropZone: $('#dropzone')
                });


                $('[id^="remove"]').each(function (index, element) {
                    console.log(index);
                    $(element).on("click", {url: $(element).attr("href")}, removeFile);
                });



                function removeFile(e) {
                    e.preventDefault();
                    $('.loader').loader('show');
                    console.log(e.data.url);
                    $.getJSON(e.data.url, function (data) {
                        console.log(data);
                        $("#uploaded-files tr:has(td)").remove();
                        $.each(data, function (index, file) {


                            $("#uploaded-files").append(
                                    $('<tr/>')
                                    .append($('<td/>').html('<a target="_blank" href=<c:url value="/resources/documents/"/>' + file.fileName + '>' + file.fileName + '</a>'))
                                    .append($('<td/>').html('<a target="_blank" href=<c:url value="/resources/documents/"/>' + file.fileName + '><span class="glyphicon glyphicon-download"></span></a>'))
                                    .append($('<td/>').html('<a id="remove' + index + '" href=<c:url value="/file/remove/"/>' + file.fileName + '.json><span class="glyphicon glyphicon-remove-circle"></span></a>')));//end $("#uploaded-files").append()
                            $('#remove' + index).on("click", {url: $('#remove' + index).attr("href")}, removeFile);
                        });
                        $('.loader').loader('hide');
                        $('#alert').html('<div id="removeSuccess" class="alert alert-success" role="alert">Remove with success</div>');
                        $('#removeSuccess').fadeOut(3000);
                    });
                    //return false;

                }


            });
        </script>
    </tiles:putAttribute>
</tiles:insertDefinition>
