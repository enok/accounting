package br.com.accounting.rest.controller;

import br.com.accounting.business.dto.GrupoDTO;
import br.com.accounting.commons.exception.BusinessException;
import br.com.accounting.commons.exception.GenericException;
import br.com.accounting.business.service.GrupoBusiness;
import br.com.accounting.commons.exception.StoreException;
import br.com.accounting.commons.exception.AbstractExceptionHandler;
import br.com.accounting.commons.vo.CodigosVO;
import br.com.accounting.rest.vo.GrupoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.valueOf;

@RestController
@RequestMapping("/grupo")
public class GrupoController extends AbstractExceptionHandler {
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
    public ResponseEntity excluir(@PathVariable Long codigo) throws StoreException, BusinessException, GenericException {
        GrupoDTO dto = createDTO(codigo);
        business.excluir(dto);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @GetMapping("/{codigo}")
    public ResponseEntity buscarPorCodigo(@PathVariable Long codigo) throws StoreException, BusinessException, GenericException {
        GrupoDTO dto = business.buscarPorCodigo(codigo);
        GrupoVO vo = createVO(dto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(vo);
    }

    @GetMapping
    public ResponseEntity buscarTudo() throws StoreException, GenericException {
        List<GrupoDTO> dtos = business.buscarTodas();
        List<GrupoVO> vos = createVOList(dtos);
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

    private GrupoDTO createDTO(GrupoVO vo) {
        return new GrupoDTO()
                .codigo(vo.codigo())
                .nome(vo.nome())
                .descricao(vo.descricao())
                .subGrupos(vo.subGrupos());
    }

    private GrupoDTO createDTO(Long codigo) {
        return new GrupoDTO()
                .codigo(valueOf(codigo));
    }

    private GrupoVO createVO(GrupoDTO dto) {
        return new GrupoVO()
                .codigo(dto.codigo())
                .nome(dto.nome())
                .descricao(dto.descricao())
                .subGrupos(dto.subGrupos());
    }

    private List<GrupoVO> createVOList(List<GrupoDTO> dtos) {
        List<GrupoVO> vos = new ArrayList<>();
        for (GrupoDTO dto : dtos) {
            vos.add(createVO(dto));
        }
        return vos;
    }
}
