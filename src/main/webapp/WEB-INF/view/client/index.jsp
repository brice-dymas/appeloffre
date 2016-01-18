<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<tiles:insertDefinition name="layout">
    <tiles:putAttribute name="body">
        <div class="row">
            <div class="col-lg-2"></div>
            <div class="col-lg-10">


                <div class="row">

                    <div class="col-lg-12">
                        <h3>Liste des Clients</h3>
                        <hr/>
                    </div>
                </div>
                <div class="row">

                    <div class="col-lg-12">
                        <table class="table table-bordered" align="center">
                            <thead>
                                <tr>
                                    <th>
                                        #
                                    </th>
                                    <th>
                                        Code
                                    </th>

                                    <th>
                                        Nom(s) et Prénom(s)
                                    </th>
                                    <th>
                                        Adresse
                                    </th>
                                    <th>
                                        Contact
                                    </th>
                                    <th>
                                        Action
                                    </th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${clients}" var="client">
                                    <tr>
                                        <td>
                                            ${client.id}
                                        </td>
                                        <td>
                                            ${client.matricule}
                                        </td>
                                        <td>
                                            ${client.nom}
                                        </td>
                                        <td>
                                            ${client.adresse}
                                        </td>
                                        <td>
                                            ${client.telephone}
                                        </td>
                                        <td>
                                            <spring:url value="/client/${client.id}/edit" htmlEscape="true" var="client_edit" />
                                            <a href="${client_edit}" >
                                                 <span class="glyphicon glyphicon-edit"></span> Éditer</a>
                                                 &nbsp;&nbsp;
                                            <spring:url value="/client/${client.id}/show" htmlEscape="true" var="client_show" />
                                            <a href="${client_show}">
                                                 <span class="glyphicon glyphicon-open">Détails</span>
                                            </a>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                        <hr/>
                    </div>
                </div>
                <div class="row">
                    <div class="col-lg-12">
                        <spring:url value="/client/new" htmlEscape="true" var="client_new" />
                        <a href="${client_new}" class="btn btn-default">
                            <span class="glyphicon glyphicon-new-window"></span>
                            Nouveau
                        </a>
                    </div>
                </div>
            </div>
        </div>
    </tiles:putAttribute>
</tiles:insertDefinition>