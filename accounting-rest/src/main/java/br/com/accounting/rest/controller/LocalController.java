package br.com.accounting.rest.controller;

import br.com.accounting.business.dto.LocalDTO;
import br.com.accounting.business.service.LocalBusiness;
import br.com.accounting.commons.exception.AbstractExceptionHandler;
import br.com.accounting.commons.exception.BusinessException;
import br.com.accounting.commons.exception.GenericException;
import br.com.accounting.commons.exception.StoreException;
import br.com.accounting.commons.vo.CodigosVO;
import br.com.accounting.rest.vo.LocalVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.valueOf;

@RestController
@RequestMapping("/local")
public class LocalController extends AbstractExceptionHandler {
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

    @GetMapping
    public ResponseEntity buscarTudo() throws StoreException, GenericException {
        List<LocalDTO> dtos = business.buscarTodas();
        List<LocalVO> vos = createVOList(dtos);
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

    private List<LocalVO> createVOList(List<LocalDTO> dtos) {
        List<LocalVO> vos = new ArrayList<>();
        for (LocalDTO dto : dtos) {
            vos.add(createVO(dto));
        }
        return vos;
    }
}
