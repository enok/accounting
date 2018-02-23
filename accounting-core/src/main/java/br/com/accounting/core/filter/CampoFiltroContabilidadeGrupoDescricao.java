package br.com.accounting.core.filter;

import br.com.accounting.core.entity.Contabilidade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

public class CampoFiltroContabilidadeGrupoDescricao implements CampoFiltro<Contabilidade, Contabilidade> {
    private static final Logger LOG = LoggerFactory.getLogger(CampoFiltroContabilidadeGrupoDescricao.class);

    private String descricao;

    public CampoFiltroContabilidadeGrupoDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public List<Contabilidade> filtrar(List<Contabilidade> entities) {
        LOG.info("[ filtrar ]");
        LOG.debug("entities: " + entities);

        return entities
                .stream()
                .filter(c -> descricaoEhIgual(c))
                .collect(Collectors.toList());
    }

    private boolean descricaoEhIgual(Contabilidade c) {
        return c.getGrupo().getDescricao().equals(descricao);
    }
}
