package br.com.dadosabertosuffs.workflow.service.impl;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;

import com.google.gson.Gson;

import br.com.dadosabertosuffs.constant.DadosAbertosConst;
import br.com.dadosabertosuffs.entity.httpresponse.DatasetShowResponse;
import br.com.dadosabertosuffs.entity.httpresponse.DatasetShowResponseResultResources;
import br.com.dadosabertosuffs.entity.httpresponse.ResourceResponse;
import br.com.dadosabertosuffs.entity.httpresponse.ResourceResponseResult;
import br.com.dadosabertosuffs.workflow.service.ObterResourceService;
import lombok.RequiredArgsConstructor;

@Component
@Service
@RequiredArgsConstructor
public class ObterResourceServiceImpl implements ObterResourceService {
    
    private final HttpClient httpClient = HttpClient.newBuilder().build();

    /**Obtém lista com nome e id dos recursos de um dataset */
    @Override
    public List<DatasetShowResponseResultResources> obterRecursosDeDataset(String nomeDataset) throws IOException, InterruptedException {
        var httpRequest = criarRequestObterListaRecurso(nomeDataset);
        var responseBody = httpClient
            .send(httpRequest, HttpResponse.BodyHandlers.ofString())
            .body();
    
        return new Gson()
            .fromJson(responseBody, DatasetShowResponse.class)
            .getResult()
            .getResources();
    }

    
    @Override
    public ResourceResponseResult obterRecursoConteudo(String idRecurso) {
        try {
            var httpRequest = criarRequestObterDatastoreConteudo(idRecurso);
            var responseBody = httpClient
                .send(httpRequest, HttpResponse.BodyHandlers.ofString())
                .body();
        
            return new Gson().fromJson(responseBody, ResourceResponse.class).getResult();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    private HttpRequest criarRequestObterListaRecurso(String nomeDataset) {
        return HttpRequest.newBuilder()
        .uri(obterUriObterListaRecurso(nomeDataset))
        .GET()
        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .build();
    }
    
    private URI obterUriObterListaRecurso(String nomeDataset) {
        return new DefaultUriBuilderFactory(DadosAbertosConst.URL_PORTAL_DADOS_ABERTOS_UFFS)
        .builder() 
        .path(DadosAbertosConst.PATH_RECURSO)
        .queryParam(DadosAbertosConst.QUERY_DATASET_ID, nomeDataset)
        .build();
    }
    

    private HttpRequest criarRequestObterDatastoreConteudo(String idRecurso) {
        return HttpRequest.newBuilder()
        .uri(obterUriObterDatastoreConteudo(idRecurso))
        .GET()
        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .build();
    }
        
    private URI obterUriObterDatastoreConteudo(String idResource) {
        return new DefaultUriBuilderFactory(DadosAbertosConst.URL_PORTAL_DADOS_ABERTOS_UFFS)
        .builder() 
        .path(DadosAbertosConst.PATH_DATASTORE_SEARCH)
        .queryParam(DadosAbertosConst.QUERY_RESOURCE_ID, idResource)
        .build();
    }
}
