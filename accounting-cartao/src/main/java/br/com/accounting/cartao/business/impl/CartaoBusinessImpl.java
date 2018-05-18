package br.com.accounting.cartao.business.impl;

import br.com.accounting.cartao.business.CartaoBusiness;
import br.com.accounting.cartao.dto.CartaoDTO;
import br.com.accounting.commons.entity.Cartao;
import br.com.accounting.cartao.factory.CartaoDTOFactory;
import br.com.accounting.cartao.factory.CartaoFactory;
import br.com.accounting.cartao.service.CartaoService;
import br.com.accounting.commons.exception.DuplicatedRegistryException;
import br.com.accounting.commons.exception.MissingFieldException;
import br.com.accounting.commons.exception.StoreException;
import br.com.accounting.commons.exception.ValidationException;
import br.com.accounting.commons.service.impl.GenericAbstractBusiness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

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
            erros.add(String.format(GenericAbstractBusiness.msg, "número"));
        }
        if (isBlank(dto.vencimento())) {
            erros.add(String.format(GenericAbstractBusiness.msg, "vencimento"));
        }
        if (isBlank(dto.diaMelhorCompra())) {
            erros.add(String.format(GenericAbstractBusiness.msg, "diaMelhorCompra"));
        }
        if (isBlank(dto.portador())) {
            erros.add(String.format(GenericAbstractBusiness.msg, "portador"));
        }
        if (isBlank(dto.tipo())) {
            erros.add(String.format(GenericAbstractBusiness.msg, "tipo"));
        }
        if (isBlank(dto.limite())) {
            erros.add(String.format(GenericAbstractBusiness.msg, "limite"));
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
