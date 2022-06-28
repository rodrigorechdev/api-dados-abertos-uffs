package br.com.dadosabertosuffs.workflow;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.dadosabertosuffs.workflow.service.impl.ObterDatasetServiceImpl;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DiscenteWorkflow {
    
    @Autowired
    private final ObterDatasetServiceImpl obterDatasetServiceimpl;

    public List<String> obterNomesDatasets() throws IOException, InterruptedException {
        return obterDatasetServiceimpl.obterNomesDatasets();
    }

}
