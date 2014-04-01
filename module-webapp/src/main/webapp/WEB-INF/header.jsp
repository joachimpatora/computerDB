<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%> 
<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link href="<c:url value="/css/main.css" />" rel="stylesheet" media="screen">
<link href="<c:url value="/css/bootstrap.min.css" />" rel="stylesheet" media="screen">
<script type="text/javascript" src="http://code.jquery.com/jquery-2.1.0.min.js"></script>
</head>
<body>
	<header class="topbar">
		<h1 class="fill">
			<a href="dashboard"><spring:message code="label.title"/></a><a href="<c:url value="/j_spring_security_logout" />" id="logoutSecurity" class="btn danger" style="float: right"> Logout</a>
		</h1>
		
	</header>