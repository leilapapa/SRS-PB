package org.example;

//dependencia pra importar no arquivo pom.xml
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.*;

//classe para importar o json (pra nao precisar digitar toda criacao de novo no sql)
public class CursoJsonImport {
    public static void importarCursosParaBanco(String caminho) {
        List<CursoJsonEntry> entradas;
        try (FileReader reader = new FileReader(caminho)) {
            JsonObject jsonObject = JsonParser.parseReader(reader).getAsJsonObject();
            JsonArray cursosArray = jsonObject.getAsJsonArray("cursos");

            Type listType = new TypeToken<List<CursoJsonEntry>>() {}.getType();
            entradas = new Gson().fromJson(cursosArray, listType);
        } catch (Exception e) {
            System.err.println("Erro ao ler JSON: " + e.getMessage());
            return;
        }

        //Mapeia os cursos
        Map<String, Curso> mapaCursos = new HashMap<>();

        for (CursoJsonEntry entrada : entradas) {
            String nomeCurso = entrada.getCategoria();
            Curso curso = mapaCursos.computeIfAbsent(nomeCurso, nome -> {
                Curso c = new Curso(nome, gerarCodigoCurso(nome));

                //insere no banco e atualiza ID
                CursoCRUD.create(c);
                return c;
            });

            Disciplina disciplina = new Disciplina(entrada.getNome(), 30, curso);
            DisciplinaCRUD.create(disciplina);
            curso.getDisciplinas().add(disciplina);
        }
    }

    //gera codigo unico nos cursos
    private static String gerarCodigoCurso(String nome) {
        String limpo = nome.replaceAll("[^A-Za-z0-9]", "").toUpperCase();
        String hash = Integer.toHexString(nome.hashCode()).toUpperCase(); // ajuda a diferenciar
        return (limpo.length() > 6 ? limpo.substring(0, 6) : limpo) + "_" + hash.substring(0, 4);
    }
}