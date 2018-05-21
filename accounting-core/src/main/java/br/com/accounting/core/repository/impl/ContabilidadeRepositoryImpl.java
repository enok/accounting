package br.com.accounting.core.repository.impl;

import br.com.accounting.commons.entity.Conta;
import br.com.accounting.commons.entity.Grupo;
import br.com.accounting.commons.entity.SubGrupo;
import br.com.accounting.commons.repository.impl.GenericAbstractRepository;
import br.com.accounting.core.entity.*;
import br.com.accounting.core.factory.ContabilidadeFactory;
import br.com.accounting.core.repository.ContabilidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static br.com.accounting.commons.util.Utils.*;
import static org.springframework.util.CollectionUtils.isEmpty;

@Repository
public class ContabilidadeRepositoryImpl extends GenericAbstractRepository<Contabilidade> implements ContabilidadeRepository {
    @Autowired
    private String diretorio;

    @Override
    public void ordenarPorDataVencimentoGrupoSubGrupo(final List<Contabilidade> entities) {
        entities.sort(Comparator
                .comparing((Contabilidade c) -> c.dataVencimento())
                .thenComparing(c -> c.grupo().nome())
                .thenComparing(c -> c.grupo().subGrupos().get(0).nome()));
    }

    @Override
    public List<Contabilidade> filtrarPorCodigoPai(final List<Contabilidade> entities, final Long codigoPai) {
        return entities
                .stream()
                .filter(c ->
                        dentroDasParcelas(codigoPai, c)
                )
                .collect(Collectors.toList());
    }

    @Override
    public List<Contabilidade> filtrarParcelasPosteriores(final List<Contabilidade> entities, final Long codigoPai,
                                                          final Integer parcelaAtual) {
        return entities
                .stream()
                .filter(c -> (
                        dentroDasParcelas(codigoPai, c) &&
                                (c.parcelamento().parcela() >= parcelaAtual))
                )
                .collect(Collectors.toList());
    }

    @Override
    public List<Contabilidade> filtrarRecorrentesNaoLancados(final List<Contabilidade> entities) {
        return entities
                .stream()
                .filter(c -> (
                        (c.recorrente()) &&
                                (c.proximoLancamento() == null))
                )
                .collect(Collectors.toList());
    }

    @Override
    public List<Contabilidade> filtrarPorCampos(List<Contabilidade> entities, LocalDate dataVencimento, Boolean recorrente,
                                                String grupo, String local, String descricao, Boolean usouCartao,
                                                Boolean parcelado, Integer parcelas, String conta, TipoContabilidade tipo,
                                                Double valor) {
        String local_ = isBlankOrNull(local) ? "" : local;
        Integer parcelas_ = (parcelas == null) ? -1 : parcelas;
        return entities
                .stream()
                .filter(c -> (dataVencimento.equals(c.dataVencimento()) &&
                        recorrente.equals(c.recorrente()) &&
                        grupo.equals(c.grupo().nome()) &&
                        (((c.local() == null) && isBlankOrNull(local)) || local_.equals(c.local().nome())) &&
                        descricao.equals(c.descricao()) &&
                        usouCartao.equals(c.usouCartao()) &&
                        parcelado.equals(c.parcelado()) &&
                        (((c.parcelamento() == null) && (parcelas == null)) || parcelas_.equals(c.parcelamento().parcelas())) &&
                        conta.equals(c.conta().nome()) &&
                        tipo.equals(c.tipo()) &&
                        valor.equals(c.valor()))
                )
                .collect(Collectors.toList());
    }

    @Override
    public String getArquivo() {
        return diretorio + File.separator + "contabilidades.csv";
    }

    @Override
    public String getArquivoContagem() {
        return diretorio + File.separator + "contabilidades-contagem.txt";
    }

