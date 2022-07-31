package br.com.dadosabertosuffs.workflow.activity;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.dadosabertosuffs.entity.dto.ResourceWithRelationshipResponse;
import br.com.dadosabertosuffs.entity.httpresponse.ResourceResponse;

@Service
public class CriarObterDatasetConteudoResponse {
    
    public ResourceResponse execute(ResourceResponse datasetPrincipalConteudo, List<ResourceWithRelationshipResponse> conteudoDatasetPrincipalComRelacionamento) {
        datasetPrincipalConteudo.getResult().setRecordsWithRelationships(conteudoDatasetPrincipalComRelacionamento);
        datasetPrincipalConteudo.getResult().setRecords(null);
        return datasetPrincipalConteudo;
    }
}
