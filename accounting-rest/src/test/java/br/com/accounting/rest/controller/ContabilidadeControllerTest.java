package br.com.accounting.rest.controller;

import br.com.accounting.cartao.controller.CartaoController;
import br.com.accounting.cartao.vo.CartaoVO;
import br.com.accounting.commons.exception.BusinessException;
import br.com.accounting.commons.exception.GenericException;
import br.com.accounting.commons.exception.StoreException;
import br.com.accounting.commons.test.GenericTest;
import br.com.accounting.conta.controller.ContaController;
import br.com.accounting.conta.vo.ContaVO;
import br.com.accounting.rest.vo.*;
import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.IOException;

import static br.com.accounting.commons.util.Utils.getStringFromCurrentDate;
import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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

        ContabilidadeVO vo = getVOParcelado();
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
        ContabilidadeVO vo = getVOParcelado()
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
        ContabilidadeVO vo = getVOParcelado()
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
        ContabilidadeVO vo = getVOParcelado()
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
        ContabilidadeVO vo = getVOParcelado()
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
        ContabilidadeVO vo = getVOParcelado()
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
        ContabilidadeVO vo = getVOParcelado()
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
        ContabilidadeVO vo = getVOParcelado()
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
        ContabilidadeVO vo = getVOParcelado()
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
        ContabilidadeVO vo = getVOParcelado()
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
        ContabilidadeVO vo = getVOParcelado()
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
        ContabilidadeVO vo = getVOParcelado()
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
        ContabilidadeVO vo = getVOParcelado()
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
        ContabilidadeVO vo = getVOParcelado()
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
        ContabilidadeVO vo = getVOParcelado()
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
        ContabilidadeVO vo = getVOParcelado()
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
        ContabilidadeVO vo = getVOParcelado()
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
        ContabilidadeVO vo = getVOParcelado()
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
        ContabilidadeVO vo = getVOParcelado()
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
        ContabilidadeVO vo = getVOParcelado()
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
        ContabilidadeVO vo = getVOParcelado()
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
        ContabilidadeVO vo = getVOParcelado()
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
        ContabilidadeVO vo = getVOParcelado()
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
        ContabilidadeVO vo = getVOParcelado()
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
        ContabilidadeVO vo = getVOParcelado()
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
        ContabilidadeVO vo = getVOParcelado()
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
        ContabilidadeVO vo = getVOParcelado()
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
        ContabilidadeVO vo = getVOParcelado()
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
        ContabilidadeVO vo = getVOParcelado()
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

        ContabilidadeVO vo = getVOParcelado()
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
        ContabilidadeVO vo = getVOParcelado();
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
                .andExpect(jsonPath("$.mensagens", hasSize(1)))
                .andExpect(jsonPath("$.mensagens[0]", is("Contabilidade duplicada.")));
    }

    @Test
    public void criarSemCartao() throws Exception {
        criarContabilidadeSemCartaoComAssert();
    }

    @Test
    public void criarParcelada() throws Exception {
        criarContabilidadeParceladaComAssert();
    }

    @Test
    public void criarRecorrente() throws Exception {
        criarContabilidadeRecorrenteComAssert();
    }

    @Test
    public void atualizarSemDiretorio() throws Exception {
        deletarDiretorioEArquivos();

        ContabilidadeVO vo = getVOParcelado();
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
                .andExpect(jsonPath("$.mensagens", hasSize(1)))
                .andExpect(jsonPath("$.mensagens[0]", is("Erro de persistência ao atualizar.")));
    }

    @Test
    public void atualizarSemDataVencimento() throws Exception {
        ContabilidadeVO vo = getVOParcelado()
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
    public void atualizarSemDataVencimentoERecorrente() throws Exception {
        ContabilidadeVO vo = getVOParcelado()
                .dataVencimento(null)
                .recorrente(null);
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
                .andExpect(jsonPath("$.mensagens[0]", is("O campo dataVencimento é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[1]", is("O campo recorrente é obrigatório.")));
    }

    @Test
    public void atualizarSemDataVencimentoERecorrenteEGrupo() throws Exception {
        ContabilidadeVO vo = getVOParcelado()
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
        ContabilidadeVO vo = getVOParcelado()
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
        ContabilidadeVO vo = getVOParcelado()
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
        ContabilidadeVO vo = getVOParcelado()
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
        ContabilidadeVO vo = getVOParcelado()
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
        ContabilidadeVO vo = getVOParcelado()
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
        ContabilidadeVO vo = getVOParcelado()
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
        ContabilidadeVO vo = getVOParcelado()
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
        ContabilidadeVO vo = getVOParcelado()
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
        ContabilidadeVO vo = getVOParcelado()
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
        ContabilidadeVO vo = getVOParcelado()
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
        ContabilidadeVO vo = getVOParcelado()
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
        ContabilidadeVO vo = getVOParcelado()
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
        criarContabilidadeParceladaComAssert();

        ContabilidadeVO vo = getVOParcelado()
                .codigo(null);
        String json = gson.toJson(vo);

        mvc.perform(put("/contabilidade")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isExpectationFailed())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(417)))
                .andExpect(jsonPath("$.mensagens", hasSize(1)))
                .andExpect(jsonPath("$.mensagens[0]", is("Registro inexistente.")));
    }

    @Test
    public void atualizarDataLancamento() throws Exception {
        criarContabilidadeParceladaComAssert();

        ContabilidadeVO vo = getVOParcelado()
                .codigo("0")
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
                .andExpect(jsonPath("$.mensagens", hasSize(1)))
                .andExpect(jsonPath("$.mensagens[0]", is("O campo dataLançamento é obrigatório.")));
    }

    @Test
    public void atualizarDataLancamentoEParcela() throws Exception {
        criarContabilidadeParceladaComAssert();

        ContabilidadeVO vo = getVOParcelado()
                .codigo("0")
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
                .andExpect(jsonPath("$.mensagens", hasSize(2)))
                .andExpect(jsonPath("$.mensagens[0]", is("O campo dataLançamento é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[1]", is("O campo parcela é obrigatório.")));
    }

    @Test
    public void atualizarSemCodigoPai() throws Exception {
        criarContabilidadeParceladaComAssert();

        ContabilidadeVO vo = getVOParcelado()
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
        criarContabilidadeParceladaComAssert();

        ContabilidadeVO vo = getVOParcelado()
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
        criarContabilidadeParceladaComAssert();

        ContabilidadeVO vo = getVOParcelado()
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
        criarContabilidadeParceladaComAssert();

        ContabilidadeVO vo = getVOParcelado()
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
        criarContabilidadeParceladaComAssert();

        ContabilidadeVO vo = getVOParcelado()
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
        criarContabilidadeParceladaComAssert();

        ContabilidadeVO vo = getVOParcelado()
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
        criarContabilidadeParceladaComAssert();

        ContabilidadeVO vo = getVOParcelado()
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
        criarContabilidadeParceladaComAssert();

        ContabilidadeVO vo = getVOParcelado()
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
        criarContabilidadeParceladaComAssert();

        ContabilidadeVO vo = getVOParcelado()
                .codigo("20");
        String json = gson.toJson(vo);

        mvc.perform(put("/contabilidade")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isExpectationFailed())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(417)))
                .andExpect(jsonPath("$.mensagens", hasSize(1)))
                .andExpect(jsonPath("$.mensagens[0]", is("Registro inexistente.")));
    }

    @Test
    public void atualizarProibidoAlterarDataLancamento() throws Exception {
        criarContabilidadeParceladaComAssert();

        ContabilidadeVO vo = getVOParcelado()
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
        criarContabilidadeParceladaComAssert();

        ContabilidadeVO vo = getVOParcelado()
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
        criarContabilidadeParceladaComAssert();

        ContabilidadeVO vo = getVOParcelado()
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
        criarContabilidadeParceladaComAssert();

        ContabilidadeVO vo = getVOParcelado()
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
        ContabilidadeVO vo = getVOParcelado()
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
        ContabilidadeVO vo = getVOParcelado()
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
        ContabilidadeVO vo = getVOParcelado()
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
        ContabilidadeVO vo = getVOParcelado()
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
        ContabilidadeVO vo = getVOParcelado()
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

        ContabilidadeVO vo = getVOParcelado()
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
        criarContabilidadeParceladaComAssert();

        ContabilidadeVO vo = getVOParcelado()
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

    @Test
    public void atualizarRecursivamenteSemCartaoSemDiretorio() throws Exception {
        deletarDiretorioEArquivos();

        ContabilidadeVO vo = getVOSemCartao();
        String json = gson.toJson(vo);

        mvc.perform(put("/contabilidade/recursivo")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInsufficientStorage())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(507)))
                .andExpect(jsonPath("$.mensagens", hasSize(1)))
                .andExpect(jsonPath("$.mensagens[0]", is("Erro de persistência ao atualizar recursivamente.")));
    }

    @Test
    public void atualizarRecursivamenteParceladoSemDiretorio() throws Exception {
        deletarDiretorioEArquivos();

        ContabilidadeVO vo = getVOParcelado();
        String json = gson.toJson(vo);

        mvc.perform(put("/contabilidade/recursivo")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInsufficientStorage())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(507)))
                .andExpect(jsonPath("$.mensagens", hasSize(1)))
                .andExpect(jsonPath("$.mensagens[0]", is("Erro de persistência ao atualizar recursivamente.")));
    }

    @Test
    public void atualizarRecursivamenteRecorrenteSemDiretorio() throws Exception {
        deletarDiretorioEArquivos();

        ContabilidadeVO vo = getVORecorrente();
        String json = gson.toJson(vo);

        mvc.perform(put("/contabilidade/recursivo")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInsufficientStorage())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(507)))
                .andExpect(jsonPath("$.mensagens", hasSize(1)))
                .andExpect(jsonPath("$.mensagens[0]", is("Erro de persistência ao atualizar recursivamente.")));
    }

    @Test
    public void atualizarRecursivamenteSemDataVencimento() throws Exception {
        ContabilidadeVO vo = getVOParcelado()
                .dataVencimento(null);
        String json = gson.toJson(vo);

        mvc.perform(put("/contabilidade/recursivo")
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
    public void atualizarRecursivamenteSemDataVencimentoERecorrente() throws Exception {
        ContabilidadeVO vo = getVOParcelado()
                .dataVencimento(null)
                .recorrente(null);
        String json = gson.toJson(vo);

        mvc.perform(put("/contabilidade/recursivo")
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
    public void atualizarRecursivamenteSemDataVencimentoERecorrenteEGrupo() throws Exception {
        ContabilidadeVO vo = getVOParcelado()
                .dataVencimento(null)
                .recorrente(null)
                .grupo(null);
        String json = gson.toJson(vo);

        mvc.perform(put("/contabilidade/recursivo")
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
    public void atualizarRecursivamenteSemDataVencimentoERecorrenteEGrupoESubGrupo() throws Exception {
        ContabilidadeVO vo = getVOParcelado()
                .dataVencimento(null)
                .recorrente(null)
                .grupo(null)
                .subGrupo(null);
        String json = gson.toJson(vo);

        mvc.perform(put("/contabilidade/recursivo")
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
    public void atualizarRecursivamenteSemDataVencimentoERecorrenteEGrupoESubGrupoELocal() throws Exception {
        ContabilidadeVO vo = getVOParcelado()
                .dataVencimento(null)
                .recorrente(null)
                .grupo(null)
                .subGrupo(null)
                .local(null);
        String json = gson.toJson(vo);

        mvc.perform(put("/contabilidade/recursivo")
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
    public void atualizarRecursivamenteSemDataVencimentoERecorrenteEGrupoESubGrupoELocalEDescricao() throws Exception {
        ContabilidadeVO vo = getVOParcelado()
                .dataVencimento(null)
                .recorrente(null)
                .grupo(null)
                .subGrupo(null)
                .local(null)
                .descricao(null);
        String json = gson.toJson(vo);

        mvc.perform(put("/contabilidade/recursivo")
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
    public void atualizarRecursivamenteSemDataVencimentoERecorrenteEGrupoESubGrupoELocalEDescricaoEUsouCartao() throws Exception {
        ContabilidadeVO vo = getVOParcelado()
                .dataVencimento(null)
                .recorrente(null)
                .grupo(null)
                .subGrupo(null)
                .local(null)
                .descricao(null)
                .usouCartao(null);
        String json = gson.toJson(vo);

        mvc.perform(put("/contabilidade/recursivo")
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
    public void atualizarRecursivamenteSemDataVencimentoERecorrenteEGrupoESubGrupoELocalEDescricaoEUsouCartaoECartao() throws Exception {
        ContabilidadeVO vo = getVOParcelado()
                .dataVencimento(null)
                .recorrente(null)
                .grupo(null)
                .subGrupo(null)
                .local(null)
                .descricao(null)
                .usouCartao(null)
                .cartao(null);
        String json = gson.toJson(vo);

        mvc.perform(put("/contabilidade/recursivo")
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
    public void atualizarRecursivamenteSemDataVencimentoERecorrenteEGrupoESubGrupoELocalEDescricaoECartao() throws Exception {
        ContabilidadeVO vo = getVOParcelado()
                .dataVencimento(null)
                .recorrente(null)
                .grupo(null)
                .subGrupo(null)
                .local(null)
                .descricao(null)
                .cartao(null);
        String json = gson.toJson(vo);

        mvc.perform(put("/contabilidade/recursivo")
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
    public void atualizarRecursivamenteSemDataVencimentoERecorrenteEGrupoESubGrupoELocalEDescricaoEUsouCartaoECartaoEParcelado() throws Exception {
        ContabilidadeVO vo = getVOParcelado()
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

        mvc.perform(put("/contabilidade/recursivo")
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
    public void atualizarRecursivamenteSemDataVencimentoERecorrenteEGrupoESubGrupoELocalEDescricaoEUsouCartaoECartaoEParceladoEParcelas() throws Exception {
        ContabilidadeVO vo = getVOParcelado()
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

        mvc.perform(put("/contabilidade/recursivo")
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
    public void atualizarRecursivamenteSemDataVencimentoERecorrenteEGrupoESubGrupoELocalEDescricaoEUsouCartaoECartaoEParcelas() throws Exception {
        ContabilidadeVO vo = getVOParcelado()
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

        mvc.perform(put("/contabilidade/recursivo")
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
    public void atualizarRecursivamenteSemDataVencimentoERecorrenteEGrupoESubGrupoELocalEDescricaoEUsouCartaoECartaoEParceladoEParcelasEConta() throws Exception {
        ContabilidadeVO vo = getVOParcelado()
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

        mvc.perform(put("/contabilidade/recursivo")
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
    public void atualizarRecursivamenteSemDataVencimentoERecorrenteEGrupoESubGrupoELocalEDescricaoEUsouCartaoECartaoEParceladoEParcelasEContaETipo() throws Exception {
        ContabilidadeVO vo = getVOParcelado()
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

        mvc.perform(put("/contabilidade/recursivo")
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
    public void atualizarRecursivamenteParceladoSemCampos() throws Exception {
        ContabilidadeVO vo = getVOParcelado()
                .dataVencimento(null)
                .recorrente(null)
                .grupo(null)
                .subGrupo(null)
                .local(null)
                .descricao(null)
                .usouCartao(null)
                .cartao(null)
                .parcelas(null)
                .conta(null)
                .tipo(null)
                .valor(null);
        String json = gson.toJson(vo);

        mvc.perform(put("/contabilidade/recursivo")
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
                .andExpect(jsonPath("$.mensagens[6]", is("O campo conta é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[7]", is("O campo tipo é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[8]", is("O campo valor é obrigatório.")));
    }

    @Test
    public void atualizarRecursivamenteSemCampos() throws Exception {
        ContabilidadeVO vo = getVOParcelado()
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

        mvc.perform(put("/contabilidade/recursivo")
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
    public void atualizarRecursivamenteSemCodigo() throws Exception {
        criarContabilidadeParceladaComAssert();

        ContabilidadeVO vo = getVOParcelado()
                .codigo(null);
        String json = gson.toJson(vo);

        mvc.perform(put("/contabilidade/recursivo")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isExpectationFailed())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(417)))
                .andExpect(jsonPath("$.mensagens", hasSize(1)))
                .andExpect(jsonPath("$.mensagens[0]", is("Registro inexistente.")));
    }

    @Test
    public void atualizarRecursivamenteRecorrenteSemCodigo() throws Exception {
        ContabilidadeVO vo = getVORecorrente()
                .codigo(null);
        String json = gson.toJson(vo);

        mvc.perform(put("/contabilidade/recursivo")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isExpectationFailed())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(417)))
                .andExpect(jsonPath("$.mensagens", hasSize(1)))
                .andExpect(jsonPath("$.mensagens[0]", is("Não foi possível buscar os registros recorrentes relacionados.")));
    }

    @Test
    public void atualizarRecursivamenteSemCartaoSemCodigo() throws Exception {
        ContabilidadeVO vo = getVOSemCartao()
                .codigo(null);
        String json = gson.toJson(vo);

        mvc.perform(put("/contabilidade/recursivo")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isExpectationFailed())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(417)))
                .andExpect(jsonPath("$.mensagens", hasSize(1)))
                .andExpect(jsonPath("$.mensagens[0]", is("Registro inexistente.")));
    }

    @Test
    public void atualizarRecursivamenteDataLancamento() throws Exception {
        criarContabilidadeParceladaComAssert();

        ContabilidadeVO vo = getVOParcelado()
                .codigo("0")
                .dataLancamento(null);
        String json = gson.toJson(vo);

        mvc.perform(put("/contabilidade/recursivo")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(400)))
                .andExpect(jsonPath("$.mensagens", hasSize(1)))
                .andExpect(jsonPath("$.mensagens[0]", is("O campo dataLançamento é obrigatório.")));
    }

    @Test
    public void atualizarRecursivamenteDataLancamentoEParcela() throws Exception {
        criarContabilidadeParceladaComAssert();

        ContabilidadeVO vo = getVOParcelado()
                .codigo("0")
                .dataLancamento(null)
                .parcela(null);
        String json = gson.toJson(vo);

        mvc.perform(put("/contabilidade/recursivo")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(400)))
                .andExpect(jsonPath("$.mensagens", hasSize(2)))
                .andExpect(jsonPath("$.mensagens[0]", is("O campo dataLançamento é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[1]", is("O campo parcela é obrigatório.")));
    }

    @Test
    public void atualizarRecursivamenteSemCodigoPai() throws Exception {
        criarContabilidadeParceladaComAssert();

        ContabilidadeVO vo = getVOParcelado()
                .codigo("1");
        String json = gson.toJson(vo);

        mvc.perform(put("/contabilidade/recursivo")
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
    public void atualizarRecursivamenteComDataVencimentoIncorreta() throws Exception {
        criarContabilidadeParceladaComAssert();

        ContabilidadeVO vo = getVOParcelado()
                .codigo("0")
                .dataVencimento("27-04/2018");
        String json = gson.toJson(vo);

        mvc.perform(put("/contabilidade/recursivo")
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
    public void atualizarRecursivamenteComRecorrenteIncorreto() throws Exception {
        criarContabilidadeParceladaComAssert();

        ContabilidadeVO vo = getVOParcelado()
                .recorrente("NS");
        String json = gson.toJson(vo);

        mvc.perform(put("/contabilidade/recursivo")
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
    public void atualizarRecursivamenteComRecorrenteEUsouCartaoIncorretos() throws Exception {
        criarContabilidadeParceladaComAssert();

        ContabilidadeVO vo = getVOParcelado()
                .recorrente("NS")
                .usouCartao("NS");
        String json = gson.toJson(vo);

        mvc.perform(put("/contabilidade/recursivo")
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
    public void atualizarRecursivamenteComRecorrenteEUsouCartaoEParceladoIncorretos() throws Exception {
        criarContabilidadeParceladaComAssert();

        ContabilidadeVO vo = getVOParcelado()
                .recorrente("NS")
                .usouCartao("NS")
                .parcelado("NS");
        String json = gson.toJson(vo);

        mvc.perform(put("/contabilidade/recursivo")
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
    public void atualizarRecursivamenteComTipoIncorreto() throws Exception {
        criarContabilidadeParceladaComAssert();

        ContabilidadeVO vo = getVOParcelado()
                .codigo("0")
                .tipo("DEBITOS");
        String json = gson.toJson(vo);

        mvc.perform(put("/contabilidade/recursivo")
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
    public void atualizarRecursivamenteComValorIncorreto() throws Exception {
        criarContabilidadeParceladaComAssert();

        ContabilidadeVO vo = getVOParcelado()
                .codigo("0")
                .valor("a24,04");
        String json = gson.toJson(vo);

        mvc.perform(put("/contabilidade/recursivo")
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
    public void atualizarRecursivamenteRecorrenteEParcelado() throws Exception {
        criarContabilidadeParceladaComAssert();

        ContabilidadeVO vo = getVOParcelado()
                .codigo("0")
                .recorrente("S")
                .parcelado("S");
        String json = gson.toJson(vo);

        mvc.perform(put("/contabilidade/recursivo")
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
    public void atualizarRecursivamenteProibidoAlterarCodigo() throws Exception {
        criarContabilidadeParceladaComAssert();

        ContabilidadeVO vo = getVOParcelado()
                .codigo("20");
        String json = gson.toJson(vo);

        mvc.perform(put("/contabilidade/recursivo")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isExpectationFailed())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(417)))
                .andExpect(jsonPath("$.mensagens", hasSize(1)))
                .andExpect(jsonPath("$.mensagens[0]", is("Registro inexistente.")));
    }

    @Test
    public void atualizarRecursivamenteProibidoAlterarDataLancamento() throws Exception {
        criarContabilidadeParceladaComAssert();

        ContabilidadeVO vo = getVOParcelado()
                .codigo("0")
                .dataLancamento("01/01/2000");
        String json = gson.toJson(vo);

        mvc.perform(put("/contabilidade/recursivo")
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
    public void atualizarRecursivamenteProibidoAlterarParcelas() throws Exception {
        criarContabilidadeParceladaComAssert();

        ContabilidadeVO vo = getVOParcelado()
                .codigo("0")
                .parcelas("10");
        String json = gson.toJson(vo);

        mvc.perform(put("/contabilidade/recursivo")
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
    public void atualizarRecursivamenteProibidoAlterarParcela() throws Exception {
        criarContabilidadeParceladaComAssert();

        ContabilidadeVO vo = getVOParcelado()
                .codigo("0")
                .parcela("2");
        String json = gson.toJson(vo);

        mvc.perform(put("/contabilidade/recursivo")
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
    public void atualizarRecursivamenteProibidoAlterarCodigoPai() throws Exception {
        criarContabilidadeParceladaComAssert();

        ContabilidadeVO vo = getVOParcelado()
                .codigo("0")
                .codigoPai("1");
        String json = gson.toJson(vo);

        mvc.perform(put("/contabilidade/recursivo")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(400)))
                .andExpect(jsonPath("$.mensagens", hasSize(2)))
                .andExpect(jsonPath("$.mensagens[0]", is("O campo parcela não pode ser alterado.")))
                .andExpect(jsonPath("$.mensagens[1]", is("O campo códigoPai não pode ser alterado.")));
    }

    @Test
    public void atualizarRecursivamenteGrupoNaoCadastrado() throws Exception {
        ContabilidadeVO vo = getVOParcelado()
                .grupo("Um grupo");
        String json = gson.toJson(vo);

        mvc.perform(put("/contabilidade/recursivo")
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
    public void atualizarRecursivamenteSubGrupoNaoCadastrado() throws Exception {
        ContabilidadeVO vo = getVOParcelado()
                .subGrupo("Um subgrupo");
        String json = gson.toJson(vo);

        mvc.perform(put("/contabilidade/recursivo")
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
    public void atualizarRecursivamenteLocalNaoCadastrado() throws Exception {
        ContabilidadeVO vo = getVOParcelado()
                .local("Um outro local");
        String json = gson.toJson(vo);

        mvc.perform(put("/contabilidade/recursivo")
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
    public void atualizarRecursivamenteCartaoNaoCadastrado() throws Exception {
        ContabilidadeVO vo = getVOParcelado()
                .cartao("1324");
        String json = gson.toJson(vo);

        mvc.perform(put("/contabilidade/recursivo")
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
    public void atualizarRecursivamenteContaNaoCadastrada() throws Exception {
        ContabilidadeVO vo = getVOParcelado()
                .conta("Uma conta");
        String json = gson.toJson(vo);

        mvc.perform(put("/contabilidade/recursivo")
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
    public void atualizarRecursivamenteGrupoESubGrupoNaoAssociados() throws Exception {
        criarSubGrupoAluguel();

        ContabilidadeVO vo = getVOParcelado()
                .grupo("Saúde")
                .subGrupo("Aluguel");

        String json = gson.toJson(vo);

        mvc.perform(put("/contabilidade/recursivo")
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
    public void atualizarRecursivamenteSemCartao() throws Exception {
        criarContabilidadeSemCartaoComAssert();

        ContabilidadeVO vo = getVOSemCartao()
                .codigo("0");
        String json = gson.toJson(vo);

        mvc.perform(put("/contabilidade/recursivo")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andExpect(content().string(""));
    }

    @Test
    public void atualizarRecursivamenteRecorrente() throws Exception {
        criarContabilidadeRecorrenteComAssert();

        ContabilidadeVO vo = getVORecorrente()
                .codigo("0");
        String json = gson.toJson(vo);

        mvc.perform(put("/contabilidade/recursivo")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andExpect(content().string(""));
    }

    @Test
    public void atualizarRecursivamenteParcelada() throws Exception {
        criarContabilidadeParceladaComAssert();

        ContabilidadeVO vo = getVOParcelado()
                .codigo("0");
        String json = gson.toJson(vo);

        mvc.perform(put("/contabilidade/recursivo")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andExpect(content().string(""));
    }

    @Test
    public void incrementarRecorrenteSemDiretorio() throws Exception {
        deletarDiretorioEArquivos();

        Integer anos = 1;

        mvc.perform(put("/contabilidade/recorrente/{anos}", anos)
                .characterEncoding("UTF-8")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInsufficientStorage())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(507)))
                .andExpect(jsonPath("$.mensagens", hasSize(1)))
                .andExpect(jsonPath("$.mensagens[0]", is("Erro de persistência ao incrementar recorrentes.")));
    }

    @Test
    public void incrementarRecorrenteSemAno() throws Exception {
        Integer anos = null;

        mvc.perform(put("/contabilidade/recorrente/{anos}", anos)
                .characterEncoding("UTF-8")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isMethodNotAllowed())
                .andExpect(content().string(""));
    }

    @Test
    public void incrementarRecorrenteComAnoNegativo() throws Exception {
        Integer anos = -1;

        mvc.perform(put("/contabilidade/recorrente/{anos}", anos)
                .characterEncoding("UTF-8")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(400)))
                .andExpect(jsonPath("$.mensagens", hasSize(1)))
                .andExpect(jsonPath("$.mensagens[0]", is("O valor de anos deve ser maior ou igual a 1.")));
    }

    @Test
    public void incrementarRecorrenteSemRegistros() throws Exception {
        Integer anos = 1;

        mvc.perform(put("/contabilidade/recorrente/{anos}", anos)
                .characterEncoding("UTF-8")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andExpect(content().string(""));
    }

    @Test
    public void incrementarRecorrente1Ano() throws Exception {
        criarContabilidadeRecorrenteComAssert();

        Integer anos = 1;

        mvc.perform(put("/contabilidade/recorrente/{anos}", anos)
                .characterEncoding("UTF-8")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigos", hasSize(1)))
                .andExpect(jsonPath("$.codigos[0]", is(8)));
    }

    @Test
    public void incrementarRecorrente2Anos() throws Exception {
        criarContabilidadeRecorrenteComAssert();

        Integer anos = 2;

        mvc.perform(put("/contabilidade/recorrente/{anos}", anos)
                .characterEncoding("UTF-8")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigos", hasSize(13)))
                .andExpect(jsonPath("$.codigos[0]", is(8)))
                .andExpect(jsonPath("$.codigos[1]", is(9)))
                .andExpect(jsonPath("$.codigos[2]", is(10)))
                .andExpect(jsonPath("$.codigos[3]", is(11)))
                .andExpect(jsonPath("$.codigos[4]", is(12)))
                .andExpect(jsonPath("$.codigos[5]", is(13)))
                .andExpect(jsonPath("$.codigos[6]", is(14)))
                .andExpect(jsonPath("$.codigos[7]", is(15)))
                .andExpect(jsonPath("$.codigos[8]", is(16)))
                .andExpect(jsonPath("$.codigos[9]", is(17)))
                .andExpect(jsonPath("$.codigos[10]", is(18)))
                .andExpect(jsonPath("$.codigos[11]", is(19)))
                .andExpect(jsonPath("$.codigos[12]", is(20)));
    }

    @Test
    public void realizarPagamentoSemDiretorio() throws Exception {
        deletarDiretorioEArquivos();

        Long codigo = 0L;

        mvc.perform(put("/contabilidade/pagamento/{codigo}", codigo)
                .characterEncoding("UTF-8")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInsufficientStorage())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(507)))
                .andExpect(jsonPath("$.mensagens", hasSize(1)))
                .andExpect(jsonPath("$.mensagens[0]", is("Erro de persistência ao realizar pagamento.")));
    }

    @Test
    public void realizarPagamentoSemCodigo() throws Exception {
        Long codigo = null;

        mvc.perform(put("/contabilidade/pagamento/{codigo}", codigo)
                .characterEncoding("UTF-8")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isMethodNotAllowed())
                .andExpect(content().string(""));
    }

    @Test
    public void realizarPagamentoComCodigoIncorreto() throws Exception {
        String codigo = "a";

        mvc.perform(put("/contabilidade/pagamento/{codigo}", codigo)
                .characterEncoding("UTF-8")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(""));
    }

    @Test
    public void realizarPagamentoSemRegistro() throws Exception {
        String codigo = "0";

        mvc.perform(put("/contabilidade/pagamento/{codigo}", codigo)
                .characterEncoding("UTF-8")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isExpectationFailed())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(417)))
                .andExpect(jsonPath("$.mensagens", hasSize(1)))
                .andExpect(jsonPath("$.mensagens[0]", is("Registro inexistente.")));
    }

    @Test
    public void realizarPagamento() throws Exception {
        criarContabilidadeParceladaComAssert();

        Integer codigo = 0;

        mvc.perform(put("/contabilidade/pagamento/{codigo}", codigo)
                .characterEncoding("UTF-8")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andExpect(content().string(""));

        mvc.perform(get("/contabilidade/{codigo}", codigo)
                .characterEncoding("UTF-8")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is("0")))
                .andExpect(jsonPath("$.dataPagamento", is(getStringFromCurrentDate())));

        codigo = 1;

        mvc.perform(get("/contabilidade/{codigo}", codigo)
                .characterEncoding("UTF-8")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is("1")))
                .andExpect(jsonPath("$.dataPagamento", isEmptyOrNullString()));
    }

    @Test
    public void excluirSemDiretorio() throws Exception {
        deletarDiretorioEArquivos();

        String codigo = "0";

        mvc.perform(delete("/contabilidade/{codigo}", codigo)
                .characterEncoding("UTF-8")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInsufficientStorage())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(507)))
                .andExpect(jsonPath("$.mensagens", hasSize(1)))
                .andExpect(jsonPath("$.mensagens[0]", is("Erro de persistência ao excluir.")));
    }

    @Test
    public void excluirSemCodigo() throws Exception {
        String codigo = null;

        mvc.perform(delete("/contabilidade/{codigo}", codigo)
                .characterEncoding("UTF-8")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isMethodNotAllowed())
                .andExpect(content().string(""));
    }

    @Test
    public void excluirComCodigoIncorreto() throws Exception {
        String codigo = "a";

        mvc.perform(delete("/contabilidade/{codigo}", codigo)
                .characterEncoding("UTF-8")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(""));
    }

    @Test
    public void excluirInexistente() throws Exception {
        String codigo = "0";

        mvc.perform(delete("/contabilidade/{codigo}", codigo)
                .characterEncoding("UTF-8")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isExpectationFailed())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(417)))
                .andExpect(jsonPath("$.mensagens", hasSize(1)))
                .andExpect(jsonPath("$.mensagens[0]", is("Registro inexistente.")));
    }

    @Test
    public void excluir() throws Exception {
        criarContabilidadeParceladaComAssert();

        String codigo = "0";

        mvc.perform(delete("/contabilidade/{codigo}", codigo)
                .characterEncoding("UTF-8")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andExpect(content().string(""));
    }

    @Test
    public void excluirRecursivamenteParceladoSemDiretorio() throws Exception {
        deletarDiretorioEArquivos();

        ContabilidadeVO vo = getVOParcelado()
                .codigo("0");
        String json = gson.toJson(vo);

        mvc.perform(delete("/contabilidade/recursivo")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInsufficientStorage())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(507)))
                .andExpect(jsonPath("$.mensagens", hasSize(1)))
                .andExpect(jsonPath("$.mensagens[0]", is("Erro de persistência ao excluir recursivamente.")));
    }

    @Test
    public void excluirRecursivamenteRecorrenteSemDiretorio() throws Exception {
        deletarDiretorioEArquivos();

        ContabilidadeVO vo = getVORecorrente()
                .codigo("0");
        String json = gson.toJson(vo);

        mvc.perform(delete("/contabilidade/recursivo")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInsufficientStorage())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(507)))
                .andExpect(jsonPath("$.mensagens", hasSize(1)))
                .andExpect(jsonPath("$.mensagens[0]", is("Erro de persistência ao excluir recursivamente.")));
    }

    @Test
    public void excluirRecursivamenteComCodigoErrado() throws Exception {
        ContabilidadeVO vo = getVOParcelado()
                .codigo("a");
        String json = gson.toJson(vo);

        mvc.perform(delete("/contabilidade/recursivo")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(400)))
                .andExpect(jsonPath("$.mensagens", hasSize(1)))
                .andExpect(jsonPath("$.mensagens[0]", is("java.lang.NumberFormatException: For input string: \"a\"")));
    }

    @Test
    public void excluirRecursivamenteParceladoSemRegistros() throws Exception {
        ContabilidadeVO vo = getVOParcelado()
                .codigo("0");
        String json = gson.toJson(vo);

        mvc.perform(delete("/contabilidade/recursivo")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andExpect(content().string(""));
    }

    @Test
    public void excluirRecursivamenteRecorrenteSemRegistros() throws Exception {
        ContabilidadeVO vo = getVORecorrente()
                .codigo("0");
        String json = gson.toJson(vo);

        mvc.perform(delete("/contabilidade/recursivo")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andExpect(content().string(""));
    }

    @Test
    public void excluirRecursivamenteParcelado() throws Exception {
        criarContabilidadeParceladaComAssert();

        ContabilidadeVO vo = getVOParcelado()
                .codigo("0");
        String json = gson.toJson(vo);

        mvc.perform(delete("/contabilidade/recursivo")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andExpect(content().string(""));

        mvc.perform(get("/contabilidade")
                .characterEncoding("UTF-8")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andExpect(content().string(""));
    }

    @Test
    public void excluirRecursivamenteRecorrente() throws Exception {
        criarContabilidadeRecorrenteComAssert();

        ContabilidadeVO vo = getVORecorrente()
                .codigo("0");
        String json = gson.toJson(vo);

        mvc.perform(delete("/contabilidade/recursivo")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andExpect(content().string(""));

        mvc.perform(get("/contabilidade")
                .characterEncoding("UTF-8")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andExpect(content().string(""));
    }

    @Test
    public void buscarPorCodigoSemDiretorio() throws Exception {
        deletarDiretorioEArquivos();

        String codigo = "0";

        mvc.perform(get("/contabilidade/{codigo}", codigo)
                .characterEncoding("UTF-8")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInsufficientStorage())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(507)))
                .andExpect(jsonPath("$.mensagens", hasSize(1)))
                .andExpect(jsonPath("$.mensagens[0]", is("Erro de persistência ao buscar por código.")));
    }

    @Test
    public void buscarPorCodigoSemCodigo() throws Exception {
        String codigo = null;

        mvc.perform(get("/contabilidade/{codigo}", codigo)
                .characterEncoding("UTF-8")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andExpect(content().string(""));
    }

    @Test
    public void buscarPorCodigoComCodigoIncorreto() throws Exception {
        String codigo = "a";

        mvc.perform(get("/contabilidade/{codigo}", codigo)
                .characterEncoding("UTF-8")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(""));
    }

    @Test
    public void buscarPorCodigoInexistente() throws Exception {
        String codigo = "0";

        mvc.perform(get("/contabilidade/{codigo}", codigo)
                .characterEncoding("UTF-8")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isExpectationFailed())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(417)))
                .andExpect(jsonPath("$.mensagens", hasSize(1)))
                .andExpect(jsonPath("$.mensagens[0]", is("Registro inexistente.")));
    }

    @Test
    public void buscarPorCodigoContabilidadeSemCartao() throws Exception {
        criarContabilidadeSemCartaoComAssert();

        String codigo = "0";

        mvc.perform(get("/contabilidade/{codigo}", codigo)
                .characterEncoding("UTF-8")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is("0")))
                .andExpect(jsonPath("$.dataLancamento", is(getStringFromCurrentDate())))
                .andExpect(jsonPath("$.dataAtualizacao", is(getStringFromCurrentDate())))
                .andExpect(jsonPath("$.dataVencimento", is("27/04/2018")))
                .andExpect(jsonPath("$.dataPagamento", isEmptyOrNullString()))
                .andExpect(jsonPath("$.recorrente", is("N")))
                .andExpect(jsonPath("$.grupo", is("Saúde")))
                .andExpect(jsonPath("$.subGrupo", is("Suplementos")))
                .andExpect(jsonPath("$.local", is("Site")))
                .andExpect(jsonPath("$.descricao", is("Suplementos comprados pela Carol")))
                .andExpect(jsonPath("$.usouCartao", is("N")))
                .andExpect(jsonPath("$.cartao", isEmptyOrNullString()))
                .andExpect(jsonPath("$.parcelado", is("N")))
                .andExpect(jsonPath("$.parcela", isEmptyOrNullString()))
                .andExpect(jsonPath("$.parcelas", isEmptyOrNullString()))
                .andExpect(jsonPath("$.conta", is("CAROL")))
                .andExpect(jsonPath("$.tipo", is("DEBITO")))
                .andExpect(jsonPath("$.valor", is("24,04")))
                .andExpect(jsonPath("$.codigoPai", isEmptyOrNullString()))
                .andExpect(jsonPath("$.proximoLancamento", isEmptyOrNullString()));
    }

    @Test
    public void buscarPorCodigoParcelada() throws Exception {
        criarContabilidadeParceladaComAssert();

        String codigo = "0";

        mvc.perform(get("/contabilidade/{codigo}", codigo)
                .characterEncoding("UTF-8")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is("0")))
                .andExpect(jsonPath("$.dataLancamento", is(getStringFromCurrentDate())))
                .andExpect(jsonPath("$.dataAtualizacao", is(getStringFromCurrentDate())))
                .andExpect(jsonPath("$.dataVencimento", is("27/04/2018")))
                .andExpect(jsonPath("$.dataPagamento", isEmptyOrNullString()))
                .andExpect(jsonPath("$.recorrente", is("N")))
                .andExpect(jsonPath("$.grupo", is("Saúde")))
                .andExpect(jsonPath("$.subGrupo", is("Suplementos")))
                .andExpect(jsonPath("$.local", is("Site")))
                .andExpect(jsonPath("$.descricao", is("Suplementos comprados pela Carol")))
                .andExpect(jsonPath("$.usouCartao", is("S")))
                .andExpect(jsonPath("$.cartao", is("0744")))
                .andExpect(jsonPath("$.parcelado", is("S")))
                .andExpect(jsonPath("$.parcela", is("1")))
                .andExpect(jsonPath("$.parcelas", is("7")))
                .andExpect(jsonPath("$.conta", is("CAROL")))
                .andExpect(jsonPath("$.tipo", is("DEBITO")))
                .andExpect(jsonPath("$.valor", is("24,04")))
                .andExpect(jsonPath("$.codigoPai", isEmptyOrNullString()))
                .andExpect(jsonPath("$.proximoLancamento", isEmptyOrNullString()));
    }

    @Test
    public void buscarPorCodigoRecorrente() throws Exception {
        criarContabilidadeRecorrenteComAssert();

        String codigo = "0";

        mvc.perform(get("/contabilidade/{codigo}", codigo)
                .characterEncoding("UTF-8")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is("0")))
                .andExpect(jsonPath("$.dataLancamento", is(getStringFromCurrentDate())))
                .andExpect(jsonPath("$.dataAtualizacao", is(getStringFromCurrentDate())))
                .andExpect(jsonPath("$.dataVencimento", is("27/04/2018")))
                .andExpect(jsonPath("$.dataPagamento", isEmptyOrNullString()))
                .andExpect(jsonPath("$.recorrente", is("S")))
                .andExpect(jsonPath("$.grupo", is("Saúde")))
                .andExpect(jsonPath("$.subGrupo", is("Suplementos")))
                .andExpect(jsonPath("$.local", is("Site")))
                .andExpect(jsonPath("$.descricao", is("Suplementos comprados pela Carol")))
                .andExpect(jsonPath("$.usouCartao", is("S")))
                .andExpect(jsonPath("$.cartao", is("0744")))
                .andExpect(jsonPath("$.parcelado", is("N")))
                .andExpect(jsonPath("$.parcela", isEmptyOrNullString()))
                .andExpect(jsonPath("$.parcelas", isEmptyOrNullString()))
                .andExpect(jsonPath("$.conta", is("CAROL")))
                .andExpect(jsonPath("$.tipo", is("DEBITO")))
                .andExpect(jsonPath("$.valor", is("24,04")))
                .andExpect(jsonPath("$.codigoPai", isEmptyOrNullString()))
                .andExpect(jsonPath("$.proximoLancamento", is("1")));
    }

    @Test
    public void buscarTudoSemDiretorio() throws Exception {
        deletarDiretorioEArquivos();

        mvc.perform(get("/contabilidade")
                .characterEncoding("UTF-8")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInsufficientStorage())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(507)))
                .andExpect(jsonPath("$.mensagens", hasSize(1)))
                .andExpect(jsonPath("$.mensagens[0]", is("Erro de persistência ao buscar tudo.")));
    }

    @Test
    public void buscarTudoInexistente() throws Exception {
        mvc.perform(get("/contabilidade")
                .characterEncoding("UTF-8")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andExpect(content().string(""));
    }

    @Test
    public void buscarTudoSemCartao() throws Exception {
        criarContabilidadeSemCartaoComAssert();

        mvc.perform(get("/contabilidade")
                .characterEncoding("UTF-8")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].codigo", is("0")))
                .andExpect(jsonPath("$[0].dataLancamento", is(getStringFromCurrentDate())))
                .andExpect(jsonPath("$[0].dataAtualizacao", is(getStringFromCurrentDate())))
                .andExpect(jsonPath("$[0].dataVencimento", is("27/04/2018")))
                .andExpect(jsonPath("$[0].dataPagamento", isEmptyOrNullString()))
                .andExpect(jsonPath("$[0].recorrente", is("N")))
                .andExpect(jsonPath("$[0].grupo", is("Saúde")))
                .andExpect(jsonPath("$[0].subGrupo", is("Suplementos")))
                .andExpect(jsonPath("$[0].local", is("Site")))
                .andExpect(jsonPath("$[0].descricao", is("Suplementos comprados pela Carol")))
                .andExpect(jsonPath("$[0].usouCartao", is("N")))
                .andExpect(jsonPath("$[0].cartao", isEmptyOrNullString()))
                .andExpect(jsonPath("$[0].parcelado", is("N")))
                .andExpect(jsonPath("$[0].parcela", isEmptyOrNullString()))
                .andExpect(jsonPath("$[0].parcelas", isEmptyOrNullString()))
                .andExpect(jsonPath("$[0].conta", is("CAROL")))
                .andExpect(jsonPath("$[0].tipo", is("DEBITO")))
                .andExpect(jsonPath("$[0].valor", is("24,04")))
                .andExpect(jsonPath("$[0].codigoPai", isEmptyOrNullString()))
                .andExpect(jsonPath("$[0].proximoLancamento", isEmptyOrNullString()));
    }

    @Test
    public void buscarTudoParcelado() throws Exception {
        criarContabilidadeParceladaComAssert();

        mvc.perform(get("/contabilidade")
                .characterEncoding("UTF-8")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(7)))

                .andExpect(jsonPath("$[0].codigo", is("0")))
                .andExpect(jsonPath("$[0].dataLancamento", is(getStringFromCurrentDate())))
                .andExpect(jsonPath("$[0].dataAtualizacao", is(getStringFromCurrentDate())))
                .andExpect(jsonPath("$[0].dataVencimento", is("27/04/2018")))
                .andExpect(jsonPath("$[0].dataPagamento", isEmptyOrNullString()))
                .andExpect(jsonPath("$[0].recorrente", is("N")))
                .andExpect(jsonPath("$[0].grupo", is("Saúde")))
                .andExpect(jsonPath("$[0].subGrupo", is("Suplementos")))
                .andExpect(jsonPath("$[0].local", is("Site")))
                .andExpect(jsonPath("$[0].descricao", is("Suplementos comprados pela Carol")))
                .andExpect(jsonPath("$[0].usouCartao", is("S")))
                .andExpect(jsonPath("$[0].cartao", is("0744")))
                .andExpect(jsonPath("$[0].parcelado", is("S")))
                .andExpect(jsonPath("$[0].parcela", is("1")))
                .andExpect(jsonPath("$[0].parcelas", is("7")))
                .andExpect(jsonPath("$[0].conta", is("CAROL")))
                .andExpect(jsonPath("$[0].tipo", is("DEBITO")))
                .andExpect(jsonPath("$[0].valor", is("24,04")))
                .andExpect(jsonPath("$[0].codigoPai", isEmptyOrNullString()))
                .andExpect(jsonPath("$[0].proximoLancamento", isEmptyOrNullString()))

                .andExpect(jsonPath("$[1].codigo", is("1")))
                .andExpect(jsonPath("$[1].dataLancamento", is(getStringFromCurrentDate())))
                .andExpect(jsonPath("$[1].dataAtualizacao", is(getStringFromCurrentDate())))
                .andExpect(jsonPath("$[1].dataVencimento", is("27/05/2018")))
                .andExpect(jsonPath("$[1].dataPagamento", isEmptyOrNullString()))
                .andExpect(jsonPath("$[1].recorrente", is("N")))
                .andExpect(jsonPath("$[1].grupo", is("Saúde")))
                .andExpect(jsonPath("$[1].subGrupo", is("Suplementos")))
                .andExpect(jsonPath("$[1].local", is("Site")))
                .andExpect(jsonPath("$[1].descricao", is("Suplementos comprados pela Carol")))
                .andExpect(jsonPath("$[1].usouCartao", is("S")))
                .andExpect(jsonPath("$[1].cartao", is("0744")))
                .andExpect(jsonPath("$[1].parcelado", is("S")))
                .andExpect(jsonPath("$[1].parcela", is("2")))
                .andExpect(jsonPath("$[1].parcelas", is("7")))
                .andExpect(jsonPath("$[1].conta", is("CAROL")))
                .andExpect(jsonPath("$[1].tipo", is("DEBITO")))
                .andExpect(jsonPath("$[1].valor", is("24,04")))
                .andExpect(jsonPath("$[1].codigoPai", is("0")))
                .andExpect(jsonPath("$[1].proximoLancamento", isEmptyOrNullString()))

                .andExpect(jsonPath("$[2].codigo", is("2")))
                .andExpect(jsonPath("$[2].dataLancamento", is(getStringFromCurrentDate())))
                .andExpect(jsonPath("$[2].dataAtualizacao", is(getStringFromCurrentDate())))
                .andExpect(jsonPath("$[2].dataVencimento", is("27/06/2018")))
                .andExpect(jsonPath("$[2].dataPagamento", isEmptyOrNullString()))
                .andExpect(jsonPath("$[2].recorrente", is("N")))
                .andExpect(jsonPath("$[2].grupo", is("Saúde")))
                .andExpect(jsonPath("$[2].subGrupo", is("Suplementos")))
                .andExpect(jsonPath("$[2].local", is("Site")))
                .andExpect(jsonPath("$[2].descricao", is("Suplementos comprados pela Carol")))
                .andExpect(jsonPath("$[2].usouCartao", is("S")))
                .andExpect(jsonPath("$[2].cartao", is("0744")))
                .andExpect(jsonPath("$[2].parcelado", is("S")))
                .andExpect(jsonPath("$[2].parcela", is("3")))
                .andExpect(jsonPath("$[2].parcelas", is("7")))
                .andExpect(jsonPath("$[2].conta", is("CAROL")))
                .andExpect(jsonPath("$[2].tipo", is("DEBITO")))
                .andExpect(jsonPath("$[2].valor", is("24,04")))
                .andExpect(jsonPath("$[2].codigoPai", is("0")))
                .andExpect(jsonPath("$[2].proximoLancamento", isEmptyOrNullString()))

                .andExpect(jsonPath("$[3].codigo", is("3")))
                .andExpect(jsonPath("$[3].dataLancamento", is(getStringFromCurrentDate())))
                .andExpect(jsonPath("$[3].dataAtualizacao", is(getStringFromCurrentDate())))
                .andExpect(jsonPath("$[3].dataVencimento", is("27/07/2018")))
                .andExpect(jsonPath("$[3].dataPagamento", isEmptyOrNullString()))
                .andExpect(jsonPath("$[3].recorrente", is("N")))
                .andExpect(jsonPath("$[3].grupo", is("Saúde")))
                .andExpect(jsonPath("$[3].subGrupo", is("Suplementos")))
                .andExpect(jsonPath("$[3].local", is("Site")))
                .andExpect(jsonPath("$[3].descricao", is("Suplementos comprados pela Carol")))
                .andExpect(jsonPath("$[3].usouCartao", is("S")))
                .andExpect(jsonPath("$[3].cartao", is("0744")))
                .andExpect(jsonPath("$[3].parcelado", is("S")))
                .andExpect(jsonPath("$[3].parcela", is("4")))
                .andExpect(jsonPath("$[3].parcelas", is("7")))
                .andExpect(jsonPath("$[3].conta", is("CAROL")))
                .andExpect(jsonPath("$[3].tipo", is("DEBITO")))
                .andExpect(jsonPath("$[3].valor", is("24,04")))
                .andExpect(jsonPath("$[3].codigoPai", is("0")))
                .andExpect(jsonPath("$[3].proximoLancamento", isEmptyOrNullString()))

                .andExpect(jsonPath("$[4].codigo", is("4")))
                .andExpect(jsonPath("$[4].dataLancamento", is(getStringFromCurrentDate())))
                .andExpect(jsonPath("$[4].dataAtualizacao", is(getStringFromCurrentDate())))
                .andExpect(jsonPath("$[4].dataVencimento", is("27/08/2018")))
                .andExpect(jsonPath("$[4].dataPagamento", isEmptyOrNullString()))
                .andExpect(jsonPath("$[4].recorrente", is("N")))
                .andExpect(jsonPath("$[4].grupo", is("Saúde")))
                .andExpect(jsonPath("$[4].subGrupo", is("Suplementos")))
                .andExpect(jsonPath("$[4].local", is("Site")))
                .andExpect(jsonPath("$[4].descricao", is("Suplementos comprados pela Carol")))
                .andExpect(jsonPath("$[4].usouCartao", is("S")))
                .andExpect(jsonPath("$[4].cartao", is("0744")))
                .andExpect(jsonPath("$[4].parcelado", is("S")))
                .andExpect(jsonPath("$[4].parcela", is("5")))
                .andExpect(jsonPath("$[4].parcelas", is("7")))
                .andExpect(jsonPath("$[4].conta", is("CAROL")))
                .andExpect(jsonPath("$[4].tipo", is("DEBITO")))
                .andExpect(jsonPath("$[4].valor", is("24,04")))
                .andExpect(jsonPath("$[4].codigoPai", is("0")))
                .andExpect(jsonPath("$[4].proximoLancamento", isEmptyOrNullString()))

                .andExpect(jsonPath("$[5].codigo", is("5")))
                .andExpect(jsonPath("$[5].dataLancamento", is(getStringFromCurrentDate())))
                .andExpect(jsonPath("$[5].dataAtualizacao", is(getStringFromCurrentDate())))
                .andExpect(jsonPath("$[5].dataVencimento", is("27/09/2018")))
                .andExpect(jsonPath("$[5].dataPagamento", isEmptyOrNullString()))
                .andExpect(jsonPath("$[5].recorrente", is("N")))
                .andExpect(jsonPath("$[5].grupo", is("Saúde")))
                .andExpect(jsonPath("$[5].subGrupo", is("Suplementos")))
                .andExpect(jsonPath("$[5].local", is("Site")))
                .andExpect(jsonPath("$[5].descricao", is("Suplementos comprados pela Carol")))
                .andExpect(jsonPath("$[5].usouCartao", is("S")))
                .andExpect(jsonPath("$[5].cartao", is("0744")))
                .andExpect(jsonPath("$[5].parcelado", is("S")))
                .andExpect(jsonPath("$[5].parcela", is("6")))
                .andExpect(jsonPath("$[5].parcelas", is("7")))
                .andExpect(jsonPath("$[5].conta", is("CAROL")))
                .andExpect(jsonPath("$[5].tipo", is("DEBITO")))
                .andExpect(jsonPath("$[5].valor", is("24,04")))
                .andExpect(jsonPath("$[5].codigoPai", is("0")))
                .andExpect(jsonPath("$[5].proximoLancamento", isEmptyOrNullString()))

                .andExpect(jsonPath("$[6].codigo", is("6")))
                .andExpect(jsonPath("$[6].dataLancamento", is(getStringFromCurrentDate())))
                .andExpect(jsonPath("$[6].dataAtualizacao", is(getStringFromCurrentDate())))
                .andExpect(jsonPath("$[6].dataVencimento", is("27/10/2018")))
                .andExpect(jsonPath("$[6].dataPagamento", isEmptyOrNullString()))
                .andExpect(jsonPath("$[6].recorrente", is("N")))
                .andExpect(jsonPath("$[6].grupo", is("Saúde")))
                .andExpect(jsonPath("$[6].subGrupo", is("Suplementos")))
                .andExpect(jsonPath("$[6].local", is("Site")))
                .andExpect(jsonPath("$[6].descricao", is("Suplementos comprados pela Carol")))
                .andExpect(jsonPath("$[6].usouCartao", is("S")))
                .andExpect(jsonPath("$[6].cartao", is("0744")))
                .andExpect(jsonPath("$[6].parcelado", is("S")))
                .andExpect(jsonPath("$[6].parcela", is("7")))
                .andExpect(jsonPath("$[6].parcelas", is("7")))
                .andExpect(jsonPath("$[6].conta", is("CAROL")))
                .andExpect(jsonPath("$[6].tipo", is("DEBITO")))
                .andExpect(jsonPath("$[6].valor", is("24,04")))
                .andExpect(jsonPath("$[6].codigoPai", is("0")))
                .andExpect(jsonPath("$[6].proximoLancamento", isEmptyOrNullString()));
    }

    @Test
    public void buscarTudoRecorrente() throws Exception {
        criarContabilidadeRecorrenteComAssert();

        mvc.perform(get("/contabilidade")
                .characterEncoding("UTF-8")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(9)))

                .andExpect(jsonPath("$[0].codigo", is("0")))
                .andExpect(jsonPath("$[0].dataLancamento", is(getStringFromCurrentDate())))
                .andExpect(jsonPath("$[0].dataAtualizacao", is(getStringFromCurrentDate())))
                .andExpect(jsonPath("$[0].dataVencimento", is("27/04/2018")))
                .andExpect(jsonPath("$[0].dataPagamento", isEmptyOrNullString()))
                .andExpect(jsonPath("$[0].recorrente", is("S")))
                .andExpect(jsonPath("$[0].grupo", is("Saúde")))
                .andExpect(jsonPath("$[0].subGrupo", is("Suplementos")))
                .andExpect(jsonPath("$[0].local", is("Site")))
                .andExpect(jsonPath("$[0].descricao", is("Suplementos comprados pela Carol")))
                .andExpect(jsonPath("$[0].usouCartao", is("S")))
                .andExpect(jsonPath("$[0].cartao", is("0744")))
                .andExpect(jsonPath("$[0].parcelado", is("N")))
                .andExpect(jsonPath("$[0].parcela", isEmptyOrNullString()))
                .andExpect(jsonPath("$[0].parcelas", isEmptyOrNullString()))
                .andExpect(jsonPath("$[0].conta", is("CAROL")))
                .andExpect(jsonPath("$[0].tipo", is("DEBITO")))
                .andExpect(jsonPath("$[0].valor", is("24,04")))
                .andExpect(jsonPath("$[0].codigoPai", isEmptyOrNullString()))
                .andExpect(jsonPath("$[0].proximoLancamento", is("1")))

                .andExpect(jsonPath("$[1].codigo", is("1")))
                .andExpect(jsonPath("$[1].dataLancamento", is(getStringFromCurrentDate())))
                .andExpect(jsonPath("$[1].dataAtualizacao", is(getStringFromCurrentDate())))
                .andExpect(jsonPath("$[1].dataVencimento", is("27/05/2018")))
                .andExpect(jsonPath("$[1].dataPagamento", isEmptyOrNullString()))
                .andExpect(jsonPath("$[1].recorrente", is("S")))
                .andExpect(jsonPath("$[1].grupo", is("Saúde")))
                .andExpect(jsonPath("$[1].subGrupo", is("Suplementos")))
                .andExpect(jsonPath("$[1].local", is("Site")))
                .andExpect(jsonPath("$[1].descricao", is("Suplementos comprados pela Carol")))
                .andExpect(jsonPath("$[1].usouCartao", is("S")))
                .andExpect(jsonPath("$[1].cartao", is("0744")))
                .andExpect(jsonPath("$[1].parcelado", is("N")))
                .andExpect(jsonPath("$[1].parcela", isEmptyOrNullString()))
                .andExpect(jsonPath("$[1].parcelas", isEmptyOrNullString()))
                .andExpect(jsonPath("$[1].conta", is("CAROL")))
                .andExpect(jsonPath("$[1].tipo", is("DEBITO")))
                .andExpect(jsonPath("$[1].valor", is("24,04")))
                .andExpect(jsonPath("$[1].codigoPai", isEmptyOrNullString()))
                .andExpect(jsonPath("$[1].proximoLancamento", is("2")))

                .andExpect(jsonPath("$[2].codigo", is("2")))
                .andExpect(jsonPath("$[2].dataLancamento", is(getStringFromCurrentDate())))
                .andExpect(jsonPath("$[2].dataAtualizacao", is(getStringFromCurrentDate())))
                .andExpect(jsonPath("$[2].dataVencimento", is("27/06/2018")))
                .andExpect(jsonPath("$[2].dataPagamento", isEmptyOrNullString()))
                .andExpect(jsonPath("$[2].recorrente", is("S")))
                .andExpect(jsonPath("$[2].grupo", is("Saúde")))
                .andExpect(jsonPath("$[2].subGrupo", is("Suplementos")))
                .andExpect(jsonPath("$[2].local", is("Site")))
                .andExpect(jsonPath("$[2].descricao", is("Suplementos comprados pela Carol")))
                .andExpect(jsonPath("$[2].usouCartao", is("S")))
                .andExpect(jsonPath("$[2].cartao", is("0744")))
                .andExpect(jsonPath("$[2].parcelado", is("N")))
                .andExpect(jsonPath("$[2].parcela", isEmptyOrNullString()))
                .andExpect(jsonPath("$[2].parcelas", isEmptyOrNullString()))
                .andExpect(jsonPath("$[2].conta", is("CAROL")))
                .andExpect(jsonPath("$[2].tipo", is("DEBITO")))
                .andExpect(jsonPath("$[2].valor", is("24,04")))
                .andExpect(jsonPath("$[2].codigoPai", isEmptyOrNullString()))
                .andExpect(jsonPath("$[2].proximoLancamento", is("3")))

                .andExpect(jsonPath("$[3].codigo", is("3")))
                .andExpect(jsonPath("$[3].dataLancamento", is(getStringFromCurrentDate())))
                .andExpect(jsonPath("$[3].dataAtualizacao", is(getStringFromCurrentDate())))
                .andExpect(jsonPath("$[3].dataVencimento", is("27/07/2018")))
                .andExpect(jsonPath("$[3].dataPagamento", isEmptyOrNullString()))
                .andExpect(jsonPath("$[3].recorrente", is("S")))
                .andExpect(jsonPath("$[3].grupo", is("Saúde")))
                .andExpect(jsonPath("$[3].subGrupo", is("Suplementos")))
                .andExpect(jsonPath("$[3].local", is("Site")))
                .andExpect(jsonPath("$[3].descricao", is("Suplementos comprados pela Carol")))
                .andExpect(jsonPath("$[3].usouCartao", is("S")))
                .andExpect(jsonPath("$[3].cartao", is("0744")))
                .andExpect(jsonPath("$[3].parcelado", is("N")))
                .andExpect(jsonPath("$[3].parcela", isEmptyOrNullString()))
                .andExpect(jsonPath("$[3].parcelas", isEmptyOrNullString()))
                .andExpect(jsonPath("$[3].conta", is("CAROL")))
                .andExpect(jsonPath("$[3].tipo", is("DEBITO")))
                .andExpect(jsonPath("$[3].valor", is("24,04")))
                .andExpect(jsonPath("$[3].codigoPai", isEmptyOrNullString()))
                .andExpect(jsonPath("$[3].proximoLancamento", is("4")))

                .andExpect(jsonPath("$[4].codigo", is("4")))
                .andExpect(jsonPath("$[4].dataLancamento", is(getStringFromCurrentDate())))
                .andExpect(jsonPath("$[4].dataAtualizacao", is(getStringFromCurrentDate())))
                .andExpect(jsonPath("$[4].dataVencimento", is("27/08/2018")))
                .andExpect(jsonPath("$[4].dataPagamento", isEmptyOrNullString()))
                .andExpect(jsonPath("$[4].recorrente", is("S")))
                .andExpect(jsonPath("$[4].grupo", is("Saúde")))
                .andExpect(jsonPath("$[4].subGrupo", is("Suplementos")))
                .andExpect(jsonPath("$[4].local", is("Site")))
                .andExpect(jsonPath("$[4].descricao", is("Suplementos comprados pela Carol")))
                .andExpect(jsonPath("$[4].usouCartao", is("S")))
                .andExpect(jsonPath("$[4].cartao", is("0744")))
                .andExpect(jsonPath("$[4].parcelado", is("N")))
                .andExpect(jsonPath("$[4].parcela", isEmptyOrNullString()))
                .andExpect(jsonPath("$[4].parcelas", isEmptyOrNullString()))
                .andExpect(jsonPath("$[4].conta", is("CAROL")))
                .andExpect(jsonPath("$[4].tipo", is("DEBITO")))
                .andExpect(jsonPath("$[4].valor", is("24,04")))
                .andExpect(jsonPath("$[4].codigoPai", isEmptyOrNullString()))
                .andExpect(jsonPath("$[4].proximoLancamento", is("5")))

                .andExpect(jsonPath("$[5].codigo", is("5")))
                .andExpect(jsonPath("$[5].dataLancamento", is(getStringFromCurrentDate())))
                .andExpect(jsonPath("$[5].dataAtualizacao", is(getStringFromCurrentDate())))
                .andExpect(jsonPath("$[5].dataVencimento", is("27/09/2018")))
                .andExpect(jsonPath("$[5].dataPagamento", isEmptyOrNullString()))
                .andExpect(jsonPath("$[5].recorrente", is("S")))
                .andExpect(jsonPath("$[5].grupo", is("Saúde")))
                .andExpect(jsonPath("$[5].subGrupo", is("Suplementos")))
                .andExpect(jsonPath("$[5].local", is("Site")))
                .andExpect(jsonPath("$[5].descricao", is("Suplementos comprados pela Carol")))
                .andExpect(jsonPath("$[5].usouCartao", is("S")))
                .andExpect(jsonPath("$[5].cartao", is("0744")))
                .andExpect(jsonPath("$[5].parcelado", is("N")))
                .andExpect(jsonPath("$[5].parcela", isEmptyOrNullString()))
                .andExpect(jsonPath("$[5].parcelas", isEmptyOrNullString()))
                .andExpect(jsonPath("$[5].conta", is("CAROL")))
                .andExpect(jsonPath("$[5].tipo", is("DEBITO")))
                .andExpect(jsonPath("$[5].valor", is("24,04")))
                .andExpect(jsonPath("$[5].codigoPai", isEmptyOrNullString()))
                .andExpect(jsonPath("$[5].proximoLancamento", is("6")))

                .andExpect(jsonPath("$[6].codigo", is("6")))
                .andExpect(jsonPath("$[6].dataLancamento", is(getStringFromCurrentDate())))
                .andExpect(jsonPath("$[6].dataAtualizacao", is(getStringFromCurrentDate())))
                .andExpect(jsonPath("$[6].dataVencimento", is("27/10/2018")))
                .andExpect(jsonPath("$[6].dataPagamento", isEmptyOrNullString()))
                .andExpect(jsonPath("$[6].recorrente", is("S")))
                .andExpect(jsonPath("$[6].grupo", is("Saúde")))
                .andExpect(jsonPath("$[6].subGrupo", is("Suplementos")))
                .andExpect(jsonPath("$[6].local", is("Site")))
                .andExpect(jsonPath("$[6].descricao", is("Suplementos comprados pela Carol")))
                .andExpect(jsonPath("$[6].usouCartao", is("S")))
                .andExpect(jsonPath("$[6].cartao", is("0744")))
                .andExpect(jsonPath("$[6].parcelado", is("N")))
                .andExpect(jsonPath("$[6].parcela", isEmptyOrNullString()))
                .andExpect(jsonPath("$[6].parcelas", isEmptyOrNullString()))
                .andExpect(jsonPath("$[6].conta", is("CAROL")))
                .andExpect(jsonPath("$[6].tipo", is("DEBITO")))
                .andExpect(jsonPath("$[6].valor", is("24,04")))
                .andExpect(jsonPath("$[6].codigoPai", isEmptyOrNullString()))
                .andExpect(jsonPath("$[6].proximoLancamento", is("7")))

                .andExpect(jsonPath("$[7].codigo", is("7")))
                .andExpect(jsonPath("$[7].dataLancamento", is(getStringFromCurrentDate())))
                .andExpect(jsonPath("$[7].dataAtualizacao", is(getStringFromCurrentDate())))
                .andExpect(jsonPath("$[7].dataVencimento", is("27/11/2018")))
                .andExpect(jsonPath("$[7].dataPagamento", isEmptyOrNullString()))
                .andExpect(jsonPath("$[7].recorrente", is("S")))
                .andExpect(jsonPath("$[7].grupo", is("Saúde")))
                .andExpect(jsonPath("$[7].subGrupo", is("Suplementos")))
                .andExpect(jsonPath("$[7].local", is("Site")))
                .andExpect(jsonPath("$[7].descricao", is("Suplementos comprados pela Carol")))
                .andExpect(jsonPath("$[7].usouCartao", is("S")))
                .andExpect(jsonPath("$[7].cartao", is("0744")))
                .andExpect(jsonPath("$[7].parcelado", is("N")))
                .andExpect(jsonPath("$[7].parcela", isEmptyOrNullString()))
                .andExpect(jsonPath("$[7].parcelas", isEmptyOrNullString()))
                .andExpect(jsonPath("$[7].conta", is("CAROL")))
                .andExpect(jsonPath("$[7].tipo", is("DEBITO")))
                .andExpect(jsonPath("$[7].valor", is("24,04")))
                .andExpect(jsonPath("$[7].codigoPai", isEmptyOrNullString()))
                .andExpect(jsonPath("$[7].proximoLancamento", is("8")))

                .andExpect(jsonPath("$[8].codigo", is("8")))
                .andExpect(jsonPath("$[8].dataLancamento", is(getStringFromCurrentDate())))
                .andExpect(jsonPath("$[8].dataAtualizacao", is(getStringFromCurrentDate())))
                .andExpect(jsonPath("$[8].dataVencimento", is("27/12/2018")))
                .andExpect(jsonPath("$[8].dataPagamento", isEmptyOrNullString()))
                .andExpect(jsonPath("$[8].recorrente", is("S")))
                .andExpect(jsonPath("$[8].grupo", is("Saúde")))
                .andExpect(jsonPath("$[8].subGrupo", is("Suplementos")))
                .andExpect(jsonPath("$[8].local", is("Site")))
                .andExpect(jsonPath("$[8].descricao", is("Suplementos comprados pela Carol")))
                .andExpect(jsonPath("$[8].usouCartao", is("S")))
                .andExpect(jsonPath("$[8].cartao", is("0744")))
                .andExpect(jsonPath("$[8].parcelado", is("N")))
                .andExpect(jsonPath("$[8].parcela", isEmptyOrNullString()))
                .andExpect(jsonPath("$[8].parcelas", isEmptyOrNullString()))
                .andExpect(jsonPath("$[8].conta", is("CAROL")))
                .andExpect(jsonPath("$[8].tipo", is("DEBITO")))
                .andExpect(jsonPath("$[8].valor", is("24,04")))
                .andExpect(jsonPath("$[8].codigoPai", isEmptyOrNullString()))
                .andExpect(jsonPath("$[8].proximoLancamento", isEmptyOrNullString()));
    }

    @Test
    public void buscarParcelasRelacionadasSemDiretorio() throws Exception {
        deletarDiretorioEArquivos();

        Long codigoPai = 0L;

        mvc.perform(get("/contabilidade/parcelado/{codigoPai}", codigoPai)
                .characterEncoding("UTF-8")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInsufficientStorage())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(507)))
                .andExpect(jsonPath("$.mensagens", hasSize(1)))
                .andExpect(jsonPath("$.mensagens[0]", is("Erro de persistência ao buscar parcelas relacionadas.")));
    }

    @Test
    public void buscarParcelasRelacionadasSemCodigo() throws Exception {
        Long codigoPai = null;

        mvc.perform(get("/contabilidade/parcelado/{codigoPai}", codigoPai)
                .characterEncoding("UTF-8")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(""));
    }

    @Test
    public void buscarParcelasRelacionadasComCodigoIncorreto() throws Exception {
        String codigoPai = "a";

        mvc.perform(get("/contabilidade/parcelado/{codigoPai}", codigoPai)
                .characterEncoding("UTF-8")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(""));
    }

    @Test
    public void buscarParcelasRelacionadasSemRegistros() throws Exception {
        Long codigoPai = 0L;

        mvc.perform(get("/contabilidade/parcelado/{codigoPai}", codigoPai)
                .characterEncoding("UTF-8")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andExpect(content().string(""));
    }

    @Test
    public void buscarParcelasRelacionadas() throws Exception {
        criarContabilidadeParcelada();
        criarContabilidadeSemCartao();
        criarContabilidadeRecorrente();

        Long codigoPai = 0L;

        mvc.perform(get("/contabilidade/parcelado/{codigoPai}", codigoPai)
                .characterEncoding("UTF-8")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(7)))

                .andExpect(jsonPath("$[0].codigo", is("0")))
                .andExpect(jsonPath("$[0].dataLancamento", is(getStringFromCurrentDate())))
                .andExpect(jsonPath("$[0].dataAtualizacao", is(getStringFromCurrentDate())))
                .andExpect(jsonPath("$[0].dataVencimento", is("27/04/2018")))
                .andExpect(jsonPath("$[0].dataPagamento", isEmptyOrNullString()))
                .andExpect(jsonPath("$[0].recorrente", is("N")))
                .andExpect(jsonPath("$[0].grupo", is("Saúde")))
                .andExpect(jsonPath("$[0].subGrupo", is("Suplementos")))
                .andExpect(jsonPath("$[0].local", is("Site")))
                .andExpect(jsonPath("$[0].descricao", is("Suplementos comprados pela Carol")))
                .andExpect(jsonPath("$[0].usouCartao", is("S")))
                .andExpect(jsonPath("$[0].cartao", is("0744")))
                .andExpect(jsonPath("$[0].parcelado", is("S")))
                .andExpect(jsonPath("$[0].parcela", is("1")))
                .andExpect(jsonPath("$[0].parcelas", is("7")))
                .andExpect(jsonPath("$[0].conta", is("CAROL")))
                .andExpect(jsonPath("$[0].tipo", is("DEBITO")))
                .andExpect(jsonPath("$[0].valor", is("24,04")))
                .andExpect(jsonPath("$[0].codigoPai", isEmptyOrNullString()))
                .andExpect(jsonPath("$[0].proximoLancamento", isEmptyOrNullString()))

                .andExpect(jsonPath("$[1].codigo", is("1")))
                .andExpect(jsonPath("$[1].dataLancamento", is(getStringFromCurrentDate())))
                .andExpect(jsonPath("$[1].dataAtualizacao", is(getStringFromCurrentDate())))
                .andExpect(jsonPath("$[1].dataVencimento", is("27/05/2018")))
                .andExpect(jsonPath("$[1].dataPagamento", isEmptyOrNullString()))
                .andExpect(jsonPath("$[1].recorrente", is("N")))
                .andExpect(jsonPath("$[1].grupo", is("Saúde")))
                .andExpect(jsonPath("$[1].subGrupo", is("Suplementos")))
                .andExpect(jsonPath("$[1].local", is("Site")))
                .andExpect(jsonPath("$[1].descricao", is("Suplementos comprados pela Carol")))
                .andExpect(jsonPath("$[1].usouCartao", is("S")))
                .andExpect(jsonPath("$[1].cartao", is("0744")))
                .andExpect(jsonPath("$[1].parcelado", is("S")))
                .andExpect(jsonPath("$[1].parcela", is("2")))
                .andExpect(jsonPath("$[1].parcelas", is("7")))
                .andExpect(jsonPath("$[1].conta", is("CAROL")))
                .andExpect(jsonPath("$[1].tipo", is("DEBITO")))
                .andExpect(jsonPath("$[1].valor", is("24,04")))
                .andExpect(jsonPath("$[1].codigoPai", is("0")))
                .andExpect(jsonPath("$[1].proximoLancamento", isEmptyOrNullString()))

                .andExpect(jsonPath("$[2].codigo", is("2")))
                .andExpect(jsonPath("$[2].dataLancamento", is(getStringFromCurrentDate())))
                .andExpect(jsonPath("$[2].dataAtualizacao", is(getStringFromCurrentDate())))
                .andExpect(jsonPath("$[2].dataVencimento", is("27/06/2018")))
                .andExpect(jsonPath("$[2].dataPagamento", isEmptyOrNullString()))
                .andExpect(jsonPath("$[2].recorrente", is("N")))
                .andExpect(jsonPath("$[2].grupo", is("Saúde")))
                .andExpect(jsonPath("$[2].subGrupo", is("Suplementos")))
                .andExpect(jsonPath("$[2].local", is("Site")))
                .andExpect(jsonPath("$[2].descricao", is("Suplementos comprados pela Carol")))
                .andExpect(jsonPath("$[2].usouCartao", is("S")))
                .andExpect(jsonPath("$[2].cartao", is("0744")))
                .andExpect(jsonPath("$[2].parcelado", is("S")))
                .andExpect(jsonPath("$[2].parcela", is("3")))
                .andExpect(jsonPath("$[2].parcelas", is("7")))
                .andExpect(jsonPath("$[2].conta", is("CAROL")))
                .andExpect(jsonPath("$[2].tipo", is("DEBITO")))
                .andExpect(jsonPath("$[2].valor", is("24,04")))
                .andExpect(jsonPath("$[2].codigoPai", is("0")))
                .andExpect(jsonPath("$[2].proximoLancamento", isEmptyOrNullString()))

                .andExpect(jsonPath("$[3].codigo", is("3")))
                .andExpect(jsonPath("$[3].dataLancamento", is(getStringFromCurrentDate())))
                .andExpect(jsonPath("$[3].dataAtualizacao", is(getStringFromCurrentDate())))
                .andExpect(jsonPath("$[3].dataVencimento", is("27/07/2018")))
                .andExpect(jsonPath("$[3].dataPagamento", isEmptyOrNullString()))
                .andExpect(jsonPath("$[3].recorrente", is("N")))
                .andExpect(jsonPath("$[3].grupo", is("Saúde")))
                .andExpect(jsonPath("$[3].subGrupo", is("Suplementos")))
                .andExpect(jsonPath("$[3].local", is("Site")))
                .andExpect(jsonPath("$[3].descricao", is("Suplementos comprados pela Carol")))
                .andExpect(jsonPath("$[3].usouCartao", is("S")))
                .andExpect(jsonPath("$[3].cartao", is("0744")))
                .andExpect(jsonPath("$[3].parcelado", is("S")))
                .andExpect(jsonPath("$[3].parcela", is("4")))
                .andExpect(jsonPath("$[3].parcelas", is("7")))
                .andExpect(jsonPath("$[3].conta", is("CAROL")))
                .andExpect(jsonPath("$[3].tipo", is("DEBITO")))
                .andExpect(jsonPath("$[3].valor", is("24,04")))
                .andExpect(jsonPath("$[3].codigoPai", is("0")))
                .andExpect(jsonPath("$[3].proximoLancamento", isEmptyOrNullString()))

                .andExpect(jsonPath("$[4].codigo", is("4")))
                .andExpect(jsonPath("$[4].dataLancamento", is(getStringFromCurrentDate())))
                .andExpect(jsonPath("$[4].dataAtualizacao", is(getStringFromCurrentDate())))
                .andExpect(jsonPath("$[4].dataVencimento", is("27/08/2018")))
                .andExpect(jsonPath("$[4].dataPagamento", isEmptyOrNullString()))
                .andExpect(jsonPath("$[4].recorrente", is("N")))
                .andExpect(jsonPath("$[4].grupo", is("Saúde")))
                .andExpect(jsonPath("$[4].subGrupo", is("Suplementos")))
                .andExpect(jsonPath("$[4].local", is("Site")))
                .andExpect(jsonPath("$[4].descricao", is("Suplementos comprados pela Carol")))
                .andExpect(jsonPath("$[4].usouCartao", is("S")))
                .andExpect(jsonPath("$[4].cartao", is("0744")))
                .andExpect(jsonPath("$[4].parcelado", is("S")))
                .andExpect(jsonPath("$[4].parcela", is("5")))
                .andExpect(jsonPath("$[4].parcelas", is("7")))
                .andExpect(jsonPath("$[4].conta", is("CAROL")))
                .andExpect(jsonPath("$[4].tipo", is("DEBITO")))
                .andExpect(jsonPath("$[4].valor", is("24,04")))
                .andExpect(jsonPath("$[4].codigoPai", is("0")))
                .andExpect(jsonPath("$[4].proximoLancamento", isEmptyOrNullString()))

                .andExpect(jsonPath("$[5].codigo", is("5")))
                .andExpect(jsonPath("$[5].dataLancamento", is(getStringFromCurrentDate())))
                .andExpect(jsonPath("$[5].dataAtualizacao", is(getStringFromCurrentDate())))
                .andExpect(jsonPath("$[5].dataVencimento", is("27/09/2018")))
                .andExpect(jsonPath("$[5].dataPagamento", isEmptyOrNullString()))
                .andExpect(jsonPath("$[5].recorrente", is("N")))
                .andExpect(jsonPath("$[5].grupo", is("Saúde")))
                .andExpect(jsonPath("$[5].subGrupo", is("Suplementos")))
                .andExpect(jsonPath("$[5].local", is("Site")))
                .andExpect(jsonPath("$[5].descricao", is("Suplementos comprados pela Carol")))
                .andExpect(jsonPath("$[5].usouCartao", is("S")))
                .andExpect(jsonPath("$[5].cartao", is("0744")))
                .andExpect(jsonPath("$[5].parcelado", is("S")))
                .andExpect(jsonPath("$[5].parcela", is("6")))
                .andExpect(jsonPath("$[5].parcelas", is("7")))
                .andExpect(jsonPath("$[5].conta", is("CAROL")))
                .andExpect(jsonPath("$[5].tipo", is("DEBITO")))
                .andExpect(jsonPath("$[5].valor", is("24,04")))
                .andExpect(jsonPath("$[5].codigoPai", is("0")))
                .andExpect(jsonPath("$[5].proximoLancamento", isEmptyOrNullString()))

                .andExpect(jsonPath("$[6].codigo", is("6")))
                .andExpect(jsonPath("$[6].dataLancamento", is(getStringFromCurrentDate())))
                .andExpect(jsonPath("$[6].dataAtualizacao", is(getStringFromCurrentDate())))
                .andExpect(jsonPath("$[6].dataVencimento", is("27/10/2018")))
                .andExpect(jsonPath("$[6].dataPagamento", isEmptyOrNullString()))
                .andExpect(jsonPath("$[6].recorrente", is("N")))
                .andExpect(jsonPath("$[6].grupo", is("Saúde")))
                .andExpect(jsonPath("$[6].subGrupo", is("Suplementos")))
                .andExpect(jsonPath("$[6].local", is("Site")))
                .andExpect(jsonPath("$[6].descricao", is("Suplementos comprados pela Carol")))
                .andExpect(jsonPath("$[6].usouCartao", is("S")))
                .andExpect(jsonPath("$[6].cartao", is("0744")))
                .andExpect(jsonPath("$[6].parcelado", is("S")))
                .andExpect(jsonPath("$[6].parcela", is("7")))
                .andExpect(jsonPath("$[6].parcelas", is("7")))
                .andExpect(jsonPath("$[6].conta", is("CAROL")))
                .andExpect(jsonPath("$[6].tipo", is("DEBITO")))
                .andExpect(jsonPath("$[6].valor", is("24,04")))
                .andExpect(jsonPath("$[6].codigoPai", is("0")))
                .andExpect(jsonPath("$[6].proximoLancamento", isEmptyOrNullString()));
    }

    @Test
    public void buscarRecorrentesRelacionadasSemDiretorio() throws Exception {
        deletarDiretorioEArquivos();

        Long codigoPai = 0L;

        mvc.perform(get("/contabilidade/recorrente/{codigoPai}", codigoPai)
                .characterEncoding("UTF-8")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInsufficientStorage())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(507)))
                .andExpect(jsonPath("$.mensagens", hasSize(1)))
                .andExpect(jsonPath("$.mensagens[0]", is("Erro de persistência ao buscar recorrentes relacionadas.")));
    }

    @Test
    public void buscarRecorrentesRelacionadasSemCodigo() throws Exception {
        Long codigoPai = null;

        mvc.perform(get("/contabilidade/recorrente/{codigoPai}", codigoPai)
                .characterEncoding("UTF-8")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(""));
    }

    @Test
    public void buscarRecorrentesRelacionadasComCodigoIncorreto() throws Exception {
        String codigoPai = "a";

        mvc.perform(get("/contabilidade/recorrente/{codigoPai}", codigoPai)
                .characterEncoding("UTF-8")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(""));
    }

    @Test
    public void buscarRecorrentesRelacionadasSemRegistros() throws Exception {
        Long codigoPai = 0L;

        mvc.perform(get("/contabilidade/recorrente/{codigoPai}", codigoPai)
                .characterEncoding("UTF-8")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andExpect(content().string(""));
    }

    @Test
    public void buscarRecorrentesRelacionadas() throws Exception {
        criarContabilidadeRecorrente();
        criarContabilidadeParcelada();
        criarContabilidadeSemCartao();

        Long codigo = 0L;

        mvc.perform(get("/contabilidade/recorrente/{codigo}", codigo)
                .characterEncoding("UTF-8")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(9)))

                .andExpect(jsonPath("$[0].codigo", is("0")))
                .andExpect(jsonPath("$[0].dataLancamento", is(getStringFromCurrentDate())))
                .andExpect(jsonPath("$[0].dataAtualizacao", is(getStringFromCurrentDate())))
                .andExpect(jsonPath("$[0].dataVencimento", is("27/04/2018")))
                .andExpect(jsonPath("$[0].dataPagamento", isEmptyOrNullString()))
                .andExpect(jsonPath("$[0].recorrente", is("S")))
                .andExpect(jsonPath("$[0].grupo", is("Saúde")))
                .andExpect(jsonPath("$[0].subGrupo", is("Suplementos")))
                .andExpect(jsonPath("$[0].local", is("Site")))
                .andExpect(jsonPath("$[0].descricao", is("Suplementos comprados pela Carol")))
                .andExpect(jsonPath("$[0].usouCartao", is("S")))
                .andExpect(jsonPath("$[0].cartao", is("0744")))
                .andExpect(jsonPath("$[0].parcelado", is("N")))
                .andExpect(jsonPath("$[0].parcela", isEmptyOrNullString()))
                .andExpect(jsonPath("$[0].parcelas", isEmptyOrNullString()))
                .andExpect(jsonPath("$[0].conta", is("CAROL")))
                .andExpect(jsonPath("$[0].tipo", is("DEBITO")))
                .andExpect(jsonPath("$[0].valor", is("24,04")))
                .andExpect(jsonPath("$[0].codigoPai", isEmptyOrNullString()))
                .andExpect(jsonPath("$[0].proximoLancamento", is("1")))

                .andExpect(jsonPath("$[1].codigo", is("1")))
                .andExpect(jsonPath("$[1].dataLancamento", is(getStringFromCurrentDate())))
                .andExpect(jsonPath("$[1].dataAtualizacao", is(getStringFromCurrentDate())))
                .andExpect(jsonPath("$[1].dataVencimento", is("27/05/2018")))
                .andExpect(jsonPath("$[1].dataPagamento", isEmptyOrNullString()))
                .andExpect(jsonPath("$[1].recorrente", is("S")))
                .andExpect(jsonPath("$[1].grupo", is("Saúde")))
                .andExpect(jsonPath("$[1].subGrupo", is("Suplementos")))
                .andExpect(jsonPath("$[1].local", is("Site")))
                .andExpect(jsonPath("$[1].descricao", is("Suplementos comprados pela Carol")))
                .andExpect(jsonPath("$[1].usouCartao", is("S")))
                .andExpect(jsonPath("$[1].cartao", is("0744")))
                .andExpect(jsonPath("$[1].parcelado", is("N")))
                .andExpect(jsonPath("$[1].parcela", isEmptyOrNullString()))
                .andExpect(jsonPath("$[1].parcelas", isEmptyOrNullString()))
                .andExpect(jsonPath("$[1].conta", is("CAROL")))
                .andExpect(jsonPath("$[1].tipo", is("DEBITO")))
                .andExpect(jsonPath("$[1].valor", is("24,04")))
                .andExpect(jsonPath("$[1].codigoPai", isEmptyOrNullString()))
                .andExpect(jsonPath("$[1].proximoLancamento", is("2")))

                .andExpect(jsonPath("$[2].codigo", is("2")))
                .andExpect(jsonPath("$[2].dataLancamento", is(getStringFromCurrentDate())))
                .andExpect(jsonPath("$[2].dataAtualizacao", is(getStringFromCurrentDate())))
                .andExpect(jsonPath("$[2].dataVencimento", is("27/06/2018")))
                .andExpect(jsonPath("$[2].dataPagamento", isEmptyOrNullString()))
                .andExpect(jsonPath("$[2].recorrente", is("S")))
                .andExpect(jsonPath("$[2].grupo", is("Saúde")))
                .andExpect(jsonPath("$[2].subGrupo", is("Suplementos")))
                .andExpect(jsonPath("$[2].local", is("Site")))
                .andExpect(jsonPath("$[2].descricao", is("Suplementos comprados pela Carol")))
                .andExpect(jsonPath("$[2].usouCartao", is("S")))
                .andExpect(jsonPath("$[2].cartao", is("0744")))
                .andExpect(jsonPath("$[2].parcelado", is("N")))
                .andExpect(jsonPath("$[2].parcela", isEmptyOrNullString()))
                .andExpect(jsonPath("$[2].parcelas", isEmptyOrNullString()))
                .andExpect(jsonPath("$[2].conta", is("CAROL")))
                .andExpect(jsonPath("$[2].tipo", is("DEBITO")))
                .andExpect(jsonPath("$[2].valor", is("24,04")))
                .andExpect(jsonPath("$[2].codigoPai", isEmptyOrNullString()))
                .andExpect(jsonPath("$[2].proximoLancamento", is("3")))

                .andExpect(jsonPath("$[3].codigo", is("3")))
                .andExpect(jsonPath("$[3].dataLancamento", is(getStringFromCurrentDate())))
                .andExpect(jsonPath("$[3].dataAtualizacao", is(getStringFromCurrentDate())))
                .andExpect(jsonPath("$[3].dataVencimento", is("27/07/2018")))
                .andExpect(jsonPath("$[3].dataPagamento", isEmptyOrNullString()))
                .andExpect(jsonPath("$[3].recorrente", is("S")))
                .andExpect(jsonPath("$[3].grupo", is("Saúde")))
                .andExpect(jsonPath("$[3].subGrupo", is("Suplementos")))
                .andExpect(jsonPath("$[3].local", is("Site")))
                .andExpect(jsonPath("$[3].descricao", is("Suplementos comprados pela Carol")))
                .andExpect(jsonPath("$[3].usouCartao", is("S")))
                .andExpect(jsonPath("$[3].cartao", is("0744")))
                .andExpect(jsonPath("$[3].parcelado", is("N")))
                .andExpect(jsonPath("$[3].parcela", isEmptyOrNullString()))
                .andExpect(jsonPath("$[3].parcelas", isEmptyOrNullString()))
                .andExpect(jsonPath("$[3].conta", is("CAROL")))
                .andExpect(jsonPath("$[3].tipo", is("DEBITO")))
                .andExpect(jsonPath("$[3].valor", is("24,04")))
                .andExpect(jsonPath("$[3].codigoPai", isEmptyOrNullString()))
                .andExpect(jsonPath("$[3].proximoLancamento", is("4")))

                .andExpect(jsonPath("$[4].codigo", is("4")))
                .andExpect(jsonPath("$[4].dataLancamento", is(getStringFromCurrentDate())))
                .andExpect(jsonPath("$[4].dataAtualizacao", is(getStringFromCurrentDate())))
                .andExpect(jsonPath("$[4].dataVencimento", is("27/08/2018")))
                .andExpect(jsonPath("$[4].dataPagamento", isEmptyOrNullString()))
                .andExpect(jsonPath("$[4].recorrente", is("S")))
                .andExpect(jsonPath("$[4].grupo", is("Saúde")))
                .andExpect(jsonPath("$[4].subGrupo", is("Suplementos")))
                .andExpect(jsonPath("$[4].local", is("Site")))
                .andExpect(jsonPath("$[4].descricao", is("Suplementos comprados pela Carol")))
                .andExpect(jsonPath("$[4].usouCartao", is("S")))
                .andExpect(jsonPath("$[4].cartao", is("0744")))
                .andExpect(jsonPath("$[4].parcelado", is("N")))
                .andExpect(jsonPath("$[4].parcela", isEmptyOrNullString()))
                .andExpect(jsonPath("$[4].parcelas", isEmptyOrNullString()))
                .andExpect(jsonPath("$[4].conta", is("CAROL")))
                .andExpect(jsonPath("$[4].tipo", is("DEBITO")))
                .andExpect(jsonPath("$[4].valor", is("24,04")))
                .andExpect(jsonPath("$[4].codigoPai", isEmptyOrNullString()))
                .andExpect(jsonPath("$[4].proximoLancamento", is("5")))

                .andExpect(jsonPath("$[5].codigo", is("5")))
                .andExpect(jsonPath("$[5].dataLancamento", is(getStringFromCurrentDate())))
                .andExpect(jsonPath("$[5].dataAtualizacao", is(getStringFromCurrentDate())))
                .andExpect(jsonPath("$[5].dataVencimento", is("27/09/2018")))
                .andExpect(jsonPath("$[5].dataPagamento", isEmptyOrNullString()))
                .andExpect(jsonPath("$[5].recorrente", is("S")))
                .andExpect(jsonPath("$[5].grupo", is("Saúde")))
                .andExpect(jsonPath("$[5].subGrupo", is("Suplementos")))
                .andExpect(jsonPath("$[5].local", is("Site")))
                .andExpect(jsonPath("$[5].descricao", is("Suplementos comprados pela Carol")))
                .andExpect(jsonPath("$[5].usouCartao", is("S")))
                .andExpect(jsonPath("$[5].cartao", is("0744")))
                .andExpect(jsonPath("$[5].parcelado", is("N")))
                .andExpect(jsonPath("$[5].parcela", isEmptyOrNullString()))
                .andExpect(jsonPath("$[5].parcelas", isEmptyOrNullString()))
                .andExpect(jsonPath("$[5].conta", is("CAROL")))
                .andExpect(jsonPath("$[5].tipo", is("DEBITO")))
                .andExpect(jsonPath("$[5].valor", is("24,04")))
                .andExpect(jsonPath("$[5].codigoPai", isEmptyOrNullString()))
                .andExpect(jsonPath("$[5].proximoLancamento", is("6")))

                .andExpect(jsonPath("$[6].codigo", is("6")))
                .andExpect(jsonPath("$[6].dataLancamento", is(getStringFromCurrentDate())))
                .andExpect(jsonPath("$[6].dataAtualizacao", is(getStringFromCurrentDate())))
                .andExpect(jsonPath("$[6].dataVencimento", is("27/10/2018")))
                .andExpect(jsonPath("$[6].dataPagamento", isEmptyOrNullString()))
                .andExpect(jsonPath("$[6].recorrente", is("S")))
                .andExpect(jsonPath("$[6].grupo", is("Saúde")))
                .andExpect(jsonPath("$[6].subGrupo", is("Suplementos")))
                .andExpect(jsonPath("$[6].local", is("Site")))
                .andExpect(jsonPath("$[6].descricao", is("Suplementos comprados pela Carol")))
                .andExpect(jsonPath("$[6].usouCartao", is("S")))
                .andExpect(jsonPath("$[6].cartao", is("0744")))
                .andExpect(jsonPath("$[6].parcelado", is("N")))
                .andExpect(jsonPath("$[6].parcela", isEmptyOrNullString()))
                .andExpect(jsonPath("$[6].parcelas", isEmptyOrNullString()))
                .andExpect(jsonPath("$[6].conta", is("CAROL")))
                .andExpect(jsonPath("$[6].tipo", is("DEBITO")))
                .andExpect(jsonPath("$[6].valor", is("24,04")))
                .andExpect(jsonPath("$[6].codigoPai", isEmptyOrNullString()))
                .andExpect(jsonPath("$[6].proximoLancamento", is("7")))

                .andExpect(jsonPath("$[7].codigo", is("7")))
                .andExpect(jsonPath("$[7].dataLancamento", is(getStringFromCurrentDate())))
                .andExpect(jsonPath("$[7].dataAtualizacao", is(getStringFromCurrentDate())))
                .andExpect(jsonPath("$[7].dataVencimento", is("27/11/2018")))
                .andExpect(jsonPath("$[7].dataPagamento", isEmptyOrNullString()))
                .andExpect(jsonPath("$[7].recorrente", is("S")))
                .andExpect(jsonPath("$[7].grupo", is("Saúde")))
                .andExpect(jsonPath("$[7].subGrupo", is("Suplementos")))
                .andExpect(jsonPath("$[7].local", is("Site")))
                .andExpect(jsonPath("$[7].descricao", is("Suplementos comprados pela Carol")))
                .andExpect(jsonPath("$[7].usouCartao", is("S")))
                .andExpect(jsonPath("$[7].cartao", is("0744")))
                .andExpect(jsonPath("$[7].parcelado", is("N")))
                .andExpect(jsonPath("$[7].parcela", isEmptyOrNullString()))
                .andExpect(jsonPath("$[7].parcelas", isEmptyOrNullString()))
                .andExpect(jsonPath("$[7].conta", is("CAROL")))
                .andExpect(jsonPath("$[7].tipo", is("DEBITO")))
                .andExpect(jsonPath("$[7].valor", is("24,04")))
                .andExpect(jsonPath("$[7].codigoPai", isEmptyOrNullString()))
                .andExpect(jsonPath("$[7].proximoLancamento", is("8")))

                .andExpect(jsonPath("$[8].codigo", is("8")))
                .andExpect(jsonPath("$[8].dataLancamento", is(getStringFromCurrentDate())))
                .andExpect(jsonPath("$[8].dataAtualizacao", is(getStringFromCurrentDate())))
                .andExpect(jsonPath("$[8].dataVencimento", is("27/12/2018")))
                .andExpect(jsonPath("$[8].dataPagamento", isEmptyOrNullString()))
                .andExpect(jsonPath("$[8].recorrente", is("S")))
                .andExpect(jsonPath("$[8].grupo", is("Saúde")))
                .andExpect(jsonPath("$[8].subGrupo", is("Suplementos")))
                .andExpect(jsonPath("$[8].local", is("Site")))
                .andExpect(jsonPath("$[8].descricao", is("Suplementos comprados pela Carol")))
                .andExpect(jsonPath("$[8].usouCartao", is("S")))
                .andExpect(jsonPath("$[8].cartao", is("0744")))
                .andExpect(jsonPath("$[8].parcelado", is("N")))
                .andExpect(jsonPath("$[8].parcela", isEmptyOrNullString()))
                .andExpect(jsonPath("$[8].parcelas", isEmptyOrNullString()))
                .andExpect(jsonPath("$[8].conta", is("CAROL")))
                .andExpect(jsonPath("$[8].tipo", is("DEBITO")))
                .andExpect(jsonPath("$[8].valor", is("24,04")))
                .andExpect(jsonPath("$[8].codigoPai", isEmptyOrNullString()))
                .andExpect(jsonPath("$[8].proximoLancamento", isEmptyOrNullString()));
    }

    private void criarGrupo() throws StoreException, BusinessException, GenericException {
        GrupoVO vo = new GrupoVO()
                .nome("Saúde")
                .descricao("Grupo que gere gastos com saúde")
                .subGrupos(asList("Suplementos"));
        grupoController.criar(vo);
    }

    private void criarSubGrupo() throws StoreException, BusinessException, GenericException {
        SubGrupoVO vo = new SubGrupoVO()
                .nome("Suplementos")
                .descricao("Subgrupo de suplementos");
        subGrupoController.criar(vo);
    }

    private void criarSubGrupoAluguel() throws StoreException, BusinessException, GenericException {
        SubGrupoVO vo = new SubGrupoVO()
                .nome("Aluguel")
                .descricao("Subgrupo de aluguel");
        subGrupoController.criar(vo);
    }

    private void criarLocal() throws StoreException, BusinessException, GenericException {
        LocalVO vo = new LocalVO()
                .nome("Site");
        localController.criar(vo);
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

    private void criarContabilidadeSemCartaoComAssert() throws Exception {
        ContabilidadeVO vo = getVOParcelado()
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

    private void criarContabilidadeSemCartao() throws Exception {
        ContabilidadeVO vo = getVOParcelado()
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
                .andExpect(jsonPath("$.codigos", hasSize(1)));
    }

    private void criarContabilidadeParceladaComAssert() throws Exception {
        ContabilidadeVO vo = getVOParcelado();
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

    private void criarContabilidadeParcelada() throws Exception {
        ContabilidadeVO vo = getVOParcelado();
        String json = gson.toJson(vo);

        mvc.perform(post("/contabilidade")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigos", hasSize(7)));
    }

    private void criarContabilidadeRecorrenteComAssert() throws Exception {
        ContabilidadeVO vo = getVOParcelado()
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

    private void criarContabilidadeRecorrente() throws Exception {
        ContabilidadeVO vo = getVOParcelado()
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
                .andExpect(jsonPath("$.codigos", hasSize(9)));
    }

    private ContabilidadeVO getVOParcelado() {
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

    private ContabilidadeVO getVOSemCartao() {
        return getVOParcelado()
                .recorrente("N")
                .parcelado("N")
                .usouCartao("N");
    }

    private ContabilidadeVO getVORecorrente() {
        return getVOParcelado()
                .recorrente("S")
                .parcelado("N");
    }
}
