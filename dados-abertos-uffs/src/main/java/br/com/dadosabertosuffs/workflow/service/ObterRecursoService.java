package br.com.dadosabertosuffs.workflow.service;

import java.io.IOException;
import java.util.List;

import br.com.dadosabertosuffs.entity.httpresponse.DatasetShowResponseResultResources;
import br.com.dadosabertosuffs.entity.httpresponse.ResourceResponse;

public interface ObterRecursoService {
    
    public List<DatasetShowResponseResultResources> obterRecursosDeDataset(String nomeRecurso) throws IOException, InterruptedException;
    public ResourceResponse obterRecursoConteudo(String idRecurso) throws IOException, InterruptedException;

}