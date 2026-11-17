package apresentacao;

import dados.*;
import negocio.RegraNegocioException;
import negocio.Sistema;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Interface gráfica principal do Sistema de Gestão de Escola de Idiomas (Etapa II).
 * Proporciona uma experiência moderna, organizada por módulos funcionais,
 * com formulários estruturados, tabelas multi-coluna com busca em tempo real,
 * validações consistentes e feedback visual ao usuário.
 */
public final class InterfaceGrafica {

    private final Sistema sistema = new Sistema();
    private final JFrame janela = new JFrame("Escola de Idiomas — Sistema de Gestão Acadêmica");
    private final CardLayout layoutConteudo = new CardLayout();
    private final JPanel painelConteudo = new JPanel(layoutConteudo);
    private final List<Runnable> atualizadores = new ArrayList<>();
    private final JLabel lblStatus = new JLabel(" Sistema pronto.");
    private final Map<String, JButton> botoesNavegacao = new LinkedHashMap<>();
    private String secaoAtiva = "dashboard";

    // Painel do Dashboard
    private JPanel painelDashboard;

    private InterfaceGrafica() {
        UITheme.inicializarLookAndFeel();
        configurarJanela();
        construirInterface();
        atualizarTodasTabelas();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            InterfaceGrafica app = new InterfaceGrafica();
            app.janela.setVisible(true);
        });
    }

    private void configurarJanela() {
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Rectangle areaUtil = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
        int largura = Math.min(1240, areaUtil.width);
        int altura = Math.min(780, areaUtil.height);
        janela.setMinimumSize(new Dimension(Math.min(900, largura), Math.min(580, altura)));
        janela.setSize(largura, altura);
        janela.setLocationRelativeTo(null);
    }

    private void construirInterface() {
        JPanel raiz = new JPanel(new BorderLayout());
        raiz.setBackground(UITheme.COLOR_BG);

        // Barra Superior (Header)
        JPanel cabecalhoApp = criarCabecalhoSuperior();
        raiz.add(cabecalhoApp, BorderLayout.NORTH);

        // Corpo: Navegação Lateral + Conteúdo Central
        JPanel corpo = new JPanel(new BorderLayout());
        corpo.setOpaque(false);

        JPanel barraLateral = criarBarraLateral();
        corpo.add(barraLateral, BorderLayout.WEST);

        painelConteudo.setOpaque(false);
        painelConteudo.setBorder(new EmptyBorder(14, 16, 14, 16));
        criarTodasSecoes();
        corpo.add(painelConteudo, BorderLayout.CENTER);

        raiz.add(corpo, BorderLayout.CENTER);

        // Rodapé com barra de status
        JPanel rodape = criarRodape();
        raiz.add(rodape, BorderLayout.SOUTH);

        janela.setContentPane(raiz);
    }

    private JPanel criarCabecalhoSuperior() {
        JPanel header = new JPanel(new BorderLayout(16, 0));
        header.setBackground(UITheme.COLOR_SIDEBAR);
        header.setBorder(new EmptyBorder(12, 20, 12, 20));

        // Título e logo
        JPanel brand = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        brand.setOpaque(false);

        JPanel brandText = new JPanel(new GridLayout(2, 1, 0, 2));
        brandText.setOpaque(false);
        JLabel appTitle = new JLabel("ESCOLA DE IDIOMAS");
        appTitle.setFont(UITheme.FONT_APP_TITLE);
        appTitle.setForeground(UITheme.COLOR_TEXT_MAIN);

        JLabel appSub = new JLabel("Sistema de Gestão Acadêmica, Pedagógica e Financeira");
        appSub.setFont(UITheme.FONT_SMALL);
        appSub.setForeground(UITheme.COLOR_SIDEBAR_MUTED);

        brandText.add(appTitle);
        brandText.add(appSub);
        brand.add(brandText);

        // Ações Rápidas no Topo
        JPanel acoesTopo = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        acoesTopo.setOpaque(false);

        JButton btnDadosExemplo = UITheme.createSecondaryButton("Carregar Dados de Demonstração");
        btnDadosExemplo.setToolTipText("Preenche o sistema com cursos, turmas, alunos e movimentações para testes rápidos.");
        btnDadosExemplo.addActionListener(e -> carregarDadosDemonstracao());

        JButton btnParametros = UITheme.createSecondaryButton("Parâmetros");
        btnParametros.addActionListener(e -> navegarPara("parametros"));

        acoesTopo.add(btnDadosExemplo);
        acoesTopo.add(btnParametros);

        header.add(brand, BorderLayout.WEST);
        header.add(acoesTopo, BorderLayout.EAST);
        return header;
    }

    private JPanel criarBarraLateral() {
        JPanel lateral = new JPanel(new BorderLayout());
        lateral.setBackground(UITheme.COLOR_SIDEBAR);
        lateral.setPreferredSize(new Dimension(230, 0));
        lateral.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, UITheme.COLOR_BORDER));

        JPanel listaMenu = new JPanel();
        listaMenu.setLayout(new BoxLayout(listaMenu, BoxLayout.Y_AXIS));
        listaMenu.setOpaque(false);
        listaMenu.setBorder(new EmptyBorder(12, 8, 12, 8));

        // Itens de Navegação organizados por categoria
        adicionarSeparadorMenu(listaMenu, "PRINCIPAL");
        adicionarItemMenu(listaMenu, "dashboard", "Visão Geral");

        adicionarSeparadorMenu(listaMenu, "ACADÊMICO");
        adicionarItemMenu(listaMenu, "cursos", "Cursos e Módulos");
        adicionarItemMenu(listaMenu, "turmas", "Turmas e Horários");

        adicionarSeparadorMenu(listaMenu, "PESSOAS");
        adicionarItemMenu(listaMenu, "alunos", "Alunos");
        adicionarItemMenu(listaMenu, "professores", "Professores");

        adicionarSeparadorMenu(listaMenu, "SECRETARIA");
        adicionarItemMenu(listaMenu, "matriculas", "Matrículas");
        adicionarItemMenu(listaMenu, "aulas", "Aulas Particulares");
        adicionarItemMenu(listaMenu, "certificados", "Certificados");

        adicionarSeparadorMenu(listaMenu, "PEDAGÓGICO");
        adicionarItemMenu(listaMenu, "avaliacoes", "Avaliações");
        adicionarItemMenu(listaMenu, "notas", "Lançamento de Notas");
        adicionarItemMenu(listaMenu, "frequencia", "Frequência");

        adicionarSeparadorMenu(listaMenu, "FINANCEIRO");
        adicionarItemMenu(listaMenu, "mensalidades", "Mensalidades");
        adicionarItemMenu(listaMenu, "pagamentos", "Pagamentos");
        adicionarItemMenu(listaMenu, "materiais", "Materiais");
        adicionarItemMenu(listaMenu, "vendas", "Vendas");

        adicionarSeparadorMenu(listaMenu, "SISTEMA");
        adicionarItemMenu(listaMenu, "parametros", "Parâmetros Globais");

        JScrollPane scrollLateral = new JScrollPane(listaMenu);
        scrollLateral.setBorder(null);
        scrollLateral.setOpaque(false);
        scrollLateral.getViewport().setOpaque(false);
        scrollLateral.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        lateral.add(scrollLateral, BorderLayout.CENTER);
        return lateral;
    }

    private void adicionarSeparadorMenu(JPanel painel, String texto) {
        JLabel rotulo = new JLabel("  " + texto);
        rotulo.setFont(UITheme.FONT_SMALL_BOLD);
        rotulo.setForeground(new Color(100, 116, 139));
        rotulo.setBorder(new EmptyBorder(10, 6, 4, 6));
        rotulo.setAlignmentX(Component.LEFT_ALIGNMENT);
        painel.add(rotulo);
    }

    private void adicionarItemMenu(JPanel painel, String chave, String titulo) {
        JButton btn = new JButton(titulo);
        UITheme.manterTextoLegivel(btn);
        btn.setFont(UITheme.FONT_REGULAR);
        btn.setForeground(UITheme.COLOR_SIDEBAR_TEXT);
        btn.setBackground(UITheme.COLOR_SIDEBAR);
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setFocusPainted(false);
        btn.setBorder(new CompoundBorder(
                BorderFactory.createEmptyBorder(2, 0, 2, 0),
                new EmptyBorder(8, 12, 8, 12)
        ));
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        btn.setAlignmentX(Component.LEFT_ALIGNMENT);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        btn.addActionListener(e -> navegarPara(chave));
        botoesNavegacao.put(chave, btn);
        painel.add(btn);
    }

    private void navegarPara(String chave) {
        secaoAtiva = chave;
        layoutConteudo.show(painelConteudo, chave);

        // Atualiza destaque visual dos botões
        botoesNavegacao.forEach((k, btn) -> {
            boolean ativo = k.equals(chave);
            btn.setBackground(ativo ? UITheme.COLOR_SIDEBAR_ACTIVE : UITheme.COLOR_SIDEBAR);
            btn.setFont(ativo ? UITheme.FONT_BOLD : UITheme.FONT_REGULAR);
            btn.setForeground(ativo ? UITheme.COLOR_TEXT_MAIN : UITheme.COLOR_SIDEBAR_TEXT);
        });

        atualizarTodasTabelas();
        lblStatus.setText(" Visualizando seção: " + chave.toUpperCase());
    }

    private JPanel criarRodape() {
        JPanel rodape = new JPanel(new BorderLayout());
        rodape.setBackground(Color.WHITE);
        rodape.setBorder(new CompoundBorder(
                BorderFactory.createMatteBorder(1, 0, 0, 0, UITheme.COLOR_BORDER),
                new EmptyBorder(6, 16, 6, 16)
        ));

        lblStatus.setFont(UITheme.FONT_SMALL);
        lblStatus.setForeground(UITheme.COLOR_TEXT_MUTED);

        JLabel lblVersao = new JLabel("Projeto POO — Etapa II  |  Dados mantidos em memória");
        lblVersao.setFont(UITheme.FONT_SMALL);
        lblVersao.setForeground(UITheme.COLOR_TEXT_MUTED);

        rodape.add(lblStatus, BorderLayout.WEST);
        rodape.add(lblVersao, BorderLayout.EAST);
        return rodape;
    }

    // =========================================================================
    // CONSTRUÇÃO DE TODAS AS SEÇÕES
    // =========================================================================
    private void criarTodasSecoes() {
        painelConteudo.add(criarSecaoDashboard(), "dashboard");
        painelConteudo.add(criarSecaoCursos(), "cursos");
        painelConteudo.add(criarSecaoTurmas(), "turmas");
        painelConteudo.add(criarSecaoAlunos(), "alunos");
        painelConteudo.add(criarSecaoProfessores(), "professores");
        painelConteudo.add(criarSecaoMatriculas(), "matriculas");
        painelConteudo.add(criarSecaoAulasParticulares(), "aulas");
        painelConteudo.add(criarSecaoCertificados(), "certificados");
        painelConteudo.add(criarSecaoAvaliacoes(), "avaliacoes");
        painelConteudo.add(criarSecaoNotas(), "notas");
        painelConteudo.add(criarSecaoFrequencia(), "frequencia");
        painelConteudo.add(criarSecaoMensalidades(), "mensalidades");
        painelConteudo.add(criarSecaoPagamentos(), "pagamentos");
        painelConteudo.add(criarSecaoMateriais(), "materiais");
        painelConteudo.add(criarSecaoVendas(), "vendas");
        painelConteudo.add(criarSecaoParametros(), "parametros");

        // Seleciona o primeiro menu
        navegarPara("dashboard");
    }

    // -------------------------------------------------------------------------
    // 1. DASHBOARD / VISÃO GERAL
    // -------------------------------------------------------------------------
    private JScrollPane criarSecaoDashboard() {
        painelDashboard = new JPanel(new BorderLayout(12, 16));
        painelDashboard.setOpaque(false);

        JPanel header = UITheme.createHeaderPanel("Painel de Controle e Indicadores",
                "Visão geral dos dados consolidados, cadastros acadêmicos e indicadores financeiros.");
        painelDashboard.add(header, BorderLayout.NORTH);

        JPanel centro = new JPanel(new BorderLayout(12, 16));
        centro.setOpaque(false);

        // Grade de Cards KPI
        JPanel gradeCards = new JPanel(new GridLayout(3, 4, 12, 12));
        gradeCards.setOpaque(false);

        atualizadores.add(() -> {
            gradeCards.removeAll();
            gradeCards.add(UITheme.createStatCard("Alunos Ativos", String.valueOf(sistema.getAlunos().size()), "Matriculados e interessados", new Color(37, 99, 235)));
            gradeCards.add(UITheme.createStatCard("Professores", String.valueOf(sistema.getProfessores().size()), "Corpo docente habilitado", new Color(16, 185, 129)));
            gradeCards.add(UITheme.createStatCard("Cursos Ofertados", String.valueOf(sistema.getCursos().size()), "Idiomas e níveis", new Color(139, 92, 246)));
            gradeCards.add(UITheme.createStatCard("Turmas", String.valueOf(sistema.getTurmas().size()), "Abertas e em andamento", new Color(245, 158, 11)));

            gradeCards.add(UITheme.createStatCard("Matrículas", String.valueOf(sistema.getMatriculas().size()), "Contratos escolares", new Color(6, 182, 212)));
            gradeCards.add(UITheme.createStatCard("Mensalidades", String.valueOf(sistema.getMensalidades().size()), "Parcelas geradas", new Color(14, 165, 233)));
            gradeCards.add(UITheme.createStatCard("Pagamentos", String.valueOf(sistema.getPagamentos().size()), "Quitados no sistema", new Color(16, 185, 129)));
            gradeCards.add(UITheme.createStatCard("Certificados", String.valueOf(sistema.getCertificados().size()), "Módulos concluídos", new Color(217, 70, 239)));

            gradeCards.add(UITheme.createStatCard("Aulas Particulares", String.valueOf(sistema.getAulasParticulares().size()), "Agendamentos individuais", new Color(236, 72, 153)));
            gradeCards.add(UITheme.createStatCard("Materiais Didáticos", String.valueOf(sistema.getMateriais().size()), "Livros e apostilas", new Color(100, 116, 139)));
            gradeCards.add(UITheme.createStatCard("Vendas de Materiais", String.valueOf(sistema.getVendas().size()), "Pedidos faturados", new Color(249, 115, 22)));

            BigDecimal totalArrecadado = sistema.getPagamentos().stream()
                    .map(Pagamento::getValorPago)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            gradeCards.add(UITheme.createStatCard("Receita Pagamentos", UITheme.formatCurrency(totalArrecadado), "Total arrecadado", new Color(5, 150, 105)));

            gradeCards.revalidate();
            gradeCards.repaint();
        });

        centro.add(gradeCards, BorderLayout.NORTH);

        // Painel de Ações Rápidas no Dashboard
        JPanel cardAcoes = UITheme.createCardPanel();
        cardAcoes.setLayout(new BorderLayout(8, 12));

        JLabel lblAcoes = new JLabel("Acesso Rápido às Funcionalidades Principais");
        lblAcoes.setFont(UITheme.FONT_SECTION);
        lblAcoes.setForeground(UITheme.COLOR_TEXT_MAIN);
        cardAcoes.add(lblAcoes, BorderLayout.NORTH);

        JPanel gridBotoesAcao = new JPanel(new GridLayout(2, 4, 10, 10));
        gridBotoesAcao.setOpaque(false);

        JButton btnNovoAluno = UITheme.createPrimaryButton("+ Novo Aluno");
        btnNovoAluno.addActionListener(e -> executar(() -> { navegarPara("alunos"); novoAluno(); }));

        JButton btnNovoCurso = UITheme.createPrimaryButton("+ Novo Curso");
        btnNovoCurso.addActionListener(e -> executar(() -> { navegarPara("cursos"); novoCurso(); }));

        JButton btnNovaTurma = UITheme.createPrimaryButton("+ Nova Turma");
        btnNovaTurma.addActionListener(e -> executar(() -> { navegarPara("turmas"); novaTurma(); }));

        JButton btnMatricular = UITheme.createPrimaryButton("+ Matricular Aluno");
        btnMatricular.addActionListener(e -> executar(() -> { navegarPara("matriculas"); matricular(); }));

        JButton btnLancarNota = UITheme.createSecondaryButton("Registrar Nota");
        btnLancarNota.addActionListener(e -> executar(() -> { navegarPara("notas"); registrarNota(); }));

        JButton btnPresenca = UITheme.createSecondaryButton("Registrar Presença");
        btnPresenca.addActionListener(e -> executar(() -> { navegarPara("frequencia"); registrarFrequencia(); }));

        JButton btnPagar = UITheme.createSecondaryButton("Registrar Pagamento");
        btnPagar.addActionListener(e -> executar(() -> { navegarPara("mensalidades"); registrarPagamento(); }));

        JButton btnCertificado = UITheme.createSecondaryButton("Emitir Certificado");
        btnCertificado.addActionListener(e -> executar(() -> { navegarPara("certificados"); emitirCertificado(); }));

        gridBotoesAcao.add(btnNovoAluno);
        gridBotoesAcao.add(btnNovoCurso);
        gridBotoesAcao.add(btnNovaTurma);
        gridBotoesAcao.add(btnMatricular);
        gridBotoesAcao.add(btnLancarNota);
        gridBotoesAcao.add(btnPresenca);
        gridBotoesAcao.add(btnPagar);
        gridBotoesAcao.add(btnCertificado);

        cardAcoes.add(gridBotoesAcao, BorderLayout.CENTER);
        centro.add(cardAcoes, BorderLayout.CENTER);

        painelDashboard.add(centro, BorderLayout.CENTER);
        JScrollPane rolagem = new JScrollPane(painelDashboard);
        rolagem.setBorder(null);
        rolagem.setOpaque(false);
        rolagem.getViewport().setOpaque(false);
        rolagem.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        return rolagem;
    }

    // -------------------------------------------------------------------------
    // 2. CURSOS E MÓDULOS
    // -------------------------------------------------------------------------
    private JTable tabelaCursos;
    private DefaultTableModel modeloCursos;

    private JPanel criarSecaoCursos() {
        String[] colunas = {"Código", "Idioma", "Nível", "Carga Horária Total", "Módulos Cadastrados", "Materiais Necessários", "Descrição do Conteúdo"};
        modeloCursos = new DefaultTableModel(colunas, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tabelaCursos = new JTable(modeloCursos);
        UITheme.setupTable(tabelaCursos);
        tabelaCursos.getColumnModel().getColumn(2).setCellRenderer(new StatusBadgeRenderer());

        atualizadores.add(() -> {
            modeloCursos.setRowCount(0);
            for (Curso c : sistema.getCursos()) {
                modeloCursos.addRow(new Object[]{
                        c.getCodigo(),
                        c.getIdioma(),
                        c.getNivel(),
                        c.getCargaHorariaTotal() + " horas",
                        c.getModulos().size() + " módulo(s)",
                        c.getMateriaisNecessarios(),
                        c.getDescricaoConteudo()
                });
            }
        });

        List<JButton> acoes = new ArrayList<>();
        JButton btnNovo = UITheme.createPrimaryButton("+ Novo Curso");
        btnNovo.addActionListener(e -> executar(this::novoCurso));
        acoes.add(btnNovo);

        JButton btnEditar = UITheme.createSecondaryButton("Editar");
        btnEditar.addActionListener(e -> executar(this::editarCurso));
        acoes.add(btnEditar);

        JButton btnRemover = UITheme.createDangerButton("Remover");
        btnRemover.addActionListener(e -> executar(this::removerCurso));
        acoes.add(btnRemover);

        JButton btnAddModulo = UITheme.createSecondaryButton("Adicionar Módulo");
        btnAddModulo.addActionListener(e -> executar(this::novoModulo));
        acoes.add(btnAddModulo);

        JButton btnVerModulos = UITheme.createSecondaryButton("Ver Módulos do Curso");
        btnVerModulos.addActionListener(e -> executar(this::verModulos));
        acoes.add(btnVerModulos);

        return montarPainelCRUD("Cursos e Módulos", "Gerenciamento de programas de ensino, cargas horárias e sequência de módulos.", tabelaCursos, modeloCursos, acoes);
    }

    // -------------------------------------------------------------------------
    // 3. TURMAS E HORÁRIOS
    // -------------------------------------------------------------------------
    private JTable tabelaTurmas;
    private DefaultTableModel modeloTurmas;

    private JPanel criarSecaoTurmas() {
        String[] colunas = {"Código", "Curso", "Professor Responsável", "Período Matrícula", "Mínimo Alunos", "Máximo Alunos", "Horários", "Situação"};
        modeloTurmas = new DefaultTableModel(colunas, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tabelaTurmas = new JTable(modeloTurmas);
        UITheme.setupTable(tabelaTurmas);
        tabelaTurmas.getColumnModel().getColumn(7).setCellRenderer(new StatusBadgeRenderer());

        atualizadores.add(() -> {
            modeloTurmas.setRowCount(0);
            for (Turma t : sistema.getTurmas()) {
                String periodo = UITheme.formatDate(t.getInicioMatricula()) + " até " + UITheme.formatDate(t.getFimMatricula());
                String horarios = t.getHorarios().isEmpty() ? "Nenhum" : t.getHorarios().size() + " horário(s)";
                modeloTurmas.addRow(new Object[]{
                        t.getCodigo(),
                        t.getCurso() != null ? t.getCurso().getCodigo() + " - " + t.getCurso().getIdioma() : "-",
                        t.getProfessor() != null ? t.getProfessor().getNome() : "-",
                        periodo,
                        t.getMinimoAlunos(),
                        t.getMaximoAlunos(),
                        horarios,
                        t.getSituacao()
                });
            }
        });

        List<JButton> acoes = new ArrayList<>();
        JButton btnNova = UITheme.createPrimaryButton("+ Nova Turma");
        btnNova.addActionListener(e -> executar(this::novaTurma));
        acoes.add(btnNova);

        JButton btnHorario = UITheme.createSecondaryButton("Adicionar Horário Semanal");
        btnHorario.addActionListener(e -> executar(this::adicionarHorario));
        acoes.add(btnHorario);

        JButton btnEncontro = UITheme.createSecondaryButton("Agendar Encontro");
        btnEncontro.addActionListener(e -> executar(this::adicionarEncontro));
        acoes.add(btnEncontro);

        JButton btnIniciar = UITheme.createSecondaryButton("Iniciar Turma");
        btnIniciar.addActionListener(e -> executar(this::iniciarTurma));
        acoes.add(btnIniciar);

        JButton btnVerHorarios = UITheme.createSecondaryButton("Ver Horários e Encontros");
        btnVerHorarios.addActionListener(e -> executar(this::verHorariosEEncontrosDaTurma));
        acoes.add(btnVerHorarios);

        return montarPainelCRUD("Turmas e Horários", "Planejamento de turmas, controle de vagas, agenda semanal e encontros de aula.", tabelaTurmas, modeloTurmas, acoes);
    }

    // -------------------------------------------------------------------------
    // 4. ALUNOS
    // -------------------------------------------------------------------------
    private JTable tabelaAlunos;
    private DefaultTableModel modeloAlunos;

    private JPanel criarSecaoAlunos() {
        String[] colunas = {"CPF", "Nome do Aluno", "E-mail", "Telefone", "Data Nascimento", "Endereço", "Interesses"};
        modeloAlunos = new DefaultTableModel(colunas, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tabelaAlunos = new JTable(modeloAlunos);
        UITheme.setupTable(tabelaAlunos);

        atualizadores.add(() -> {
            modeloAlunos.setRowCount(0);
            for (Aluno a : sistema.getAlunos()) {
                String interesses = a.getInteresses().stream()
                        .map(i -> i.getIdioma() + " (" + i.getNivelConhecimento() + ")")
                        .collect(Collectors.joining(", "));
                modeloAlunos.addRow(new Object[]{
                        UITheme.formatCpf(a.getCpf()),
                        a.getNome(),
                        a.getEmail(),
                        a.getTelefone(),
                        UITheme.formatDate(a.getDataNascimento()),
                        a.getEndereco(),
                        interesses.isEmpty() ? "Nenhum informado" : interesses
                });
            }
        });

        List<JButton> acoes = new ArrayList<>();
        JButton btnNovo = UITheme.createPrimaryButton("+ Novo Aluno");
        btnNovo.addActionListener(e -> executar(this::novoAluno));
        acoes.add(btnNovo);

        JButton btnEditar = UITheme.createSecondaryButton("Editar");
        btnEditar.addActionListener(e -> executar(this::editarAluno));
        acoes.add(btnEditar);

        JButton btnRemover = UITheme.createDangerButton("Remover");
        btnRemover.addActionListener(e -> executar(this::removerAluno));
        acoes.add(btnRemover);

        JButton btnHistorico = UITheme.createSecondaryButton("Histórico do Aluno");
        btnHistorico.addActionListener(e -> executar(this::exibirHistoricoCompletoAluno));
        acoes.add(btnHistorico);

        return montarPainelCRUD("Alunos", "Cadastro de estudantes, dados de contato e idiomas de interesse.", tabelaAlunos, modeloAlunos, acoes);
    }

    // -------------------------------------------------------------------------
    // 5. PROFESSORES
    // -------------------------------------------------------------------------
    private JTable tabelaProfessores;
    private DefaultTableModel modeloProfessores;

    private JPanel criarSecaoProfessores() {
        String[] colunas = {"CPF", "Nome do Professor", "E-mail", "Telefone", "Data Contratação", "Formação Acadêmica", "Qualificações"};
        modeloProfessores = new DefaultTableModel(colunas, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tabelaProfessores = new JTable(modeloProfessores);
        UITheme.setupTable(tabelaProfessores);

        atualizadores.add(() -> {
            modeloProfessores.setRowCount(0);
            for (Professor p : sistema.getProfessores()) {
                String qualifs = p.getQualificacoes().stream()
                        .map(q -> q.getIdioma() + " (" + q.getNivelProficiencia() + ")")
                        .collect(Collectors.joining(", "));
                modeloProfessores.addRow(new Object[]{
                        UITheme.formatCpf(p.getCpf()),
                        p.getNome(),
                        p.getEmail(),
                        p.getTelefone(),
                        UITheme.formatDate(p.getDataContratacao()),
                        p.getFormacaoAcademica(),
                        qualifs.isEmpty() ? "Nenhuma" : qualifs
                });
            }
        });

        List<JButton> acoes = new ArrayList<>();
        JButton btnNovo = UITheme.createPrimaryButton("+ Novo Professor");
        btnNovo.addActionListener(e -> executar(this::novoProfessor));
        acoes.add(btnNovo);

        JButton btnEditar = UITheme.createSecondaryButton("Editar");
        btnEditar.addActionListener(e -> executar(this::editarProfessor));
        acoes.add(btnEditar);

        JButton btnRemover = UITheme.createDangerButton("Remover");
        btnRemover.addActionListener(e -> executar(this::removerProfessor));
        acoes.add(btnRemover);

        return montarPainelCRUD("Professores", "Corpo docente da instituição, certificações e idiomas habilitados para lecionar.", tabelaProfessores, modeloProfessores, acoes);
    }

    // -------------------------------------------------------------------------
    // 6. MATRÍCULAS
    // -------------------------------------------------------------------------
    private JTable tabelaMatriculas;
    private DefaultTableModel modeloMatriculas;

    private JPanel criarSecaoMatriculas() {
        String[] colunas = {"Nº Matrícula", "Aluno", "Turma", "Data Matrícula", "Mensalidade (Base)", "Parcelas", "Desconto", "Forma Pagto", "Situação"};
        modeloMatriculas = new DefaultTableModel(colunas, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tabelaMatriculas = new JTable(modeloMatriculas);
        UITheme.setupTable(tabelaMatriculas);
        tabelaMatriculas.getColumnModel().getColumn(8).setCellRenderer(new StatusBadgeRenderer());

        atualizadores.add(() -> {
            modeloMatriculas.setRowCount(0);
            for (Matricula m : sistema.getMatriculas()) {
                modeloMatriculas.addRow(new Object[]{
                        m.getNumero(),
                        m.getAluno() != null ? m.getAluno().getNome() : "-",
                        m.getTurma() != null ? m.getTurma().getCodigo() : "-",
                        UITheme.formatDate(m.getDataMatricula()),
                        UITheme.formatCurrency(m.getValorMensalidade()),
                        m.getQuantidadeParcelas() + "x",
                        UITheme.formatCurrency(m.getDesconto()),
                        m.getFormaPagamento(),
                        m.isAtiva()
                });
            }
        });

        List<JButton> acoes = new ArrayList<>();
        JButton btnNova = UITheme.createPrimaryButton("+ Matricular Aluno");
        btnNova.addActionListener(e -> executar(this::matricular));
        acoes.add(btnNova);

        JButton btnCancelar = UITheme.createDangerButton("Cancelar Matrícula");
        btnCancelar.addActionListener(e -> executar(this::cancelarMatricula));
        acoes.add(btnCancelar);

        JButton btnPorAluno = UITheme.createSecondaryButton("Filtrar por Aluno");
        btnPorAluno.addActionListener(e -> executar(this::matriculasPorAluno));
        acoes.add(btnPorAluno);

        JButton btnPorTurma = UITheme.createSecondaryButton("Filtrar por Turma");
        btnPorTurma.addActionListener(e -> executar(this::matriculasPorTurma));
        acoes.add(btnPorTurma);

        JButton btnMensalidades = UITheme.createSecondaryButton("Ver Mensalidades Desta Matrícula");
        btnMensalidades.addActionListener(e -> executar(this::mensalidadesDaMatricula));
        acoes.add(btnMensalidades);

        return montarPainelCRUD("Matrículas", "Contratos acadêmicos ativos, condições comerciais e parcelamento de mensalidades.", tabelaMatriculas, modeloMatriculas, acoes);
    }

    // -------------------------------------------------------------------------
    // 7. AULAS PARTICULARES
    // -------------------------------------------------------------------------
    private JTable tabelaAulas;
    private DefaultTableModel modeloAulas;

    private JPanel criarSecaoAulasParticulares() {
        String[] colunas = {"ID", "Aluno", "Professor", "Data", "Início", "Término", "Conteúdo Ministrado", "Observações"};
        modeloAulas = new DefaultTableModel(colunas, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tabelaAulas = new JTable(modeloAulas);
        UITheme.setupTable(tabelaAulas);

        atualizadores.add(() -> {
            modeloAulas.setRowCount(0);
            for (AulaParticular a : sistema.getAulasParticulares()) {
                modeloAulas.addRow(new Object[]{
                        a.getIdentificador(),
                        a.getAluno() != null ? a.getAluno().getNome() : "-",
                        a.getProfessor() != null ? a.getProfessor().getNome() : "-",
                        UITheme.formatDate(a.getData()),
                        UITheme.formatTime(a.getHorarioInicio()),
                        UITheme.formatTime(a.getHorarioTermino()),
                        a.getConteudoMinistrado(),
                        a.getObservacoes() != null ? a.getObservacoes() : "-"
                });
            }
        });

        List<JButton> acoes = new ArrayList<>();
        JButton btnAgendar = UITheme.createPrimaryButton("+ Agendar Aula Particular");
        btnAgendar.addActionListener(e -> executar(this::agendarAula));
        acoes.add(btnAgendar);

        JButton btnPorAluno = UITheme.createSecondaryButton("Aulas do Aluno");
        btnPorAluno.addActionListener(e -> executar(this::aulasDoAluno));
        acoes.add(btnPorAluno);

        JButton btnPorProf = UITheme.createSecondaryButton("Aulas do Professor");
        btnPorProf.addActionListener(e -> executar(this::aulasDoProfessor));
        acoes.add(btnPorProf);

        return montarPainelCRUD("Aulas Particulares", "Agendamento de aulas individuais com professores para reforço ou conteúdo sob medida.", tabelaAulas, modeloAulas, acoes);
    }

    // -------------------------------------------------------------------------
    // 8. CERTIFICADOS
    // -------------------------------------------------------------------------
    private JTable tabelaCertificados;
    private DefaultTableModel modeloCertificados;

    private JPanel criarSecaoCertificados() {
        String[] colunas = {"Nº Certificado", "Data Emissão", "Aluno", "Curso", "Módulo", "Carga Horária", "Média Final"};
        modeloCertificados = new DefaultTableModel(colunas, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tabelaCertificados = new JTable(modeloCertificados);
        UITheme.setupTable(tabelaCertificados);

        atualizadores.add(() -> {
            modeloCertificados.setRowCount(0);
            for (Certificado c : sistema.getCertificados()) {
                modeloCertificados.addRow(new Object[]{
                        c.getNumero(),
                        UITheme.formatDate(c.getDataEmissao()),
                        c.getAluno() != null ? c.getAluno().getNome() : "-",
                        c.getCurso() != null ? c.getCurso().getCodigo() : "-",
                        c.getModulo() != null ? c.getModulo().getNome() : "-",
                        c.getCargaHoraria() + "h",
                        UITheme.formatNumber(c.getNotaFinal())
                });
            }
        });

        List<JButton> acoes = new ArrayList<>();
        JButton btnEmitir = UITheme.createPrimaryButton("+ Emitir Certificado");
        btnEmitir.addActionListener(e -> executar(this::emitirCertificado));
        acoes.add(btnEmitir);

        JButton btnDoAluno = UITheme.createSecondaryButton("Certificados do Aluno");
        btnDoAluno.addActionListener(e -> executar(this::certificadosDoAluno));
        acoes.add(btnDoAluno);

        return montarPainelCRUD("Certificados", "Emissão oficial de conclusão de módulos mediante validação de nota e frequência mínimas.", tabelaCertificados, modeloCertificados, acoes);
    }

    // -------------------------------------------------------------------------
    // 9. AVALIAÇÕES
    // -------------------------------------------------------------------------
    private JTable tabelaAvaliacoes;
    private DefaultTableModel modeloAvaliacoes;

    private JPanel criarSecaoAvaliacoes() {
        String[] colunas = {"Identificador", "Módulo", "Tipo", "Data Aplicação", "Valor Máximo", "Peso"};
        modeloAvaliacoes = new DefaultTableModel(colunas, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tabelaAvaliacoes = new JTable(modeloAvaliacoes);
        UITheme.setupTable(tabelaAvaliacoes);
        tabelaAvaliacoes.getColumnModel().getColumn(2).setCellRenderer(new StatusBadgeRenderer());

        atualizadores.add(() -> {
            modeloAvaliacoes.setRowCount(0);
            for (Avaliacao a : sistema.getAvaliacoes()) {
                modeloAvaliacoes.addRow(new Object[]{
                        a.getIdentificador(),
                        a.getModulo() != null ? a.getModulo().getNome() : "-",
                        a.getTipo(),
                        UITheme.formatDate(a.getDataAplicacao()),
                        UITheme.formatNumber(a.getValorMaximo()),
                        UITheme.formatNumber(a.getPeso())
                });
            }
        });

        List<JButton> acoes = new ArrayList<>();
        JButton btnNova = UITheme.createPrimaryButton("+ Nova Avaliação");
        btnNova.addActionListener(e -> executar(this::novaAvaliacao));
        acoes.add(btnNova);

        return montarPainelCRUD("Avaliações Cadastradas", "Instrumentos de avaliação (provas, trabalhos e exercícios) associados aos módulos.", tabelaAvaliacoes, modeloAvaliacoes, acoes);
    }

    // -------------------------------------------------------------------------
    // 10. NOTAS
    // -------------------------------------------------------------------------
    private JTable tabelaNotas;
    private DefaultTableModel modeloNotas;

    private JPanel criarSecaoNotas() {
        String[] colunas = {"Aluno", "Avaliação", "Pontuação Obtida", "Valor Máx", "Observações"};
        modeloNotas = new DefaultTableModel(colunas, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tabelaNotas = new JTable(modeloNotas);
        UITheme.setupTable(tabelaNotas);

        atualizadores.add(() -> {
            modeloNotas.setRowCount(0);
            for (Nota n : sistema.getNotas()) {
                // Localiza o aluno dono desta nota
                String nomeAluno = "-";
                for (Matricula m : sistema.getMatriculas()) {
                    if (m.getNotas().contains(n) && m.getAluno() != null) {
                        nomeAluno = m.getAluno().getNome();
                        break;
                    }
                }
                modeloNotas.addRow(new Object[]{
                        nomeAluno,
                        n.getAvaliacao() != null ? n.getAvaliacao().getIdentificador() : "-",
                        UITheme.formatNumber(n.getPontuacao()),
                        n.getAvaliacao() != null ? UITheme.formatNumber(n.getAvaliacao().getValorMaximo()) : "-",
                        n.getObservacoes() != null ? n.getObservacoes() : "-"
                });
            }
        });

        List<JButton> acoes = new ArrayList<>();
        JButton btnRegistrar = UITheme.createPrimaryButton("Registrar Nota");
        btnRegistrar.addActionListener(e -> executar(this::registrarNota));
        acoes.add(btnRegistrar);

        JButton btnCalcularMedia = UITheme.createSecondaryButton("Calcular Média Final");
        btnCalcularMedia.addActionListener(e -> executar(this::calcularMedia));
        acoes.add(btnCalcularMedia);

        return montarPainelCRUD("Lançamento de Notas", "Registro de notas obtidas por alunos em avaliações e cálculo da média ponderada final.", tabelaNotas, modeloNotas, acoes);
    }

    // -------------------------------------------------------------------------
    // 11. FREQUÊNCIA
    // -------------------------------------------------------------------------
    private JTable tabelaFrequencia;
    private DefaultTableModel modeloFrequencia;

    private JPanel criarSecaoFrequencia() {
        String[] colunas = {"Data da Aula", "Aluno", "Turma", "Módulo", "Presença / Falta"};
        modeloFrequencia = new DefaultTableModel(colunas, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tabelaFrequencia = new JTable(modeloFrequencia);
        UITheme.setupTable(tabelaFrequencia);
        tabelaFrequencia.getColumnModel().getColumn(4).setCellRenderer(new StatusBadgeRenderer());

        atualizadores.add(() -> {
            modeloFrequencia.setRowCount(0);
            for (RegistroFrequencia reg : sistema.getRegistrosFrequencia()) {
                String alunoNome = "-";
                String turmaCod = "-";
                String moduloNome = "-";

                for (Matricula m : sistema.getMatriculas()) {
                    if (m.getRegistrosFrequencia().contains(reg)) {
                        if (m.getAluno() != null) alunoNome = m.getAluno().getNome();
                        if (m.getTurma() != null) turmaCod = m.getTurma().getCodigo();
                        break;
                    }
                }
                if (reg.getEncontro() != null && reg.getEncontro().getModulo() != null) {
                    moduloNome = reg.getEncontro().getModulo().getNome();
                }

                modeloFrequencia.addRow(new Object[]{
                        UITheme.formatDate(reg.getDataAula()),
                        alunoNome,
                        turmaCod,
                        moduloNome,
                        reg.isPresente()
                });
            }
        });

        List<JButton> acoes = new ArrayList<>();
        JButton btnRegistrar = UITheme.createPrimaryButton("Registrar Presença / Falta");
        btnRegistrar.addActionListener(e -> executar(this::registrarFrequencia));
        acoes.add(btnRegistrar);

        JButton btnCalcularFreq = UITheme.createSecondaryButton("Calcular Frequência do Módulo");
        btnCalcularFreq.addActionListener(e -> executar(this::calcularFrequencia));
        acoes.add(btnCalcularFreq);

        return montarPainelCRUD("Controle de Frequência", "Chamada de aulas, registros de presença e conferência do percentual mínimo para aprovação.", tabelaFrequencia, modeloFrequencia, acoes);
    }

    // -------------------------------------------------------------------------
    // 12. MENSALIDADES
    // -------------------------------------------------------------------------
    private JTable tabelaMensalidades;
    private DefaultTableModel modeloMensalidades;

    private JPanel criarSecaoMensalidades() {
        String[] colunas = {"Nº Parcela", "Matrícula", "Aluno", "Vencimento", "Valor Original", "Valor Atualizado", "Situação", "Pago em"};
        modeloMensalidades = new DefaultTableModel(colunas, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tabelaMensalidades = new JTable(modeloMensalidades);
        UITheme.setupTable(tabelaMensalidades);
        tabelaMensalidades.getColumnModel().getColumn(6).setCellRenderer(new StatusBadgeRenderer());

        atualizadores.add(() -> {
            modeloMensalidades.setRowCount(0);
            for (Mensalidade m : sistema.getMensalidades()) {
                String matNum = m.getMatricula() != null ? m.getMatricula().getNumero() : "-";
                String alunoNome = (m.getMatricula() != null && m.getMatricula().getAluno() != null) ? m.getMatricula().getAluno().getNome() : "-";
                modeloMensalidades.addRow(new Object[]{
                        m.getNumero() + "ª Parcela",
                        matNum,
                        alunoNome,
                        UITheme.formatDate(m.getVencimento()),
                        UITheme.formatCurrency(m.getValorOriginal()),
                        UITheme.formatCurrency(m.getValorAtualizado()),
                        m.getSituacao(),
                        UITheme.formatDate(m.getDataPagamento())
                });
            }
        });

        List<JButton> acoes = new ArrayList<>();
        JButton btnCalcularAtraso = UITheme.createSecondaryButton("Calcular Total com Juros/Multa");
        btnCalcularAtraso.addActionListener(e -> executar(this::calcularAtraso));
        acoes.add(btnCalcularAtraso);

        JButton btnPagar = UITheme.createPrimaryButton("Registrar Pagamento");
        btnPagar.addActionListener(e -> executar(this::registrarPagamento));
        acoes.add(btnPagar);

        JButton btnPorMatricula = UITheme.createSecondaryButton("Mensalidades da Matrícula");
        btnPorMatricula.addActionListener(e -> executar(this::mensalidadesDaMatricula));
        acoes.add(btnPorMatricula);

        return montarPainelCRUD("Mensalidades", "Cobranças geradas para as matrículas, incidência de multa/juros em atraso e quitações.", tabelaMensalidades, modeloMensalidades, acoes);
    }

    // -------------------------------------------------------------------------
    // 13. PAGAMENTOS
    // -------------------------------------------------------------------------
    private JTable tabelaPagamentos;
    private DefaultTableModel modeloPagamentos;

    private JPanel criarSecaoPagamentos() {
        String[] colunas = {"Data do Pagamento", "Valor Pago", "Forma de Pagamento", "Matrícula Referente", "Aluno"};
        modeloPagamentos = new DefaultTableModel(colunas, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tabelaPagamentos = new JTable(modeloPagamentos);
        UITheme.setupTable(tabelaPagamentos);

        atualizadores.add(() -> {
            modeloPagamentos.setRowCount(0);
            for (Pagamento p : sistema.getPagamentos()) {
                String mat = "-";
                String aluno = "-";
                for (Mensalidade m : sistema.getMensalidades()) {
                    if (m.getPagamento() == p) {
                        if (m.getMatricula() != null) {
                            mat = m.getMatricula().getNumero() + " (Parcela " + m.getNumero() + ")";
                            if (m.getMatricula().getAluno() != null) aluno = m.getMatricula().getAluno().getNome();
                        }
                        break;
                    }
                }
                modeloPagamentos.addRow(new Object[]{
                        UITheme.formatDate(p.getData()),
                        UITheme.formatCurrency(p.getValorPago()),
                        p.getFormaPagamento(),
                        mat,
                        aluno
                });
            }
        });

        return montarPainelCRUD("Pagamentos Realizados", "Histórico financeiro com comprovantes de quitações de mensalidades.", tabelaPagamentos, modeloPagamentos, Collections.emptyList());
    }

    // -------------------------------------------------------------------------
    // 14. MATERIAIS
    // -------------------------------------------------------------------------
    private JTable tabelaMateriais;
    private DefaultTableModel modeloMateriais;

    private JPanel criarSecaoMateriais() {
        String[] colunas = {"Código", "Título do Material", "Editora", "Edição", "Tipo", "Preço Unitário"};
        modeloMateriais = new DefaultTableModel(colunas, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tabelaMateriais = new JTable(modeloMateriais);
        UITheme.setupTable(tabelaMateriais);

        atualizadores.add(() -> {
            modeloMateriais.setRowCount(0);
            for (Material m : sistema.getMateriais()) {
                modeloMateriais.addRow(new Object[]{
                        m.getCodigo(),
                        m.getTitulo(),
                        m.getEditora(),
                        m.getEdicao(),
                        m.getTipo(),
                        UITheme.formatCurrency(m.getPreco())
                });
            }
        });

        List<JButton> acoes = new ArrayList<>();
        JButton btnNovo = UITheme.createPrimaryButton("+ Cadastrar Material");
        btnNovo.addActionListener(e -> executar(this::novoMaterial));
        acoes.add(btnNovo);

        return montarPainelCRUD("Materiais Didáticos", "Acervo de livros, apostilas e recursos didáticos comercializados aos alunos.", tabelaMateriais, modeloMateriais, acoes);
    }

    // -------------------------------------------------------------------------
    // 15. VENDAS
    // -------------------------------------------------------------------------
    private JTable tabelaVendas;
    private DefaultTableModel modeloVendas;

    private JPanel criarSecaoVendas() {
        String[] colunas = {"Nº Venda", "Aluno", "Data", "Itens Comprados", "Valor Total"};
        modeloVendas = new DefaultTableModel(colunas, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tabelaVendas = new JTable(modeloVendas);
        UITheme.setupTable(tabelaVendas);

        atualizadores.add(() -> {
            modeloVendas.setRowCount(0);
            for (Venda v : sistema.getVendas()) {
                String itens = v.getItens().stream()
                        .map(i -> i.getMaterial().getTitulo() + " (x" + i.getQuantidade() + ")")
                        .collect(Collectors.joining(", "));
                modeloVendas.addRow(new Object[]{
                        v.getNumero(),
                        v.getAluno() != null ? v.getAluno().getNome() : "-",
                        UITheme.formatDate(v.getData()),
                        itens,
                        UITheme.formatCurrency(v.getValorTotal())
                });
            }
        });

        List<JButton> acoes = new ArrayList<>();
        JButton btnNova = UITheme.createPrimaryButton("Registrar Nova Venda");
        btnNova.addActionListener(e -> executar(this::registrarVenda));
        acoes.add(btnNova);

        JButton btnPorAluno = UITheme.createSecondaryButton("Vendas por Aluno");
        btnPorAluno.addActionListener(e -> executar(this::vendasDoAluno));
        acoes.add(btnPorAluno);

        return montarPainelCRUD("Vendas de Materiais", "Venda de apostilas e livros diretamente vinculada ao cadastro do aluno.", tabelaVendas, modeloVendas, acoes);
    }

    // -------------------------------------------------------------------------
    // 16. PARÂMETROS DO SISTEMA
    // -------------------------------------------------------------------------
    private JPanel criarSecaoParametros() {
        JPanel painel = new JPanel(new BorderLayout(14, 16));
        painel.setOpaque(false);

        JPanel header = UITheme.createHeaderPanel("Parâmetros Globais do Sistema",
                "Definição de taxas financeiras, critérios acadêmicos e exigências para aprovação.");
        painel.add(header, BorderLayout.NORTH);

        JPanel centro = new JPanel(new FlowLayout(FlowLayout.LEFT));
        centro.setOpaque(false);

        JPanel card = UITheme.createCardPanel();
        card.setLayout(new GridBagLayout());
        card.setPreferredSize(new Dimension(580, 360));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblInfo = new JLabel("Configurações Financeiras e Pedagógicas");
        lblInfo.setFont(UITheme.FONT_SECTION);
        lblInfo.setForeground(UITheme.COLOR_TEXT_MAIN);
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        card.add(lblInfo, gbc);

        gbc.gridwidth = 1;

        // Multa
        gbc.gridx = 0; gbc.gridy = 1;
        card.add(new JLabel("Taxa de Multa por Atraso (%):"), gbc);
        JTextField tfMulta = UITheme.createTextField(10);
        gbc.gridx = 1;
        card.add(tfMulta, gbc);

        // Juros
        gbc.gridx = 0; gbc.gridy = 2;
        card.add(new JLabel("Taxa de Juros Mensal (% ao mês):"), gbc);
        JTextField tfJuros = UITheme.createTextField(10);
        gbc.gridx = 1;
        card.add(tfJuros, gbc);

        // Nota Mínima
        gbc.gridx = 0; gbc.gridy = 3;
        card.add(new JLabel("Nota Mínima para Aprovação (0 a 10):"), gbc);
        JTextField tfNota = UITheme.createTextField(10);
        gbc.gridx = 1;
        card.add(tfNota, gbc);

        // Frequência Mínima
        gbc.gridx = 0; gbc.gridy = 4;
        card.add(new JLabel("Frequência Mínima Exigida (%):"), gbc);
        JTextField tfFreq = UITheme.createTextField(10);
        gbc.gridx = 1;
        card.add(tfFreq, gbc);

        JButton btnSalvar = UITheme.createPrimaryButton("Salvar Parâmetros");
        btnSalvar.addActionListener(e -> {
            try {
                BigDecimal multa = new BigDecimal(tfMulta.getText().trim().replace(',', '.')).movePointLeft(2);
                BigDecimal juros = new BigDecimal(tfJuros.getText().trim().replace(',', '.')).movePointLeft(2);
                BigDecimal nota = new BigDecimal(tfNota.getText().trim().replace(',', '.'));
                BigDecimal freq = new BigDecimal(tfFreq.getText().trim().replace(',', '.')).movePointLeft(2);

                if (multa.signum() < 0 || juros.signum() < 0) {
                    throw new IllegalArgumentException("As taxas de multa e juros não podem ser negativas.");
                }
                if (nota.signum() < 0 || nota.compareTo(BigDecimal.TEN) > 0) {
                    throw new IllegalArgumentException("A nota mínima deve estar entre 0 e 10.");
                }
                if (freq.signum() < 0 || freq.compareTo(BigDecimal.ONE) > 0) {
                    throw new IllegalArgumentException("A frequência mínima deve estar entre 0% e 100%.");
                }

                sistema.setTaxaMulta(multa);
                sistema.setTaxaJurosMensal(juros);
                sistema.setNotaMinima(nota);
                sistema.setFrequenciaMinima(freq);

                lblStatus.setText("Parâmetros atualizados com sucesso.");
                JOptionPane.showMessageDialog(janela, "Parâmetros globais salvos com sucesso!", "Configurações Atualizadas", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(janela, "Erro ao atualizar parâmetros: " + ex.getMessage(), "Validação", JOptionPane.ERROR_MESSAGE);
            }
        });

        gbc.gridx = 1; gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.LINE_END;
        card.add(btnSalvar, gbc);

        atualizadores.add(() -> {
            tfMulta.setText(sistema.getTaxaMulta().movePointRight(2).toString());
            tfJuros.setText(sistema.getTaxaJurosMensal().movePointRight(2).toString());
            tfNota.setText(sistema.getNotaMinima().toString());
            tfFreq.setText(sistema.getFrequenciaMinima().movePointRight(2).toString());
        });

        centro.add(card);
        painel.add(centro, BorderLayout.CENTER);
        return painel;
    }

    // =========================================================================
    // UTILITÁRIO PARA MONTAR PAINEL PADRÃO DE TELA (HEADER + BUSCA + TABELA + AÇÕES)
    // =========================================================================
    private JPanel montarPainelCRUD(String titulo, String subtitulo, JTable tabela, DefaultTableModel modelo, List<JButton> botoesAcao) {
        JPanel pagina = new JPanel(new BorderLayout(12, 14));
        pagina.setOpaque(false);

        // Header da Tela
        JPanel header = UITheme.createHeaderPanel(titulo, subtitulo);
        pagina.add(header, BorderLayout.NORTH);

        // Card com Tabela, Busca e Barra de Ações
        JPanel card = UITheme.createCardPanel();
        card.setLayout(new BorderLayout(10, 12));

        // Busca em uma linha e ações em uma grade, para evitar cortes em janelas menores.
        JPanel barraFerramentas = new JPanel(new BorderLayout(8, 8));
        barraFerramentas.setOpaque(false);

        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(modelo);
        tabela.setRowSorter(sorter);

        JPanel searchBar = UITheme.createSearchBar("Buscar em " + titulo.toLowerCase() + "...", texto -> {
            if (texto.isEmpty()) {
                sorter.setRowFilter(null);
            } else {
                sorter.setRowFilter(RowFilter.regexFilter("(?i)" + java.util.regex.Pattern.quote(texto)));
            }
        });
        barraFerramentas.add(searchBar, BorderLayout.NORTH);

        if (botoesAcao != null && !botoesAcao.isEmpty()) {
            int colunas = Math.min(4, botoesAcao.size());
            JPanel painelBotoes = new JPanel(new GridLayout(0, colunas, 8, 6));
            painelBotoes.setOpaque(false);
            for (JButton b : botoesAcao) painelBotoes.add(b);
            barraFerramentas.add(painelBotoes, BorderLayout.CENTER);
        }

        card.add(barraFerramentas, BorderLayout.NORTH);

        // Tabela envolvida em JScrollPane estilizado
        JScrollPane scroll = new JScrollPane(tabela);
        scroll.setBorder(new LineBorder(UITheme.COLOR_BORDER, 1));
        scroll.getViewport().setBackground(Color.WHITE);
        card.add(scroll, BorderLayout.CENTER);

        pagina.add(card, BorderLayout.CENTER);
        return pagina;
    }

    // =========================================================================
    // EXECUÇÃO SEGURA DE OPERAÇÕES E CONTROLE DE EXCEÇÕES
    // =========================================================================
    private void executar(Runnable acao) {
        try {
            acao.run();
            atualizarTodasTabelas();
            lblStatus.setText("Operação realizada com sucesso.");
        } catch (OperacaoCancelada ignorada) {
            lblStatus.setText(" Operação cancelada pelo usuário.");
        } catch (RegraNegocioException | IllegalArgumentException erro) {
            lblStatus.setText("Validação de negócio: " + erro.getMessage());
            JOptionPane.showMessageDialog(janela, erro.getMessage(), "Validação de Negócio", JOptionPane.WARNING_MESSAGE);
        } catch (RuntimeException erro) {
            lblStatus.setText(" Erro na operação: " + erro.getMessage());
            JOptionPane.showMessageDialog(janela, erro.getMessage() == null ? "Não foi possível concluir a operação." : erro.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void atualizarTodasTabelas() {
        atualizadores.forEach(Runnable::run);
    }

    // =========================================================================
    // IMPLEMENTAÇÃO DOS FORMULÁRIOS E DIÁLOGOS DE AÇÃO
    // =========================================================================

    // --- CURSO E MÓDULOS ---
    private void novoCurso() {
        UIFormDialog form = new UIFormDialog(janela, "Cadastrar Novo Curso", "Preencha as informações do curso de idiomas.");
        form.addTextField("codigo", "Código do Curso", "", true);
        form.addTextField("idioma", "Idioma", "", true);
        form.addComboBox("nivel", "Nível de Ensino", Arrays.asList(NivelCurso.values()), NivelCurso.BASICO, NivelCurso::toString, true);
        form.addIntegerSpinner("carga", "Carga Horária Total (horas)", 60, 1, 1000, 10, true);
        form.addTextArea("descricao", "Descrição do Conteúdo", "", 3, true);
        form.addTextField("material", "Materiais Didáticos Necessários", "", true);

        if (!form.showDialog()) throw new OperacaoCancelada();

        Curso curso = new Curso(
                form.getText("codigo"),
                form.getText("idioma"),
                form.getSelected("nivel"),
                form.getInteger("carga"),
                form.getText("descricao"),
                form.getText("material")
        );
        sistema.cadastrarCurso(curso);
    }

    private void editarCurso() {
        Curso curso = obterCursoSelecionado();
        UIFormDialog form = new UIFormDialog(janela, "Editar Curso [" + curso.getCodigo() + "]", "Altere os dados do curso existente.");
        form.addTextField("idioma", "Idioma", curso.getIdioma(), true);
        form.addComboBox("nivel", "Nível de Ensino", Arrays.asList(NivelCurso.values()), curso.getNivel(), NivelCurso::toString, true);
        form.addIntegerSpinner("carga", "Carga Horária Total (horas)", curso.getCargaHorariaTotal(), 1, 1000, 10, true);
        form.addTextArea("descricao", "Descrição do Conteúdo", curso.getDescricaoConteudo(), 3, true);
        form.addTextField("material", "Materiais Didáticos Necessários", curso.getMateriaisNecessarios(), true);

        if (!form.showDialog()) throw new OperacaoCancelada();

        curso.setIdioma(form.getText("idioma"));
        curso.setNivel(form.getSelected("nivel"));
        curso.setCargaHorariaTotal(form.getInteger("carga"));
        curso.setDescricaoConteudo(form.getText("descricao"));
        curso.setMateriaisNecessarios(form.getText("material"));
        sistema.atualizarCurso(curso);
    }

    private void removerCurso() {
        Curso curso = obterCursoSelecionado();
        int resp = JOptionPane.showConfirmDialog(janela,
                "Tem certeza de que deseja remover o curso '" + curso.getCodigo() + " - " + curso.getIdioma() + "'?",
                "Confirmar Remoção", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (resp != JOptionPane.YES_OPTION) throw new OperacaoCancelada();
        sistema.removerCurso(curso.getCodigo());
    }

    private void novoModulo() {
        Curso curso = obterCursoSelecionado();
        int proximoNumero = curso.getModulos().size() + 1;

        UIFormDialog form = new UIFormDialog(janela, "Adicionar Módulo ao Curso", "Curso selecionado: " + curso.getCodigo() + " - " + curso.getIdioma());
        form.addIntegerSpinner("numero", "Número Sequencial do Módulo", proximoNumero, 1, 100, 1, true);
        form.addTextField("nome", "Nome do Módulo", "", true);
        form.addIntegerSpinner("carga", "Carga Horária (horas)", 40, 1, 500, 5, true);
        form.addTextField("horario", "Horário de Aprendizagem (Ex: Noite, 19h)", "Noite", true);

        List<Modulo> modulosExistentes = new ArrayList<>(curso.getModulos());
        if (!modulosExistentes.isEmpty()) {
            form.addComboBox("prereq", "Pré-requisito (opcional)", modulosExistentes, null, Modulo::getNome, false);
        }

        if (!form.showDialog()) throw new OperacaoCancelada();

        Modulo modulo = new Modulo(
                form.getInteger("numero"),
                form.getText("nome"),
                form.getInteger("carga"),
                form.getText("horario")
        );

        if (!modulosExistentes.isEmpty()) {
            Modulo pre = form.getSelected("prereq");
            if (pre != null) modulo.adicionarPreRequisito(pre);
        }

        sistema.cadastrarModulo(curso.getCodigo(), modulo);
    }

    private void verModulos() {
        Curso curso = obterCursoSelecionado();
        TableDetailDialog dlg = new TableDetailDialog(janela,
                "Módulos do Curso: " + curso.getCodigo(),
                "Idioma: " + curso.getIdioma() + "  |  Nível: " + curso.getNivel(),
                new String[]{"Nº", "Nome do Módulo", "Carga Horária", "Horário Sugerido", "Pré-requisitos"}
        );

        for (Modulo m : sistema.consultarModulosCurso(curso.getCodigo())) {
            String pre = m.getPreRequisitos().stream().map(p -> "Módulo " + p.getNumero()).collect(Collectors.joining(", "));
            dlg.adicionarLinha(new Object[]{
                    m.getNumero(),
                    m.getNome(),
                    m.getCargaHoraria() + "h",
                    m.getHorarioAprendizagem(),
                    pre.isEmpty() ? "Nenhum" : pre
            });
        }
        dlg.setVisible(true);
    }

    private Curso obterCursoSelecionado() {
        int row = tabelaCursos.getSelectedRow();
        if (row != -1) {
            int modelRow = tabelaCursos.convertRowIndexToModel(row);
            String codigo = (String) modeloCursos.getValueAt(modelRow, 0);
            Curso c = sistema.consultarCurso(codigo);
            if (c != null) return c;
        }
        if (sistema.getCursos().isEmpty()) {
            throw new RegraNegocioException("Nenhum curso cadastrado no sistema.");
        }
        return escolherDialogo("Selecione o Curso:", sistema.getCursos(), Curso::getCodigo);
    }

    // --- ALUNOS ---
    private void novoAluno() {
        UIFormDialog form = new UIFormDialog(janela, "Cadastrar Novo Aluno", "Informe os dados pessoais e interesses do aluno.");
        form.addTextField("nome", "Nome Completo", "", true);
        form.addTextField("cpf", "CPF (apenas números ou formatado)", "", true);
        form.addDateField("nasc", "Data de Nascimento", LocalDate.of(2000, 1, 1), true);
        form.addTextField("endereco", "Endereço Completo", "", true);
        form.addTextField("telefone", "Telefone de Contato", "", true);
        form.addTextField("email", "E-mail", "", true);
        form.addTextField("idioma", "Idioma de Interesse (opcional)", "Inglês", false);
        form.addComboBox("nivelInteresse", "Nível Atual no Idioma", Arrays.asList(NivelCurso.values()), NivelCurso.BASICO, NivelCurso::toString, false);

        if (!form.showDialog()) throw new OperacaoCancelada();

        Aluno aluno = new Aluno(
                form.getText("nome"),
                form.getText("cpf"),
                form.getDate("nasc"),
                form.getText("endereco"),
                form.getText("telefone"),
                form.getText("email")
        );

        String idioma = form.getText("idioma");
        if (!idioma.isEmpty()) {
            NivelCurso nivel = form.getSelected("nivelInteresse");
            aluno.adicionarInteresse(new IdiomaInteresse(idioma, nivel != null ? nivel : NivelCurso.BASICO));
        }

        sistema.cadastrarAluno(aluno);
    }

    private void editarAluno() {
        Aluno aluno = obterAlunoSelecionado();
        UIFormDialog form = new UIFormDialog(janela, "Editar Aluno: " + aluno.getNome(), "Atualize as informações cadastrais.");
        form.addTextField("nome", "Nome Completo", aluno.getNome(), true);
        form.addDateField("nasc", "Data de Nascimento", aluno.getDataNascimento(), true);
        form.addTextField("endereco", "Endereço Completo", aluno.getEndereco(), true);
        form.addTextField("telefone", "Telefone", aluno.getTelefone(), true);
        form.addTextField("email", "E-mail", aluno.getEmail(), true);

        if (!form.showDialog()) throw new OperacaoCancelada();

        Aluno editado = new Aluno(
                form.getText("nome"),
                aluno.getCpf(),
                form.getDate("nasc"),
                form.getText("endereco"),
                form.getText("telefone"),
                form.getText("email")
        );
        aluno.getInteresses().forEach(editado::adicionarInteresse);
        sistema.atualizarAluno(editado);
    }

    private void removerAluno() {
        Aluno aluno = obterAlunoSelecionado();
        int resp = JOptionPane.showConfirmDialog(janela,
                "Tem certeza de que deseja remover o aluno '" + aluno.getNome() + "' (CPF: " + aluno.getCpf() + ")?",
                "Confirmar Remoção", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (resp != JOptionPane.YES_OPTION) throw new OperacaoCancelada();
        sistema.removerAluno(aluno.getCpf());
    }

    private void exibirHistoricoCompletoAluno() {
        Aluno aluno = obterAlunoSelecionado();
        List<Matricula> matriculas = sistema.consultarMatriculasPorAluno(aluno.getCpf());
        List<Certificado> certificados = sistema.consultarCertificadosPorAluno(aluno.getCpf());
        List<Venda> vendas = sistema.consultarVendasPorAluno(aluno.getCpf());

        StringBuilder sb = new StringBuilder();
        sb.append("=== HISTÓRICO ACADÊMICO E FINANCEIRO DO ALUNO ===\n");
        sb.append("Nome: ").append(aluno.getNome()).append("  |  CPF: ").append(aluno.getCpf()).append("\n");
        sb.append("E-mail: ").append(aluno.getEmail()).append("  |  Tel: ").append(aluno.getTelefone()).append("\n\n");

        sb.append("--- MATRÍCULAS (").append(matriculas.size()).append(") ---\n");
        for (Matricula m : matriculas) {
            sb.append("• Matrícula: ").append(m.getNumero())
                    .append(" | Turma: ").append(m.getTurma().getCodigo())
                    .append(" | Status: ").append(m.isAtiva() ? "ATIVA" : "INATIVA")
                    .append(" | Mensalidade: ").append(UITheme.formatCurrency(m.getValorMensalidade()))
                    .append("\n");
        }

        sb.append("\n--- CERTIFICADOS (").append(certificados.size()).append(") ---\n");
        for (Certificado c : certificados) {
            sb.append("• ").append(c.getNumero())
                    .append(" | Módulo: ").append(c.getModulo().getNome())
                    .append(" | Média: ").append(c.getNotaFinal())
                    .append(" | Emitido em: ").append(UITheme.formatDate(c.getDataEmissao()))
                    .append("\n");
        }

        sb.append("\n--- COMPRAS / VENDAS (").append(vendas.size()).append(") ---\n");
        for (Venda v : vendas) {
            sb.append("• Pedido ").append(v.getNumero())
                    .append(" | Total: ").append(UITheme.formatCurrency(v.getValorTotal()))
                    .append(" | Data: ").append(UITheme.formatDate(v.getData()))
                    .append("\n");
        }

        TableDetailDialog.exibirTextoFormatado(janela, "Histórico do Aluno", aluno.getNome(), sb.toString());
    }

    private Aluno obterAlunoSelecionado() {
        int row = tabelaAlunos.getSelectedRow();
        if (row != -1) {
            int modelRow = tabelaAlunos.convertRowIndexToModel(row);
            String cpf = (String) modeloAlunos.getValueAt(modelRow, 0);
            Aluno a = sistema.consultarAluno(cpf);
            if (a != null) return a;
        }
        if (sistema.getAlunos().isEmpty()) {
            throw new RegraNegocioException("Nenhum aluno cadastrado.");
        }
        return escolherDialogo("Selecione o Aluno:", sistema.getAlunos(), a -> a.getNome() + " (" + a.getCpf() + ")");
    }

    // --- PROFESSORES ---
    private void novoProfessor() {
        UIFormDialog form = new UIFormDialog(janela, "Cadastrar Novo Professor", "Informe dados cadastrais e qualificações do docente.");
        form.addTextField("nome", "Nome Completo", "", true);
        form.addTextField("cpf", "CPF", "", true);
        form.addTextField("telefone", "Telefone", "", true);
        form.addTextField("email", "E-mail", "", true);
        form.addDateField("contratacao", "Data de Contratação", LocalDate.now(), true);
        form.addTextField("formacao", "Formação Acadêmica", "Licenciatura em Letras", true);
        form.addTextField("idioma", "Idioma que pode ministrar", "Inglês", true);
        form.addComboBox("proficiencia", "Nível de Proficiência", Arrays.asList(NivelCurso.values()), NivelCurso.AVANCADO, NivelCurso::toString, true);

        if (!form.showDialog()) throw new OperacaoCancelada();

        Professor professor = new Professor(
                form.getText("nome"),
                form.getText("cpf"),
                form.getText("telefone"),
                form.getText("email"),
                form.getDate("contratacao"),
                form.getText("formacao")
        );
        professor.adicionarQualificacao(new IdiomaProfissional(form.getText("idioma"), form.getSelected("proficiencia")));
        sistema.cadastrarProfessor(professor);
    }

    private void editarProfessor() {
        Professor prof = obterProfessorSelecionado();
        UIFormDialog form = new UIFormDialog(janela, "Editar Professor: " + prof.getNome(), "Atualize os dados profissionais.");
        form.addTextField("nome", "Nome Completo", prof.getNome(), true);
        form.addTextField("telefone", "Telefone", prof.getTelefone(), true);
        form.addTextField("email", "E-mail", prof.getEmail(), true);
        form.addDateField("contratacao", "Data de Contratação", prof.getDataContratacao(), true);
        form.addTextField("formacao", "Formação Acadêmica", prof.getFormacaoAcademica(), true);

        if (!form.showDialog()) throw new OperacaoCancelada();

        Professor editado = new Professor(
                form.getText("nome"),
                prof.getCpf(),
                form.getText("telefone"),
                form.getText("email"),
                form.getDate("contratacao"),
                form.getText("formacao")
        );
        prof.getQualificacoes().forEach(editado::adicionarQualificacao);
        sistema.atualizarProfessor(editado);
    }

    private void removerProfessor() {
        Professor prof = obterProfessorSelecionado();
        int resp = JOptionPane.showConfirmDialog(janela,
                "Tem certeza de que deseja remover o professor '" + prof.getNome() + "'?",
                "Confirmar Remoção", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (resp != JOptionPane.YES_OPTION) throw new OperacaoCancelada();
        sistema.removerProfessor(prof.getCpf());
    }

    private Professor obterProfessorSelecionado() {
        int row = tabelaProfessores.getSelectedRow();
        if (row != -1) {
            int modelRow = tabelaProfessores.convertRowIndexToModel(row);
            String cpf = (String) modeloProfessores.getValueAt(modelRow, 0);
            Professor p = sistema.consultarProfessor(cpf);
            if (p != null) return p;
        }
        if (sistema.getProfessores().isEmpty()) {
            throw new RegraNegocioException("Nenhum professor cadastrado.");
        }
        return escolherDialogo("Selecione o Professor:", sistema.getProfessores(), p -> p.getNome() + " (" + p.getCpf() + ")");
    }

    // --- TURMAS ---
    private void novaTurma() {
        if (sistema.getCursos().isEmpty()) throw new RegraNegocioException("Cadastre ao menos um curso antes de criar turmas.");
        if (sistema.getProfessores().isEmpty()) throw new RegraNegocioException("Cadastre ao menos um professor antes de criar turmas.");

        UIFormDialog form = new UIFormDialog(janela, "Criar Nova Turma", "Selecione o curso, professor habilitado e período letivo.");
        form.addTextField("codigo", "Código da Turma", "TURMA-" + (sistema.getTurmas().size() + 1), true);
        form.addComboBox("curso", "Curso", sistema.getCursos(), sistema.getCursos().get(0), c -> c.getCodigo() + " - " + c.getIdioma() + " (" + c.getNivel() + ")", true);
        form.addComboBox("professor", "Professor Responsável", sistema.getProfessores(), sistema.getProfessores().get(0), Professor::getNome, true);
        form.addDateField("inicio", "Início das Matrículas", LocalDate.now(), true);
        form.addDateField("fim", "Fim das Matrículas", LocalDate.now().plusMonths(1), true);
        form.addIntegerSpinner("minimo", "Mínimo de Alunos", 5, 1, 100, 1, true);
        form.addIntegerSpinner("maximo", "Máximo de Alunos (Capacidade)", 15, 1, 100, 1, true);

        if (!form.showDialog()) throw new OperacaoCancelada();

        Curso curso = form.getSelected("curso");
        Professor professor = form.getSelected("professor");

        Turma turma = new Turma(
                form.getText("codigo"),
                curso,
                professor,
                form.getDate("inicio"),
                form.getDate("fim"),
                form.getInteger("minimo"),
                form.getInteger("maximo")
        );
        sistema.cadastrarTurma(turma);
    }

    private void adicionarHorario() {
        Turma turma = obterTurmaSelecionada();
        UIFormDialog form = new UIFormDialog(janela, "Adicionar Horário Semanal", "Turma: " + turma.getCodigo());
        form.addComboBox("dia", "Dia da Semana", Arrays.asList(DayOfWeek.values()), DayOfWeek.MONDAY, DayOfWeek::toString, true);
        form.addTimeField("inicio", "Horário de Início", LocalTime.of(19, 0), true);
        form.addTimeField("fim", "Horário de Término", LocalTime.of(21, 0), true);
        form.addTextField("sala", "Sala / Ambiente", "Sala 101", true);

        if (!form.showDialog()) throw new OperacaoCancelada();

        HorarioTurma horario = new HorarioTurma(
                form.getSelected("dia"),
                form.getTime("inicio"),
                form.getTime("fim"),
                form.getText("sala")
        );
        sistema.adicionarHorarioTurma(turma.getCodigo(), horario);
    }

    private void adicionarEncontro() {
        Turma turma = obterTurmaSelecionada();
        List<Modulo> modulos = sistema.consultarModulosCurso(turma.getCurso().getCodigo());
        if (modulos.isEmpty()) throw new RegraNegocioException("O curso desta turma não possui módulos cadastrados.");

        UIFormDialog form = new UIFormDialog(janela, "Agendar Encontro de Aula", "Turma: " + turma.getCodigo());
        form.addComboBox("modulo", "Módulo Pertencente", modulos, modulos.get(0), Modulo::getNome, true);
        form.addDateField("data", "Data da Aula", LocalDate.now().plusDays(2), true);
        form.addTimeField("inicio", "Horário de Início", LocalTime.of(19, 0), true);
        form.addTimeField("fim", "Horário de Término", LocalTime.of(21, 0), true);

        if (!form.showDialog()) throw new OperacaoCancelada();

        Encontro encontro = new Encontro(
                form.getDate("data"),
                form.getTime("inicio"),
                form.getTime("fim"),
                form.getSelected("modulo")
        );
        sistema.adicionarEncontroTurma(turma.getCodigo(), encontro);
    }

    private void iniciarTurma() {
        Turma turma = obterTurmaSelecionada();
        sistema.iniciarTurma(turma.getCodigo());
        JOptionPane.showMessageDialog(janela, "A turma " + turma.getCodigo() + " foi iniciada com sucesso!", "Turma em Andamento", JOptionPane.INFORMATION_MESSAGE);
    }

    private void verHorariosEEncontrosDaTurma() {
        Turma turma = obterTurmaSelecionada();
        TableDetailDialog dlg = new TableDetailDialog(janela,
                "Agenda da Turma: " + turma.getCodigo(),
                "Curso: " + turma.getCurso().getCodigo() + "  |  Professor: " + turma.getProfessor().getNome(),
                new String[]{"Tipo", "Dia / Data", "Horário", "Detalhes / Módulo / Sala"}
        );

        for (HorarioTurma h : turma.getHorarios()) {
            dlg.adicionarLinha(new Object[]{
                    "HORÁRIO FIXO",
                    h.getDiaSemana().toString(),
                    h.getHorarioInicio() + " às " + h.getHorarioTermino(),
                    "Sala: " + h.getSala()
            });
        }
        for (Encontro e : turma.getEncontros()) {
            dlg.adicionarLinha(new Object[]{
                    "ENCONTRO AGENDADO",
                    UITheme.formatDate(e.getData()),
                    e.getHorarioInicio() + " às " + e.getHorarioTermino(),
                    e.getModulo() != null ? e.getModulo().getNome() : "Sem módulo"
            });
        }
        dlg.setVisible(true);
    }

    private Turma obterTurmaSelecionada() {
        int row = tabelaTurmas.getSelectedRow();
        if (row != -1) {
            int modelRow = tabelaTurmas.convertRowIndexToModel(row);
            String codigo = (String) modeloTurmas.getValueAt(modelRow, 0);
            Turma t = sistema.consultarTurma(codigo);
            if (t != null) return t;
        }
        if (sistema.getTurmas().isEmpty()) throw new RegraNegocioException("Nenhuma turma cadastrada.");
        return escolherDialogo("Selecione a Turma:", sistema.getTurmas(), Turma::getCodigo);
    }

    // --- MATRÍCULAS ---
    private void matricular() {
        if (sistema.getAlunos().isEmpty()) throw new RegraNegocioException("Cadastre alunos antes de efetuar matrículas.");
        List<Turma> turmasAbertas = sistema.getTurmas().stream()
                .filter(t -> t.getSituacao() == SituacaoTurma.ABERTA).collect(Collectors.toList());
        if (turmasAbertas.isEmpty()) throw new RegraNegocioException("Não há turmas abertas para novas matrículas.");

        UIFormDialog form = new UIFormDialog(janela, "Efetivar Matrícula de Aluno", "Vincule o aluno à turma e defina as condições financeiras.");
        form.addComboBox("aluno", "Aluno", sistema.getAlunos(), sistema.getAlunos().get(0), Aluno::getNome, true);
        form.addComboBox("turma", "Turma Aberta", turmasAbertas, turmasAbertas.get(0), t -> t.getCodigo() + " - " + t.getCurso().getIdioma(), true);
        form.addTextField("numero", "Nº da Matrícula (opcional)", "MAT-" + (sistema.getMatriculas().size() + 1), false);
        form.addDateField("data", "Data da Matrícula", LocalDate.now(), true);
        form.addDecimalField("valor", "Valor Mensal da Parcela (R$)", new BigDecimal("250.00"), true);
        form.addIntegerSpinner("parcelas", "Quantidade de Parcelas", 6, 1, 24, 1, true);
        form.addDecimalField("desconto", "Desconto por Parcela (R$)", BigDecimal.ZERO, false);
        form.addComboBox("forma", "Forma de Pagamento", Arrays.asList("Boleto Bancário", "Cartão de Crédito", "PIX", "Transferência", "Dinheiro"), "Boleto Bancário", s -> s, true);

        if (!form.showDialog()) throw new OperacaoCancelada();

        Aluno aluno = form.getSelected("aluno");
        Turma turma = form.getSelected("turma");
        Matricula dados = new Matricula(
                form.getText("numero"),
                aluno,
                turma,
                form.getDate("data"),
                form.getBigDecimal("valor"),
                form.getInteger("parcelas"),
                form.getBigDecimal("desconto"),
                form.getText("forma")
        );

        Matricula mat = sistema.matricular(aluno, turma, dados);
        JOptionPane.showMessageDialog(janela, "Matrícula " + mat.getNumero() + " realizada com sucesso! " +
                mat.getQuantidadeParcelas() + " mensalidades geradas automaticamente.", "Matrícula Concluída", JOptionPane.INFORMATION_MESSAGE);
    }

    private void cancelarMatricula() {
        Matricula mat = obterMatriculaSelecionada();
        int resp = JOptionPane.showConfirmDialog(janela,
                "Deseja cancelar a matrícula '" + mat.getNumero() + "' do aluno " + mat.getAluno().getNome() + "?\n" +
                "Cobranças pendentes serão canceladas, preservando o histórico.",
                "Confirmar Cancelamento", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (resp != JOptionPane.YES_OPTION) throw new OperacaoCancelada();
        sistema.cancelarMatricula(mat.getNumero());
    }

    private void matriculasPorAluno() {
        Aluno aluno = escolherDialogo("Selecione o Aluno:", sistema.getAlunos(), Aluno::getNome);
        List<Matricula> mats = sistema.consultarMatriculasPorAluno(aluno.getCpf());
        TableDetailDialog dlg = new TableDetailDialog(janela, "Matrículas do Aluno: " + aluno.getNome(), "CPF: " + aluno.getCpf(),
                new String[]{"Nº", "Turma", "Data Matrícula", "Valor Parcela", "Parcelas", "Situação"});
        for (Matricula m : mats) {
            dlg.adicionarLinha(new Object[]{
                    m.getNumero(),
                    m.getTurma().getCodigo(),
                    UITheme.formatDate(m.getDataMatricula()),
                    UITheme.formatCurrency(m.getValorMensalidade()),
                    m.getQuantidadeParcelas() + "x",
                    m.isAtiva() ? "ATIVA" : "INATIVA"
            });
        }
        dlg.setVisible(true);
    }

    private void matriculasPorTurma() {
        Turma turma = escolherDialogo("Selecione a Turma:", sistema.getTurmas(), Turma::getCodigo);
        List<Matricula> mats = sistema.consultarMatriculasPorTurma(turma.getCodigo());
        TableDetailDialog dlg = new TableDetailDialog(janela, "Alunos Matriculados na Turma: " + turma.getCodigo(), "Curso: " + turma.getCurso().getIdioma(),
                new String[]{"Matrícula", "Aluno", "E-mail", "Data Matrícula", "Status"});
        for (Matricula m : mats) {
            dlg.adicionarLinha(new Object[]{
                    m.getNumero(),
                    m.getAluno().getNome(),
                    m.getAluno().getEmail(),
                    UITheme.formatDate(m.getDataMatricula()),
                    m.isAtiva() ? "ATIVA" : "CANCELADA"
            });
        }
        dlg.setVisible(true);
    }

    private void mensalidadesDaMatricula() {
        Matricula mat = obterMatriculaSelecionada();
        List<Mensalidade> mensalidades = sistema.consultarMensalidadesPorMatricula(mat.getNumero());
        TableDetailDialog dlg = new TableDetailDialog(janela, "Mensalidades da Matrícula: " + mat.getNumero(), "Aluno: " + mat.getAluno().getNome(),
                new String[]{"Nº Parcela", "Vencimento", "Valor Original", "Valor Atualizado", "Situação", "Data Pagamento"});
        for (Mensalidade m : mensalidades) {
            dlg.adicionarLinha(new Object[]{
                    m.getNumero() + "ª Parcela",
                    UITheme.formatDate(m.getVencimento()),
                    UITheme.formatCurrency(m.getValorOriginal()),
                    UITheme.formatCurrency(m.getValorAtualizado()),
                    m.getSituacao(),
                    UITheme.formatDate(m.getDataPagamento())
            });
        }
        dlg.setVisible(true);
    }

    private Matricula obterMatriculaSelecionada() {
        int row = tabelaMatriculas.getSelectedRow();
        if (row != -1) {
            int modelRow = tabelaMatriculas.convertRowIndexToModel(row);
            String num = (String) modeloMatriculas.getValueAt(modelRow, 0);
            for (Matricula m : sistema.getMatriculas()) {
                if (m.getNumero().equalsIgnoreCase(num)) return m;
            }
        }
        if (sistema.getMatriculas().isEmpty()) throw new RegraNegocioException("Nenhuma matrícula cadastrada.");
        return escolherDialogo("Selecione a Matrícula:", sistema.getMatriculas(), m -> m.getNumero() + " - " + m.getAluno().getNome());
    }

    // --- MENSALIDADES E PAGAMENTOS ---
    private void calcularAtraso() {
        Mensalidade mens = obterMensalidadeSelecionada();
        BigDecimal devido = sistema.calcularTotalEmAtraso(mens, LocalDate.now());

        String resumo = "=== CÁLCULO DE MENSALIDADE ===\n" +
                "Matrícula: " + (mens.getMatricula() != null ? mens.getMatricula().getNumero() : "-") + "\n" +
                "Aluno: " + (mens.getMatricula() != null && mens.getMatricula().getAluno() != null ? mens.getMatricula().getAluno().getNome() : "-") + "\n" +
                "Parcela: " + mens.getNumero() + "ª\n" +
                "Vencimento: " + UITheme.formatDate(mens.getVencimento()) + "\n" +
                "Valor Original: " + UITheme.formatCurrency(mens.getValorOriginal()) + "\n" +
                "Situação: " + mens.getSituacao() + "\n\n" +
                "Multa Configurada: " + sistema.getTaxaMulta().movePointRight(2) + "%\n" +
                "Juros Mensais: " + sistema.getTaxaJurosMensal().movePointRight(2) + "%\n\n" +
                "TOTAL DEVIDO HOJE: " + UITheme.formatCurrency(devido);

        TableDetailDialog.exibirTextoFormatado(janela, "Total em Atraso Atualizado", "Demonstrativo de cálculo", resumo);
    }

    private void registrarPagamento() {
        Mensalidade mens = obterMensalidadeSelecionada();
        BigDecimal devido = sistema.calcularTotalEmAtraso(mens, LocalDate.now());

        UIFormDialog form = new UIFormDialog(janela, "Quitar Mensalidade", "Registrar pagamento e baixa da parcela.");
        form.addTextField("info", "Parcela / Aluno", mens.getNumero() + "ª Parcela - " + (mens.getMatricula() != null ? mens.getMatricula().getAluno().getNome() : ""), false);
        form.addDecimalField("valorDevido", "Valor Total Devido (R$)", devido, true);
        form.addDateField("data", "Data do Pagamento", LocalDate.now(), true);
        form.addComboBox("forma", "Forma de Pagamento", Arrays.asList("PIX", "Cartão de Crédito", "Cartão de Débito", "Boleto", "Dinheiro"), "PIX", s -> s, true);

        if (!form.showDialog()) throw new OperacaoCancelada();

        Pagamento pagamento = new Pagamento(
                form.getDate("data"),
                form.getBigDecimal("valorDevido"),
                form.getText("forma")
        );
        sistema.registrarPagamento(mens, pagamento);
        JOptionPane.showMessageDialog(janela, "Pagamento de " + UITheme.formatCurrency(pagamento.getValorPago()) + " registrado com sucesso!", "Baixa Concluída", JOptionPane.INFORMATION_MESSAGE);
    }

    private Mensalidade obterMensalidadeSelecionada() {
        int row = tabelaMensalidades.getSelectedRow();
        if (row != -1) {
            int modelRow = tabelaMensalidades.convertRowIndexToModel(row);
            String matNum = (String) modeloMensalidades.getValueAt(modelRow, 1);
            String parcTexto = (String) modeloMensalidades.getValueAt(modelRow, 0);
            int numeroParc = Integer.parseInt(parcTexto.replaceAll("[^0-9]", ""));

            for (Mensalidade m : sistema.getMensalidades()) {
                if (m.getNumero() == numeroParc && m.getMatricula() != null && m.getMatricula().getNumero().equalsIgnoreCase(matNum)) {
                    return m;
                }
            }
        }
        List<Mensalidade> emAberto = sistema.getMensalidades().stream()
                .filter(m -> m.getSituacao() != SituacaoMensalidade.PAGA && m.getSituacao() != SituacaoMensalidade.CANCELADA)
                .collect(Collectors.toList());
        if (emAberto.isEmpty()) throw new RegraNegocioException("Não há mensalidades em aberto.");
        return escolherDialogo("Selecione a Mensalidade:", emAberto, m -> m.getNumero() + "ª Parc. - " + m.getMatricula().getAluno().getNome() + " (" + UITheme.formatCurrency(m.getValorOriginal()) + ")");
    }

    // --- MATERIAIS E VENDAS ---
    private void novoMaterial() {
        UIFormDialog form = new UIFormDialog(janela, "Cadastrar Material Didático", "Livros, apostilas e materiais de apoio.");
        form.addTextField("codigo", "Código do Material", "MAT-" + (sistema.getMateriais().size() + 1), true);
        form.addTextField("titulo", "Título do Material", "", true);
        form.addTextField("editora", "Editora", "Oxford", true);
        form.addTextField("edicao", "Edição", "1ª Edição", true);
        form.addTextField("tipo", "Tipo (Livro / Apostila / Kit)", "Livro do Aluno", true);
        form.addDecimalField("preco", "Preço de Venda (R$)", new BigDecimal("120.00"), true);

        if (!form.showDialog()) throw new OperacaoCancelada();

        Material material = new Material(
                form.getText("codigo"),
                form.getText("titulo"),
                form.getText("editora"),
                form.getText("edicao"),
                form.getText("tipo"),
                form.getBigDecimal("preco")
        );
        sistema.cadastrarMaterial(material);
    }

    private void registrarVenda() {
        if (sistema.getAlunos().isEmpty()) throw new RegraNegocioException("Cadastre alunos antes de registrar vendas.");
        if (sistema.getMateriais().isEmpty()) throw new RegraNegocioException("Cadastre materiais antes de registrar vendas.");

        UIFormDialog form = new UIFormDialog(janela, "Registrar Venda de Material", "Selecione o aluno comprador e o material.");
        form.addComboBox("aluno", "Aluno Comprador", sistema.getAlunos(), sistema.getAlunos().get(0), Aluno::getNome, true);
        form.addComboBox("material", "Material Didático", sistema.getMateriais(), sistema.getMateriais().get(0), m -> m.getTitulo() + " (" + UITheme.formatCurrency(m.getPreco()) + ")", true);
        form.addIntegerSpinner("quantidade", "Quantidade", 1, 1, 50, 1, true);

        if (!form.showDialog()) throw new OperacaoCancelada();

        Aluno aluno = form.getSelected("aluno");
        Material material = form.getSelected("material");
        int qtd = form.getInteger("quantidade");

        String numVenda = "VND-" + String.format(Locale.ROOT, "%04d", sistema.getVendas().size() + 1);
        Venda venda = new Venda(numVenda, aluno, LocalDate.now());
        venda.adicionarItem(new ItemVenda(material, qtd, material.getPreco()));

        sistema.registrarVenda(venda);
        JOptionPane.showMessageDialog(janela, "Venda " + numVenda + " registrada com sucesso! Total: " + UITheme.formatCurrency(venda.getValorTotal()), "Venda Concluída", JOptionPane.INFORMATION_MESSAGE);
    }

    private void vendasDoAluno() {
        Aluno aluno = obterAlunoSelecionado();
        List<Venda> vendas = sistema.consultarVendasPorAluno(aluno.getCpf());
        TableDetailDialog dlg = new TableDetailDialog(janela, "Vendas para: " + aluno.getNome(), "CPF: " + aluno.getCpf(),
                new String[]{"Nº Venda", "Data", "Itens", "Valor Total"});
        for (Venda v : vendas) {
            String itens = v.getItens().stream().map(i -> i.getMaterial().getTitulo() + " (x" + i.getQuantidade() + ")").collect(Collectors.joining(", "));
            dlg.adicionarLinha(new Object[]{v.getNumero(), UITheme.formatDate(v.getData()), itens, UITheme.formatCurrency(v.getValorTotal())});
        }
        dlg.setVisible(true);
    }

    // --- AVALIAÇÕES, NOTAS E FREQUÊNCIA ---
    private void novaAvaliacao() {
        if (sistema.getCursos().isEmpty()) throw new RegraNegocioException("Cadastre cursos e módulos antes de criar avaliações.");

        Curso curso = escolherDialogo("Selecione o Curso:", sistema.getCursos(), Curso::getCodigo);
        List<Modulo> modulos = sistema.consultarModulosCurso(curso.getCodigo());
        if (modulos.isEmpty()) throw new RegraNegocioException("Este curso não possui módulos.");

        UIFormDialog form = new UIFormDialog(janela, "Cadastrar Nova Avaliação", "Curso: " + curso.getCodigo());
        form.addComboBox("modulo", "Módulo", modulos, modulos.get(0), Modulo::getNome, true);
        form.addTextField("id", "Identificador (Ex: PROVA-1)", "AVAL-" + (sistema.getAvaliacoes().size() + 1), true);
        form.addComboBox("tipo", "Tipo de Avaliação", Arrays.asList(TipoAvaliacao.values()), TipoAvaliacao.PROVA_ESCRITA, TipoAvaliacao::toString, true);
        form.addDateField("data", "Data de Aplicação", LocalDate.now().plusWeeks(2), true);
        form.addDecimalField("valorMax", "Valor Máximo", new BigDecimal("10.00"), true);
        form.addDecimalField("peso", "Peso na Média Final", new BigDecimal("1.00"), true);

        if (!form.showDialog()) throw new OperacaoCancelada();

        Avaliacao avaliacao = new Avaliacao(
                form.getText("id"),
                form.getSelected("tipo"),
                form.getDate("data"),
                form.getBigDecimal("valorMax"),
                form.getBigDecimal("peso"),
                form.getSelected("modulo")
        );
        sistema.cadastrarAvaliacao(avaliacao);
    }

    private void registrarNota() {
        List<Matricula> matriculasAtivas = sistema.getMatriculas().stream().filter(Matricula::isAtiva).collect(Collectors.toList());
        if (matriculasAtivas.isEmpty()) throw new RegraNegocioException("Não há matrículas ativas no sistema.");

        Matricula matricula = escolherDialogo("Selecione a Matrícula:", matriculasAtivas, m -> m.getNumero() + " - " + m.getAluno().getNome());
        List<Avaliacao> avaliacoesCurso = sistema.getAvaliacoes().stream()
                .filter(a -> matricula.getTurma().getCurso().getModulos().contains(a.getModulo()))
                .collect(Collectors.toList());

        if (avaliacoesCurso.isEmpty()) throw new RegraNegocioException("Não há avaliações cadastradas para o curso da matrícula.");

        UIFormDialog form = new UIFormDialog(janela, "Lançar Nota", "Aluno: " + matricula.getAluno().getNome() + " | Turma: " + matricula.getTurma().getCodigo());
        form.addComboBox("avaliacao", "Avaliação", avaliacoesCurso, avaliacoesCurso.get(0), a -> a.getIdentificador() + " (" + a.getTipo() + ", Máx: " + a.getValorMaximo() + ")", true);
        form.addDecimalField("pontuacao", "Pontuação Obtida", new BigDecimal("8.5"), true);
        form.addTextField("obs", "Observações (opcional)", "Bom desempenho", false);

        if (!form.showDialog()) throw new OperacaoCancelada();

        Avaliacao avaliacao = form.getSelected("avaliacao");
        Nota nota = new Nota(avaliacao, form.getBigDecimal("pontuacao"), form.getText("obs"));
        sistema.registrarNota(matricula, avaliacao, nota);
        JOptionPane.showMessageDialog(janela, "Nota registrada com sucesso!", "Nota Gravada", JOptionPane.INFORMATION_MESSAGE);
    }

    private void calcularMedia() {
        Matricula mat = obterMatriculaSelecionada();
        List<Modulo> modulos = sistema.consultarModulosCurso(mat.getTurma().getCurso().getCodigo());
        if (modulos.isEmpty()) throw new RegraNegocioException("Curso não possui módulos.");

        Modulo modulo = escolherDialogo("Selecione o Módulo:", modulos, Modulo::getNome);
        BigDecimal media = sistema.calcularMediaFinal(mat, modulo);

        boolean aprovadoNota = media.compareTo(sistema.getNotaMinima()) >= 0;
        String msg = "Aluno: " + mat.getAluno().getNome() + "\n" +
                "Curso: " + mat.getTurma().getCurso().getIdioma() + "  |  Módulo: " + modulo.getNome() + "\n\n" +
                "MÉDIA FINAL PONDERADA: " + UITheme.formatNumber(media) + "\n" +
                "Nota Mínima Exigida: " + sistema.getNotaMinima() + "\n" +
                "Situação por Nota: " + (aprovadoNota ? "APROVADO POR NOTA" : "REPROVADO POR NOTA");

        TableDetailDialog.exibirTextoFormatado(janela, "Média Final Ponderada", "Boletim do Módulo", msg);
    }

    private void registrarFrequencia() {
        List<Matricula> matriculasAtivas = sistema.getMatriculas().stream().filter(Matricula::isAtiva).collect(Collectors.toList());
        if (matriculasAtivas.isEmpty()) throw new RegraNegocioException("Não há matrículas ativas.");

        Matricula matricula = escolherDialogo("Selecione a Matrícula:", matriculasAtivas, m -> m.getNumero() + " - " + m.getAluno().getNome());
        List<Encontro> encontros = matricula.getTurma().getEncontros();
        if (encontros.isEmpty()) throw new RegraNegocioException("A turma deste aluno não possui encontros cadastrados.");

        UIFormDialog form = new UIFormDialog(janela, "Registro de Frequência", "Aluno: " + matricula.getAluno().getNome());
        form.addComboBox("encontro", "Encontro de Aula", encontros, encontros.get(0), e -> UITheme.formatDate(e.getData()) + " (" + e.getHorarioInicio() + " às " + e.getHorarioTermino() + ")", true);
        form.addCheckBox("presente", "Status de Presença", "Marcar como PRESENTE (Desmarcado = FALTA)", true);

        if (!form.showDialog()) throw new OperacaoCancelada();

        Encontro encontro = form.getSelected("encontro");
        boolean presente = form.getBoolean("presente");
        sistema.registrarFrequencia(matricula, encontro, presente);
        JOptionPane.showMessageDialog(janela, "Frequência registrada: " + (presente ? "PRESENTE" : "FALTA"), "Frequência", JOptionPane.INFORMATION_MESSAGE);
    }

    private void calcularFrequencia() {
        Matricula mat = obterMatriculaSelecionada();
        List<Modulo> modulos = sistema.consultarModulosCurso(mat.getTurma().getCurso().getCodigo());
        if (modulos.isEmpty()) throw new RegraNegocioException("Curso não possui módulos.");

        Modulo modulo = escolherDialogo("Selecione o Módulo:", modulos, Modulo::getNome);
        BigDecimal freq = sistema.calcularFrequencia(mat, modulo);
        BigDecimal freqPerc = freq.multiply(BigDecimal.valueOf(100)).setScale(1, RoundingMode.HALF_UP);

        boolean aprovadoFreq = freq.compareTo(sistema.getFrequenciaMinima()) >= 0;
        String msg = "Aluno: " + mat.getAluno().getNome() + "\n" +
                "Módulo: " + modulo.getNome() + "\n\n" +
                "FREQUÊNCIA APURADA: " + freqPerc + "%\n" +
                "Frequência Mínima Exigida: " + sistema.getFrequenciaMinima().movePointRight(2) + "%\n" +
                "Situação por Presença: " + (aprovadoFreq ? "SUFICIENTE" : "REPROVADO POR FALTA");

        TableDetailDialog.exibirTextoFormatado(janela, "Frequência no Módulo", "Apurado de presenças", msg);
    }

    // --- CERTIFICADOS E AULAS PARTICULARES ---
    private void emitirCertificado() {
        List<Matricula> matriculasAtivas = sistema.getMatriculas().stream().filter(Matricula::isAtiva).collect(Collectors.toList());
        if (matriculasAtivas.isEmpty()) throw new RegraNegocioException("Não há matrículas ativas.");

        Matricula matricula = escolherDialogo("Selecione a Matrícula:", matriculasAtivas, m -> m.getNumero() + " - " + m.getAluno().getNome());
        List<Modulo> modulos = sistema.consultarModulosCurso(matricula.getTurma().getCurso().getCodigo());
        Modulo modulo = escolherDialogo("Selecione o Módulo a Concluir:", modulos, Modulo::getNome);

        Certificado cert = sistema.concluirModulo(matricula, modulo);
        String msg = "CERTIFICADO DE CONCLUSÃO EMITIDO COM SUCESSO!\n\n" +
                "Certificado Nº: " + cert.getNumero() + "\n" +
                "Aluno: " + cert.getAluno().getNome() + "\n" +
                "Curso: " + cert.getCurso().getIdioma() + " (" + cert.getCurso().getNivel() + ")\n" +
                "Módulo Concluído: " + cert.getModulo().getNome() + "\n" +
                "Carga Horária: " + cert.getCargaHoraria() + " horas\n" +
                "Média Final Obtida: " + UITheme.formatNumber(cert.getNotaFinal()) + "\n" +
                "Data de Emissão: " + UITheme.formatDate(cert.getDataEmissao());

        TableDetailDialog.exibirTextoFormatado(janela, "Certificado Emitido", "Documento de Conclusão", msg);
    }

    private void certificadosDoAluno() {
        Aluno aluno = obterAlunoSelecionado();
        List<Certificado> certs = sistema.consultarCertificadosPorAluno(aluno.getCpf());
        TableDetailDialog dlg = new TableDetailDialog(janela, "Certificados de " + aluno.getNome(), "CPF: " + aluno.getCpf(),
                new String[]{"Nº", "Data Emissão", "Curso", "Módulo", "Carga Horária", "Média Final"});
        for (Certificado c : certs) {
            dlg.adicionarLinha(new Object[]{
                    c.getNumero(),
                    UITheme.formatDate(c.getDataEmissao()),
                    c.getCurso().getCodigo(),
                    c.getModulo().getNome(),
                    c.getCargaHoraria() + "h",
                    UITheme.formatNumber(c.getNotaFinal())
            });
        }
        dlg.setVisible(true);
    }

    private void agendarAula() {
        if (sistema.getAlunos().isEmpty()) throw new RegraNegocioException("Cadastre alunos antes de agendar aulas.");
        if (sistema.getProfessores().isEmpty()) throw new RegraNegocioException("Cadastre professores antes de agendar aulas.");

        UIFormDialog form = new UIFormDialog(janela, "Agendar Aula Particular", "Sessão individual com professor.");
        form.addTextField("id", "Identificador da Aula", "AULA-" + (sistema.getAulasParticulares().size() + 1), true);
        form.addComboBox("aluno", "Aluno", sistema.getAlunos(), sistema.getAlunos().get(0), Aluno::getNome, true);
        form.addComboBox("professor", "Professor", sistema.getProfessores(), sistema.getProfessores().get(0), Professor::getNome, true);
        form.addDateField("data", "Data da Aula", LocalDate.now().plusDays(1), true);
        form.addTimeField("inicio", "Horário de Início", LocalTime.of(14, 0), true);
        form.addTimeField("fim", "Horário de Término", LocalTime.of(15, 30), true);
        form.addTextArea("conteudo", "Conteúdo a ser Ministrado", "Conversação intensiva e revisão de gramática", 2, true);
        form.addTextField("obs", "Observações", "Material de apoio fornecido", false);

        if (!form.showDialog()) throw new OperacaoCancelada();

        AulaParticular aula = new AulaParticular(
                form.getText("id"),
                form.getSelected("aluno"),
                form.getSelected("professor"),
                form.getDate("data"),
                form.getTime("inicio"),
                form.getTime("fim"),
                form.getText("conteudo"),
                form.getText("obs")
        );
        sistema.agendarAulaParticular(aula);
    }

    private void aulasDoAluno() {
        Aluno aluno = obterAlunoSelecionado();
        List<AulaParticular> aulas = sistema.consultarAulasParticularesPorAluno(aluno.getCpf());
        TableDetailDialog dlg = new TableDetailDialog(janela, "Aulas Particulares do Aluno: " + aluno.getNome(), "CPF: " + aluno.getCpf(),
                new String[]{"ID", "Professor", "Data", "Início", "Término", "Conteúdo"});
        for (AulaParticular a : aulas) {
            dlg.adicionarLinha(new Object[]{a.getIdentificador(), a.getProfessor().getNome(), UITheme.formatDate(a.getData()), UITheme.formatTime(a.getHorarioInicio()), UITheme.formatTime(a.getHorarioTermino()), a.getConteudoMinistrado()});
        }
        dlg.setVisible(true);
    }

    private void aulasDoProfessor() {
        Professor prof = obterProfessorSelecionado();
        List<AulaParticular> aulas = sistema.consultarAulasParticularesPorProfessor(prof.getCpf());
        TableDetailDialog dlg = new TableDetailDialog(janela, "Aulas Particulares do Professor: " + prof.getNome(), "Docente",
                new String[]{"ID", "Aluno", "Data", "Início", "Término", "Conteúdo"});
        for (AulaParticular a : aulas) {
            dlg.adicionarLinha(new Object[]{a.getIdentificador(), a.getAluno().getNome(), UITheme.formatDate(a.getData()), UITheme.formatTime(a.getHorarioInicio()), UITheme.formatTime(a.getHorarioTermino()), a.getConteudoMinistrado()});
        }
        dlg.setVisible(true);
    }

    // --- CARREGAMENTO DE DADOS DE DEMONSTRAÇÃO ---
    private void carregarDadosDemonstracao() {
        boolean possuiDados = !sistema.getCursos().isEmpty()
                || !sistema.getProfessores().isEmpty()
                || !sistema.getAlunos().isEmpty()
                || !sistema.getTurmas().isEmpty()
                || !sistema.getMatriculas().isEmpty()
                || !sistema.getMensalidades().isEmpty()
                || !sistema.getPagamentos().isEmpty()
                || !sistema.getAvaliacoes().isEmpty()
                || !sistema.getNotas().isEmpty()
                || !sistema.getRegistrosFrequencia().isEmpty()
                || !sistema.getAulasParticulares().isEmpty()
                || !sistema.getCertificados().isEmpty()
                || !sistema.getMateriais().isEmpty()
                || !sistema.getVendas().isEmpty()
                || !sistema.getItensVenda().isEmpty();
        if (possuiDados) {
            JOptionPane.showMessageDialog(janela,
                    "Os dados de demonstração só podem ser carregados quando o sistema está vazio. Nenhum dado foi alterado.",
                    "Sistema já possui dados", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        try {
            // Cursos e Módulos
            Curso cursoIngles = new Curso("ING-BAS", "Inglês", NivelCurso.BASICO, 80,
                    "Fundamentos da língua inglesa: conversação básica e gramática estrutural.",
                    "Livro English File Starter, Caderno de Exercícios");
            sistema.cadastrarCurso(cursoIngles);

            Modulo mod1 = new Modulo(1, "English Starter - Módulo 1", 40, "Noite");
            Modulo mod2 = new Modulo(2, "English Starter - Módulo 2", 40, "Noite");
            mod2.adicionarPreRequisito(mod1);
            sistema.cadastrarModulo(cursoIngles.getCodigo(), mod1);
            sistema.cadastrarModulo(cursoIngles.getCodigo(), mod2);

            Curso cursoEspanhol = new Curso("ESP-INT", "Espanhol", NivelCurso.INTERMEDIARIO, 60,
                    "Desenvolvimento de fluência e redação intermediária.", "Gramática del Español Paso a Paso");
            sistema.cadastrarCurso(cursoEspanhol);
            Modulo modEsp = new Modulo(1, "Español en Acción", 60, "Sábado Manhã");
            sistema.cadastrarModulo(cursoEspanhol.getCodigo(), modEsp);

            // Professores
            Professor profCarlos = new Professor("Carlos Eduardo", "111.222.333-44", "(41) 98888-1111",
                    "carlos.eduardo@escola.com", LocalDate.of(2023, 2, 1), "Letras Inglês / Cambridge CELTA");
            profCarlos.adicionarQualificacao(new IdiomaProfissional("Inglês", NivelCurso.AVANCADO));
            sistema.cadastrarProfessor(profCarlos);

            Professor profAna = new Professor("Ana Paula", "555.666.777-88", "(41) 99999-2222",
                    "ana.paula@escola.com", LocalDate.of(2024, 1, 15), "Licenciatura em Espanhol");
            profAna.adicionarQualificacao(new IdiomaProfissional("Espanhol", NivelCurso.AVANCADO));
            sistema.cadastrarProfessor(profAna);

            // Alunos
            Aluno alunoLucas = new Aluno("Lucas Silva", "123.456.789-00", LocalDate.of(2002, 5, 20),
                    "Rua XV de Novembro, 100", "(41) 97777-3333", "lucas.silva@email.com");
            alunoLucas.adicionarInteresse(new IdiomaInteresse("Inglês", NivelCurso.BASICO));
            sistema.cadastrarAluno(alunoLucas);

            Aluno alunaMariana = new Aluno("Mariana Souza", "987.654.321-11", LocalDate.of(1999, 11, 10),
                    "Av. Sete de Setembro, 500", "(41) 96666-4444", "mariana.souza@email.com");
            alunaMariana.adicionarInteresse(new IdiomaInteresse("Espanhol", NivelCurso.INTERMEDIARIO));
            sistema.cadastrarAluno(alunaMariana);

            // Turmas
            Turma turmaIng = new Turma("TURMA-ING-01", cursoIngles, profCarlos,
                    LocalDate.now().minusDays(10), LocalDate.now().plusMonths(1), 2, 15);
            sistema.cadastrarTurma(turmaIng);
            sistema.adicionarHorarioTurma(turmaIng.getCodigo(), new HorarioTurma(DayOfWeek.MONDAY, LocalTime.of(19, 0), LocalTime.of(21, 0), "Sala 101"));
            Encontro encontro1 = new Encontro(LocalDate.now().plusDays(3), LocalTime.of(19, 0), LocalTime.of(21, 0), mod1);
            sistema.adicionarEncontroTurma(turmaIng.getCodigo(), encontro1);

            // Matrícula
            Matricula matLucas = new Matricula("MAT-001", alunoLucas, turmaIng, LocalDate.now(),
                    new BigDecimal("220.00"), 6, new BigDecimal("20.00"), "Boleto Bancário");
            sistema.matricular(alunoLucas, turmaIng, matLucas);

            // Materiais
            Material matBook = new Material("MAT-ENG-01", "English File Starter Student's Book", "Oxford", "4ª Edição", "Livro", new BigDecimal("160.00"));
            Material matGram = new Material("MAT-ESP-01", "Gramática de Español", "Edelsa", "2ª Edição", "Apostila", new BigDecimal("110.00"));
            sistema.cadastrarMaterial(matBook);
            sistema.cadastrarMaterial(matGram);

            // Venda
            Venda venda1 = new Venda("VND-0001", alunoLucas, LocalDate.now());
            venda1.adicionarItem(new ItemVenda(matBook, 1, matBook.getPreco()));
            sistema.registrarVenda(venda1);

            // Avaliação e Nota
            Avaliacao aval1 = new Avaliacao("PROVA-1", TipoAvaliacao.PROVA_ESCRITA, LocalDate.now().plusWeeks(1),
                    new BigDecimal("10.00"), new BigDecimal("1.00"), mod1);
            sistema.cadastrarAvaliacao(aval1);
            sistema.registrarNota(matLucas, aval1, new Nota(aval1, new BigDecimal("8.5"), "Ótima dedicação"));

            atualizarTodasTabelas();
            lblStatus.setText("Dados de demonstração carregados com sucesso.");
            JOptionPane.showMessageDialog(janela,
                    "Dados de demonstração carregados com sucesso!\n\n" +
                    "• 2 Cursos com módulos e pré-requisitos\n" +
                    "• 2 Professores qualificados\n" +
                    "• 2 Alunos cadastrados\n" +
                    "• 1 Turma com horários e encontros\n" +
                    "• 1 Matrícula com 6 mensalidades automáticas\n" +
                    "• Materiais, venda, avaliação e notas lançadas",
                    "Demonstração Pronta", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(janela, "Erro ao carregar dados: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private <T> T escolherDialogo(String pergunta, List<T> opcoes, java.util.function.Function<T, String> rotulo) {
        if (opcoes == null || opcoes.isEmpty()) {
            throw new RegraNegocioException("Nenhuma opção disponível.");
        }
        Object[] itens = opcoes.stream().map(rotulo::apply).toArray();
        String selecionado = (String) JOptionPane.showInputDialog(janela, pergunta, "Seleção",
                JOptionPane.QUESTION_MESSAGE, null, itens, itens[0]);
        if (selecionado == null) throw new OperacaoCancelada();

        for (int i = 0; i < itens.length; i++) {
            if (itens[i].equals(selecionado)) return opcoes.get(i);
        }
        throw new OperacaoCancelada();
    }

    private static final class OperacaoCancelada extends RuntimeException {
        private static final long serialVersionUID = 1L;
    }
}
