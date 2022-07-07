package br.com.dadosabertosuffs.workflow.service.impl;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;

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
    public ResourceResponseResult obterRecursoConteudo(String idRecurso) {
        try {
            var httpRequest = criarRequest(obterUriObterDatastoreConteudo(idRecurso));
            var responseBody = super.obterResponseBody(httpRequest);
        
            return new Gson().fromJson(responseBody, ResourceResponse.class).getResult();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    private URI obterUriObterRecursoCampos(String idResource) {
        return new DefaultUriBuilderFactory(DadosAbertosConst.URL_PORTAL_DADOS_ABERTOS_UFFS)
        .builder() 
        .path(DadosAbertosConst.PATH_DATASTORE_SEARCH)
        .queryParam(DadosAbertosConst.QUERY_RESOURCE_ID, idResource)
        .queryParam(DadosAbertosConst.QUERY_PAGINACAO, 0)
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
