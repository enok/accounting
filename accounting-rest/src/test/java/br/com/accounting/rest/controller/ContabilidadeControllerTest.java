package br.com.accounting.rest.controller;

import br.com.accounting.business.dto.LocalDTO;
import br.com.accounting.business.dto.SubGrupoDTO;
import br.com.accounting.business.exception.BusinessException;
import br.com.accounting.business.exception.GenericException;
import br.com.accounting.business.factory.LocalDTOFactory;
import br.com.accounting.business.factory.SubGrupoDTOFactory;
import br.com.accounting.core.exception.StoreException;
import br.com.accounting.rest.vo.CartaoVO;
import br.com.accounting.rest.vo.ContaVO;
import br.com.accounting.rest.vo.ContabilidadeVO;
import br.com.accounting.rest.vo.GrupoVO;
import com.google.gson.Gson;
import org.hamcrest.collection.IsCollectionWithSize;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.IOException;
import java.util.Arrays;

import static br.com.accounting.core.util.Utils.getStringFromCurrentDate;
import static java.util.Arrays.*;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
public class ContabilidadeControllerTest extends GenericTest {
    @Autowired
    private ContabilidadeController controller;
    @Autowired
    private GrupoController grupoController;
    @Autowired
    private SubGrupoController subGrupoController;
    @Autowired
    private LocalController localController;
    @Autowired
    private CartaoController cartaoController;
    @Autowired
    private ContaController contaController;

    private MockMvc mvc;
    private Gson gson;

    @Before
    public void setup() {
        mvc = MockMvcBuilders.standaloneSetup(controller).build();
        gson = new Gson();
    }

    @Before
    public void setUp() throws IOException, StoreException, BusinessException, GenericException {
        super.setUp();
        criarGrupo();
        criarSubGrupo();
        criarLocal();
        criarCartao();
        criarConta();
    }

