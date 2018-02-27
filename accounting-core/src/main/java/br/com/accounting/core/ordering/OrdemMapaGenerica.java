package br.com.accounting.core.ordering;

import br.com.accounting.core.entity.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public abstract class OrdemMapaGenerica<E, T> {
    private static final Logger LOG = LoggerFactory.getLogger(OrdemContabilidadeCategoria.class);

    public List<E> ordenar(List<T> entities, Order order) {
        LOG.info("[ ordenar ]");
        LOG.debug("entities: " + entities);
        LOG.debug("order: " + order);

        switch (order) {
            case DESC:
                return entities
                        .stream()
                        .sorted(getPredicate().reversed())
                        .map(getMap())
                        .collect(Collectors.toList());
            case ASC:
            default:
                return entities
                        .stream()
                        .sorted(getPredicate())
                        .map(getMap())
                        .collect(Collectors.toList());
        }
    }

    public abstract Comparator<T> getPredicate();

    public abstract Function<T, E> getMap();
}
