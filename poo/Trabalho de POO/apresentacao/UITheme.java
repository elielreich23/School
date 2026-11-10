package apresentacao;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * Sistema de design e estilos visuais da aplicação.
 * Centraliza cores, fontes, bordas, renderizadores de tabela e utilitários de formatação.
 */
public final class UITheme {

    // Paleta simples e neutra para manter uma aparência adequada a um projeto acadêmico.
    public static final Color COLOR_BG = new Color(248, 248, 248);
    public static final Color COLOR_CARD = Color.WHITE;
    public static final Color COLOR_SIDEBAR = new Color(238, 240, 242);
    public static final Color COLOR_SIDEBAR_HOVER = new Color(228, 232, 236);
    public static final Color COLOR_SIDEBAR_ACTIVE = new Color(218, 229, 240);
    public static final Color COLOR_SIDEBAR_TEXT = new Color(45, 52, 58);
    public static final Color COLOR_SIDEBAR_MUTED = new Color(100, 106, 112);

    public static final Color COLOR_PRIMARY = new Color(48, 86, 125);
    public static final Color COLOR_PRIMARY_HOVER = new Color(40, 72, 105);
    public static final Color COLOR_PRIMARY_LIGHT = new Color(232, 238, 244);

    public static final Color COLOR_SECONDARY = new Color(248, 250, 252);
    public static final Color COLOR_SECONDARY_HOVER = new Color(241, 245, 249);
    public static final Color COLOR_SECONDARY_TEXT = new Color(51, 65, 85);

    public static final Color COLOR_SUCCESS = new Color(75, 130, 90);
    public static final Color COLOR_SUCCESS_LIGHT = new Color(238, 245, 238);
    public static final Color COLOR_SUCCESS_DARK = new Color(55, 105, 70);

    public static final Color COLOR_DANGER = new Color(170, 55, 55);
    public static final Color COLOR_DANGER_LIGHT = new Color(250, 238, 238);
    public static final Color COLOR_DANGER_DARK = new Color(140, 40, 40);

    public static final Color COLOR_WARNING = new Color(170, 125, 45);
    public static final Color COLOR_WARNING_LIGHT = new Color(248, 245, 235);

    public static final Color COLOR_TEXT_MAIN = new Color(40, 48, 56);
    public static final Color COLOR_TEXT_MUTED = new Color(90, 96, 102);
    public static final Color COLOR_BORDER = new Color(210, 214, 218);
    public static final Color COLOR_BORDER_INPUT = new Color(185, 190, 195);
    public static final Color COLOR_TABLE_ROW_ALT = new Color(248, 248, 248);
    public static final Color COLOR_TABLE_SELECTION = new Color(218, 229, 240);

    // Tipografia
    public static final Font FONT_APP_TITLE = new Font("Arial", Font.BOLD, 18);
    public static final Font FONT_TITLE = new Font("Arial", Font.BOLD, 15);
    public static final Font FONT_SUBTITLE = new Font("Arial", Font.PLAIN, 12);
    public static final Font FONT_SECTION = new Font("Arial", Font.BOLD, 13);
    public static final Font FONT_REGULAR = new Font("Arial", Font.PLAIN, 12);
    public static final Font FONT_BOLD = new Font("Arial", Font.BOLD, 12);
    public static final Font FONT_SMALL = new Font("Arial", Font.PLAIN, 11);
    public static final Font FONT_SMALL_BOLD = new Font("Arial", Font.BOLD, 11);
    public static final Font FONT_STAT_NUMBER = new Font("Arial", Font.BOLD, 22);

    // Formatadores de Dados
    private static final Locale PT_BR = Locale.forLanguageTag("pt-BR");
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");
    private static final DecimalFormat CURRENCY_FORMAT = new DecimalFormat("R$ #,##0.00", new DecimalFormatSymbols(PT_BR));
    private static final DecimalFormat PERCENT_FORMAT = new DecimalFormat("0.0%", new DecimalFormatSymbols(PT_BR));
    private static final DecimalFormat NUMBER_FORMAT = new DecimalFormat("#,##0.00", new DecimalFormatSymbols(PT_BR));

    private UITheme() {}

    /** Configura o Look and Feel e propriedades visuais do sistema. */
    public static void inicializarLookAndFeel() {
        try {
            System.setProperty("awt.useSystemAAFontSettings", "on");
            System.setProperty("swing.aatext", "true");
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
            // Mantém L&F padrão caso ocorra exceção
        }

        UIManager.put("Label.font", FONT_REGULAR);
        UIManager.put("Button.font", FONT_BOLD);
        UIManager.put("TextField.font", FONT_REGULAR);
        UIManager.put("ComboBox.font", FONT_REGULAR);
        UIManager.put("Table.font", FONT_REGULAR);
        UIManager.put("TableHeader.font", FONT_BOLD);
        UIManager.put("TabbedPane.font", FONT_BOLD);
    }

