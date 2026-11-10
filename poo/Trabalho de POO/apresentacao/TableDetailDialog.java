package apresentacao;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Janela modal estilizada para visualização de listas detalhadas e consultas específicas
 * (ex: Módulos de um Curso, Horários de uma Turma, Mensalidades de uma Matrícula).
 */
public class TableDetailDialog extends JDialog {

    private final DefaultTableModel modelo;
    private final JTable tabela;
    private final JLabel lblContador;

    public TableDetailDialog(Window owner, String titulo, String subtitulo, String[] colunas) {
        super(owner, titulo, ModalityType.APPLICATION_MODAL);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(780, 480);
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout());
        getContentPane().setBackground(UITheme.COLOR_BG);

        // Cabeçalho
        JPanel header = new JPanel(new BorderLayout(4, 4));
        header.setBackground(Color.WHITE);
        header.setBorder(new CompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, UITheme.COLOR_BORDER),
                new EmptyBorder(16, 20, 16, 20)
        ));

        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(UITheme.FONT_TITLE);
        lblTitulo.setForeground(UITheme.COLOR_TEXT_MAIN);

        JLabel lblSub = new JLabel(subtitulo);
        lblSub.setFont(UITheme.FONT_SUBTITLE);
        lblSub.setForeground(UITheme.COLOR_TEXT_MUTED);

        header.add(lblTitulo, BorderLayout.NORTH);
        header.add(lblSub, BorderLayout.SOUTH);
        add(header, BorderLayout.NORTH);

        // Tabela centralizada
        modelo = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabela = new JTable(modelo);
        UITheme.setupTable(tabela);

        JPanel centro = new JPanel(new BorderLayout());
        centro.setBackground(UITheme.COLOR_BG);
        centro.setBorder(new EmptyBorder(12, 16, 12, 16));

        JPanel cardTabela = UITheme.createCardPanel();
        cardTabela.add(new JScrollPane(tabela), BorderLayout.CENTER);
        centro.add(cardTabela, BorderLayout.CENTER);
        add(centro, BorderLayout.CENTER);

        // Rodapé
        JPanel rodape = new JPanel(new BorderLayout());
        rodape.setBackground(UITheme.COLOR_SECONDARY);
        rodape.setBorder(new CompoundBorder(
                BorderFactory.createMatteBorder(1, 0, 0, 0, UITheme.COLOR_BORDER),
                new EmptyBorder(10, 20, 10, 20)
        ));

        lblContador = new JLabel("0 registro(s)");
        lblContador.setFont(UITheme.FONT_SMALL);
        lblContador.setForeground(UITheme.COLOR_TEXT_MUTED);

        JButton btnFechar = UITheme.createSecondaryButton("Fechar");
        btnFechar.addActionListener(e -> dispose());

        rodape.add(lblContador, BorderLayout.WEST);
        rodape.add(btnFechar, BorderLayout.EAST);
        add(rodape, BorderLayout.SOUTH);
    }

    public void adicionarLinha(Object[] dados) {
        modelo.addRow(dados);
        lblContador.setText(modelo.getRowCount() + " registro(s) encontrado(s)");
    }

    public void adicionarLinhas(List<Object[]> linhas) {
        if (linhas != null) {
            for (Object[] row : linhas) {
                modelo.addRow(row);
            }
            lblContador.setText(modelo.getRowCount() + " registro(s) encontrado(s)");
        }
    }

    public JTable getTabela() {
        return tabela;
    }

    public static void exibirTextoFormatado(Window owner, String titulo, String subtitulo, String conteudo) {
        JDialog dlg = new JDialog(owner, titulo, ModalityType.APPLICATION_MODAL);
        dlg.setSize(540, 400);
        dlg.setLocationRelativeTo(owner);
        dlg.setLayout(new BorderLayout());
        dlg.getContentPane().setBackground(UITheme.COLOR_BG);

        JPanel header = new JPanel(new BorderLayout(4, 4));
        header.setBackground(Color.WHITE);
        header.setBorder(new CompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, UITheme.COLOR_BORDER),
                new EmptyBorder(16, 20, 16, 20)
        ));

        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(UITheme.FONT_TITLE);
        lblTitulo.setForeground(UITheme.COLOR_TEXT_MAIN);

        JLabel lblSub = new JLabel(subtitulo);
        lblSub.setFont(UITheme.FONT_SUBTITLE);
        lblSub.setForeground(UITheme.COLOR_TEXT_MUTED);

        header.add(lblTitulo, BorderLayout.NORTH);
        header.add(lblSub, BorderLayout.SOUTH);
        dlg.add(header, BorderLayout.NORTH);

        JTextArea ta = new JTextArea(conteudo);
        ta.setFont(new Font("Consolas", Font.PLAIN, 13));
        ta.setEditable(false);
        ta.setBackground(Color.WHITE);
        ta.setBorder(new EmptyBorder(12, 14, 12, 14));

        JPanel centro = new JPanel(new BorderLayout());
        centro.setBackground(UITheme.COLOR_BG);
        centro.setBorder(new EmptyBorder(12, 16, 12, 16));
        centro.add(new JScrollPane(ta), BorderLayout.CENTER);
        dlg.add(centro, BorderLayout.CENTER);

        JPanel rodape = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 10));
        rodape.setBackground(UITheme.COLOR_SECONDARY);
        rodape.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, UITheme.COLOR_BORDER));

        JButton btnFechar = UITheme.createPrimaryButton("Entendido");
        btnFechar.addActionListener(e -> dlg.dispose());
        rodape.add(btnFechar);
        dlg.add(rodape, BorderLayout.SOUTH);

        dlg.setVisible(true);
    }
}
