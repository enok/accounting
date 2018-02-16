package br.com.accounting.core.service.impl;

import br.com.accounting.core.entity.Contabilidade;
import br.com.accounting.core.exception.ServiceException;
import br.com.accounting.core.repository.ContabilidadeRepository;
import br.com.accounting.core.service.ContabilidadeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContabilidadeServiceImpl implements ContabilidadeService {
    private static final Logger LOG = LoggerFactory.getLogger(ContabilidadeServiceImpl.class);

    @Autowired
    private ContabilidadeRepository contabilidadeRepository;

    @Override
    public void salvar(Contabilidade registro) throws ServiceException {
        LOG.info("[ salvar ] registro: " + registro);

        try {
            contabilidadeRepository.salvar(registro);
        } catch (Exception e) {
            String mensagem = "Nao foi possivel salvar o registro: " + registro;
            LOG.error(mensagem, e);
            throw new ServiceException(mensagem, e);
        }
    }

    @Override
    public List<Contabilidade> buscarRegistros() throws ServiceException {
        LOG.info("[ buscarRegistros ]");

        try {
            return contabilidadeRepository.buscarRegistros();
        } catch (Exception e) {
            String mensagem = "Nao foi possivel buscar os registros";
            LOG.error(mensagem, e);
            throw new ServiceException(mensagem, e);
        }
    }
}
