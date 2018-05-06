package br.com.accounting.core.repository;

import br.com.accounting.core.entity.Contabilidade;
import br.com.accounting.core.entity.TipoContabilidade;

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
