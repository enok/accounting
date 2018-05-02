package br.com.accounting.core.service;

import br.com.accounting.core.entity.Contabilidade;
import br.com.accounting.core.exception.RepositoryException;
import br.com.accounting.core.exception.ServiceException;
import br.com.accounting.core.exception.StoreException;

import java.util.List;

public interface ContabilidadeService extends GenericService<Contabilidade> {
    List<Contabilidade> buscarTodasAsParcelas(Long codigoPai) throws RepositoryException, StoreException;

    List<Contabilidade> buscarParcelasSeguintesInclusivo(Long codigoPai, Integer parcelaAtual) throws StoreException, RepositoryException;

    List<Contabilidade> buscarTodasRecorrentesNaoLancadas() throws StoreException, RepositoryException;

    List<Contabilidade> buscarTodasRecorrentesSeguintesInclusivo(Long codigo) throws ServiceException, StoreException;

    void normalizarProximosLancamentos() throws ServiceException, StoreException;
}
