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

                <h4 class="text-uppercase">Informations sur le client </h4>
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
                            <td> ${client.id}</td>
                        </tr>
                        <tr>
                            <th> Code Client :</th>
                            <td> ${client.matricule} </td>
                        </tr>
                        <tr>

                            <th>Nom(s) et Prénom(s) : </th>
                            <td>${client.nom}</td>
                        </tr>
                        <tr>
                            <th> Adresse / Contact : </th>
                            <td>${client.adresse}</td>
                        </tr>
                        <tr>
                            <th> Téléphone : </th>
                            <td>${client.telephone} </td>
                        </tr>
                        <tr>
                            <th> E-Mail : </th>
                            <td>${client.email}  </td>
                        </tr>
                       
                    </tbody>
                </table>

            </div>
            <div class="col-lg-2"></div>
        </div>

        
        
        <div class="row">
            <div class="col-lg-2"></div>
            <div class="col-lg-10">
                <spring:url value="/client/${client.id}/edit" var="client_edit"/>
                <spring:url value="/client/delete" var="client_delete"/>
                <spring:url value="/client/" htmlEscape="true" var="client_home" />

                <form:form method="post" commandName="client" action="${client_delete}">
                    <form:hidden path="id"/>
                    <a href="${client_home}" class="btn btn-default">
                        <span class="glyphicon glyphicon-list"></span>
                        Liste
                    </a>
					<a href="${client_edit}" class="btn btn-default">
					    <span class="glyphicon glyphicon-edit"></span>
					    Modifier
				    </a>
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