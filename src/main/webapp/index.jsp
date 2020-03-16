<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<% String lang = request.getParameter("lang"); %>
<fmt:setLocale value="<%=lang%>"/>
<fmt:setBundle basename="localization/messages"/>

<html lang="<%=lang%>">
<head>
    <meta charset="UTF-8">
    <title><fmt:message key="message.title"/></title>
</head>
<body>
<ul>
    <li><a href="?lang=en"><fmt:message key="label.lang.en"/></a></li>
    <li><a href="?lang=gr"><fmt:message key="label.lang.gr"/></a></li>
    <li><a href="?lang=fr"><fmt:message key="label.lang.fr"/></a></li>
</ul>
<h2><fmt:message key="message.title"/></h2>
<fmt:message key="message.description"/>
</body>
</html>