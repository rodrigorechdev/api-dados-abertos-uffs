package br.com.dadosabertosuffs.workflow.activity;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.dadosabertosuffs.workflow.service.impl.ObterDatasetServiceImpl;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ObterNomesDatasetsActivity {
    
    @Autowired
    private final ObterDatasetServiceImpl obterDatasetService;

    public List<String> execute() throws IOException, InterruptedException {
        return obterDatasetService.obterNomesDatasets();
    }

}
