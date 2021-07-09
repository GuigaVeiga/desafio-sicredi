package com.desafio.sicredi.services;

import com.desafio.sicredi.converters.AssociadoConverter;
import com.desafio.sicredi.exceptions.associado.*;
import com.desafio.sicredi.models.entities.Associado;
import com.desafio.sicredi.models.request.AssociadoRequestModel;
import com.desafio.sicredi.models.response.AssociadoResponseModel;
import com.desafio.sicredi.repositories.AssociadoRepository;
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
public class AssociadoServiceTest {

    private static final Long ID_ASSOCIADO = 1L;
    private static final Long ID_ASSOCIADO_DOIS = 2L;
    private static final String NOME_ASSOCIADO = "ASSOCIADO";
    private static final String NOME_ASSOCIADO_DOIS = "ASSOCIADO_2";
    private static final String CPF_ASSOCIADO = "CPF_ASSOCIADO";
    private static final String CPF_ASSOCIADO_DOIS = "CPF_ASSOCIADO_2";
    private static final String MSG_ASSOCIADO_VAZIO = "Nome e CPF do associado vazio. Favor preencher o campo. ";
    private static final String MSG_ASSOCIADO_NAO_ENCONTRADO_PELO_ID = "Associado não encontrado na base de dados. Confira se o id esta correto. ID: %s";

    @Mock
    private AssociadoRepository repository;

    @Spy
    private AssociadoConverter converter;

    @InjectMocks
    private AssociadoService service;

    private AssociadoRequestModel associadoRequestModel;

    private Associado associadoDatabase;

    @Before
    public void setUp() {

        associadoRequestModel = AssociadoRequestModel.builder()
                .nome(NOME_ASSOCIADO)
                .cpf(CPF_ASSOCIADO)
                .build();

        associadoDatabase = Associado.builder()
                .id(ID_ASSOCIADO)
                .nome(NOME_ASSOCIADO)
                .cpf(CPF_ASSOCIADO)
                .build();
    }

    /* criação */
    @Test
    public void deveLancaExececaoCasoNomeAssociadoSejaNulo() {
        associadoRequestModel.setNome(null);

        FalhaAoCriarAssociadoException exception = assertThrows(
                FalhaAoCriarAssociadoException.class, () -> this.service.criar(associadoRequestModel));

        assertThat(exception.getMessageDetail())
                .isEqualTo(MSG_ASSOCIADO_VAZIO);

        assertThat(exception.getMessage())
                .isEqualTo("Error ao criar associado, tente novamente mais tarde. ");

        assertThat(exception.getCause())
                .isInstanceOf(FalhaValidacaoAssociadoException.class);
    }

    @Test
    public void deveLancaExececaoCasoNomeAssociadoSejaVazio() {
        associadoRequestModel.setNome("");

        FalhaAoCriarAssociadoException exception = assertThrows(
                FalhaAoCriarAssociadoException.class, () -> this.service.criar(associadoRequestModel));

        assertThat(exception.getMessageDetail())
                .isEqualTo(MSG_ASSOCIADO_VAZIO);

        assertThat(exception.getMessage())
                .isEqualTo("Error ao criar associado, tente novamente mais tarde. ");

        assertThat(exception.getCause())
                .isInstanceOf(FalhaValidacaoAssociadoException.class);
    }

    @Test
    public void deveLancaExececaoCasoCpfAssociadoSejaNulo() {
        associadoRequestModel.setCpf(null);

        FalhaAoCriarAssociadoException exception = assertThrows(
                FalhaAoCriarAssociadoException.class, () -> this.service.criar(associadoRequestModel));

        assertThat(exception.getMessageDetail())
                .isEqualTo(MSG_ASSOCIADO_VAZIO);

        assertThat(exception.getMessage())
                .isEqualTo("Error ao criar associado, tente novamente mais tarde. ");

        assertThat(exception.getCause())
                .isInstanceOf(FalhaValidacaoAssociadoException.class);
    }

    @Test
    public void deveLancaExececaoCasoCpfAssociadoSejaVazio() {
        associadoRequestModel.setCpf("");

        FalhaAoCriarAssociadoException exception = assertThrows(
                FalhaAoCriarAssociadoException.class, () -> this.service.criar(associadoRequestModel));

        assertThat(exception.getMessageDetail())
                .isEqualTo(MSG_ASSOCIADO_VAZIO);

        assertThat(exception.getMessage())
                .isEqualTo("Error ao criar associado, tente novamente mais tarde. ");

        assertThat(exception.getCause())
                .isInstanceOf(FalhaValidacaoAssociadoException.class);
    }

