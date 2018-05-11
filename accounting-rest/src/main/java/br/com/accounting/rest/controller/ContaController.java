package br.com.accounting.rest.controller;

import br.com.accounting.business.dto.ContaDTO;
import br.com.accounting.business.exception.BusinessException;
import br.com.accounting.business.exception.GenericException;
import br.com.accounting.business.service.ContaBusiness;
import br.com.accounting.core.exception.StoreException;
import br.com.accounting.rest.controller.exception.AbstractExceptionHandler;
import br.com.accounting.rest.vo.CodigosVO;
import br.com.accounting.rest.vo.ContaVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.lang.String.valueOf;

@RestController
@RequestMapping("/conta")
public class ContaController extends AbstractExceptionHandler {
    @Autowired
    private ContaBusiness business;

    @PostMapping
    @ResponseBody
    public ResponseEntity criar(@RequestBody ContaVO vo) throws StoreException, BusinessException, GenericException {
        ContaDTO dto = createDTO(vo);
        List<Long> codigos = business.criar(dto);
        CodigosVO codigosVO = new CodigosVO(codigos);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(codigosVO);
    }

    @PutMapping
    @ResponseBody
    public ResponseEntity atualizar(@RequestBody ContaVO vo) throws StoreException, BusinessException, GenericException {
        ContaDTO dto = createDTO(vo);
        business.atualizar(dto);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @DeleteMapping("/{codigo}")
    public ResponseEntity excluir(@PathVariable Long codigo) throws StoreException, BusinessException, GenericException {
        ContaDTO dto = createDTO(codigo);
        business.excluir(dto);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @GetMapping("/{codigo}")
    public ResponseEntity buscarPorCodigo(@PathVariable Long codigo) throws StoreException, BusinessException, GenericException {
        ContaDTO dto = business.buscarPorCodigo(codigo);
        ContaVO vo = createVO(dto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(vo);
    }

    private ContaDTO createDTO(ContaVO vo) {
        return new ContaDTO()
                .codigo(vo.codigo())
                .nome(vo.nome())
                .descricao(vo.descricao())
                .valorDefault(vo.valorDefault())
                .saldo(vo.saldo())
                .cumulativo(vo.cumulativo())
                .dataAtualizacao(vo.dataAtualizacao());
    }

    private ContaDTO createDTO(Long codigo) {
        return new ContaDTO()
                .codigo(valueOf(codigo));
    }

    private ContaVO createVO(ContaDTO dto) {
        return new ContaVO()
                .codigo(dto.codigo())
                .nome(dto.nome())
                .descricao(dto.descricao())
                .valorDefault(dto.valorDefault())
                .saldo(dto.saldo())
                .cumulativo(dto.cumulativo())
                .dataAtualizacao(dto.dataAtualizacao());
    }
}
