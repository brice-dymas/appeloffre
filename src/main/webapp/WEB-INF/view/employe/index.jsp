<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<tiles:insertDefinition name="layout">
    <tiles:putAttribute name="body">
        
                <div class="row">

                    <div class="col-lg-12">
                        <h3>Liste des employés</h3>
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
                                        Matricule
                                    </th>

                                    <th>
                                        Noms et Prénoms
                                    </th>
                                    <th>
                                        Adresse
                                    </th>
                                    <th>
                                        telephone
                                    </th>
                                    <th>
                                        Fonction
                                    </th>
                                    <th>
                                        Action
                                    </th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${employes}" var="employe">
                                    <tr>
                                        <td>
                                            ${employe.id}
                                        </td>
                                        <td>
                                            ${employe.matricule}
                                        </td>
                                        <td>
                                            ${employe.nom}
                                        </td>
                                        <td>
                                            ${employe.adresse}
                                        </td>
                                        <td>
                                            ${employe.telephone}
                                        </td>
                                        <td>
                                            ${employe.fonction.libelle}
                                        </td>
                                        <td>
                                            <spring:url value="/employe/${employe.id}/edit" htmlEscape="true" var="employe_edit" />
                                            <a href="${employe_edit}" >
                                                 <span class="glyphicon glyphicon-edit"></span> Éditer</a>
                                                 &nbsp;&nbsp;
                                            <spring:url value="/employe/${employe.id}/show" htmlEscape="true" var="employe_show" />
                                            <a href="${employe_show}">
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
                        <spring:url value="/employe/new" htmlEscape="true" var="employe_new" />
                        <a href="${employe_new}" class="btn btn-default">
                            <span class="glyphicon glyphicon-new-window"></span>
                            Nouveau
                        </a>
                    </div>
                </div>
            </div>
        </div>
    </tiles:putAttribute>
</tiles:insertDefinition>