    @Test
    public void deveRetornarAssociadoResponseModelComValoresPassadosNaRequestInseridosNoBancoDeDadosNoFluxoDeCriacaoDeUmAssociado() {
        Mockito.lenient().when(repository.findById(eq(ID_ASSOCIADO)))
                .thenReturn(empty());

        when(repository.save(any(Associado.class))).thenReturn(associadoDatabase);

        AssociadoResponseModel criarAssociadoResponse = this.service.criar(associadoRequestModel);

        assertThat(criarAssociadoResponse.getId())
                .isEqualTo(associadoDatabase.getId());

        assertThat(criarAssociadoResponse.getNome())
                .isEqualTo(associadoDatabase.getNome());

        assertThat(criarAssociadoResponse.getCpf())
                .isEqualTo(associadoDatabase.getCpf());
    }

    /* atualização */
    @Test
    public void deveLancarExececaoQuandoAssociadoPassadoNaRequestNaoExistirNoBancoDeDadosNoFluxoDeAtualizacaoDoAssociado() {

        when(repository.findById(eq(ID_ASSOCIADO))).thenReturn(empty());

        String mensagemException = format("Associado não encontrado na base de dados. Confira se o id esta correto. ID: %s", ID_ASSOCIADO);

        FalhaAoAtualizarAssociadoException exception = assertThrows(
                FalhaAoAtualizarAssociadoException.class, () -> this.service.atualizar(ID_ASSOCIADO, associadoRequestModel));

        assertThat(exception.getMessageDetail())
                .isEqualTo(mensagemException);

        assertThat(exception.getMessage())
                .isEqualTo("Error ao atualizar associado, tente novamente mais tarde. ");

        assertThat(exception.getCause())
                .isInstanceOf(FalhaAoBuscarAssociadoException.class);
    }

    @Test
    public void deveAtualizarAssociadoComPropriedadesRecebidasNoFluxoDeAtualizacaoDoAssociado() {

        when(repository.findById(eq(ID_ASSOCIADO)))
                .thenReturn(ofNullable(associadoDatabase));

        associadoRequestModel.setNome(NOME_ASSOCIADO_DOIS);
        associadoRequestModel.setCpf(CPF_ASSOCIADO);

        this.service.atualizar(ID_ASSOCIADO, associadoRequestModel);

        ArgumentCaptor<Associado> associadoArgumentCaptor = ArgumentCaptor.forClass(Associado.class);

        verify(repository).save(associadoArgumentCaptor.capture());

        Associado associadoCaptured = associadoArgumentCaptor.getValue();

        assertThat(associadoCaptured.getNome())
                .isEqualTo(associadoRequestModel.getNome());

        assertThat(associadoCaptured.getId())
                .isEqualTo(ID_ASSOCIADO);
    }

    @Test
    public void deveRetornarAssociadoAtualizadoCasoFluxoDeAtualizacaoSejaSucesso() {
        when(repository.findById(eq(ID_ASSOCIADO)))
                .thenReturn(ofNullable(associadoDatabase));

        associadoRequestModel.setNome(NOME_ASSOCIADO_DOIS);
        associadoRequestModel.setCpf(CPF_ASSOCIADO);

        AssociadoResponseModel atualizarResponseModel = this.service.atualizar(ID_ASSOCIADO, associadoRequestModel);

        assertThat(atualizarResponseModel.getId())
                .isEqualTo(ID_ASSOCIADO);

        assertThat(atualizarResponseModel.getNome())
                .isEqualTo(associadoRequestModel.getNome());

        assertThat(atualizarResponseModel.getCpf())
                .isEqualTo(associadoRequestModel.getCpf());
    }

    /* buscar */
    @Test
    public void deveRetornarExcecaoQuandoAlgumErroAcontecerNoFluxoDaBuscaDeTodosAssociados() {
        List<Associado> associados = new ArrayList<>();
        Page<Associado> associadoPage = new PageImpl(associados);

        when(repository.findAll(associadoPage.getPageable()))
                .thenThrow(new RuntimeException("Error"));

        FalhaAoBuscarAssociadosException exception = assertThrows(
                FalhaAoBuscarAssociadosException.class, () -> this.service.buscarTodos(associadoPage.getPageable())
        );

        assertThat(exception.getMessageDetail())
                .isEqualTo("Error");

        assertThat(exception.getMessage())
                .isEqualTo("Error ao buscar associados, tente novamente mais tarde. ");

        assertThat(exception.getCause())
                .isInstanceOf(RuntimeException.class);
    }

    @Test
    public void deveRetornarNuloCasoNaoSejaEncontradoNenhumAssociadoNaBase() {
        List<Associado> associados = new ArrayList<>();
        Page<Associado> associadoPage = new PageImpl(associados);

        when(repository.findAll(associadoPage.getPageable()))
                .thenReturn(associadoPage);

        assertThat(this.service.buscarTodos(associadoPage.getPageable())).isNull();
    }

