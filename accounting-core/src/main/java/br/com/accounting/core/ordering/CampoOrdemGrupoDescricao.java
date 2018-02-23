package br.com.accounting.core.ordering;

import br.com.accounting.core.entity.Grupo;
import br.com.accounting.core.entity.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class CampoOrdemGrupoDescricao implements CampoOrdem<Grupo, Grupo> {
    private static final Logger LOG = LoggerFactory.getLogger(CampoOrdemGrupoDescricao.class);

    @Override
    public List<Grupo> ordenar(List<Grupo> entities, Order order) {
        LOG.info("[ ordenar ]");
        LOG.debug("entities: " + entities);
        LOG.debug("order: " + order);

        switch (order) {
            case DESC:
                return entities
                        .stream()
                        .sorted(Comparator.comparing(Grupo::getDescricao).reversed()
                                .thenComparing(Comparator.comparing(Grupo::getSubGrupoDescricao).reversed()))
                        .collect(Collectors.toList());
            case ASC:
            default:
                return entities
                        .stream()
                        .sorted(Comparator.comparing(Grupo::getDescricao)
                                .thenComparing(Comparator.comparing(Grupo::getSubGrupoDescricao)))
                        .collect(Collectors.toList());
        }
    }
}
