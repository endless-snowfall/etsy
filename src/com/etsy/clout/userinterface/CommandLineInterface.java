package com.etsy.clout.userinterface;

import java.util.Scanner;

import com.etsy.clout.command.CommandFactory;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CommandLineInterface implements UserInterface {

    private CommandFactory commandFactory;

    @Override
    public void run() {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Welcome to 'clout', type 'exit' to quit.");

            while (true) {
                System.out.print("> ");
                String input = scanner.nextLine();
                commandFactory.buildCommand(input)
                    .execute()
                    .getResponses()
                    .forEach(response -> System.out.println(response));
            }
        }
    }
}
