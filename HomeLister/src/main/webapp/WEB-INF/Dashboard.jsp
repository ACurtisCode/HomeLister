<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!-- c:out ; c:forEach etc. -->
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!-- Formatting (dates) -->
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!-- form:form -->
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!-- for rendering errors on PUT routes -->
<%@ page isErrorPage="true"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="/css/main.css">
<!-- change to match your file/naming structure -->
<script src="/webjars/jquery/jquery.min.js"></script>
<script src="/webjars/bootstrap/js/bootstrap.min.js"></script>
</head>
<body>
	<div class="container">
		<ul class="nav">
			<li class="nav-item" id="pageTitle">Home Lister</li>
			<li class="nav-item">
			  <a class="nav-link" href="/logout">Logout</a>
			</li>
		  </ul>
		<h1 style="text-align: center;">Welcome ${user.getUserName()}</h1>
		<table class="table">
			<thead>
				<tr>
					<td>Address</td>
					<td>Listed On</td>
					<td>Added By</td>
					<td>Bed/Bath</td>
					<td>Price</td>
				</tr>
				</thead>
				<tbody>
				<c:forEach var="listing" items="${ listings }">
					<tr>
						<td><a href="/listings/${ listing.getId() }">${ listing.getAddress() }</a></td>
						<td><fmt:formatDate pattern="dd-MM-yy"
								value="${listing.getListingDate()}" /></td>
						<td>${ listing.getListCreator().getUserName() }</td>
						<td>${ listing.getRooms() }/${ listing.getBaths() }</td>	
						<td><fmt:formatNumber value = "${ listing.getPrice()}" type = "currency"/></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<form:form action="/listings/new" method="get">
			<input type="submit" value="Add Listing" />
		</form:form>
	</div>

</body>
</html>