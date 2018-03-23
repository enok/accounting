package br.com.accounting.core.service;

import br.com.accounting.core.entity.Conta;
import br.com.accounting.core.exception.ServiceException;

import java.util.List;

public interface ContaService extends GenericService<Conta> {
    Long salvar(Conta conta) throws ServiceException;

    void atualizar(Conta conta) throws ServiceException;

    void atualizarSaldo(Conta conta, Double saldo) throws ServiceException;

    void deletar(Conta conta) throws ServiceException;

    Conta buscarPorCodigo(Long codigo) throws ServiceException;

    Conta buscarPorNomeDescricao(String nome, String descricao) throws ServiceException;

    List<Conta> buscarTodas() throws ServiceException;
}
