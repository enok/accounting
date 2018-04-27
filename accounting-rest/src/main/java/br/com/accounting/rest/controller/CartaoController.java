package br.com.accounting.rest.controller;

import br.com.accounting.business.dto.CartaoDTO;
import br.com.accounting.business.exception.*;
import br.com.accounting.business.service.CartaoBusiness;
import br.com.accounting.core.exception.StoreException;
import br.com.accounting.core.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cartao")
public class CartaoController extends GenericController {
    private Logger log = LoggerFactory.getLogger(CartaoController.class);

    private final String mensagem = "Erro ao criar cartão";

    @Autowired
    private CartaoBusiness cartaoBusiness;

    @PostMapping
    @ResponseBody
    public ResponseEntity criar(@RequestBody CartaoDTO cartaoDTO) throws StoreException, BusinessException, ValidationException, GenericException {
        List<Long> codigos = cartaoBusiness.criar(cartaoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(codigos);
    }

    @Override
    protected Logger getLog() {
        return log;
    }

    @Override
    protected String getMensagem() {
        return mensagem;
    }
}
