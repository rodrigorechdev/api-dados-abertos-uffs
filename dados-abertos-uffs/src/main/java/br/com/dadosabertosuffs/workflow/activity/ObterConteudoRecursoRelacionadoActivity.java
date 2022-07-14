package br.com.dadosabertosuffs.workflow.activity;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.com.dadosabertosuffs.entity.dto.ColunasRelacionadas;
import br.com.dadosabertosuffs.entity.dto.ResourceEstrutura;
import br.com.dadosabertosuffs.entity.dto.ResourceComRelacionamentoResponse;
import br.com.dadosabertosuffs.entity.dto.ResourceRelacionado;
import br.com.dadosabertosuffs.workflow.service.ObterResourceService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ObterConteudoRecursoRelacionadoActivity {

    @Autowired
    private final ObterResourceService obterResourceService;

    
    public List<ResourceComRelacionamentoResponse> execute(List<HashMap<String, String>> datasetPrincipalConteudoHash, String nomeDatasetPrincipal, HashMap<String, ResourceEstrutura> hashRecursosEstrutura) throws JsonProcessingException {
        List<ColunasRelacionadas> datasetPrincipalRelacionamentos = hashRecursosEstrutura.get(nomeDatasetPrincipal).getColunasRelacionadas();

        return obterConteudoRecursosRelacionados(datasetPrincipalConteudoHash, datasetPrincipalRelacionamentos, hashRecursosEstrutura);
    }

    private List<ResourceComRelacionamentoResponse> obterConteudoRecursosRelacionados(List<HashMap<String, String>> datasetPrincipalConteudoHash, List<ColunasRelacionadas> datasetPrincipalRelacionamentos, HashMap<String, ResourceEstrutura> hashRecursosPorDataset) {
        return datasetPrincipalConteudoHash.stream().map((registroConteudoHash) -> {
            List<ResourceRelacionado> relacionamentosConteudo = datasetPrincipalRelacionamentos.stream()
            .map((relacionamento) -> obterRelacionamentoConteudo(relacionamento, hashRecursosPorDataset, registroConteudoHash))
            .collect(Collectors.toList());

            return new ResourceComRelacionamentoResponse(registroConteudoHash, relacionamentosConteudo);
        }
        ).collect(Collectors.toList());
    }


    private ResourceRelacionado obterRelacionamentoConteudo(ColunasRelacionadas relacionamento, HashMap<String, ResourceEstrutura> hashRecursoEstruturaPorDataset, HashMap<String, String> elementoHashCampoValor) {
        String idDatasetRelacionado = hashRecursoEstruturaPorDataset.get(relacionamento.getNomeDataset()).getId();
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
