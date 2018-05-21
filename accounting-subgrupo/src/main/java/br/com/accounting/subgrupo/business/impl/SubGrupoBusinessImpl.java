package br.com.accounting.subgrupo.business.impl;

import br.com.accounting.commons.entity.SubGrupo;
import br.com.accounting.commons.exception.*;
import br.com.accounting.commons.service.impl.GenericAbstractBusiness;
import br.com.accounting.subgrupo.business.SubGrupoBusiness;
import br.com.accounting.subgrupo.dto.SubGrupoDTO;
import br.com.accounting.subgrupo.factory.SubGrupoDTOFactory;
import br.com.accounting.subgrupo.factory.SubGrupoFactory;
import br.com.accounting.subgrupo.service.SubGrupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;
import static org.apache.commons.lang3.StringUtils.isBlank;

@Service
public class SubGrupoBusinessImpl extends GenericAbstractBusiness<SubGrupoDTO, SubGrupo> implements SubGrupoBusiness {
    private SubGrupoService service;

    @Autowired
    public SubGrupoBusinessImpl(SubGrupoService service) {
        super(service, SubGrupoDTOFactory.create());
        this.service = service;
    }

    @Override
    public void validarEntrada(final SubGrupoDTO dto, final List<String> erros) throws MissingFieldException, StoreException, ParseException, CreateException {
        if (isBlank(dto.nome())) {
            erros.add(format(msg, "nome"));
        }
        if (isBlank(dto.descricao())) {
            erros.add(format(msg, "descrição"));
        }
        conferirErrosCamposObrigatorios(erros);
    }

    @Override
    public void validarEntradaUpdate(final SubGrupoDTO dto, final SubGrupo entity, final List<String> erros) throws ValidationException {
        conferirErrosCamposObrigatorios(erros);

        List<String> errosUpdate = new ArrayList<>();
        conferirErrosUpdate(errosUpdate);
    }

    @Override
    public void validaRegistroDuplicado(final SubGrupo entity) throws StoreException, ParseException, DuplicatedRegistryException {
        SubGrupo entityBuscada = service.buscarPorNome(entity.nome());

        if (entity.equals(entityBuscada)) {
            throw new DuplicatedRegistryException("SubGrupo duplicado.");
        }
    }

    @Override
    public SubGrupo criarEntity(final SubGrupoDTO dto) {
        return SubGrupoFactory
                .begin()
                .withCodigo(dto.codigo())
                .withNome(dto.nome())
                .withDescricao(dto.descricao())
                .build();
    }

    @Override
    protected SubGrupo criarEntity(SubGrupoDTO dto, SubGrupo entityBuscado) {
        return criarEntity(dto);
    }
}
