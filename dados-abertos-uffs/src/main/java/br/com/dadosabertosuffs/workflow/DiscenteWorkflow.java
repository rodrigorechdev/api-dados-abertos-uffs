package br.com.dadosabertosuffs.workflow;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.dadosabertosuffs.cache.Cache;
import br.com.dadosabertosuffs.entity.dto.ResourceEstrutura;
import br.com.dadosabertosuffs.entity.httpresponse.ResourceResponse;
import br.com.dadosabertosuffs.workflow.activity.CriarObterDatasetConteudoResponse;
import br.com.dadosabertosuffs.workflow.activity.JsonArrayToListaDeRegistrosActivity;
import br.com.dadosabertosuffs.workflow.activity.ObterCamposRecursoActivity;
import br.com.dadosabertosuffs.workflow.activity.ObterColunasRelacionadasActivity;
import br.com.dadosabertosuffs.workflow.activity.ObterConteudoRecursoPrincipalActivity;
import br.com.dadosabertosuffs.workflow.activity.ObterConteudoRecursoRelacionadoActivity;
import br.com.dadosabertosuffs.workflow.activity.ObterIdRecursoPorDatasetActivity;
import br.com.dadosabertosuffs.workflow.activity.ObterNomesDatasetsActivity;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DiscenteWorkflow {
    
    @Autowired
    private final ObterNomesDatasetsActivity obterNomesDatasets;

    @Autowired
    private final ObterIdRecursoPorDatasetActivity obterIdRecursosPorDataset;
    
    @Autowired
    private final ObterCamposRecursoActivity obterCamposRecurso;

    @Autowired
    private final ObterColunasRelacionadasActivity obterColunasRelacionadas;
    
    @Autowired
    private final JsonArrayToListaDeRegistrosActivity jsonArrayToListaDeRegistrosActivity;

    @Autowired
    private final ObterConteudoRecursoPrincipalActivity obterConteudoRecursoPrincipalActivity;
    
    @Autowired
    private final ObterConteudoRecursoRelacionadoActivity obterConteudoRecursoRelacionadoActivity;

    @Autowired
    private final CriarObterDatasetConteudoResponse criarObterDatasetConteudoResponse;
    
    @Autowired
    private final Cache cache;
    
    public List<String> obterNomesDatasets() throws IOException, InterruptedException {
        return obterNomesDatasets.execute();
    }

    public ResourceResponse obterDatasetConteudo(String datasetNome, String filtros, List<String> relacionamentos) throws IOException, InterruptedException {
        var hashRecursoEstrutura = obterRecursoEstrutura(relacionamentos);

        ResourceResponse datasetPrincipalConteudo = obterConteudoRecursoPrincipalActivity.execute(datasetNome, hashRecursoEstrutura, filtros);
        var conteudoDatasetPrincipalSemRelacionamento = jsonArrayToListaDeRegistrosActivity.execute(datasetPrincipalConteudo);
        var conteudoDatasetPrincipalComRelacionamento = obterConteudoRecursoRelacionadoActivity.execute(conteudoDatasetPrincipalSemRelacionamento, datasetNome, hashRecursoEstrutura);

        return criarObterDatasetConteudoResponse.execute(datasetPrincipalConteudo, conteudoDatasetPrincipalComRelacionamento);
    }

    /**
     * Obtém hash de recursoEstrutura. A chave do Hash é o nome do dataset, o campo é a estrutura
     * que consiste no id, nome, campos e datasets relacionados.
     * @throws InterruptedException
     * @throws IOException
     */
    private HashMap<String, ResourceEstrutura> obterRecursoEstrutura(List<String> relacionamentos) throws IOException, InterruptedException {
        HashMap<String, ResourceEstrutura> hashRecursoEstruturaPorDataset;
        if(cache.atualizacaoNecessaria()) {
            var nomesDatasets = obterNomesDatasets.execute();
            hashRecursoEstruturaPorDataset = obterIdRecursosPorDataset.execute(nomesDatasets);
            obterCamposRecurso.execute(hashRecursoEstruturaPorDataset);

            cache.atualizarCache(hashRecursoEstruturaPorDataset);
        } else {
            hashRecursoEstruturaPorDataset = cache.getHashRecursoEstruturaPorDataset();
        }

        return obterColunasRelacionadas.execute(hashRecursoEstruturaPorDataset, relacionamentos);
    }
}
