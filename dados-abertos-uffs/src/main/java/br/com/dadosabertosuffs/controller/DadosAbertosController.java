package br.com.dadosabertosuffs.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import br.com.dadosabertosuffs.workflow.DiscenteWorkflow;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "*")
public class DadosAbertosController {
    
    @Autowired
    private final DiscenteWorkflow discenteWorkflow;
    
    @RequestMapping(value = "/dataset", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<String>> obterDiscente() throws IOException, InterruptedException {
        var response = discenteWorkflow.obterNomesDatasets();
        return new ResponseEntity<List<String>>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/dataset/{datasetName}/resource", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> obterRecurso(@PathVariable String datasetName, @RequestHeader(required = false) String filtros, @RequestHeader(required = false) String relacionamentos) throws IOException, InterruptedException {
        var listRelacionamentos = (relacionamentos == null) ? null : formatarRelacionamentosCsv(relacionamentos);
        var conteudoDatasetPrincipalComRelacionamento = discenteWorkflow.obterDatasetConteudo(datasetName, filtros, listRelacionamentos);
        var response = new Gson().toJson(conteudoDatasetPrincipalComRelacionamento);

        return new ResponseEntity<String>(response, HttpStatus.OK);
    }

    public List<String> formatarRelacionamentosCsv(String relacionamentos) {
        return Arrays.asList(
            relacionamentos.trim().split(",")
        );
    }
}
