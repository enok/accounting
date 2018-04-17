package br.com.accounting.core.service;

import br.com.accounting.core.entity.Contabilidade;
import br.com.accounting.core.exception.RepositoryException;

import java.util.List;

public interface ContabilidadeService extends GenericService<Contabilidade> {
    List<Contabilidade> buscarTodasAsParcelas(List<Contabilidade> entities, Long codigoPai);

    List<Contabilidade> buscarTodasRecorrentesNaoLancadas() throws RepositoryException;
}
