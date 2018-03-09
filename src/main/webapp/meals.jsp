<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://sargue.net/jsptags/time" prefix="javatime" %>
<html>
<head>
  <title>Meals</title>
  <style>
    <%@include file="tableStyle.css"%>
  </style>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<h2>Meals</h2>
<div>
  <table class="tg" align="center">
    <thead>
    <tr>
      <th width="300">Date Time</th>
      <th>Description</th>
      <th>Calories</th>
      <th colspan="2">Action</th>
    </tr>
    </thead>
    <c:forEach items="${mealList}" var="meal">

      <tr>
        <td><span style="${meal.isExceed() ? "color: red" : "color: green"}"><javatime:format value="${meal.getDateTime()}" pattern="dd.MM.yyyy HH:mm"/>"</td>
        <td><span style="${meal.isExceed() ? "color: red" : "color: green"}"><c:out value="${meal.getDescription()}"/></span></td>
        <td><span style="${meal.isExceed() ? "color: red" : "color: green"}"><c:out value="${meal.getCalories()}"/></span></td>
        <td><a href="<c:url value='/edit/${meal.id}'/>">Edit</a></td>
        <td><a href="<c:url value='/delete/${meal.id}'/>">Delete</a></td>
      </tr>

    </c:forEach>
  </table>
</div>
</body>
</html>
