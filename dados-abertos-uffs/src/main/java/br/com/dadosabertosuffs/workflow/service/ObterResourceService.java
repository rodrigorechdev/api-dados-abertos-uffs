package br.com.dadosabertosuffs.workflow.service;

import br.com.dadosabertosuffs.entity.httpresponse.ResourceResponseResult;

public interface ObterResourceService {
        
    public ResourceResponseResult obterRecursoCampos(String idRecurso);
     
    public ResourceResponseResult obterRecursoConteudo(String idRecurso);

}
