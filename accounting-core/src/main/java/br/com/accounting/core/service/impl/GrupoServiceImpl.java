package br.com.accounting.core.service.impl;

import br.com.accounting.core.entity.GrupoEntity;
import br.com.accounting.core.service.GrupoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class GrupoServiceImpl implements GrupoService {
    private static final Logger LOG = LoggerFactory.getLogger(GrupoServiceImpl.class);

    @Override
    public Long salvarGrupo(GrupoEntity grupoEntity) {
        LOG.info("[ salvarGrupo ] grupoEntity: " + grupoEntity);
        Long codigo = 1L;
        grupoEntity.setCodigo(codigo);
        return codigo;
    }
}
