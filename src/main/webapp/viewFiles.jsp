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