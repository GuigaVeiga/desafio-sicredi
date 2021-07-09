package com.desafio.sicredi.services;

import com.desafio.sicredi.converters.PautaConverter;
import com.desafio.sicredi.exceptions.pauta.*;
import com.desafio.sicredi.exceptions.pauta.FalhaAoCriarPautaException;
import com.desafio.sicredi.exceptions.pauta.FalhaValidacaoPautaException;
import com.desafio.sicredi.models.entities.Pauta;
import com.desafio.sicredi.models.request.PautaRequestModel;
import com.desafio.sicredi.models.response.PautaResponseModel;
import com.desafio.sicredi.repositories.PautaRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;
import static java.util.Arrays.asList;
import static java.util.Optional.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PautaServiceTest {

    private static final Long ID_PAUTA = 1L;
    private static final Long ID_PAUTA_DOIS = 2L;
    private static final String TITULO_PAUTA = "PAUTA";
    private static final String TITULO_PAUTA_DOIS = "PAUTA_2";
    private static final String DESCRICAO_PAUTA = "DESCRICAO_PAUTA";
    private static final String DESCRICAO_PAUTA_DOIS = "DESCRICAO_PAUTA_2";
    private static final String MSG_PAUTA_VAZIO = "Título e Descrição da pauta vazio. Favor preencher o campo. ";
    private static final String MSG_PAUTA_NAO_ENCONTRADO_PELO_ID = "Pauta não encontrado na base de dados. Confira se o id esta correto. ID: %s";

    @Mock
    private PautaRepository repository;

    @Spy
    private PautaConverter converter;

    @InjectMocks
    private PautaService service;

    private PautaRequestModel pautaRequestModel;

    private Pauta pautaDatabase;

    @Before
    public void setUp() {

        pautaRequestModel = PautaRequestModel.builder()
                .titulo(TITULO_PAUTA)
                .descricao(DESCRICAO_PAUTA)
                .build();

        pautaDatabase = Pauta.builder()
                .id(ID_PAUTA)
                .titulo(TITULO_PAUTA)
                .descricao(DESCRICAO_PAUTA)
                .build();
    }

    /* criação */
    @Test
    public void deveLancaExececaoCasoNomePautaSejaNulo() {
        pautaRequestModel.setTitulo(null);

        FalhaAoCriarPautaException exception = assertThrows(
                FalhaAoCriarPautaException.class, () -> this.service.criar(pautaRequestModel));

        assertThat(exception.getMessageDetail())
                .isEqualTo(MSG_PAUTA_VAZIO);

        assertThat(exception.getMessage())
                .isEqualTo("Error ao criar pauta, tente novamente mais tarde. ");

        assertThat(exception.getCause())
                .isInstanceOf(FalhaValidacaoPautaException.class);
    }

    @Test
    public void deveLancaExececaoCasoNomePautaSejaVazio() {
        pautaRequestModel.setTitulo("");

        FalhaAoCriarPautaException exception = assertThrows(
                FalhaAoCriarPautaException.class, () -> this.service.criar(pautaRequestModel));

        assertThat(exception.getMessageDetail())
                .isEqualTo(MSG_PAUTA_VAZIO);

        assertThat(exception.getMessage())
                .isEqualTo("Error ao criar pauta, tente novamente mais tarde. ");

        assertThat(exception.getCause())
                .isInstanceOf(FalhaValidacaoPautaException.class);
    }

    @Test
    public void deveLancaExececaoCasoCpfPautaSejaNulo() {
        pautaRequestModel.setDescricao(null);

        FalhaAoCriarPautaException exception = assertThrows(
                FalhaAoCriarPautaException.class, () -> this.service.criar(pautaRequestModel));

        assertThat(exception.getMessageDetail())
                .isEqualTo(MSG_PAUTA_VAZIO);

        assertThat(exception.getMessage())
                .isEqualTo("Error ao criar pauta, tente novamente mais tarde. ");

        assertThat(exception.getCause())
                .isInstanceOf(FalhaValidacaoPautaException.class);
    }

    @Test
    public void deveLancaExececaoCasoCpfPautaSejaVazio() {
        pautaRequestModel.setDescricao("");

        FalhaAoCriarPautaException exception = assertThrows(
                FalhaAoCriarPautaException.class, () -> this.service.criar(pautaRequestModel));

        assertThat(exception.getMessageDetail())
                .isEqualTo(MSG_PAUTA_VAZIO);

        assertThat(exception.getMessage())
                .isEqualTo("Error ao criar pauta, tente novamente mais tarde. ");

        assertThat(exception.getCause())
                .isInstanceOf(FalhaValidacaoPautaException.class);
    }

    @Test
    public void deveRetornarPautaResponseModelComValoresPassadosNaRequestInseridosNoBancoDeDadosNoFluxoDeCriacaoDeUmPauta() {
        Mockito.lenient().when(repository.findById(eq(ID_PAUTA)))
                .thenReturn(empty());

        when(repository.save(any(Pauta.class))).thenReturn(pautaDatabase);

        PautaResponseModel criarPautaResponse = this.service.criar(pautaRequestModel);

        assertThat(criarPautaResponse.getId())
                .isEqualTo(pautaDatabase.getId());

        assertThat(criarPautaResponse.getTitulo())
                .isEqualTo(pautaDatabase.getTitulo());

        assertThat(criarPautaResponse.getDescricao())
                .isEqualTo(pautaDatabase.getDescricao());
    }

    /* atualização */
    @Test
    public void deveLancarExececaoQuandoPautaPassadoNaRequestNaoExistirNoBancoDeDadosNoFluxoDeAtualizacaoDoPauta() {

        when(repository.findById(eq(ID_PAUTA))).thenReturn(empty());

        String mensagemException = format("Pauta não encontrada na base de dados. Confira se o id esta correto. ID: %s", ID_PAUTA);

        FalhaAoAtualizarPautaException exception = assertThrows(
                FalhaAoAtualizarPautaException.class, () -> this.service.atualizar(ID_PAUTA, pautaRequestModel));

        assertThat(exception.getMessageDetail())
                .isEqualTo(mensagemException);

        assertThat(exception.getMessage())
                .isEqualTo("Error ao atualizar Pauta, tente novamente mais tarde. ");

        assertThat(exception.getCause())
                .isInstanceOf(FalhaAoBuscarPautaException.class);
    }

    @Test
    public void deveAtualizarPautaComPropriedadesRecebidasNoFluxoDeAtualizacaoDoPauta() {

        when(repository.findById(eq(ID_PAUTA)))
                .thenReturn(ofNullable(pautaDatabase));

        pautaRequestModel.setTitulo(TITULO_PAUTA_DOIS);
        pautaRequestModel.setDescricao(DESCRICAO_PAUTA);

        this.service.atualizar(ID_PAUTA, pautaRequestModel);

        ArgumentCaptor<Pauta> pautaArgumentCaptor = ArgumentCaptor.forClass(Pauta.class);

        verify(repository).save(pautaArgumentCaptor.capture());

        Pauta pautaCaptured = pautaArgumentCaptor.getValue();

        assertThat(pautaCaptured.getTitulo())
                .isEqualTo(pautaRequestModel.getTitulo());

        assertThat(pautaCaptured.getId())
                .isEqualTo(ID_PAUTA);
    }

    @Test
    public void deveRetornarPautaAtualizadoCasoFluxoDeAtualizacaoSejaSucesso() {
        when(repository.findById(eq(ID_PAUTA)))
                .thenReturn(ofNullable(pautaDatabase));

        pautaRequestModel.setTitulo(TITULO_PAUTA_DOIS);
        pautaRequestModel.setDescricao(DESCRICAO_PAUTA);

        PautaResponseModel atualizarResponseModel = this.service.atualizar(ID_PAUTA, pautaRequestModel);

        assertThat(atualizarResponseModel.getId())
                .isEqualTo(ID_PAUTA);

        assertThat(atualizarResponseModel.getTitulo())
                .isEqualTo(pautaRequestModel.getTitulo());

        assertThat(atualizarResponseModel.getDescricao())
                .isEqualTo(pautaRequestModel.getDescricao());
    }

    /* buscar */
    @Test
    public void deveRetornarExcecaoQuandoAlgumErroAcontecerNoFluxoDaBuscaDeTodosPautas() {
        List<Pauta> pautas = new ArrayList<>();
        Page<Pauta> pautaPage = new PageImpl(pautas);

        when(repository.findAll(pautaPage.getPageable()))
                .thenThrow(new RuntimeException("Error"));

        FalhaAoBuscarPautasException exception = assertThrows(
                FalhaAoBuscarPautasException.class, () -> this.service.buscarTodos(pautaPage.getPageable())
        );

        assertThat(exception.getMessageDetail())
                .isEqualTo("Error");

        assertThat(exception.getMessage())
                .isEqualTo("Error ao buscar pautas, tente novamente mais tarde. ");

        assertThat(exception.getCause())
                .isInstanceOf(RuntimeException.class);
    }

    @Test
    public void deveRetornarNuloCasoNaoSejaEncontradoNenhumPautaNaBase() {
        List<Pauta> pautas = new ArrayList<>();
        Page<Pauta> pautaPage = new PageImpl(pautas);

        when(repository.findAll(pautaPage.getPageable()))
                .thenReturn(pautaPage);

        assertThat(this.service.buscarTodos(pautaPage.getPageable())).isNull();
    }

    @Test
    public void deveRetornarListaDePautasCasoSejaEncontradoPautasNaBase() {
        Pauta pautaUm = Pauta
                .builder()
                .id(ID_PAUTA)
                .titulo(TITULO_PAUTA)
                .descricao(DESCRICAO_PAUTA)
                .build();

        Pauta pautaDois = Pauta
                .builder()
                .id(ID_PAUTA_DOIS)
                .titulo(TITULO_PAUTA_DOIS)
                .descricao(DESCRICAO_PAUTA_DOIS)
                .build();

        List<Pauta> pautaDBResponse = asList(pautaUm, pautaDois);
        Page<Pauta> pautaPage = new PageImpl(pautaDBResponse);

        when(repository.findAll(pautaPage.getPageable()))
                .thenReturn(pautaPage);

        List<PautaResponseModel> buscarTodosResponseModel = this.service.buscarTodos(pautaPage.getPageable());

        assertThat(buscarTodosResponseModel).isNotNull();

        assertThat(buscarTodosResponseModel).isNotEmpty();

        assertThat(buscarTodosResponseModel.get(0).getId())
                .isEqualTo(pautaUm.getId());

        assertThat(buscarTodosResponseModel.get(0).getTitulo())
                .isEqualTo(pautaUm.getTitulo());

        assertThat(buscarTodosResponseModel.get(0).getDescricao())
                .isEqualTo(pautaUm.getDescricao());

        assertThat(buscarTodosResponseModel.get(1).getId())
                .isEqualTo(pautaDois.getId());

        assertThat(buscarTodosResponseModel.get(1).getTitulo())
                .isEqualTo(pautaDois.getTitulo());

        assertThat(buscarTodosResponseModel.get(1).getDescricao())
                .isEqualTo(pautaDois.getDescricao());
    }

    @Test
    public void deveLancarExcecaoCasoIdPautaNaoExista() {
        when(repository.findById(eq(ID_PAUTA)))
                .thenReturn(empty());

        String mensagemException = format("Pauta não encontrada na base de dados. Confira se o id esta correto. ID: %s", ID_PAUTA);

        FalhaAoBuscarPautaPorIdException exception = assertThrows(
                FalhaAoBuscarPautaPorIdException.class, () -> this.service.buscarPorId(ID_PAUTA)
        );

        assertThat(exception.getMessageDetail())
                .isEqualTo(mensagemException);

        assertThat(exception.getMessage())
                .isEqualTo("Error ao buscar pauta por id, tente novamente mais tarde. ");

        assertThat(exception.getCause())
                .isInstanceOf(FalhaAoBuscarPautaException.class);
    }

    @Test
    public void deveRetornarPautaCasoIdPautaExista() {
        when(repository.findById(eq(ID_PAUTA)))
                .thenReturn(of(pautaDatabase));

        PautaResponseModel buscarPorIdResponseModel = this.service.buscarPorId(ID_PAUTA);

        assertThat(buscarPorIdResponseModel.getId())
                .isEqualTo(pautaDatabase.getId());

        assertThat(buscarPorIdResponseModel.getTitulo())
                .isEqualTo(pautaDatabase.getTitulo());

        assertThat(buscarPorIdResponseModel.getDescricao())
                .isEqualTo(pautaDatabase.getDescricao());
    }

    /* deleção */
    @Test
    public void deveLancarExcecaoCasoIdPautaNaoSejaEncontradoPeloIdNoFluxoDelecaoDePauta() {

        when(repository.findById(eq(ID_PAUTA)))
                .thenReturn(empty());

        String mensagemException = format("Pauta não encontrada na base de dados. Confira se o id esta correto. ID: %s", ID_PAUTA);

        FalhaAoDeletarPautaException exception = assertThrows(
                FalhaAoDeletarPautaException.class, () -> this.service.deletar(ID_PAUTA)
        );

        assertThat(exception.getMessageDetail())
                .isEqualTo(mensagemException);

        assertThat(exception.getMessage())
                .isEqualTo("Error ao deletar pauta, tente novamente mais tarde. ");

        assertThat(exception.getCause())
                .isInstanceOf(FalhaAoBuscarPautaException.class);
    }

    @Test
    public void deveChamarMetodoDeDeletePassandoPautaEncontradoPeloIdNoFluxoDelecaoDePauta() {
        when(repository.findById(eq(ID_PAUTA)))
                .thenReturn(of(pautaDatabase));

        this.service.deletar(ID_PAUTA);

        ArgumentCaptor<Pauta> pautaArgumentCaptor = ArgumentCaptor.forClass(Pauta.class);

        verify(repository).delete(pautaArgumentCaptor.capture());

        assertThat(pautaArgumentCaptor.getValue())
                .isSameAs(pautaDatabase);
    }

}
