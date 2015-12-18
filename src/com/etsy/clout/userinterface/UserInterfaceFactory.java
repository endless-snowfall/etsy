package com.etsy.clout.userinterface;

import com.etsy.clout.command.CommandFactory;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserInterfaceFactory {

    public UserInterface buildUserInterface(String[] args, CommandFactory commandFactory) {
        return (args.length != 2)
            ? new CommandLineInterface(commandFactory)
            : new InputOutputFileInterface(commandFactory, args[0], args[1]);
    }
}
