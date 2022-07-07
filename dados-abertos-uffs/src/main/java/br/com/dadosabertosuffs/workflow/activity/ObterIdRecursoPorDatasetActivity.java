package br.com.dadosabertosuffs.workflow.activity;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.dadosabertosuffs.entity.dto.Resource;
import br.com.dadosabertosuffs.entity.httpresponse.DatasetShowResponseResultResources;
import br.com.dadosabertosuffs.workflow.service.ObterResourceService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ObterIdRecursoPorDatasetActivity {
    
    @Autowired
    private final ObterResourceService obterResourceService;
    
    /**
     * Retorna hash onde a chave é o nome do dataset e o valor é um objeto com id e nome do recurso.
     * O método consulta a lista de recursos de cada dataset e diferencia o recurso com os dados
     * do recurso Dicionário considerando que o recurso de dicionário sempre terá a palavra
     * "dicionario" no nome do recurso.
     * @param nomesDatasets
     * @return 
     * @throws IOException
     * @throws InterruptedException
     */
    public HashMap<String, Resource> execute(List<String> nomesDatasets) throws IOException, InterruptedException {
        HashMap<String, Resource> hashRecurso = new HashMap<>();
        for(String nomeDataset : nomesDatasets) {
            var listRecursosDeDataset = obterResourceService.obterRecursosDeDataset(nomeDataset);
            var recurso = removeDicionario(listRecursosDeDataset).toResource();
            hashRecurso.put(nomeDataset, recurso);
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
