package negocio;

import dados.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Sistema {
    // Listas mantidas em memória (Etapa I)
    private final List<Curso> cursos = new ArrayList<>();
    private final List<Professor> professores = new ArrayList<>();
    private final List<Aluno> alunos = new ArrayList<>();
    private final List<Turma> turmas = new ArrayList<>();
    private final List<Matricula> matriculas = new ArrayList<>();
    private final List<Mensalidade> mensalidades = new ArrayList<>();
    private final List<Pagamento> pagamentos = new ArrayList<>();
    private final List<Avaliacao> avaliacoes = new ArrayList<>();
    private final List<Nota> notas = new ArrayList<>();
    private final List<RegistroFrequencia> registrosFrequencia = new ArrayList<>();
    private final List<AulaParticular> aulasParticulares = new ArrayList<>();
    private final List<Certificado> certificados = new ArrayList<>();
    private final List<Material> materiais = new ArrayList<>();
    private final List<Venda> vendas = new ArrayList<>();
    private final List<ItemVenda> itensVenda = new ArrayList<>();

    // Parâmetros configuráveis de negócio
    private BigDecimal taxaMulta = new BigDecimal("0.02"); // 2%
    private BigDecimal taxaJurosMensal = new BigDecimal("0.01"); // 1% ao mês
    private BigDecimal notaMinima = new BigDecimal("7.0"); // Nota mínima para aprovação
    private BigDecimal frequenciaMinima = new BigDecimal("0.75"); // 75% de frequência mínima
    private int escalaMedia = 2;
    private final RoundingMode modoArredondamento = RoundingMode.HALF_UP;

    public Sistema() {
    }

    public Sistema(BigDecimal taxaMulta, BigDecimal taxaJurosMensal, BigDecimal notaMinima, BigDecimal frequenciaMinima) {
        if (taxaMulta != null) setTaxaMulta(taxaMulta);
        if (taxaJurosMensal != null) setTaxaJurosMensal(taxaJurosMensal);
        if (notaMinima != null) setNotaMinima(notaMinima);
        if (frequenciaMinima != null) setFrequenciaMinima(frequenciaMinima);
    }

    // --- Getters e Setters de Configurações ---
    public BigDecimal getTaxaMulta() { return taxaMulta; }
    public void setTaxaMulta(BigDecimal taxaMulta) { this.taxaMulta = validarPercentualNaoNegativo(taxaMulta, "Taxa de multa"); }
    public BigDecimal getTaxaJurosMensal() { return taxaJurosMensal; }
    public void setTaxaJurosMensal(BigDecimal taxaJurosMensal) { this.taxaJurosMensal = validarPercentualNaoNegativo(taxaJurosMensal, "Taxa de juros"); }
    public BigDecimal getNotaMinima() { return notaMinima; }
    public void setNotaMinima(BigDecimal notaMinima) { this.notaMinima = validarPercentualNaoNegativo(notaMinima, "Nota mínima"); }
    public BigDecimal getFrequenciaMinima() { return frequenciaMinima; }
    public void setFrequenciaMinima(BigDecimal frequenciaMinima) { this.frequenciaMinima = validarIntervaloUnitario(frequenciaMinima, "Frequência mínima"); }
    public int getEscalaMedia() { return escalaMedia; }
    public void setEscalaMedia(int escalaMedia) { if (escalaMedia < 0) throw new RegraNegocioException("Escala da média não pode ser negativa."); this.escalaMedia = escalaMedia; }
    public RoundingMode getModoArredondamento() { return modoArredondamento; }

    // --- Getters das Coleções ---
    public List<Curso> getCursos() { return Collections.unmodifiableList(cursos); }
    public List<Professor> getProfessores() { return Collections.unmodifiableList(professores); }
    public List<Aluno> getAlunos() { return Collections.unmodifiableList(alunos); }
    public List<Turma> getTurmas() { return Collections.unmodifiableList(turmas); }
    public List<Matricula> getMatriculas() { return Collections.unmodifiableList(matriculas); }
    public List<Mensalidade> getMensalidades() { return Collections.unmodifiableList(mensalidades); }
    public List<Pagamento> getPagamentos() { return Collections.unmodifiableList(pagamentos); }
    public List<Avaliacao> getAvaliacoes() { return Collections.unmodifiableList(avaliacoes); }
    public List<Nota> getNotas() { return Collections.unmodifiableList(notas); }
    public List<RegistroFrequencia> getRegistrosFrequencia() { return Collections.unmodifiableList(registrosFrequencia); }
    public List<AulaParticular> getAulasParticulares() { return Collections.unmodifiableList(aulasParticulares); }
    public List<Certificado> getCertificados() { return Collections.unmodifiableList(certificados); }
    public List<Material> getMateriais() { return Collections.unmodifiableList(materiais); }
    public List<Venda> getVendas() { return Collections.unmodifiableList(vendas); }
    public List<ItemVenda> getItensVenda() { return Collections.unmodifiableList(itensVenda); }

    // ==========================================
    // RF01 — Cursos & RN01, RN12
    // ==========================================
    public void cadastrarCurso(Curso curso) {
        if (curso == null) {
            throw new RegraNegocioException("Curso não pode ser nulo.");
        }
        if (curso.getCodigo() == null || curso.getCodigo().isBlank()) {
            throw new RegraNegocioException("Código do curso é obrigatório.");
        }
        if (consultarCurso(curso.getCodigo()) != null) {
            throw new RegraNegocioException("RN01: Já existe um curso com o código '" + curso.getCodigo() + "'.");
        }
        if (curso.getCargaHorariaTotal() <= 0) {
            throw new RegraNegocioException("Carga horária total do curso deve ser maior que zero.");
        }
        cursos.add(curso);
    }

    public Curso consultarCurso(String codigo) {
        if (codigo == null) return null;
        return cursos.stream()
                .filter(c -> c.getCodigo().equalsIgnoreCase(codigo.trim()))
                .findFirst()
                .orElse(null);
    }

    public void atualizarCurso(Curso cursoAtualizado) {
        if (cursoAtualizado == null || cursoAtualizado.getCodigo() == null) {
            throw new RegraNegocioException("Dados do curso inválidos para atualização.");
        }
        Curso existente = consultarCurso(cursoAtualizado.getCodigo());
        if (existente == null) {
            throw new RegraNegocioException("Curso não encontrado para atualização: " + cursoAtualizado.getCodigo());
        }
        existente.setIdioma(cursoAtualizado.getIdioma());
        existente.setNivel(cursoAtualizado.getNivel());
        existente.setCargaHorariaTotal(cursoAtualizado.getCargaHorariaTotal());
        existente.setDescricaoConteudo(cursoAtualizado.getDescricaoConteudo());
        existente.setMateriaisNecessarios(cursoAtualizado.getMateriaisNecessarios());
    }

    public void removerCurso(String codigo) {
        Curso curso = consultarCurso(codigo);
        if (curso == null) {
            throw new RegraNegocioException("Curso não encontrado para remoção: " + codigo);
        }
        // RN12: Verificar relacionamentos
        boolean possuiTurmas = turmas.stream().anyMatch(t -> t.getCurso() != null && t.getCurso().equals(curso));
        if (possuiTurmas) {
            throw new RegraNegocioException("RN12: Não é possível remover o curso pois há turmas vinculadas.");
        }
        cursos.remove(curso);
    }

    // ==========================================
    // RF02 — Módulos & RN02
    // ==========================================
    public void cadastrarModulo(String codigoCurso, Modulo modulo) {
        Curso curso = consultarCurso(codigoCurso);
        if (curso == null) {
            throw new RegraNegocioException("Curso não encontrado para vincular módulo: " + codigoCurso);
        }
        if (modulo == null) {
            throw new RegraNegocioException("Módulo não pode ser nulo.");
        }
        // RN02: Checar se o número do módulo já existe no curso
        boolean numeroDuplicado = curso.getModulos().stream().anyMatch(m -> m.getNumero() == modulo.getNumero());
        if (numeroDuplicado) {
            throw new RegraNegocioException("RN02: Já existe módulo com o número " + modulo.getNumero() + " neste curso.");
        }
        // RN02: Pré-requisitos devem ser módulos válidos
        for (Modulo preReq : modulo.getPreRequisitos()) {
            boolean preReqExiste = curso.getModulos().stream().anyMatch(m -> m.getNumero() == preReq.getNumero());
            if (!preReqExiste) {
                throw new RegraNegocioException("RN02: Pré-requisito módulo " + preReq.getNumero() + " não pertence à sequência válida do curso.");
            }
        }
        curso.adicionarModulo(modulo);
    }

    public List<Modulo> consultarModulosCurso(String codigoCurso) {
        Curso curso = consultarCurso(codigoCurso);
        if (curso == null) {
            throw new RegraNegocioException("Curso não encontrado: " + codigoCurso);
        }
        return curso.getModulos().stream()
                .sorted(Comparator.comparingInt(Modulo::getNumero))
                .collect(Collectors.toList());
    }

    // ==========================================
    // RF03 — Professores & RN01, RN03, RN12
    // ==========================================
    public void cadastrarProfessor(Professor professor) {
        if (professor == null) {
            throw new RegraNegocioException("Professor não pode ser nulo.");
        }
        if (professor.getCpf() == null || professor.getCpf().isBlank()) {
            throw new RegraNegocioException("CPF do professor é obrigatório.");
        }
        if (consultarProfessor(professor.getCpf()) != null) {
            throw new RegraNegocioException("RN01: Já existe um professor cadastrado com o CPF '" + professor.getCpf() + "'.");
        }
        professores.add(professor);
    }

    public Professor consultarProfessor(String cpf) {
        if (cpf == null) return null;
        return professores.stream()
                .filter(p -> p.getCpf() != null && p.getCpf().replaceAll("[^0-9]", "").equals(cpf.replaceAll("[^0-9]", "")))
                .findFirst()
                .orElse(null);
    }

    public void atualizarProfessor(Professor professorAtualizado) {
        if (professorAtualizado == null || professorAtualizado.getCpf() == null) {
            throw new RegraNegocioException("Dados inválidos para atualizar professor.");
        }
        Professor existente = consultarProfessor(professorAtualizado.getCpf());
        if (existente == null) {
            throw new RegraNegocioException("Professor não encontrado: " + professorAtualizado.getCpf());
        }
        existente.setNome(professorAtualizado.getNome());
        existente.setTelefone(professorAtualizado.getTelefone());
        existente.setEmail(professorAtualizado.getEmail());
        existente.setDataContratacao(professorAtualizado.getDataContratacao());
        existente.setFormacaoAcademica(professorAtualizado.getFormacaoAcademica());
        existente.setQualificacoes(professorAtualizado.getQualificacoes());
    }

    public void removerProfessor(String cpf) {
        Professor prof = consultarProfessor(cpf);
        if (prof == null) {
            throw new RegraNegocioException("Professor não encontrado: " + cpf);
        }
        // RN12: Verificar histórico
        boolean temTurmas = turmas.stream().anyMatch(t -> prof.equals(t.getProfessor()));
        boolean temAulas = aulasParticulares.stream().anyMatch(a -> prof.equals(a.getProfessor()));
        if (temTurmas || temAulas) {
            throw new RegraNegocioException("RN12: Não é possível remover o professor pois há histórico ativo (turmas ou aulas particulares vinculadas).");
        }
        professores.remove(prof);
    }

    // ==========================================
    // RF04 — Alunos & RN01, RN12
    // ==========================================
    public void cadastrarAluno(Aluno aluno) {
        if (aluno == null) {
            throw new RegraNegocioException("Aluno não pode ser nulo.");
        }
        if (aluno.getCpf() == null || aluno.getCpf().isBlank()) {
            throw new RegraNegocioException("CPF do aluno é obrigatório.");
        }
        if (consultarAluno(aluno.getCpf()) != null) {
            throw new RegraNegocioException("RN01: Já existe um aluno cadastrado com o CPF '" + aluno.getCpf() + "'.");
        }
        alunos.add(aluno);
    }

    public Aluno consultarAluno(String cpf) {
        if (cpf == null) return null;
        return alunos.stream()
                .filter(a -> a.getCpf() != null && a.getCpf().replaceAll("[^0-9]", "").equals(cpf.replaceAll("[^0-9]", "")))
                .findFirst()
                .orElse(null);
    }

    public void atualizarAluno(Aluno alunoAtualizado) {
        if (alunoAtualizado == null || alunoAtualizado.getCpf() == null) {
            throw new RegraNegocioException("Dados inválidos para atualizar aluno.");
        }
        Aluno existente = consultarAluno(alunoAtualizado.getCpf());
        if (existente == null) {
            throw new RegraNegocioException("Aluno não encontrado: " + alunoAtualizado.getCpf());
        }
        existente.setNome(alunoAtualizado.getNome());
        existente.setDataNascimento(alunoAtualizado.getDataNascimento());
        existente.setEndereco(alunoAtualizado.getEndereco());
        existente.setTelefone(alunoAtualizado.getTelefone());
        existente.setEmail(alunoAtualizado.getEmail());
        existente.setInteresses(alunoAtualizado.getInteresses());
    }

    public void removerAluno(String cpf) {
        Aluno aluno = consultarAluno(cpf);
        if (aluno == null) {
            throw new RegraNegocioException("Aluno não encontrado: " + cpf);
        }
        // RN12: Verificar histórico
        boolean temMatriculas = matriculas.stream().anyMatch(m -> aluno.equals(m.getAluno()));
        boolean temVendas = vendas.stream().anyMatch(v -> aluno.equals(v.getAluno()));
        boolean temAulas = aulasParticulares.stream().anyMatch(a -> aluno.equals(a.getAluno()));
        if (temMatriculas || temVendas || temAulas) {
            throw new RegraNegocioException("RN12: Não é possível remover o aluno pois há registros históricos (matrículas, vendas ou aulas) vinculados.");
        }
        alunos.remove(aluno);
    }

    // ==========================================
    // RF05, RF06, RF08 — Turmas & RN03, RN05
    // ==========================================
    public void cadastrarTurma(Turma turma) {
        if (turma == null) {
            throw new RegraNegocioException("Turma não pode ser nula.");
        }
        if (turma.getCodigo() == null || turma.getCodigo().isBlank()) {
            throw new RegraNegocioException("Código da turma é obrigatório.");
        }
        if (consultarTurma(turma.getCodigo()) != null) {
            throw new RegraNegocioException("Já existe uma turma cadastrada com o código '" + turma.getCodigo() + "'.");
        }
        if (turma.getCurso() == null || consultarCurso(turma.getCurso().getCodigo()) == null) {
            throw new RegraNegocioException("Curso associado à turma não existe no sistema.");
        }
        if (turma.getProfessor() == null || consultarProfessor(turma.getProfessor().getCpf()) == null) {
            throw new RegraNegocioException("Professor associado à turma não existe no sistema.");
        }
        // RN03: Professor habilitado
        if (!turma.getProfessor().isHabilitadoPara(turma.getCurso().getIdioma(), turma.getCurso().getNivel())) {
            throw new RegraNegocioException("RN03: O professor " + turma.getProfessor().getNome() +
                    " não está habilitado para lecionar o idioma " + turma.getCurso().getIdioma() +
                    " no nível " + turma.getCurso().getNivel() + ".");
        }
        if (turma.getMinimoAlunos() <= 0 || turma.getMaximoAlunos() < turma.getMinimoAlunos()) {
            throw new RegraNegocioException("Quantidade mínima e máxima de alunos inválida.");
        }
        if (turma.getInicioMatricula() != null && turma.getFimMatricula() != null &&
                turma.getFimMatricula().isBefore(turma.getInicioMatricula())) {
            throw new RegraNegocioException("Data de término de matrícula não pode ser anterior ao início.");
        }
        for (Encontro encontro : turma.getEncontros()) {
            if (encontro.getModulo() == null || !moduloPertenceCurso(turma.getCurso(), encontro.getModulo())) {
                throw new RegraNegocioException("Cada encontro deve estar associado a um módulo do curso da turma.");
            }
        }
        turmas.add(turma);
    }

    public Turma consultarTurma(String codigo) {
        if (codigo == null) return null;
        return turmas.stream()
                .filter(t -> t.getCodigo().equalsIgnoreCase(codigo.trim()))
                .findFirst()
                .orElse(null);
    }

    public void adicionarHorarioTurma(String codigoTurma, HorarioTurma horario) {
        Turma turma = consultarTurma(codigoTurma);
        if (turma == null) throw new RegraNegocioException("Turma não encontrada: " + codigoTurma);
        turma.adicionarHorario(horario);
    }

    public void adicionarEncontroTurma(String codigoTurma, Encontro encontro) {
        Turma turma = consultarTurma(codigoTurma);
        if (turma == null) throw new RegraNegocioException("Turma não encontrada: " + codigoTurma);
        if (encontro == null || encontro.getModulo() == null ||
                !moduloPertenceCurso(turma.getCurso(), encontro.getModulo())) {
            throw new RegraNegocioException("Encontro deve estar associado a um módulo do curso da turma.");
        }
        if (turma.getEncontros().stream().anyMatch(e -> e == encontro)) {
            throw new RegraNegocioException("Encontro já está cadastrado na turma.");
        }
        turma.adicionarEncontro(encontro);
    }

    public boolean turmaAtingiuMinimoAlunos(String codigoTurma) {
        Turma turma = consultarTurma(codigoTurma);
        if (turma == null) throw new RegraNegocioException("Turma não encontrada: " + codigoTurma);
        long totalMatriculados = matriculas.stream()
                .filter(m -> m.getTurma().equals(turma) && m.isAtiva())
                .count();
        return totalMatriculados >= turma.getMinimoAlunos();
    }

    public void iniciarTurma(String codigoTurma) {
        Turma turma = consultarTurma(codigoTurma);
        if (turma == null) throw new RegraNegocioException("Turma não encontrada: " + codigoTurma);
        if (!turmaAtingiuMinimoAlunos(codigoTurma)) {
            throw new RegraNegocioException("RF08/RN05: A turma não pode iniciar com menos alunos que o mínimo (" +
                    turma.getMinimoAlunos() + ").");
        }
        turma.setSituacao(SituacaoTurma.EM_ANDAMENTO);
    }

    // ==========================================
    // RF07, RF08 — Matrículas & RN04, RN05, RN06, RN12
    // ==========================================
    public Matricula matricular(Aluno aluno, Turma turma, Matricula dados) {
        Aluno alunoCadastrado = aluno == null ? null : consultarAluno(aluno.getCpf());
        if (alunoCadastrado == null) {
            throw new RegraNegocioException("Aluno inválido ou não cadastrado.");
        }
        Turma turmaCadastrada = turma == null ? null : consultarTurma(turma.getCodigo());
        if (turmaCadastrada == null) {
            throw new RegraNegocioException("Turma inválida ou não cadastrada.");
        }
        aluno = alunoCadastrado;
        turma = turmaCadastrada;
        final Aluno alunoFinal = aluno;
        final Turma turmaFinal = turma;
        if (dados == null) {
            throw new RegraNegocioException("Dados da matrícula não informados.");
        }

        // RN04: Turma aberta e no período de matrícula
        if (turma.getSituacao() != SituacaoTurma.ABERTA) {
            throw new RegraNegocioException("RN04: Não é possível matricular em turma com situação " + turma.getSituacao() + ".");
        }
        LocalDate dataRef = dados.getDataMatricula() != null ? dados.getDataMatricula() : LocalDate.now();
        if (!turma.isPeriodoMatriculaAberto(dataRef)) {
            throw new RegraNegocioException("RN04: Fora do período de matrícula da turma (" +
                    turma.getInicioMatricula() + " a " + turma.getFimMatricula() + ").");
        }

        // RN05: Limite máximo de alunos
        long totalAtivos = matriculas.stream()
                .filter(m -> m.getTurma().equals(turmaFinal) && m.isAtiva())
                .count();
        if (totalAtivos >= turma.getMaximoAlunos()) {
            throw new RegraNegocioException("RN05: Limite máximo de alunos (" + turma.getMaximoAlunos() + ") já atingido nesta turma.");
        }

        // Checar se já matriculado ativamente na turma
        boolean jaMatriculado = matriculas.stream()
                .anyMatch(m -> m.getAluno().equals(alunoFinal) && m.getTurma().equals(turmaFinal) && m.isAtiva());
        if (jaMatriculado) {
            throw new RegraNegocioException("Aluno já possui matrícula ativa nesta turma.");
        }

        BigDecimal valor = dados.getValorMensalidade();
        BigDecimal desconto = dados.getDesconto() == null ? BigDecimal.ZERO : dados.getDesconto();
        if (dados.getQuantidadeParcelas() <= 0 || valor == null || valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RegraNegocioException("Matrícula deve ter parcelas e valor mensal positivos.");
        }
        if (desconto.compareTo(BigDecimal.ZERO) < 0 || desconto.compareTo(valor) >= 0) {
            throw new RegraNegocioException("Desconto deve ser não negativo e menor que o valor da mensalidade.");
        }

        String numero = dados.getNumero();
        if (numero == null || numero.isBlank()) {
            numero = "MAT-" + (matriculas.size() + 1);
        }
        final String numeroFinal = numero.trim();
        if (matriculas.stream().anyMatch(m -> m.getNumero().equalsIgnoreCase(numeroFinal))) {
            throw new RegraNegocioException("Já existe matrícula com o número '" + numeroFinal + "'.");
        }

        Matricula novaMatricula = new Matricula(
                numeroFinal,
                aluno,
                turma,
                dataRef,
                dados.getValorMensalidade(),
                dados.getQuantidadeParcelas(),
                dados.getDesconto(),
                dados.getFormaPagamento()
        );

        matriculas.add(novaMatricula);

        // RF09 & RN06: Gerar mensalidades
        gerarMensalidades(novaMatricula);

        return novaMatricula;
    }

    public void cancelarMatricula(String numeroMatricula) {
        Matricula matricula = matriculas.stream()
                .filter(m -> m.getNumero().equalsIgnoreCase(numeroMatricula))
                .findFirst()
                .orElse(null);
        if (matricula == null) {
            throw new RegraNegocioException("Matrícula não encontrada: " + numeroMatricula);
        }
        // RN12: Inativar a matrícula preservando histórico
        matricula.setAtiva(false);
        // Mantém o histórico, mas cancela cobranças ainda não pagas.
        for (Mensalidade m : matricula.getMensalidades()) {
            if (m.getSituacao() == SituacaoMensalidade.PENDENTE || m.getSituacao() == SituacaoMensalidade.VENCIDA) {
                m.setSituacao(SituacaoMensalidade.CANCELADA);
            }
        }
    }

    public List<Matricula> consultarMatriculasPorAluno(String cpfAluno) {
        return matriculas.stream()
                .filter(m -> m.getAluno().getCpf().replaceAll("[^0-9]", "").equals(cpfAluno.replaceAll("[^0-9]", "")))
                .collect(Collectors.toList());
    }

    public List<Matricula> consultarMatriculasPorTurma(String codigoTurma) {
        return matriculas.stream()
                .filter(m -> m.getTurma().getCodigo().equalsIgnoreCase(codigoTurma))
                .collect(Collectors.toList());
    }

    // ==========================================
    // RF09, RF10, RF11 — Mensalidades, Pagamentos & RN06, RN07
    // ==========================================
    public void gerarMensalidades(Matricula matricula) {
        if (!matriculaRegistrada(matricula)) {
            throw new RegraNegocioException("Matrícula deve estar cadastrada no sistema para gerar mensalidades.");
        }
        if (!matricula.getMensalidades().isEmpty()) {
            throw new RegraNegocioException("Mensalidades já foram geradas para esta matrícula.");
        }
        if (matricula.getQuantidadeParcelas() <= 0) {
            throw new RegraNegocioException("RN06: Quantidade de parcelas deve ser maior que zero.");
        }
        BigDecimal valorBase = matricula.getValorMensalidade();
        if (valorBase == null || valorBase.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RegraNegocioException("RN06: Valor da mensalidade deve ser maior que zero.");
        }
        BigDecimal desconto = matricula.getDesconto() != null ? matricula.getDesconto() : BigDecimal.ZERO;
        BigDecimal valorFinal = valorBase.subtract(desconto);
        if (valorFinal.compareTo(BigDecimal.ZERO) < 0) {
            valorFinal = BigDecimal.ZERO;
        }

        LocalDate dataBase = matricula.getDataMatricula() != null ? matricula.getDataMatricula() : LocalDate.now();

        for (int i = 1; i <= matricula.getQuantidadeParcelas(); i++) {
            LocalDate vencimento = dataBase.plusMonths(i);
            Mensalidade mens = new Mensalidade(i, vencimento, valorFinal, matricula);
            matricula.adicionarMensalidade(mens);
            mensalidades.add(mens);
        }
    }

    public BigDecimal calcularTotalEmAtraso(Mensalidade mensalidade, LocalDate hoje) {
        if (mensalidade == null) {
            throw new RegraNegocioException("Mensalidade não informada.");
        }
        if (hoje == null) hoje = LocalDate.now();
        if (mensalidade.getSituacao() == SituacaoMensalidade.CANCELADA) {
            throw new RegraNegocioException("Mensalidade cancelada não pode ser recalculada.");
        }

        // Se já está paga, valor não muda
        if (mensalidade.getSituacao() == SituacaoMensalidade.PAGA) {
            return mensalidade.getValorAtualizado();
        }

        // RN07: Multa e juros incidem apenas se vencida e não paga
        if (mensalidade.getVencimento() != null && hoje.isAfter(mensalidade.getVencimento())) {
            mensalidade.setSituacao(SituacaoMensalidade.VENCIDA);

            BigDecimal valorOrig = mensalidade.getValorOriginal();
            BigDecimal multa = valorOrig.multiply(taxaMulta);

            long diasAtraso = ChronoUnit.DAYS.between(mensalidade.getVencimento(), hoje);
            // Juros proporcional mensal diário: taxaJurosMensal / 30 * dias
            BigDecimal jurosDiario = taxaJurosMensal.divide(BigDecimal.valueOf(30), 6, RoundingMode.HALF_UP);
            BigDecimal jurosTotal = valorOrig.multiply(jurosDiario.multiply(BigDecimal.valueOf(diasAtraso)));

            BigDecimal atualizado = valorOrig.add(multa).add(jurosTotal).setScale(2, RoundingMode.HALF_UP);
            mensalidade.setValorAtualizado(atualizado);
            return atualizado;
        } else {
            mensalidade.setValorAtualizado(mensalidade.getValorOriginal());
            return mensalidade.getValorOriginal();
        }
    }

    public void registrarPagamento(Mensalidade mensalidade, Pagamento pagamento) {
        if (mensalidade == null || pagamento == null) {
            throw new RegraNegocioException("Mensalidade e pagamento são obrigatórios.");
        }
        if (mensalidades.stream().noneMatch(m -> m == mensalidade)) {
            throw new RegraNegocioException("Mensalidade não pertence a este sistema.");
        }
        if (mensalidade.getSituacao() == SituacaoMensalidade.PAGA) {
            throw new RegraNegocioException("RN06: Esta mensalidade já foi paga em " + mensalidade.getDataPagamento() + ".");
        }
        if (mensalidade.getSituacao() == SituacaoMensalidade.CANCELADA) {
            throw new RegraNegocioException("Não é possível pagar uma mensalidade cancelada.");
        }
        if (pagamento.getValorPago() == null || pagamento.getValorPago().compareTo(BigDecimal.ZERO) <= 0) {
            throw new RegraNegocioException("Valor pago deve ser maior que zero.");
        }
        LocalDate dataPagamento = pagamento.getData() == null ? LocalDate.now() : pagamento.getData();
        if (dataPagamento.isAfter(LocalDate.now())) {
            throw new RegraNegocioException("Data de pagamento não pode estar no futuro.");
        }
        BigDecimal valorDevido = calcularTotalEmAtraso(mensalidade, dataPagamento);
        if (pagamento.getValorPago().compareTo(valorDevido) != 0) {
            throw new RegraNegocioException("Pagamento deve corresponder ao total devido: R$ " + valorDevido + ".");
        }

        pagamento.setData(dataPagamento);
        mensalidade.setValorAtualizado(valorDevido);
        mensalidade.setSituacao(SituacaoMensalidade.PAGA);
        mensalidade.setDataPagamento(dataPagamento);
        mensalidade.setPagamento(pagamento);
        pagamentos.add(pagamento);
    }

    public List<Mensalidade> consultarMensalidadesPorMatricula(String numeroMatricula) {
        return mensalidades.stream()
                .filter(m -> m.getMatricula() != null && m.getMatricula().getNumero().equalsIgnoreCase(numeroMatricula))
                .collect(Collectors.toList());
    }

    // ==========================================
    // RF12, RF13 — Materiais Didáticos & Vendas (RN01, RN11)
    // ==========================================
    public void cadastrarMaterial(Material material) {
        if (material == null) {
            throw new RegraNegocioException("Material não pode ser nulo.");
        }
        if (material.getCodigo() == null || material.getCodigo().isBlank()) {
            throw new RegraNegocioException("Código do material é obrigatório.");
        }
        if (consultarMaterial(material.getCodigo()) != null) {
            throw new RegraNegocioException("RN01: Já existe um material cadastrado com o código '" + material.getCodigo() + "'.");
        }
        if (material.getPreco() == null || material.getPreco().compareTo(BigDecimal.ZERO) < 0) {
            throw new RegraNegocioException("Preço do material inválido.");
        }
        materiais.add(material);
    }

    public Material consultarMaterial(String codigo) {
        if (codigo == null) return null;
        return materiais.stream()
                .filter(m -> m.getCodigo().equalsIgnoreCase(codigo.trim()))
                .findFirst()
                .orElse(null);
    }

    public void registrarVenda(Venda venda) {
        if (venda == null) {
            throw new RegraNegocioException("Venda não pode ser nula.");
        }
        if (venda.getNumero() == null || venda.getNumero().isBlank()) {
            throw new RegraNegocioException("Número da venda é obrigatório.");
        }
        if (vendas.stream().anyMatch(v -> v.getNumero().equalsIgnoreCase(venda.getNumero().trim()))) {
            throw new RegraNegocioException("Já existe venda com o número '" + venda.getNumero() + "'.");
        }
        Aluno alunoCadastrado = venda.getAluno() == null ? null : consultarAluno(venda.getAluno().getCpf());
        if (alunoCadastrado == null) {
            throw new RegraNegocioException("Aluno da venda deve estar cadastrado no sistema.");
        }
        if (venda.getItens() == null || venda.getItens().isEmpty()) {
            throw new RegraNegocioException("RN11: Venda deve possuir ao menos um item de material.");
        }
        for (ItemVenda item : venda.getItens()) {
            if (item == null || item.getMaterial() == null) {
                throw new RegraNegocioException("Cada item da venda deve referenciar um material cadastrado.");
            }
            Material materialCadastrado = consultarMaterial(item.getMaterial().getCodigo());
            if (materialCadastrado == null) {
                throw new RegraNegocioException("Material da venda não está cadastrado no sistema.");
            }
            if (item.getQuantidade() <= 0) {
                throw new RegraNegocioException("Quantidade do item deve ser maior que zero.");
            }
            if (item.getPrecoUnitario() == null || item.getPrecoUnitario().compareTo(BigDecimal.ZERO) < 0) {
                throw new RegraNegocioException("Preço unitário do item inválido.");
            }
            item.setMaterial(materialCadastrado);
        }
        venda.setNumero(venda.getNumero().trim());
        venda.setAluno(alunoCadastrado);
        itensVenda.addAll(venda.getItens());
        vendas.add(venda);
    }

    public List<Venda> consultarVendasPorAluno(String cpfAluno) {
        return vendas.stream()
                .filter(v -> v.getAluno() != null && v.getAluno().getCpf().replaceAll("[^0-9]", "").equals(cpfAluno.replaceAll("[^0-9]", "")))
                .collect(Collectors.toList());
    }

    // ==========================================
    // RF14, RF15, RF16 — Avaliações, Notas & Média Final (RN08)
    // ==========================================
    public void cadastrarAvaliacao(Avaliacao avaliacao) {
        if (avaliacao == null) {
            throw new RegraNegocioException("Avaliação não pode ser nula.");
        }
        if (avaliacao.getIdentificador() == null || avaliacao.getIdentificador().isBlank()) {
            throw new RegraNegocioException("Identificador da avaliação é obrigatório.");
        }
        if (avaliacoes.stream().anyMatch(a -> a.getIdentificador().equalsIgnoreCase(avaliacao.getIdentificador().trim()))) {
            throw new RegraNegocioException("Já existe avaliação com o identificador '" + avaliacao.getIdentificador() + "'.");
        }
        if (avaliacao.getModulo() == null || !moduloPertenceAAlgumCurso(avaliacao.getModulo())) {
            throw new RegraNegocioException("A avaliação deve pertencer a um módulo cadastrado.");
        }
        // RN08: peso positivo e valor máximo positivo
        if (avaliacao.getValorMaximo() == null || avaliacao.getValorMaximo().compareTo(BigDecimal.ZERO) <= 0) {
            throw new RegraNegocioException("RN08: Valor máximo da avaliação deve ser maior que zero.");
        }
        if (avaliacao.getPeso() == null || avaliacao.getPeso().compareTo(BigDecimal.ZERO) <= 0) {
            throw new RegraNegocioException("RN08: Peso da avaliação deve ser positivo.");
        }
        avaliacao.getModulo().adicionarAvaliacao(avaliacao);
    }

    public void registrarNota(Matricula matricula, Avaliacao avaliacao, Nota nota) {
        if (matricula == null || avaliacao == null || nota == null) {
            throw new RegraNegocioException("Matrícula, avaliação e nota são obrigatórios.");
        }
        if (!matriculaRegistrada(matricula) || !matricula.isAtiva()) {
            throw new RegraNegocioException("A matrícula deve estar ativa e cadastrada no sistema.");
        }
        if (avaliacoes.stream().noneMatch(a -> a == avaliacao)) {
            throw new RegraNegocioException("A avaliação não está cadastrada no sistema.");
        }
        if (!moduloPertenceCurso(matricula.getTurma().getCurso(), avaliacao.getModulo())) {
            throw new RegraNegocioException("A avaliação não pertence ao curso da matrícula.");
        }
        if (matricula.getNotas().stream().anyMatch(n -> n.getAvaliacao() == avaliacao)) {
            throw new RegraNegocioException("Já existe nota registrada para esta avaliação nesta matrícula.");
        }
        // RN08: Pontuação deve estar entre 0 e o valor máximo
        if (nota.getPontuacao() == null ||
                nota.getPontuacao().compareTo(BigDecimal.ZERO) < 0 ||
                nota.getPontuacao().compareTo(avaliacao.getValorMaximo()) > 0) {
            throw new RegraNegocioException("RN08: Pontuação da nota (" + nota.getPontuacao() +
                    ") deve estar entre 0 e o valor máximo (" + avaliacao.getValorMaximo() + ").");
        }
        nota.setAvaliacao(avaliacao);
        matricula.adicionarNota(nota);
        notas.add(nota);
    }

    public BigDecimal calcularMediaFinal(Matricula matricula, Modulo modulo) {
        if (!matriculaRegistrada(matricula) || modulo == null ||
                !moduloPertenceCurso(matricula.getTurma().getCurso(), modulo)) {
            throw new RegraNegocioException("Matrícula e módulo devem pertencer ao mesmo curso cadastrado.");
        }
        // Notas do aluno que pertencem a avaliações deste módulo
        List<Nota> notasModulo = matricula.getNotas().stream()
                .filter(n -> n.getAvaliacao() != null && modulo == n.getAvaliacao().getModulo())
                .collect(Collectors.toList());

        if (notasModulo.isEmpty()) {
            return BigDecimal.ZERO.setScale(escalaMedia, modoArredondamento);
        }

        BigDecimal somaPonderada = BigDecimal.ZERO;
        BigDecimal somaPesos = BigDecimal.ZERO;

        for (Nota n : notasModulo) {
            BigDecimal peso = n.getAvaliacao().getPeso();
            somaPonderada = somaPonderada.add(n.getPontuacao().multiply(peso));
            somaPesos = somaPesos.add(peso);
        }

        if (somaPesos.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO.setScale(escalaMedia, modoArredondamento);
        }

        return somaPonderada.divide(somaPesos, escalaMedia, modoArredondamento);
    }

    // ==========================================
    // RF17 — Frequência (RN09)
    // ==========================================
    public void registrarFrequencia(Matricula matricula, Encontro encontro, boolean presente) {
        if (matricula == null || encontro == null) {
            throw new RegraNegocioException("Matrícula e encontro são obrigatórios para registrar frequência.");
        }
        if (!matriculaRegistrada(matricula) || !matricula.isAtiva()) {
            throw new RegraNegocioException("A matrícula deve estar ativa e cadastrada no sistema.");
        }
        Turma turma = matricula.getTurma();
        if (turma.getEncontros().stream().noneMatch(e -> e == encontro)) {
            throw new RegraNegocioException("O encontro não pertence à turma da matrícula.");
        }
        if (encontro.getModulo() == null || !moduloPertenceCurso(turma.getCurso(), encontro.getModulo())) {
            throw new RegraNegocioException("O encontro deve estar associado a um módulo do curso da turma.");
        }
        if (matricula.getRegistrosFrequencia().stream().anyMatch(r -> r.getEncontro() == encontro)) {
            throw new RegraNegocioException("A frequência deste aluno já foi registrada para o encontro.");
        }
        RegistroFrequencia reg = new RegistroFrequencia(encontro, encontro.getData(), presente);
        matricula.adicionarRegistroFrequencia(reg);
        registrosFrequencia.add(reg);
    }

    public BigDecimal calcularFrequencia(Matricula matricula) {
        if (!matriculaRegistrada(matricula)) {
            throw new RegraNegocioException("Matrícula não cadastrada no sistema.");
        }
        return calcularPercentualPresenca(matricula.getRegistrosFrequencia());
    }

    public BigDecimal calcularFrequencia(Matricula matricula, Modulo modulo) {
        if (!matriculaRegistrada(matricula) || modulo == null ||
                !moduloPertenceCurso(matricula.getTurma().getCurso(), modulo)) {
            throw new RegraNegocioException("Matrícula e módulo devem pertencer ao mesmo curso cadastrado.");
        }
        List<RegistroFrequencia> registrosModulo = matricula.getRegistrosFrequencia().stream()
                .filter(r -> r.getEncontro() != null && r.getEncontro().getModulo() == modulo)
                .collect(Collectors.toList());
        return calcularPercentualPresenca(registrosModulo);
    }

    // ==========================================
    // RF18 — Aulas Particulares
    // ==========================================
    public void agendarAulaParticular(AulaParticular aula) {
        if (aula == null) {
            throw new RegraNegocioException("Dados da aula particular não podem ser nulos.");
        }
        if (aula.getAluno() == null || consultarAluno(aula.getAluno().getCpf()) == null) {
            throw new RegraNegocioException("Aluno da aula particular não cadastrado.");
        }
        if (aula.getProfessor() == null || consultarProfessor(aula.getProfessor().getCpf()) == null) {
            throw new RegraNegocioException("Professor da aula particular não cadastrado.");
        }
        if (aula.getHorarioInicio() != null && aula.getHorarioTermino() != null &&
                !aula.getHorarioInicio().isBefore(aula.getHorarioTermino())) {
            throw new RegraNegocioException("Horário de início da aula deve ser anterior ao término.");
        }
        aulasParticulares.add(aula);
    }

    public List<AulaParticular> consultarAulasParticularesPorAluno(String cpfAluno) {
        return aulasParticulares.stream()
                .filter(a -> a.getAluno() != null && a.getAluno().getCpf().replaceAll("[^0-9]", "").equals(cpfAluno.replaceAll("[^0-9]", "")))
                .collect(Collectors.toList());
    }

    public List<AulaParticular> consultarAulasParticularesPorProfessor(String cpfProfessor) {
        return aulasParticulares.stream()
                .filter(a -> a.getProfessor() != null && a.getProfessor().getCpf().replaceAll("[^0-9]", "").equals(cpfProfessor.replaceAll("[^0-9]", "")))
                .collect(Collectors.toList());
    }

    // ==========================================
    // RF19 — Conclusão de Módulo & Certificado (RN01, RN10)
    // ==========================================
    public Certificado concluirModulo(Matricula matricula, Modulo modulo) {
        if (matricula == null || modulo == null || !matriculaRegistrada(matricula) || !matricula.isAtiva()) {
            throw new RegraNegocioException("É necessária uma matrícula ativa e cadastrada para concluir módulo.");
        }
        Curso curso = matricula.getTurma().getCurso();
        if (!moduloPertenceCurso(curso, modulo)) {
            throw new RegraNegocioException("O módulo não pertence ao curso da matrícula.");
        }
        if (certificados.stream().anyMatch(c -> c.getAluno().equals(matricula.getAluno()) && c.getModulo() == modulo)) {
            throw new RegraNegocioException("Já existe certificado para este aluno e módulo.");
        }

        BigDecimal mediaFinal = calcularMediaFinal(matricula, modulo);
        BigDecimal frequencia = calcularFrequencia(matricula, modulo);

        // RN10: Validação de nota e frequência mínimas
        if (mediaFinal.compareTo(notaMinima) < 0) {
            throw new RegraNegocioException("RN10: Aluno reprovado por nota. Média final (" + mediaFinal +
                    ") inferior à nota mínima (" + notaMinima + ").");
        }
        if (frequencia.compareTo(frequenciaMinima) < 0) {
            throw new RegraNegocioException("RN10: Aluno reprovado por frequência. Frequência obtida (" +
                    frequencia.multiply(BigDecimal.valueOf(100)) + "%) inferior à mínima exigida (" +
                    frequenciaMinima.multiply(BigDecimal.valueOf(100)) + "%).");
        }

        String numeroCertificado = "CERT-" + (certificados.size() + 1);
        Certificado cert = new Certificado(
                numeroCertificado,
                LocalDate.now(),
                matricula.getAluno(),
                matricula.getTurma().getCurso(),
                modulo,
                modulo.getCargaHoraria(),
                mediaFinal
        );

        certificados.add(cert);
        return cert;
    }

    private BigDecimal validarPercentualNaoNegativo(BigDecimal valor, String nome) {
        if (valor == null || valor.compareTo(BigDecimal.ZERO) < 0) {
            throw new RegraNegocioException(nome + " não pode ser nula nem negativa.");
        }
        return valor;
    }

    private BigDecimal validarIntervaloUnitario(BigDecimal valor, String nome) {
        validarPercentualNaoNegativo(valor, nome);
        if (valor.compareTo(BigDecimal.ONE) > 0) {
            throw new RegraNegocioException(nome + " deve estar entre zero e um.");
        }
        return valor;
    }

    private boolean matriculaRegistrada(Matricula matricula) {
        return matricula != null && matriculas.stream().anyMatch(m -> m == matricula);
    }

    private boolean moduloPertenceAAlgumCurso(Modulo modulo) {
        return modulo != null && cursos.stream().anyMatch(c -> moduloPertenceCurso(c, modulo));
    }

    private boolean moduloPertenceCurso(Curso curso, Modulo modulo) {
        return curso != null && modulo != null && curso.getModulos().stream().anyMatch(m -> m == modulo);
    }

    private BigDecimal calcularPercentualPresenca(List<RegistroFrequencia> registros) {
        if (registros == null || registros.isEmpty()) {
            return BigDecimal.ZERO.setScale(escalaMedia, modoArredondamento);
        }
        long presencas = registros.stream().filter(RegistroFrequencia::isPresente).count();
        return BigDecimal.valueOf(presencas)
                .divide(BigDecimal.valueOf(registros.size()), escalaMedia, modoArredondamento);
    }

    public List<Certificado> consultarCertificadosPorAluno(String cpfAluno) {
        return certificados.stream()
                .filter(c -> c.getAluno() != null && c.getAluno().getCpf().replaceAll("[^0-9]", "").equals(cpfAluno.replaceAll("[^0-9]", "")))
                .collect(Collectors.toList());
    }
}
