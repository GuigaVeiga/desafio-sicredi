package com.desafio.sicredi.controllers.pautas;

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

import com.desafio.sicredi.models.request.PautaRequestModel;
import com.desafio.sicredi.models.response.PautaResponseModel;
import com.desafio.sicredi.models.response.ErrorResponseModel;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RequestMapping("/api/v1")
@Api(value = "pautas")
public interface PautasApi {

     @ApiOperation(value = "Adicionar uma nova pauta. ", nickname = "criarPauta", notes = "Adiciona uma nova pauta na base de dados. ", response = PautaResponseModel.class)
     @ApiResponses(value = {
               @ApiResponse(code = 201, message = "Pauta criada com sucesso. ", response = PautaResponseModel.class),
               @ApiResponse(code = 400, message = "Dados inválidos para inserção de uma nova pauta. "),
               @ApiResponse(code = 422, message = "Não foi possível inserir a Pauta. tente novamente mais tarde. ", response = ErrorResponseModel.class)})
     @PostMapping(value = "/pautas",
               produces = {"application/json"},
               consumes = {"application/json"})
     ResponseEntity<PautaResponseModel> criar (@ApiParam(value = "Dados necessários para inserção de uma pauta. ", required = true) @Valid @RequestBody PautaRequestModel pautaRequestModel);

     @ApiOperation(value = "Atualizar Pauta. ", nickname = "atualizarPauta", notes = "Atualizar uma Pauta existente. ", response = PautaResponseModel.class)
     @ApiResponses(value = {
               @ApiResponse(code = 200, message = "Pauta atualizada com sucesso. ", response = PautaResponseModel.class),
               @ApiResponse(code = 422, message = "Não foi possível atualizar a Pauta. tente novamente mais tarde. ", response = ErrorResponseModel.class)})
     @PutMapping(value = "/pautas/{id}",
               produces = {"application/json"},
               consumes = {"application/json"})
     ResponseEntity<PautaResponseModel> atualizar(@ApiParam(value = "ID da pauta", required = true) @PathVariable("id") Long id, @ApiParam(value = "Dados necessários para atualizar uma pauta. ", required = true) @Valid @RequestBody PautaRequestModel pautaRequestModel);

     @ApiOperation(value = "Buscar pauta por Id. ", nickname = "buscarPautaPorId", notes = "Buscar uma pauta específica e retornar as suas informacoes por id. ", response = PautaResponseModel.class)
     @ApiResponses(value = {
               @ApiResponse(code = 200, message = "successful operation", response = PautaResponseModel.class),
               @ApiResponse(code = 422, message = "Não foi possível buscar a pauta. Tente novamente mais tarde. ", response = ErrorResponseModel.class)})
     @GetMapping(value = "/pautas/{id}",
               produces = {"application/json"})
     ResponseEntity<PautaResponseModel> buscarPorId(@ApiParam(value = "ID da pauta a ser retornado. ", required = true) @PathVariable("id") Long id);


     @ApiOperation(value = "Buscar todos as pautas. ", nickname = "buscarTodasPautas", notes = "Buscar todas as pautas cadastrados na base de dados. ", response = PautaResponseModel.class, responseContainer = "List")
     @ApiResponses(value = {
               @ApiResponse(code = 200, message = "successful operation", response = PautaResponseModel.class, responseContainer = "List"),
               @ApiResponse(code = 404, message = "pautas não encontradas. "),
               @ApiResponse(code = 422, message = "Não foi possível buscar as pautas. Tente novamente mais tarde. ", response = ErrorResponseModel.class)})
     @GetMapping(value = "/pautas",
               produces = {"application/json"})
     ResponseEntity<List<PautaResponseModel>> buscarTodos(@PageableDefault(sort = "id", direction = Sort.Direction.ASC, page = 0, size = 10) Pageable page);

     @ApiOperation(value = "Excluir uma pauta. ", nickname = "deletarPauta", notes = "Excluir uma pauta da base de dados. ")
     @ApiResponses(value = {
               @ApiResponse(code = 204, message = "Pauta excluida com sucesso. "),
               @ApiResponse(code = 422, message = "Pauta não encontrada. ", response = ErrorResponseModel.class)})
     @DeleteMapping(value = "/pautas/{id}",
               produces = {"application/json"})
     ResponseEntity<Void> deletar(@ApiParam(value = "id da pauta. ", required = true) @PathVariable("id") Long id);


}
