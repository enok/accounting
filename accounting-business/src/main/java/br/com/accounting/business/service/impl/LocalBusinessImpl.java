package br.com.accounting.business.service.impl;

import br.com.accounting.business.exception.DuplicatedRegistryException;
import br.com.accounting.business.exception.MissingFieldException;
import br.com.accounting.business.exception.UpdateException;
import br.com.accounting.business.dto.LocalDTO;
import br.com.accounting.business.exception.ValidationException;
import br.com.accounting.business.factory.LocalDTOFactory;
import br.com.accounting.business.service.LocalBusiness;
import br.com.accounting.core.entity.Local;
import br.com.accounting.core.exception.ServiceException;
import br.com.accounting.core.exception.StoreException;
import br.com.accounting.core.factory.GrupoFactory;
import br.com.accounting.core.factory.LocalFactory;
import br.com.accounting.core.service.LocalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;
import static org.apache.commons.lang3.StringUtils.isBlank;

@Service
public class LocalBusinessImpl extends GenericAbstractBusiness<LocalDTO, Local> implements LocalBusiness {
    private LocalService service;

    @Autowired
    public LocalBusinessImpl(final LocalService service) {
        super(service, LocalDTOFactory.create());
        this.service = service;
    }

    @Override
    public void validarEntrada(final LocalDTO dto, final List<String> erros) throws MissingFieldException {
        if (isBlank(dto.nome())) {
            erros.add(format(msg, "nome"));
        }
        conferirErrosCamposObrigatorios(erros);
    }

    @Override
    public void validarEntradaUpdate(final LocalDTO dto, final Local entity, final List<String> erros) throws MissingFieldException, UpdateException {
        conferirCodigo(dto, erros);
        conferirErrosCamposObrigatorios(erros);

        List<String> errosUpdate = new ArrayList<>();
        conferirCodigoAlterado(dto, entity, errosUpdate);
        conferirErrosUpdate(errosUpdate);
    }

    @Override
    public void validaRegistroDuplicado(final Local local) throws ServiceException, DuplicatedRegistryException, StoreException {
        Local localBuscada = service.buscarPorNome(local.nome());

        if (local.equals(localBuscada)) {
            throw new DuplicatedRegistryException("Local duplicado.");
        }
    }

    @Override
    public Local criarEntity(final LocalDTO dto) throws ValidationException {
        try {
            return LocalFactory
                    .begin()
                    .withCodigo(dto.codigo())
                    .withNome(dto.nome())
                    .build();
        }
        catch (DateTimeParseException | IllegalArgumentException e) {
            throw new ValidationException(e);
        }
    }

    @Override
    protected Local criarEntity(LocalDTO dto, Local entityBuscado) throws ValidationException {
        return criarEntity(dto);
    }
}
