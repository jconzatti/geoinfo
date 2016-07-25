package com.geoinfo.test;

import com.geoinfo.entity.Pessoa;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MessageDigestTest {

    public static void main(String[] args) throws NoSuchAlgorithmException {
        String dsSenha = "080690";
        System.out.println("Senha: " + dsSenha);
        String dsSenhaMD5 = new BigInteger(1, MessageDigest.getInstance("MD5").digest(dsSenha.getBytes())).toString();
        System.out.println("Senha MD5: " + dsSenhaMD5);
        String dsSenhaSHA256 = new BigInteger(1, MessageDigest.getInstance("SHA-256").digest(dsSenha.getBytes())).toString();
        System.out.println("Senha SHA-256: " + dsSenhaSHA256);
        String dsSenhaSHA256MD5 = Pessoa.encriptDsSenha(dsSenha);
        System.out.println("Senha SHA-256 + MD5: " + dsSenhaSHA256MD5);
    }
}
