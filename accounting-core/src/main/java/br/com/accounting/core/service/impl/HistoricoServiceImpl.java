package br.com.accounting.core.service.impl;

import br.com.accounting.core.entity.Historico;
import br.com.accounting.core.exception.RepositoryException;
import br.com.accounting.core.exception.ServiceException;
import br.com.accounting.core.factory.HistoricoFactory;
import br.com.accounting.core.repository.HistoricoRepository;
import br.com.accounting.core.service.HistoricoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class HistoricoServiceImpl implements HistoricoService {
    @Autowired
    private HistoricoRepository historicoRepository;

    @Override
    public Long salvar(final String metodo, final Map<String, Object> parametros) throws ServiceException {

        Historico historico;
        try {
            historico = HistoricoFactory
                    .begin()
                    .withMetodo(metodo)
                    .withParametros(parametros)
                    .build();
            setaProximoCodigo(historico);
            historicoRepository.salvar(historico);
        }
        catch (Exception e) {
            String message = "Não foi possível salvar o histórico.";
            throw new ServiceException(message, e);
        }

        return historico.codigo();
    }

    private void setaProximoCodigo(Historico historico) throws RepositoryException {
        Long proximoCodigo = historicoRepository.proximoCodigo();
        historicoRepository.incrementarCodigo(proximoCodigo);
        historico.codigo(proximoCodigo);
    }
}
