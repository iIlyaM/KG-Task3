package ru.vsu.cs.kg2021.g41.moldavskiy_i_m.kg3;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ParametersPanel extends JPanel {

    private List<JTextField> firstTriangleParams;
    private List<JTextField> secondTriangleParams;

    private final JTextField resultField = new JTextField();
    private final JButton startCalcButton = new JButton();
    private final JLabel firstTriangleInputPointer = new JLabel("Enter points for first triangle");
    private final JLabel SecondTriangleInputPointer = new JLabel("Enter points for second triangle");

    ParametersPanel() {
        this.setLayout(null);
        initParamFields();


    }
    
    private void initParamFields() {
        firstTriangleParams = new ArrayList<>();
        secondTriangleParams = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            firstTriangleParams.add(new JTextField());
            secondTriangleParams.add(new JTextField());
        }
    }

    private void textFieldsOnPanel() {
        JTextField field;
        for (int i = 0; i < firstTriangleParams.size(); i++) {
            field = firstTriangleParams.get(i);
            field.setBounds(50, i * 20, 80, 60);
        }
        for (int i = 0; i < secondTriangleParams.size(); i++) {
            field = secondTriangleParams.get(i);
            field.setBounds(50, i * 20, 80, 60);
        }
    }


}
