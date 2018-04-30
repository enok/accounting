package br.com.accounting.rest.controller;

import br.com.accounting.business.dto.ContaDTO;
import br.com.accounting.business.exception.BusinessException;
import br.com.accounting.business.exception.GenericException;
import br.com.accounting.business.service.ContaBusiness;
import br.com.accounting.core.exception.StoreException;
import br.com.accounting.rest.entity.Codigos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/conta")
public class ContaController extends GenericController {
    @Autowired
    private ContaBusiness contaBusiness;

    @PostMapping
    @ResponseBody
    public ResponseEntity criar(@RequestBody ContaDTO contaDTO) throws StoreException, BusinessException, GenericException {
        List<Long> codigos = contaBusiness.criar(contaDTO);
        Codigos codigosObj = new Codigos(codigos);
        return ResponseEntity.status(HttpStatus.CREATED).body(codigosObj);
    }
}
