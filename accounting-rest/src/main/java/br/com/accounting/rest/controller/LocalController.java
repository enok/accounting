package br.com.accounting.rest.controller;

import br.com.accounting.business.dto.LocalDTO;
import br.com.accounting.business.exception.BusinessException;
import br.com.accounting.business.exception.GenericException;
import br.com.accounting.business.service.LocalBusiness;
import br.com.accounting.core.exception.StoreException;
import br.com.accounting.rest.vo.CodigosVO;
import br.com.accounting.rest.vo.LocalVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.lang.String.valueOf;

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

    @DeleteMapping("/{codigo}")
    public ResponseEntity excluir(@PathVariable Long codigo) throws StoreException, BusinessException, GenericException {
        LocalDTO dto = createDTO(codigo);
        business.excluir(dto);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @GetMapping("/{codigo}")
    public ResponseEntity buscarPorCodigo(@PathVariable Long codigo) throws StoreException, BusinessException, GenericException {
        LocalDTO dto = business.buscarPorCodigo(codigo);
        LocalVO vo = createVO(dto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(vo);
    }

    private LocalDTO createDTO(LocalVO vo) {
        return new LocalDTO()
                .codigo(vo.codigo())
                .nome(vo.nome());
    }

    private LocalDTO createDTO(Long codigo) {
        return new LocalDTO()
                .codigo(valueOf(codigo));
    }

    private LocalVO createVO(LocalDTO dto) {
        return new LocalVO()
                .codigo(dto.codigo())
                .nome(dto.nome());
    }
}
