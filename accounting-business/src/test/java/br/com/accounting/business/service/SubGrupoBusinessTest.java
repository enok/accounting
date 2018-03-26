package br.com.accounting.business.service;

import br.com.accounting.business.dto.SubGrupoDTO;
import br.com.accounting.business.exception.BusinessException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static br.com.accounting.business.factory.SubGrupoDTOMockFactory.subGrupoAluguel;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertTrue;

public class SubGrupoBusinessTest extends GenericTest {
    @Autowired
    private SubGrupoBusiness subGrupoBusiness;

    @Test
    public void criarUmSubGrupo() throws BusinessException {
        Long codigo = criarSubGrupoAluguel();
        assertSubGrupoAluguel(codigo);
    }

    private Long criarSubGrupoAluguel() throws BusinessException {
        SubGrupoDTO dto = subGrupoAluguel();
        Long codigo = subGrupoBusiness.criar(dto);
        assertTrue(codigo >= 0);
        return codigo;
    }

    private void assertSubGrupoAluguel(Long codigo) throws BusinessException {
        SubGrupoDTO dtoBuscada = subGrupoBusiness.buscarSubGrupoPorId(codigo);
        assertThat(dtoBuscada.nome(), equalTo("Aluguel"));
        assertThat(dtoBuscada.descricao(), equalTo("Aluguel pago todo mês, incluindo no valor iptu, seguro e condomínio"));
    }
}
