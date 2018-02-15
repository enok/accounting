package br.com.accounting.core.service.impl;

import br.com.accounting.core.entity.Registro;
import br.com.accounting.core.exception.RepositoryException;
import br.com.accounting.core.exception.ServiceException;
import br.com.accounting.core.repository.RegistroRepository;
import br.com.accounting.core.service.RegistroService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegistroServiceImpl implements RegistroService {
    private static final Logger LOG = LoggerFactory.getLogger(RegistroServiceImpl.class);

    @Autowired
    private RegistroRepository registroRepository;

    @Override
    public void salvar(Registro registro) throws ServiceException {
        LOG.info("[ salvar ] registro: " + registro);

        try {
            registroRepository.salvar(registro);
        } catch (Exception e) {
            String mensagem = "Nao foi possivel salvar o registro: " + registro;
            LOG.error(mensagem, e);
            throw new ServiceException(mensagem, e);
        }
    }


}
