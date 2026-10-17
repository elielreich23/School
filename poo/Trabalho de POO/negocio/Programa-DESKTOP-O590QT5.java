package negocio;

import dados.*;
import dados.enums.NivelCurso;
import dados.enums.TipoAvaliacao;

import java.util.Scanner;

public class Programa {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Sistema sistema = new Sistema();
        int opcao = -1;

        while (opcao != 0) {
            mostrarMenu();
            opcao = lerInteiro(scanner);

            switch (opcao) {
                case 1:
                    cadastrarAluno(scanner, sistema);
                    break;
                case 2:
                    cadastrarProfessor(scanner, sistema);
                    break;
                case 3:
                    cadastrarCurso(scanner, sistema);
                    break;
                case 4:
                    adicionarModulo(scanner, sistema);
                    break;
                case 5:
                    cadastrarTurma(scanner, sistema);
                    break;
                case 6:
                    matricularAluno(scanner, sistema);
                    break;
                case 7:
                    cadastrarAvaliacao(scanner, sistema);
                    break;
                case 8:
                    registrarNota(scanner, sistema);
                    break;
                case 9:
                    cadastrarMensalidade(scanner, sistema);
                    break;
                case 10:
                    registrarPagamento(scanner, sistema);
                    break;
                case 11:
                    cadastrarEncontro(scanner, sistema);
                    break;
                case 12:
                    menuListar(scanner, sistema);
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida.");
                    break;
            }
            System.out.println();
        }

