<%@page import="etu2064.framework.modele.Emp"%>
<p>Valeur:</p>
<% Emp e =(Emp)request.getAttribute("emp"); %>
<% out.print(e.getnom()); %>
<% out.print(e.getage()); %>
