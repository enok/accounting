package br.com.accounting.core.service;

import br.com.accounting.core.entity.Contabilidade;
import br.com.accounting.core.exception.RepositoryException;
import br.com.accounting.core.exception.ServiceException;

import java.util.List;

public interface ContabilidadeService extends GenericService<Contabilidade> {
    List<Contabilidade> buscarTodasAsParcelas(Long codigoPai) throws RepositoryException;

    List<Contabilidade> buscarParcelasSeguintesInclusivo(Long codigoPai, Integer parcelaAtual) throws RepositoryException;

    List<Contabilidade> buscarTodasRecorrentesNaoLancadas() throws RepositoryException;

    List<Contabilidade> buscarTodasRecorrentesSeguintesInclusivo(Long codigo) throws ServiceException;

    void normalizarProximosLancamentos() throws ServiceException;
}
