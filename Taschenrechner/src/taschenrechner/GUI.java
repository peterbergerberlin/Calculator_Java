package taschenrechner;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

public class GUI implements ActionListener {
    
    private static GUI instance;
    private final JFrame taschenrechner;
    private JPanel jp;
    private JTextField zahl1,zahl2;
    private JButton b0, b1, b2, b3, b4, b5, b6, b7, b8, b9, b_mult, b_add, b_sub, b_div, b_reset, b_result, b_point;
    // ein Bool welcher repräsentiert, ob das erste oder zweite 
    // Textfeld aktiv ist
    private boolean zahl1_active = true;
    private JLabel ergebnis;
    private JLabel rechenKette;
    private CalcOperation calcOp = new CalcOperation();
    
    private GUI(){
    taschenrechner = new JFrame("Taschenrechner");
    taschenrechner.setSize(400,650);
    taschenrechner.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    taschenrechner.add(getContainer());
    // Fensterposition zentrieren
    taschenrechner.setLocationRelativeTo(null);
    taschenrechner.setVisible(true);
    }
    
    public static GUI getInstance(){
        // Abfrage, ob eine Instanz existiert
        if(GUI.instance == null){
           GUI.instance = new GUI();
        }
        return GUI.instance;
    }
    
     private JPanel getContainer(){
        Font font = new Font("Calibri", Font.BOLD, 34);
        jp = new JPanel();
        jp.setLayout(null);
        zahl1 = new JTextField();
        zahl2 = new JTextField();
        zahl1.setBounds(20,20,300,50);
        zahl2.setBounds(20,90,300,50);
        zahl1.setEnabled(false);
        zahl2.setEnabled(false);
        zahl1.setDisabledTextColor(Color.BLACK);
        zahl2.setDisabledTextColor(Color.BLACK);
        zahl2.setFont(font);
        zahl1.setFont(font);
        jp.add(zahl1);
        jp.add(zahl2);
        
        ergebnis = new JLabel("Ergebnis");
        ergebnis.setBounds(20,155,360,50);
        ergebnis.setFont(font);
        jp.add(ergebnis);
        
        rechenKette = new JLabel();
        rechenKette.setBounds(20,125,360,50);
        jp.add(rechenKette);        
        
        b_reset = new JButton("R");
        b_reset.setBounds(180,200,70,70);
        b_reset.setFont(font);
        jp.add(b_reset);
        b_reset.addActionListener(this);
        
        b_result = new JButton("=");
        b_result.setBounds(260,200,70,70);
        b_result.setFont(font);
        jp.add(b_result);
        b_result.addActionListener(this);
       
        b7 = new JButton("7");
        b7.setBounds(20,280,70,70);
        b7.setFont(font);
        jp.add(b7);
        b7.addActionListener(this);
        
        b8 = new JButton("8");
        b8.setBounds(100,280,70,70);
        b8.setFont(font);
        jp.add(b8);
        b8.addActionListener(this);
        
        b9 = new JButton("9");
        b9.setBounds(180,280,70,70);
        b9.setFont(font);
        jp.add(b9);
        b9.addActionListener(this);
        
        b_div = new JButton("÷");
        b_div.setBounds(260,280,70,70);
        b_div.setFont(font);
        jp.add(b_div);
        b_div.addActionListener(this);
        
        b4 = new JButton("4");
        b4.setBounds(20,360,70,70);
        b4.setFont(font);
        jp.add(b4);
        b4.addActionListener(this);
        
        b5 = new JButton("5");
        b5.setBounds(100,360,70,70);
        b5.setFont(font);
        jp.add(b5);
        b5.addActionListener(this);
        
        b6 = new JButton("6");
        b6.setBounds(180,360,70,70);
        b6.setFont(font);
        jp.add(b6);
        b6.addActionListener(this);
        
        b_mult = new JButton("x");
        b_mult.setBounds(260,360,70,70);
        b_mult.setFont(font);
        jp.add(b_mult);
        b_mult.addActionListener(this);
        
        b1 = new JButton("1");
        b1.setBounds(20,440,70,70);
        b1.setFont(font);
        jp.add(b1);
        b1.addActionListener(this);
        
        b2 = new JButton("2");
        b2.setBounds(100,440,70,70);
        b2.setFont(font);
        jp.add(b2);
        b2.addActionListener(this);
        
        b3 = new JButton("3");
        b3.setBounds(180,440,70,70);
        b3.setFont(font);
        jp.add(b3);
        b3.addActionListener(this);
        
        b_sub = new JButton("-");
        b_sub.setBounds(260,440,70,70);
        b_sub.setFont(font);
        jp.add(b_sub);
        b_sub.addActionListener(this);
        
        b0 = new JButton("0");
        b0.setBounds(100,520,70,70);
        b0.setFont(font);
        jp.add(b0);
        b0.addActionListener(this);
        
        b_point = new JButton(".");
        b_point.setBounds(180,520,70,70);
        b_point.setFont(font);
        jp.add(b_point);
        b_point.addActionListener(this);
        
        b_add = new JButton("+");
        b_add.setBounds(260,520,70,70);
        b_add.setFont(font);
        jp.add(b_add);
        b_add.addActionListener(this);
        
        return jp;
    }
    
