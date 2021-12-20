package ru.vsu.cs.kg2021.g41.moldavskiy_i_m.kg3;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {

    private final DrawPanel drawPanel;


    public MainWindow() throws HeadlessException {
        drawPanel = new DrawPanel();
        add(drawPanel);
    }
}