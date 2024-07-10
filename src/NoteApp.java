import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class NoteApp extends JFrame
{
    private final JTextField titelText = new JTextField();
    private final JPanel buttonPanel = new JPanel();
    private final JTextArea ausgabeTextArea = new JTextArea();
    private final HashMap<JButton, Notiz> notizenMap;
    private JRadioButton bigText;
    private JRadioButton smalText;
    //Konstruktor
    NoteApp()
    {
        JFrame frame = new JFrame("NoteLote");
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("PersonenFormular");
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setSize(Toolkit.getDefaultToolkit().getScreenSize().width/3,
                Toolkit.getDefaultToolkit().getScreenSize().height-500);
        notizenMap = new HashMap<>();

        JPanel ausgabePanel = new JPanel();
        ausgabePanel.setLayout(new BorderLayout());
        ausgabePanel.setBackground(Color.darkGray);

        JPanel titelPanel = new JPanel();
        titelPanel.setLayout(new BorderLayout(5, 5)); // Abstand zwischen Komponenten

        JLabel titelLabel = new JLabel("Titel");
        titelLabel.setFont(new Font("Roboto", Font.BOLD, 19));
        titelLabel.setPreferredSize(new Dimension(50, 30));

        titelText.setPreferredSize(new Dimension(200, 30));
        titelText.setFont(new Font("Roboto", Font.BOLD, 19));
        titelText.setEnabled(false);

        titelPanel.add(titelLabel, BorderLayout.WEST);
        titelPanel.add(titelText, BorderLayout.CENTER);


        ausgabeTextArea.setFont(new Font("Roboto", Font.BOLD, 16));
        ausgabeTextArea.setBackground(new Color(134, 187, 226));
        ausgabeTextArea.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        ausgabeTextArea.setLineWrap(true);
        ausgabeTextArea.setWrapStyleWord(true);
        ausgabeTextArea.setEnabled(false);
        // Fügen Sie hier weitere Buttons hinzu, wenn nötig

        JPanel speicherPanel = new JPanel();
        speicherPanel.setLayout(new FlowLayout());
        speicherPanel.setBackground(Color.darkGray);

        JButton speicherButton = new JButton("speichern");
        speicherButton.setFont(new Font("Roboto", Font.BOLD, 14));
        speicherButton.setPreferredSize(new Dimension(100, 30)); // Setzt die bevorzugte Größe des Buttons


         bigText = new JRadioButton("BigText");
        bigText.setFont(new Font("Roboto",Font.BOLD,13));
        bigText.setForeground(Color.white);
        bigText.setPreferredSize(new Dimension(100,30));
        bigText.setSelected(false);
        bigText.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (smalText.isSelected())
                    smalText.setSelected(false);

                ausgabeTextArea.setFont(new Font("Roboto",Font.BOLD,22));
            }
        });

        smalText = new JRadioButton("SmallText");
        smalText.setFont(new Font("Roboto",Font.BOLD,13));
        smalText.setForeground(Color.white);
        smalText.setPreferredSize(new Dimension(100,30));
        smalText.setSelected(true);
        smalText.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (bigText.isSelected())
                    bigText.setSelected(false);

                ausgabeTextArea.setFont(new Font("Roboto",Font.BOLD,16));
            }
        });

        speicherPanel.add(bigText);
        speicherPanel.add(smalText);
        speicherPanel.add(speicherButton);

        ausgabePanel.add(titelPanel, BorderLayout.NORTH);
        ausgabePanel.add(new JScrollPane(ausgabeTextArea), BorderLayout.CENTER);
        ausgabePanel.add(speicherPanel,BorderLayout.SOUTH);

        // NotizListePanel

        JPanel notizlistePanel = new JPanel();
        notizlistePanel.setLayout(new BorderLayout());
        notizlistePanel.setBackground(Color.darkGray);


        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS)); // Vertikales Layout für Buttons mit Abstand
        buttonPanel.setBackground(Color.darkGray);


        JButton addButton = new JButton("+");
        addButton.setFont(new Font("Roboto", Font.BOLD, 19));
        addButton.setSize(new Dimension(140, 60));
        addButton.setBackground(new Color(255, 255, 255)); // Weiß
        buttonPanel.add(addButton);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                addNewButton();
            }
        });


        JScrollPane scrollPane = new JScrollPane(buttonPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        notizlistePanel.add(scrollPane, BorderLayout.CENTER);

        speicherButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveCurrentNotiz();
            }
        });

        //Splitpanel
        frame.getContentPane().setLayout(new GridLayout());

        JPanel pnl_links = notizlistePanel;
        JPanel pnl_recht = ausgabePanel;

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setLeftComponent(pnl_links);
        splitPane.setRightComponent(new JScrollPane(pnl_recht, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED));
        splitPane.setDividerLocation(150); // Setzt die Breite der linken Spalte auf 50 Pixel
        splitPane.setEnabled(false); // Deaktiviert das Ziehen der Split-Leiste
        frame.add(splitPane);
    } // Ende Des Konstruktors


    private void addNewButton() {
        JButton anotherButton = new JButton();
        anotherButton.setFont(new Font("Roboto", Font.BOLD, 19));
        anotherButton.setPreferredSize(new Dimension(140, 60));
        anotherButton.setBackground(new Color(255, 255, 255)); // Weiß
        buttonPanel.add(anotherButton);

        if (!ausgabeTextArea.getText().isEmpty() || !titelText.getText().isEmpty()) {
            ausgabeTextArea.setText("");
            titelText.setText("");
        } else {
            titelText.setEnabled(true);
            ausgabeTextArea.setEnabled(true);
        }

        anotherButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showNotiz(anotherButton);
            }
        });

        buttonPanel.revalidate();
        buttonPanel.repaint();
    }

    private void saveCurrentNotiz() {
        for (Component component : buttonPanel.getComponents()) {
            if (component instanceof JButton) {
                JButton button = (JButton) component;
                if (button.getText().isEmpty()) {
                    button.setText(titelText.getText());
                    Notiz notiz = new Notiz(titelText.getText(), ausgabeTextArea.getText());
                    notizenMap.put(button, notiz);
                    break;
                }
            }
        }
    }

    private void showNotiz(JButton button) {
        Notiz notiz = notizenMap.get(button);
        if (notiz != null) {
            titelText.setText(notiz.getTitel());
            ausgabeTextArea.setText(notiz.getInhalt());
        }
    }









}
