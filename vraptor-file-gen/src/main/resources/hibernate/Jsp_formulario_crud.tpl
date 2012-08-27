<%@ include file="/header.jsp"%>

<form id="%MODELNAME_LOWERCASE%Form" action="<c:url value="/%REPOSITORYNAME_LOWERCASE%"/>" method="POST">
	<fieldset>
	
		<legend>Adicionar Produto</legend>
		
%FIELDS%
		
		<button type="submit">Enviar</button>
	</fieldset>
</form>

<!-- 

<script type="text/javascript">
	$('#produtosForm').validate({
	rules: {"produto.nome": {required: true, minlength: 3},
			"produto.descricao": {required: true, maxlength: 40},
			"produto.preco": {min: 0.0}
			}
	});
</script>

 -->


<%@ include file="/footer.jsp"%>