package com.etsy.clout.command;

import com.etsy.clout.concepts.Response;

public class ExitCommand implements Command {

    @Override
    public Response execute() {
        System.exit(0);
        return null;
    }
}
