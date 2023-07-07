<p>Coucou</p>
<%@page import="etu2064.framework.modele.Emp"%>
<p>HUHU</p>
<% Emp pv = (Emp)request.getAttribute("file"); %>
<p>Nom du fichier: <%= pv.getFu().getnameFile() %></p>