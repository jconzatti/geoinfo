package com.geoinfo.data;

import com.geoinfo.entity.Cidade;
import com.geoinfo.entity.Estado;
import com.geoinfo.entity.Localizacao;
import com.geoinfo.entity.LocalizacaoPK;
import com.geoinfo.entity.Pais;
import com.geoinfo.entity.Pessoa;
import com.geoinfo.entity.PessoaMaster;
import com.geoinfo.repository.CidadeRepository;
import com.geoinfo.repository.LocalizacaoRepository;
import com.geoinfo.factory.RepositoryFactory;
import com.geoinfo.repository.EstadoRepository;
import com.geoinfo.repository.PaisRepository;
import com.geoinfo.repository.PessoaRepository;
import com.geoinfo.util.EGeoInfoLogType;
import com.geoinfo.model.GeoInfoLogNode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.persistence.EntityManager;

public abstract class ImportDataGeoInfoCSVPessoa extends ImportDataGeoInfoCSV{
    private final Class<? extends Pessoa> classPessoa;
    private Pessoa pessoa;

    public ImportDataGeoInfoCSVPessoa(EntityManager entityManager, PessoaMaster gerente, Class<? extends Pessoa> classPessoa) {
        super(entityManager, gerente);
        this.pessoa = null;
        this.classPessoa = classPessoa;
    }

