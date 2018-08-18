<%--
    Document   : show
    Created on : Dec 10, 2014, 9:48:58 AM
    Author     : sando
--%>
<%--
    Document   : show
    Updated on : Aug 16, 2018, 6:55:58 PM
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
                    <spring:message code="cautiondouane.afficher" />
                </h4>
                <c:if test="${cautiondouane.deleted}" >
                    <div class="text-danger">
                        <spring:message code="element.desactive" />
                    </div>
                </c:if>
                <hr/>
            </div>
        </div>
        <div class="row">
            <div class="col-md-6 col-md-offset-3" id="table_show">
                <table class="table table-bordered">
                    <tbody>
                        <tr>
                            <th><spring:message code="cautiondouane.numero" /></th>
                            <td>${cautiondouane.numero}</td>
                        </tr>
                        <tr>
                            <th><spring:message code="cautiondouane.libelle" /></th>
                            <td>${cautiondouane.libelle}</td>
                        </tr>
                        <tr>
                            <th><spring:message code="cautiondouane.montant" /></th>
                            <td><fmt:formatNumber value="${cautiondouane.montant}" pattern="#,##0" /></td>
                        </tr>
                        <tr>
                            <th><spring:message code="cautiondouane.dateDebut" /></th>
                            <td><fmt:formatDate value="${cautiondouane.dateDebut}" pattern="dd/MM/yyyy" /></td>
                        </tr>
                        <tr>
                            <th><spring:message code="cautiondouane.dateFin" /></th>
                                <c:if test="${cautiondouane.dateFin le todayDate}">
                                <td  class="text-danger">
                                    <fmt:formatDate type="date" value="${cautiondouane.dateFin}" pattern="dd/MM/yyyy" />
                                </td>
                            </c:if>
                            <c:if test="${cautiondouane.dateFin gt todayDate}">
                                <td><fmt:formatDate type="date" value="${cautiondouane.dateFin}" pattern="dd/MM/yyyy" /> </td>
                            </c:if>
                        </tr>
                        <tr>
                            <th><spring:message code="cautiondouane.divers" /></th>
                            <td>${cautiondouane.divers}</td>
                        </tr>
                        <tr>
                            <th><spring:message code="cautiondouane.banque" /></th>
                            <td>${cautiondouane.banque.libelle}</td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>

        <div class="row">
            <div class="col-md-12 text-center">
                <hr/>
                <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#myModal">
                    <span class="glyphicon glyphicon-book"></span>
                    <spring:message code="action.document" />
                </button>&nbsp;&nbsp;
                <spring:url value="/cautiondouane/" var="cautiondouane_home"/>
                <a href="${cautiondouane_home}" class="btn btn-primary  btn-sm">
                    <span class="glyphicon glyphicon-th-list"></span>
                    <spring:message code="cautiondouane.liste" />
                </a>
            </div>
        </div>


        <!-- Modal -->
        <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title" id="myModalLabel"><spring:message code="cautiondouane.listeAttachedFiles" /></h4>
                    </div>
                    <div class="modal-body">
                        <!-- Start Content -->
                        <div class="container-fluid">
                            <spring:url
                                value="/file/${cautiondouane.id}/upload.json"
                                var="file_upload"
                                htmlEscape="true" />

                            <div class="row">
                                <div class="col-md-2">

                                    <input id="fileupload" class="filestyle" type="file" name="files[]"
                                           data-badge="false" data-iconName="glyphicon glyphicon-inbox"
                                           data-input="false" data-size="sm" data-buttonText= "Upload"
                                           data-buttonName="btn-primary"
                                           data-url="${file_upload}?${_csrf.parameterName}=${_csrf.token}">
                                </div>
                                <div class="col-md-1 text-right">
                                    &nbsp;<span class="loader"></span>
                                </div>

                            </div>
                        </div>
                        <hr/>
                        <div id="alert"></div>
                        <table id="uploaded-files" class="table table-bordered">
                            <thead>
                                <tr>
                                    <th class="text-center">File</th>
                                    <th class="text-center">Download</th>
                                    <th class="text-center">Remove</th>

                                </tr>
                            </thead>
                            <c:forEach var="file" items="${cautiondouane.files}" varStatus="loop">
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
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal"><spring:message code="action.fermer" /></button>
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