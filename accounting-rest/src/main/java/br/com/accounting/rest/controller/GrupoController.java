package br.com.accounting.rest.controller;

import br.com.accounting.business.dto.GrupoDTO;
import br.com.accounting.business.dto.GrupoDTO;
import br.com.accounting.business.exception.BusinessException;
import br.com.accounting.business.exception.GenericException;
import br.com.accounting.business.service.GrupoBusiness;
import br.com.accounting.core.exception.StoreException;
import br.com.accounting.rest.vo.CodigosVO;
import br.com.accounting.rest.vo.GrupoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/grupo")
public class GrupoController extends GenericController {
    @Autowired
    private GrupoBusiness business;

    @PostMapping
    @ResponseBody
    public ResponseEntity criar(@RequestBody GrupoVO vo) throws StoreException, BusinessException, GenericException {
        GrupoDTO dto = createDTO(vo);
        List<Long> codigos = business.criar(dto);
        CodigosVO codigosVO = new CodigosVO(codigos);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(codigosVO);
    }

    @PutMapping
    @ResponseBody
    public ResponseEntity atualizar(@RequestBody GrupoVO vo) throws StoreException, BusinessException, GenericException {
        GrupoDTO dto = createDTO(vo);
        business.atualizar(dto);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @DeleteMapping("/{codigo}")
    public ResponseEntity excluir(@PathVariable Integer codigo) throws StoreException, BusinessException, GenericException {
        GrupoDTO dto = createDTO(codigo);
        business.excluir(dto);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    private GrupoDTO createDTO(GrupoVO vo) {
        return new GrupoDTO()
                .codigo(vo.codigo())
                .nome(vo.nome())
                .descricao(vo.descricao())
                .subGrupos(vo.subGrupos());
    }

    private GrupoDTO createDTO(Integer codigo) {
        return new GrupoDTO()
                .codigo(String.valueOf(codigo));
    }
}
