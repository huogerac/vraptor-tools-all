<%@ include file="/header.jsp"%>

<table>
	<thead>
		<tr>
			<th></th>
			<th></th>
			<th></th>
			<th></th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${%MODELNAME_LOWERCASE%List}" var="%MODELNAME_LOWERCASE%">
			<tr>
				
%FIELDS%
								
				<td><a href="<c:url value="/%REPOSITORYNAME_LOWERCASE%/${%MODELNAME_LOWERCASE%.id}"/>">Editar</a></td>
				<td>
					<form action="<c:url value="/%REPOSITORYNAME_LOWERCASE%/${%MODELNAME_LOWERCASE%.id}"/>" method="POST">
						<button class="link" name="_method" value="DELETE">Remover</button>
					</form>
				</td>
				
				<td>
				</td>
				
			</tr>
		</c:forEach>
	</tbody>
</table>

<%@ include file="/footer.jsp"%>