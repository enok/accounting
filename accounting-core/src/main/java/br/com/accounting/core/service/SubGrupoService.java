package br.com.accounting.core.service;

import br.com.accounting.core.entity.Order;
import br.com.accounting.core.entity.SubGrupo;
import br.com.accounting.core.exception.ServiceException;

import java.util.List;

public interface SubGrupoService extends GenericService<SubGrupo> {
    List<SubGrupo> filtrarPorDescricao(String descricao, List<SubGrupo> subGrupos) throws ServiceException;

    List<SubGrupo> filtrarPorDescricao(String descricao) throws ServiceException;

    List<SubGrupo> ordenarPorDescricao(Order order, List<SubGrupo> subGrupos) throws ServiceException;

    List<SubGrupo> ordenarPorDescricao(Order order) throws ServiceException;
}
