package br.com.accounting.core.repository;

import br.com.accounting.core.entity.Contabilidade;

import java.util.List;

public interface ContabilidadeRepository extends GenericRepository<Contabilidade> {
    void ordenarPorDataVencimentoGrupoSubGrupo(List<Contabilidade> entities);

    List<Contabilidade> filtrarPorCodigoPai(List<Contabilidade> entities, Long codigoPai);

    List<Contabilidade> filtrarParcelasPosteriores(List<Contabilidade> entities, Long codigoPai,
                                                   Integer parcelaAtual);

    List<Contabilidade> filtrarRecorrentesNaoLancados(List<Contabilidade> entities);
}
