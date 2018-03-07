package br.com.accounting.core.service;

import br.com.accounting.core.entity.Grupo;
import br.com.accounting.core.entity.Order;
import br.com.accounting.core.entity.SubGrupo;
import br.com.accounting.core.exception.ServiceException;

import java.util.List;

public interface GrupoService extends GenericService<Grupo> {
    List<Grupo> filtrarPorDescricao(String descricao, List<Grupo> grupos) throws ServiceException;

    List<Grupo> filtrarPorDescricao(String descricao) throws ServiceException;

    List<Grupo> filtrarPorDescricaoESubGrupo(String descricaoGrupo, String descricaoSubGrupo, List<Grupo> grupos) throws ServiceException;

    List<Grupo> filtrarPorDescricaoESubGrupo(String descricaoGrupo, String descricaoSubGrupo) throws ServiceException;

    List<SubGrupo> filtrarSubGruposPorGrupoDescricao(String descricaoGrupo, List<Grupo> registros) throws ServiceException;

    List<SubGrupo> filtrarSubGruposPorGrupoDescricaoSemDuplicidade(String descricaoGrupo, List<Grupo> grupos) throws ServiceException;

    List<SubGrupo> filtrarSubGruposPorGrupoDescricao(String descricaoGrupo) throws ServiceException;

    List<Grupo> ordenarPorDescricao(Order order, List<Grupo> grupos) throws ServiceException;

    List<Grupo> ordenarPorDescricao(Order order) throws ServiceException;

    List<Grupo> ordenarPorDescricaoESubGrupo(Order order, List<Grupo> grupos) throws ServiceException;

    List<Grupo> ordenarPorDescricaoESubGrupo(Order order) throws ServiceException;

    List<SubGrupo> ordenarSubGruposPorGrupoDescricao(Order order, List<Grupo> grupos) throws ServiceException;

    List<SubGrupo> ordenarSubGruposPorGrupoDescricao(Order order) throws ServiceException;
}
