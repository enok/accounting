package br.com.accounting.rest.controller;

import br.com.accounting.business.dto.CartaoDTO;
import br.com.accounting.business.dto.ContabilidadeDTO;
import br.com.accounting.business.exception.BusinessException;
import br.com.accounting.business.exception.GenericException;
import br.com.accounting.business.service.ContabilidadeBusiness;
import br.com.accounting.core.exception.StoreException;
import br.com.accounting.rest.controller.exception.AbstractExceptionHandler;
import br.com.accounting.rest.vo.CartaoVO;
import br.com.accounting.rest.vo.CodigosVO;
import br.com.accounting.rest.vo.ContabilidadeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.*;

@RestController
@RequestMapping("/contabilidade")
public class ContabilidadeController extends AbstractExceptionHandler {
    @Autowired
    private ContabilidadeBusiness business;

    @PostMapping
    @ResponseBody
    public ResponseEntity criar(@RequestBody ContabilidadeVO vo) throws StoreException, BusinessException, GenericException {
        ContabilidadeDTO dto = createDTO(vo);
        List<Long> codigos = business.criar(dto);
        CodigosVO codigosVO = new CodigosVO(codigos);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(codigosVO);
    }

    @PutMapping
    public ResponseEntity atualizar(@RequestBody ContabilidadeVO vo) throws StoreException, BusinessException, GenericException {
        ContabilidadeDTO dto = createDTO(vo);
        business.atualizar(dto);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @DeleteMapping("/{codigo}")
    public ResponseEntity excluir(@PathVariable Long codigo) throws StoreException, BusinessException, GenericException {
        ContabilidadeDTO dto = createDTO(codigo);
        business.excluir(dto);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @GetMapping("/{codigo}")
    public ResponseEntity buscarPorCodigo(@PathVariable Long codigo) throws StoreException, BusinessException, GenericException {
        ContabilidadeDTO dto = business.buscarPorCodigo(codigo);
        ContabilidadeVO vo = createVO(dto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(vo);
    }

    @GetMapping
    public ResponseEntity buscarTudo() throws StoreException, GenericException {
        List<ContabilidadeDTO> dtos = business.buscarTodas();
        List<ContabilidadeVO> vos = createVOList(dtos);
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

    private ContabilidadeDTO createDTO(ContabilidadeVO vo) {
        return new ContabilidadeDTO()
                .codigo(vo.codigo())
                .dataLancamento(vo.dataLancamento())
                .dataVencimento(vo.dataVencimento())
                .recorrente(vo.recorrente())
                .grupo(vo.grupo())
                .subGrupo(vo.subGrupo())
                .local(vo.local())
                .descricao(vo.descricao())
                .usouCartao(vo.usouCartao())
                .cartao(vo.cartao())
                .parcelado(vo.parcelado())
                .parcela(vo.parcela())
                .parcelas(vo.parcelas())
                .conta(vo.conta())
                .tipo(vo.tipo())
                .valor(vo.valor())
                .codigoPai(vo.codigoPai());
    }

    private ContabilidadeDTO createDTO(Long codigo) {
        return new ContabilidadeDTO()
                .codigo(valueOf(codigo));
    }

    private ContabilidadeVO createVO(ContabilidadeDTO dto) {
        return new ContabilidadeVO()
                .codigo(dto.codigo())
                .dataLancamento(dto.dataLancamento())
                .dataAtualizacao(dto.dataAtualizacao())
                .dataVencimento(dto.dataVencimento())
                .dataPagamento(dto.dataPagamento())
                .recorrente(dto.recorrente())
                .grupo(dto.grupo())
                .subGrupo(dto.subGrupo())
                .local(dto.local())
                .descricao(dto.descricao())
                .usouCartao(dto.usouCartao())
                .cartao(dto.cartao())
                .parcelado(dto.parcelado())
                .parcela(dto.parcela())
                .parcelas(dto.parcelas())
                .conta(dto.conta())
                .tipo(dto.tipo())
                .valor(dto.valor())
                .codigoPai(dto.codigoPai())
                .proximoLancamento(dto.proximoLancamento());
    }

    private List<ContabilidadeVO> createVOList(List<ContabilidadeDTO> dtos) {
        List<ContabilidadeVO> vos = new ArrayList<>();
        for (ContabilidadeDTO dto : dtos) {
            vos.add(createVO(dto));
        }
        return vos;
    }
}
