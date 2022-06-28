package br.com.dadosabertosuffs.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.dadosabertosuffs.workflow.DiscenteWorkflow;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "*")
public class DadosAbertosController {
    
    @Autowired
    private final DiscenteWorkflow discenteWorkflow;
    
    @RequestMapping(value = "/discente", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<String>> obterDiscente() throws IOException, InterruptedException {
        var response = discenteWorkflow.obterNomesDatasets();
        return new ResponseEntity<List<String>>(response, HttpStatus.OK);
    }
}
