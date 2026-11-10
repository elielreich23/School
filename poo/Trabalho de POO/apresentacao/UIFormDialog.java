package apresentacao;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Caixa de diálogo modal para formulários com visual profissional,
 * suporte a múltiplos tipos de componentes e validação integrada sem perda de dados.
 */
public class UIFormDialog extends JDialog {

    private final JPanel formPanel;
    private final GridBagConstraints gbc;
    private final JLabel lblMensagemErro;
    private final Map<String, JComponent> campos = new HashMap<>();
    private final Map<String, Boolean> camposObrigatorios = new HashMap<>();
    private final Map<String, String> nomesCampos = new HashMap<>();
    private boolean confirmado = false;
    private int linhaAtual = 0;

    public UIFormDialog(Window owner, String titulo, String subtitulo) {
        super(owner, titulo, ModalityType.APPLICATION_MODAL);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(UITheme.COLOR_BG);

        // Cabeçalho da Janela
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

        // Painel Central com Formulário
        formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(new EmptyBorder(16, 20, 16, 20));

        gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JScrollPane scroll = new JScrollPane(formPanel);
        scroll.setBorder(null);
        scroll.getViewport().setBackground(Color.WHITE);
        add(scroll, BorderLayout.CENTER);

        // Rodapé com Ações e Feedback de Erro
        JPanel rodape = new JPanel(new BorderLayout(8, 8));
        rodape.setBackground(UITheme.COLOR_SECONDARY);
        rodape.setBorder(new CompoundBorder(
                BorderFactory.createMatteBorder(1, 0, 0, 0, UITheme.COLOR_BORDER),
                new EmptyBorder(12, 20, 12, 20)
        ));

        lblMensagemErro = new JLabel(" ");
        lblMensagemErro.setFont(UITheme.FONT_SMALL_BOLD);
        lblMensagemErro.setForeground(UITheme.COLOR_DANGER);

        JPanel botoes = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        botoes.setOpaque(false);

        JButton btnCancelar = UITheme.createSecondaryButton("Cancelar");
        btnCancelar.addActionListener(e -> {
            confirmado = false;
            dispose();
        });

        JButton btnSalvar = UITheme.createPrimaryButton("Salvar / Confirmar");
        btnSalvar.addActionListener(e -> validarESubmeter());

        botoes.add(btnCancelar);
        botoes.add(btnSalvar);

        rodape.add(lblMensagemErro, BorderLayout.CENTER);
        rodape.add(botoes, BorderLayout.EAST);
        add(rodape, BorderLayout.SOUTH);

        getRootPane().setDefaultButton(btnSalvar);
    }

    public void addTextField(String chave, String label, String valorInicial, boolean obrigatorio) {
        JTextField tf = UITheme.createTextField(24);
        if (valorInicial != null) tf.setText(valorInicial);
        adicionarLinha(chave, label, tf, obrigatorio);
    }

    public void addTextArea(String chave, String label, String valorInicial, int linhas, boolean obrigatorio) {
        JTextArea ta = new JTextArea(linhas, 24);
        ta.setFont(UITheme.FONT_REGULAR);
        ta.setLineWrap(true);
        ta.setWrapStyleWord(true);
        ta.setBorder(new EmptyBorder(6, 8, 6, 8));
        if (valorInicial != null) ta.setText(valorInicial);

        JScrollPane scroll = new JScrollPane(ta);
        scroll.setBorder(new LineBorder(UITheme.COLOR_BORDER_INPUT, 1, true));
        adicionarLinha(chave, label, scroll, obrigatorio);
        campos.put(chave, ta); // armazena o text area real
    }

