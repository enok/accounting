package br.com.accounting.business.service.impl;

import br.com.accounting.business.dto.LocalDTO;
import br.com.accounting.business.exception.CreateException;
import br.com.accounting.business.exception.DuplicatedRegistryException;
import br.com.accounting.business.exception.MissingFieldException;
import br.com.accounting.business.exception.ValidationException;
import br.com.accounting.business.factory.LocalDTOFactory;
import br.com.accounting.business.service.LocalBusiness;
import br.com.accounting.core.entity.Local;
import br.com.accounting.core.exception.StoreException;
import br.com.accounting.core.factory.LocalFactory;
import br.com.accounting.core.service.LocalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
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
    public void validarEntradaUpdate(final LocalDTO dto, final Local entity, final List<String> erros) throws ValidationException {
        conferirErrosCamposObrigatorios(erros);

        List<String> errosUpdate = new ArrayList<>();
        conferirErrosUpdate(errosUpdate);
    }

    @Override
    public void validaRegistroDuplicado(final Local local) throws StoreException, ParseException, DuplicatedRegistryException {
        Local localBuscada = service.buscarPorNome(local.nome());

        if (local.equals(localBuscada)) {
            throw new DuplicatedRegistryException("Local duplicado.");
        }
    }

    @Override
    public Local criarEntity(final LocalDTO dto) {
        return LocalFactory
                .begin()
                .withCodigo(dto.codigo())
                .withNome(dto.nome())
                .build();
    }

    @Override
    protected Local criarEntity(LocalDTO dto, Local entityBuscado) {
        return criarEntity(dto);
    }
}
