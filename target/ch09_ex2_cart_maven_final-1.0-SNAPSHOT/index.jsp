<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Murach's Java Servlets and JSP</title>
    <link rel="stylesheet" href="styles/main.css" type="text/css"/>
</head>
<body>
    
<h1>CD list</h1>
<table>
    
    <form action="loadProducts">
        <button> Load data </button>
    </form>

   


    
    <tr>
        <th>Description</th>
        <th class="right">Price</th>
        <th>&nbsp;</th>
    </tr>
    
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
    <c:forEach var="item" items="${sessionScope.products}">
        <tr>
        <td><c:out value='${item.description}'/></td>
        <td class="right"><c:out value='${item.price}'/></td>
        <td><form action="cart" method="post">
                <input type="hidden" name="productCode" value="<c:out value='${item.code}'/>">
                <input type="submit" value="Add To Cart">
            </form></td>
    </tr>
    </c:forEach>

    
</table>
        
    <br> 
    <a class = "returnHome" href="https://thangmainpage-f659678d7b24.herokuapp.com/#listEx"><i class="fa-solid fa-house"></i> &#127968 Back To Main Page</a>
</body>
</html>