package br.com.accounting.core.factory;

import br.com.accounting.core.entity.Parcelamento;

import static br.com.accounting.commons.util.Utils.isBlankOrNull;

public final class ParcelamentoFactory {
    private static ParcelamentoFactory factory;
    private Parcelamento entity;

    private ParcelamentoFactory() {
        entity = new Parcelamento();
    }

    public static ParcelamentoFactory begin() {
        factory = new ParcelamentoFactory();
        return factory;
    }

    public Parcelamento build() {
        return entity;
    }

    public ParcelamentoFactory withParcela(String parcela) {
        if (!isBlankOrNull(parcela)) {
            entity.parcela(Integer.parseInt(parcela));
        }
        return this;
    }

    public ParcelamentoFactory withParcelas(String parcelas) {
        if (!isBlankOrNull(parcelas)) {
            entity.parcelas(Integer.parseInt(parcelas));
        }
        return this;
    }
}
