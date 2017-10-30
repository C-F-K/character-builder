package com.cfk.characterBuilder;

import java.util.*;

public class Main {
    ArrayList<CharacterClass> classes = new ArrayList<>();
    LinkedHashMap<String, Boolean> skillSet = new LinkedHashMap<>(){{
        this.put("Appraise",false);
        this.put("Autohypnosis",false);
        this.put("Balance",false);
        this.put("Bluff",false);
        this.put("Climb",false);
        this.put("Concentration",false);
        this.put("Craft",false);
        this.put("Decipher Script",false);
        this.put("Diplomacy",false);
        this.put("Disable Device",false);
        this.put("Disguise",false);
        this.put("Escape Artist",false);
        this.put("Forgery",false);
        this.put("Gather Information",false);
        this.put("Handle Animal",false);
        this.put("Heal",false);
        this.put("Hide",false);
        this.put("Iaijutsu Focus",false);
        this.put("Intimidate",false);
        this.put("Jump",false);
        this.put("Knowledge",false);
        this.put("Listen",false);
        this.put("Move Silently",false);
        this.put("Open Lock",false);
        this.put("Perform",false);
        this.put("Profession",false);
        this.put("Psicraft",false);
        this.put("Ride",false);
        this.put("Search",false);
        this.put("Sense Motive",false);
        this.put("Sleight Of Hand",false);
        this.put("Speak Language",false);
        this.put("Spellcraft",false);
        this.put("Spot",false);
        this.put("Survival",false);
        this.put("Swim",false);
        this.put("Tumble",false);
        this.put("Use Magic Device",false);
        this.put("Use Psionic Device",false);
        this.put("Use Rope",false);
    }};
    enum Knowledge {
        Arc, Eng, Dun, Geo, His, Loc, Nat, Nob, Rel, Pla, Psi
    }

    private static HashMap<String,Command> allowedCmds = new HashMap<>(){{
        // system commands
        this.put("help", new Help());
        this.put("exit", new Exit());
        this.put("add", new AddCharacterClass());
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
    private static class AddCharacterClass implements Command {
        @Override
        public void doCommand(String... params) {
            // do stuff
        }
        @Override
        public String getHelpText() {
            return "Add a new character class";
        }
    }

    private enum BAB {
        BAD(0.5),
        MEDIUM(0.75),
        GOOD(1);

        private double fraction;

        BAB(double fractional) {
            this.fraction = fractional;
        }

        public double getFraction() {
            return fraction;
        }
    }
    private enum Save {
        BAD(0.33),
        GOOD(0.5);

        private double fraction;

        Save(double fractional) {
            this.fraction = fractional;
        }

        public double getFraction() {
            return fraction;
        }
    }
    private static class CharacterClass {
        private final BAB baseAttack;
        private final Save fortSave;
        private final Save refSave;
        private final Save willSave;
        private final int skillPoints;
        private final LinkedHashMap<String,Boolean> classSkills;
        private final ArrayList<String> prereqs;

        public CharacterClass(BAB baseAttack, Save fortSave, Save refSave, Save willSave,
                              int skillPoints, LinkedHashMap<String, Boolean> classSkills,
                              ArrayList<String> prereqs) {
            this.baseAttack = baseAttack;
            this.fortSave = fortSave;
            this.refSave = refSave;
            this.willSave = willSave;
            this.skillPoints = skillPoints;
            this.classSkills = classSkills;
            this.prereqs = prereqs;
        }


    }
}
