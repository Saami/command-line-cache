package com.interview;


import com.interview.cache.api.Cache;
import com.interview.cache.impl.TextFileCache;
import com.interview.enums.Command;

import java.util.Scanner;

public class Main {

    private static final Cache cache = new TextFileCache();

    public static void main(String[] args) {

      try  {
            Scanner scanner = new Scanner(System.in);
            System.out.print("type HELP to see allowed commands or type a command");

            while (true) {
                String input = scanner.next();

                Command command = (Command) getValue(Command.class, input);
                String params = "";
                String[] paramArray= null;
                switch (command) {
                    case CREATE:
                        params = scanner.next();
                        paramArray =  params.split("=");
                        System.out.println(cache.create(paramArray[0], paramArray[1]));
                        break;
                    case UPDATE:
                         params = scanner.next();
                         paramArray =  params.split("=");
                         String output = cache.update(paramArray[0], paramArray[1]);
                         System.out.println(output);
                        break;
                    case DELETE:
                        params = scanner.next();
                        System.out.println(cache.delete(params));
                        break;
                    case GET:
                        params = scanner.next();
                        System.out.println(cache.get(params));
                        break;
                    case GETALL:
                        System.out.println(cache.getAll());
                        break;
                    case HELP:
                        System.out.println("The following commands are available");
                        for (Command com : Command.values()) {
                            System.out.println(String.format(com.getCommand() + com.getSyntax()));
                        }
                        break;
                }
            }

        } catch (Exception e) {
          System.out.println("invalid input, try again");
      }
    }

    private static <T extends Enum> Enum getValue(final Class<T> enumType, final String name) {
        try {
            if (enumType == null || !enumType.isEnum() || name.isEmpty()) {
                return null;
            }

            return Enum.valueOf(enumType, name.toUpperCase());

        } catch (Exception e) {
            return null;
        }
    }
}
