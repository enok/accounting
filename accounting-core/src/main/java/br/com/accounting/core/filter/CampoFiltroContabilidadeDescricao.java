package br.com.accounting.core.filter;

import br.com.accounting.core.entity.Contabilidade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

public class CampoFiltroContabilidadeDescricao implements CampoFiltro<Contabilidade, Contabilidade> {
    private static final Logger LOG = LoggerFactory.getLogger(CampoFiltroContabilidadeDescricao.class);

    private String descricao;

    public CampoFiltroContabilidadeDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public List<Contabilidade> filtrar(List<Contabilidade> entities) {
        LOG.info("[ filtrar ]");
        LOG.debug("entities: " + entities);

        return entities
                .stream()
                .filter(c -> c.getDescricao().equals(descricao))
                .collect(Collectors.toList());
    }
}
