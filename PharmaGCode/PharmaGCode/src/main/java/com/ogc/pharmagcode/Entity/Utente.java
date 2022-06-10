package com.ogc.pharmagcode.Entity;

import com.ogc.pharmagcode.Main;

import java.util.Objects;

public class Utente{
    private final String nome;
    private final String cognome;
    private final String email;

    public Utente(String nome, String cognome, String email, String password) {
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
    }

    public Utente(String nome, String cognome, String email) {
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
    }

    public String nome() {
        return nome;
    }

    public String cognome() {
        return cognome;
    }

    public String email() {
        return email;
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Utente) obj;
        return this.email.compareToIgnoreCase(that.email()) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome, cognome, email);
    }

    @Override
    public String toString() {
        return "Utente[" +
                "nome=" + nome + ", " +
                "cognome=" + cognome + ", " +
                "email=" + email + ", " +']';
    }

}
