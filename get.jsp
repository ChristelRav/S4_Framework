<p>Coucou</p>
<%@page import="etu2064.framework.modele.Emp"%>
<% Emp pv = (Emp)request.getAttribute("emp"); %>
<p>Nom: <%= pv.getnom() %></p>
<p>Age: <%= pv.getage() %></p>
