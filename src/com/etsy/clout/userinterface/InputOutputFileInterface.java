package com.etsy.clout.userinterface;

import static java.util.stream.Collectors.*;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

import com.etsy.clout.command.Command;
import com.etsy.clout.command.CommandFactory;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

@AllArgsConstructor
public class InputOutputFileInterface implements UserInterface {

    private static final char TAB = '\t';

    private CommandFactory commandFactory;
    private String inputFileFullPath;
    private String outputFileFullPath;

    @SneakyThrows
    @Override
    public void run() {
        List<String> output = Files.lines(Paths.get(inputFileFullPath))
            .flatMap(this::processInput)
            .collect(toList());

        Files.write(Paths.get(outputFileFullPath), output);
    }

    private Stream<String> processInput(String input) {
        Command command = commandFactory.buildCommand(input);
        return Stream.concat(Stream.of(input), formatAndGetResponses(command));
    }

    private Stream<String> formatAndGetResponses(Command command) {
        return command.execute().getResponses().stream().map(this::formatResponse);
    }

    private String formatResponse(String response) {
        return TAB + response;
    }
}
