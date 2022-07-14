package br.com.dadosabertosuffs.workflow.activity;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;

import br.com.dadosabertosuffs.entity.dto.ResourceEstrutura;
import br.com.dadosabertosuffs.entity.httpresponse.ResourceResponse;
import br.com.dadosabertosuffs.workflow.service.ObterResourceService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ObterConteudoRecursoPrincipalActivity {

    @Autowired
    private final ObterResourceService obterResourceService;

    
    public ResourceResponse execute(String nomeDatasetPrincipal, HashMap<String, ResourceEstrutura> hashRecursosEstrutura, String filtros) throws JsonProcessingException {
        
        ResourceResponse datasetPrincipalConteudo = obtemDatasetPrincipalConteudo(hashRecursosEstrutura, nomeDatasetPrincipal, filtros);

        return datasetPrincipalConteudo;
    }

    private ResourceResponse obtemDatasetPrincipalConteudo(HashMap<String, ResourceEstrutura> hashRecursosEstrutura, String nomeDatasetPrincipal, String filtros) {
        var idDatasetPrincipal = hashRecursosEstrutura.get(nomeDatasetPrincipal).getId();
        String datasetConteudo =  (filtros == null) ?
            obterResourceService.obterRecursoConteudo(idDatasetPrincipal) :
            obterResourceService.obterRecursoConteudo(idDatasetPrincipal, filtros);

        return new Gson().fromJson(datasetConteudo, ResourceResponse.class);
    }

}
