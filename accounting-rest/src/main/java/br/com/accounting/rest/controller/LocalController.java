package br.com.accounting.rest.controller;

import br.com.accounting.business.dto.LocalDTO;
import br.com.accounting.business.exception.BusinessException;
import br.com.accounting.business.exception.GenericException;
import br.com.accounting.business.service.LocalBusiness;
import br.com.accounting.core.exception.StoreException;
import br.com.accounting.rest.entity.Codigos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/local")
public class LocalController extends GenericController {
    @Autowired
    private LocalBusiness localBusiness;

    @PostMapping
    @ResponseBody
    public ResponseEntity criar(@RequestBody LocalDTO localDTO) throws StoreException, BusinessException, GenericException {
        List<Long> codigos = localBusiness.criar(localDTO);
        Codigos codigosObj = new Codigos(codigos);
        return ResponseEntity.status(HttpStatus.CREATED).body(codigosObj);
    }
}
