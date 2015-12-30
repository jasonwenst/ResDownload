<%@ page language="java" import="utils.FileUtils" %>
<%@ page language="java" import="utils.PropertyUtils" %>
<%@ page language="java" import="java.util.List" %>
<html>
<body>

<table>
<%List<String> files =  FileUtils.getAllFiles(PropertyUtils.getProperty(FileUtils.isWindows() ? "savePathWindows" : "savePathLinux"));%>
<%for(int i = 0; i < files.size(); i++) {%>
<tr>
	<td><%=files.get(i)%></td><td><a href="<%=request.getContextPath() %>/downloadBySFTP?fileName=<%=files.get(i) %>" >download</a></td>
</tr>
<%} %>

</table>
</body>
</html>