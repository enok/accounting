package br.com.accounting.core.filter;

import br.com.accounting.core.entity.Contabilidade;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static br.com.accounting.core.util.Utils.entreDatas;
import static br.com.accounting.core.util.Utils.getDateFromString;

public class CampoFiltroContabilidadeVencimento implements CampoFiltro<Contabilidade, Contabilidade> {
    private static final Logger LOG = LoggerFactory.getLogger(CampoFiltroContabilidadeVencimento.class);

    private LocalDate vencimentoInicial;
    private LocalDate vencimentoFinal;

    public CampoFiltroContabilidadeVencimento(String vencimentoInicial, String vencimentoFinal) {
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
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
