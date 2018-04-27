package br.com.accounting.core.service.impl;

import br.com.accounting.core.entity.Historico;
import br.com.accounting.core.exception.StoreException;
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
    private HistoricoRepository repository;

    @Override
    public Long salvar(final String metodo, final Map<String, Object> parametros) throws StoreException, ServiceException {
        Historico historico;
        try {
            historico = HistoricoFactory
                    .begin()
                    .withMetodo(metodo)
                    .withParametros(parametros)
                    .build();
            setarProximoCodigo(historico);
            repository.salvar(historico);
        }
        catch (StoreException e) {
            throw e;
        }
        catch (Exception e) {
            String message = "Não foi possível salvar o histórico.";
            throw new ServiceException(message, e);
        }
        return historico.codigo();
    }

    private void setarProximoCodigo(final Historico entity) throws StoreException, RepositoryException {
        Long proximoCodigo = repository.proximoCodigo();
        repository.incrementarCodigo(proximoCodigo);
        entity.codigo(proximoCodigo);
    }
}
