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
			<li class="nav-item" >
				<a class="nav-link" href="/home">Dashboard</a>
			</li>
			<li class="nav-item">
			  <a class="nav-link" href="/logout">Logout</a>
			</li>
		  </ul>
		<div class="card" style="padding: 15px;">
		<h1>Edit Listing</h1>
		<table>
			<tbody>
				<form:form action="/listings/edit/${listing.getId()}" method="post"
					modelAttribute="listing">
					<input type="hidden" name="_method" value="put">
					<tr>
						<td><form:label path="address">Address:</form:label> </td>
						<td><form:errors path="address" class="text-danger" /></td>
						<td><form:input path="address" /></td>
					</tr>
					<tr>
						<td><form:label path="price">Price:</form:label> </td>
						<td><form:errors path="price" class="text-danger" /></td>
						<td><form:input type="number" step="0.01" path="price" /></td>
					</tr>
					<tr>
						<td><form:label path="listingDate" class="">Listing Date:</form:label></td>
						<td><form:errors path="listingDate" class="text-danger" /></td>
						<td><form:input type="date" path="listingDate" class="" max="${ date }" /></td>
					</tr>
					<tr>
						<td><form:label path="rooms">Bedroom count:</form:label></td>
						<td><form:errors path="rooms" class="text-danger" /></td>
						<td><form:input type="number" step="1" path="rooms" /></td>
					</tr>
					<tr>
						<td><form:label path="baths">Bath count:</form:label></td>
						<td><form:errors path="baths" class="text-danger" /></td>
						<td><form:input type="number" step="0.5" path="baths" /></td>
					</tr>
					<tr><td><input class="btn btn-primary" type="submit" value="Submit"/></td></tr>
				</form:form>
			</tbody>
			
		</table>
		<a class="btn btn-danger" href="/listings/${ listing.getId() }/delete" style="width: 77px;">delete</a>
	</div>
	</div>
</body>
</html>