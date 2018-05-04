package br.com.accounting.rest.controller;

import br.com.accounting.business.dto.ContabilidadeDTO;
import br.com.accounting.business.exception.BusinessException;
import br.com.accounting.business.exception.GenericException;
import br.com.accounting.business.service.ContabilidadeBusiness;
import br.com.accounting.core.exception.StoreException;
import br.com.accounting.rest.vo.CodigosVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contabilidade")
public class ContabilidadeController extends GenericController {
    @Autowired
    private ContabilidadeBusiness contabilidadeBusiness;

    @PostMapping
    @ResponseBody
    public ResponseEntity criar(@RequestBody ContabilidadeDTO contabilidadeDTO) throws StoreException, BusinessException, GenericException {
        List<Long> codigos = contabilidadeBusiness.criar(contabilidadeDTO);
        CodigosVO codigosVO = new CodigosVO(codigos);
        return ResponseEntity.status(HttpStatus.CREATED).body(codigosVO);
    }
}
