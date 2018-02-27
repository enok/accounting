package br.com.accounting.core.ordering;

import br.com.accounting.core.entity.Contabilidade;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Comparator;

public class OrdemContabilidadeVencimento extends OrdemGenerica<Contabilidade> implements Ordem<Contabilidade, Contabilidade> {

    @Override
    public Comparator<Contabilidade> getComparator() {
        return Comparator.comparing(Contabilidade::getVencimento);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
