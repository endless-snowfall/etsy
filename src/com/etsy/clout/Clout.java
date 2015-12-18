package com.etsy.clout;

import com.etsy.clout.command.CommandFactory;
import com.etsy.clout.service.CloutServiceImpl;
import com.etsy.clout.userinterface.UserInterfaceFactory;

public class Clout {

    public static void main(String args[]) {
        // args = new String[2];
        // args[0] =
        // "/Users/anthony/Documents/workspace/test/tst/com/etsy/clout/integration/input/sample.in";
        // args[1] =
        // "/Users/anthony/Documents/workspace/test/tst/com/etsy/clout/integration/output/sample.out";

        new UserInterfaceFactory()
            .buildUserInterface(args, CommandFactory.of(new CloutServiceImpl()))
            .run();
    }
}
