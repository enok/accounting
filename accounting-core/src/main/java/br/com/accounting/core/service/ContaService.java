package br.com.accounting.core.service;

import br.com.accounting.core.entity.Conta;
import br.com.accounting.core.exception.ServiceException;

public interface ContaService {
    Long salvar(Conta conta) throws ServiceException;

    Conta buscarPorNomeDescricao(String nome, String descricao) throws ServiceException;
}
