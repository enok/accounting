package br.com.accounting.core.filter;

import br.com.accounting.core.entity.Contabilidade;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.time.LocalDate;
import java.util.function.Predicate;

import static br.com.accounting.core.util.Utils.entreDatas;
import static br.com.accounting.core.util.Utils.getDateFromString;

public class FiltroContabilidadeVencimento extends FiltroGenerico<Contabilidade> implements Filtro<Contabilidade, Contabilidade> {
    private LocalDate vencimentoInicial;
    private LocalDate vencimentoFinal;

    public FiltroContabilidadeVencimento(String vencimentoInicial, String vencimentoFinal) {
        this.vencimentoInicial = getDateFromString(vencimentoInicial);
        this.vencimentoFinal = getDateFromString(vencimentoFinal);
    }

    @Override
    public Predicate<Contabilidade> getFiltroPredicado() {
        return c -> entreDatas(c.getVencimento(), vencimentoInicial, vencimentoFinal);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
