package br.com.accounting.core.factory;

import br.com.accounting.core.entity.*;

import java.text.ParseException;
import java.time.LocalDate;

import static br.com.accounting.core.util.Utils.*;

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
//        if (entity.recorrente() == null) {
//            entity.recorrente(false);
//        }
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
            entity.codigo(Long.parseLong(codigo));
        }
        return this;
    }

    public ContabilidadeFactory withDataLancamento(String dataLancamento) {
        if (!isBlankOrNull(dataLancamento)) {
            entity.dataLancamento(getDateFromString(dataLancamento));
        }
        return this;
    }

    public ContabilidadeFactory withDataAtualizacao(String dataAtualizacao) {
        if (!isBlankOrNull(dataAtualizacao)) {
            entity.dataAtualizacao(getDateFromString(dataAtualizacao));
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
            entity.dataPagamento(getDateFromString(dataPagamento));
        }
        return this;
    }

    public ContabilidadeFactory withRecorrente(String recorrente) {
        if (!isBlankOrNull(recorrente)) {
            entity.recorrente(getBooleanFromString(recorrente));
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
            Cartao cartaoObj = CartaoFactory
                    .begin()
                    .withNumero(cartao)
                    .build();
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
            entity.tipo(TipoContabilidade.valueOf(tipo));
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
}
