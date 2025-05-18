package org.example;

//dependencia pra importar no arquivo pom.xml
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.*;

//classe para importar o json (pra nao precisar digitar toda criacao de novo no sql)
public class CursoJsonImport {

    public static List<Curso> importarCursosDeJson(String caminho) {
        List<CursoJsonEntry> entradas;
        try (FileReader reader = new FileReader(caminho)) {
            JsonObject jsonObject = JsonParser.parseReader(reader).getAsJsonObject();
            JsonArray cursosArray = jsonObject.getAsJsonArray("cursos");

            Type listType = new TypeToken<List<CursoJsonEntry>>() {}.getType();
            entradas = new Gson().fromJson(cursosArray, listType);

        } catch (Exception e) {
            System.err.println("Erro ao ler JSON: " + e.getMessage());
            return Collections.emptyList();
        }

        //Mapear cursos por categoria (para evitar duplicatas)
        Map<String, Curso> mapaCursos = new HashMap<>();

        for (CursoJsonEntry entrada : entradas) {
            String nomeCurso = entrada.getCategoria();
            Curso curso = mapaCursos.computeIfAbsent(nomeCurso, k -> new Curso(nomeCurso, gerarCodigoCurso(k)));

            Disciplina disciplina = new Disciplina(entrada.getNome(), 30, curso);
            curso.getDisciplinas().add(disciplina);
        }

        return new ArrayList<>(mapaCursos.values());
    }

    private static String gerarCodigoCurso(String nome) {
        return nome.replaceAll("[^A-Za-z0-9]", "").substring(0, Math.min(10, nome.length())).toUpperCase();
    }
}

