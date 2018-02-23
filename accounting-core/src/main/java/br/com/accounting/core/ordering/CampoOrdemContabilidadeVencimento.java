package br.com.accounting.core.ordering;

import br.com.accounting.core.entity.Contabilidade;
import br.com.accounting.core.entity.Order;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class CampoOrdemContabilidadeVencimento implements CampoOrdem<Contabilidade, Contabilidade> {
    private static final Logger LOG = LoggerFactory.getLogger(CampoOrdemContabilidadeVencimento.class);

    @Override
    public List<Contabilidade> ordenar(List<Contabilidade> entities, Order order) {
        LOG.info("[ ordenar ]");
        LOG.debug("entities: " + entities);
        LOG.debug("order: " + order);

        switch (order) {
            case DESC:
                return entities
                        .stream()
                        .sorted(Comparator.comparing(Contabilidade::getVencimento).reversed())
                        .collect(Collectors.toList());
            case ASC:
            default:
                return entities
                        .stream()
                        .sorted(Comparator.comparing(Contabilidade::getVencimento))
                        .collect(Collectors.toList());
        }
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
