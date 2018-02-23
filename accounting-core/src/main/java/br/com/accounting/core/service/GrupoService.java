package br.com.accounting.core.service;

import br.com.accounting.core.entity.Grupo;
import br.com.accounting.core.entity.Order;
import br.com.accounting.core.entity.SubGrupo;
import br.com.accounting.core.exception.ServiceException;
import br.com.accounting.core.filter.CampoFiltro;
import br.com.accounting.core.ordering.CampoOrdem;

import java.util.List;

public interface GrupoService extends GenericService<Grupo> {
    List<SubGrupo> filtrarSubGrupos(CampoFiltro campoFiltro, List<Grupo> registros) throws ServiceException;

    List<SubGrupo> filtrarSubGrupos(CampoFiltro campoFiltro) throws ServiceException;

    List<SubGrupo> ordenarSubGrupos(CampoOrdem campoOrdem, List<Grupo> grupos, Order order) throws ServiceException;

    List<SubGrupo> ordenarSubGrupos(CampoOrdem campoOrdem, Order order) throws ServiceException;
}
