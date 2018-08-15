<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<c:set var="url" value="${pageContext.request.requestURI}" />

<div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">

    <!--    Partenaires-->
    <sec:authorize access="hasRole('ROLE_ADMIN')" >
        <div class="panel panel-info">
            <div class="panel-heading" role="tab" id="administration">
                <h4 class="panel-title">
                    <a class="collapsed" data-toggle="collapse" data-parent="#accordion" href="#collapseAdministration" aria-expanded="true" aria-controls="collapseAdministration">
                        <spring:message code="menu.administration" />
                    </a>
                </h4>
            </div>
            <div id="collapseAdministration"  role="tabpanel" aria-labelledby="administration"
                 <c:choose>
                     <c:when test="${fn:containsIgnoreCase(url, 'materiel') || fn:containsIgnoreCase(url, 'filiale') || fn:containsIgnoreCase(url, 'typecaution')
                                     || fn:containsIgnoreCase(url, 'typemateriel') || fn:containsIgnoreCase(url, 'banque')|| fn:containsIgnoreCase(url, 'user') }">
                             class="panel-collapse collapse in"
                     </c:when>
                     <c:otherwise>
                         class="panel-collapse collapse"
                     </c:otherwise>
                 </c:choose>
                 >
                <div class="panel-body">
                    <ul class="list-unstyled">
                        <li>
                            <a
                                <c:if test="${fn:containsIgnoreCase(url, 'materiel')}">
                                    class="list-group-item active"
                                </c:if>
                                href="<spring:url  value="/materiel/" />">
                                <spring:message code="menu.materiel" />
                            </a>
                        </li>
                        <li>
                            <a
                                <c:if test="${fn:containsIgnoreCase(url, 'filiale')}">
                                    class="list-group-item active"
                                </c:if>
                                href="<spring:url value="/filiale/"/>">
                                <spring:message code="menu.filiale" />
                            </a>
                        </li>
                        <li>
                            <a
                                <c:if test="${fn:containsIgnoreCase(url, 'typecaution')}">
                                    class="list-group-item active"
                                </c:if>
                                href="<spring:url value="/typecaution/" />">
                                <spring:message code="menu.typeCaution" />
                            </a>
                        </li>
                        <li>
                            <a
                                <c:if test="${fn:containsIgnoreCase(url, 'typemateriel')}">
                                    class="list-group-item active"
                                </c:if>
                                href="<spring:url value="/typemateriel/" />">
                                <spring:message code="menu.typeMateriel" />
                            </a>
                        </li>
                        <li>
                            <a
                                <c:if test="${fn:containsIgnoreCase(url, 'banque')}">
                                    class="list-group-item active"
                                </c:if>
                                href="<spring:url value="/banque/" />">
                                <spring:message code="banque.liste" />
                            </a>
                        </li>

                        <sec:authorize access="hasRole('ROLE_ADMIN')" >
                            <li>
                                <a
                                    <c:if test="${fn:containsIgnoreCase(url, 'user')}">
                                        class="list-group-item active"
                                    </c:if>
                                    href="<spring:url value="/user/"/>" >
                                    <spring:message code="user.title" />
                                </a>
                            </li>
                        </sec:authorize>
                    </ul>
                </div>
            </div>
        </div>
    </sec:authorize>
    <!-- Approvisionnements -->
    <div class="panel panel-info">
        <div class="panel-heading" role="tab" id="appeloffre">
            <h4 class="panel-title">
                <a class="collapsed" data-toggle="collapse" data-parent="#accordion" href="#collapseAppelOffre" aria-expanded="false" aria-controls="collapseAppelOffre">
                    <spring:message code="menu.appelOffre" />
                </a>
            </h4>
        </div>
        <div id="collapseAppelOffre" role="tabpanel" aria-labelledby="appeloffre"

             <c:choose>
                 <c:when test="${fn:containsIgnoreCase(url, 'caution') || fn:containsIgnoreCase(url, 'appeloffre')}">
                     class="panel-collapse collapse in"
                 </c:when>
                 <c:otherwise>
                     class="panel-collapse collapse"
                 </c:otherwise>
             </c:choose>

             >
            <div class="panel-body">
                <ul class="list-unstyled">
                    <li><a
                            <c:if test="${fn:containsIgnoreCase(url, 'caution')}">
                                class="list-group-item active"
                            </c:if>
                            href="<spring:url value="/caution/"/>" >
                            <spring:message code="caution.liste" />
                        </a></li>
                    <li>
                        <a
                            <c:if test="${(fn:containsIgnoreCase(url, 'appeloffre')) && (fn:containsIgnoreCase(url, 'new')==false) }">
                                class="list-group-item active"
                            </c:if>
                            href="<spring:url value="/appeloffre/"/>" >
                            <spring:message code="menu.appelOffre.liste" />
                        </a>
                    </li>

                </ul>

            </div>
        </div>
    </div>


</div>
