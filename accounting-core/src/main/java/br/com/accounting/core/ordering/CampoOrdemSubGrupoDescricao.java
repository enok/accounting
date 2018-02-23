package br.com.accounting.core.ordering;

import br.com.accounting.core.entity.Order;
import br.com.accounting.core.entity.SubGrupo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class CampoOrdemSubGrupoDescricao implements CampoOrdem<SubGrupo, SubGrupo> {
    private static final Logger LOG = LoggerFactory.getLogger(CampoOrdemSubGrupoDescricao.class);

    @Override
    public List<SubGrupo> ordenar(List<SubGrupo> entities, Order order) {
        LOG.info("[ ordenar ]");
        LOG.debug("entities: " + entities);
        LOG.debug("order: " + order);

        switch (order) {
            case DESC:
                return entities
                        .stream()
                        .sorted(Comparator.comparing(SubGrupo::getDescricao).reversed())
                        .collect(Collectors.toList());
            case ASC:
            default:
                return entities
                        .stream()
                        .sorted(Comparator.comparing(SubGrupo::getDescricao))
                        .collect(Collectors.toList());
        }
    }
}
