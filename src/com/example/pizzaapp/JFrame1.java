package com.example.pizzaapp;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
class PizzaApp {
    private JFrame frame;
    private JComboBox<Pizza> pizzaComboBox;
    private JList<Topping> toppingList;
    private JProgressBar progressBar;
    private JLabel totalLabel;
    private double totalGastado = 0;

    public PizzaApp() {
        frame = new JFrame("Little Caesar");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(5, 1));

        pizzaComboBox = new JComboBox<>();
        pizzaComboBox.addItem(new Pizza("Jamon", 60));
        pizzaComboBox.addItem(new Pizza("Pepperoni", 70));
        pizzaComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTotal();
            }
        });

        toppingList = new JList<>();
        DefaultListModel<Topping> toppingListModel = new DefaultListModel<>();
        toppingListModel.addElement(new Topping("Orilla de queso", 25));
        toppingListModel.addElement(new Topping("Salsa", 15));
        toppingList.setModel(toppingListModel);
        toppingList.addListSelectionListener(e -> updateTotal());

        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);

        totalLabel = new JLabel("Total: Q" + totalGastado);

        JButton preparePizzaButton = new JButton("Preparar Pizza");
        preparePizzaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                preparePizza();
            }
        });

        frame.add(pizzaComboBox);
        frame.add(toppingList);
        frame.add(progressBar);
        frame.add(preparePizzaButton);
        frame.add(totalLabel);

        frame.pack();
        frame.setVisible(true);
    }


    private void updateTotal() {
        totalGastado = ((Pizza) pizzaComboBox.getSelectedItem()).getPrice();
        totalGastado += toppingList.getSelectedValuesList().stream().mapToDouble(Topping::getPrice).sum();
        totalLabel.setText("Total: Q" + totalGastado);
    }

    private void preparePizza() {
        SwingWorker<Void, Integer> worker = new SwingWorker<Void, Integer>() {
            @Override
            protected Void doInBackground() throws Exception {
                for (int i = 0; i <= 100; i++) {
                    Thread.sleep(50);
                    publish(i);
                }
                return null;
            }

            @Override
            protected void process(java.util.List<Integer> chunks) {
                int latestProgress = chunks.get(chunks.size() - 1);
                progressBar.setValue(latestProgress);
            }

            @Override
            protected void done() {
                progressBar.setValue(100);
                JOptionPane.showMessageDialog(frame, "¡La pizza está lista!");
                progressBar.setValue(0);
                totalLabel.setText("Total: Q" + totalGastado);
            }
        };

        worker.execute();
    }


    }

