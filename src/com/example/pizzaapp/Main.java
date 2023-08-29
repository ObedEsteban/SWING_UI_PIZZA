package com.example.pizzaapp;

import javax.swing.*;

class PizzaAppMain {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new PizzaApp();
        });
    }
}
