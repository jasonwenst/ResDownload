<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page language="java" import="utils.FileUtils" %>
<%@ page language="java" import="utils.PropertyUtils" %>
<%@ page language="java" import="java.util.List" %>
<%@ page language="java" import="entity.FileStatusBean" %>

<html>
<body>

<table>
<tr><td>FILENAME</td><td>SIZE</td><td>STATUS</td><td>OPERATION</td></tr>
<%List<FileStatusBean> files =  FileUtils.getAllFiles(PropertyUtils.getProperty("savePath"));%>
<%for(int i = 0; i < files.size(); i++) {%>
<tr>
	<td><%=files.get(i).getFileName()%></td><td><%=files.get(i).getSizeAsMB() %></td><td><%=files.get(i).getStatus() %></td><td><a href="<%=request.getContextPath() %>/downloadBySFTP?fileName=<%=files.get(i).getFileName().split("~")[0] %>" >download</a></td>
</tr>
<%} %>

</table>
</body>
</html>