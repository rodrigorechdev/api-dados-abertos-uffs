package br.com.dadosabertosuffs.workflow.service;

import java.io.IOException;
import java.util.List;

import br.com.dadosabertosuffs.entity.httpresponse.DatasetShowResponseResultResources;

public interface ObterDatasetService {
    
    public List<String> obterNomesDatasets() throws IOException, InterruptedException;

    public List<DatasetShowResponseResultResources> obterRecursosDeDataset(String nomeDataset) throws IOException, InterruptedException;
}
