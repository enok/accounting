package br.com.accounting.core.filter;

import br.com.accounting.core.entity.Contabilidade;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static br.com.accounting.core.util.Utils.entreDatas;
import static br.com.accounting.core.util.Utils.getDateFromString;

public class CampoFiltroVencimento implements CampoFiltro<Contabilidade> {
    private LocalDate vencimentoInicial;
    private LocalDate vencimentoFinal;

    public CampoFiltroVencimento(String vencimentoInicial, String vencimentoFinal) {
        this.vencimentoInicial = getDateFromString(vencimentoInicial);
        this.vencimentoFinal = getDateFromString(vencimentoFinal);
    }

    @Override
    public List<Contabilidade> filtrar(List<Contabilidade> entities) {
        return entities
                .stream()
                .filter(c -> entreDatas(c.getVencimento(), getVencimentoInicial(), getVencimentoFinal()))
                .collect(Collectors.toList());
    }

    public LocalDate getVencimentoInicial() {
        return vencimentoInicial;
    }

    public LocalDate getVencimentoFinal() {
        return vencimentoFinal;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