    @Test
    public void criarSemDiretorio() throws Exception {
        deletarDiretorioEArquivos();

        ContabilidadeVO vo = getVO();
        String json = gson.toJson(vo);

        mvc.perform(post("/contabilidade")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInsufficientStorage())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(507)))
                .andExpect(jsonPath("$.mensagens", hasSize(1)))
                .andExpect(jsonPath("$.mensagens[0]", is("Erro de persistência ao salvar.")));

    }

    @Test
    public void criarSemDataVencimento() throws Exception {
        ContabilidadeVO vo = getVO()
                .dataVencimento(null);
        String json = gson.toJson(vo);

        mvc.perform(post("/contabilidade")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(400)))
                .andExpect(jsonPath("$.mensagens", hasSize(1)))
                .andExpect(jsonPath("$.mensagens[0]", is("O campo dataVencimento é obrigatório.")));
    }

    @Test
    public void criarSemDataVencimentoERecorrente() throws Exception {
        ContabilidadeVO vo = getVO()
                .dataVencimento(null)
                .recorrente(null);
        String json = gson.toJson(vo);

        mvc.perform(post("/contabilidade")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(400)))
                .andExpect(jsonPath("$.mensagens", hasSize(2)))
                .andExpect(jsonPath("$.mensagens[0]", is("O campo dataVencimento é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[1]", is("O campo recorrente é obrigatório.")));
    }

    @Test
    public void criarSemDataVencimentoERecorrenteEGrupo() throws Exception {
        ContabilidadeVO vo = getVO()
                .dataVencimento(null)
                .recorrente(null)
                .grupo(null);
        String json = gson.toJson(vo);

        mvc.perform(post("/contabilidade")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(400)))
                .andExpect(jsonPath("$.mensagens", hasSize(3)))
                .andExpect(jsonPath("$.mensagens[0]", is("O campo dataVencimento é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[1]", is("O campo recorrente é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[2]", is("O campo grupo é obrigatório.")));
    }

    @Test
    public void criarSemDataVencimentoERecorrenteEGrupoESubGrupo() throws Exception {
        ContabilidadeVO vo = getVO()
                .dataVencimento(null)
                .recorrente(null)
                .grupo(null)
                .subGrupo(null);
        String json = gson.toJson(vo);

        mvc.perform(post("/contabilidade")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(400)))
                .andExpect(jsonPath("$.mensagens", hasSize(4)))
                .andExpect(jsonPath("$.mensagens[0]", is("O campo dataVencimento é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[1]", is("O campo recorrente é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[2]", is("O campo grupo é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[3]", is("O campo subGrupo é obrigatório.")));
    }

    @Test
    public void criarSemDataVencimentoERecorrenteEGrupoESubGrupoELocal() throws Exception {
        ContabilidadeVO vo = getVO()
                .dataVencimento(null)
                .recorrente(null)
                .grupo(null)
                .subGrupo(null)
                .local(null);
        String json = gson.toJson(vo);

        mvc.perform(post("/contabilidade")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(400)))
                .andExpect(jsonPath("$.mensagens", hasSize(4)))
                .andExpect(jsonPath("$.mensagens[0]", is("O campo dataVencimento é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[1]", is("O campo recorrente é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[2]", is("O campo grupo é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[3]", is("O campo subGrupo é obrigatório.")));
    }

    @Test
    public void criarSemDataVencimentoERecorrenteEGrupoESubGrupoELocalEDescricao() throws Exception {
        ContabilidadeVO vo = getVO()
                .dataVencimento(null)
                .recorrente(null)
                .grupo(null)
                .subGrupo(null)
                .local(null)
                .descricao(null);
        String json = gson.toJson(vo);

        mvc.perform(post("/contabilidade")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(400)))
                .andExpect(jsonPath("$.mensagens", hasSize(5)))
                .andExpect(jsonPath("$.mensagens[0]", is("O campo dataVencimento é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[1]", is("O campo recorrente é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[2]", is("O campo grupo é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[3]", is("O campo subGrupo é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[4]", is("O campo descrição é obrigatório.")));
    }

    @Test
    public void criarSemDataVencimentoERecorrenteEGrupoESubGrupoELocalEDescricaoEUsouCartao() throws Exception {
        ContabilidadeVO vo = getVO()
                .dataVencimento(null)
                .recorrente(null)
                .grupo(null)
                .subGrupo(null)
                .local(null)
                .descricao(null)
                .usouCartao(null);
        String json = gson.toJson(vo);

        mvc.perform(post("/contabilidade")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(400)))
                .andExpect(jsonPath("$.mensagens", hasSize(6)))
                .andExpect(jsonPath("$.mensagens[0]", is("O campo dataVencimento é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[1]", is("O campo recorrente é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[2]", is("O campo grupo é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[3]", is("O campo subGrupo é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[4]", is("O campo descrição é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[5]", is("O campo usouCartão é obrigatório.")));
    }

    @Test
    public void criarSemDataVencimentoERecorrenteEGrupoESubGrupoELocalEDescricaoEUsouCartaoECartao() throws Exception {
        ContabilidadeVO vo = getVO()
                .dataVencimento(null)
                .recorrente(null)
                .grupo(null)
                .subGrupo(null)
                .local(null)
                .descricao(null)
                .usouCartao(null)
                .cartao(null);
        String json = gson.toJson(vo);

        mvc.perform(post("/contabilidade")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(400)))
                .andExpect(jsonPath("$.mensagens", hasSize(6)))
                .andExpect(jsonPath("$.mensagens[0]", is("O campo dataVencimento é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[1]", is("O campo recorrente é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[2]", is("O campo grupo é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[3]", is("O campo subGrupo é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[4]", is("O campo descrição é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[5]", is("O campo usouCartão é obrigatório.")));
    }

    @Test
    public void criarSemDataVencimentoERecorrenteEGrupoESubGrupoELocalEDescricaoECartao() throws Exception {
        ContabilidadeVO vo = getVO()
                .dataVencimento(null)
                .recorrente(null)
                .grupo(null)
                .subGrupo(null)
                .local(null)
                .descricao(null)
                .cartao(null);
        String json = gson.toJson(vo);

        mvc.perform(post("/contabilidade")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(400)))
                .andExpect(jsonPath("$.mensagens", hasSize(6)))
                .andExpect(jsonPath("$.mensagens[0]", is("O campo dataVencimento é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[1]", is("O campo recorrente é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[2]", is("O campo grupo é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[3]", is("O campo subGrupo é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[4]", is("O campo descrição é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[5]", is("O campo cartão é obrigatório.")));
    }

    @Test
    public void criarSemDataVencimentoERecorrenteEGrupoESubGrupoELocalEDescricaoEUsouCartaoECartaoEParcelado() throws Exception {
        ContabilidadeVO vo = getVO()
                .dataVencimento(null)
                .recorrente(null)
                .grupo(null)
                .subGrupo(null)
                .local(null)
                .descricao(null)
                .usouCartao(null)
                .cartao(null)
                .parcelado(null);
        String json = gson.toJson(vo);

        mvc.perform(post("/contabilidade")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(400)))
                .andExpect(jsonPath("$.mensagens", hasSize(7)))
                .andExpect(jsonPath("$.mensagens[0]", is("O campo dataVencimento é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[1]", is("O campo recorrente é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[2]", is("O campo grupo é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[3]", is("O campo subGrupo é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[4]", is("O campo descrição é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[5]", is("O campo usouCartão é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[6]", is("O campo parcelado é obrigatório.")));
    }

    @Test
    public void criarSemDataVencimentoERecorrenteEGrupoESubGrupoELocalEDescricaoEUsouCartaoECartaoEParceladoEParcelas() throws Exception {
        ContabilidadeVO vo = getVO()
                .dataVencimento(null)
                .recorrente(null)
                .grupo(null)
                .subGrupo(null)
                .local(null)
                .descricao(null)
                .usouCartao(null)
                .cartao(null)
                .parcelado(null)
                .parcelas(null);
        String json = gson.toJson(vo);

        mvc.perform(post("/contabilidade")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(400)))
                .andExpect(jsonPath("$.mensagens", hasSize(7)))
                .andExpect(jsonPath("$.mensagens[0]", is("O campo dataVencimento é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[1]", is("O campo recorrente é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[2]", is("O campo grupo é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[3]", is("O campo subGrupo é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[4]", is("O campo descrição é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[5]", is("O campo usouCartão é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[6]", is("O campo parcelado é obrigatório.")));
    }

    @Test
    public void criarSemDataVencimentoERecorrenteEGrupoESubGrupoELocalEDescricaoEUsouCartaoECartaoEParcelas() throws Exception {
        ContabilidadeVO vo = getVO()
                .dataVencimento(null)
                .recorrente(null)
                .grupo(null)
                .subGrupo(null)
                .local(null)
                .descricao(null)
                .usouCartao(null)
                .cartao(null)
                .parcelas(null);
        String json = gson.toJson(vo);

        mvc.perform(post("/contabilidade")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(400)))
                .andExpect(jsonPath("$.mensagens", hasSize(6)))
                .andExpect(jsonPath("$.mensagens[0]", is("O campo dataVencimento é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[1]", is("O campo recorrente é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[2]", is("O campo grupo é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[3]", is("O campo subGrupo é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[4]", is("O campo descrição é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[5]", is("O campo usouCartão é obrigatório.")));
    }

    @Test
    public void criarSemDataVencimentoERecorrenteEGrupoESubGrupoELocalEDescricaoEUsouCartaoECartaoEParceladoEParcelasEConta() throws Exception {
        ContabilidadeVO vo = getVO()
                .dataVencimento(null)
                .recorrente(null)
                .grupo(null)
                .subGrupo(null)
                .local(null)
                .descricao(null)
                .usouCartao(null)
                .cartao(null)
                .parcelado(null)
                .parcelas(null)
                .conta(null);
        String json = gson.toJson(vo);

        mvc.perform(post("/contabilidade")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(400)))
                .andExpect(jsonPath("$.mensagens", hasSize(8)))
                .andExpect(jsonPath("$.mensagens[0]", is("O campo dataVencimento é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[1]", is("O campo recorrente é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[2]", is("O campo grupo é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[3]", is("O campo subGrupo é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[4]", is("O campo descrição é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[5]", is("O campo usouCartão é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[6]", is("O campo parcelado é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[7]", is("O campo conta é obrigatório.")));
    }

    @Test
    public void criarSemDataVencimentoERecorrenteEGrupoESubGrupoELocalEDescricaoEUsouCartaoECartaoEParceladoEParcelasEContaETipo() throws Exception {
        ContabilidadeVO vo = getVO()
                .dataVencimento(null)
                .recorrente(null)
                .grupo(null)
                .subGrupo(null)
                .local(null)
                .descricao(null)
                .usouCartao(null)
                .cartao(null)
                .parcelado(null)
                .parcelas(null)
                .conta(null)
                .tipo(null);
        String json = gson.toJson(vo);

        mvc.perform(post("/contabilidade")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(400)))
                .andExpect(jsonPath("$.mensagens", hasSize(9)))
                .andExpect(jsonPath("$.mensagens[0]", is("O campo dataVencimento é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[1]", is("O campo recorrente é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[2]", is("O campo grupo é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[3]", is("O campo subGrupo é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[4]", is("O campo descrição é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[5]", is("O campo usouCartão é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[6]", is("O campo parcelado é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[7]", is("O campo conta é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[8]", is("O campo tipo é obrigatório.")));
    }

    @Test
    public void criarSemCampos() throws Exception {
        ContabilidadeVO vo = getVO()
                .dataVencimento(null)
                .recorrente(null)
                .grupo(null)
                .subGrupo(null)
                .local(null)
                .descricao(null)
                .usouCartao(null)
                .cartao(null)
                .parcelado(null)
                .parcelas(null)
                .conta(null)
                .tipo(null)
                .valor(null);
        String json = gson.toJson(vo);

        mvc.perform(post("/contabilidade")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(400)))
                .andExpect(jsonPath("$.mensagens", hasSize(10)))
                .andExpect(jsonPath("$.mensagens[0]", is("O campo dataVencimento é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[1]", is("O campo recorrente é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[2]", is("O campo grupo é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[3]", is("O campo subGrupo é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[4]", is("O campo descrição é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[5]", is("O campo usouCartão é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[6]", is("O campo parcelado é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[7]", is("O campo conta é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[8]", is("O campo tipo é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[9]", is("O campo valor é obrigatório.")));
    }

    @Test
    public void criarComDataVencimentoIncorreta() throws Exception {
        ContabilidadeVO vo = getVO()
                .dataVencimento("27-04/2018");
        String json = gson.toJson(vo);

        mvc.perform(post("/contabilidade")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(400)))
                .andExpect(jsonPath("$.mensagens", hasSize(1)))
                .andExpect(jsonPath("$.mensagens[0]", is("java.time.format.DateTimeParseException: Text '27-04/2018' could not be parsed at index 2")));
    }

    @Test
    public void criarComRecorrenteIncorreto() throws Exception {
        ContabilidadeVO vo = getVO()
                .recorrente("NS");
        String json = gson.toJson(vo);

        mvc.perform(post("/contabilidade")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(400)))
                .andExpect(jsonPath("$.mensagens", hasSize(1)))
                .andExpect(jsonPath("$.mensagens[0]", is("O valor do campo recorrente é diferente de 'S' ou 'N'.")));
    }

    @Test
    public void criarComRecorrenteEUsouCartaoIncorretos() throws Exception {
        ContabilidadeVO vo = getVO()
                .recorrente("NS")
                .usouCartao("NS");
        String json = gson.toJson(vo);

        mvc.perform(post("/contabilidade")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(400)))
                .andExpect(jsonPath("$.mensagens", hasSize(2)))
                .andExpect(jsonPath("$.mensagens[0]", is("O valor do campo recorrente é diferente de 'S' ou 'N'.")))
                .andExpect(jsonPath("$.mensagens[1]", is("O valor do campo usouCartao é diferente de 'S' ou 'N'.")));
    }

    @Test
    public void criarComRecorrenteEUsouCartaoEParceladoIncorretos() throws Exception {
        ContabilidadeVO vo = getVO()
                .recorrente("NS")
                .usouCartao("NS")
                .parcelado("NS");
        String json = gson.toJson(vo);

        mvc.perform(post("/contabilidade")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(400)))
                .andExpect(jsonPath("$.mensagens", hasSize(3)))
                .andExpect(jsonPath("$.mensagens[0]", is("O valor do campo recorrente é diferente de 'S' ou 'N'.")))
                .andExpect(jsonPath("$.mensagens[1]", is("O valor do campo usouCartao é diferente de 'S' ou 'N'.")))
                .andExpect(jsonPath("$.mensagens[2]", is("O valor do campo parcelado é diferente de 'S' ou 'N'.")));
    }

    @Test
    public void criarComParcelasIncorretas() throws Exception {
        ContabilidadeVO vo = getVO()
                .parcelas("uma");
        String json = gson.toJson(vo);

        mvc.perform(post("/contabilidade")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(400)))
                .andExpect(jsonPath("$.mensagens", hasSize(1)))
                .andExpect(jsonPath("$.mensagens[0]", is("java.lang.NumberFormatException: For input string: \"uma\"")));
    }

    @Test
    public void criarComTipoIncorreto() throws Exception {
        ContabilidadeVO vo = getVO()
                .tipo("DEBITOS");
        String json = gson.toJson(vo);

        mvc.perform(post("/contabilidade")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(400)))
                .andExpect(jsonPath("$.mensagens", hasSize(1)))
                .andExpect(jsonPath("$.mensagens[0]", is("java.lang.IllegalArgumentException: No enum constant TipoContabilidade.DEBITOS")));
    }

    @Test
    public void criarComValorIncorreto() throws Exception {
        ContabilidadeVO vo = getVO()
                .valor("a24,04");
        String json = gson.toJson(vo);

        mvc.perform(post("/contabilidade")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(400)))
                .andExpect(jsonPath("$.mensagens", hasSize(1)))
                .andExpect(jsonPath("$.mensagens[0]", is("java.text.ParseException: Unparseable number: \"a24,04\"")));
    }

    @Test
    public void criarRecorrenteEParcelado() throws Exception {
        ContabilidadeVO vo = getVO()
                .recorrente("S")
                .parcelado("S");
        String json = gson.toJson(vo);

        mvc.perform(post("/contabilidade")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(400)))
                .andExpect(jsonPath("$.mensagens", hasSize(1)))
                .andExpect(jsonPath("$.mensagens[0]", is("Uma contabilidade não pode ser recorrente e parcelada.")));
    }

    @Test
    public void criarGrupoNaoCadastrado() throws Exception {
        ContabilidadeVO vo = getVO()
                .grupo("Um grupo");
        String json = gson.toJson(vo);

        mvc.perform(post("/contabilidade")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(400)))
                .andExpect(jsonPath("$.mensagens", hasSize(1)))
                .andExpect(jsonPath("$.mensagens[0]", is("Grupo não cadastrado.")));
    }

    @Test
    public void criarSubGrupoNaoCadastrado() throws Exception {
        ContabilidadeVO vo = getVO()
                .subGrupo("Um subgrupo");
        String json = gson.toJson(vo);

        mvc.perform(post("/contabilidade")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(400)))
                .andExpect(jsonPath("$.mensagens", hasSize(2)))
                .andExpect(jsonPath("$.mensagens[0]", is("SubGrupo não cadastrado.")))
                .andExpect(jsonPath("$.mensagens[1]", is("Grupo e SubGrupo não estão associados.")));
    }

    @Test
    public void criarLocalNaoCadastrado() throws Exception {
        ContabilidadeVO vo = getVO()
                .local("Um outro local");
        String json = gson.toJson(vo);

        mvc.perform(post("/contabilidade")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(400)))
                .andExpect(jsonPath("$.mensagens", hasSize(1)))
                .andExpect(jsonPath("$.mensagens[0]", is("Local não cadastrado.")));
    }

    @Test
    public void criarCartaoNaoCadastrado() throws Exception {
        ContabilidadeVO vo = getVO()
                .cartao("1324");
        String json = gson.toJson(vo);

        mvc.perform(post("/contabilidade")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(400)))
                .andExpect(jsonPath("$.mensagens", hasSize(1)))
                .andExpect(jsonPath("$.mensagens[0]", is("Cartão não cadastrado.")));
    }

    @Test
    public void criarContaNaoCadastrada() throws Exception {
        ContabilidadeVO vo = getVO()
                .conta("Uma conta");
        String json = gson.toJson(vo);

        mvc.perform(post("/contabilidade")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(400)))
                .andExpect(jsonPath("$.mensagens", hasSize(1)))
                .andExpect(jsonPath("$.mensagens[0]", is("Conta não cadastrada.")));
    }

    @Test
    public void criarGrupoESubGrupoNaoAssociados() throws Exception {
        criarSubGrupoAluguel();

        ContabilidadeVO vo = getVO()
                .grupo("Saúde")
                .subGrupo("Aluguel");

        String json = gson.toJson(vo);

        mvc.perform(post("/contabilidade")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(400)))
                .andExpect(jsonPath("$.mensagens", hasSize(1)))
                .andExpect(jsonPath("$.mensagens[0]", is("Grupo e SubGrupo não estão associados.")));
    }

    @Test
    public void criarDuplicado() throws Exception {
        ContabilidadeVO vo = getVO();
        String json = gson.toJson(vo);

        mvc.perform(post("/contabilidade")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigos", hasSize(7)))
                .andExpect(jsonPath("$.codigos[0]", is(0)))
                .andExpect(jsonPath("$.codigos[1]", is(1)))
                .andExpect(jsonPath("$.codigos[2]", is(2)))
                .andExpect(jsonPath("$.codigos[3]", is(3)))
                .andExpect(jsonPath("$.codigos[4]", is(4)))
                .andExpect(jsonPath("$.codigos[5]", is(5)))
                .andExpect(jsonPath("$.codigos[6]", is(6)));

        mvc.perform(post("/contabilidade")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(400)))
                .andExpect(jsonPath("$.mensagens", IsCollectionWithSize.hasSize(1)))
                .andExpect(jsonPath("$.mensagens[0]", is("Contabilidade duplicada.")));
    }

    @Test
    public void criarSemCartao() throws Exception {
        ContabilidadeVO vo = getVO()
                .recorrente("N")
                .parcelado("N")
                .usouCartao("N");
        String json = gson.toJson(vo);

        mvc.perform(post("/contabilidade")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigos", hasSize(1)))
                .andExpect(jsonPath("$.codigos[0]", is(0)));
    }

    @Test
    public void criarParcelada() throws Exception {
        criarContabilidadeParcelada();
    }

    @Test
    public void criarRecorrente() throws Exception {
        ContabilidadeVO vo = getVO()
                .recorrente("S")
                .parcelado("N");
        String json = gson.toJson(vo);

        mvc.perform(post("/contabilidade")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigos", hasSize(9)))
                .andExpect(jsonPath("$.codigos[0]", is(0)))
                .andExpect(jsonPath("$.codigos[1]", is(1)))
                .andExpect(jsonPath("$.codigos[2]", is(2)))
                .andExpect(jsonPath("$.codigos[3]", is(3)))
                .andExpect(jsonPath("$.codigos[4]", is(4)))
                .andExpect(jsonPath("$.codigos[5]", is(5)))
                .andExpect(jsonPath("$.codigos[6]", is(6)))
                .andExpect(jsonPath("$.codigos[7]", is(7)))
                .andExpect(jsonPath("$.codigos[8]", is(8)));
    }

    @Test
    public void atualizarSemDiretorio() throws Exception {
        deletarDiretorioEArquivos();

        ContabilidadeVO vo = getVO();
        String json = gson.toJson(vo);

        mvc.perform(put("/contabilidade")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInsufficientStorage())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(507)))
                .andExpect(jsonPath("$.mensagens", IsCollectionWithSize.hasSize(1)))
                .andExpect(jsonPath("$.mensagens[0]", is("Erro de persistência ao atualizar.")));

    }

    @Test
    public void atualizarSemDataVencimento() throws Exception {
        ContabilidadeVO vo = getVO()
                .dataVencimento(null);
        String json = gson.toJson(vo);

        mvc.perform(put("/contabilidade")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(400)))
                .andExpect(jsonPath("$.mensagens", hasSize(1)))
                .andExpect(jsonPath("$.mensagens[0]", is("O campo dataVencimento é obrigatório.")));
    }

    @Test
    public void atualizarSemDataVencimentoERecorrenteEGrupo() throws Exception {
        ContabilidadeVO vo = getVO()
                .dataVencimento(null)
                .recorrente(null)
                .grupo(null);
        String json = gson.toJson(vo);

        mvc.perform(put("/contabilidade")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(400)))
                .andExpect(jsonPath("$.mensagens", hasSize(3)))
                .andExpect(jsonPath("$.mensagens[0]", is("O campo dataVencimento é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[1]", is("O campo recorrente é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[2]", is("O campo grupo é obrigatório.")));
    }

    @Test
    public void atualizarSemDataVencimentoERecorrenteEGrupoESubGrupo() throws Exception {
        ContabilidadeVO vo = getVO()
                .dataVencimento(null)
                .recorrente(null)
                .grupo(null)
                .subGrupo(null);
        String json = gson.toJson(vo);

        mvc.perform(put("/contabilidade")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(400)))
                .andExpect(jsonPath("$.mensagens", hasSize(4)))
                .andExpect(jsonPath("$.mensagens[0]", is("O campo dataVencimento é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[1]", is("O campo recorrente é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[2]", is("O campo grupo é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[3]", is("O campo subGrupo é obrigatório.")));
    }

    @Test
    public void atualizarSemDataVencimentoERecorrenteEGrupoESubGrupoELocal() throws Exception {
        ContabilidadeVO vo = getVO()
                .dataVencimento(null)
                .recorrente(null)
                .grupo(null)
                .subGrupo(null)
                .local(null);
        String json = gson.toJson(vo);

        mvc.perform(put("/contabilidade")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(400)))
                .andExpect(jsonPath("$.mensagens", hasSize(4)))
                .andExpect(jsonPath("$.mensagens[0]", is("O campo dataVencimento é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[1]", is("O campo recorrente é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[2]", is("O campo grupo é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[3]", is("O campo subGrupo é obrigatório.")));
    }

    @Test
    public void atualizarSemDataVencimentoERecorrenteEGrupoESubGrupoELocalEDescricao() throws Exception {
        ContabilidadeVO vo = getVO()
                .dataVencimento(null)
                .recorrente(null)
                .grupo(null)
                .subGrupo(null)
                .local(null)
                .descricao(null);
        String json = gson.toJson(vo);

        mvc.perform(put("/contabilidade")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(400)))
                .andExpect(jsonPath("$.mensagens", hasSize(5)))
                .andExpect(jsonPath("$.mensagens[0]", is("O campo dataVencimento é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[1]", is("O campo recorrente é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[2]", is("O campo grupo é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[3]", is("O campo subGrupo é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[4]", is("O campo descrição é obrigatório.")));
    }

    @Test
    public void atualizarSemDataVencimentoERecorrenteEGrupoESubGrupoELocalEDescricaoEUsouCartao() throws Exception {
        ContabilidadeVO vo = getVO()
                .dataVencimento(null)
                .recorrente(null)
                .grupo(null)
                .subGrupo(null)
                .local(null)
                .descricao(null)
                .usouCartao(null);
        String json = gson.toJson(vo);

        mvc.perform(put("/contabilidade")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(400)))
                .andExpect(jsonPath("$.mensagens", hasSize(6)))
                .andExpect(jsonPath("$.mensagens[0]", is("O campo dataVencimento é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[1]", is("O campo recorrente é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[2]", is("O campo grupo é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[3]", is("O campo subGrupo é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[4]", is("O campo descrição é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[5]", is("O campo usouCartão é obrigatório.")));
    }

    @Test
    public void atualizarSemDataVencimentoERecorrenteEGrupoESubGrupoELocalEDescricaoEUsouCartaoECartao() throws Exception {
        ContabilidadeVO vo = getVO()
                .dataVencimento(null)
                .recorrente(null)
                .grupo(null)
                .subGrupo(null)
                .local(null)
                .descricao(null)
                .usouCartao(null)
                .cartao(null);
        String json = gson.toJson(vo);

        mvc.perform(put("/contabilidade")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(400)))
                .andExpect(jsonPath("$.mensagens", hasSize(6)))
                .andExpect(jsonPath("$.mensagens[0]", is("O campo dataVencimento é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[1]", is("O campo recorrente é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[2]", is("O campo grupo é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[3]", is("O campo subGrupo é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[4]", is("O campo descrição é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[5]", is("O campo usouCartão é obrigatório.")));
    }

    @Test
    public void atualizarSemDataVencimentoERecorrenteEGrupoESubGrupoELocalEDescricaoECartao() throws Exception {
        ContabilidadeVO vo = getVO()
                .dataVencimento(null)
                .recorrente(null)
                .grupo(null)
                .subGrupo(null)
                .local(null)
                .descricao(null)
                .cartao(null);
        String json = gson.toJson(vo);

        mvc.perform(put("/contabilidade")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(400)))
                .andExpect(jsonPath("$.mensagens", hasSize(6)))
                .andExpect(jsonPath("$.mensagens[0]", is("O campo dataVencimento é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[1]", is("O campo recorrente é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[2]", is("O campo grupo é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[3]", is("O campo subGrupo é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[4]", is("O campo descrição é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[5]", is("O campo cartão é obrigatório.")));
    }

    @Test
    public void atualizarSemDataVencimentoERecorrenteEGrupoESubGrupoELocalEDescricaoEUsouCartaoECartaoEParcelado() throws Exception {
        ContabilidadeVO vo = getVO()
                .dataVencimento(null)
                .recorrente(null)
                .grupo(null)
                .subGrupo(null)
                .local(null)
                .descricao(null)
                .usouCartao(null)
                .cartao(null)
                .parcelado(null);
        String json = gson.toJson(vo);

        mvc.perform(put("/contabilidade")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(400)))
                .andExpect(jsonPath("$.mensagens", hasSize(7)))
                .andExpect(jsonPath("$.mensagens[0]", is("O campo dataVencimento é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[1]", is("O campo recorrente é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[2]", is("O campo grupo é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[3]", is("O campo subGrupo é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[4]", is("O campo descrição é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[5]", is("O campo usouCartão é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[6]", is("O campo parcelado é obrigatório.")));
    }

    @Test
    public void atualizarSemDataVencimentoERecorrenteEGrupoESubGrupoELocalEDescricaoEUsouCartaoECartaoEParceladoEParcelas() throws Exception {
        ContabilidadeVO vo = getVO()
                .dataVencimento(null)
                .recorrente(null)
                .grupo(null)
                .subGrupo(null)
                .local(null)
                .descricao(null)
                .usouCartao(null)
                .cartao(null)
                .parcelado(null)
                .parcelas(null);
        String json = gson.toJson(vo);

        mvc.perform(put("/contabilidade")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(400)))
                .andExpect(jsonPath("$.mensagens", hasSize(7)))
                .andExpect(jsonPath("$.mensagens[0]", is("O campo dataVencimento é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[1]", is("O campo recorrente é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[2]", is("O campo grupo é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[3]", is("O campo subGrupo é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[4]", is("O campo descrição é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[5]", is("O campo usouCartão é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[6]", is("O campo parcelado é obrigatório.")));
    }

    @Test
    public void atualizarSemDataVencimentoERecorrenteEGrupoESubGrupoELocalEDescricaoEUsouCartaoECartaoEParcelas() throws Exception {
        ContabilidadeVO vo = getVO()
                .dataVencimento(null)
                .recorrente(null)
                .grupo(null)
                .subGrupo(null)
                .local(null)
                .descricao(null)
                .usouCartao(null)
                .cartao(null)
                .parcelas(null);
        String json = gson.toJson(vo);

        mvc.perform(put("/contabilidade")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(400)))
                .andExpect(jsonPath("$.mensagens", hasSize(6)))
                .andExpect(jsonPath("$.mensagens[0]", is("O campo dataVencimento é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[1]", is("O campo recorrente é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[2]", is("O campo grupo é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[3]", is("O campo subGrupo é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[4]", is("O campo descrição é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[5]", is("O campo usouCartão é obrigatório.")));
    }

    @Test
    public void atualizarSemDataVencimentoERecorrenteEGrupoESubGrupoELocalEDescricaoEUsouCartaoECartaoEParceladoEParcelasEConta() throws Exception {
        ContabilidadeVO vo = getVO()
                .dataVencimento(null)
                .recorrente(null)
                .grupo(null)
                .subGrupo(null)
                .local(null)
                .descricao(null)
                .usouCartao(null)
                .cartao(null)
                .parcelado(null)
                .parcelas(null)
                .conta(null);
        String json = gson.toJson(vo);

        mvc.perform(put("/contabilidade")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(400)))
                .andExpect(jsonPath("$.mensagens", hasSize(8)))
                .andExpect(jsonPath("$.mensagens[0]", is("O campo dataVencimento é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[1]", is("O campo recorrente é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[2]", is("O campo grupo é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[3]", is("O campo subGrupo é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[4]", is("O campo descrição é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[5]", is("O campo usouCartão é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[6]", is("O campo parcelado é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[7]", is("O campo conta é obrigatório.")));
    }

    @Test
    public void atualizarSemDataVencimentoERecorrenteEGrupoESubGrupoELocalEDescricaoEUsouCartaoECartaoEParceladoEParcelasEContaETipo() throws Exception {
        ContabilidadeVO vo = getVO()
                .dataVencimento(null)
                .recorrente(null)
                .grupo(null)
                .subGrupo(null)
                .local(null)
                .descricao(null)
                .usouCartao(null)
                .cartao(null)
                .parcelado(null)
                .parcelas(null)
                .conta(null)
                .tipo(null);
        String json = gson.toJson(vo);

        mvc.perform(put("/contabilidade")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(400)))
                .andExpect(jsonPath("$.mensagens", hasSize(9)))
                .andExpect(jsonPath("$.mensagens[0]", is("O campo dataVencimento é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[1]", is("O campo recorrente é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[2]", is("O campo grupo é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[3]", is("O campo subGrupo é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[4]", is("O campo descrição é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[5]", is("O campo usouCartão é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[6]", is("O campo parcelado é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[7]", is("O campo conta é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[8]", is("O campo tipo é obrigatório.")));
    }

    @Test
    public void atualizarSemCampos() throws Exception {
        ContabilidadeVO vo = getVO()
                .dataVencimento(null)
                .recorrente(null)
                .grupo(null)
                .subGrupo(null)
                .local(null)
                .descricao(null)
                .usouCartao(null)
                .cartao(null)
                .parcelado(null)
                .parcelas(null)
                .conta(null)
                .tipo(null)
                .valor(null);
        String json = gson.toJson(vo);

        mvc.perform(put("/contabilidade")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(400)))
                .andExpect(jsonPath("$.mensagens", hasSize(10)))
                .andExpect(jsonPath("$.mensagens[0]", is("O campo dataVencimento é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[1]", is("O campo recorrente é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[2]", is("O campo grupo é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[3]", is("O campo subGrupo é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[4]", is("O campo descrição é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[5]", is("O campo usouCartão é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[6]", is("O campo parcelado é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[7]", is("O campo conta é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[8]", is("O campo tipo é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[9]", is("O campo valor é obrigatório.")));
    }

    @Test
    public void atualizarSemCodigo() throws Exception {
        ContabilidadeVO vo = getVO()
                .codigo(null);
        String json = gson.toJson(vo);

        mvc.perform(put("/contabilidade")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(400)))
                .andExpect(jsonPath("$.mensagens", hasSize(1)))
                .andExpect(jsonPath("$.mensagens[0]", is("O campo código é obrigatório.")));
    }

    @Test
    public void atualizarSemCodigoEDataLancamento() throws Exception {
        ContabilidadeVO vo = getVO()
                .codigo(null)
                .dataLancamento(null);
        String json = gson.toJson(vo);

        mvc.perform(put("/contabilidade")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(400)))
                .andExpect(jsonPath("$.mensagens", hasSize(2)))
                .andExpect(jsonPath("$.mensagens[0]", is("O campo código é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[1]", is("O campo dataLançamento é obrigatório.")));
    }

    @Test
    public void atualizarSemCodigoEDataLancamentoEParcela() throws Exception {
        ContabilidadeVO vo = getVO()
                .codigo(null)
                .dataLancamento(null)
                .parcela(null);
        String json = gson.toJson(vo);

        mvc.perform(put("/contabilidade")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(400)))
                .andExpect(jsonPath("$.mensagens", hasSize(3)))
                .andExpect(jsonPath("$.mensagens[0]", is("O campo código é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[1]", is("O campo dataLançamento é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[2]", is("O campo parcela é obrigatório.")));
    }

    @Test
    public void atualizarSemCodigoPai() throws Exception {
        criarContabilidadeParcelada();

        ContabilidadeVO vo = getVO()
                .codigo("1");
        String json = gson.toJson(vo);

        mvc.perform(put("/contabilidade")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(400)))
                .andExpect(jsonPath("$.mensagens", hasSize(1)))
                .andExpect(jsonPath("$.mensagens[0]", is("O campo códigoPai é obrigatório.")));
    }

    @Test
    public void atualizarComDataVencimentoIncorreta() throws Exception {
        criarContabilidadeParcelada();

        ContabilidadeVO vo = getVO()
                .codigo("0")
                .dataVencimento("27-04/2018");
        String json = gson.toJson(vo);

        mvc.perform(put("/contabilidade")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(400)))
                .andExpect(jsonPath("$.mensagens", hasSize(1)))
                .andExpect(jsonPath("$.mensagens[0]", is("java.time.format.DateTimeParseException: Text '27-04/2018' could not be parsed at index 2")));
    }

    @Test
    public void atualizarComRecorrenteIncorreto() throws Exception {
        criarContabilidadeParcelada();

        ContabilidadeVO vo = getVO()
                .recorrente("NS");
        String json = gson.toJson(vo);

        mvc.perform(put("/contabilidade")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(400)))
                .andExpect(jsonPath("$.mensagens", hasSize(1)))
                .andExpect(jsonPath("$.mensagens[0]", is("O valor do campo recorrente é diferente de 'S' ou 'N'.")));
    }

    @Test
    public void atualizarComRecorrenteEUsouCartaoIncorretos() throws Exception {
        criarContabilidadeParcelada();

        ContabilidadeVO vo = getVO()
                .recorrente("NS")
                .usouCartao("NS");
        String json = gson.toJson(vo);

        mvc.perform(put("/contabilidade")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(400)))
                .andExpect(jsonPath("$.mensagens", hasSize(2)))
                .andExpect(jsonPath("$.mensagens[0]", is("O valor do campo recorrente é diferente de 'S' ou 'N'.")))
                .andExpect(jsonPath("$.mensagens[1]", is("O valor do campo usouCartao é diferente de 'S' ou 'N'.")));
    }

    @Test
    public void atualizarComRecorrenteEUsouCartaoEParceladoIncorretos() throws Exception {
        criarContabilidadeParcelada();

        ContabilidadeVO vo = getVO()
                .recorrente("NS")
                .usouCartao("NS")
                .parcelado("NS");
        String json = gson.toJson(vo);

        mvc.perform(put("/contabilidade")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(400)))
                .andExpect(jsonPath("$.mensagens", hasSize(3)))
                .andExpect(jsonPath("$.mensagens[0]", is("O valor do campo recorrente é diferente de 'S' ou 'N'.")))
                .andExpect(jsonPath("$.mensagens[1]", is("O valor do campo usouCartao é diferente de 'S' ou 'N'.")))
                .andExpect(jsonPath("$.mensagens[2]", is("O valor do campo parcelado é diferente de 'S' ou 'N'.")));
    }

    @Test
    public void atualizarComTipoIncorreto() throws Exception {
        criarContabilidadeParcelada();

        ContabilidadeVO vo = getVO()
                .codigo("0")
                .tipo("DEBITOS");
        String json = gson.toJson(vo);

        mvc.perform(put("/contabilidade")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(400)))
                .andExpect(jsonPath("$.mensagens", hasSize(1)))
                .andExpect(jsonPath("$.mensagens[0]", is("java.lang.IllegalArgumentException: No enum constant TipoContabilidade.DEBITOS")));
    }

    @Test
    public void atualizarComValorIncorreto() throws Exception {
        criarContabilidadeParcelada();

        ContabilidadeVO vo = getVO()
                .codigo("0")
                .valor("a24,04");
        String json = gson.toJson(vo);

        mvc.perform(put("/contabilidade")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(400)))
                .andExpect(jsonPath("$.mensagens", hasSize(1)))
                .andExpect(jsonPath("$.mensagens[0]", is("java.text.ParseException: Unparseable number: \"a24,04\"")));
    }

    @Test
    public void atualizarRecorrenteEParcelado() throws Exception {
        criarContabilidadeParcelada();

        ContabilidadeVO vo = getVO()
                .codigo("0")
                .recorrente("S")
                .parcelado("S");
        String json = gson.toJson(vo);

        mvc.perform(put("/contabilidade")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(400)))
                .andExpect(jsonPath("$.mensagens", hasSize(1)))
                .andExpect(jsonPath("$.mensagens[0]", is("Uma contabilidade não pode ser recorrente e parcelada.")));
    }

    @Test
    public void atualizarProibidoAlterarCodigo() throws Exception {
        criarContabilidadeParcelada();

        ContabilidadeVO vo = getVO()
                .codigo("20");
        String json = gson.toJson(vo);

        mvc.perform(put("/contabilidade")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(400)))
                .andExpect(jsonPath("$.mensagens", hasSize(1)))
                .andExpect(jsonPath("$.mensagens[0]", is("O campo código não pode ser alterado.")));
    }

    @Test
    public void atualizarProibidoAlterarDataLancamento() throws Exception {
        criarContabilidadeParcelada();

        ContabilidadeVO vo = getVO()
                .codigo("0")
                .dataLancamento("01/01/2000");
        String json = gson.toJson(vo);

        mvc.perform(put("/contabilidade")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(400)))
                .andExpect(jsonPath("$.mensagens", hasSize(1)))
                .andExpect(jsonPath("$.mensagens[0]", is("O campo dataLançamento não pode ser alterado.")));
    }

    @Test
    public void atualizarProibidoAlterarParcelas() throws Exception {
        criarContabilidadeParcelada();

        ContabilidadeVO vo = getVO()
                .codigo("0")
                .parcelas("10");
        String json = gson.toJson(vo);

        mvc.perform(put("/contabilidade")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(400)))
                .andExpect(jsonPath("$.mensagens", hasSize(1)))
                .andExpect(jsonPath("$.mensagens[0]", is("O campo parcelas não pode ser alterado.")));
    }

    @Test
    public void atualizarProibidoAlterarParcela() throws Exception {
        criarContabilidadeParcelada();

        ContabilidadeVO vo = getVO()
                .codigo("0")
                .parcela("2");
        String json = gson.toJson(vo);

        mvc.perform(put("/contabilidade")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(400)))
                .andExpect(jsonPath("$.mensagens", hasSize(1)))
                .andExpect(jsonPath("$.mensagens[0]", is("O campo parcela não pode ser alterado.")));
    }

    @Test
    public void atualizarProibidoAlterarCodigoPai() throws Exception {
        criarContabilidadeParcelada();

        ContabilidadeVO vo = getVO()
                .codigo("0")
                .codigoPai("1");
        String json = gson.toJson(vo);

        mvc.perform(put("/contabilidade")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(400)))
                .andExpect(jsonPath("$.mensagens", hasSize(1)))
                .andExpect(jsonPath("$.mensagens[0]", is("O campo códigoPai não pode ser alterado.")));
    }

    @Test
    public void atualizarGrupoNaoCadastrado() throws Exception {
        ContabilidadeVO vo = getVO()
                .grupo("Um grupo");
        String json = gson.toJson(vo);

        mvc.perform(put("/contabilidade")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(400)))
                .andExpect(jsonPath("$.mensagens", hasSize(1)))
                .andExpect(jsonPath("$.mensagens[0]", is("Grupo não cadastrado.")));
    }

    @Test
    public void atualizarSubGrupoNaoCadastrado() throws Exception {
        ContabilidadeVO vo = getVO()
                .subGrupo("Um subgrupo");
        String json = gson.toJson(vo);

        mvc.perform(put("/contabilidade")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(400)))
                .andExpect(jsonPath("$.mensagens", hasSize(2)))
                .andExpect(jsonPath("$.mensagens[0]", is("SubGrupo não cadastrado.")))
                .andExpect(jsonPath("$.mensagens[1]", is("Grupo e SubGrupo não estão associados.")));
    }

    @Test
    public void atualizarLocalNaoCadastrado() throws Exception {
        ContabilidadeVO vo = getVO()
                .local("Um outro local");
        String json = gson.toJson(vo);

        mvc.perform(put("/contabilidade")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(400)))
                .andExpect(jsonPath("$.mensagens", hasSize(1)))
                .andExpect(jsonPath("$.mensagens[0]", is("Local não cadastrado.")));
    }

    @Test
    public void atualizarCartaoNaoCadastrado() throws Exception {
        ContabilidadeVO vo = getVO()
                .cartao("1324");
        String json = gson.toJson(vo);

        mvc.perform(put("/contabilidade")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(400)))
                .andExpect(jsonPath("$.mensagens", hasSize(1)))
                .andExpect(jsonPath("$.mensagens[0]", is("Cartão não cadastrado.")));
    }

    @Test
    public void atualizarContaNaoCadastrada() throws Exception {
        ContabilidadeVO vo = getVO()
                .conta("Uma conta");
        String json = gson.toJson(vo);

        mvc.perform(put("/contabilidade")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(400)))
                .andExpect(jsonPath("$.mensagens", hasSize(1)))
                .andExpect(jsonPath("$.mensagens[0]", is("Conta não cadastrada.")));
    }

    @Test
    public void atualizarGrupoESubGrupoNaoAssociados() throws Exception {
        criarSubGrupoAluguel();

        ContabilidadeVO vo = getVO()
                .grupo("Saúde")
                .subGrupo("Aluguel");

        String json = gson.toJson(vo);

        mvc.perform(put("/contabilidade")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(400)))
                .andExpect(jsonPath("$.mensagens", hasSize(1)))
                .andExpect(jsonPath("$.mensagens[0]", is("Grupo e SubGrupo não estão associados.")));
    }

    @Test
    public void atualizar() throws Exception {
        criarContabilidadeParcelada();

        ContabilidadeVO vo = getVO()
                .codigo("0");
        String json = gson.toJson(vo);

        mvc.perform(put("/contabilidade")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andExpect(content().string(""));
    }

    private void criarGrupo() throws StoreException, BusinessException, GenericException {
        GrupoVO vo = new GrupoVO()
                .nome("Saúde")
                .descricao("Grupo que gere gastos com saúde")
                .subGrupos(asList("Suplementos"));
        grupoController.criar(vo);
    }

    private void criarSubGrupo() throws StoreException, BusinessException, GenericException {
        SubGrupoDTO subGrupoDTO = SubGrupoDTOFactory
                .create()
                .withNome("Suplementos")
                .withDescricao("Subgrupo de suplementos")
                .build();
        subGrupoController.criar(subGrupoDTO);
    }

    private void criarSubGrupoAluguel() throws StoreException, BusinessException, GenericException {
        SubGrupoDTO subGrupoDTO = SubGrupoDTOFactory
                .create()
                .withNome("Aluguel")
                .withDescricao("Subgrupo de aluguel")
                .build();
        subGrupoController.criar(subGrupoDTO);
    }

    private void criarLocal() throws StoreException, BusinessException, GenericException {
        LocalDTO localDTO = LocalDTOFactory
                .create()
                .withNome("Site")
                .build();
        localController.criar(localDTO);
    }

    private void criarCartao() throws StoreException, BusinessException, GenericException {
        CartaoVO cartaoVO = new CartaoVO()
                .numero("0744")
                .vencimento("27/03/2018")
                .diaMelhorCompra("17/04/2018")
                .portador("Carol")
                .tipo("FISICO")
                .limite("2.000,00");
        cartaoController.criar(cartaoVO);
    }

    private void criarConta() throws StoreException, BusinessException, GenericException {
        ContaVO vo = new ContaVO()
                .nome("CAROL")
                .descricao("Valor separado para a Carol")
                .valorDefault("500,00")
                .cumulativo("S");
        contaController.criar(vo);
    }

    private void criarContabilidadeParcelada() throws Exception {
        ContabilidadeVO vo = getVO();
        String json = gson.toJson(vo);

        mvc.perform(post("/contabilidade")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigos", hasSize(7)))
                .andExpect(jsonPath("$.codigos[0]", is(0)))
                .andExpect(jsonPath("$.codigos[1]", is(1)))
                .andExpect(jsonPath("$.codigos[2]", is(2)))
                .andExpect(jsonPath("$.codigos[3]", is(3)))
                .andExpect(jsonPath("$.codigos[4]", is(4)))
                .andExpect(jsonPath("$.codigos[5]", is(5)))
                .andExpect(jsonPath("$.codigos[6]", is(6)));
    }

    private ContabilidadeVO getVO() {
        return new ContabilidadeVO()
                .dataLancamento(getStringFromCurrentDate())
                .dataVencimento("27/04/2018")
                .recorrente("N")
                .grupo("Saúde")
                .subGrupo("Suplementos")
                .local("Site")
                .descricao("Suplementos comprados pela Carol")
                .usouCartao("S")
                .cartao("0744")
                .parcelado("S")
                .parcela("1")
                .parcelas("7")
                .conta("CAROL")
                .tipo("DEBITO")
                .valor("24,04");
    }
}
