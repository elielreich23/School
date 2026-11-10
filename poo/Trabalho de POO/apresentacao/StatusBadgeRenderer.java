package apresentacao;

import dados.NivelCurso;
import dados.SituacaoMensalidade;
import dados.SituacaoTurma;
import dados.TipoAvaliacao;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * Renderizador de células de tabela que desenha "badges" (etiquetas coloridas)
 * para enumerações e estados como Situação de Turma, Situação de Mensalidade, etc. tambem das cogu
 */
public class StatusBadgeRenderer extends DefaultTableCellRenderer {

    private final JLabel badgeLabel = new JLabel() {
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fillRoundRect(2, 2, getWidth() - 4, getHeight() - 4, 12, 12);
            super.paintComponent(g2);
            g2.dispose();
        }
    };

    public StatusBadgeRenderer() {
        badgeLabel.setOpaque(false);
        badgeLabel.setFont(UITheme.FONT_SMALL_BOLD);
        badgeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        badgeLabel.setBorder(new EmptyBorder(2, 8, 2, 8));
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setOpaque(true);
        wrapper.setBackground(isSelected ? UITheme.COLOR_TABLE_SELECTION : (row % 2 == 0 ? UITheme.COLOR_CARD : UITheme.COLOR_TABLE_ROW_ALT));

        if (value == null) {
            badgeLabel.setText("-");
            badgeLabel.setBackground(Color.LIGHT_GRAY);
            badgeLabel.setForeground(Color.DARK_GRAY);
            wrapper.add(badgeLabel);
            return wrapper;
        }

        String texto = value.toString();
        Color bg = new Color(241, 245, 249);
        Color fg = new Color(71, 85, 105);

        if (value instanceof SituacaoTurma situacao) {
            switch (situacao) {
                case ABERTA -> {
                    bg = new Color(220, 252, 231);
                    fg = new Color(22, 101, 52);
                    texto = "ABERTA";
                }
                case EM_ANDAMENTO -> {
                    bg = new Color(224, 231, 255);
                    fg = new Color(55, 48, 163);
                    texto = "EM ANDAMENTO";
                }
                case CONCLUIDA -> {
                    bg = new Color(241, 245, 249);
                    fg = new Color(71, 85, 105);
                    texto = "CONCLUÍDA";
                }
            }
        } else if (value instanceof SituacaoMensalidade situacao) {
            switch (situacao) {
                case PAGA -> {
                    bg = new Color(220, 252, 231);
                    fg = new Color(22, 101, 52);
                    texto = "PAGA";
                }
                case PENDENTE -> {
                    bg = new Color(254, 243, 199);
                    fg = new Color(146, 64, 14);
                    texto = "PENDENTE";
                }
                case VENCIDA -> {
                    bg = new Color(254, 226, 226);
                    fg = new Color(153, 27, 27);
                    texto = "VENCIDA";
                }
                case CANCELADA -> {
                    bg = new Color(241, 245, 249);
                    fg = new Color(100, 116, 139);
                    texto = "CANCELADA";
                }
            }
        } else if (value instanceof TipoAvaliacao tipo) {
            switch (tipo) {
                case PROVA_ESCRITA -> {
                    bg = new Color(237, 233, 254);
                    fg = new Color(109, 40, 217);
                    texto = "PROVA ESCRITA";
                }
                case PROVA_ORAL -> {
                    bg = new Color(243, 232, 255);
                    fg = new Color(126, 34, 206);
                    texto = "PROVA ORAL";
                }
                case TRABALHO -> {
                    bg = new Color(224, 242, 254);
                    fg = new Color(3, 105, 161);
                    texto = "TRABALHO";
                }
                case EXERCICIO -> {
                    bg = new Color(254, 249, 195);
                    fg = new Color(133, 77, 14);
                    texto = "EXERCÍCIO";
                }
            }
        } else if (value instanceof NivelCurso nivel) {
            switch (nivel) {
                case BASICO -> {
                    bg = new Color(220, 252, 231);
                    fg = new Color(21, 128, 61);
                    texto = "BÁSICO";
                }
                case INTERMEDIARIO -> {
                    bg = new Color(224, 242, 254);
                    fg = new Color(14, 116, 144);
                    texto = "INTERMEDIÁRIO";
                }
                case AVANCADO -> {
                    bg = new Color(243, 232, 255);
                    fg = new Color(126, 34, 206);
                    texto = "AVANÇADO";
                }
            }
        } else if (value instanceof Boolean bool) {
            if (bool) {
                bg = new Color(220, 252, 231);
                fg = new Color(22, 101, 52);
                texto = "SIM / ATIVA";
            } else {
                bg = new Color(254, 226, 226);
                fg = new Color(153, 27, 27);
                texto = "NÃO / INATIVA";
            }
        }

        badgeLabel.setText(texto);
        badgeLabel.setBackground(bg);
        badgeLabel.setForeground(fg);
        wrapper.add(badgeLabel);
        return wrapper;
    }
}
