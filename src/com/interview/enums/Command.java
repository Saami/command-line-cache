package com.interview.enums;

/**
 * Created by sasiddi on 3/13/17.
 */
public enum Command {
    CREATE("create", "<key>=<value>",2),
    UPDATE("update", "<key>=<value>", 2),
    GET("get", "<key>", 1),
    DELETE("key", "<key>", 1),
    GETALL("getall","" ,0),
    HELP("help","", 0);

    private String command;
    private String syntax;
    private Integer args;

    Command(String command, String syntax, Integer args) {
        this.command = command;
        this.syntax = syntax;
        this.args = args;
    }

    public String getCommand() {
        return command;
    }

    public String getSyntax() {
        return syntax;
    }

    public Integer getArgs() {
        return args;
    }
}
