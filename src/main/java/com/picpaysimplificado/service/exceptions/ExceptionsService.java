package com.picpaysimplificado.service.exceptions;

public enum ExceptionsService {

	MERCHAN_NAO_AUTORIZADO("Usuario do tipo logista não esta autorizado a realizar transação"),
	SALVO_INSUFICIENTE("Saldo insuficiente"),
	USER_NAO_ENCONTRADO("Usuario não encontrado"),
	TRANSACAO_NAO_AUTORIZADA("Transação não autorizada"),
	SERVICE_FORA_DO_AR("Serviço de notificação fora do ar"),
	TRANSACAO_REALIZADA_SUCESSO("Transação realizado com sucesso"),
	TRANSACAO_RECEBIDA_SUCESSO("Transação Recebida com sucesso");;
	
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
