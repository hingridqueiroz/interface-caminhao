import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class InterfaceGrafica extends JFrame {

    JLabel lblPlaca, lblCombustivel, lblDataChegada, lblVolume;
    JLabel lblTotalCaminhoes, lblListaCaminhoes;

    JTextField txtPlaca, txtCombustivel, txtDataChegada, txtVolume;
    JButton btnSalvar, btnCancelar;

    JList jlstCaminhoes;
    DefaultListModel mdlListaCaminhoes;

    // Lista para guardar os caminhões
    ArrayList<Caminhao> lstCaminhoes = new ArrayList<>();

    InterfaceGrafica() {
        // Para colocar os nomes e campos de texto
        lblPlaca = new JLabel("Placa (AAA-1234)");
        txtPlaca = new JTextField();
        lblCombustivel = new JLabel("Combustível");
        txtCombustivel = new JTextField();
        lblDataChegada = new JLabel("Data de chegada (dd/mm/aaaa)");
        txtDataChegada = new JTextField();
        lblVolume = new JLabel("Volume (Litros)");
        txtVolume = new JTextField();

        // Inserindo os botões de Salvar e Cancelar
        btnSalvar = new JButton("Salvar");
        btnCancelar = new JButton("Cancelar");
        // Contador da quantidade de caminhões
        lblTotalCaminhoes = new JLabel("Total de caminhões: 0");

        // Lista de caminhões que foram inseridos
        lblListaCaminhoes = new JLabel("Lista de caminhões");
        mdlListaCaminhoes = new DefaultListModel();
        jlstCaminhoes = new JList(mdlListaCaminhoes);

        //Cores e Fontes
        Color corSalvar = new Color(107, 142, 35);
        Color corCancelar = new Color(220, 20, 60);

        btnSalvar.setBackground(corSalvar);
        btnSalvar.setForeground(Color.WHITE);
        btnCancelar.setBackground(corCancelar);
        btnCancelar.setForeground(Color.WHITE);

        btnSalvar.setFont(new Font("Arial", Font.BOLD, 16));
        btnCancelar.setFont(new Font("Arial", Font.BOLD, 16));
        lblPlaca.setFont(new Font("Arial", Font.PLAIN, 15));
        lblCombustivel.setFont(new Font("Arial", Font.PLAIN, 15));
        lblDataChegada.setFont(new Font("Arial", Font.PLAIN, 15));
        lblVolume.setFont(new Font("Arial", Font.PLAIN, 15));
        lblTotalCaminhoes.setFont(new Font("Arial", Font.PLAIN, 14));
        lblListaCaminhoes.setFont(new Font("Arial", Font.ITALIC, 15));


        // Torna os botões "clicáveis" e funcionais
        EventoHandler handler = new EventoHandler();
        btnSalvar.addActionListener(handler);
        btnCancelar.addActionListener(handler);

        // Gerencia o layout da janela
        JPanel formularioPane = new JPanel();
        formularioPane.setLayout(new GridLayout(6, 2, 10, 10));
        formularioPane.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25)); // Borda
        // Adiciona os controles
        formularioPane.add(lblPlaca);
        formularioPane.add(txtPlaca);
        formularioPane.add(lblCombustivel);
        formularioPane.add(txtCombustivel);
        formularioPane.add(lblDataChegada);
        formularioPane.add(txtDataChegada);
        formularioPane.add(lblVolume);
        formularioPane.add(txtVolume);
        formularioPane.add(btnSalvar);
        formularioPane.add(btnCancelar);
        formularioPane.add(lblTotalCaminhoes);

        // Cria o painel de rolagem (JScrollPane) para a JList (jlstCaminhoes)
        JScrollPane listScroller = new JScrollPane(jlstCaminhoes);
        listScroller.setPreferredSize(new Dimension(250, 250)); // Tamanho da janela
        listScroller.setAlignmentX(LEFT_ALIGNMENT); // Alinha à esquerda

        // Cria a JPanel para organizar os componentes
        JPanel listPane = new JPanel();
        listPane.setLayout(new BoxLayout(listPane, BoxLayout.PAGE_AXIS)); // Se expande na vertical
        listPane.add(lblListaCaminhoes);
        listPane.add(Box.createRigidArea(new Dimension(5, 5)));
        listPane.add(listScroller);
        listPane.setBorder(BorderFactory.createEmptyBorder(20, 25, 25, 25));
        // O container da Janela (ContentPane) e os dois paineis com formulário (superior-Norte) e lista (Centro)
        Container contentPane = getContentPane();
        contentPane.add(formularioPane, BorderLayout.NORTH);
        contentPane.add(listPane, BorderLayout.CENTER);
    }

    // Cria o novo item
    public void NovoCaminhao() {
        String placa = txtPlaca.getText();
        String combustivel = txtCombustivel.getText();
        LocalDate dataChegada = null;
        double volume;

        // Try/Catch é uma forma de lançar exceções - Essa parte é para fazer as validações
        try {
            volume = Double.parseDouble(txtVolume.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "O volume deve ser um número válido."); // Mostra mensagem de erro
            return; // Retorna sem criar um novo caminhão
        }

        if (!placa.matches("[A-Z]{3}-[0-9]{4}")) {
            JOptionPane.showMessageDialog(null, "A placa deve ser no formato AAA-1234.");
            return; // Retorna sem criar um novo caminhão
        }

        // Obtem a data de chegada do campo de texto
        try {
            String dataChegadaStr = txtDataChegada.getText();
            dataChegada = LocalDate.parse(dataChegadaStr, DateTimeFormatter.ofPattern("dd/MM/yyyy")); // Formata a data para o padrão BR
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(null, "Formato de data inválido. Use dd/mm/aaaa.");
            return;
        }

        // Cria e adiciona um novo caminhão
        Caminhao caminhao = new Caminhao(placa, combustivel, dataChegada, volume);
        lstCaminhoes.add(caminhao);
        lblTotalCaminhoes.setText("Total de caminhões: " + lstCaminhoes.size());
        AtualizarLista();
    }

    public void AtualizarLista() {
        mdlListaCaminhoes.clear();
        int id = 1;// Variável para numerar os caminhões
        for (Caminhao aux : lstCaminhoes) {
            // Formata a string para cada caminhão
            String infoCaminhao = String.format("Caminhão %d: (Placa: %s, Combustível: %s, Chegada: %s, Volume: %.2f)",
                    id, aux.getPlaca(), aux.getCombustivel(), aux.getDataChegada(), aux.getVolume());

            mdlListaCaminhoes.addElement(infoCaminhao);
            id++; // Incrementa o número do caminhão
        }
    }

    public static void main(String[] args) {
        JFrame f = new InterfaceGrafica();
        f.setTitle("Caminhões");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Fecha a janela no "X" - padrão
        f.setBounds(600, 600, 600, 600); // Tamanho da janela
        f.setVisible(true); // Torna a janela visível
        f.setLocationRelativeTo(null);

    }

    // Classe interna private para tratamento de evento
    private class EventoHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            if (event.getSource() == btnSalvar) {
                NovoCaminhao();
            } else if (event.getSource() == btnCancelar) {
                // Limpa o texto já escrito nas caixas de texto
                txtPlaca.setText("");
                txtCombustivel.setText("");
                txtDataChegada.setText("");
                txtVolume.setText("");
                JOptionPane.showMessageDialog(null, "Operação cancelada.");
            }
        }
    }
}