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

<tiles:insertDefinition name="layout">
    <tiles:putAttribute name="body">
        <div class="row">
            <div class="col-lg-2"></div>
            <div class="col-lg-10 center-block">

                <h4 class="text-uppercase">Détail sur l'employé </h4>
                <hr/>
            </div>
        </div>
        <div class="row">
            <div class="col-lg-2"></div>
            <div class="col-lg-8">

                <table class="table table-bordered table-striped">
                    <tbody>
                        <tr>
                            <th> ID :</th>
                            <td> ${employe.id}</td>
                        </tr>
                        <tr>
                            <th> Matricule :</th>
                            <td> ${employe.matricule} </td>
                        </tr>
                        <tr>

                            <th>Noms et Prénoms : </th>
                            <td>${employe.nom}</td>
                        </tr>
                        <tr>
                            <th> Adresse : </th>
                            <td>${employe.adresse}</td>
                        </tr>
                        <tr>
                            <th> Téléphone : </th>
                            <td>${employe.telephone} </td>
                        </tr>
                        <tr>
                            <th> E-Mail : </th>
                            <td>${employe.email}  </td>
                        </tr>
                        <tr>
                            <th>  Fonction : </th>
                            <td>${employe.fonction.libelle}</td> 
                        </tr>

                    </tbody>
                </table>

            </div>
            <div class="col-lg-2"></div>
        </div>

        <div class="row">
            <div class="col-lg-2"></div>
            <div class="col-lg-10 center-block">

                <h4 class="text-uppercase">Information de sécurité </h4>
                <hr/>
            </div>
        </div>
        <div class="row">
            <div class="col-lg-2"></div>
            <div class="col-lg-8">

                <table class="table table-bordered table-striped">
                    <tbody>
                        <tr>
                            <th>Nom d'utilisateur :</th>
                            <td> ${employe.login}</td>
                        </tr>
                        <tr>
                            <th>Mot de Passe :</th>
                            <td> ${employe.password} </td>
                        </tr>
                        <tr>



                    </tbody>
                </table>

            </div>
            <div class="col-lg-2"></div>
        </div>

        <div class="row">
            <div class="col-lg-2"></div>
            <div class="col-lg-10">
                <spring:url value="/employe/${employe.id}/edit" var="employe_edit"/>
                <spring:url value="/employe/delete" var="employe_delete"/>
                <spring:url value="/employe/" htmlEscape="true" var="employe_home" />

                <form:form method="post" commandName="employe" action="${employe_delete}">
                    <form:hidden path="id"/>
                    <a href="${employe_home}" class="btn btn-default">
                        <span class="glyphicon glyphicon-list"></span>
                        Liste
                    </a>
<a href="${employe_edit}" class="btn btn-default">
    <span class="glyphicon glyphicon-edit"></span>
    Modifier</a>
    <button class="btn btn-danger">
        <span class="glyphicon glyphicon-remove"></span> Supprimer
    </button>
                    
                </form:form>
                
                
            </div>
                
                <div class="row">
                    <div class="col-lg-12">
                        
                        <hr/>
                    </div>
                </div>
                
        </div>


    </tiles:putAttribute>
</tiles:insertDefinition>