package br.com.accounting.core.ordering;

import br.com.accounting.core.entity.Grupo;
import br.com.accounting.core.entity.Order;
import br.com.accounting.core.entity.SubGrupo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class CampoOrdemGrupoDescricaoSubGrupo implements CampoOrdem<SubGrupo, Grupo> {
    private static final Logger LOG = LoggerFactory.getLogger(CampoOrdemGrupoDescricaoSubGrupoDescricao.class);

    @Override
    public List<SubGrupo> ordenar(List<Grupo> entities, Order order) {
        LOG.info("[ ordenar ]");
        LOG.debug("entities: " + entities);
        LOG.debug("order: " + order);

        switch (order) {
            case DESC:
                return entities
                        .stream()
                        .sorted(Comparator.comparing(Grupo::getSubGrupoDescricao).reversed())
                        .map(c -> new SubGrupo(c.getSubGrupo().getCodigo(), c.getSubGrupo().getDescricao()))
                        .collect(Collectors.toList());
            case ASC:
            default:
                return entities
                        .stream()
                        .sorted(Comparator.comparing(Grupo::getSubGrupoDescricao))
                        .map(c -> new SubGrupo(c.getSubGrupo().getCodigo(), c.getSubGrupo().getDescricao()))
                        .collect(Collectors.toList());
        }
    }
}
