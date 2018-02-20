package br.com.accounting.core.service.impl;

import br.com.accounting.core.entity.Contabilidade;
import br.com.accounting.core.entity.Order;
import br.com.accounting.core.exception.ServiceException;
import br.com.accounting.core.filter.CampoFiltro;
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
    public void salvar(Contabilidade contabilidade) throws ServiceException {
        LOG.info("[ salvar ] contabilidade: " + contabilidade);

        try {
            contabilidadeRepository.salvar(contabilidade);
        } catch (Exception e) {
            String mensagem = "Nao foi possivel salvar o registro: " + contabilidade;
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

    @Override
    public List<Contabilidade> filtrar(CampoFiltro campoFiltro, List<Contabilidade> contabilidades) throws ServiceException {
        LOG.info("[ filtrar ] campoFiltro: " + campoFiltro + ", contabilidades: " + contabilidades);

        try {
            return campoFiltro.filtrar(contabilidades);
        } catch (Exception e) {
            String mensagem = "Nao foi possivel filtrar os registros";
            LOG.error(mensagem, e);
            throw new ServiceException(mensagem, e);
        }
    }

    @Override
    public List<Contabilidade> filtrar(CampoFiltro campoFiltro) throws ServiceException {
        LOG.info("[ filtrar ] campoFiltro: " + campoFiltro);

        try {
            List<Contabilidade> contabilidades = contabilidadeRepository.buscarRegistros();
            return filtrar(campoFiltro, contabilidades);
        } catch (Exception e) {
            String mensagem = "Nao foi possivel filtrar os registros";
            LOG.error(mensagem, e);
            throw new ServiceException(mensagem, e);
        }
    }

    @Override
    public List<Contabilidade> filtrar(CampoFiltro campoFiltro, List<Contabilidade> contabilidades, Order order) throws ServiceException {
        LOG.info("[ filtrar ] campoFiltro: " + campoFiltro + ", contabilidades: " + contabilidades + ", order: " + order);

        try {
            return campoFiltro.filtrar(contabilidades, order);
        } catch (Exception e) {
            String mensagem = "Nao foi possivel filtrar os registros";
            LOG.error(mensagem, e);
            throw new ServiceException(mensagem, e);
        }
    }

    @Override
    public List<Contabilidade> filtrar(CampoFiltro campoFiltro, Order order) throws ServiceException {
        LOG.info("[ filtrar ] campoFiltro: " + campoFiltro + ", order: " + order);

        try {
            List<Contabilidade> contabilidades = contabilidadeRepository.buscarRegistros();
            return filtrar(campoFiltro, contabilidades, order);
        } catch (Exception e) {
            String mensagem = "Nao foi possivel filtrar os registros";
            LOG.error(mensagem, e);
            throw new ServiceException(mensagem, e);
        }
    }
}
