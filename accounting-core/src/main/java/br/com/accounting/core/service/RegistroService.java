package br.com.accounting.core.service;

import br.com.accounting.core.entity.Registro;
import br.com.accounting.core.exception.ServiceException;

public interface RegistroService {
    void salvar(Registro registro) throws ServiceException;
}
