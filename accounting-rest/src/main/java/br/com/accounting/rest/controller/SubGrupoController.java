package br.com.accounting.rest.controller;

import br.com.accounting.business.dto.SubGrupoDTO;
import br.com.accounting.business.exception.BusinessException;
import br.com.accounting.business.exception.GenericException;
import br.com.accounting.business.service.SubGrupoBusiness;
import br.com.accounting.core.exception.StoreException;
import br.com.accounting.rest.entity.Codigos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/subgrupo")
public class SubGrupoController extends GenericController {
    @Autowired
    private SubGrupoBusiness subGrupoBusiness;

    @PostMapping
    @ResponseBody
    public ResponseEntity criar(@RequestBody SubGrupoDTO subGrupoDTO) throws StoreException, BusinessException, GenericException {
        List<Long> codigos = subGrupoBusiness.criar(subGrupoDTO);
        Codigos codigosObj = new Codigos(codigos);
        return ResponseEntity.status(HttpStatus.CREATED).body(codigosObj);
    }
}
