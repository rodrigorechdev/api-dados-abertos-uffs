package br.com.dadosabertosuffs.workflow.activity;

import java.io.IOException;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.dadosabertosuffs.entity.httpresponse.DatasetShowResponseResultResources;
import br.com.dadosabertosuffs.entity.httpresponse.ResourceResponse;
import br.com.dadosabertosuffs.entity.httpresponse.ResourceResponseResult;
import br.com.dadosabertosuffs.workflow.service.impl.ObterResourceServiceImpl;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ObterRecursoConteudoActivity {
    
    @Autowired
    private final ObterResourceServiceImpl obterResourceService;

    public HashMap<String, ResourceResponseResult> execute(HashMap<String, DatasetShowResponseResultResources> hashIdRecursosPorDataset) throws IOException, InterruptedException {
        HashMap<String, ResourceResponseResult> hashRecursoConteudo = new HashMap<>();

        hashIdRecursosPorDataset.forEach((chave, valor) -> {
            var recursoConteudo = obterResourceService.obterRecursoConteudo(valor.getId());
            hashRecursoConteudo.put(chave, recursoConteudo);
        });

        return hashRecursoConteudo;
    }
}
