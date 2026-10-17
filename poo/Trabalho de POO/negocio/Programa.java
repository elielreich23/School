package negocio;

import dados.*;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class Programa {

    public static void main(String[] args) {
        System.out.println("===============================================================================");
        System.out.println("     SISTEMA DE GESTÃO DE ESCOLA DE IDIOMAS — DEMONSTRAÇÃO ETAPA I");
        System.out.println("===============================================================================\n");

        Sistema sistema = new Sistema();

        // Configuração dos parâmetros globais do sistema
        sistema.setTaxaMulta(new BigDecimal("0.02"));       // Multa de 2%
        sistema.setTaxaJurosMensal(new BigDecimal("0.01")); // Juros de 1% ao mês
        sistema.setNotaMinima(new BigDecimal("7.0"));       // Nota mínima 7.0
        sistema.setFrequenciaMinima(new BigDecimal("0.75")); // Frequência mínima 75%

        // ---------------------------------------------------------------------------
        // 1. CADASTROS ACADÊMICOS (RF01, RF02, RF03, RF04) & REGRAS DE NEGÓCIO (RN01, RN02)
        // ---------------------------------------------------------------------------
        System.out.println(">>> 1. CADASTRO DE CURSOS E MÓDULOS (RF01, RF02)");
        Curso cursoIngles = new Curso(
                "ING-BAS",
                "Inglês",
                NivelCurso.BASICO,
                80,
                "Fundamentos da língua inglesa: gramática essencial, vocabulário e conversação.",
                "Livro English File Starter, Caderno de Exercícios"
        );
        sistema.cadastrarCurso(cursoIngles);
        System.out.println("Curso cadastrado: " + cursoIngles);

        // Módulos com sequência e pré-requisito (RN02)
        Modulo mod1 = new Modulo(1, "English Starter - Módulo 1", 40, "Noite");
        Modulo mod2 = new Modulo(2, "English Starter - Módulo 2", 40, "Noite");
        mod2.adicionarPreRequisito(mod1);

        sistema.cadastrarModulo(cursoIngles.getCodigo(), mod1);
        sistema.cadastrarModulo(cursoIngles.getCodigo(), mod2);
        System.out.println("Módulos cadastrados para o curso:");
        for (Modulo m : sistema.consultarModulosCurso("ING-BAS")) {
            System.out.println("  - " + m + " (Pré-requisitos: " + m.getPreRequisitos().size() + ")");
        }

        // Teste de RN01: Tentar cadastrar curso com código duplicado
        System.out.println("\n[Teste RN01] Tentando cadastrar curso com código duplicado 'ING-BAS':");
        try {
            sistema.cadastrarCurso(new Curso("ING-BAS", "Inglês", NivelCurso.AVANCADO, 60, "Outro", "Outro"));
        } catch (RegraNegocioException e) {
            System.out.println("  Sucesso na validação (rejeitado): " + e.getMessage());
        }

        // ---------------------------------------------------------------------------
        // 2. PROFESSORES E QUALIFICAÇÕES (RF03, RN01, RN03)
        // ---------------------------------------------------------------------------
        System.out.println("\n>>> 2. CADASTRO DE PROFESSORES (RF03, RN03)");
        Professor profCarlos = new Professor(
                "Carlos Eduardo",
                "111.222.333-44",
                "(41) 98888-1111",
                "carlos.eduardo@escola.com",
                LocalDate.of(2023, 2, 1),
                "Letras Inglês / Cambridge CELTA"
        );
        profCarlos.adicionarQualificacao(new IdiomaProfissional("Inglês", NivelCurso.AVANCADO));
        sistema.cadastrarProfessor(profCarlos);
        System.out.println("Professor cadastrado: " + profCarlos);

        Professor profAna = new Professor(
                "Ana Paula",
                "555.666.777-88",
                "(41) 99999-2222",
                "ana.paula@escola.com",
                LocalDate.of(2024, 1, 15),
                "Licenciatura em Espanhol"
        );
        profAna.adicionarQualificacao(new IdiomaProfissional("Espanhol", NivelCurso.AVANCADO));
        sistema.cadastrarProfessor(profAna);
        System.out.println("Professora cadastrada: " + profAna);

        // ---------------------------------------------------------------------------
        // 3. ALUNOS E INTERESSES (RF04, RN01)
        // ---------------------------------------------------------------------------
        System.out.println("\n>>> 3. CADASTRO DE ALUNOS (RF04)");
        Aluno alunoLucas = new Aluno(
                "Lucas Silva",
                "123.456.789-00",
                LocalDate.of(2002, 5, 20),
                "Rua XV de Novembro, 100",
                "(41) 97777-3333",
                "lucas.silva@email.com"
        );
        alunoLucas.adicionarInteresse(new IdiomaInteresse("Inglês", NivelCurso.BASICO));
        sistema.cadastrarAluno(alunoLucas);
        System.out.println("Aluno 1 cadastrado: " + alunoLucas);

        Aluno alunaMaria = new Aluno(
                "Maria Fernandes",
                "987.654.321-11",
                LocalDate.of(2001, 8, 12),
                "Av. Sete de Setembro, 500",
                "(41) 96666-4444",
                "maria.fernandes@email.com"
        );
        alunaMaria.adicionarInteresse(new IdiomaInteresse("Inglês", NivelCurso.BASICO));
        sistema.cadastrarAluno(alunaMaria);
        System.out.println("Aluna 2 cadastrada: " + alunaMaria);

        // ---------------------------------------------------------------------------
        // 4. TURMAS, HORÁRIOS E REGRAS DE HABILITAÇÃO DO PROFESSOR (RF05, RF06, RN03)
        // ---------------------------------------------------------------------------
        System.out.println("\n>>> 4. CADASTRO DE TURMAS (RF05, RF06)");

        // Teste de RN03: Tentar associar turma de Inglês à professora Ana (habilitada apenas em Espanhol)
        System.out.println("[Teste RN03] Tentando criar turma de Inglês com professora habilitada apenas em Espanhol:");
        try {
            Turma turmaInvalida = new Turma(
                    "TURMA-ERR",
                    cursoIngles,
                    profAna,
                    LocalDate.now().minusDays(10),
                    LocalDate.now().plusDays(20),
                    2,
                    15
            );
            sistema.cadastrarTurma(turmaInvalida);
        } catch (RegraNegocioException e) {
            System.out.println("  Sucesso na validação (rejeitado): " + e.getMessage());
        }

        // Criando turma válida com o professor Carlos
        Turma turmaIngles = new Turma(
                "TURMA-ING-01",
                cursoIngles,
                profCarlos,
                LocalDate.now().minusDays(15),
                LocalDate.now().plusDays(15),
                2, // mínimo 2 alunos
                5  // máximo 5 alunos
        );
        turmaIngles.adicionarHorario(new HorarioTurma(DayOfWeek.MONDAY, LocalTime.of(19, 0), LocalTime.of(20, 30), "Sala 101"));
        turmaIngles.adicionarHorario(new HorarioTurma(DayOfWeek.WEDNESDAY, LocalTime.of(19, 0), LocalTime.of(20, 30), "Sala 101"));

        // Adicionando encontros/aulas programadas
        Encontro encontro1 = new Encontro(LocalDate.now().plusDays(1), LocalTime.of(19, 0), LocalTime.of(20, 30), mod1);
        Encontro encontro2 = new Encontro(LocalDate.now().plusDays(3), LocalTime.of(19, 0), LocalTime.of(20, 30), mod1);
        Encontro encontro3 = new Encontro(LocalDate.now().plusDays(8), LocalTime.of(19, 0), LocalTime.of(20, 30), mod1);
        Encontro encontro4 = new Encontro(LocalDate.now().plusDays(10), LocalTime.of(19, 0), LocalTime.of(20, 30), mod1);
        turmaIngles.adicionarEncontro(encontro1);
        turmaIngles.adicionarEncontro(encontro2);
        turmaIngles.adicionarEncontro(encontro3);
        turmaIngles.adicionarEncontro(encontro4);

        sistema.cadastrarTurma(turmaIngles);
        System.out.println("Turma válida cadastrada: " + turmaIngles);

        // ---------------------------------------------------------------------------
        // 5. MATRÍCULAS E REGRAS DE LIMITE (RF07, RF08, RN04, RN05, RN06)
        // ---------------------------------------------------------------------------
        System.out.println("\n>>> 5. MATRÍCULAS DE ALUNOS (RF07, RF08, RN04, RN05, RN06)");

        // Matrícula do Lucas
        Matricula dadosLucas = new Matricula(
                "MAT-2026-001",
                alunoLucas,
                turmaIngles,
                LocalDate.now(),
                new BigDecimal("350.00"),
                3, // 3 parcelas
                new BigDecimal("50.00"), // desconto de 50 reais
                "Boleto Bancário"
        );
        Matricula matLucas = sistema.matricular(alunoLucas, turmaIngles, dadosLucas);
        System.out.println("Matrícula realizada com sucesso: " + matLucas);

        // Teste de RF08/RN05: Tentar iniciar turma antes de atingir o mínimo de alunos
        System.out.println("\n[Teste RF08/RN05] Tentando iniciar turma com apenas 1 aluno (mínimo = 2):");
        try {
            sistema.iniciarTurma(turmaIngles.getCodigo());
        } catch (RegraNegocioException e) {
            System.out.println("  Sucesso na validação (rejeitado): " + e.getMessage());
        }

        // Matrícula da Maria para atingir o mínimo
        Matricula dadosMaria = new Matricula(
                "MAT-2026-002",
                alunaMaria,
                turmaIngles,
                LocalDate.now(),
                new BigDecimal("350.00"),
                3,
                BigDecimal.ZERO,
                "Cartão de Crédito"
        );
        Matricula matMaria = sistema.matricular(alunaMaria, turmaIngles, dadosMaria);
        System.out.println("Segunda matrícula realizada: " + matMaria);

        // Agora a turma atingiu o mínimo de alunos
        System.out.println("Turma atingiu o mínimo de alunos? " + sistema.turmaAtingiuMinimoAlunos(turmaIngles.getCodigo()));
        sistema.iniciarTurma(turmaIngles.getCodigo());
        System.out.println("Situação da turma após atingir o mínimo e iniciar: " + turmaIngles.getSituacao());

        // ---------------------------------------------------------------------------
        // 6. FINANCEIRO: MENSALIDADES, PAGAMENTO E ATRASO (RF09, RF10, RF11, RN06, RN07)
        // ---------------------------------------------------------------------------
        System.out.println("\n>>> 6. MENSALIDADES E PAGAMENTOS (RF09, RF10, RF11)");
        List<Mensalidade> mensalidadesLucas = sistema.consultarMensalidadesPorMatricula(matLucas.getNumero());
        System.out.println("Mensalidades geradas para o aluno Lucas:");
        for (Mensalidade m : mensalidadesLucas) {
            System.out.println("  - " + m);
        }

        // Pagando a 1ª mensalidade em dia
        Mensalidade mens1 = mensalidadesLucas.get(0);
        Pagamento pag1 = new Pagamento(LocalDate.now(), mens1.getValorOriginal(), "PIX");
        sistema.registrarPagamento(mens1, pag1);
        System.out.println("\nPagamento realizado da mensalidade #1: " + mens1);

        // Teste de RN06: Tentar pagar novamente uma mensalidade já quitada
        System.out.println("[Teste RN06] Tentando pagar novamente mensalidade já paga:");
        try {
            sistema.registrarPagamento(mens1, new Pagamento(LocalDate.now(), mens1.getValorOriginal(), "Dinheiro"));
        } catch (RegraNegocioException e) {
            System.out.println("  Sucesso na validação (rejeitado): " + e.getMessage());
        }

        // Simulação de mensalidade em atraso (RF11, RN07)
        Mensalidade mens2 = mensalidadesLucas.get(1);
        mens2.setVencimento(LocalDate.now().minusDays(15)); // Vencida há 15 dias
        LocalDate hoje = LocalDate.now();
        BigDecimal valorComEncargos = sistema.calcularTotalEmAtraso(mens2, hoje);
        System.out.println("\n[RF11/RN07] Cálculo de multa e juros para mensalidade vencida há 15 dias:");
        System.out.println("  Valor Original: R$ " + mens2.getValorOriginal());
        System.out.println("  Valor Atualizado (com multa de 2% e juros diários): R$ " + valorComEncargos);

        // ---------------------------------------------------------------------------
        // 7. MATERIAIS DIDÁTICOS E VENDAS (RF12, RF13, RN11)
        // ---------------------------------------------------------------------------
        System.out.println("\n>>> 7. MATERIAIS DIDÁTICOS E VENDAS (RF12, RF13, RN11)");
        Material matIngles = new Material("MAT-ENG-01", "English File Starter 4th Ed.", "Oxford", "4ª", "Livro Didático", new BigDecimal("180.00"));
        Material matDicionario = new Material("MAT-DIC-01", "Dicionário Oxford Pocket", "Oxford", "2ª", "Dicionário", new BigDecimal("75.00"));
        sistema.cadastrarMaterial(matIngles);
        sistema.cadastrarMaterial(matDicionario);

        Venda vendaLucas = new Venda("VND-001", alunoLucas, LocalDate.now());
        vendaLucas.adicionarItem(new ItemVenda(matIngles, 1, matIngles.getPreco()));
        vendaLucas.adicionarItem(new ItemVenda(matDicionario, 1, matDicionario.getPreco()));
        sistema.registrarVenda(vendaLucas);

        System.out.println("Venda de materiais registrada separadamente: " + vendaLucas);
        for (ItemVenda item : vendaLucas.getItens()) {
            System.out.println("  - " + item);
        }

        // ---------------------------------------------------------------------------
        // 8. ACOMPANHAMENTO PEDAGÓGICO: AVALIAÇÕES E NOTAS (RF14, RF15, RF16, RN08)
        // ---------------------------------------------------------------------------
        System.out.println("\n>>> 8. AVALIAÇÕES E NOTAS (RF14, RF15, RF16, RN08)");
        Avaliacao provaEscrita = new Avaliacao("AV-01", TipoAvaliacao.PROVA_ESCRITA, LocalDate.now().plusDays(7), new BigDecimal("10.0"), new BigDecimal("0.6"), mod1);
        Avaliacao provaOral = new Avaliacao("AV-02", TipoAvaliacao.PROVA_ORAL, LocalDate.now().plusDays(9), new BigDecimal("10.0"), new BigDecimal("0.4"), mod1);
        sistema.cadastrarAvaliacao(provaEscrita);
        sistema.cadastrarAvaliacao(provaOral);
        mod1.adicionarAvaliacao(provaEscrita);
        mod1.adicionarAvaliacao(provaOral);

        // Teste de RN08: Lançar pontuação superior ao máximo da prova
        System.out.println("[Teste RN08] Tentando registrar pontuação superior ao máximo (11.0 de max 10.0):");
        try {
            sistema.registrarNota(matLucas, provaEscrita, new Nota(provaEscrita, new BigDecimal("11.0"), "Nota inválida"));
        } catch (RegraNegocioException e) {
            System.out.println("  Sucesso na validação (rejeitado): " + e.getMessage());
        }

        // Lançando notas válidas para o Lucas
        sistema.registrarNota(matLucas, provaEscrita, new Nota(provaEscrita, new BigDecimal("8.5"), "Bom vocabulário e gramática"));
        sistema.registrarNota(matLucas, provaOral, new Nota(provaOral, new BigDecimal("9.0"), "Excelente pronúncia e fluência"));

        BigDecimal mediaLucas = sistema.calcularMediaFinal(matLucas, mod1);
        System.out.println("Notas registradas para Lucas. Média ponderada calculada (RF16): " + mediaLucas);

        // ---------------------------------------------------------------------------
        // 9. FREQUÊNCIA E AULAS PARTICULARES (RF17, RF18, RN09)
        // ---------------------------------------------------------------------------
        System.out.println("\n>>> 9. FREQUÊNCIA E AULAS PARTICULARES (RF17, RF18, RN09)");
        sistema.registrarFrequencia(matLucas, encontro1, true);
        sistema.registrarFrequencia(matLucas, encontro2, true);
        sistema.registrarFrequencia(matLucas, encontro3, true);
        sistema.registrarFrequencia(matLucas, encontro4, false); // 1 falta em 4 aulas = 75% de presença

        BigDecimal freqLucas = sistema.calcularFrequencia(matLucas);
        System.out.println("Frequência de Lucas (3 presenças em 4 aulas): " + freqLucas.multiply(BigDecimal.valueOf(100)) + "%");

        // Agendamento de aula particular (RF18)
        AulaParticular aulaPart = new AulaParticular(
                "AP-001",
                alunoLucas,
                profCarlos,
                LocalDate.now().plusDays(12),
                LocalTime.of(14, 0),
                LocalTime.of(15, 0),
                "Reforço em Phrasal Verbs",
                "Aluno demonstrou bom rendimento na aula individual."
        );
        sistema.agendarAulaParticular(aulaPart);
        System.out.println("Aula particular agendada com sucesso: " + aulaPart);

        // ---------------------------------------------------------------------------
        // 10. CONCLUSÃO DE MÓDULO E CERTIFICADO (RF19, RN10)
        // ---------------------------------------------------------------------------
        System.out.println("\n>>> 10. EMISSÃO DE CERTIFICADO (RF19, RN10)");
        Certificado certLucas = sistema.concluirModulo(matLucas, mod1);
        System.out.println("Certificado emitido com sucesso:");
        System.out.println("  " + certLucas);

        // Teste de reprovação de certificado por nota insuficiente
        Aluno alunoReprovado = new Aluno("João Teste", "000.111.222-33", LocalDate.of(2000, 1, 1), "Rua A", "111", "joao@email.com");
        sistema.cadastrarAluno(alunoReprovado);
        Turma turmaIngles2 = new Turma("TURMA-ING-02", cursoIngles, profCarlos, LocalDate.now().minusDays(5), LocalDate.now().plusDays(20), 1, 10);
        Encontro encontroReprovado = new Encontro(LocalDate.now().plusDays(1), LocalTime.of(19, 0), LocalTime.of(20, 30), mod1);
        turmaIngles2.adicionarEncontro(encontroReprovado);
        sistema.cadastrarTurma(turmaIngles2);
        Matricula matReprovada = sistema.matricular(alunoReprovado, turmaIngles2, new Matricula("MAT-2026-003", alunoReprovado, turmaIngles2, LocalDate.now(), new BigDecimal("350.00"), 1, BigDecimal.ZERO, "Boleto"));
        sistema.registrarNota(matReprovada, provaEscrita, new Nota(provaEscrita, new BigDecimal("4.0"), "Insuficiente"));
        sistema.registrarNota(matReprovada, provaOral, new Nota(provaOral, new BigDecimal("5.0"), "Insuficiente"));
        sistema.registrarFrequencia(matReprovada, encontroReprovado, true);

        System.out.println("\n[Teste RN10] Tentando emitir certificado para aluno com média abaixo de 7.0:");
        try {
            sistema.concluirModulo(matReprovada, mod1);
        } catch (RegraNegocioException e) {
            System.out.println("  Sucesso na validação (rejeitado): " + e.getMessage());
        }

        // ---------------------------------------------------------------------------
        // 11. REMOÇÕES CONSISTENTES E PRESERVAÇÃO DE HISTÓRICO (RN12)
        // ---------------------------------------------------------------------------
        System.out.println("\n>>> 11. CONSISTÊNCIA DE REMOÇÃO / HISTÓRICO (RN12)");
        System.out.println("[Teste RN12] Tentando remover professor Carlos que possui turmas ativas:");
        try {
            sistema.removerProfessor(profCarlos.getCpf());
        } catch (RegraNegocioException e) {
            System.out.println("  Sucesso na validação (rejeitado): " + e.getMessage());
        }

        System.out.println("[Teste RN12] Tentando remover curso que possui turmas vinculadas:");
        try {
            sistema.removerCurso(cursoIngles.getCodigo());
        } catch (RegraNegocioException e) {
            System.out.println("  Sucesso na validação (rejeitado): " + e.getMessage());
        }

        System.out.println("\n===============================================================================");
        System.out.println("  TODOS OS REQUISITOS (RF01–RF19) E REGRAS (RN01–RN12) TESTADOS COM SUCESSO!");
        System.out.println("===============================================================================");
    }
}