     // Die Methode erzeugt die rechenKette bei Eingabe von Operatoren
     // sie verhindert Mehrfacheingabe von Operatoren und ersetzt den Operator
     // bei Eingabe eines neuen Operators in der rechenkette
    private void makeRechenKette(char operator){
        char lastChar = rechenKette.getText().charAt(rechenKette.getText().length() - 1);
        if(lastChar == '.'){
            rechenKette.setText(rechenKette.getText().substring(0,rechenKette.getText().length()-1));
        }
        if(rechenKette.getText().contains("+") || rechenKette.getText().contains("-") 
           || rechenKette.getText().contains("x") || rechenKette.getText().contains("÷")){
            if(!(rechenKette.getText().contains(String.valueOf(operator)))){
                rechenKette.setText(rechenKette.getText().replace(lastChar,operator));
            }
        }else{
            rechenKette.setText(rechenKette.getText() + operator);
        }
            
    }
      
    @Override
    public void actionPerformed(ActionEvent e) {
        String eingabe = "";
        boolean resultPressed = false;
        
        if(e.getSource() == this.b0){
            eingabe = "0";
            rechenKette.setText(rechenKette.getText() + eingabe);
        }
        if(e.getSource() == this.b1){
            eingabe = "1";
            rechenKette.setText(rechenKette.getText() + eingabe);
        }
        if(e.getSource() == this.b2){
            eingabe = "2";
            rechenKette.setText(rechenKette.getText() + eingabe);
        }
        if(e.getSource() == this.b3){
            eingabe = "3";
            rechenKette.setText(rechenKette.getText() + eingabe);
        }
        if(e.getSource() == this.b4){
            eingabe = "4";
            rechenKette.setText(rechenKette.getText() + eingabe);
        }
        if(e.getSource() == this.b5){
            eingabe = "5";
            rechenKette.setText(rechenKette.getText() + eingabe);
        }
        if(e.getSource() == this.b6){
            eingabe = "6";
            rechenKette.setText(rechenKette.getText() + eingabe);
        }
        if(e.getSource() == this.b7){
            eingabe = "7";
            rechenKette.setText(rechenKette.getText() + eingabe);
        }
        if(e.getSource() == this.b8){
            eingabe = "8";
            rechenKette.setText(rechenKette.getText() + eingabe);
        }
        if(e.getSource() == this.b9){
            eingabe = "9";
            rechenKette.setText(rechenKette.getText() + eingabe);
        }
        if(e.getSource() == this.b_point){
            eingabe = ".";
            // Punkte nur in die Rechenkette hinzufügen wenn eine Zahl 
            // vorhanden ist noch kein Punkt in der jeweiligen 
            // Zahl vorhanden ist
            if(zahl1_active){
                if(!(zahl1.getText().contains(".")) && !(zahl1.getText().equals(""))){
                rechenKette.setText(rechenKette.getText() + eingabe);
                }
            } else {
                if(!(zahl2.getText().contains(".")) && !(zahl2.getText().equals(""))){
                rechenKette.setText(rechenKette.getText() + eingabe);
                }
            }
        }

        if(e.getSource() == this.b_mult){
            // nur wenn eine Zahl in das erste Textfeld eingegeben wurde
            // soll die Rechenoperation in dem calcOp-Objekt gesetzt werden,
            // zahl1_active auf false gesetzt werden und die Variable "eingabe"
            // geleert werden.
            // Äquivalent bei den anderen Rechenoperationsbuttons
            if(!zahl1.getText().equals("")){
                calcOp.setCalcOp(0);
                zahl1_active = false;
                eingabe = "";
                if(!rechenKette.getText().contains("=")){
                makeRechenKette('x');
                }
            }
            
        }
        if(e.getSource() == this.b_div){
            if(!zahl1.getText().equals("")){
                calcOp.setCalcOp(1);
                zahl1_active = false;
                eingabe = "";               
                if(!rechenKette.getText().contains("=")){
                makeRechenKette('÷');
                }
            }
        }
        if(e.getSource() == this.b_add){
            if(!zahl1.getText().equals("")){
                calcOp.setCalcOp(2);
                zahl1_active = false;
                eingabe = "";
                if(!rechenKette.getText().contains("=")){
                makeRechenKette('+');
                }
            }
        }
        if(e.getSource() == this.b_sub){
            if(!zahl1.getText().equals("")){
                calcOp.setCalcOp(3);
                zahl1_active = false;
                eingabe = "";
                if(!rechenKette.getText().contains("=")){
                makeRechenKette('-');
                }
            }
        }      
        if(e.getSource() == this.b_result){
            // Nur wenn in jeweils beide Textfelder eine Zahl eingegeben wurde
            // sollen resultPressed und zahl1_active auf true gesetzt werden
            if(!zahl1.getText().equals("") && !zahl2.getText().equals("")){
                resultPressed = true;
                zahl1_active = true;
                if(!(rechenKette.getText().charAt(rechenKette.getText().length() - 1) == '=')){
                rechenKette.setText(rechenKette.getText() + b_result.getText());
                }
            }

        }       
        if(e.getSource() == this.b_reset){
            zahl1.setText("");
            zahl2.setText("");
            ergebnis.setText("");
            rechenKette.setText("");
            zahl1_active = true;
            resultPressed = false;
        }
        
        if(zahl1_active && !resultPressed && !(eingabe.equals(".") && ((zahl1.getText().contains("."))||(zahl1.getText().equals(""))))){
            
            zahl1.setText(zahl1.getText() + eingabe);
        }
        else if (!zahl1_active && !resultPressed && !(eingabe.equals(".") && ((zahl2.getText().contains("."))||(zahl2.getText().equals(""))))){
            // Punkt abschneiden falls er als letztes in zahl1 steht
            if(zahl1.getText().charAt(zahl1.getText().length() - 1) == '.'){
            zahl1.setText(zahl1.getText().substring(0,zahl1.getText().length()-1));
            }
            zahl2.setText(zahl2.getText() + eingabe);
        }
        
        if(resultPressed){
            // Punkt abschneiden falls er als letztes in zahl2 steht
            if(zahl2.getText().charAt(zahl2.getText().length() - 1) == '.'){
            zahl2.setText(zahl2.getText().substring(0,zahl2.getText().length()-1));
            }
            // Punkt entfernen falls er als letztes in der rechenKette steht und man dann "=" drückt
            if(rechenKette.getText().charAt(rechenKette.getText().length() - 2) == '.'){
            rechenKette.setText(rechenKette.getText().substring(0,rechenKette.getText().length()-2) + rechenKette.getText().substring(rechenKette.getText().length()-1,rechenKette.getText().length()));
            }
            double z1 = Double.parseDouble(zahl1.getText());
            double z2 = Double.parseDouble(zahl2.getText());
            switch(calcOp.getCalcOp()){
                case 0:
                    ergebnis.setText(Double.toString(z1 * z2));
                    break;
                case 1:
                    ergebnis.setText(Double.toString(z1 / z2));
                    break;
                case 2:
                    ergebnis.setText(Double.toString(z1 + z2));
                    break;
                case 3:
                    ergebnis.setText(Double.toString(z1 - z2));
                    break;
                case -1:
                    ergebnis.setText("Keine Rechenoperation ausgewählt");
                    break;
                default:
                    ergebnis.setText("Fehler");
            }
        }
    }
    
}
