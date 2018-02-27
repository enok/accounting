package br.com.accounting.core.filter;

import br.com.accounting.core.entity.Contabilidade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

public class CampoFiltroContabilidadeParcelamentoPai implements CampoFiltro<Contabilidade, Contabilidade> {
    private static final Logger LOG = LoggerFactory.getLogger(CampoFiltroContabilidadeParcelamentoPai.class);

    private Long codigoPai;

    public CampoFiltroContabilidadeParcelamentoPai(Long codigoPai) {
        this.codigoPai = codigoPai;
    }

    @Override
    public List<Contabilidade> filtrar(List<Contabilidade> entities) {
        LOG.info("[ filtrar ]");
        LOG.debug("entities: " + entities);

        return entities
                .stream()
                .filter(c -> comparacaoCodigoPai(c))
                .collect(Collectors.toList());
    }

    private boolean comparacaoCodigoPai(Contabilidade c) {
        return (c.getParcelamento() != null) && (c.getParcelamento().getCodigoPai().equals(codigoPai));
    }
}
