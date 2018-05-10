package br.com.accounting.rest.controller;

import br.com.accounting.business.dto.LocalDTO;
import br.com.accounting.business.dto.LocalDTO;
import br.com.accounting.business.exception.BusinessException;
import br.com.accounting.business.exception.GenericException;
import br.com.accounting.business.service.LocalBusiness;
import br.com.accounting.core.exception.StoreException;
import br.com.accounting.rest.vo.CodigosVO;
import br.com.accounting.rest.vo.LocalVO;
import br.com.accounting.rest.vo.LocalVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/local")
public class LocalController extends GenericController {
    @Autowired
    private LocalBusiness business;

    @PostMapping
    @ResponseBody
    public ResponseEntity criar(@RequestBody LocalVO vo) throws StoreException, BusinessException, GenericException {
        LocalDTO dto = createDTO(vo);
        List<Long> codigos = business.criar(dto);
        CodigosVO codigosVO = new CodigosVO(codigos);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(codigosVO);
    }

    @PutMapping
    @ResponseBody
    public ResponseEntity atualizar(@RequestBody LocalVO vo) throws StoreException, BusinessException, GenericException {
        LocalDTO dto = createDTO(vo);
        business.atualizar(dto);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    private LocalDTO createDTO(LocalVO vo) {
        return new LocalDTO()
                .codigo(vo.codigo())
                .nome(vo.nome());
    }
}
