package br.com.accounting.core.service;

import br.com.accounting.core.entity.Conta;
import br.com.accounting.core.exception.ServiceException;

import java.util.List;

public interface ContaService {
    Long salvar(Conta conta) throws ServiceException;

    Conta buscarPorNomeDescricao(String nome, String descricao) throws ServiceException;

    List<Conta> buscarTodas() throws ServiceException;
}
