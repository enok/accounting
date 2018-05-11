package br.com.accounting.rest.controller;

import br.com.accounting.business.dto.SubGrupoDTO;
import br.com.accounting.business.exception.BusinessException;
import br.com.accounting.business.exception.GenericException;
import br.com.accounting.business.service.SubGrupoBusiness;
import br.com.accounting.core.exception.StoreException;
import br.com.accounting.rest.vo.CodigosVO;
import br.com.accounting.rest.vo.SubGrupoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.lang.String.valueOf;

@RestController
@RequestMapping("/subgrupo")
public class SubGrupoController extends GenericController {
    @Autowired
    private SubGrupoBusiness business;

    @PostMapping
    @ResponseBody
    public ResponseEntity criar(@RequestBody SubGrupoVO vo) throws StoreException, BusinessException, GenericException {
        SubGrupoDTO dto = createDTO(vo);
        List<Long> codigos = business.criar(dto);
        CodigosVO codigosVO = new CodigosVO(codigos);
        return ResponseEntity.status(HttpStatus.CREATED).body(codigosVO);
    }

    @PutMapping
    @ResponseBody
    public ResponseEntity atualizar(@RequestBody SubGrupoVO vo) throws StoreException, BusinessException, GenericException {
        SubGrupoDTO dto = createDTO(vo);
        business.atualizar(dto);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @DeleteMapping("/{codigo}")
    public ResponseEntity excluir(@PathVariable Long codigo) throws StoreException, BusinessException, GenericException {
        SubGrupoDTO dto = createDTO(codigo);
        business.excluir(dto);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    private SubGrupoDTO createDTO(SubGrupoVO vo) {
        return new SubGrupoDTO()
                .codigo(vo.codigo())
                .nome(vo.nome())
                .descricao(vo.descricao());
    }

    private SubGrupoDTO createDTO(Long codigo) {
        return new SubGrupoDTO()
                .codigo(valueOf(codigo));
    }
}
