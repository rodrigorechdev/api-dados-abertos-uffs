package br.com.dadosabertosuffs.workflow.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;

import br.com.dadosabertosuffs.entity.dto.ColunasRelacionadas;
import br.com.dadosabertosuffs.entity.dto.Resource;
import br.com.dadosabertosuffs.entity.dto.ResourceComRelacionamentoResponse;
import br.com.dadosabertosuffs.entity.dto.ResourceRelacionado;
import br.com.dadosabertosuffs.entity.httpresponse.ResourceResponse;
import br.com.dadosabertosuffs.entity.httpresponse.ResourceResponseResult;
import br.com.dadosabertosuffs.workflow.service.ObterResourceService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ObterConteudoRecursoActivity {

    @Autowired
    private final ObterResourceService obterResourceService;
    /**
     * TODO transformar hashRecursosPorDataset.get(nomeDatasetPrincipal).getId() em um objeto
     */
    public String execute(String nomeDatasetPrincipal, HashMap<String, Resource> hashRecursosPorDataset, HashMap<String, List<ColunasRelacionadas>> relacionamentosDatasets, String filtros) throws JsonProcessingException {
        var idDatasetPrincipal = hashRecursosPorDataset.get(nomeDatasetPrincipal).getId();
        String datasetPrincipalConteudoString = filtros == null ?
            obterResourceService.obterRecursoConteudo(idDatasetPrincipal) :
            obterResourceService.obterRecursoConteudo(idDatasetPrincipal, filtros);
        
        List<HashMap<String, String>> datasetPrincipalConteudoHash = obtemHashDeCampoValor(new Gson().fromJson(datasetPrincipalConteudoString, ResourceResponse.class).getResult());

        List<ColunasRelacionadas> datasetPrincipalRelacionamentos = relacionamentosDatasets.get(nomeDatasetPrincipal);

        var conteudoRecursosRelacionados = obterConteudoRecursosRelacionados(datasetPrincipalConteudoHash, datasetPrincipalRelacionamentos, hashRecursosPorDataset);
            
        return new Gson().toJson(conteudoRecursosRelacionados);
    }

    /**
     * Obtém uma lista do conteúdo ao receber o body da requisição inteiro por parametro. Cada
     * elemento da lista obtida representa um registro, cada registro é um hash referente ao nome
     * e valor de cada campo do registro.
    */
    private List<HashMap<String, String>> obtemHashDeCampoValor(ResourceResponseResult conteudoRecursoDatasetPrincipal) {
        List<HashMap<String, String>> listRegistros = new ArrayList<>();
        for(int i=0; i< conteudoRecursoDatasetPrincipal.getRecords().size(); i++) {
            var registroJsonArray = conteudoRecursoDatasetPrincipal.getRecords().get(i).getAsJsonArray();
            HashMap<String, String> hashRecurso = new HashMap<>();
            listRegistros.add(hashRecurso);
            for(int i2=0; i2< registroJsonArray.size(); i2++) {
                hashRecurso.put(conteudoRecursoDatasetPrincipal.getFields().get(i2).getId(), registroJsonArray.get(i2).getAsString());
            }
        }
        return listRegistros;
    }

    /**
     * Para cada registro, consulta cada um dos relacionamentos.
     */
    private List<ResourceComRelacionamentoResponse> obterConteudoRecursosRelacionados(List<HashMap<String, String>> datasetPrincipalConteudoHash, List<ColunasRelacionadas> datasetPrincipalRelacionamentos, HashMap<String, Resource> hashRecursosPorDataset) {
        return datasetPrincipalConteudoHash.stream().map((registroConteudoHash) -> {
            List<ResourceRelacionado> relacionamentosConteudo = datasetPrincipalRelacionamentos.stream()
            .map((relacionamento) -> obterRelacionamentoConteudo(relacionamento, hashRecursosPorDataset, registroConteudoHash))
            .collect(Collectors.toList());

            return new ResourceComRelacionamentoResponse(registroConteudoHash, relacionamentosConteudo);
        }
        ).collect(Collectors.toList());
    }


    private ResourceRelacionado obterRelacionamentoConteudo(ColunasRelacionadas relacionamento, HashMap<String, Resource> hashRecursosPorDataset, HashMap<String, String> elementoHashCampoValor) {
        String idDatasetRelacionado = hashRecursosPorDataset.get(relacionamento.getNomeDataset()).getId();
        String nomeCampoRelacionado = relacionamento.getNomeCampo();
        String valorCampoRelacionado = elementoHashCampoValor.get(relacionamento.getNomeCampo());

        String conteudoRecursoRelacionado = obterResourceService.obterRecursoConteudo(idDatasetRelacionado, nomeCampoRelacionado, valorCampoRelacionado);

        try {
            return ResourceRelacionado.builder()
            .nomeDataset(relacionamento.getNomeDataset())
            .nomeCampoRelacionado(nomeCampoRelacionado)
            .conteudoRecurso(new JSONObject(conteudoRecursoRelacionado))
            .build();
        } catch(JSONException e) {
            throw new RuntimeException();
        }
    }
}
