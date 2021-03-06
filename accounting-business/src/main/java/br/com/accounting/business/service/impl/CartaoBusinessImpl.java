package br.com.accounting.business.service.impl;

import br.com.accounting.business.dto.CartaoDTO;
import br.com.accounting.business.exception.CreateException;
import br.com.accounting.business.exception.DuplicatedRegistryException;
import br.com.accounting.business.exception.MissingFieldException;
import br.com.accounting.business.exception.ValidationException;
import br.com.accounting.business.factory.CartaoDTOFactory;
import br.com.accounting.business.service.CartaoBusiness;
import br.com.accounting.core.entity.Cartao;
import br.com.accounting.core.exception.StoreException;
import br.com.accounting.core.factory.CartaoFactory;
import br.com.accounting.core.service.CartaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;
import static org.apache.commons.lang3.StringUtils.isBlank;

@Service
public class CartaoBusinessImpl extends GenericAbstractBusiness<CartaoDTO, Cartao> implements CartaoBusiness {
    private CartaoService service;

    @Autowired
    public CartaoBusinessImpl(final CartaoService service) {
        super(service, CartaoDTOFactory.create());
        this.service = service;
    }

    @Override
    public void validarEntrada(final CartaoDTO dto, final List<String> erros) throws MissingFieldException {
        if (isBlank(dto.numero())) {
            erros.add(format(msg, "número"));
        }
        if (isBlank(dto.vencimento())) {
            erros.add(format(msg, "vencimento"));
        }
        if (isBlank(dto.diaMelhorCompra())) {
            erros.add(format(msg, "diaMelhorCompra"));
        }
        if (isBlank(dto.portador())) {
            erros.add(format(msg, "portador"));
        }
        if (isBlank(dto.tipo())) {
            erros.add(format(msg, "tipo"));
        }
        if (isBlank(dto.limite())) {
            erros.add(format(msg, "limite"));
        }
        conferirErrosCamposObrigatorios(erros);
    }

    @Override
    public void validarEntradaUpdate(final CartaoDTO dto, final Cartao entity, final List<String> erros) throws ValidationException {
        conferirErrosCamposObrigatorios(erros);

        List<String> errosUpdate = new ArrayList<>();
        conferirErrosUpdate(errosUpdate);
    }

    @Override
    public void validaRegistroDuplicado(final Cartao entity) throws StoreException, ParseException, DuplicatedRegistryException {
        Cartao entityBuscado = service.buscarPorNumero(entity.numero());

        if (entity.equals(entityBuscado)) {
            throw new DuplicatedRegistryException("Cartão duplicado.");
        }
    }

    @Override
    public Cartao criarEntity(CartaoDTO dto) throws ValidationException {
        try {
            return CartaoFactory
                    .begin()
                    .withCodigo(dto.codigo())
                    .withNumero(dto.numero())
                    .withVencimento(dto.vencimento())
                    .withDiaMelhorCompra(dto.diaMelhorCompra())
                    .withPortador(dto.portador())
                    .withTipo(dto.tipo())
                    .withLimite(dto.limite())
                    .build();
        }
        catch (DateTimeParseException | ParseException | IllegalArgumentException e) {
            throw new ValidationException(e);
        }
    }

    @Override
    protected Cartao criarEntity(CartaoDTO dto, Cartao entityBuscado) throws ValidationException {
        return criarEntity(dto);
    }
}
