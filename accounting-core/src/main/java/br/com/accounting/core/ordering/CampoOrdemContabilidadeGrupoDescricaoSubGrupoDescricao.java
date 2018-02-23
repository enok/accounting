package br.com.accounting.core.ordering;

import br.com.accounting.core.entity.Contabilidade;
import br.com.accounting.core.entity.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class CampoOrdemContabilidadeGrupoDescricaoSubGrupoDescricao implements CampoOrdem<Contabilidade, Contabilidade> {
    private static final Logger LOG = LoggerFactory.getLogger(CampoOrdemContabilidadeGrupoDescricao.class);

    @Override
    public List<Contabilidade> ordenar(List<Contabilidade> entities, Order order) {
        LOG.info("[ ordenar ]");
        LOG.debug("entities: " + entities);
        LOG.debug("order: " + order);

        switch (order) {
            case DESC:
                return entities
                        .stream()
                        .sorted(Comparator.comparing(Contabilidade::getGrupoDescricao).reversed()
                                .thenComparing(Comparator.comparing(Contabilidade::getSubGrupoDescricao).reversed()))
                        .collect(Collectors.toList());
            case ASC:
            default:
                return entities
                        .stream()
                        .sorted(Comparator.comparing(Contabilidade::getGrupoDescricao)
                                .thenComparing(Comparator.comparing(Contabilidade::getSubGrupoDescricao)))
                        .collect(Collectors.toList());
        }
    }
}
