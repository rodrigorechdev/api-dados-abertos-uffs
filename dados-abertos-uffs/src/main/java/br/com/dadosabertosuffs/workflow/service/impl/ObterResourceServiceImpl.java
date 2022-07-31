package br.com.dadosabertosuffs.workflow.service.impl;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import com.google.gson.Gson;

import br.com.dadosabertosuffs.constant.DadosAbertosConst;
import br.com.dadosabertosuffs.entity.httpresponse.ResourceResponse;
import br.com.dadosabertosuffs.workflow.service.ObterResourceService;
import lombok.RequiredArgsConstructor;

@Component
@Service
@RequiredArgsConstructor
public class ObterResourceServiceImpl extends ServiceUtils implements ObterResourceService {
    
    @Override
    public ResourceResponse obterRecursoCampos(String idRecurso) {
        try {
            var httpRequest = super.criarRequest(obterUriObterRecursoCampos(idRecurso));
            var responseBody = super.obterResponseBody(httpRequest);
        
            return new Gson().fromJson(responseBody, ResourceResponse.class);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    @Override
    public ResourceResponse obterRecursoConteudo(String idRecurso, String filtroChave, String filtroValor) {
        var filtroJson = "{\"" + filtroChave + "\":\"" + filtroValor + "\"}";
        return obterRecursoConteudo(idRecurso, Optional.of(filtroJson));
    }

    @Override
    public ResourceResponse obterRecursoConteudo(String idRecurso, Optional<String> filtroJsonOptional) {
        try {
            var httpRequest = criarRequest(obterUriObterDatastoreConteudo(idRecurso, filtroJsonOptional));
            return new Gson().fromJson(super.obterResponseBody(httpRequest), ResourceResponse.class);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    private URI obterUriObterRecursoCampos(String idResource) {
        return UriComponentsBuilder.fromUriString(DadosAbertosConst.URL_PORTAL_DADOS_ABERTOS_UFFS)
        .path(DadosAbertosConst.PATH_DATASTORE_SEARCH)
        .queryParam(DadosAbertosConst.QUERY_FORMATO_CONTEUDO, "lists")
        .queryParam(DadosAbertosConst.QUERY_RESOURCE_ID, idResource)
        .queryParam(DadosAbertosConst.QUERY_PAGINACAO, 0)
        .build()
        .toUri();
    }

    /**
     * O encoding do filtro está sendo realizado pelo URLEncoder em vez do encoding do UriComponentsBuilder
     * pois o UriComponentsBuilder não estava codificando caracteres como : e }, isso não estava sendo
     * aceito pelo Ckan, já o URLEncoder codifica estes carácteres.
     */
    private URI obterUriObterDatastoreConteudo(String idResource, Optional<String> filtroJsonOptional) {
        if(filtroJsonOptional.isPresent()) {
            filtroJsonOptional = Optional.of(URLEncoder.encode(filtroJsonOptional.get(), StandardCharsets.UTF_8));
        }
        
        return UriComponentsBuilder.fromUriString(DadosAbertosConst.URL_PORTAL_DADOS_ABERTOS_UFFS)
        .path(DadosAbertosConst.PATH_DATASTORE_SEARCH)
        .queryParam(DadosAbertosConst.QUERY_FORMATO_CONTEUDO, "lists")
        .queryParam(DadosAbertosConst.QUERY_RESOURCE_ID, idResource)
        .queryParamIfPresent(DadosAbertosConst.QUERY_FILTRO, filtroJsonOptional)
        .build(true)
        .toUri();
    }
}