    @Test
    public void deveRetornarListaDeAssociadosCasoSejaEncontradoAssociadosNaBase() {
        Associado associadoUm = Associado
                .builder()
                .id(ID_ASSOCIADO)
                .nome(NOME_ASSOCIADO)
                .cpf(CPF_ASSOCIADO)
                .build();

        Associado associadoDois = Associado
                .builder()
                .id(ID_ASSOCIADO_DOIS)
                .nome(NOME_ASSOCIADO_DOIS)
                .cpf(CPF_ASSOCIADO_DOIS)
                .build();

        List<Associado> associadoDBResponse = asList(associadoUm, associadoDois);
        Page<Associado> associadoPage = new PageImpl(associadoDBResponse);

        when(repository.findAll(associadoPage.getPageable()))
                .thenReturn(associadoPage);

        List<AssociadoResponseModel> buscarTodosResponseModel = this.service.buscarTodos(associadoPage.getPageable());

        assertThat(buscarTodosResponseModel).isNotNull();

        assertThat(buscarTodosResponseModel).isNotEmpty();

        assertThat(buscarTodosResponseModel.get(0).getId())
                .isEqualTo(associadoUm.getId());

        assertThat(buscarTodosResponseModel.get(0).getNome())
                .isEqualTo(associadoUm.getNome());

        assertThat(buscarTodosResponseModel.get(0).getCpf())
                .isEqualTo(associadoUm.getCpf());

        assertThat(buscarTodosResponseModel.get(1).getId())
                .isEqualTo(associadoDois.getId());

        assertThat(buscarTodosResponseModel.get(1).getNome())
                .isEqualTo(associadoDois.getNome());

        assertThat(buscarTodosResponseModel.get(1).getCpf())
                .isEqualTo(associadoDois.getCpf());
    }

    @Test
    public void deveLancarExcecaoCasoIdAssociadoNaoExista() {
        when(repository.findById(eq(ID_ASSOCIADO)))
                .thenReturn(empty());

        String mensagemException = format("Associado não encontrado na base de dados. Confira se o id esta correto. ID: %s", ID_ASSOCIADO);

        FalhaAoBuscarAssociadoPorIdException exception = assertThrows(
                FalhaAoBuscarAssociadoPorIdException.class, () -> this.service.buscarPorId(ID_ASSOCIADO)
        );

        assertThat(exception.getMessageDetail())
                .isEqualTo(mensagemException);

        assertThat(exception.getMessage())
                .isEqualTo("Error ao buscar associado por id, tente novamente mais tarde. ");

        assertThat(exception.getCause())
                .isInstanceOf(FalhaAoBuscarAssociadoException.class);
    }

    @Test
    public void deveRetornarAssociadoCasoIdAssociadoExista() {
        when(repository.findById(eq(ID_ASSOCIADO)))
                .thenReturn(of(associadoDatabase));

        AssociadoResponseModel buscarPorIdResponseModel = this.service.buscarPorId(ID_ASSOCIADO);

        assertThat(buscarPorIdResponseModel.getId())
                .isEqualTo(associadoDatabase.getId());

        assertThat(buscarPorIdResponseModel.getNome())
                .isEqualTo(associadoDatabase.getNome());

        assertThat(buscarPorIdResponseModel.getCpf())
                .isEqualTo(associadoDatabase.getCpf());
    }

    /* deleção */
    @Test
    public void deveLancarExcecaoCasoIdAssociadoNaoSejaEncontradoPeloIdNoFluxoDelecaoDeAssociado() {

        when(repository.findById(eq(ID_ASSOCIADO)))
                .thenReturn(empty());

        String mensagemException = format("Associado não encontrado na base de dados. Confira se o id esta correto. ID: %s", ID_ASSOCIADO);

        FalhaAoDeletarAssociadoException exception = assertThrows(
                FalhaAoDeletarAssociadoException.class, () -> this.service.deletar(ID_ASSOCIADO)
        );

        assertThat(exception.getMessageDetail())
                .isEqualTo(mensagemException);

        assertThat(exception.getMessage())
                .isEqualTo("Error ao deletar associado, tente novamente mais tarde. ");

        assertThat(exception.getCause())
                .isInstanceOf(FalhaAoBuscarAssociadoException.class);
    }

    @Test
    public void deveChamarMetodoDeDeletePassandoAssociadoEncontradoPeloIdNoFluxoDelecaoDeAssociado() {
        when(repository.findById(eq(ID_ASSOCIADO)))
                .thenReturn(of(associadoDatabase));

        this.service.deletar(ID_ASSOCIADO);

        ArgumentCaptor<Associado> associadoArgumentCaptor = ArgumentCaptor.forClass(Associado.class);

        verify(repository).delete(associadoArgumentCaptor.capture());

        assertThat(associadoArgumentCaptor.getValue())
                .isSameAs(associadoDatabase);
    }

}
