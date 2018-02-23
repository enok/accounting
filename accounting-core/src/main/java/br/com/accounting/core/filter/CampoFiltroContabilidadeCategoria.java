package br.com.accounting.core.filter;

import br.com.accounting.core.entity.Categoria;
import br.com.accounting.core.entity.Contabilidade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

public class CampoFiltroContabilidadeCategoria implements CampoFiltro<Contabilidade, Contabilidade> {
    private static final Logger LOG = LoggerFactory.getLogger(CampoFiltroContabilidadeCategoria.class);

    private Categoria categoria;

    public CampoFiltroContabilidadeCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    @Override
    public List<Contabilidade> filtrar(List<Contabilidade> entities) {
        LOG.info("[ filtrar ]");
        LOG.debug("entities: " + entities);

        return entities
                .stream()
                .filter(c -> c.getCategoria().equals(categoria))
                .collect(Collectors.toList());
    }
}
