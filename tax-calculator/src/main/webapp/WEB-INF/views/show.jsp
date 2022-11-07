
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
  <title>showAll</title>
</head>
<body>
<form method="post" action="upDel">
Enter id to Edit : <input type="text" name="taxID" id="taxID" placeholder="Enter ID">
  <input type="submit" name="edit" value="Edit">
</form>
<h1>All tax Calculation Data</h1>
<table>
  <thead>
  <tr>
    <th>ID</th>
    <th>Basic Salary</th>
    <th>House Rent</th>
    <th>Medical Allowance</th>
    <th>Conveyance</th>
    <th>Festival Bonus</th>
    <th>Taxable Income</th>
    <th>Gross Tax</th>
    <th>Rebate</th>
    <th>Net Tax</th>
  </tr>
  </thead>
  <tbody>
  <c:forEach items="${taxCalculator}" var="t">
    <tr>
      <td>${t.id}</td>
      <td>${t.basicSalary}</td>
      <td>${t.houseRent}</td>
      <td>${t.medicalAllowance}</td>
      <td>${t.conveyance}</td>
      <td>${t.festivalBonus}</td>
      <td>${t.taxAbleIncome}</td>
      <td>${t.taxLiability}</td>
      <td>${t.rebate}</td>
      <td>${t.netTax}</td>
    </tr>
  </c:forEach>
  </tbody>
</table>
</body>
</html>
