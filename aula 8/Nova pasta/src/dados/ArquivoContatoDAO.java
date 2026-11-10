package dados;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class ArquivoContatoDAO {

    private final Path arquivo;

    public ArquivoContatoDAO(String nomeArquivo) {
        arquivo = Paths.get(nomeArquivo).toAbsolutePath().normalize();
    }

    public Path getArquivo() {
        return arquivo;
    }

    // Converte um contato para uma linha CSV.
    public String toCSV(Contato contato) {
        return escapar(contato.getNome()) + ";"
                + escapar(contato.getTelefone());
    }

    // Converte uma linha CSV em um contato.
    public Contato fromCSV(String linha) {
        List<String> partes = dividirCampos(linha);

        if (partes.size() != 2) {
            throw new IllegalArgumentException(
                    "Linha CSV inválida: " + linha);
        }

        return new Contato(
                desescapar(partes.get(0).trim()),
                desescapar(partes.get(1).trim())
        );
    }

    // Lê todos os contatos do arquivo.
    public List<Contato> leContatos() {
        List<Contato> contatos = new ArrayList<>();

        if (!Files.exists(arquivo)) {
            return contatos;
        }

        try {
            List<String> linhas = Files.readAllLines(
                    arquivo, StandardCharsets.UTF_8);

            for (String linha : linhas) {
                linha = linha.replace("\uFEFF", "");
                if (linha.isBlank() || ehCabecalho(linha)) {
                    continue;
                }
                contatos.add(fromCSV(linha));
            }

        } catch (IOException e) {
            throw new RuntimeException(
                    "Erro ao ler contatos do arquivo.", e);
        }

        return contatos;
    }

    // Salva todos os contatos, substituindo o conteúdo anterior.
    public void salvaContatos(List<Contato> contatos) {
        List<String> linhas = new ArrayList<>();

        for (Contato contato : contatos) {
            linhas.add(toCSV(contato));
        }

        try {
            Path diretorio = arquivo.getParent();

            if (diretorio != null) {
                Files.createDirectories(diretorio);
            }

            Files.write(
                    arquivo,
                    linhas,
                    StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING
            );

        } catch (IOException e) {
            throw new RuntimeException(
                    "Erro ao salvar contatos no arquivo.", e);
        }
    }

    private boolean ehCabecalho(String linha) {
        String normalizada = linha.replace("\uFEFF", "").trim().toLowerCase();
        return normalizada.equals("nome;telefone")
                || normalizada.equals("nome,telefone")
                || normalizada.startsWith("nome;")
                || normalizada.startsWith("nome,");
    }

    // Aceita CSV com ";" ou ",", respeitando campos escapados.
    private List<String> dividirCampos(String linha) {
        char separador = linha.indexOf(';') >= 0 ? ';' : ',';
        List<String> partes = new ArrayList<>();
        StringBuilder atual = new StringBuilder();
        boolean escape = false;

        for (int i = 0; i < linha.length(); i++) {
            char c = linha.charAt(i);

            if (escape) {
                atual.append(c);
                escape = false;
            } else if (c == '\\') {
                atual.append(c);
                escape = true;
            } else if (c == separador) {
                partes.add(atual.toString());
                atual.setLength(0);
            } else {
                atual.append(c);
            }
        }

        partes.add(atual.toString());
        return partes;
    }

    // Protege os caracteres especiais usados na separação CSV.
    private String escapar(String texto) {
        return texto.replace("\\", "\\\\")
                    .replace(";", "\\;")
                    .replace("\n", "\\n")
                    .replace("\r", "\\r");
    }

    private String desescapar(String texto) {
        StringBuilder resultado = new StringBuilder();
        boolean escape = false;

        for (int i = 0; i < texto.length(); i++) {
            char c = texto.charAt(i);

            if (escape) {
                switch (c) {
                    case 'n':
                        resultado.append('\n');
                        break;
                    case 'r':
                        resultado.append('\r');
                        break;
                    default:
                        resultado.append(c);
                }
                escape = false;
            } else if (c == '\\') {
                escape = true;
            } else {
                resultado.append(c);
            }
        }

        if (escape) {
            resultado.append('\\');
        }

        return resultado.toString();
    }
}