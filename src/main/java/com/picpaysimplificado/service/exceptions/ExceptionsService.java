package com.picpaysimplificado.service.exceptions;

public enum ExceptionsService {

	MERCHAN_NAO_AUTORIZADO("Usuario do tipo logista não esta autorizado a realizar transação"),
	SALVO_INSUFICIENTE("Saldo insuficiente"),
	USER_NAO_ENCONTRADO("Usuario não encontrado"),
	TRANSACAO_NAO_AUTORIZADA("Transação não autorizada");
	
	private String descricao;

	ExceptionsService(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
}
