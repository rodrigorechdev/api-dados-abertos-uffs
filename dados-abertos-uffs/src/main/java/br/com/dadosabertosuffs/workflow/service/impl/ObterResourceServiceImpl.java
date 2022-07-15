package br.com.dadosabertosuffs.workflow.service.impl;

import java.io.IOException;
import java.net.URI;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import com.google.gson.Gson;

import br.com.dadosabertosuffs.constant.DadosAbertosConst;
import br.com.dadosabertosuffs.entity.httpresponse.ResourceResponse;
import br.com.dadosabertosuffs.entity.httpresponse.ResourceResponseResult;
import br.com.dadosabertosuffs.workflow.service.ObterResourceService;
import lombok.RequiredArgsConstructor;

@Component
@Service
@RequiredArgsConstructor
public class ObterResourceServiceImpl extends ServiceUtils implements ObterResourceService {
    
    @Override
    public ResourceResponseResult obterRecursoCampos(String idRecurso) {
        try {
            var httpRequest = super.criarRequest(obterUriObterRecursoCampos(idRecurso));
            var responseBody = super.obterResponseBody(httpRequest);
        
            return new Gson().fromJson(responseBody, ResourceResponse.class).getResult();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    @Override
    public String obterRecursoConteudo(String idRecurso) {
        try {
            var httpRequest = criarRequest(obterUriObterDatastoreConteudo(idRecurso));
            return super.obterResponseBody(httpRequest);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    @Override
    public String obterRecursoConteudo(String idRecurso, String filtroChave, String filtroValor) {
        try {
            var httpRequest = criarRequest(obterUriObterDatastoreConteudo(idRecurso, filtroChave, filtroValor));
            return super.obterResponseBody(httpRequest);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    @Override
    public String obterRecursoConteudo(String idRecurso, String filtroJson) {
        try {
            var httpRequest = criarRequest(obterUriObterDatastoreConteudo(idRecurso, filtroJson));
            return super.obterResponseBody(httpRequest);
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
        
    private URI obterUriObterDatastoreConteudo(String idResource) {
        return UriComponentsBuilder.fromUriString(DadosAbertosConst.URL_PORTAL_DADOS_ABERTOS_UFFS)
        .path(DadosAbertosConst.PATH_DATASTORE_SEARCH)
        .queryParam(DadosAbertosConst.QUERY_FORMATO_CONTEUDO, "lists")
        .queryParam(DadosAbertosConst.QUERY_RESOURCE_ID, idResource)
        .build()
        .toUri();
    }

    private URI obterUriObterDatastoreConteudo(String idResource, String filtroChave, String filtroValor) {    
        return UriComponentsBuilder.fromUriString(DadosAbertosConst.URL_PORTAL_DADOS_ABERTOS_UFFS)
        .path(DadosAbertosConst.PATH_DATASTORE_SEARCH)
        .queryParam(DadosAbertosConst.QUERY_FORMATO_CONTEUDO, "lists")
        .queryParam(DadosAbertosConst.QUERY_RESOURCE_ID, idResource)
        .queryParam(DadosAbertosConst.QUERY_FILTRO, "{\"" + filtroChave + "\":\"" + filtroValor + "\"}")
        .build()
        .toUri();
    }

    private URI obterUriObterDatastoreConteudo(String idResource, String filtroJson) {    
        return UriComponentsBuilder.fromUriString(DadosAbertosConst.URL_PORTAL_DADOS_ABERTOS_UFFS)
        .path(DadosAbertosConst.PATH_DATASTORE_SEARCH)
        .queryParam(DadosAbertosConst.QUERY_FORMATO_CONTEUDO, "lists")
        .queryParam(DadosAbertosConst.QUERY_RESOURCE_ID, idResource)
        .queryParam(DadosAbertosConst.QUERY_FILTRO, filtroJson)
        .build()
        .toUri();
    }
}
