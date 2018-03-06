package br.com.accounting.core.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public abstract class FiltroGenerico<T> {
    private static final Logger LOG = LoggerFactory.getLogger(FiltroGenerico.class);

    public List<T> filtrar(List<T> entities, Duplicates duplicates) {
        LOG.info("[ filtrar ]");
        LOG.debug("entities: " + entities);
        LOG.debug("duplicates: " + duplicates);

        switch (duplicates) {
            case REMOVE:
                return removeDuplicates(getCollect(entities));
            case KEEP:
            default:
                return getCollect(entities);
        }
    }

    public T filtrarSingle(List<T> entities) {
        LOG.info("[ filtrarUm ]");
        LOG.debug("entities: " + entities);

        return getCollectSingle(entities);
    }

    public abstract Predicate<T> getPredicate();

    private List<T> removeDuplicates(List<T> list) {
        Set<T> set = new HashSet<>(list);
        return new ArrayList<>(set);
    }

    private List<T> getCollect(List<T> entities) {
        return entities
                .stream()
                .filter(getPredicate())
                .collect(Collectors.toList());
    }

    private T getCollectSingle(List<T> entities) {
        return entities
                .stream()
                .filter(getPredicate())
                .collect(Collectors.toList()).get(0);
    }
}
