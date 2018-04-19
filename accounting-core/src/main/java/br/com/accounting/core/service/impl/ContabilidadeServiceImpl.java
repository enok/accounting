package br.com.accounting.core.service.impl;

import br.com.accounting.core.entity.Contabilidade;
import br.com.accounting.core.exception.RepositoryException;
import br.com.accounting.core.exception.ServiceException;
import br.com.accounting.core.repository.ContabilidadeRepository;
import br.com.accounting.core.service.ContabilidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ContabilidadeServiceImpl extends GenericAbstractService<Contabilidade> implements ContabilidadeService {
    private ContabilidadeRepository repository;

    @Autowired
    public ContabilidadeServiceImpl(ContabilidadeRepository repository) {
        super(repository);
        this.repository = repository;
    }

    @Override
    public void ordenarTodas(final List<Contabilidade> entities) {
        repository.ordenarPorDataVencimentoGrupoSubGrupo(entities);
    }

    @Override
    public List<Contabilidade> buscarTodasAsParcelas(final Long codigoPai) throws RepositoryException {
        List<Contabilidade> entities = repository.buscarRegistros();
        return repository.filtrarPorCodigoPai(entities, codigoPai);
    }

    @Override
    public List<Contabilidade> buscarParcelasSeguintesInclusivo(final Long codigoPai, final Integer parcelaAtual) throws RepositoryException {
        List<Contabilidade> entities = repository.buscarRegistros();
        return repository.filtrarParcelasPosteriores(entities, codigoPai, parcelaAtual);
    }

    @Override
    public List<Contabilidade> buscarTodasRecorrentesNaoLancadas() throws RepositoryException {
        List<Contabilidade> entities = repository.buscarRegistros();
        return repository.filtrarRecorrentesNaoLancados(entities);
    }

    @Override
    public List<Contabilidade> buscarTodasRecorrentesSeguintesInclusivo(final Long codigo) throws ServiceException {
        List<Contabilidade> entities = new ArrayList<>();
        Long proximoLancamento = codigo;
        do {
            Contabilidade entity = buscarPorCodigo(proximoLancamento);
            if (entity == null) {
                break;
            }
            entities.add(entity);
            proximoLancamento = entity.proximoLancamento();
        }
        while (proximoLancamento != null);
        return entities;
    }

    @Override
    public void normalizarProximosLancamentos() throws ServiceException {
        List<Contabilidade> entities = buscarTodas();
        for (Contabilidade entity : entities) {
            Contabilidade entityBuscada = buscarPorCodigo(entity.proximoLancamento());
            if (entityBuscada == null) {
                entity.proximoLancamento(null);
                atualizar(entity);
            }
        }
    }
}
