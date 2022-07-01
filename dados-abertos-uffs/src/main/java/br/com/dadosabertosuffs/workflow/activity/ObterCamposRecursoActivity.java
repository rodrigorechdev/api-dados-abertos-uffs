package br.com.dadosabertosuffs.workflow.activity;

import java.io.IOException;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.dadosabertosuffs.entity.dto.Resource;
import br.com.dadosabertosuffs.entity.httpresponse.ResourceResponseResult;
import br.com.dadosabertosuffs.workflow.service.impl.ObterResourceServiceImpl;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ObterCamposRecursoActivity {
    
    @Autowired
    private final ObterResourceServiceImpl obterResourceService;

    public HashMap<String, Resource> execute(HashMap<String, Resource> hashIdRecursosPorDataset) throws IOException, InterruptedException {
        
        hashIdRecursosPorDataset.forEach((nomeDataset, resource) -> {
            var recursoConteudo = obterResourceService.obterRecursoConteudo(resource.getId());
            resource.setCampos(recursoConteudo.getFields());
        });

        return hashIdRecursosPorDataset;
    }
}
