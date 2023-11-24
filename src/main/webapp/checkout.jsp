<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Thanks you</title>
    <link rel="stylesheet" href="styles/main.css" type="text/css"/>
</head>
<body>
    <h1>Thanks you for using our services</h1>
    <p>This is your invoice</p>
    
    <table>
        
        <tr>
    <th>Description</th>    
    <th>Quantity</th>
    <th>Price</th>
    <th>Discount Percent</th>
    <th>Final Price</th>
    
        </tr>
    
        
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <c:forEach var="item" items="${cart.items}">
      <tr>  
    <td><c:out value='${item.product.description}'/></td>
    <td>${item.quantity} </td>
    <td>${item.product.priceCurrencyFormat}</td>
    <td>${item.giamgia}</td>
    <td>${item.totalCurrencyFormat}</td>
      </tr>
    </c:forEach>
    

    </table>

    
    
    <p>the total is: $${thisIsTotal} <p>
    
    <br> 
</body>
</html>