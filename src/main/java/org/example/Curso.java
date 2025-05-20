package org.example;

import java.util.*;

public final class Curso {
    private int id;
    private String nome;
    private String codigo;
    private List<Disciplina> disciplinas;

    public Curso(String nome, String codigo) {
        this.nome = nome;
        this.codigo = codigo;
        this.disciplinas = new ArrayList<>();
    }

    //construtor adicional para carregar do banco de dados
    public Curso(int id, String nome, String codigo) {
        this.id = id;
        this.nome = nome;
        this.codigo = codigo;
        this.disciplinas = new ArrayList<>();
    }

    //getter setter

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public List<Disciplina> getDisciplinas() {
        return disciplinas;
    }

    public void setDisciplinas(List<Disciplina> disciplinas) {
        this.disciplinas = disciplinas;
    }


    public List<Disciplina> listarDisciplinas() {
        return disciplinas;
    }

    @Override
    public String toString() {
        return "Curso{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", codigo='" + codigo + '\'' +
                '}';
    }
}