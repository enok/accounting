package br.com.accounting.grupo.business;

import br.com.accounting.commons.business.GenericBusiness;
import br.com.accounting.commons.exception.BusinessException;
import br.com.accounting.commons.exception.GenericException;
import br.com.accounting.commons.exception.StoreException;
import br.com.accounting.commons.dto.GrupoDTO;

public interface GrupoBusiness extends GenericBusiness<GrupoDTO> {
    GrupoDTO buscarPorNome(String nome) throws StoreException, BusinessException, GenericException;
}
