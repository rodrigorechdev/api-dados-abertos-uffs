package br.com.dadosabertosuffs.workflow.service;

import java.io.IOException;
import java.util.List;

public interface ObterDatasetService {
    
    public List<String> obterNomesDatasets() throws IOException, InterruptedException;

}
