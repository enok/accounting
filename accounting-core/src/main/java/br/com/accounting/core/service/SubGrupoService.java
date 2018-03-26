package br.com.accounting.core.service;

import br.com.accounting.core.entity.SubGrupo;
import br.com.accounting.core.exception.ServiceException;

import java.util.List;

public interface SubGrupoService extends GenericService<SubGrupo> {
    Long salvar(SubGrupo subGrupo) throws ServiceException;

    void atualizar(SubGrupo subGrupo) throws ServiceException;

    void deletar(SubGrupo subGrupo) throws ServiceException;

    SubGrupo buscarPorCodigo(Long codigo) throws ServiceException;

    SubGrupo buscarPorNome(String nome) throws ServiceException;

    List<SubGrupo> buscarTodos() throws ServiceException;
}
