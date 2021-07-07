package com.desafio.sicred.controllers.sessoes;

import java.util.List;

import javax.validation.Valid;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.desafio.sicred.exceptions.pauta.FalhaAoBuscarPautaException;
import com.desafio.sicred.models.request.SessaoRequestModel;
import com.desafio.sicred.models.request.VotoRequestModel;
import com.desafio.sicred.models.response.ErrorResponseModel;
import com.desafio.sicred.models.response.SessaoResponseModel;
import com.desafio.sicred.models.response.VotoResponseModel;
import com.desafio.sicred.models.response.TotalVotosResponseModel;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RequestMapping("/api/v1")
@Api(value = "sessoes")
public interface SessoesApi {

     @ApiOperation(value = "Inicia uma nova sessão de votação. ", nickname = "iniciarSessao", notes = "Uma nova sessão na base de dados. ", response = SessaoResponseModel.class)
     @ApiResponses(value = {
               @ApiResponse(code = 201, message = "Sessão iniciada com sucesso. ", response = SessaoResponseModel.class),
               @ApiResponse(code = 400, message = "Dados inválidos para iniciar uma nova sessão. "),
               @ApiResponse(code = 422, message = "Não foi possível iniciar a Sessão. tente novamente mais tarde. ", response = ErrorResponseModel.class)})
     @PostMapping(value = "/sessoes/iniciar",
               produces = {"application/json"},
               consumes = {"application/json"})
     ResponseEntity<SessaoResponseModel> iniciarSessao (@ApiParam(value = "Dados necessários para iniciar uma sessão. ", required = true) @Valid @RequestBody SessaoRequestModel sessaoRequestModel);

     @ApiOperation(value = "Atualizar Sessão. ", nickname = "atualizarSessao", notes = "Atualizar uma Sessão existente. ", response = SessaoResponseModel.class)
     @ApiResponses(value = {
               @ApiResponse(code = 200, message = "Sessão atualizada com sucesso. ", response = SessaoResponseModel.class),
               @ApiResponse(code = 422, message = "Não foi possível atualizar a Sessão. tente novamente mais tarde. ", response = ErrorResponseModel.class)})
     @PutMapping(value = "/sessoes/{id}",
               produces = {"application/json"},
               consumes = {"application/json"})
     ResponseEntity<SessaoResponseModel> atualizar(@ApiParam(value = "ID da sessão", required = true) @PathVariable("id") Long id, @ApiParam(value = "Dados necessários para atualizar uma sessão. ", required = true) @Valid @RequestBody SessaoRequestModel sessaoRequestModel);

     @ApiOperation(value = "Buscar sessão por Id. ", nickname = "buscarSessaoPorId", notes = "Buscar uma sessão específica e retornar as suas informacoes por id. ", response = SessaoResponseModel.class)
     @ApiResponses(value = {
               @ApiResponse(code = 200, message = "successful operation", response = SessaoResponseModel.class),
               @ApiResponse(code = 422, message = "Não foi possível buscar a sessão. Tente novamente mais tarde. ", response = ErrorResponseModel.class)})
     @GetMapping(value = "/sessoes/{id}",
               produces = {"application/json"})
     ResponseEntity<SessaoResponseModel> buscarPorId(@ApiParam(value = "ID da sessão a ser retornado. ", required = true) @PathVariable("id") Long id);


     @ApiOperation(value = "Buscar todos as sessões. ", nickname = "buscarTodasSessoes", notes = "Buscar todas as sessões cadastrados na base de dados. ", response = SessaoResponseModel.class, responseContainer = "List")
     @ApiResponses(value = {
               @ApiResponse(code = 200, message = "successful operation", response = SessaoResponseModel.class, responseContainer = "List"),
               @ApiResponse(code = 404, message = "sessões não encontradas. "),
               @ApiResponse(code = 422, message = "Não foi possível buscar as sessões. Tente novamente mais tarde. ", response = ErrorResponseModel.class)})
     @GetMapping(value = "/sessoes",
               produces = {"application/json"})
     ResponseEntity<List<SessaoResponseModel>> buscarTodos(@PageableDefault(sort = "id", direction = Sort.Direction.ASC, page = 0, size = 10) Pageable page);

     @ApiOperation(value = "Excluir uma sessão. ", nickname = "deletarSessao", notes = "Excluir uma sessão da base de dados. ")
     @ApiResponses(value = {
               @ApiResponse(code = 204, message = "Sessão excluida com sucesso. "),
               @ApiResponse(code = 422, message = "Sessão não encontrada. ", response = ErrorResponseModel.class)})
     @DeleteMapping(value = "/sessoes/{id}",
               produces = {"application/json"})
     ResponseEntity<Void> deletar(@ApiParam(value = "id da sessão. ", required = true) @PathVariable("id") Long id);

     @ApiOperation(value = "Votar na sessão. ", nickname = "votarSessao", notes = "Vota em uma pauta na Sessão e armazena na base de dados. ", response = VotoResponseModel.class)
     @ApiResponses(value = {
               @ApiResponse(code = 201, message = "Voto realizado com sucesso. ", response = VotoResponseModel.class),
               @ApiResponse(code = 400, message = "Dados inválidos para realizar votação. "),
               @ApiResponse(code = 422, message = "Não foi possível realizar a votação. tente novamente mais tarde. ", response = ErrorResponseModel.class)})
     @PostMapping(value = "/sessoes/votar",
               produces = {"application/json"},
               consumes = {"application/json"})
     ResponseEntity<VotoResponseModel> votar (@ApiParam(value = "Dados necessários para votar. ", required = true) @Valid @RequestBody VotoRequestModel votoRequestModel);

     @ApiOperation(value = "Votos Total da Pauta na sessão e Fechar sessao. ", nickname = "votosTotalSessao", notes = "Total de Votos em uma pauta na Sessão. ", response = TotalVotosResponseModel.class)
     @ApiResponses(value = {
               @ApiResponse(code = 201, message = "Sucesso ao buscar dados. ", response = TotalVotosResponseModel.class),
               @ApiResponse(code = 400, message = "Dados não encontrados. "),
               @ApiResponse(code = 422, message = "Não foi possível buscar o total de votação. tente novamente mais tarde. ", response = ErrorResponseModel.class)})
     @GetMapping(value = "/sessoes/{idPauta}/pauta/{id}/fechar-sessao",
               produces = {"application/json"})
     ResponseEntity<TotalVotosResponseModel> contabilizarTotalVotosFechandoSessao (@ApiParam(value = "ID da pauta ser retornado o total de votos. ", required = true) @PathVariable("id") Long id, Long idSessao) throws FalhaAoBuscarPautaException;


}
