<%--
    Document   : mainPage
    Created on : 4 juil. 2015, 11:55:10
    Author     : sando
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<tiles:insertDefinition name="layout">
    <tiles:putAttribute name="body">
        <div class="row">
            <div class="col-md-4">

                <h3>
                    <spring:message code="caution.repartition" />
                </h3>
            </div>
            <div class="col-md-8">
                <h3>
                    <div class="dropdown pull-right ">
                        <button class="btn btn-primary dropdown-toggle" type="button" id="dropdownMenu1" data-toggle="dropdown" aria-expanded="true">
                            ${year}&nbsp;
                            <span class="caret"></span>
                        </button>
                        <ul class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu1">
                            <c:forEach var="i" begin="2015" end="2035">
                                <li role="presentation"><a role="menuitem" tabindex="-1" href="?year=${i}">${i}</a></li>
                                </c:forEach>
                        </ul>
                    </div>
                </h3>

            </div>
            <div class="col-md-4">

            </div>

        </div>
        <div class="row">
            <div class="col-lg-12">
                <hr style="margin-top: 0px;"/>
            </div>
        </div>
        <div class="row">

            
                <c:choose>
                    <c:when test="${ not empty results}">
                        <div class="col-lg-4 col-lg-offset-2">
                        <div id="chartdiv" style="height:400px;width:800px; "></div>
                        </div>
                    </c:when>    
                    <c:otherwise>
                        <div class="col-lg-8 col-lg-offset-2" >
                        <div class="jumbotron" style="padding: 30px;">
                            <h1>Sorry!</h1>
                            <hr>
                            <h3>There is no data in ${year}</h3>
                        </div>
                        <div>
                    </c:otherwise>
                </c:choose>


            </div>

        </div>




<!--[if lt IE 9]><script language="javascript" type="text/javascript" src="<c:url value="/resources/js/excanvas.js" />"></script><![endif]-->
        <script src="<c:url value="/resources/js/jquery.jqplot.min.js" />"></script>
        <script src="<c:url value="/resources/js/plugins/jqplot.dateAxisRenderer.min.js" />"></script>

        <script>
            $(function () {

                $.jqplot.config.enablePlugins = true;
                var element = {};
                var year = "";

            <c:forEach items="${results}" var="result">
                year = ${year} + "-" +${result[2]} + "-" + "1";
                if (element['${result[1]}']) {
                    element['${result[1]}'].push([year,${result[0]}]);

                } else {
                    element['${result[1]}'] = [[year,${result[0]}]];
                    console.log(year);
                }
            </c:forEach>
                console.log(element);
                console.log("Hello");

                //$.jqplot('chartdiv',  [[[1, 2],[3,5.12],[5,13.1],[7,33.6],[9,85.9],[11,219.9]]]); 

                var lines = [];
                var labels = [];
                Object.keys(element).forEach(function (key) {
                    labels.push(key);
                    lines.push(element[key]);
                    //$.jqplot('chartdiv', [element[key]]);
                });
                console.log(lines);
            <c:if test="${not empty results}" >
                var plot3 = $.jqplot('chartdiv', lines,
                        {
                            title: '<spring:message code="caution.repartition" /> ${year}',
                                                // Series options are specified as an array of objects, one object
                                                // for each series.
                                                axes: {
                                                    xaxis: {
                                                        renderer: $.jqplot.DateAxisRenderer,
                                                        tickOptions: {formatString: '%b'},
                                                        min: 'January 1, ' +${year},
                                                        tickInterval: '1 month'
                                                    }
                                                },
                                                legend: {
                                                    show: true,
                                                    renderer: $.jqplot.EnhancedLegendRenderer,
                                                    rendererOptions: {
                                                        numberRows: 1
                                                    },
                                                    placement: 'outsideGrid',
                                                    labels: labels,
                                                    location: 'e'
                                                }
                                            }
                                    );
            </c:if>

                                });
        </script>
    </tiles:putAttribute>
</tiles:insertDefinition>