        scanner.close();
    }

    private static void mostrarMenu() {
        System.out.println("====================================");
        System.out.println("        ESCOLA DE IDIOMAS");
        System.out.println("====================================");
        System.out.println();
        System.out.println("1 - Cadastrar aluno");
        System.out.println("2 - Cadastrar professor");
        System.out.println("3 - Cadastrar curso");
        System.out.println("4 - Adicionar módulo ao curso");
        System.out.println("5 - Cadastrar turma");
        System.out.println("6 - Matricular aluno");
        System.out.println("7 - Cadastrar avaliação");
        System.out.println("8 - Registrar nota");
        System.out.println("9 - Cadastrar mensalidade");
        System.out.println("10 - Registrar pagamento");
        System.out.println("11 - Cadastrar encontro");
        System.out.println("12 - Listar dados");
        System.out.println("0 - Sair");
        System.out.println();
        System.out.print("Escolha uma opção: ");
    }

    private static void cadastrarAluno(Scanner scanner, Sistema sistema) {
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("CPF: ");
        String cpf = scanner.nextLine();
        System.out.print("E-mail: ");
        String email = scanner.nextLine();

        try {
            Aluno aluno = new Aluno(nome, cpf, email);
            sistema.cadastrarAluno(aluno);
            System.out.println("Aluno cadastrado com sucesso!");
        } catch (RegraNegocioException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void cadastrarProfessor(Scanner scanner, Sistema sistema) {
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("CPF: ");
        String cpf = scanner.nextLine();
        System.out.print("E-mail: ");
        String email = scanner.nextLine();

        try {
            Professor professor = new Professor(nome, cpf, email);
            sistema.cadastrarProfessor(professor);
            System.out.println("Professor cadastrado com sucesso!");
        } catch (RegraNegocioException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void cadastrarCurso(Scanner scanner, Sistema sistema) {
        System.out.print("Código: ");
        String codigo = scanner.nextLine();
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Idioma: ");
        String idioma = scanner.nextLine();

        System.out.println();
        System.out.println("1 - Básico");
        System.out.println("2 - Intermediário");
        System.out.println("3 - Avançado");
        System.out.println();
        System.out.print("Escolha o nível: ");
        int escolha = lerInteiro(scanner);

        NivelCurso nivel;
        if (escolha == 1) {
            nivel = NivelCurso.BASICO;
        } else if (escolha == 2) {
            nivel = NivelCurso.INTERMEDIARIO;
        } else if (escolha == 3) {
            nivel = NivelCurso.AVANCADO;
        } else {
            System.out.println("Nível inválido.");
            return;
        }

        try {
            Curso curso = new Curso(codigo, nome, idioma, nivel);
            sistema.cadastrarCurso(curso);
            System.out.println("Curso cadastrado com sucesso!");
        } catch (RegraNegocioException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void adicionarModulo(Scanner scanner, Sistema sistema) {
        System.out.print("Código do curso: ");
        String codigo = scanner.nextLine();
        Curso curso = sistema.buscarCurso(codigo);

        if (curso == null) {
            System.out.println("Curso não encontrado.");
            return;
        }

        System.out.print("Número do módulo: ");
        int numero = lerInteiro(scanner);
        System.out.print("Nome do módulo: ");
        String nome = scanner.nextLine();
        System.out.print("Carga horária: ");
        int cargaHoraria = lerInteiro(scanner);

        Modulo modulo = new Modulo(numero, nome, cargaHoraria);
        curso.adicionarModulo(modulo);
        System.out.println("Módulo adicionado com sucesso!");
    }

    private static void cadastrarTurma(Scanner scanner, Sistema sistema) {
        System.out.print("Código da turma: ");
        String codigoTurma = scanner.nextLine();
        System.out.print("Código do curso: ");
        String codigoCurso = scanner.nextLine();
        System.out.print("CPF do professor: ");
        String cpfProfessor = scanner.nextLine();
        System.out.print("Quantidade máxima de alunos: ");
        int quantidadeMaxima = lerInteiro(scanner);

        Curso curso = sistema.buscarCurso(codigoCurso);
        if (curso == null) {
            System.out.println("Curso não encontrado.");
            return;
        }

        Professor professor = sistema.buscarProfessor(cpfProfessor);
        if (professor == null) {
            System.out.println("Professor não encontrado.");
            return;
        }

        try {
            Turma turma = new Turma(codigoTurma, curso, professor, quantidadeMaxima);
            sistema.cadastrarTurma(turma);
            System.out.println("Turma cadastrada com sucesso!");
        } catch (RegraNegocioException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void matricularAluno(Scanner scanner, Sistema sistema) {
        System.out.print("CPF do aluno: ");
        String cpfAluno = scanner.nextLine();
        System.out.print("Código da turma: ");
        String codigoTurma = scanner.nextLine();
        System.out.print("Valor da mensalidade: ");
        double valor = lerDouble(scanner);

        Aluno aluno = sistema.buscarAluno(cpfAluno);
        if (aluno == null) {
            System.out.println("Aluno não encontrado.");
            return;
        }

        Turma turma = sistema.buscarTurma(codigoTurma);
        if (turma == null) {
            System.out.println("Turma não encontrada.");
            return;
        }

        try {
            Matricula matricula = new Matricula(aluno, turma, valor);
            sistema.cadastrarMatricula(matricula);
            System.out.println("Matrícula realizada com sucesso!");
        } catch (RegraNegocioException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void cadastrarAvaliacao(Scanner scanner, Sistema sistema) {
        System.out.print("Código do curso: ");
        String codigoCurso = scanner.nextLine();
        Curso curso = sistema.buscarCurso(codigoCurso);
        if (curso == null) {
            System.out.println("Curso não encontrado.");
            return;
        }

        System.out.print("Número do módulo: ");
        int numeroModulo = lerInteiro(scanner);
        Modulo modulo = curso.buscarModulo(numeroModulo);
        if (modulo == null) {
            System.out.println("Módulo não encontrado.");
            return;
        }

        System.out.print("Descrição da avaliação: ");
        String descricao = scanner.nextLine();
        System.out.print("Valor da avaliação: ");
        double valor = lerDouble(scanner);

        System.out.println();
        System.out.println("1 - Prova");
        System.out.println("2 - Trabalho");
        System.out.println("3 - Exercício");
        System.out.println();
        System.out.print("Escolha o tipo: ");
        int escolha = lerInteiro(scanner);

        TipoAvaliacao tipo;
        if (escolha == 1) {
            tipo = TipoAvaliacao.PROVA;
        } else if (escolha == 2) {
            tipo = TipoAvaliacao.TRABALHO;
        } else if (escolha == 3) {
            tipo = TipoAvaliacao.EXERCICIO;
        } else {
            System.out.println("Tipo inválido.");
            return;
        }

        try {
            Avaliacao avaliacao = new Avaliacao(descricao, valor, modulo, tipo);
            sistema.cadastrarAvaliacao(avaliacao);
            System.out.println("Avaliação cadastrada com sucesso!");
        } catch (RegraNegocioException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void registrarNota(Scanner scanner, Sistema sistema) {
        System.out.print("CPF do aluno: ");
        String cpfAluno = scanner.nextLine();
        Aluno aluno = sistema.buscarAluno(cpfAluno);
        if (aluno == null) {
            System.out.println("Aluno não encontrado.");
            return;
        }

        System.out.print("Descrição da avaliação: ");
        String descricao = scanner.nextLine();
        Avaliacao avaliacao = sistema.buscarAvaliacao(descricao);
        if (avaliacao == null) {
            System.out.println("Avaliação não encontrada.");
            return;
        }

        System.out.print("Nota: ");
        double valor = lerDouble(scanner);
        if (valor < 0 || valor > 10) {
            System.out.println("Nota inválida. Digite um valor entre 0 e 10.");
            return;
        }

        try {
            Nota nota = new Nota(aluno, avaliacao, valor);
            sistema.cadastrarNota(nota);
            System.out.println("Nota registrada com sucesso!");
        } catch (RegraNegocioException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void cadastrarMensalidade(Scanner scanner, Sistema sistema) {
        System.out.print("CPF do aluno da matrícula: ");
        String cpfAluno = scanner.nextLine();
        System.out.print("Código da turma da matrícula: ");
        String codigoTurma = scanner.nextLine();

        Matricula matricula = sistema.buscarMatricula(cpfAluno, codigoTurma);
        if (matricula == null) {
            System.out.println("Matrícula não encontrada.");
            return;
        }

        System.out.print("Número da mensalidade: ");
        int numero = lerInteiro(scanner);
        System.out.print("Valor: ");
        double valor = lerDouble(scanner);

        try {
            Mensalidade mensalidade = new Mensalidade(numero, valor, matricula);
            sistema.cadastrarMensalidade(mensalidade);
            System.out.println("Mensalidade cadastrada com sucesso!");
        } catch (RegraNegocioException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void registrarPagamento(Scanner scanner, Sistema sistema) {
        System.out.print("Número da mensalidade: ");
        int numero = lerInteiro(scanner);
        Mensalidade mensalidade = sistema.buscarMensalidade(numero);
        if (mensalidade == null) {
            System.out.println("Mensalidade não encontrada.");
            return;
        }

        System.out.print("Valor pago: ");
        double valor = lerDouble(scanner);

        System.out.println();
        System.out.println("1 - PIX");
        System.out.println("2 - CARTÃO");
        System.out.println("3 - DINHEIRO");
        System.out.println();
        System.out.print("Escolha a forma de pagamento: ");
        int escolha = lerInteiro(scanner);

        String forma;
        if (escolha == 1) {
            forma = "PIX";
        } else if (escolha == 2) {
            forma = "CARTAO";
        } else if (escolha == 3) {
            forma = "DINHEIRO";
        } else {
            System.out.println("Forma de pagamento inválida.");
            return;
        }

        try {
            Pagamento pagamento = new Pagamento(valor, forma);
            sistema.cadastrarPagamento(mensalidade, pagamento);
            System.out.println("Pagamento registrado com sucesso!");
            System.out.println("Mensalidade marcada como paga.");
        } catch (RegraNegocioException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void cadastrarEncontro(Scanner scanner, Sistema sistema) {
        System.out.print("Código do curso: ");
        String codigoCurso = scanner.nextLine();
        Curso curso = sistema.buscarCurso(codigoCurso);
        if (curso == null) {
            System.out.println("Curso não encontrado.");
            return;
        }

        System.out.print("Número do módulo: ");
        int numeroModulo = lerInteiro(scanner);
        Modulo modulo = curso.buscarModulo(numeroModulo);
        if (modulo == null) {
            System.out.println("Módulo não encontrado.");
            return;
        }

        System.out.print("Número do encontro: ");
        int numero = lerInteiro(scanner);
        System.out.print("Data: ");
        String data = scanner.nextLine();

        try {
            Encontro encontro = new Encontro(numero, modulo, data);
            sistema.cadastrarEncontro(encontro);
            System.out.println("Encontro cadastrado com sucesso!");
        } catch (RegraNegocioException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void menuListar(Scanner scanner, Sistema sistema) {
        int opcao = -1;

        while (opcao != 0) {
            System.out.println();
            System.out.println("===== LISTAR DADOS =====");
            System.out.println();
            System.out.println("1 - Listar alunos");
            System.out.println("2 - Listar professores");
            System.out.println("3 - Listar cursos");
            System.out.println("4 - Listar turmas");
            System.out.println("5 - Listar matrículas");
            System.out.println("6 - Listar avaliações");
            System.out.println("7 - Listar notas");
            System.out.println("8 - Listar mensalidades");
            System.out.println("9 - Listar pagamentos");
            System.out.println("10 - Listar encontros");
            System.out.println("0 - Voltar");
            System.out.println();
            System.out.print("Escolha uma opção: ");
            opcao = lerInteiro(scanner);

            switch (opcao) {
                case 1:
                    listarAlunos(sistema);
                    break;
                case 2:
                    listarProfessores(sistema);
                    break;
                case 3:
                    listarCursos(sistema);
                    break;
                case 4:
                    listarTurmas(sistema);
                    break;
                case 5:
                    listarMatriculas(sistema);
                    break;
                case 6:
                    listarAvaliacoes(sistema);
                    break;
                case 7:
                    listarNotas(sistema);
                    break;
                case 8:
                    listarMensalidades(sistema);
                    break;
                case 9:
                    listarPagamentos(sistema);
                    break;
                case 10:
                    listarEncontros(sistema);
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opção inválida.");
                    break;
            }
        }
    }

    private static void listarAlunos(Sistema sistema) {
        if (sistema.getAlunos().isEmpty()) {
            System.out.println("Nenhum aluno cadastrado.");
            return;
        }
        for (int i = 0; i < sistema.getAlunos().size(); i++) {
            System.out.println(sistema.getAlunos().get(i));
        }
    }

    private static void listarProfessores(Sistema sistema) {
        if (sistema.getProfessores().isEmpty()) {
            System.out.println("Nenhum professor cadastrado.");
            return;
        }
        for (int i = 0; i < sistema.getProfessores().size(); i++) {
            System.out.println(sistema.getProfessores().get(i));
        }
    }

    private static void listarCursos(Sistema sistema) {
        if (sistema.getCursos().isEmpty()) {
            System.out.println("Nenhum curso cadastrado.");
            return;
        }
        for (int i = 0; i < sistema.getCursos().size(); i++) {
            Curso curso = sistema.getCursos().get(i);
            System.out.println(curso);
            for (int j = 0; j < curso.getModulos().size(); j++) {
                System.out.println("  - " + curso.getModulos().get(j));
            }
        }
    }

    private static void listarTurmas(Sistema sistema) {
        if (sistema.getTurmas().isEmpty()) {
            System.out.println("Nenhuma turma cadastrada.");
            return;
        }
        for (int i = 0; i < sistema.getTurmas().size(); i++) {
            System.out.println(sistema.getTurmas().get(i));
        }
    }

    private static void listarMatriculas(Sistema sistema) {
        if (sistema.getMatriculas().isEmpty()) {
            System.out.println("Nenhuma matrícula cadastrada.");
            return;
        }
        for (int i = 0; i < sistema.getMatriculas().size(); i++) {
            System.out.println(sistema.getMatriculas().get(i));
        }
    }

    private static void listarAvaliacoes(Sistema sistema) {
        if (sistema.getAvaliacoes().isEmpty()) {
            System.out.println("Nenhuma avaliação cadastrada.");
            return;
        }
        for (int i = 0; i < sistema.getAvaliacoes().size(); i++) {
            System.out.println(sistema.getAvaliacoes().get(i));
        }
    }

    private static void listarNotas(Sistema sistema) {
        if (sistema.getNotas().isEmpty()) {
            System.out.println("Nenhuma nota cadastrada.");
            return;
        }
        for (int i = 0; i < sistema.getNotas().size(); i++) {
            System.out.println(sistema.getNotas().get(i));
        }
    }

    private static void listarMensalidades(Sistema sistema) {
        if (sistema.getMensalidades().isEmpty()) {
            System.out.println("Nenhuma mensalidade cadastrada.");
            return;
        }
        for (int i = 0; i < sistema.getMensalidades().size(); i++) {
            System.out.println(sistema.getMensalidades().get(i));
        }
    }

    private static void listarPagamentos(Sistema sistema) {
        if (sistema.getPagamentos().isEmpty()) {
            System.out.println("Nenhum pagamento cadastrado.");
            return;
        }
        for (int i = 0; i < sistema.getPagamentos().size(); i++) {
            System.out.println(sistema.getPagamentos().get(i));
        }
    }

    private static void listarEncontros(Sistema sistema) {
        if (sistema.getEncontros().isEmpty()) {
            System.out.println("Nenhum encontro cadastrado.");
            return;
        }
        for (int i = 0; i < sistema.getEncontros().size(); i++) {
            System.out.println(sistema.getEncontros().get(i));
        }
    }

    private static int lerInteiro(Scanner scanner) {
        String texto = scanner.nextLine();
        try {
            return Integer.parseInt(texto);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static double lerDouble(Scanner scanner) {
        String texto = scanner.nextLine();
        try {
            return Double.parseDouble(texto.replace(",", "."));
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
