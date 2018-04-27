package br.com.accounting.rest.controller;

import br.com.accounting.business.dto.CartaoDTO;
import br.com.accounting.business.exception.BusinessException;
import br.com.accounting.business.exception.GenericException;
import br.com.accounting.business.service.CartaoBusiness;
import br.com.accounting.core.exception.StoreException;
import br.com.accounting.rest.entity.Codigos;
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

    @Autowired
    private CartaoBusiness cartaoBusiness;

    @PostMapping
    @ResponseBody
    public ResponseEntity criar(@RequestBody CartaoDTO cartaoDTO) throws StoreException, BusinessException, GenericException {
        List<Long> codigos = cartaoBusiness.criar(cartaoDTO);
        Codigos codigosObj = new Codigos(codigos);
        return ResponseEntity.status(HttpStatus.CREATED).body(codigosObj);
    }

    @Override
    protected Logger getLog() {
        return log;
    }
}
