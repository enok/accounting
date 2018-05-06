package br.com.accounting.rest.controller;

import br.com.accounting.business.dto.*;
import br.com.accounting.business.exception.BusinessException;
import br.com.accounting.business.exception.GenericException;
import br.com.accounting.business.factory.*;
import br.com.accounting.core.exception.StoreException;
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

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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

        ContabilidadeDTO dto = getDTO();
        String json = gson.toJson(dto);

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
                .andExpect(jsonPath("$.mensagens[0]", is("Não foi possível buscar os registros.")));

    }

    @Test
    public void criarSemDataVencimento() throws Exception {
        ContabilidadeDTO dto = getDTO()
                .dataVencimento(null);
        String json = gson.toJson(dto);

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
        ContabilidadeDTO dto = getDTO()
                .dataVencimento(null)
                .recorrente(null);
        String json = gson.toJson(dto);

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
        ContabilidadeDTO dto = getDTO()
                .dataVencimento(null)
                .recorrente(null)
                .grupo(null);
        String json = gson.toJson(dto);

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
        ContabilidadeDTO dto = getDTO()
                .dataVencimento(null)
                .recorrente(null)
                .grupo(null)
                .subGrupo(null);
        String json = gson.toJson(dto);

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
        ContabilidadeDTO dto = getDTO()
                .dataVencimento(null)
                .recorrente(null)
                .grupo(null)
                .subGrupo(null)
                .local(null);
        String json = gson.toJson(dto);

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
        ContabilidadeDTO dto = getDTO()
                .dataVencimento(null)
                .recorrente(null)
                .grupo(null)
                .subGrupo(null)
                .local(null)
                .descricao(null);
        String json = gson.toJson(dto);

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
        ContabilidadeDTO dto = getDTO()
                .dataVencimento(null)
                .recorrente(null)
                .grupo(null)
                .subGrupo(null)
                .local(null)
                .descricao(null)
                .usouCartao(null);
        String json = gson.toJson(dto);

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
                .andExpect(jsonPath("$.mensagens[5]", is("O campo usouCartão é obrigatório.")))
                .andDo(print());
    }

    @Test
    public void criarSemDataVencimentoERecorrenteEGrupoESubGrupoELocalEDescricaoEUsouCartaoECartao() throws Exception {
        ContabilidadeDTO dto = getDTO()
                .dataVencimento(null)
                .recorrente(null)
                .grupo(null)
                .subGrupo(null)
                .local(null)
                .descricao(null)
                .usouCartao(null)
                .cartao(null);
        String json = gson.toJson(dto);

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
        ContabilidadeDTO dto = getDTO()
                .dataVencimento(null)
                .recorrente(null)
                .grupo(null)
                .subGrupo(null)
                .local(null)
                .descricao(null)
                .cartao(null);
        String json = gson.toJson(dto);

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
        ContabilidadeDTO dto = getDTO()
                .dataVencimento(null)
                .recorrente(null)
                .grupo(null)
                .subGrupo(null)
                .local(null)
                .descricao(null)
                .usouCartao(null)
                .cartao(null)
                .parcelado(null);
        String json = gson.toJson(dto);

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
        ContabilidadeDTO dto = getDTO()
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
        String json = gson.toJson(dto);

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
        ContabilidadeDTO dto = getDTO()
                .dataVencimento(null)
                .recorrente(null)
                .grupo(null)
                .subGrupo(null)
                .local(null)
                .descricao(null)
                .usouCartao(null)
                .cartao(null)
                .parcelas(null);
        String json = gson.toJson(dto);

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
        ContabilidadeDTO dto = getDTO()
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
        String json = gson.toJson(dto);

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
        ContabilidadeDTO dto = getDTO()
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
        String json = gson.toJson(dto);

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
        ContabilidadeDTO dto = getDTO()
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
        String json = gson.toJson(dto);

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
    public void criarComDataVencimentoIncorreto() throws Exception {
        ContabilidadeDTO dto = getDTO()
                .dataVencimento("27-04/2018");
        String json = gson.toJson(dto);

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
        ContabilidadeDTO dto = getDTO()
                .recorrente("NS");
        String json = gson.toJson(dto);

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
        ContabilidadeDTO dto = getDTO()
                .recorrente("NS")
                .usouCartao("NS");
        String json = gson.toJson(dto);

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
        ContabilidadeDTO dto = getDTO()
                .recorrente("NS")
                .usouCartao("NS")
                .parcelado("NS");
        String json = gson.toJson(dto);

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
        ContabilidadeDTO dto = getDTO()
                .parcelas("uma");
        String json = gson.toJson(dto);

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
        ContabilidadeDTO dto = getDTO()
                .tipo("DEBITOS");
        String json = gson.toJson(dto);

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
                .andExpect(jsonPath("$.mensagens[0]", is("java.lang.IllegalArgumentException: No enum constant br.com.accounting.core.entity.TipoContabilidade.DEBITOS")));
    }

    @Test
    public void criarComValorIncorreto() throws Exception {
        ContabilidadeDTO dto = getDTO()
                .valor("a24,04");
        String json = gson.toJson(dto);

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
        ContabilidadeDTO dto = getDTO()
                .recorrente("S")
                .parcelado("S");
        String json = gson.toJson(dto);

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
        ContabilidadeDTO dto = getDTO()
                .grupo("Um grupo");
        String json = gson.toJson(dto);

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
        ContabilidadeDTO dto = getDTO()
                .subGrupo("Um subgrupo");
        String json = gson.toJson(dto);

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
                .andExpect(jsonPath("$.mensagens[0]", is("SubGrupo não cadastrado.")));
    }

    @Test
    public void criarLocalNaoCadastrado() throws Exception {
        ContabilidadeDTO dto = getDTO()
                .local("Um outro local");
        String json = gson.toJson(dto);

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
        ContabilidadeDTO dto = getDTO()
                .cartao("1324");
        String json = gson.toJson(dto);

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
        ContabilidadeDTO dto = getDTO()
                .conta("Uma conta");
        String json = gson.toJson(dto);

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
    public void criarDuplicado() throws Exception {
        ContabilidadeDTO dto = getDTO();
        String json = gson.toJson(dto);

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
        ContabilidadeDTO dto = getDTO()
                .recorrente("N")
                .parcelado("N")
                .usouCartao("N");
        String json = gson.toJson(dto);

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
        ContabilidadeDTO dto = getDTO();
        String json = gson.toJson(dto);

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

    @Test
    public void criarRecorrente() throws Exception {
        ContabilidadeDTO dto = getDTO()
                .recorrente("S")
                .parcelado("N");
        String json = gson.toJson(dto);

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

    private ContabilidadeDTO getDTO() {
        return new ContabilidadeDTO()
                .dataVencimento("27/04/2018")
                .recorrente("N")
                .grupo("Saúde")
                .subGrupo("Suplementos")
                .local("Site")
                .descricao("Suplementos comprados pela Carol")
                .usouCartao("S")
                .cartao("0744")
                .parcelado("S")
                .parcelas("7")
                .conta("CAROL")
                .tipo("DEBITO")
                .valor("24,04");
    }

    private void criarGrupo() throws StoreException, BusinessException, GenericException {
        GrupoDTO grupoDTO = GrupoDTOFactory
                .create()
                .withNome("Saúde")
                .withDescricao("Grupo que gere gastos com saúde")
                .withSubGrupo("Suplementos")
                .build();
        grupoController.criar(grupoDTO);
    }

    private void criarSubGrupo() throws StoreException, BusinessException, GenericException {
        SubGrupoDTO subGrupoDTO = SubGrupoDTOFactory
                .create()
                .withNome("Suplementos")
                .withDescricao("Subgrupo de suplementos")
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
        CartaoDTO cartaoDTO = CartaoDTOFactory
                .create()
                .withNumero("0744")
                .withVencimento("27/03/2018")
                .withDiaMelhorCompra("17/04/2018")
                .withPortador("Carol")
                .withTipo("FISICO")
                .withLimite("2.000,00")
                .build();
        cartaoController.criar(cartaoDTO);
    }

    private void criarConta() throws StoreException, BusinessException, GenericException {
        ContaDTO contaDTO = ContaDTOFactory
                .create()
                .begin()
                .withNome("CAROL")
                .withDescricao("Valor separado para a Carol")
                .withValorDefault("500,00")
                .withCumulativo("S")
                .build();
        contaController.criar(contaDTO);
    }
}
