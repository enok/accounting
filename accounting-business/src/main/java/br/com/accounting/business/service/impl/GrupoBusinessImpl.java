package br.com.accounting.business.service.impl;

import br.com.accounting.business.dto.GrupoDTO;
import br.com.accounting.business.exception.DuplicatedRegistryException;
import br.com.accounting.business.exception.MissingFieldException;
import br.com.accounting.business.factory.GrupoDTOFactory;
import br.com.accounting.business.service.GrupoBusiness;
import br.com.accounting.core.entity.Grupo;
import br.com.accounting.core.exception.ServiceException;
import br.com.accounting.core.factory.GrupoFactory;
import br.com.accounting.core.service.GrupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.lang.String.format;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.springframework.util.CollectionUtils.isEmpty;

@Service
public class GrupoBusinessImpl extends GenericAbstractBusiness<GrupoDTO, Grupo> implements GrupoBusiness {
    private GrupoService service;

    @Autowired
    public GrupoBusinessImpl(final GrupoService service) {
        super(service, GrupoDTOFactory.create());
        this.service = service;
    }

    @Override
    public void validarEntrada(final GrupoDTO dto, final List<String> erros, final boolean atualizacao) throws MissingFieldException {
        String msg = "O campo %s é obrigatório.";

        if (atualizacao) {
            if (isBlank(dto.codigo())) {
                erros.add(format(msg, "código"));
            }
        }
        if (isBlank(dto.nome())) {
            erros.add(format(msg, "nome"));
        }
        if (isBlank(dto.descricao())) {
            erros.add(format(msg, "descrição"));
        }
        if (isEmpty(dto.subGrupos())) {
            erros.add(format(msg, "subGrupos"));
        }

        conferirErros(erros);
    }

    @Override
    public void validaRegistroDuplicado(final Grupo entity) throws ServiceException, DuplicatedRegistryException {
        Grupo entityBuscada = service.buscarPorNome(entity.nome());

        if (entity.equals(entityBuscada)) {
            throw new DuplicatedRegistryException("Grupo duplicado.");
        }
    }

    @Override
    public Grupo criarEntity(final GrupoDTO dto) {
        return GrupoFactory
                .begin()
                .withCodigo(dto.codigo())
                .withNome(dto.nome())
                .withDescricao(dto.descricao())
                .withSubGrupos(dto.subGrupos())
                .build();
    }
}
