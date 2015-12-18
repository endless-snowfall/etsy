package com.etsy.clout;

import com.etsy.clout.command.CommandFactory;
import com.etsy.clout.service.CloutServiceImpl;
import com.etsy.clout.userinterface.UserInterfaceFactory;

public class Clout {

    public static void main(String args[]) {
        new UserInterfaceFactory()
            .buildUserInterface(args, CommandFactory.of(new CloutServiceImpl()))
            .run();
    }
}
