package br.com.accounting.business.service;

import br.com.accounting.business.dto.SubGrupoDTO;
import br.com.accounting.business.exception.BusinessException;
import br.com.accounting.core.entity.SubGrupo;

public interface SubGrupoBusiness extends GenericBusiness<SubGrupoDTO, SubGrupo> {
    Long criar(final SubGrupoDTO subGrupoDTO) throws BusinessException;

    SubGrupoDTO buscarSubGrupoPorId(final Long codigo) throws BusinessException;
}
