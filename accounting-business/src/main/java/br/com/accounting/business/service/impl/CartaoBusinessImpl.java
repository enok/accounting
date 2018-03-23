package br.com.accounting.business.service.impl;

import br.com.accounting.business.annotation.History;
import br.com.accounting.business.dto.CartaoDTO;
import br.com.accounting.business.exception.BusinessException;
import br.com.accounting.business.exception.DuplicatedRegistryException;
import br.com.accounting.business.exception.MissingFieldException;
import br.com.accounting.business.factory.CartaoDTOFactory;
import br.com.accounting.business.service.CartaoBusiness;
import br.com.accounting.core.entity.Cartao;
import br.com.accounting.core.exception.ServiceException;
import br.com.accounting.core.factory.CartaoFactory;
import br.com.accounting.core.service.CartaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;
import static org.apache.commons.lang3.StringUtils.isBlank;

@Service
public class CartaoBusinessImpl implements CartaoBusiness {
    @Autowired
    private CartaoService cartaoService;

    @History
    @Override
    public Long criar(final CartaoDTO cartaoDTO) throws BusinessException {
        try {
            final List<String> erros = new ArrayList<>();

            validarEntrada(cartaoDTO, erros, false);

            Cartao cartao = CartaoFactory
                    .begin()
                    .withNumero(cartaoDTO.numero())
                    .withVencimento(cartaoDTO.vencimento())
                    .withDiaMelhorCompra(cartaoDTO.diaMelhorCompra())
                    .withPortador(cartaoDTO.portador())
                    .withTipo(cartaoDTO.tipo())
                    .withLimite(cartaoDTO.limite())
                    .build();

            validaRegistroDuplicado(cartao);

            return cartaoService.salvar(cartao);
        }
        catch (Exception e) {
            String message = "Não foi possível criar o cartão.";
            throw new BusinessException(message, e);
        }
    }

    @Override
    public void atualizar(final CartaoDTO cartaoDTO) throws BusinessException {
        try {
            final List<String> erros = new ArrayList<>();

            validarEntrada(cartaoDTO, erros, true);

            Cartao cartao = CartaoFactory
                    .begin()
                    .withCodigo(cartaoDTO.codigo())
                    .withNumero(cartaoDTO.numero())
                    .withVencimento(cartaoDTO.vencimento())
                    .withDiaMelhorCompra(cartaoDTO.diaMelhorCompra())
                    .withPortador(cartaoDTO.portador())
                    .withTipo(cartaoDTO.tipo())
                    .withLimite(cartaoDTO.limite())
                    .build();

            cartaoService.atualizar(cartao);
        }
        catch (Exception e) {
            String message = "Não foi possível atualizar o cartão.";
            throw new BusinessException(message, e);
        }
    }

    @Override
    public CartaoDTO buscarCartaoPorId(final Long codigo) throws BusinessException {
        try {
            Cartao cartao = cartaoService.buscarPorCodigo(codigo);
            return criarDTOEntity(CartaoDTOFactory.create(), cartao);
        }
        catch (Exception e) {
            String message = "Não foi possível buscar o cartão por id.";
            throw new BusinessException(message, e);
        }
    }

    @Override
    public void validarEntrada(final CartaoDTO dto, final List<String> erros, final boolean atualizacao) throws MissingFieldException {
        String msg = "O campo %s é obrigatório.";

        if (atualizacao) {
            if (isBlank(dto.codigo())) {
                erros.add(format(msg, "código"));
            }
        }
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

        conferirErros(erros);
    }

    @Override
    public void validaRegistroDuplicado(final Cartao entity) throws ServiceException, DuplicatedRegistryException {
        Cartao entityBuscado = cartaoService.buscarPorNumero(entity.numero());

        if (entity.equals(entityBuscado)) {
            throw new DuplicatedRegistryException("Cartão duplicado.");
        }
    }
}
