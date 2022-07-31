package br.com.dadosabertosuffs.workflow.service;

import java.util.Optional;

import br.com.dadosabertosuffs.entity.httpresponse.ResourceResponse;

public interface ObterResourceService {
        
    public ResourceResponse obterRecursoCampos(String idRecurso);
     
    public ResourceResponse obterRecursoConteudo(String idRecurso, String filtroChave, String filtroValor);

    public ResourceResponse obterRecursoConteudo(String idRecurso, Optional<String> filtroJsonOptional);

}
