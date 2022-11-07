<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>edit</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
</head>
<body>
<div class="w3-container w3-center">

<h3>TAX CALCULATOR</h3>

<form method="post" action="update" modelAttribute="">
    ID: ${dataID} <br><br>
    Basic Salary : <input type="text" name="upBasicSalary" id="upBasicSalary" value="${dataSal}">
    <br><br>
    House Rent : <input type="text" name="upHouseRent" id="upHouseRent" value="${dataHR}">
    <br><br>
    Medical Allowance : <input type="text" name="upMedicalAllowance" id="upMedicalAllowance" value="${dataMA}">
    <br><br>
    Conveyance : <input type="text" name="upConveyance" id="upConveyance" value="${dataCon}">
    <br><br>
    Festival Bonus : <input type="text" name="upFestivalBonus" id="upFestivalBonus" value="${dataFB}">
    <br><br>
    <input type="submit" value="Update">

</form>
<form method="post" action="delete" modelAttribute="">
    <input type="submit" name="show" value="Delete">
</form>
<form method="post" action="show" modelAttribute="">
    <input type="submit" name="show" value="Show All">
</form>
<h4>Total Income   : ${totalIncome} </h4>
<h4>Taxable Income : ${taxableIncome} </h4>
<h4>Gross Tax      : ${grossTax}</h4>
<h4>Rebate         : ${rebate}</h4>
<h4>Net tax        : ${netTax}</h4>

</div>
</body>
</html>