    public static String formatCurrency(BigDecimal value) {
        if (value == null) return "R$ 0,00";
        return CURRENCY_FORMAT.format(value);
    }

    public static String formatPercent(BigDecimal value) {
        if (value == null) return "0,0%";
        return PERCENT_FORMAT.format(value);
    }

    public static String formatNumber(BigDecimal value) {
        if (value == null) return "0,00";
        return NUMBER_FORMAT.format(value);
    }

    public static String formatDate(LocalDate date) {
        if (date == null) return "-";
        return date.format(DATE_FORMATTER);
    }

    public static String formatTime(LocalTime time) {
        if (time == null) return "-";
        return time.format(TIME_FORMATTER);
    }

    public static String formatCpf(String cpf) {
        if (cpf == null) return "";
        String digitos = cpf.replaceAll("[^0-9]", "");
        if (digitos.length() == 11) {
            return String.format("%s.%s.%s-%s",
                    digitos.substring(0, 3), digitos.substring(3, 6),
                    digitos.substring(6, 9), digitos.substring(9, 11));
        }
        return cpf;
    }

    /** Cria um botão primário com visual moderno em azul royal. */
    public static JButton createPrimaryButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(FONT_BOLD);
        btn.setForeground(Color.WHITE);
        btn.setBackground(COLOR_PRIMARY);
        btn.setFocusPainted(false);
        btn.setBorder(new CompoundBorder(new LineBorder(COLOR_PRIMARY, 1, true), new EmptyBorder(7, 14, 7, 14)));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (btn.isEnabled()) btn.setBackground(COLOR_PRIMARY_HOVER);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                if (btn.isEnabled()) btn.setBackground(COLOR_PRIMARY);
            }
        });
        return btn;
    }

    /** Cria um botão secundário suave e bem delineado. */
    public static JButton createSecondaryButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(FONT_BOLD);
        btn.setForeground(COLOR_SECONDARY_TEXT);
        btn.setBackground(COLOR_SECONDARY);
        btn.setFocusPainted(false);
        btn.setBorder(new CompoundBorder(new LineBorder(COLOR_BORDER_INPUT, 1, true), new EmptyBorder(7, 13, 7, 13)));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (btn.isEnabled()) btn.setBackground(COLOR_SECONDARY_HOVER);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                if (btn.isEnabled()) btn.setBackground(COLOR_SECONDARY);
            }
        });
        return btn;
    }

    /** Cria um botão para ações perigosas/destrutivas (remover, cancelar). */
    public static JButton createDangerButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(FONT_BOLD);
        btn.setForeground(COLOR_DANGER_DARK);
        btn.setBackground(COLOR_DANGER_LIGHT);
        btn.setFocusPainted(false);
        btn.setBorder(new CompoundBorder(new LineBorder(new Color(254, 202, 202), 1, true), new EmptyBorder(7, 13, 7, 13)));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (btn.isEnabled()) {
                    btn.setBackground(COLOR_DANGER);
                    btn.setForeground(Color.WHITE);
                }
            }
            @Override
            public void mouseExited(MouseEvent e) {
                if (btn.isEnabled()) {
                    btn.setBackground(COLOR_DANGER_LIGHT);
                    btn.setForeground(COLOR_DANGER_DARK);
                }
            }
        });
        return btn;
    }

    /** Cria um painel cartão branco com borda suave e cantos visuais limpos. */
    public static JPanel createCardPanel() {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(COLOR_CARD);
        card.setBorder(new CompoundBorder(
                new LineBorder(COLOR_BORDER, 1, true),
                new EmptyBorder(14, 16, 14, 16)
        ));
        return card;
    }

    /** Cria o cabeçalho padronizado de uma seção ou tela. */
    public static JPanel createHeaderPanel(String title, String subtitle) {
        JPanel panel = new JPanel(new BorderLayout(4, 2));
        panel.setOpaque(false);

        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(FONT_TITLE);
        lblTitle.setForeground(COLOR_TEXT_MAIN);

        JLabel lblSub = new JLabel(subtitle);
        lblSub.setFont(FONT_SUBTITLE);
        lblSub.setForeground(COLOR_TEXT_MUTED);

        panel.add(lblTitle, BorderLayout.NORTH);
        panel.add(lblSub, BorderLayout.SOUTH);
        panel.setBorder(new EmptyBorder(2, 0, 8, 0));
        return panel;
    }

    /** Cria um card de estatística (KPI) para o Dashboard da Visão Geral. */
    public static JPanel createStatCard(String title, String value, String subtitle, Color accentColor) {
        JPanel card = new JPanel(new BorderLayout(6, 4));
        card.setBackground(COLOR_CARD);
        card.setBorder(new CompoundBorder(
                new LineBorder(COLOR_BORDER, 1, true),
                new EmptyBorder(14, 16, 14, 16)
        ));

        // Barra colorida superior
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setOpaque(false);
        JLabel lblTitle = new JLabel(title.toUpperCase());
        lblTitle.setFont(FONT_SMALL_BOLD);
        lblTitle.setForeground(COLOR_TEXT_MUTED);

        JPanel indicator = new JPanel();
        indicator.setPreferredSize(new Dimension(8, 8));
        indicator.setBackground(accentColor);
        indicator.setBorder(new LineBorder(accentColor, 1, true));

        topBar.add(lblTitle, BorderLayout.WEST);
        topBar.add(indicator, BorderLayout.EAST);

        JLabel lblVal = new JLabel(value);
        lblVal.setFont(FONT_STAT_NUMBER);
        lblVal.setForeground(COLOR_TEXT_MAIN);

        JLabel lblSub = new JLabel(subtitle);
        lblSub.setFont(FONT_SMALL);
        lblSub.setForeground(COLOR_TEXT_MUTED);

        card.add(topBar, BorderLayout.NORTH);
        card.add(lblVal, BorderLayout.CENTER);
        card.add(lblSub, BorderLayout.SOUTH);
        return card;
    }

    /** Configura o estilo profissional de uma JTable (altura de linha, cores de seleção, zebrado). */
    public static void setupTable(JTable table) {
        table.setFillsViewportHeight(true);
        table.setRowHeight(30);
        table.setFont(FONT_REGULAR);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setSelectionBackground(COLOR_TABLE_SELECTION);
        table.setSelectionForeground(COLOR_TEXT_MAIN);
        table.setGridColor(COLOR_BORDER);
        table.setShowVerticalLines(false);
        table.setShowHorizontalLines(true);

        // Estiliza o cabeçalho
        JTableHeader header = table.getTableHeader();
        header.setFont(FONT_BOLD);
        header.setBackground(COLOR_SECONDARY);
        header.setForeground(COLOR_TEXT_MAIN);
        header.setReorderingAllowed(false);
        header.setPreferredSize(new Dimension(0, 32));
        ((DefaultTableCellRenderer) header.getDefaultRenderer()).setHorizontalAlignment(SwingConstants.LEFT);

        // Renderizador zebrado para as linhas normais
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable tbl, Object value, boolean isSelected, boolean hasFocus, int row, int col) {
                Component c = super.getTableCellRendererComponent(tbl, value, isSelected, hasFocus, row, col);
                setBorder(new EmptyBorder(0, 10, 0, 10));
                if (isSelected) {
                    c.setBackground(COLOR_TABLE_SELECTION);
                    c.setForeground(COLOR_TEXT_MAIN);
                } else {
                    c.setBackground(row % 2 == 0 ? COLOR_CARD : COLOR_TABLE_ROW_ALT);
                    c.setForeground(COLOR_TEXT_MAIN);
                }
                return c;
            }
        });
    }

    /** Cria um campo de texto padronizado com padding agradável. */
    public static JTextField createTextField(int columns) {
        JTextField tf = new JTextField(columns);
        tf.setFont(FONT_REGULAR);
        tf.setBorder(new CompoundBorder(
                new LineBorder(COLOR_BORDER_INPUT, 1, true),
                new EmptyBorder(6, 10, 6, 10)
        ));
        return tf;
    }

    /** Cria uma barra de busca elegante conectada a um TableRowSorter. */
    public static JPanel createSearchBar(String placeholder, java.util.function.Consumer<String> onSearch) {
        JPanel bar = new JPanel(new BorderLayout(8, 0));
        bar.setOpaque(false);

        JLabel icon = new JLabel("🔍");
        icon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 14));
        icon.setForeground(COLOR_TEXT_MUTED);

        JTextField field = createTextField(20);
        field.putClientProperty("JTextField.placeholderText", placeholder);
        field.setToolTipText(placeholder);
        field.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            private void update() { onSearch.accept(field.getText().trim()); }
            @Override public void insertUpdate(javax.swing.event.DocumentEvent e) { update(); }
            @Override public void removeUpdate(javax.swing.event.DocumentEvent e) { update(); }
            @Override public void changedUpdate(javax.swing.event.DocumentEvent e) { update(); }
        });

        JButton btnLimpar = new JButton("×");
        btnLimpar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnLimpar.setForeground(COLOR_TEXT_MUTED);
        btnLimpar.setBorder(new EmptyBorder(2, 6, 2, 6));
        btnLimpar.setContentAreaFilled(false);
        btnLimpar.setFocusPainted(false);
        btnLimpar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnLimpar.addActionListener(e -> field.setText(""));

        bar.add(icon, BorderLayout.WEST);
        bar.add(field, BorderLayout.CENTER);
        bar.add(btnLimpar, BorderLayout.EAST);
        return bar;
    }
}
