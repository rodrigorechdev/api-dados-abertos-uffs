package br.com.dadosabertosuffs.workflow.activity;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.com.dadosabertosuffs.entity.dto.ColunasRelacionadas;
import br.com.dadosabertosuffs.entity.dto.ResourceWithRelationshipResponse;
import br.com.dadosabertosuffs.entity.dto.ResourceEstrutura;
import br.com.dadosabertosuffs.entity.dto.ResourceRelacionado;
import br.com.dadosabertosuffs.workflow.service.ObterResourceService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ObterConteudoRecursoRelacionadoActivity {

    @Autowired
    private final ObterResourceService obterResourceService;

    
    public List<ResourceWithRelationshipResponse> execute(List<String> relacionamentosFiltro, String nomeDatasetPrincipal, List<HashMap<String, String>> datasetPrincipalConteudoHash, HashMap<String, ResourceEstrutura> hashRecursosEstrutura) throws JsonProcessingException {
        List<ColunasRelacionadas> datasetPrincipalRelacionamentos = filtrarRelacionamentos(relacionamentosFiltro, hashRecursosEstrutura.get(nomeDatasetPrincipal).getColunasRelacionadas());

        return obterConteudoRecursosRelacionados(datasetPrincipalConteudoHash, datasetPrincipalRelacionamentos, hashRecursosEstrutura);
    }

    private List<ResourceWithRelationshipResponse> obterConteudoRecursosRelacionados(List<HashMap<String, String>> datasetPrincipalConteudoHash, List<ColunasRelacionadas> datasetPrincipalRelacionamentos, HashMap<String, ResourceEstrutura> hashRecursosPorDataset) {
        return datasetPrincipalConteudoHash.stream().map((registroConteudoHash) -> {
            List<ResourceRelacionado> relacionamentosConteudo = datasetPrincipalRelacionamentos.stream()
            .map((relacionamento) -> obterRelacionamentoConteudo(relacionamento, hashRecursosPorDataset, registroConteudoHash))
            .collect(Collectors.toList());

            return new ResourceWithRelationshipResponse(registroConteudoHash, relacionamentosConteudo);
        }
        ).collect(Collectors.toList());
    }


    private ResourceRelacionado obterRelacionamentoConteudo(ColunasRelacionadas relacionamento, HashMap<String, ResourceEstrutura> hashRecursoEstruturaPorDataset, HashMap<String, String> elementoHashCampoValor) {
        String idDatasetRelacionado = hashRecursoEstruturaPorDataset.get(relacionamento.getNomeDataset()).getId();
        String nomeCampoRelacionado = relacionamento.getNomeCampo();
        String valorCampoRelacionado = elementoHashCampoValor.get(relacionamento.getNomeCampo());

        var conteudoRecursoRelacionado = obterResourceService.obterRecursoConteudo(idDatasetRelacionado, nomeCampoRelacionado, valorCampoRelacionado);

        return ResourceRelacionado.builder()
        .datasetName(relacionamento.getNomeDataset())
        .fieldName(nomeCampoRelacionado)
        .content(conteudoRecursoRelacionado)
        .build();
    }

    private List<ColunasRelacionadas> filtrarRelacionamentos(List<String> relacionamentosFiltro, List<ColunasRelacionadas> colunasRelacionadas) {
        return colunasRelacionadas
        .stream()
        .filter(
            (colunaRelacionada) -> relacionamentosFiltro == null || relacionamentosFiltro.contains(colunaRelacionada.getNomeCampo()))
        .collect(Collectors.toList());
    }
}
