package br.com.accounting.core.service.impl;

import br.com.accounting.core.entity.SubGrupo;
import br.com.accounting.core.repository.SubGrupoRepository;
import br.com.accounting.core.service.SubGrupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubGrupoServiceImpl extends GenericService<SubGrupo> implements SubGrupoService {

    @Autowired
    public SubGrupoServiceImpl(SubGrupoRepository repository) {
        super(repository);
    }
}
