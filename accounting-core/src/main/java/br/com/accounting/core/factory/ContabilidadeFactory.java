package br.com.accounting.core.factory;

import br.com.accounting.commons.entity.Cartao;
import br.com.accounting.core.entity.*;

import java.text.ParseException;
import java.time.LocalDate;

import static br.com.accounting.commons.util.Utils.*;

public final class ContabilidadeFactory {
    private static ContabilidadeFactory factory;
    private Contabilidade entity;

    private ContabilidadeFactory() {
        entity = new Contabilidade();
    }

    public static ContabilidadeFactory begin() {
        factory = new ContabilidadeFactory();
        return factory;
    }

    public Contabilidade build() {
        if (entity.dataLancamento() == null) {
            entity.dataLancamento(LocalDate.now());
        }
        entity.dataAtualizacao(LocalDate.now());
        if (entity.usouCartao() == null) {
            entity.usouCartao(false);
            entity.cartao(null);
            zeraParcelamento();
        }
        if (entity.parcelado() == null) {
            zeraParcelamento();
        }
        return entity;
    }

    private void zeraParcelamento() {
        entity.parcelado(false);
        entity.parcelamento(null);
    }

    public ContabilidadeFactory withCodigo(String codigo) {
        if (!isBlankOrNull(codigo)) {
            withCodigo(Long.parseLong(codigo));
        }
        return this;
    }

    public ContabilidadeFactory withCodigo(Long codigo) {
        if (codigo != null) {
            entity.codigo(codigo);
        }
        return this;
    }

    public ContabilidadeFactory withDataLancamento(String dataLancamento) {
        if (!isBlankOrNull(dataLancamento)) {
            withDataLancamento(getDateFromString(dataLancamento));
        }
        return this;
    }

    public ContabilidadeFactory withDataLancamento(LocalDate dataLancamento) {
        if (dataLancamento != null) {
            entity.dataLancamento(dataLancamento);
        }
        return this;
    }

    public ContabilidadeFactory withDataAtualizacao(String dataAtualizacao) {
        if (!isBlankOrNull(dataAtualizacao)) {
            withDataAtualizacao(getDateFromString(dataAtualizacao));
        }
        return this;
    }

    public ContabilidadeFactory withDataAtualizacao(LocalDate dataAtualizacao) {
        if (dataAtualizacao != null) {
            entity.dataAtualizacao(dataAtualizacao);
        }
        return this;
    }

    public ContabilidadeFactory withDataVencimento(String dataVencimento) {
        if (!isBlankOrNull(dataVencimento)) {
            entity.dataVencimento(getDateFromString(dataVencimento));
        }
        return this;
    }

    public ContabilidadeFactory withDataPagamento(String dataPagamento) {
        if (!isBlankOrNull(dataPagamento)) {
            withDataPagamento(getDateFromString(dataPagamento));
        }
        return this;
    }

    public ContabilidadeFactory withDataPagamento(LocalDate dataPagamento) {
        if (dataPagamento != null) {
            entity.dataPagamento(dataPagamento);
        }
        return this;
    }

    public ContabilidadeFactory withRecorrente(String recorrente) {
        if (!isBlankOrNull(recorrente)) {
            withRecorrente(getBooleanFromString(recorrente));
        }
        return this;
    }

    public ContabilidadeFactory withRecorrente(Boolean recorrente) {
        if (recorrente != null) {
            entity.recorrente(recorrente);
        }
        return this;
    }

    public ContabilidadeFactory withGrupo(String grupo, String subGrupo) {
        if (!isBlankOrNull(grupo)) {
            Grupo grupoObj = GrupoFactory
                    .begin()
                    .withNome(grupo)
                    .withSubGrupo(subGrupo)
                    .build();
            entity.grupo(grupoObj);
        }
        return this;
    }

    public ContabilidadeFactory withLocal(String local) {
        if (!isBlankOrNull(local)) {
            Local localObj = LocalFactory
                    .begin()
                    .withNome(local)
                    .build();
            entity.local(localObj);
        }
        return this;
    }

    public ContabilidadeFactory withDescricao(String descricao) {
        if (!isBlankOrNull(descricao)) {
            entity.descricao(descricao);
        }
        return this;
    }

    public ContabilidadeFactory withUsouCartao(String usouCartao) {
        if (!isBlankOrNull(usouCartao)) {
            entity.usouCartao(getBooleanFromString(usouCartao));
        }
        return this;
    }

    public ContabilidadeFactory withCartao(String cartao) {
        if (!isBlankOrNull(cartao)) {
            Cartao cartaoObj = new Cartao()
                    .numero(cartao);
            entity.cartao(cartaoObj);
        }
        return this;
    }

    public ContabilidadeFactory withParcelado(String parcelado) {
        if (!isBlankOrNull(parcelado)) {
            entity.parcelado(getBooleanFromString(parcelado));
        }
        return this;
    }

    public ContabilidadeFactory withParcelamento(String parcela, String parcelas) {
        if (!isBlankOrNull(parcelas)) {
            Parcelamento parcelamento = ParcelamentoFactory
                    .begin()
                    .withParcela(parcela)
                    .withParcelas(parcelas)
                    .build();
            entity.parcelamento(parcelamento);
        }
        return this;
    }

    public ContabilidadeFactory withConta(String conta) {
        if (!isBlankOrNull(conta)) {
            Conta contaObj = ContaFactory
                    .begin()
                    .withNome(conta)
                    .build();
            entity.conta(contaObj);
        }
        return this;
    }

    public ContabilidadeFactory withTipo(String tipo) {
        if (!isBlankOrNull(tipo)) {
            withTipo(TipoContabilidade.valueOf(tipo));
        }
        return this;
    }

    public ContabilidadeFactory withTipo(TipoContabilidade tipo) {
        if (tipo != null) {
            entity.tipo(tipo);
        }
        return this;
    }

    public ContabilidadeFactory withValor(String valor) throws ParseException {
        if (!isBlankOrNull(valor)) {
            entity.valor(getDoubleFromString(valor));
        }
        return this;
    }

    public ContabilidadeFactory withCodigoPai(String codigoPai) {
        if (!isBlankOrNull(codigoPai)) {
            entity.codigoPai(Long.parseLong(codigoPai));
        }
        return this;
    }

    public ContabilidadeFactory withProximoLancamento(String proximoLancamento) {
        if (!isBlankOrNull(proximoLancamento)) {
            withProximoLancamento(Long.parseLong(proximoLancamento));
        }
        return this;
    }

    public ContabilidadeFactory withProximoLancamento(Long proximoLancamento) {
        if (proximoLancamento != null) {
            entity.proximoLancamento(proximoLancamento);
        }
        return this;
    }
}
