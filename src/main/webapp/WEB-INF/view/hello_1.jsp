<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>Spring MVC - Upload File</title>
        <script src="http://code.jquery.com/jquery-1.9.1.js"></script>
        <script src="http://malsup.github.com/jquery.form.js"></script>
        <script>
            //using jquery.form.js
            function uploadJqueryForm() {
                $('#result').html('');

                $("#form2").ajaxForm({
                    success: function (data) {
                        $('#result').html(data);
                    },
                    dataType: "text"
                }).submit();
            }

            //using FormData() object
            function uploadFormData() {
                $('#result').html('');

                var oMyForm = new FormData();
                oMyForm.append("file", file2.files[0]);

                $.ajax({
                    url: "<c:url value='/upload/upload'/>?${_csrf.parameterName}=${_csrf.token}",
                    data: oMyForm,
                    dataType: 'text',
                    processData: false,
                    contentType: false,
                    type: 'POST',
                    success: function (data) {
                        $('#result').html(data);
                    }
                });
            }
        </script>
    <body>
        <h1>SpringMVC - File Upload with/without Ajax</h1> 

        <!--  Form 1 -->
        <i>Uploading File Without Ajax</i><br/>
        <form id="form1" method="post" action="<c:url value='/upload/upload'/>?${_csrf.parameterName}=${_csrf.token}" enctype="multipart/form-data">

            <!-- File input -->    
            <input name="file" id="file" type="file" /><br/>

            <input type="submit" value="Upload" />
        </form>
        <hr/>
        <!--  Form 2 -->
        <i>Uploading File With Ajax</i><br/>
        <form id="form2" method="post" action="<c:url value='/upload/upload'/>?${_csrf.parameterName}=${_csrf.token}" enctype="multipart/form-data">
            <!-- File input -->    
            <input name="file2" id="file2" type="file" /><br/>
        </form>

        <button value="Submit" onclick="uploadJqueryForm()" >Upload</button><i>Using JQuery Form Plugin</i><br/>
        <button value="Submit" onclick="uploadFormData()" >Upload</button><i>Using FormData Object</i>

        <div id="result"></div>
    </body>
</html>