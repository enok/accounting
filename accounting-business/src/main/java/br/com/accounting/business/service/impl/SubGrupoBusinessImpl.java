package br.com.accounting.business.service.impl;

import br.com.accounting.business.dto.SubGrupoDTO;
import br.com.accounting.business.exception.DuplicatedRegistryException;
import br.com.accounting.business.exception.MissingFieldException;
import br.com.accounting.business.exception.UpdateException;
import br.com.accounting.business.exception.ValidationException;
import br.com.accounting.business.factory.SubGrupoDTOFactory;
import br.com.accounting.business.service.SubGrupoBusiness;
import br.com.accounting.core.entity.SubGrupo;
import br.com.accounting.core.exception.StoreException;
import br.com.accounting.core.factory.SubGrupoFactory;
import br.com.accounting.core.service.SubGrupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.format.DateTimeParseException;
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
    public void validarEntrada(final SubGrupoDTO dto, final List<String> erros) throws MissingFieldException {
        if (isBlank(dto.nome())) {
            erros.add(format(msg, "nome"));
        }
        if (isBlank(dto.descricao())) {
            erros.add(format(msg, "descrição"));
        }
        conferirErrosCamposObrigatorios(erros);
    }

    @Override
    public void validarEntradaUpdate(final SubGrupoDTO dto, final SubGrupo entity, final List<String> erros) throws MissingFieldException, UpdateException {
        conferirCodigo(dto, erros);
        conferirErrosCamposObrigatorios(erros);

        List<String> errosUpdate = new ArrayList<>();
        conferirCodigoAlterado(dto, entity, errosUpdate);
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
    public SubGrupo criarEntity(final SubGrupoDTO dto) throws ValidationException {
        try {
            return SubGrupoFactory
                    .begin()
                    .withCodigo(dto.codigo())
                    .withNome(dto.nome())
                    .withDescricao(dto.descricao())
                    .build();
        }
        catch (DateTimeParseException | IllegalArgumentException e) {
            throw new ValidationException(e);
        }
    }

    @Override
    protected SubGrupo criarEntity(SubGrupoDTO dto, SubGrupo entityBuscado) throws ValidationException {
        return criarEntity(dto);
    }
}
