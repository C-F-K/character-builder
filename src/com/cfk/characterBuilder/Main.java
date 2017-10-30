package com.cfk.characterBuilder;

import java.util.*;

public class Main {
    private static HashMap<String,Command> allowedCmds = new HashMap<>(){{
        // system commands
        this.put("help", new Help());
        this.put("exit", new Exit());
    }};

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        String cmd;
        String[] params;

        while(true) {
            System.out.print("> ");
            params = s.nextLine().toLowerCase().split(" ");
            cmd = params[0];
            if (allowedCmds.containsKey(cmd)) {
                allowedCmds.get(cmd).doCommand(Arrays.copyOfRange(params,1,params.length));
            }
        }
    }

    private interface Command {
        void doCommand(String... params);
        String getHelpText();
    }

    private static class Exit implements Command {
        @Override
        public void doCommand(String... params) {
            System.exit(0);
        }
        @Override
        public String getHelpText() {
            return "Exit to command line";
        }
    }

    private static class Help implements Command {
        @Override
        public void doCommand(String... params) {
            for (String s : allowedCmds.keySet()) {
                System.out.println(s + " - " + allowedCmds.get(s).getHelpText());
            }
        }
        @Override
        public String getHelpText() {
            return "Display this text";
        }
    }

}
