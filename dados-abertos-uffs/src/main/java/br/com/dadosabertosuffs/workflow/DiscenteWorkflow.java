package br.com.dadosabertosuffs.workflow;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.dadosabertosuffs.workflow.activity.ObterCamposRecursoActivity;
import br.com.dadosabertosuffs.workflow.activity.ObterColunasRelacionadasActivity;
import br.com.dadosabertosuffs.workflow.activity.ObterConteudoRecursoActivity;
import br.com.dadosabertosuffs.workflow.activity.ObterIdRecursoPorDatasetActivity;
import br.com.dadosabertosuffs.workflow.activity.ObterNomesDatasetsActivity;
import lombok.RequiredArgsConstructor;
//TODO alterar @Service de tds as classes pra component?
@Service
@RequiredArgsConstructor
public class DiscenteWorkflow {
    
    @Autowired
    private final ObterNomesDatasetsActivity obterNomesDatasets;

    @Autowired
    private final ObterIdRecursoPorDatasetActivity obterIdRecursosPorDataset;
    
    @Autowired
    private final ObterCamposRecursoActivity obterCamposRecurso;

    @Autowired
    private final ObterColunasRelacionadasActivity obterColunasRelacionadas;
    
    @Autowired
    private final ObterConteudoRecursoActivity obterConteudoRecursoActivity;
    
    public List<String> obterNomesDatasets() throws IOException, InterruptedException {
        return obterNomesDatasets.execute();
    }

    public String obterDatasetConteudo(String datasetNome, String filtros, List<String> relacionamentos) throws IOException, InterruptedException {
        var nomesDatasets = obterNomesDatasets.execute();
        var hashRecursosPorDataset = obterIdRecursosPorDataset.execute(nomesDatasets);
        hashRecursosPorDataset = obterCamposRecurso.execute(hashRecursosPorDataset);

        var hashRelacionamentos = obterColunasRelacionadas.execute(hashRecursosPorDataset, relacionamentos);
        var conteudoRecurso = obterConteudoRecursoActivity.execute(datasetNome, hashRecursosPorDataset, hashRelacionamentos, filtros);
        
        return conteudoRecurso;
    }
}
