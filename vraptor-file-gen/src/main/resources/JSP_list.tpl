<%@ include file="/header.jsp" %> 

<h1>%MODELNAME%</h1>
<table>
	<c:forEach var="%MODELNAME_LOWERCASE%" items="${%MODELNAME_LOWERCASE%_list}">
		<tr>
			<td>${%MODELNAME_LOWERCASE%}</td>
		</tr>
	</c:forEach>
</table>

<%@ include file="/footer.jsp" %> 
