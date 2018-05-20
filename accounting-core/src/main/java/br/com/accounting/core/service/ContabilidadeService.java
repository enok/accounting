package br.com.accounting.core.service;

import br.com.accounting.commons.entity.Conta;
import br.com.accounting.commons.entity.Local;
import br.com.accounting.commons.exception.ServiceException;
import br.com.accounting.commons.exception.StoreException;
import br.com.accounting.commons.service.GenericService;
import br.com.accounting.core.entity.*;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;

public interface ContabilidadeService extends GenericService<Contabilidade> {
    List<Contabilidade> buscarTodasAsParcelas(Long codigoPai) throws StoreException, ParseException;

    List<Contabilidade> buscarParcelasSeguintesInclusivo(Long codigoPai, Integer parcelaAtual) throws StoreException, ParseException;

    List<Contabilidade> buscarTodasRecorrentesNaoLancadas() throws StoreException, ParseException;

    List<Contabilidade> buscarTodasRecorrentesSeguintesInclusivo(Long codigo) throws StoreException, ParseException;

    Contabilidade buscar(LocalDate dataVencimento, Boolean recorrente, Grupo grupo, Local local, String descricao,
                         Boolean usouCartao, Boolean parcelado, Parcelamento parcelamento, Conta conta, TipoContabilidade tipo,
                         Double valor) throws StoreException, ParseException;

    void normalizarProximosLancamentos() throws ServiceException, StoreException, ParseException;
}