    @Override
    public boolean importar(long nrLinha, String dsLinha, boolean inUltima) {
        String[] listaPessoa = dsLinha.split(";", -1);
        if(this.getListaGeoInfoLogNode() != null){
            this.getListaGeoInfoLogNode().add(new GeoInfoLogNode(EGeoInfoLogType.LOG_INFO, "Importando linha (" + nrLinha + "): " + dsLinha, nrLinha));
        }
        
        if((listaPessoa[0].equalsIgnoreCase("C") && (this.classPessoa.getSimpleName().equalsIgnoreCase("Cliente")))
                ||(listaPessoa[0].equalsIgnoreCase("D") && (this.classPessoa.getSimpleName().equalsIgnoreCase("Vendedor")))
                ||(listaPessoa[0].equalsIgnoreCase("E") && (this.classPessoa.getSimpleName().equalsIgnoreCase("Estabelecimento")))){
            pessoa = null;
            if(listaPessoa.length == 3){
                String cdExterno = listaPessoa[1];
                if((cdExterno != null) && (!cdExterno.equalsIgnoreCase(""))){
                    String dsPessoa = listaPessoa[2];
                    if((dsPessoa != null) && (!dsPessoa.equalsIgnoreCase(""))){
                        boolean inInsert = false;
                        PessoaRepository pessoaRepository = (PessoaRepository) RepositoryFactory.createByRepositorable(this.classPessoa, this.getEntityManager());
                        pessoa = pessoaRepository.find(this.getGerente(), cdExterno);
                        if(pessoa == null){
                            try {
                                pessoa = classPessoa.newInstance();
                                pessoa.setCdExterno(cdExterno);
                                pessoa.setGerente(this.getGerente());
                                inInsert = true;
                            } catch (ReflectiveOperationException ex) {
                                if(this.getListaGeoInfoLogNode() != null){
                                    this.getListaGeoInfoLogNode().add(new GeoInfoLogNode(EGeoInfoLogType.LOG_WARN, 
                                            "Linha (" + nrLinha + "): Problema no carregamento de "+this.classPessoa.getSimpleName()+"! " + ex.getMessage(), nrLinha));
                                }
                            }
                        }
                        if(pessoa != null){
                            pessoa.setDsPessoa(dsPessoa);
                            if(inInsert)
                                pessoaRepository.insert(pessoa);
                            else
                                pessoaRepository.edit(pessoa);
                        }else{
                            if(this.getListaGeoInfoLogNode() != null){
                                this.getListaGeoInfoLogNode().add(new GeoInfoLogNode(EGeoInfoLogType.LOG_ERROR, "Linha (" + nrLinha + "): " + this.classPessoa.getSimpleName()+" não carregado(a)!", nrLinha));
                            }
                        }
                        return true;
                    }else{
                        if(this.getListaGeoInfoLogNode() != null){
                            this.getListaGeoInfoLogNode().add(new GeoInfoLogNode(EGeoInfoLogType.LOG_ERROR, 
                                    "Linha (" + nrLinha + "): O nome do(a) " + this.classPessoa.getSimpleName() + " (campo 3 obrigatório) está em branco ou nulo!", nrLinha));
                        }
                    }
                }else{
                    if(this.getListaGeoInfoLogNode() != null){
                        this.getListaGeoInfoLogNode().add(new GeoInfoLogNode(EGeoInfoLogType.LOG_ERROR, 
                                "Linha (" + nrLinha + "): O código do(a) " + this.classPessoa.getSimpleName() + " (campo 3 obrigatório) está em branco ou nulo!", nrLinha));
                    }
                }
            }else{
                if(this.getListaGeoInfoLogNode() != null){
                    this.getListaGeoInfoLogNode().add(new GeoInfoLogNode(EGeoInfoLogType.LOG_ERROR, 
                            "Linha (" + nrLinha + "): Número de campos no registro de "+this.classPessoa.getSimpleName()+" (P) diferente do esperado!", nrLinha));
                }
            }
        }else{
            if((listaPessoa[0].equalsIgnoreCase("LC") && (this.classPessoa.getSimpleName().equalsIgnoreCase("Cliente")))
                    ||(listaPessoa[0].equalsIgnoreCase("LD") && (this.classPessoa.getSimpleName().equalsIgnoreCase("Vendedor")))
                    ||(listaPessoa[0].equalsIgnoreCase("LE") && (this.classPessoa.getSimpleName().equalsIgnoreCase("Estabelecimento")))){
                if(pessoa != null){
                    if(listaPessoa.length == 11){
                        Pais pais = null;
                        Estado estado = null;
                        Cidade cidade = null;

                        if((listaPessoa[5] != null)&&(!listaPessoa[5].equalsIgnoreCase(""))){
                            PaisRepository paisRepository = new PaisRepository(this.getEntityManager());
                            int idPais = 2;
                            try{
                                idPais = Integer.parseInt(listaPessoa[1]);
                            }catch(NumberFormatException nfe){ 
                                if(this.getListaGeoInfoLogNode() != null){
                                    this.getListaGeoInfoLogNode().add(new GeoInfoLogNode(EGeoInfoLogType.LOG_WARN, 
                                            "Linha (" + nrLinha + "): Problema no tipo de localização de país ("+listaPessoa[1]+")! " + nfe.getMessage(), nrLinha));
                                }
                            }

                            switch(idPais){
                                case 0:
                                    Long cdBACENPais = (long) 0;
                                    try{
                                        cdBACENPais = Long.valueOf(listaPessoa[5]);
                                    }catch(NumberFormatException nfe){ 
                                        if(this.getListaGeoInfoLogNode() != null){
                                            this.getListaGeoInfoLogNode().add(new GeoInfoLogNode(EGeoInfoLogType.LOG_WARN, 
                                                    "Linha (" + nrLinha + "): Código BACEN de país (campo 6 com valor "+listaPessoa[5]+") inválido! " + nfe.getMessage(), nrLinha));
                                        }
                                    }

                                    if(cdBACENPais > 0){

                                        pais = paisRepository.findBACEN(cdBACENPais);
                                    }
                                    break;
                                case 1:
                                    String cdGMIPais = listaPessoa[5];
                                    pais = paisRepository.findGMI(cdGMIPais);
                                    break;
                                case 2:
                                    String dsPais = listaPessoa[5];
                                    pais = paisRepository.find(dsPais);
                                    break;
                                default:
                                    if(this.getListaGeoInfoLogNode() != null){
                                        this.getListaGeoInfoLogNode().add(new GeoInfoLogNode(EGeoInfoLogType.LOG_WARN, 
                                                "Linha (" + nrLinha + "): Valor (" + String.valueOf(idPais) + ") inválido para o tipo de localização de país!", nrLinha));
                                    }
                                    break;
                            }
                            if(pais != null){
                                if(this.getListaGeoInfoLogNode() != null){
                                    this.getListaGeoInfoLogNode().add(new GeoInfoLogNode(EGeoInfoLogType.LOG_INFO, 
                                            "Linha (" + nrLinha + "): País localizado: " + pais.getDsPais(), nrLinha));
                                }
                            }else{
                                if(this.getListaGeoInfoLogNode() != null){
                                    this.getListaGeoInfoLogNode().add(new GeoInfoLogNode(EGeoInfoLogType.LOG_WARN, 
                                            "Linha (" + nrLinha + "): País não localizado! " + this.classPessoa.getSimpleName() + " " + pessoa.getDsPessoa(), nrLinha));
                                }
                            }
                        }

                        if((listaPessoa[6] != null)&&(!listaPessoa[6].equalsIgnoreCase(""))){
                            EstadoRepository estadoRepository = new EstadoRepository(this.getEntityManager());
                            int idEstado = 2;
                            try{
                                idEstado = Integer.parseInt(listaPessoa[2]);
                            }catch(NumberFormatException nfe){ 
                                if(this.getListaGeoInfoLogNode() != null){
                                    this.getListaGeoInfoLogNode().add(new GeoInfoLogNode(EGeoInfoLogType.LOG_WARN, 
                                            "Linha (" + nrLinha + "): Problema no tipo de localização de estado ("+listaPessoa[2]+")! " + nfe.getMessage(), nrLinha));
                                }
                            }

                            switch(idEstado){
                                case 0:
                                    Long cdIBGEEstado = (long) 0;
                                    try{
                                        cdIBGEEstado = Long.valueOf(listaPessoa[6]);
                                    }catch(NumberFormatException nfe){ 
                                        if(this.getListaGeoInfoLogNode() != null){
                                            this.getListaGeoInfoLogNode().add(new GeoInfoLogNode(EGeoInfoLogType.LOG_WARN, 
                                                    "Linha (" + nrLinha + "): Código IBGE de estado (campo 7 com valor "+listaPessoa[6]+") inválido! " + nfe.getMessage(), nrLinha));
                                        }
                                    }

                                    if(cdIBGEEstado > 0){
                                        estado = estadoRepository.findIBGE(cdIBGEEstado);
                                    }
                                    break;
                                case 1:
                                    if(pais != null){
                                        String cdUFEstado = listaPessoa[6];
                                        estado = estadoRepository.findUF(pais, cdUFEstado);
                                    }else{
                                        if(this.getListaGeoInfoLogNode() != null){
                                            this.getListaGeoInfoLogNode().add(new GeoInfoLogNode(EGeoInfoLogType.LOG_WARN, 
                                                    "Linha (" + nrLinha + "): Impossível localizar estado pela UF sem ter localizado país!", nrLinha));
                                        }
                                    }
                                    break;
                                case 2:
                                    if(pais != null){
                                        String dsEstado = listaPessoa[6];
                                        estado = estadoRepository.find(pais, dsEstado);
                                    }else{
                                        if(this.getListaGeoInfoLogNode() != null){
                                            this.getListaGeoInfoLogNode().add(new GeoInfoLogNode(EGeoInfoLogType.LOG_WARN, 
                                                    "Linha (" + nrLinha + "): Impossível localizar estado pela descrição sem ter localizado país!", nrLinha));
                                        }
                                    }
                                    break;
                                default:
                                    if(this.getListaGeoInfoLogNode() != null){
                                        this.getListaGeoInfoLogNode().add(new GeoInfoLogNode(EGeoInfoLogType.LOG_WARN, 
                                                "Linha (" + nrLinha + "): Valor (" + String.valueOf(idEstado) + ") inválido para o tipo de localização de estado!", nrLinha));
                                    }
                                    break;
                            }
                            if(estado != null){
                                if(this.getListaGeoInfoLogNode() != null){
                                    this.getListaGeoInfoLogNode().add(new GeoInfoLogNode(EGeoInfoLogType.LOG_INFO, 
                                            "Linha (" + nrLinha + "): Estado localizado: " + estado.getDsEstado()));
                                }
                            }else{
                                if(this.getListaGeoInfoLogNode() != null){
                                    this.getListaGeoInfoLogNode().add(new GeoInfoLogNode(EGeoInfoLogType.LOG_WARN, 
                                            "Linha (" + nrLinha + "): Estado não localizado! " + this.classPessoa.getSimpleName() + " " + pessoa.getDsPessoa(), nrLinha));
                                }
                            }
                        }

                        if((listaPessoa[7] != null)&&(!listaPessoa[7].equalsIgnoreCase(""))){
                            CidadeRepository cidadeRepository = new CidadeRepository(this.getEntityManager());
                            int idCidade = 1;
                            try{
                                idCidade = Integer.parseInt(listaPessoa[3]);
                            }catch(NumberFormatException nfe){ 
                                if(this.getListaGeoInfoLogNode() != null){
                                    this.getListaGeoInfoLogNode().add(new GeoInfoLogNode(EGeoInfoLogType.LOG_WARN, 
                                            "Linha (" + nrLinha + "): Problema no tipo de localização de cidade ("+listaPessoa[3]+")! " + nfe.getMessage(), nrLinha));
                                }
                            }

                            switch(idCidade){
                                case 0:
                                    Long cdIBGECidade = (long) 0;
                                    try{
                                        cdIBGECidade = Long.valueOf(listaPessoa[7]);
                                    }catch(NumberFormatException nfe){
                                        if(this.getListaGeoInfoLogNode() != null){
                                            this.getListaGeoInfoLogNode().add(new GeoInfoLogNode(EGeoInfoLogType.LOG_WARN, 
                                                    "Linha (" + nrLinha + "): Código IBGE de cidade (campo 8 com valor "+listaPessoa[7]+") inválido! " + nfe.getMessage(), nrLinha));
                                        }
                                    }

                                    if(cdIBGECidade > 0){
                                        cidade = cidadeRepository.findIBGE(cdIBGECidade);
                                    }
                                    break;
                                case 1:
                                    if(estado != null){
                                        String dsCidade = listaPessoa[7];
                                        cidade = cidadeRepository.find(estado, dsCidade);
                                    }else{
                                        if(this.getListaGeoInfoLogNode() != null){
                                            this.getListaGeoInfoLogNode().add(new GeoInfoLogNode(EGeoInfoLogType.LOG_WARN, 
                                                    "Linha (" + nrLinha + "): Impossível localizar cidade pela descrição sem ter localizado estado!", nrLinha));
                                        }
                                    }
                                    break;
                                default:
                                    if(this.getListaGeoInfoLogNode() != null){
                                        this.getListaGeoInfoLogNode().add(new GeoInfoLogNode(EGeoInfoLogType.LOG_ERROR, 
                                                "Linha (" + nrLinha + "): Valor (" + String.valueOf(idCidade) + ") inválido para o tipo de localização de cidade!", nrLinha));
                                    }
                                    break;
                            }
                            if(cidade != null){
                                if(this.getListaGeoInfoLogNode() != null){
                                    this.getListaGeoInfoLogNode().add(new GeoInfoLogNode(EGeoInfoLogType.LOG_INFO, 
                                            "Linha (" + nrLinha + "): Cidade localizada: " + cidade.getDsCidade()));
                                }
                            }else{
                                if(this.getListaGeoInfoLogNode() != null){
                                    this.getListaGeoInfoLogNode().add(new GeoInfoLogNode(EGeoInfoLogType.LOG_WARN, 
                                            "Linha (" + nrLinha + "): Cidade não localizada! " + this.classPessoa.getSimpleName() + " " + pessoa.getDsPessoa(), nrLinha));
                                }
                            }
                        }

                        if(cidade != null){
                            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                            Date dtLocalizacao = null;
                            try {
                                String strDtLocalizacao = listaPessoa[4];
                                if(strDtLocalizacao.isEmpty())
                                    strDtLocalizacao = "30/12/1899";
                                dtLocalizacao = sdf.parse(strDtLocalizacao);
                            } catch (ParseException ex) { }

                            if(dtLocalizacao != null){
                                boolean inInsert = false;
                                LocalizacaoRepository localizacaoRepository = new LocalizacaoRepository(this.getEntityManager());
                                LocalizacaoPK localizacaoPK = new LocalizacaoPK();
                                localizacaoPK.setDtLocalizacao(dtLocalizacao);
                                localizacaoPK.setPessoa(pessoa);
                                localizacaoPK.setCidade(cidade);

                                Localizacao localizacao = localizacaoRepository.find(localizacaoPK);
                                if(localizacao == null){
                                    localizacao = new Localizacao();
                                    localizacao.setLocalizacaoPK(localizacaoPK);
                                    inInsert = true;
                                }

                                localizacao.setDsBairro(listaPessoa[8]);
                                localizacao.setDsEndereco(listaPessoa[9]);
                                localizacao.setDsNumero(listaPessoa[10]);

                                if(inInsert)
                                    localizacaoRepository.insert(localizacao);
                                else
                                    localizacaoRepository.edit(localizacao);
                                return true;
                            }else{
                                if(this.getListaGeoInfoLogNode() != null){
                                    this.getListaGeoInfoLogNode().add(new GeoInfoLogNode(EGeoInfoLogType.LOG_ERROR, 
                                            "Linha (" + nrLinha + "): Data de localização (" + listaPessoa[4] + ") de " + 
                                                    this.classPessoa.getSimpleName() + " " + 
                                                    pessoa.getDsPessoa() + " não encontada!", nrLinha));
                                }
                            }
                        }else{
                            if(this.getListaGeoInfoLogNode() != null){
                                this.getListaGeoInfoLogNode().add(new GeoInfoLogNode(EGeoInfoLogType.LOG_ERROR, 
                                        "Linha (" + nrLinha + "): Cidade (" + listaPessoa[7] + ") de " + 
                                                this.classPessoa.getSimpleName() + " " + 
                                                pessoa.getDsPessoa() + " não encontada!", nrLinha));
                            }
                        }
                    }else{
                        if(this.getListaGeoInfoLogNode() != null){
                            this.getListaGeoInfoLogNode().add(new GeoInfoLogNode(EGeoInfoLogType.LOG_ERROR, 
                                    "Linha (" + nrLinha + "): Número de campos no registro de localização (L) diferente do esperado!", nrLinha));
                        }
                    }
                }else{
                    if(this.getListaGeoInfoLogNode() != null){
                        this.getListaGeoInfoLogNode().add(new GeoInfoLogNode(EGeoInfoLogType.LOG_ERROR, 
                                "Linha (" + nrLinha + "): Falta o registro de " + this.classPessoa.getSimpleName() + " para o registro localização!", nrLinha));
                    }
                }
            }else{
                if(this.getListaGeoInfoLogNode() != null){
                    this.getListaGeoInfoLogNode().add(new GeoInfoLogNode(EGeoInfoLogType.LOG_ERROR, 
                            "Linha (" + nrLinha + "): Tipo de registro (campo 1) inválido para o arquivo de " + this.classPessoa.getSimpleName() + "!", nrLinha));
                }
            }
        }
        return false;
    }
    
}
