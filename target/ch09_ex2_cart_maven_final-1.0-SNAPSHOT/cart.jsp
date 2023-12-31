<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Your Cart</title>
    <link rel="stylesheet" href="styles/main.css" type="text/css"/>
</head>
<body>

<h1>Your cart</h1>

<table>
  <tr>
    <th>Quantity</th>
    <th>Description</th>
    <th>Price</th>
    <th>Amount</th>
    <th>Discount Percent</th>

    <th></th>
  </tr>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:forEach var="item" items="${cart.items}">
  <tr>
    <td>
      <form action="" method="post">
        <input type="hidden" name="productCode" 
               value="<c:out value='${item.product.code}'/>">
        <input type=text name="quantity" 
               value="<c:out value='${item.quantity}'/>" id="quantity"> <br>
        <label for = "giamgia">Enter your discount here</label>
        <input type=text name="giamgia"  placeholder="Enter your discount here"
               value="<c:out value='${item.giamgia}'/>" id="giamgia" >
        <input type="submit" value="Update">
      </form>
    </td>
    <td><c:out value='${item.product.description}'/></td>
    <td>${item.product.priceCurrencyFormat}</td>
    <td>${item.totalCurrencyFormat}</td>
    <td>${item.giamgia}</td>
    <td>
      <form action="cart" method="post">
        <input type="hidden" name="productCode" 
               value="<c:out value='${item.product.code}'/>">
        <input type="hidden" name="quantity" 
               value="0">
         <input type="hidden" name="giamgia" 
               value="1">
        <input type="submit" value="Remove Item using hidden fields">
        
        
      </form>
        
        
        <form action="cart?productCode=<c:out value='${item.product.code}'/>&quantity=0&giamgia=1" method="post">
            <input type="submit" value="Remove Item using URL rewriting">
            
        </form>
    </td>
  </tr>
</c:forEach>
</table>

<p><b>To change the quantity</b>, enter the new quantity 
      and click on the Update button.</p>
  
<form action="" method="post">
  <input type="hidden" name="action" value="shop">
  <input type="submit" value="Continue Shopping">
</form>

<form action="" method="post">
  <input type="hidden" name="action" value="checkout">
  <input type="submit" value="Print Invoice">
</form>

<br> 

</body>
</html>