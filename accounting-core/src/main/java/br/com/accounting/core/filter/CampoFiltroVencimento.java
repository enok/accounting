package br.com.accounting.core.filter;

import br.com.accounting.core.entity.Contabilidade;
import br.com.accounting.core.entity.Order;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static br.com.accounting.core.util.Utils.entreDatas;
import static br.com.accounting.core.util.Utils.getDateFromString;

public class CampoFiltroVencimento implements CampoFiltro<Contabilidade> {
    private static final Logger LOG = LoggerFactory.getLogger(CampoFiltroVencimento.class);

    private LocalDate vencimentoInicial;
    private LocalDate vencimentoFinal;

    public CampoFiltroVencimento() {
    }

    public CampoFiltroVencimento(String vencimentoInicial, String vencimentoFinal) {
        this.vencimentoInicial = getDateFromString(vencimentoInicial);
        this.vencimentoFinal = getDateFromString(vencimentoFinal);
    }

    @Override
    public List<Contabilidade> filtrar(List<Contabilidade> entities) {
        LOG.info("[ filtrar ]");
        LOG.debug("entities: " + entities);

        return entities
                .stream()
                .filter(c -> entreDatas(c.getVencimento(), vencimentoInicial, vencimentoFinal))
                .collect(Collectors.toList());
    }

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
