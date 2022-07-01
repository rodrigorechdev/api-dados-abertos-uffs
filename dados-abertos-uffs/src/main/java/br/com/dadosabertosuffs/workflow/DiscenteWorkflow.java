package br.com.dadosabertosuffs.workflow;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.dadosabertosuffs.workflow.activity.ObterIdRecursoPorDatasetActivity;
import br.com.dadosabertosuffs.workflow.activity.ObterNomesDatasetsActivity;
import br.com.dadosabertosuffs.workflow.activity.ObterCamposRecursoActivity;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DiscenteWorkflow {
    
    @Autowired
    private final ObterNomesDatasetsActivity obterNomesDatasets;

    @Autowired
    private final ObterIdRecursoPorDatasetActivity obterIdRecursosPorDataset;
    
    @Autowired
    private final ObterCamposRecursoActivity obterCamposRecurso;
    
    public List<String> obterNomesDatasets() throws IOException, InterruptedException {
        return obterNomesDatasets.execute();
    }

    public List<String> obterDatasetConteudo(String dataset) throws IOException, InterruptedException {
        var nomesDatasets = obterNomesDatasets.execute();
        var hashRecursosPorDataset = obterIdRecursosPorDataset.execute(nomesDatasets);
        hashRecursosPorDataset = obterCamposRecurso.execute(hashRecursosPorDataset);
        //TODO descobrir colunas com nomes iguais do meu dataset e salvar num hash novo.
        //hash<string(nomeDataset),object(nomeDatasetAlvo, nomeCampo)
        return null;
    }
}
