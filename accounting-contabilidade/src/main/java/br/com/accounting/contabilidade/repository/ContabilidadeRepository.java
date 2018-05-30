package br.com.accounting.contabilidade.repository;

import br.com.accounting.commons.entity.Contabilidade;
import br.com.accounting.commons.entity.TipoContabilidade;
import br.com.accounting.commons.repository.GenericRepository;

import java.time.LocalDate;
import java.util.List;

public interface ContabilidadeRepository extends GenericRepository<Contabilidade> {
    void ordenarPorDataVencimentoGrupoSubGrupo(List<Contabilidade> entities);

    List<Contabilidade> filtrarPorCodigoPai(List<Contabilidade> entities, Long codigoPai);

    List<Contabilidade> filtrarParcelasPosteriores(List<Contabilidade> entities, Long codigoPai,
                                                   Integer parcelaAtual);

    List<Contabilidade> filtrarRecorrentesNaoLancados(List<Contabilidade> entities);

    List<Contabilidade> filtrarPorCampos(List<Contabilidade> entities, LocalDate dataVencimento, Boolean recorrente,
                                         String grupo, String local, String descricao, Boolean usouCartao, Boolean parcelado,
                                         Integer parcelas, String conta, TipoContabilidade tipo, Double valor);
}
