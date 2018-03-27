package br.com.accounting.core.service;

import br.com.accounting.core.entity.Grupo;
import br.com.accounting.core.exception.ServiceException;

import java.util.List;

public interface GrupoService extends GenericService<Grupo> {
    Long salvar(Grupo subGrupo) throws ServiceException;

    void atualizar(Grupo subGrupo) throws ServiceException;

    void deletar(Grupo subGrupo) throws ServiceException;

    Grupo buscarPorCodigo(Long codigo) throws ServiceException;

    Grupo buscarPorNome(String nome) throws ServiceException;

    List<Grupo> buscarTodos() throws ServiceException;
}
