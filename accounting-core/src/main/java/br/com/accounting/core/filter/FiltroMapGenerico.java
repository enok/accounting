package br.com.accounting.core.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public abstract class FiltroMapGenerico<E, T> {
    private static final Logger LOG = LoggerFactory.getLogger(FiltroMapGenerico.class);

    public List<E> filtrar(List<T> entities, Duplicates duplicates) {
        LOG.info("[ filtrar ]");
        LOG.debug("entities: " + entities);

        switch (duplicates) {
            case REMOVE:
                return removeDuplicates(getCollect(entities));
            case KEEP:
            default:
                return getCollect(entities);
        }
    }

    public abstract Predicate<T> getFiltroPredicado();

    public abstract Function<T, E> getMapPredicado();

    private List<E> removeDuplicates(List<E> list) {
        Set<E> set = new HashSet<>(list);
        return new ArrayList<>(set);
    }

    private List<E> getCollect(List<T> entities) {
        return entities
                .stream()
                .filter(getFiltroPredicado())
                .map(getMapPredicado())
                .collect(Collectors.toList());
    }
}
