package br.com.accounting.rest.controller;

import br.com.accounting.business.dto.SubGrupoDTO;
import br.com.accounting.business.service.SubGrupoBusiness;
import br.com.accounting.commons.exception.AbstractExceptionHandler;
import br.com.accounting.commons.exception.BusinessException;
import br.com.accounting.commons.exception.GenericException;
import br.com.accounting.commons.exception.StoreException;
import br.com.accounting.commons.vo.CodigosVO;
import br.com.accounting.rest.vo.SubGrupoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.valueOf;

@RestController
@RequestMapping("/subgrupo")
public class SubGrupoController extends AbstractExceptionHandler {
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

    @GetMapping("/{codigo}")
    public ResponseEntity buscarPorCodigo(@PathVariable Long codigo) throws StoreException, BusinessException, GenericException {
        SubGrupoDTO dto = business.buscarPorCodigo(codigo);
        SubGrupoVO vo = createVO(dto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(vo);
    }

    @GetMapping
    public ResponseEntity buscarTudo() throws StoreException, GenericException {
        List<SubGrupoDTO> dtos = business.buscarTodas();
        List<SubGrupoVO> vos = createVOList(dtos);
        if (CollectionUtils.isEmpty(vos)) {
            return ResponseEntity
                    .status(HttpStatus.NO_CONTENT)
                    .build();
        }
        else {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(vos);
        }
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

    private SubGrupoVO createVO(SubGrupoDTO dto) {
        return new SubGrupoVO()
                .codigo(dto.codigo())
                .nome(dto.nome())
                .descricao(dto.descricao());
    }

    private List<SubGrupoVO> createVOList(List<SubGrupoDTO> dtos) {
        List<SubGrupoVO> vos = new ArrayList<>();
        for (SubGrupoDTO dto : dtos) {
            vos.add(createVO(dto));
        }
        return vos;
    }
}
