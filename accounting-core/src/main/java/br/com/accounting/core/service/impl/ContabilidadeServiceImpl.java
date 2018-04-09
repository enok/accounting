package br.com.accounting.core.service.impl;

import br.com.accounting.core.entity.Contabilidade;
import br.com.accounting.core.repository.ContabilidadeRepository;
import br.com.accounting.core.service.ContabilidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public List<Contabilidade> buscarTodasAsParcelas(final List<Contabilidade> entities, final Long codigoPai) {
        return repository.filtrarPorCodigoPai(entities, codigoPai);
    }
}