    public <T> void addComboBox(String chave, String label, java.util.List<T> opcoes, T selecionado, Function<T, String> visualizador, boolean obrigatorio) {
        JComboBox<T> combo = new JComboBox<>();
        combo.setFont(UITheme.FONT_REGULAR);
        combo.setBackground(Color.WHITE);
        if (opcoes != null) {
            for (T op : opcoes) combo.addItem(op);
        }
        if (selecionado != null) combo.setSelectedItem(selecionado);

        if (visualizador != null) {
            combo.setRenderer(new DefaultListCellRenderer() {
                @Override
                @SuppressWarnings("unchecked")
                public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                    Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                    if (value != null) {
                        try {
                            setText(visualizador.apply((T) value));
                        } catch (Exception ex) {
                            setText(value.toString());
                        }
                    } else {
                        setText("- Selecione -");
                    }
                    setBorder(new EmptyBorder(4, 6, 4, 6));
                    return c;
                }
            });
        }
        combo.setBorder(new CompoundBorder(new LineBorder(UITheme.COLOR_BORDER_INPUT, 1, true), new EmptyBorder(2, 4, 2, 4)));
        adicionarLinha(chave, label, combo, obrigatorio);
    }

    public void addIntegerSpinner(String chave, String label, int inicial, int min, int max, int passo, boolean obrigatorio) {
        SpinnerNumberModel model = new SpinnerNumberModel(inicial, min, max, passo);
        JSpinner spinner = new JSpinner(model);
        spinner.setFont(UITheme.FONT_REGULAR);
        spinner.setBorder(new LineBorder(UITheme.COLOR_BORDER_INPUT, 1, true));
        adicionarLinha(chave, label, spinner, obrigatorio);
    }

    public void addDecimalField(String chave, String label, BigDecimal inicial, boolean obrigatorio) {
        JTextField tf = UITheme.createTextField(14);
        if (inicial != null) {
            tf.setText(inicial.toString());
        }
        tf.setToolTipText("Exemplo: 150.00 ou 150,00");
        adicionarLinha(chave, label, tf, obrigatorio);
    }

    public void addDateField(String chave, String label, LocalDate inicial, boolean obrigatorio) {
        LocalDate dataInicial = inicial == null ? LocalDate.now() : inicial;
        java.util.Date valor = java.util.Date.from(dataInicial.atStartOfDay(ZoneId.systemDefault()).toInstant());
        JSpinner seletor = new JSpinner(new SpinnerDateModel(valor, null, null, java.util.Calendar.DAY_OF_MONTH));
        seletor.setEditor(new JSpinner.DateEditor(seletor, "yyyy-MM-dd"));
        seletor.setFont(UITheme.FONT_REGULAR);
        seletor.setBorder(new LineBorder(UITheme.COLOR_BORDER_INPUT, 1, true));
        adicionarLinha(chave, label, seletor, obrigatorio);
    }

    public void addTimeField(String chave, String label, LocalTime inicial, boolean obrigatorio) {
        JTextField tf = UITheme.createTextField(10);
        if (inicial != null) {
            tf.setText(inicial.toString());
        } else {
            tf.setText("08:00");
        }
        tf.setToolTipText("Formato: HH:MM (ex: 08:30 ou 19:00)");
        adicionarLinha(chave, label + " (HH:MM)", tf, obrigatorio);
    }

    public void addCheckBox(String chave, String label, String textoBox, boolean inicial) {
        JCheckBox cb = new JCheckBox(textoBox, inicial);
        cb.setFont(UITheme.FONT_REGULAR);
        cb.setOpaque(false);
        adicionarLinha(chave, label, cb, false);
    }

    public void addCustomComponent(String chave, String label, JComponent componente, boolean obrigatorio) {
        adicionarLinha(chave, label, componente, obrigatorio);
    }

    private void adicionarLinha(String chave, String labelTexto, JComponent comp, boolean obrigatorio) {
        gbc.gridx = 0;
        gbc.gridy = linhaAtual;
        gbc.weightx = 0.35;
        gbc.anchor = GridBagConstraints.LINE_END;

        String textoRotulo = (obrigatorio ? "* " : "") + labelTexto;
        JLabel lbl = new JLabel(textoRotulo);
        lbl.setFont(UITheme.FONT_BOLD);
        lbl.setForeground(obrigatorio ? UITheme.COLOR_TEXT_MAIN : UITheme.COLOR_TEXT_MUTED);
        formPanel.add(lbl, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.65;
        gbc.anchor = GridBagConstraints.LINE_START;
        formPanel.add(comp, gbc);

        if (!campos.containsKey(chave)) {
            campos.put(chave, comp);
        }
        camposObrigatorios.put(chave, obrigatorio);
        nomesCampos.put(chave, labelTexto);
        linhaAtual++;
    }

    private void validarESubmeter() {
        for (Map.Entry<String, Boolean> entry : camposObrigatorios.entrySet()) {
            if (entry.getValue()) {
                String chave = entry.getKey();
                JComponent comp = campos.get(chave);
                String nome = nomesCampos.get(chave);

                if (comp instanceof JTextField tf) {
                    if (tf.getText() == null || tf.getText().trim().isEmpty()) {
                        exibirErro("O campo '" + nome + "' é obrigatório.");
                        tf.requestFocus();
                        return;
                    }
                } else if (comp instanceof JTextArea ta) {
                    if (ta.getText() == null || ta.getText().trim().isEmpty()) {
                        exibirErro("O campo '" + nome + "' é obrigatório.");
                        ta.requestFocus();
                        return;
                    }
                } else if (comp instanceof JComboBox<?> cb) {
                    if (cb.getSelectedItem() == null) {
                        exibirErro("Selecione uma opção para '" + nome + "'.");
                        cb.requestFocus();
                        return;
                    }
                }
            }
        }

        // Validação de formatos (datas, decimais)
        for (Map.Entry<String, JComponent> entry : campos.entrySet()) {
            String chave = entry.getKey();
            JComponent comp = entry.getValue();
            if (comp instanceof JTextField tf) {
                String texto = tf.getText().trim();
                if (!texto.isEmpty()) {
                    String nome = nomesCampos.get(chave);
                    if (nome.contains("AAAA-MM-DD")) {
                        try {
                            LocalDate.parse(texto);
                        } catch (DateTimeParseException ex) {
                            exibirErro("Data inválida em '" + nome + "'. Use AAAA-MM-DD.");
                            tf.requestFocus();
                            return;
                        }
                    } else if (nome.contains("HH:MM")) {
                        try {
                            LocalTime.parse(texto);
                        } catch (DateTimeParseException ex) {
                            exibirErro("Horário inválido em '" + nome + "'. Use HH:MM.");
                            tf.requestFocus();
                            return;
                        }
                    }
                }
            }
        }

        confirmado = true;
        dispose();
    }

    private void exibirErro(String msg) {
        lblMensagemErro.setText("⚠ " + msg);
    }

    public boolean showDialog() {
        pack();
        if (getWidth() < 480) setSize(520, getHeight());
        if (getHeight() > 680) setSize(getWidth(), 680);
        setLocationRelativeTo(getOwner());
        setVisible(true);
        return confirmado;
    }

    public String getText(String chave) {
        JComponent comp = campos.get(chave);
        if (comp instanceof JTextField tf) return tf.getText().trim();
        if (comp instanceof JTextArea ta) return ta.getText().trim();
        return "";
    }

    public int getInteger(String chave) {
        JComponent comp = campos.get(chave);
        if (comp instanceof JSpinner sp) return ((Number) sp.getValue()).intValue();
        if (comp instanceof JTextField tf) {
            try {
                return Integer.parseInt(tf.getText().trim());
            } catch (NumberFormatException e) {
                return 0;
            }
        }
        return 0;
    }

    public BigDecimal getBigDecimal(String chave) {
        JComponent comp = campos.get(chave);
        if (comp instanceof JTextField tf) {
            String txt = tf.getText().trim().replace(',', '.');
            if (txt.isEmpty()) return BigDecimal.ZERO;
            try {
                return new BigDecimal(txt);
            } catch (Exception e) {
                return BigDecimal.ZERO;
            }
        }
        return BigDecimal.ZERO;
    }

    public LocalDate getDate(String chave) {
        JComponent comp = campos.get(chave);
        if (comp instanceof JSpinner seletor && seletor.getValue() instanceof java.util.Date valor) {
            return valor.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        }
        String txt = getText(chave);
        if (txt.isEmpty()) return LocalDate.now();
        return LocalDate.parse(txt);
    }

    public LocalTime getTime(String chave) {
        String txt = getText(chave);
        if (txt.isEmpty()) return LocalTime.of(8, 0);
        return LocalTime.parse(txt);
    }

    @SuppressWarnings("unchecked")
    public <T> T getSelected(String chave) {
        JComponent comp = campos.get(chave);
        if (comp instanceof JComboBox<?> cb) return (T) cb.getSelectedItem();
        return null;
    }

    public boolean getBoolean(String chave) {
        JComponent comp = campos.get(chave);
        if (comp instanceof JCheckBox cb) return cb.isSelected();
        return false;
    }
}
