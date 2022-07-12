package br.com.dadosabertosuffs.workflow.activity;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.dadosabertosuffs.entity.dto.ResourceEstrutura;
import br.com.dadosabertosuffs.entity.httpresponse.DatasetShowResponseResultResources;
import br.com.dadosabertosuffs.workflow.service.ObterDatasetService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ObterIdRecursoPorDatasetActivity {
    
    @Autowired
    private final ObterDatasetService obterDatasetService;
    
    /**
     * Obtém id e nome do recurso pelo nome do dataset. Desconsidera o recurso "dicionário"
     * @param nomesDatasets
     * @return 
     * @throws IOException
     * @throws InterruptedException
     */
    public HashMap<String, ResourceEstrutura> execute(List<String> nomesDatasets) throws IOException, InterruptedException {
        HashMap<String, ResourceEstrutura> hashRecurso = new HashMap<>();
        for(String nomeDataset : nomesDatasets) {
            var listRecursosDeDataset = obterDatasetService.obterRecursosDeDataset(nomeDataset);
            var recursoEstrutura = removeDicionario(listRecursosDeDataset).toResourceEstrutura();
            hashRecurso.put(nomeDataset, recursoEstrutura);
        }

        return hashRecurso;
    }

    private DatasetShowResponseResultResources removeDicionario(List<DatasetShowResponseResultResources> listRecursosDeDataset) {
        return listRecursosDeDataset.stream()
        .filter(recursos -> !recursos.getName().toLowerCase().contains("dicionario"))
        .findAny()
        .orElseThrow();
    }
}
