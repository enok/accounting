package br.com.accounting.rest.controller;

import br.com.accounting.business.dto.GrupoDTO;
import br.com.accounting.business.exception.BusinessException;
import br.com.accounting.business.exception.GenericException;
import br.com.accounting.business.service.GrupoBusiness;
import br.com.accounting.core.exception.StoreException;
import br.com.accounting.rest.vo.CodigosVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/grupo")
public class GrupoController extends GenericController {
    @Autowired
    private GrupoBusiness grupoBusiness;

    @PostMapping
    @ResponseBody
    public ResponseEntity criar(@RequestBody GrupoDTO grupoDTO) throws StoreException, BusinessException, GenericException {
        List<Long> codigos = grupoBusiness.criar(grupoDTO);
        CodigosVO codigosVO = new CodigosVO(codigos);
        return ResponseEntity.status(HttpStatus.CREATED).body(codigosVO);
    }
}
