package br.com.accounting.core.service;

import br.com.accounting.core.entity.Cartao;
import br.com.accounting.core.exception.ServiceException;

import java.util.List;

public interface CartaoService extends GenericService<Cartao> {
    Long salvar(Cartao cartao) throws ServiceException;

    void atualizar(Cartao cartao) throws ServiceException;

    void deletar(Cartao cartao) throws ServiceException;

    Cartao buscarPorCodigo(Long codigo) throws ServiceException;

    Cartao buscarPorNumero(String numero) throws ServiceException;

    List<Cartao> buscarTodos() throws ServiceException;
}
