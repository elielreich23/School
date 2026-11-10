package apresentacao;

import dados.*;
import negocio.RegraNegocioException;
import negocio.Sistema;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/** Interface gráfica da Etapa II. Os dados permanecem em memória durante a execução. */
public final class InterfaceGrafica {
    private final Sistema sistema = new Sistema();
    private final JFrame janela = new JFrame("Gestão de Escola de Idiomas");
    private final JPanel conteudo = new JPanel(new CardLayout());
    private final JPanel navegacao = new JPanel();
    private final Color azul = new Color(48, 86, 125);
    private final ButtonGroup grupoNavegacao = new ButtonGroup();
    private final List<Runnable> atualizadores = new ArrayList<>();
    private final JLabel status = new JLabel("Pronto");

    private InterfaceGrafica() {
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janela.setSize(1180, 760);
        janela.setMinimumSize(new java.awt.Dimension(900, 600));
        janela.setLocationRelativeTo(null);
        janela.getContentPane().setBackground(new Color(248, 248, 248));
        navegacao.setLayout(new BoxLayout(navegacao, BoxLayout.Y_AXIS));
        navegacao.setBackground(new Color(238, 240, 242));
        navegacao.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 0, 1, new Color(210, 214, 218)),
                BorderFactory.createEmptyBorder(12, 8, 12, 8)));
        navegacao.setPreferredSize(new java.awt.Dimension(190, 0));
        JLabel marca = new JLabel("Navegação");
        marca.setFont(new Font("Arial", Font.BOLD, 14));
        marca.setForeground(new Color(45, 52, 58));
        marca.setBorder(BorderFactory.createEmptyBorder(4, 8, 12, 4));
        marca.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
        navegacao.add(marca);
        criarAbas();
        JPanel principal = new JPanel(new BorderLayout());
        principal.add(new JScrollPane(navegacao), BorderLayout.WEST);
        principal.add(conteudo, BorderLayout.CENTER);
        JPanel rodape = new JPanel(new BorderLayout());
        rodape.setBackground(new Color(238, 240, 242));
        rodape.setBorder(BorderFactory.createEmptyBorder(8, 14, 8, 14));
        rodape.add(status, BorderLayout.WEST);
        janela.add(principal, BorderLayout.CENTER);
        janela.add(rodape, BorderLayout.SOUTH);
        atualizarTabelas();
    }

    public static void main(String[] args) {
        UITheme.inicializarLookAndFeel();
        SwingUtilities.invokeLater(() -> new InterfaceGrafica().janela.setVisible(true));
    }

    private void criarAbas() {
        adicionarAba("Visão geral", "Resumo do sistema", () -> Arrays.asList(
                "Cursos: " + sistema.getCursos().size(), "Professores: " + sistema.getProfessores().size(),
                "Alunos: " + sistema.getAlunos().size(), "Turmas: " + sistema.getTurmas().size(),
                "Matrículas: " + sistema.getMatriculas().size(), "Mensalidades: " + sistema.getMensalidades().size(),
                "Pagamentos: " + sistema.getPagamentos().size(), "Materiais: " + sistema.getMateriais().size(),
                "Vendas: " + sistema.getVendas().size(), "Avaliações: " + sistema.getAvaliacoes().size(),
                "Notas: " + sistema.getNotas().size(), "Presenças/faltas: " + sistema.getRegistrosFrequencia().size(),
                "Aulas particulares: " + sistema.getAulasParticulares().size(), "Certificados: " + sistema.getCertificados().size()
        ), barra -> { });
        adicionarAba("Cursos e módulos", "Cursos cadastrados", sistema::getCursos, barra -> {
            botao(barra, "Novo curso", this::novoCurso); botao(barra, "Editar curso", this::editarCurso);
            botao(barra, "Remover curso", this::removerCurso); botao(barra, "Adicionar módulo", this::novoModulo);
            botao(barra, "Ver módulos", this::verModulos);
        });
        adicionarAba("Alunos", "Alunos cadastrados", sistema::getAlunos, barra -> {
            botao(barra, "Novo aluno", this::novoAluno); botao(barra, "Editar aluno", this::editarAluno);
            botao(barra, "Remover aluno", this::removerAluno);
        });
        adicionarAba("Professores", "Professores cadastrados", sistema::getProfessores, barra -> {
            botao(barra, "Novo professor", this::novoProfessor); botao(barra, "Editar professor", this::editarProfessor);
            botao(barra, "Remover professor", this::removerProfessor);
        });
        adicionarAba("Turmas", "Turmas cadastradas", sistema::getTurmas, barra -> {
            botao(barra, "Nova turma", this::novaTurma); botao(barra, "Adicionar horário", this::adicionarHorario);
            botao(barra, "Agendar encontro", this::adicionarEncontro); botao(barra, "Iniciar turma", this::iniciarTurma);
        });
        adicionarAba("Matrículas", "Matrículas cadastradas", sistema::getMatriculas, barra -> {
            botao(barra, "Matricular aluno", this::matricular); botao(barra, "Cancelar matrícula", this::cancelarMatricula);
            botao(barra, "Por aluno", this::matriculasPorAluno); botao(barra, "Por turma", this::matriculasPorTurma);
        });
        adicionarAba("Mensalidades", "Mensalidades", sistema::getMensalidades, barra -> {
            botao(barra, "Calcular valor atualizado", this::calcularAtraso); botao(barra, "Registrar pagamento", this::registrarPagamento);
            botao(barra, "Da matrícula", this::mensalidadesDaMatricula);
        });
        adicionarAba("Pagamentos", "Pagamentos registrados", sistema::getPagamentos, barra -> { });
        adicionarAba("Materiais", "Materiais cadastrados", sistema::getMateriais, barra -> botao(barra, "Cadastrar material", this::novoMaterial));
        adicionarAba("Vendas", "Vendas registradas", sistema::getVendas, barra -> {
            botao(barra, "Registrar venda", this::registrarVenda); botao(barra, "Vendas do aluno", this::vendasDoAluno);
        });
        adicionarAba("Avaliações", "Avaliações cadastradas", sistema::getAvaliacoes, barra -> botao(barra, "Nova avaliação", this::novaAvaliacao));
        adicionarAba("Notas", "Notas registradas", sistema::getNotas, barra -> {
            botao(barra, "Registrar nota", this::registrarNota); botao(barra, "Calcular média", this::calcularMedia);
        });
        adicionarAba("Frequência", "Registros de frequência", sistema::getRegistrosFrequencia, barra -> {
            botao(barra, "Registrar presença/falta", this::registrarFrequencia); botao(barra, "Frequência do módulo", this::calcularFrequencia);
        });
        adicionarAba("Certificados", "Certificados emitidos", sistema::getCertificados, barra -> {
            botao(barra, "Emitir certificado", this::emitirCertificado); botao(barra, "Do aluno", this::certificadosDoAluno);
        });
        adicionarAba("Aulas particulares", "Aulas agendadas", sistema::getAulasParticulares, barra -> {
            botao(barra, "Agendar aula", this::agendarAula); botao(barra, "Por aluno", this::aulasDoAluno);
            botao(barra, "Por professor", this::aulasDoProfessor);
        });
        adicionarAba("Parâmetros", "Configuração atual", () -> Arrays.asList(
                "Multa: " + sistema.getTaxaMulta().multiply(BigDecimal.valueOf(100)) + "%",
                "Juros mensais: " + sistema.getTaxaJurosMensal().multiply(BigDecimal.valueOf(100)) + "%",
                "Nota mínima: " + sistema.getNotaMinima(),
                "Frequência mínima: " + sistema.getFrequenciaMinima().multiply(BigDecimal.valueOf(100)) + "%"
        ), barra -> botao(barra, "Alterar parâmetros", this::configurarParametros));
    }
    private void adicionarAba(String titulo, String tituloTabela, Supplier<List<?>> dados, MontadorBarra montarBarra) {
        JPanel pagina = new JPanel(new BorderLayout(12, 12));
        pagina.setBackground(Color.WHITE);
        pagina.setBorder(BorderFactory.createEmptyBorder(18, 20, 16, 20));
        JPanel cabecalho = new JPanel(new BorderLayout());
        cabecalho.setOpaque(false);
        JLabel tituloPagina = new JLabel(titulo);
        tituloPagina.setFont(new Font("Arial", Font.BOLD, 20));
        tituloPagina.setForeground(new Color(40, 48, 56));
        cabecalho.add(tituloPagina, BorderLayout.NORTH);
        JLabel subtitulo = new JLabel(tituloTabela);
        subtitulo.setFont(new Font("Arial", Font.PLAIN, 12));
        subtitulo.setForeground(new Color(90, 96, 102));
        cabecalho.add(subtitulo, BorderLayout.CENTER);
        JPanel barra = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 4));
        barra.setOpaque(false);
        montarBarra.montar(barra);
        JPanel topo = new JPanel(new BorderLayout(8, 12));
        topo.setOpaque(false);
        topo.add(cabecalho, BorderLayout.NORTH);
        if (barra.getComponentCount() > 0) topo.add(barra, BorderLayout.CENTER);
        pagina.add(topo, BorderLayout.NORTH);

        DefaultTableModel modelo = new DefaultTableModel(new Object[]{tituloTabela}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        JTable tabela = new JTable(modelo);
        tabela.setFillsViewportHeight(true);
        tabela.setRowHeight(25);
        tabela.setFont(new Font("Arial", Font.PLAIN, 12));
        tabela.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        JScrollPane rolagem = new JScrollPane(tabela);
        rolagem.setBorder(BorderFactory.createLineBorder(new Color(210, 214, 218)));
        pagina.add(rolagem, BorderLayout.CENTER);
        atualizadores.add(() -> {
            modelo.setRowCount(0);
            for (Object item : dados.get()) modelo.addRow(new Object[]{String.valueOf(item)});
        });
        conteudo.add(pagina, titulo);
        JToggleButton itemMenu = new JToggleButton(titulo);
        itemMenu.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
        itemMenu.setMaximumSize(new java.awt.Dimension(Integer.MAX_VALUE, 34));
        itemMenu.setHorizontalAlignment(SwingConstants.LEFT);
        itemMenu.setFont(new Font("Arial", Font.PLAIN, 12));
        itemMenu.setForeground(new Color(45, 52, 58));
        itemMenu.setBackground(new Color(238, 240, 242));
        itemMenu.setBorder(BorderFactory.createEmptyBorder(6, 10, 6, 6));
        itemMenu.setFocusPainted(true);
        itemMenu.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
        grupoNavegacao.add(itemMenu);
        itemMenu.addActionListener(e -> ((CardLayout) conteudo.getLayout()).show(conteudo, titulo));
        navegacao.add(itemMenu);
        if (conteudo.getComponentCount() == 1) itemMenu.setSelected(true);
        itemMenu.setToolTipText("Abrir " + titulo);
    }

    private void botao(JPanel barra, String titulo, Runnable acao) {
        JButton botao = new JButton(titulo);
        botao.setFont(new Font("Arial", Font.PLAIN, 12));
        botao.setForeground(azul);
        botao.addActionListener(evento -> executar(acao));
        barra.add(botao);
    }

    private void executar(Runnable acao) {
        try {
            acao.run();
            atualizarTabelas();
            status.setText("Pronto");
        } catch (OperacaoCancelada ignorada) {
            atualizarTabelas();
        } catch (RegraNegocioException | IllegalArgumentException erro) {
            status.setText("A operação não foi concluída");
            JOptionPane.showMessageDialog(janela, erro.getMessage(), "Verifique os dados", JOptionPane.WARNING_MESSAGE);
        } catch (RuntimeException erro) {
            status.setText("Erro na operação");
            JOptionPane.showMessageDialog(janela, erro.getMessage() == null ? "Não foi possível concluir a operação." : erro.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void atualizarTabelas() {
        atualizadores.forEach(Runnable::run);
    }

    private String texto(String pergunta) {
        return JOptionPane.showInputDialog(janela, pergunta);
    }

    private String textoObrigatorio(String pergunta) {
        UIFormDialog formulario = new UIFormDialog(janela, "Informar dados", pergunta);
        formulario.addTextField("valor", pergunta, "", true);
        if (!formulario.showDialog()) throw new OperacaoCancelada();
        return formulario.getText("valor").trim();
    }

    private BigDecimal decimal(String pergunta) {
        UIFormDialog formulario = new UIFormDialog(janela, "Informar valor", pergunta);
        formulario.addDecimalField("valor", pergunta, BigDecimal.ZERO, true);
        if (!formulario.showDialog()) throw new OperacaoCancelada();
        return formulario.getBigDecimal("valor");
    }

    private int inteiro(String pergunta) {
        UIFormDialog formulario = new UIFormDialog(janela, "Informar quantidade", pergunta);
        formulario.addIntegerSpinner("valor", pergunta, 1, 0, 1000000, 1, true);
        if (!formulario.showDialog()) throw new OperacaoCancelada();
        return formulario.getInteger("valor");
    }

    private LocalDate data(String pergunta) {
        UIFormDialog formulario = new UIFormDialog(janela, "Selecionar data", pergunta);
        formulario.addDateField("data", pergunta, LocalDate.now(), true);
        if (!formulario.showDialog()) throw new OperacaoCancelada();
        return formulario.getDate("data");
    }

    private LocalTime hora(String pergunta) {
        UIFormDialog formulario = new UIFormDialog(janela, "Selecionar horário", pergunta);
        formulario.addTimeField("hora", pergunta, LocalTime.of(9, 0), true);
        if (!formulario.showDialog()) throw new OperacaoCancelada();
        return formulario.getTime("hora");
    }

    @SuppressWarnings("unchecked")
    private <T> T escolher(String pergunta, List<T> opcoes) {
        if (opcoes == null || opcoes.isEmpty()) {
            JOptionPane.showMessageDialog(janela, "Cadastre os dados necessários antes desta operação.", "Sem opções", JOptionPane.INFORMATION_MESSAGE);
            throw new OperacaoCancelada();
        }
        Object selecionado = JOptionPane.showInputDialog(janela, pergunta, "Selecione", JOptionPane.QUESTION_MESSAGE,
                null, opcoes.toArray(), opcoes.get(0));
        if (selecionado == null) throw new OperacaoCancelada();
        return (T) selecionado;
    }

    private <E extends Enum<E>> E escolherEnum(String pergunta, Class<E> tipo) {
        return escolher(pergunta, Arrays.asList(tipo.getEnumConstants()));
    }

    private boolean confirmar(String pergunta) {
        int resposta = JOptionPane.showConfirmDialog(janela, pergunta, "Confirmação", JOptionPane.YES_NO_CANCEL_OPTION);
        if (resposta == JOptionPane.CLOSED_OPTION || resposta == JOptionPane.CANCEL_OPTION) throw new OperacaoCancelada();
        return resposta == JOptionPane.YES_OPTION;
    }

    private void informar(String mensagem) {
        JOptionPane.showMessageDialog(janela, mensagem, "Gestão de Idiomas", JOptionPane.INFORMATION_MESSAGE);
    }

    private void configurarParametros() {
        UIFormDialog formulario = new UIFormDialog(janela, "Parâmetros do sistema", "Defina os valores usados nos cálculos e aprovações.");
        formulario.addDecimalField("multa", "Multa (%)", sistema.getTaxaMulta().movePointRight(2), true);
        formulario.addDecimalField("juros", "Juros mensais (%)", sistema.getTaxaJurosMensal().movePointRight(2), true);
        formulario.addDecimalField("nota", "Nota mínima", sistema.getNotaMinima(), true);
        formulario.addDecimalField("frequencia", "Frequência mínima (%)", sistema.getFrequenciaMinima().movePointRight(2), true);
        if (!formulario.showDialog()) throw new OperacaoCancelada();
        sistema.setTaxaMulta(formulario.getBigDecimal("multa").movePointLeft(2));
        sistema.setTaxaJurosMensal(formulario.getBigDecimal("juros").movePointLeft(2));
        sistema.setNotaMinima(formulario.getBigDecimal("nota"));
        sistema.setFrequenciaMinima(formulario.getBigDecimal("frequencia").movePointLeft(2));
    }
    private void novoCurso() {
        String codigo = textoObrigatorio("Código do curso:");
        String idioma = textoObrigatorio("Idioma:");
        NivelCurso nivel = escolherEnum("Nível:", NivelCurso.class);
        int carga = inteiro("Carga horária total:");
        String descricao = textoObrigatorio("Descrição do conteúdo:");
        String material = textoObrigatorio("Materiais necessários:");
        sistema.cadastrarCurso(new Curso(codigo, idioma, nivel, carga, descricao, material));
    }

    private void editarCurso() {
        Curso curso = escolher("Curso:", sistema.getCursos());
        curso.setIdioma(textoObrigatorio("Idioma:"));
        curso.setNivel(escolherEnum("Nível:", NivelCurso.class));
        curso.setCargaHorariaTotal(inteiro("Carga horária total:"));
        curso.setDescricaoConteudo(textoObrigatorio("Descrição do conteúdo:"));
        curso.setMateriaisNecessarios(textoObrigatorio("Materiais necessários:"));
        sistema.atualizarCurso(curso);
    }

    private void removerCurso() {
        Curso curso = escolher("Curso a remover:", sistema.getCursos());
        if (confirmar("Remover o curso " + curso.getCodigo() + "?")) sistema.removerCurso(curso.getCodigo());
    }

    private void novoModulo() {
        Curso curso = escolher("Curso:", sistema.getCursos());
        int numero = inteiro("Número sequencial do módulo:");
        String nome = textoObrigatorio("Nome do módulo:");
        int carga = inteiro("Carga horária:");
        String horario = textoObrigatorio("Horário de aprendizagem (descrição):");
        Modulo modulo = new Modulo(numero, nome, carga, horario);
        while (!curso.getModulos().isEmpty() && confirmar("Adicionar pré-requisito a este módulo?")) {
            List<Modulo> anteriores = new ArrayList<>(curso.getModulos());
            anteriores.removeIf(m -> m.getNumero() >= numero);
            if (anteriores.isEmpty()) break;
            modulo.adicionarPreRequisito(escolher("Pré-requisito:", anteriores));
        }
        sistema.cadastrarModulo(curso.getCodigo(), modulo);
    }

    private void verModulos() {
        Curso curso = escolher("Curso:", sistema.getCursos());
        informar(sistema.consultarModulosCurso(curso.getCodigo()).stream().map(Object::toString).collect(Collectors.joining("\n")));
    }

    private void novoProfessor() {
        Professor professor = new Professor(textoObrigatorio("Nome:"), textoObrigatorio("CPF:"),
                textoObrigatorio("Telefone:"), textoObrigatorio("E-mail:"), data("Data de contratação (AAAA-MM-DD):"),
                textoObrigatorio("Formação acadêmica:"));
        while (true) {
            String idioma = texto("Idioma que pode ministrar (Cancelar para terminar):");
            if (idioma == null || idioma.trim().isEmpty()) break;
            professor.adicionarQualificacao(new IdiomaProfissional(idioma.trim(), escolherEnum("Proficiência:", NivelCurso.class)));
        }
        sistema.cadastrarProfessor(professor);
    }

    private void editarProfessor() {
        Professor atual = escolher("Professor:", sistema.getProfessores());
        Professor editado = new Professor(textoObrigatorio("Nome:"), atual.getCpf(), textoObrigatorio("Telefone:"),
                textoObrigatorio("E-mail:"), data("Data de contratação (AAAA-MM-DD):"), textoObrigatorio("Formação acadêmica:"));
        if (!atual.getQualificacoes().isEmpty()) {
            atual.getQualificacoes().forEach(editado::adicionarQualificacao);
        } else {
            String idioma = texto("Idioma que pode ministrar (opcional):");
            if (idioma != null && !idioma.trim().isEmpty()) editado.adicionarQualificacao(new IdiomaProfissional(idioma.trim(), escolherEnum("Proficiência:", NivelCurso.class)));
        }
        sistema.atualizarProfessor(editado);
    }

    private void removerProfessor() {
        Professor professor = escolher("Professor a remover:", sistema.getProfessores());
        if (confirmar("Remover " + professor.getNome() + "?")) sistema.removerProfessor(professor.getCpf());
    }

    private void novoAluno() {
        Aluno aluno = new Aluno(textoObrigatorio("Nome:"), textoObrigatorio("CPF:"), data("Nascimento (AAAA-MM-DD):"),
                textoObrigatorio("Endereço:"), textoObrigatorio("Telefone:"), textoObrigatorio("E-mail:"));
        while (true) {
            String idioma = texto("Idioma de interesse (Cancelar para terminar):");
            if (idioma == null || idioma.trim().isEmpty()) break;
            aluno.adicionarInteresse(new IdiomaInteresse(idioma.trim(), escolherEnum("Nível atual:", NivelCurso.class)));
        }
        sistema.cadastrarAluno(aluno);
    }

    private void editarAluno() {
        Aluno atual = escolher("Aluno:", sistema.getAlunos());
        Aluno editado = new Aluno(textoObrigatorio("Nome:"), atual.getCpf(), data("Nascimento (AAAA-MM-DD):"),
                textoObrigatorio("Endereço:"), textoObrigatorio("Telefone:"), textoObrigatorio("E-mail:"));
        atual.getInteresses().forEach(editado::adicionarInteresse);
        sistema.atualizarAluno(editado);
    }

    private void removerAluno() {
        Aluno aluno = escolher("Aluno a remover:", sistema.getAlunos());
        if (confirmar("Remover " + aluno.getNome() + "?")) sistema.removerAluno(aluno.getCpf());
    }

    private void novaTurma() {
        Curso curso = escolher("Curso:", sistema.getCursos());
        List<Professor> habilitados = sistema.getProfessores().stream()
                .filter(p -> p.isHabilitadoPara(curso.getIdioma(), curso.getNivel())).collect(Collectors.toList());
        Professor professor = escolher("Professor responsável:", habilitados);
        Turma turma = new Turma(textoObrigatorio("Código da turma:"), curso, professor,
                data("Início das matrículas (AAAA-MM-DD):"), data("Fim das matrículas (AAAA-MM-DD):"),
                inteiro("Mínimo de alunos:"), inteiro("Máximo de alunos:"));
        sistema.cadastrarTurma(turma);
        while (confirmar("Adicionar horário semanal à turma?")) {
            DayOfWeek dia = escolherEnum("Dia da semana:", DayOfWeek.class);
            LocalTime inicio = hora("Horário de início (HH:MM):");
            LocalTime fim = hora("Horário de término (HH:MM):");
            sistema.adicionarHorarioTurma(turma.getCodigo(), new HorarioTurma(dia, inicio, fim, textoObrigatorio("Sala:")));
        }
    }

    private void adicionarHorario() {
        Turma turma = escolher("Turma:", sistema.getTurmas());
        DayOfWeek dia = escolherEnum("Dia da semana:", DayOfWeek.class);
        LocalTime inicio = hora("Horário de início (HH:MM):");
        LocalTime fim = hora("Horário de término (HH:MM):");
        sistema.adicionarHorarioTurma(turma.getCodigo(), new HorarioTurma(dia, inicio, fim, textoObrigatorio("Sala:")));
    }

    private void adicionarEncontro() {
        Turma turma = escolher("Turma:", sistema.getTurmas());
        Modulo modulo = escolher("Módulo:", sistema.consultarModulosCurso(turma.getCurso().getCodigo()));
        Encontro encontro = new Encontro(data("Data do encontro (AAAA-MM-DD):"), hora("Início (HH:MM):"),
                hora("Término (HH:MM):"), modulo);
        sistema.adicionarEncontroTurma(turma.getCodigo(), encontro);
    }

    private void iniciarTurma() {
        Turma turma = escolher("Turma a iniciar:", sistema.getTurmas());
        sistema.iniciarTurma(turma.getCodigo());
    }

    private void matricular() {
        Aluno aluno = escolher("Aluno:", sistema.getAlunos());
        Turma turma = escolher("Turma aberta:", sistema.getTurmas().stream()
                .filter(t -> t.getSituacao() == SituacaoTurma.ABERTA).collect(Collectors.toList()));
        Matricula dados = new Matricula(texto("Número da matrícula (opcional):"), aluno, turma, LocalDate.now(),
                decimal("Valor mensal da parcela:"), inteiro("Quantidade de parcelas:"), decimal("Desconto por parcela:"),
                textoObrigatorio("Forma de pagamento:"));
        sistema.matricular(aluno, turma, dados);
    }

    private void cancelarMatricula() {
        Matricula matricula = escolher("Matrícula ativa:", sistema.getMatriculas().stream().filter(Matricula::isAtiva).collect(Collectors.toList()));
        if (confirmar("Cancelar a matrícula " + matricula.getNumero() + "?")) sistema.cancelarMatricula(matricula.getNumero());
    }

    private void matriculasPorAluno() {
        Aluno aluno = escolher("Aluno:", sistema.getAlunos());
        informar(sistema.consultarMatriculasPorAluno(aluno.getCpf()).stream().map(Object::toString).collect(Collectors.joining("\n")));
    }

    private void matriculasPorTurma() {
        Turma turma = escolher("Turma:", sistema.getTurmas());
        informar(sistema.consultarMatriculasPorTurma(turma.getCodigo()).stream().map(Object::toString).collect(Collectors.joining("\n")));
    }

    private void novoMaterial() {
        sistema.cadastrarMaterial(new Material(textoObrigatorio("Código:"), textoObrigatorio("Título:"),
                textoObrigatorio("Editora:"), textoObrigatorio("Edição:"), textoObrigatorio("Tipo:"), decimal("Preço:")));
    }

    private void calcularAtraso() {
        Mensalidade mensalidade = escolher("Mensalidade:", sistema.getMensalidades().stream()
                .filter(m -> m.getSituacao() != SituacaoMensalidade.PAGA && m.getSituacao() != SituacaoMensalidade.CANCELADA)
                .collect(Collectors.toList()));
        informar("Total devido hoje: R$ " + sistema.calcularTotalEmAtraso(mensalidade, LocalDate.now()));
    }

    private void registrarPagamento() {
        Mensalidade mensalidade = escolher("Mensalidade a pagar:", sistema.getMensalidades().stream()
                .filter(m -> m.getSituacao() != SituacaoMensalidade.PAGA && m.getSituacao() != SituacaoMensalidade.CANCELADA)
                .collect(Collectors.toList()));
        BigDecimal devido = sistema.calcularTotalEmAtraso(mensalidade, LocalDate.now());
        informar("Valor integral devido: R$ " + devido);
        Pagamento pagamento = new Pagamento(LocalDate.now(), decimal("Valor pago:"), textoObrigatorio("Forma de pagamento:"));
        sistema.registrarPagamento(mensalidade, pagamento);
    }

    private void mensalidadesDaMatricula() {
        Matricula matricula = escolher("Matrícula:", sistema.getMatriculas());
        informar(sistema.consultarMensalidadesPorMatricula(matricula.getNumero()).stream()
                .map(Object::toString).collect(Collectors.joining("\n")));
    }

    private void registrarVenda() {
        Aluno aluno = escolher("Aluno:", sistema.getAlunos());
        Venda venda = new Venda("VND-" + String.format(Locale.ROOT, "%04d", sistema.getVendas().size() + 1), aluno, LocalDate.now());
        do {
            Material material = escolher("Material:", sistema.getMateriais());
            int quantidade = inteiro("Quantidade:");
            venda.adicionarItem(new ItemVenda(material, quantidade, material.getPreco()));
        } while (confirmar("Adicionar outro material?"));
        sistema.registrarVenda(venda);
    }

    private void vendasDoAluno() {
        Aluno aluno = escolher("Aluno:", sistema.getAlunos());
        informar(sistema.consultarVendasPorAluno(aluno.getCpf()).stream().map(Object::toString).collect(Collectors.joining("\n")));
    }

    private void novaAvaliacao() {
        Curso curso = escolher("Curso:", sistema.getCursos());
        Modulo modulo = escolher("Módulo:", sistema.consultarModulosCurso(curso.getCodigo()));
        Avaliacao avaliacao = new Avaliacao(textoObrigatorio("Identificador:"), escolherEnum("Tipo:", TipoAvaliacao.class),
                data("Data de aplicação (AAAA-MM-DD):"), decimal("Valor máximo:"), decimal("Peso:"), modulo);
        sistema.cadastrarAvaliacao(avaliacao);
    }

    private void registrarNota() {
        Matricula matricula = escolher("Matrícula ativa:", sistema.getMatriculas().stream().filter(Matricula::isAtiva).collect(Collectors.toList()));
        List<Avaliacao> avaliacoesDoCurso = sistema.getAvaliacoes().stream()
                .filter(a -> matricula.getTurma().getCurso().getModulos().contains(a.getModulo())).collect(Collectors.toList());
        Avaliacao avaliacao = escolher("Avaliação:", avaliacoesDoCurso);
        sistema.registrarNota(matricula, avaliacao, new Nota(avaliacao, decimal("Pontuação:"), texto("Observações:")));
    }

    private void calcularMedia() {
        Matricula matricula = escolher("Matrícula:", sistema.getMatriculas());
        Modulo modulo = escolher("Módulo:", sistema.consultarModulosCurso(matricula.getTurma().getCurso().getCodigo()));
        informar("Média final: " + sistema.calcularMediaFinal(matricula, modulo));
    }

    private void registrarFrequencia() {
        Matricula matricula = escolher("Matrícula ativa:", sistema.getMatriculas().stream().filter(Matricula::isAtiva).collect(Collectors.toList()));
        Encontro encontro = escolher("Encontro da turma:", matricula.getTurma().getEncontros());
        int resposta = JOptionPane.showConfirmDialog(janela, "O aluno esteve presente?", "Registrar frequência",
                JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (resposta == JOptionPane.CANCEL_OPTION || resposta == JOptionPane.CLOSED_OPTION) throw new OperacaoCancelada();
        boolean presente = resposta == JOptionPane.YES_OPTION;
        sistema.registrarFrequencia(matricula, encontro, presente);
    }

    private void calcularFrequencia() {
        Matricula matricula = escolher("Matrícula:", sistema.getMatriculas());
        Modulo modulo = escolher("Módulo:", sistema.consultarModulosCurso(matricula.getTurma().getCurso().getCodigo()));
        BigDecimal percentual = sistema.calcularFrequencia(matricula, modulo).multiply(BigDecimal.valueOf(100));
        informar("Frequência no módulo: " + percentual + "%");
    }

    private void emitirCertificado() {
        Matricula matricula = escolher("Matrícula ativa:", sistema.getMatriculas().stream().filter(Matricula::isAtiva).collect(Collectors.toList()));
        Modulo modulo = escolher("Módulo:", sistema.consultarModulosCurso(matricula.getTurma().getCurso().getCodigo()));
        Certificado certificado = sistema.concluirModulo(matricula, modulo);
        informar("Certificado emitido:\n" + certificado);
    }

    private void certificadosDoAluno() {
        Aluno aluno = escolher("Aluno:", sistema.getAlunos());
        informar(sistema.consultarCertificadosPorAluno(aluno.getCpf()).stream().map(Object::toString).collect(Collectors.joining("\n")));
    }

    private void agendarAula() {
        Aluno aluno = escolher("Aluno:", sistema.getAlunos());
        Professor professor = escolher("Professor:", sistema.getProfessores());
        AulaParticular aula = new AulaParticular(textoObrigatorio("Identificador:"), aluno, professor,
                data("Data (AAAA-MM-DD):"), hora("Início (HH:MM):"), hora("Término (HH:MM):"),
                textoObrigatorio("Conteúdo ministrado:"), texto("Observações:"));
        sistema.agendarAulaParticular(aula);
    }

    private void aulasDoAluno() {
        Aluno aluno = escolher("Aluno:", sistema.getAlunos());
        informar(sistema.consultarAulasParticularesPorAluno(aluno.getCpf()).stream().map(Object::toString).collect(Collectors.joining("\n")));
    }

    private void aulasDoProfessor() {
        Professor professor = escolher("Professor:", sistema.getProfessores());
        informar(sistema.consultarAulasParticularesPorProfessor(professor.getCpf()).stream().map(Object::toString).collect(Collectors.joining("\n")));
    }

    @FunctionalInterface
    private interface MontadorBarra {
        void montar(JPanel barra);
    }

    private static final class OperacaoCancelada extends RuntimeException {
        private static final long serialVersionUID = 1L;
    }
}
