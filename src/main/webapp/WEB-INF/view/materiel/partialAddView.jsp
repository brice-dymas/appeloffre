<%-- 
    Document   : partialAddView
    Created on : Feb 3, 2015, 9:16:21 PM
    Author     : gervais
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<tr id="materiel${ligneNumber}">
    <td>
         <form:select path="appelOffreForm.ligneAppels[${ligneNumber}].materiel.id" cssClass="form-control input-sm" >
                            <form:options items="${materiels}" />
                        </form:select>
    </td>
    <td>
        <form:input path="appelOffreForm.ligneAppels[${ligneNumber}].prixUnitaire" cssClass="form-control input-sm" />
        
    </td>
    <td>
        <form:input path="appelOffreForm.ligneAppels[${ligneNumber}].quantite" cssClass="form-control input-sm" />
        
    </td>
     
    <td class="row-align">
         <button type="button" id="removeMaterielButton"  class="btn btn-sm btn-default remove-materiel" >
            <span class="glyphicon glyphicon-minus-sign"></span> Retirer
        </button>
    </td>
</tr>