    @Override
    public String criarLinha(final Contabilidade entity) {
        String dataPagamento = null;
        if (entity.dataPagamento() != null) {
            dataPagamento = getStringFromDate(entity.dataPagamento());
        }
        String local = null;
        if (entity.local() != null) {
            local = entity.local().nome();
        }
        String usouCartao = null;
        String numeroCartao = null;
        if (entity.usouCartao()) {
            usouCartao = getStringFromBoolean(entity.usouCartao());
            numeroCartao = entity.cartao().numero();
        }
        String parcelado = null;
        String parcela = null;
        String parcelas = null;
        if (entity.parcelado()) {
            parcelado = getStringFromBoolean(entity.parcelado());
            parcela = entity.parcelamento().parcela().toString();
            parcelas = entity.parcelamento().parcelas().toString();
        }

        Grupo grupo = entity.grupo();
        String grupoNome = null;
        String subGrupoNome = null;
        if (grupo != null) {
            grupoNome = entity.grupo().nome();
            List<SubGrupo> subGrupos = grupo.subGrupos();
            if (!isEmpty(subGrupos)) {
                subGrupoNome = subGrupos.get(0).nome();
            }
        }

        Conta conta = entity.conta();
        String contaNome = null;
        if (conta != null) {
            contaNome = conta.nome();
        }

        StringBuilder builder = new StringBuilder()
                .append(entity.codigo()).append(SEPARADOR)
                .append(getStringFromDate(entity.dataLancamento())).append(SEPARADOR)
                .append(getStringFromDate(entity.dataAtualizacao())).append(SEPARADOR)
                .append(getStringFromDate(entity.dataVencimento())).append(SEPARADOR)
                .append(dataPagamento).append(SEPARADOR)
                .append(getStringFromBoolean(entity.recorrente())).append(SEPARADOR)
                .append(grupoNome).append(SEPARADOR)
                .append(subGrupoNome).append(SEPARADOR)
                .append(local).append(SEPARADOR)
                .append(entity.descricao()).append(SEPARADOR)
                .append(usouCartao).append(SEPARADOR)
                .append(numeroCartao).append(SEPARADOR)
                .append(parcelado).append(SEPARADOR)
                .append(parcela).append(SEPARADOR)
                .append(parcelas).append(SEPARADOR)
                .append(contaNome).append(SEPARADOR)
                .append(entity.tipo()).append(SEPARADOR)
                .append(getStringFromDouble(entity.valor())).append(SEPARADOR)
                .append(entity.codigoPai()).append(SEPARADOR)
                .append(entity.proximoLancamento());
        return builder.toString();
    }

    @Override
    public Contabilidade criarEntity(final String linha) throws ParseException {
        List<String> registro = Stream
                .of(linha)
                .map(w -> w.split(SEPARADOR)).flatMap(Arrays::stream)
                .collect(Collectors.toList());
        int i = 0;
        return ContabilidadeFactory
                .begin()
                .withCodigo(registro.get(i++))
                .withDataLancamento(registro.get(i++))
                .withDataAtualizacao(registro.get(i++))
                .withDataVencimento(registro.get(i++))
                .withDataPagamento(registro.get(i++))
                .withRecorrente(registro.get(i++))
                .withGrupo(registro.get(i++), registro.get(i++))
                .withLocal(registro.get(i++))
                .withDescricao(registro.get(i++))
                .withUsouCartao(registro.get(i++))
                .withCartao(registro.get(i++))
                .withParcelado(registro.get(i++))
                .withParcelamento(registro.get(i++), registro.get(i++))
                .withConta(registro.get(i++))
                .withTipo(registro.get(i++))
                .withValor(registro.get(i++))
                .withCodigoPai(registro.get(i++))
                .withProximoLancamento(registro.get(i++))
                .build();
    }

    private boolean dentroDasParcelas(Long codigoPai, Contabilidade c) {
        return ((c.codigoPai() == null) && (c.codigo() == codigoPai)) ||
                (c.codigoPai() == codigoPai);
    }
}
