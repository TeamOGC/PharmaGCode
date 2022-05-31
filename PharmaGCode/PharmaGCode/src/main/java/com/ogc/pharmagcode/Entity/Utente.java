package com.ogc.pharmagcode.Entity;

public record Utente(int id_farmacista, int id_farmacia, String nome, String cognome, String email, String password) {
}
