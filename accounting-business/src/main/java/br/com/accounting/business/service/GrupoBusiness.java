package br.com.accounting.business.service;

import br.com.accounting.business.dto.GrupoDTO;
import br.com.accounting.business.exception.BusinessException;
import br.com.accounting.core.entity.Grupo;

import java.util.List;

public interface GrupoBusiness extends GenericBusiness<GrupoDTO, Grupo> {
    Long criar(GrupoDTO dto) throws BusinessException;

    void atualizar(GrupoDTO dto) throws BusinessException;

    void excluir(GrupoDTO dto) throws BusinessException;

    GrupoDTO buscarPorId(Long codigo) throws BusinessException;

    List<GrupoDTO> buscarTodos() throws BusinessException;
}
