package com.geoinfo.entity;

import com.geoinfo.util.IGroupable;
import com.geoinfo.util.IRepositorable;
import java.io.Serializable;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

@Entity
@SequenceGenerator(name = "genPessoa", sequenceName = "GENPESSOA")
public abstract class Pessoa implements Serializable, IGroupable, IRepositorable {
    @Id @GeneratedValue(generator = "genPessoa")
    private Long cdPessoa;
    @Column(length=60)
    private String cdExterno;
    private String dsPessoa;
    @Column(length=60)
    private String dsUsuario;
    @Column(length=50)
    private String dsSenha;
    @ManyToOne
    private PessoaMaster gerente;

    public Long getCdPessoa() {
        return cdPessoa;
    }

    public void setCdPessoa(Long cdPessoa) {
        this.cdPessoa = cdPessoa;
    }

    public String getCdExterno() {
        return cdExterno;
    }

    public void setCdExterno(String cdExterno) {
        this.cdExterno = cdExterno;
    }

    public String getDsPessoa() {
        return dsPessoa;
    }

    public void setDsPessoa(String dsPessoa) {
        this.dsPessoa = dsPessoa;
    }

    public String getDsUsuario() {
        return dsUsuario;
    }

    public void setDsUsuario(String dsUsuario) {
        this.dsUsuario = dsUsuario;
    }

    public String getDsSenha() {
        return dsSenha;
    }

    public void setDsSenha(String dsSenha) {
        this.dsSenha = dsSenha;
    }

    public PessoaMaster getGerente() {
        return gerente;
    }

    public void setGerente(PessoaMaster gerente) {
        this.gerente = gerente;
    }
    
    public Boolean getInGerente() {
        return getGerente().equals(this);
    }
    
    @Override
    public boolean equals(Object o){
        if(o != null){
            if(o.getClass() == this.getClass()){
                Pessoa pessoa = (Pessoa)o;
                return (this.cdPessoa.equals(pessoa.getCdPessoa()));
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 13 * hash + (this.cdPessoa.hashCode());
        return hash;
    }
    
    @Override
    public String toString(){
        return dsPessoa;
    }

    @Override
    public String getDsCodigo() {
        return this.getCdPessoa().toString();
    }
    
    public static String encriptDsSenha(String dsSenha) throws NoSuchAlgorithmException{
        return new BigInteger(1, MessageDigest.getInstance("MD5").digest(MessageDigest.getInstance("SHA-256").digest(dsSenha.getBytes()))).toString();
    }
    
}
