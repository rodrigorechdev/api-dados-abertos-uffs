package br.com.dadosabertosuffs.workflow.activity;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.dadosabertosuffs.entity.dto.ColunasRelacionadas;
import br.com.dadosabertosuffs.entity.dto.Resource;
import br.com.dadosabertosuffs.entity.dto.ResourceRelacionado;
import br.com.dadosabertosuffs.workflow.service.ObterResourceService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ObterConteudoRecursoActivity {
    
    @Autowired
    private final ObterResourceService obterResourceService;
    
    public void execute(String nomeDatasetPrincipal, HashMap<String, Resource> hashRecursosPorDataset, HashMap<String, List<ColunasRelacionadas>> relacionamentosDatasets) {
        var conteudoRecursoDatasetPrincipal = obterRecursoConteudo(hashRecursosPorDataset, nomeDatasetPrincipal);

        var relacionamentosDatasetPrincipal = relacionamentosDatasets.get(nomeDatasetPrincipal);
        List<ResourceRelacionado> conteudoRecursoDatasetsRelacionados = obterConteudoRecursoDatasetsRelacionados(relacionamentosDatasetPrincipal, hashRecursosPorDataset);

    }

    private List<ResourceRelacionado> obterConteudoRecursoDatasetsRelacionados(List<ColunasRelacionadas> relacionamentosDatasetPrincipal, HashMap<String, Resource> hashRecursosPorDataset) {
        return relacionamentosDatasetPrincipal.stream()
            .map(
                colunaRelacionada -> {
                    var conteudoRecursoDatasetRelacionado = obterRecursoConteudo(hashRecursosPorDataset, colunaRelacionada.getNomeDataset());

                    return ResourceRelacionado.builder()
                        .nomeDataset(colunaRelacionada.getNomeDataset())
                        .nomeCampoRelacionado(colunaRelacionada.getNomeCampo())
                        .conteudoRecurso(conteudoRecursoDatasetRelacionado)
                        .build();
                }
            ).collect(Collectors.toList());
    }

    private String obterRecursoConteudo(HashMap<String, Resource> hashRecursosPorDataset, String nomeDataset) {
        var idRecursoDoDataset = hashRecursosPorDataset.get(nomeDataset).getId();
        return obterResourceService.obterRecursoConteudo(idRecursoDoDataset);
    }
}
