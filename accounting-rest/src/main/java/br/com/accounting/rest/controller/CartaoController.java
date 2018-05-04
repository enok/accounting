package br.com.accounting.rest.controller;

import br.com.accounting.business.dto.CartaoDTO;
import br.com.accounting.business.exception.BusinessException;
import br.com.accounting.business.exception.GenericException;
import br.com.accounting.business.service.CartaoBusiness;
import br.com.accounting.core.exception.StoreException;
import br.com.accounting.rest.vo.CodigosVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cartao")
public class CartaoController extends GenericController {
    @Autowired
    private CartaoBusiness cartaoBusiness;

    @PostMapping
    @ResponseBody
    public ResponseEntity<CodigosVO> criar(@RequestBody CartaoDTO cartaoDTO) throws StoreException, BusinessException, GenericException {
        List<Long> codigos = cartaoBusiness.criar(cartaoDTO);
        CodigosVO codigosVO = new CodigosVO(codigos);
        return ResponseEntity.status(HttpStatus.CREATED).body(codigosVO);
    }
}
