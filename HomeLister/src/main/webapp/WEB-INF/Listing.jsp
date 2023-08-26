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
		<h1>${ listing.getAddress() }</h1>
		<p>Address: ${ listing.getAddress() }</p>
		<p>
			Listing Date:
			<fmt:formatDate pattern="dd-MM-yy"
				value="${listing.getListingDate()}" />
		</p>
		<p>Price: $${ listing.getPrice() }</p>

		<div class="border border-dark overflow-auto" id="note-box">
			<ul>
				<c:forEach var="note" items="${notes}">
					<li>Added by ${note.getNoteCreator().getUserName()}</li>
					<li>${ note.getMessage() }</li>
				</c:forEach>
			</ul>
		</div>
			<form:form action="/notes/add/${listing.getId()}" method="post" modelAttribute="note" style="margin-bottom: 15px;">
				<form:label path="message">Message:</form:label>
				<form:errors path="message" class="text-danger" />
				<form:input path="message" />
				<input type="submit" value="Submit"/>
			</form:form>
		

		<c:if test="${ user.getId() == listing.getListCreator().getId() }">
			<a class="btn btn-primary" href="/listings/edit/${ listing.getId() }">edit</a>
			<br />
			<a class="btn btn-danger" href="/listings/${ listing.getId() }/delete">delete</a>
		</c:if>
	</div>
	</div>
</body>
</html>