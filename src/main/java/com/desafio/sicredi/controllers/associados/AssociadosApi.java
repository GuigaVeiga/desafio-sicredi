package com.desafio.sicredi.controllers.associados;

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

import com.desafio.sicredi.models.request.AssociadoRequestModel;
import com.desafio.sicredi.models.response.AssociadoResponseModel;
import com.desafio.sicredi.models.response.ErrorResponseModel;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RequestMapping("/api/v1")
@Api(value = "associados")
public interface AssociadosApi {

     @ApiOperation(value = "Adicionar um novo associado. ", nickname = "criarAssociado", notes = "Adiciona um novo associado na base de dados. ", response = AssociadoResponseModel.class)
     @ApiResponses(value = {
               @ApiResponse(code = 201, message = "Associado criado com sucesso. ", response = AssociadoResponseModel.class),
               @ApiResponse(code = 400, message = "Dados inválidos para inserção de um novo associado. "),
               @ApiResponse(code = 422, message = "Não foi possível inserir o Associado. tente novamente mais tarde. ", response = ErrorResponseModel.class)})
     @PostMapping(value = "/associados",
               produces = {"application/json"},
               consumes = {"application/json"})
     ResponseEntity<AssociadoResponseModel> criar (@ApiParam(value = "Dados necessários para inserção de um associado. ", required = true) @Valid @RequestBody AssociadoRequestModel associadoRequestModel);

     @ApiOperation(value = "Atualizar Associado. ", nickname = "atualizarAssociado", notes = "Atualizar um Associado existente. ", response = AssociadoResponseModel.class)
     @ApiResponses(value = {
               @ApiResponse(code = 200, message = "Associado atualizado com sucesso. ", response = AssociadoResponseModel.class),
               @ApiResponse(code = 422, message = "Não foi possível atualizar o Associado. tente novamente mais tarde. ", response = ErrorResponseModel.class)})
     @PutMapping(value = "/associados/{id}",
               produces = {"application/json"},
               consumes = {"application/json"})
     ResponseEntity<AssociadoResponseModel> atualizar(@ApiParam(value = "ID do associado", required = true) @PathVariable("id") Long id, @ApiParam(value = "Dados necessários para atualizar um associado. ", required = true) @Valid @RequestBody AssociadoRequestModel associadoRequestModel);

     @ApiOperation(value = "Buscar associado por Id. ", nickname = "buscarAssociadoPorId", notes = "Buscar um associado especifico e retornar as suas informacoes por id. ", response = AssociadoResponseModel.class)
     @ApiResponses(value = {
               @ApiResponse(code = 200, message = "successful operation", response = AssociadoResponseModel.class),
               @ApiResponse(code = 422, message = "Não foi possível buscar o associado. Tente novamente mais tarde. ", response = ErrorResponseModel.class)})
     @GetMapping(value = "/associados/{id}",
               produces = {"application/json"})
     ResponseEntity<AssociadoResponseModel> buscarPorId(@ApiParam(value = "ID do associado a ser retornado. ", required = true) @PathVariable("id") Long id);


     @ApiOperation(value = "Buscar todos os associados. ", nickname = "buscarTodosAssociados", notes = "Buscar todos os associados cadastrados na base de dados. ", response = AssociadoResponseModel.class, responseContainer = "List")
     @ApiResponses(value = {
               @ApiResponse(code = 200, message = "successful operation", response = AssociadoResponseModel.class, responseContainer = "List"),
               @ApiResponse(code = 404, message = "associados não encontrado. "),
               @ApiResponse(code = 422, message = "Não foi possível buscar os associados. Tente novamente mais tarde. ", response = ErrorResponseModel.class)})
     @GetMapping(value = "/associados",
               produces = {"application/json"})
     ResponseEntity<List<AssociadoResponseModel>> buscarTodos(@PageableDefault(sort = "id", direction = Sort.Direction.ASC, page = 0, size = 10) Pageable page);

     @ApiOperation(value = "Excluir um associado. ", nickname = "deletarAssociado", notes = "Excluir um associado da base de dados. ")
     @ApiResponses(value = {
               @ApiResponse(code = 204, message = "Associado excluido com sucesso. "),
               @ApiResponse(code = 422, message = "Associado não encontrado. ", response = ErrorResponseModel.class)})
     @DeleteMapping(value = "/associados/{id}",
               produces = {"application/json"})
     ResponseEntity<Void> deletar(@ApiParam(value = "id do associado. ", required = true) @PathVariable("id") Long id);


}
