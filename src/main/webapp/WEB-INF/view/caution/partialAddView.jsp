<%-- 
    Document   : partialAddView
    Created on : Feb 3, 2015, 9:16:21 PM
    Author     : gervais
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<tr id="caution${ligneNumber}">
    <td>
        <form:select path="appelOffreForm.cautions[${ligneNumber}].typeCaution.id" cssClass="form-control input-sm" >
            <form:options items="${typeCautions}" />
        </form:select>
    </td>
    <td>
        <form:input path="appelOffreForm.cautions[${ligneNumber}].numero" cssClass="form-control input-sm" />

    </td>
    <td>
        <form:input path="appelOffreForm.cautions[${ligneNumber}].banque" cssClass="form-control input-sm" />

    </td>
    <td>
        <form:input path="appelOffreForm.cautions[${ligneNumber}].montant" cssClass="form-control input-sm" />

    </td>
    <td>
        <form:input path="appelOffreForm.cautions[${ligneNumber}].dateDebut" cssClass="form-control input-sm" />

    </td>
    <td>
        <form:input path="appelOffreForm.cautions[${ligneNumber}].dateFin" cssClass="form-control input-sm" />

    </td>

    <td class="row-align">
        <input type="checkbox" class="form-control" id="linecaution${ligneNumber}" onclick="checkLineStatuts('caution',${ligneNumber});" />
    </td>
</tr>