package br.com.accounting.business.service;

import br.com.accounting.business.dto.SubGrupoDTO;
import br.com.accounting.business.exception.BusinessException;
import br.com.accounting.core.entity.SubGrupo;

import java.util.List;

public interface SubGrupoBusiness extends GenericBusiness<SubGrupoDTO, SubGrupo> {
    Long criar(SubGrupoDTO dto) throws BusinessException;

    void atualizar(SubGrupoDTO dto) throws BusinessException;

    void excluir(SubGrupoDTO dto) throws BusinessException;

    SubGrupoDTO buscarPorId(Long codigo) throws BusinessException;

    List<SubGrupoDTO> buscarTodos() throws BusinessException;
}
