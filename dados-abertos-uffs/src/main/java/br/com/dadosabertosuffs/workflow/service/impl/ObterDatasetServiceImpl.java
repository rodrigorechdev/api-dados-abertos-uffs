package br.com.dadosabertosuffs.workflow.service.impl;

import java.io.IOException;
import java.net.URI;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import com.google.gson.Gson;

import br.com.dadosabertosuffs.constant.DadosAbertosConst;
import br.com.dadosabertosuffs.entity.httpresponse.DatasetShowResponse;
import br.com.dadosabertosuffs.entity.httpresponse.DatasetShowResponseResultResources;
import br.com.dadosabertosuffs.entity.httpresponse.NomesDatasetsResponse;
import br.com.dadosabertosuffs.workflow.service.ObterDatasetService;
import lombok.RequiredArgsConstructor;

@Component
@Service
@RequiredArgsConstructor
public class ObterDatasetServiceImpl extends ServiceUtils implements ObterDatasetService {
    
    @Override
    public List<String> obterNomesDatasets() throws IOException, InterruptedException {
        var httpRequest = criarRequest(obterUriNomesDatasets());
        var responseBody = super.obterResponseBody(httpRequest);

        return new Gson()
                    .fromJson(responseBody, NomesDatasetsResponse.class)
                    .getResult();   
    }

    /**Obtém lista com nome e id dos recursos presenets em um dataset */
    @Override
    public List<DatasetShowResponseResultResources> obterRecursosDeDataset(String nomeDataset) throws IOException, InterruptedException {
        var httpRequest = criarRequest(obterUriObterListaRecurso(nomeDataset));
        var responseBody = super.obterResponseBody(httpRequest);
    
        return new Gson()
            .fromJson(responseBody, DatasetShowResponse.class)
            .getResult()
            .getResources();
    }

    private URI obterUriNomesDatasets() {
        return UriComponentsBuilder.fromUriString(DadosAbertosConst.URL_PORTAL_DADOS_ABERTOS_UFFS)
        .path(DadosAbertosConst.PATH_RECURSO_LISTA)
        .build()
        .toUri();
    }
    
    private URI obterUriObterListaRecurso(String nomeDataset) {
        return UriComponentsBuilder.fromUriString(DadosAbertosConst.URL_PORTAL_DADOS_ABERTOS_UFFS)
        .path(DadosAbertosConst.PATH_RECURSO)
        .queryParam(DadosAbertosConst.QUERY_DATASET_ID, nomeDataset)
        .build()
        .toUri();
    }
}
