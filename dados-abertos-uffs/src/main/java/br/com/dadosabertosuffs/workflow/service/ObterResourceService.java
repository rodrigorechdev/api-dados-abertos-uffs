package br.com.dadosabertosuffs.workflow.service;

import java.util.Optional;

import br.com.dadosabertosuffs.entity.httpresponse.ResourceResponseResult;

public interface ObterResourceService {
        
    public ResourceResponseResult obterRecursoCampos(String idRecurso);
     
    public String obterRecursoConteudo(String idRecurso, String filtroChave, String filtroValor);

    public String obterRecursoConteudo(String idRecurso, Optional<String> filtroJsonOptional);

}
