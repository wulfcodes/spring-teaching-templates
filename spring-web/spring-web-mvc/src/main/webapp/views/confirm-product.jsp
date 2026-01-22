<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Product - ${productName}</title>
</head>
<body>

<h1>Product Details</h1>

<div>
    <h2>Product is Confirmed</h2>
    <p><strong>Name:</strong> ${productName}</p>
    <p><strong>Category:</strong> ${category}</p>
    <p><strong>Price:</strong> ₹${price}</p>

    <%-- Discount section --%>
    <%
        Object discount = request.getAttribute("discount");
        boolean hasDiscount = (discount != null && !discount.toString().trim().isEmpty());
    %>

    <% if (hasDiscount) { %>
        <p><strong>Discount:</strong> <%= discount %>% OFF</p>
    <% } else { %>
        <p><em>No discount available</em></p>
    <% } %>

    <%-- Stock availability --%>
    <%
        Object inStockObj = request.getAttribute("inStock");
        boolean inStock = (inStockObj != null && Boolean.parseBoolean(inStockObj.toString()));
    %>

    <% if (inStock) { %>
        <p style="color: green;"><strong>Available in stock</strong></p>
    <% } else { %>
        <p style="color: red;"><strong>Currently out of stock</strong></p>
    <% } %>
</div>

</body>
</html>
