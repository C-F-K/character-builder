package com.cfk.characterBuilder;

//import com.google.gson.*;
//import com.google.gson.annotations.*;
//import com.google.gson.stream.*;
import java.io.IOException;
import java.util.*;

public class Main {
    ArrayList<CharacterClass> classes = new ArrayList<>();

    private static HashMap<String,Command> allowedCmds = new HashMap<String, Command>(){{
        // system commands
        this.put("help", new Help());
        this.put("exit", new Exit());
        this.put("add", new AddCharacterClass());
    }};

    public static void main(String[] args) throws IOException {
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
    private interface Describable {
    	default String getDescription() {
    		return this.toString();
	    }
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
	        LinkedHashMap<String, Boolean> bork = SkillSet.generate();
	        System.out.println("heckin complete skillset");
	        System.out.println("========================");
	        bork.forEach((k,v) -> {
	        	String y0s = v ? "yes" : "no";
		        System.out.println(k + ": " + y0s);
	        });
        }
        @Override
        public String getHelpText() {
            return "Add a new character class";
        }
    }

	private enum HitDie implements Describable {
        d4(4), d6(6), d8(8), d10(10), d12(12);

        private final int hitDieSides;

        HitDie(int sides) {
            this.hitDieSides = sides;
        }

	    public int getHitDieSides() {
		    return hitDieSides;
	    }

		@Override
		public String getDescription() {
			return this.name();
		}
	}
    private enum BAB implements Describable {
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

	    @Override
	    public String getDescription() {
		    return this.name() + "_" + this.getClass();
	    }
    }
    private enum Save implements Describable {
        BAD(0.33),
        GOOD(0.5);

        private double fraction;

        Save(double fractional) {
            this.fraction = fractional;
        }

        public double getFraction() {
            return fraction;
        }

	    @Override
	    public String getDescription() {
		    return this.name() + "_" + this.getClass();
	    }
    }
    private static class CharacterClass {
    	private final String name;
        private final HitDie hitDie;
        private final BAB baseAttack;
        private final Save fortSave;
        private final Save refSave;
        private final Save willSave;
        private final int skillPoints;
        private final LinkedHashMap<String,Boolean> classSkills;
        private final ArrayList<String> prereqs;

        public CharacterClass(String name, HitDie hitDie, BAB baseAttack,
                              Save fortSave, Save refSave, Save willSave,
                              int skillPoints, LinkedHashMap<String, Boolean> classSkills,
                              ArrayList<String> prereqs) {
        	this.name = name;
            this.hitDie = hitDie;
            this.baseAttack = baseAttack;
            this.fortSave = fortSave;
            this.refSave = refSave;
            this.willSave = willSave;
            this.skillPoints = skillPoints;
            this.classSkills = classSkills;
            this.prereqs = prereqs;
        }
    }

	private static class SkillSet {
		static LinkedHashMap<String, Boolean> skills = new LinkedHashMap<String, Boolean>(){{
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
		static ArrayList<Character> menuChars = new ArrayList<Character>(){{
			// [a-zA-Z0-9]
			for (int i = 97; i < 123; i++) {
				// lowercase
				this.add((char) i);
			}
			for (int i = 65; i < 91; i++) {
				// uppercase
				this.add((char) i);
			}
			for (int i = 48; i < 58; i++) {
				// digits
				this.add((char) i);
			}
		}};
		enum Knowledge {
			All("all skills, chosen individually"),
			Arc("Arcana"),
			Eng("Architecture and Engineering"),
			Dun("Dungeoneering"),
			Geo("Geography"),
			His("History"),
			Loc("Local"),
			Nat("Nature"),
			Nob("Nobility and Royalty"),
			Rel("Religion"),
			Pla("The Planes"),
			Psi("Psionics");

			private String fullTitle;
			Knowledge(String fullTitle) {
				this.fullTitle = fullTitle;
			}
			public String getFullTitle() {
				return fullTitle;
			}
		}


		public static LinkedHashMap<String, Boolean> generate() {
			LinkedHashMap<String,Boolean> classSkillSet = new LinkedHashMap<String, Boolean>(){{
				this.putAll(skills);
			}};
			LinkedHashMap<Character, String> tableMap = new LinkedHashMap<Character, String>(){{
				Iterator<Character> iter = menuChars.iterator();
				classSkillSet.keySet().forEach((skill) -> {
					if (iter.hasNext()) {
						this.put(iter.next(), skill);
					}
				});
			}};

			int in;
		    do {
			    System.out.println("Type a letter to toggle the corresponding skill");
			    System.out.println("(Ctrl+C to save and continue)");
			    tableMap.forEach((chr, skill) -> {
//			    	char check = classSkillSet.get(skill) ? '\u2611' : '\u2610';
			    	char check = classSkillSet.get(skill) ? '\u221A' : 'X';
				    System.out.printf("[%1$s] %2$s - %3$s",check,chr,skill);
				    // TODO: do some sicc calculations here to politely format tabbing
			    });
			    System.out.println();
			    System.out.print("> ");
			    try {
				    in = RawConsoleInput.read(true);
			    } catch (IOException e) {
				    System.out.println("IOException");
				    return null;
			    }
		    	char inChr = (char) in;
			    if (tableMap.keySet().contains(inChr)) {
			    	classSkillSet.replace(tableMap.get(inChr),!classSkillSet.get(tableMap.get(inChr)));
			    }
		    } while(in != 0x3);
			if (classSkillSet.get("Knowledge")) {
				// preparatory things
				do {
					System.out.println("Type a number to toggle the corresponding Knowledge subskill");
					System.out.println("(Ctrl+C to save and continue)");
					System.out.print("> ");
					for (Knowledge k : Knowledge.values()) {
						// things
					}
					try {
						in = RawConsoleInput.read(true);
					} catch (IOException e) {
						System.out.println("IOException");
						return null;
					}
					// other things
				} while (in != 0x3);
				classSkillSet.remove("Knowledge");
			}
		    try { RawConsoleInput.resetConsoleMode(); } catch (IOException e) { return null; }

		    Scanner read = new Scanner(System.in);
		    for (String sk : new String[]{"Craft", "Perform", "Profession"}) {
		    	while (true) {
					if (classSkillSet.get(sk)) {
						System.out.println("Enter " + sk + " subskills (blank line to end");
						String skill = read.nextLine();
						if (skill.isEmpty()) {
							break;
						} else {
							classSkillSet.put(sk + " (" + skill + ")", true);
						}
					}
				}
				classSkillSet.remove(sk);
			}

			return classSkillSet;
		}
	}

    private <T extends Describable> T parseUntilGoodInputOrCancel(String prompt, ArrayList<T> allowed) {
    	// TODO: this can also be refactored to use raw mode ONCE I FIGURE OUT HOW THE FUCK TO MAKE THAT WORK
    	Scanner s = new Scanner(System.in);
	    System.out.println(prompt);
	    System.out.println("(Case sensitive; enter a blank line to cancel)");
	    HashMap<Character, T> switchMap = new HashMap<Character, T>(){{
	    	char menuOpt = 'A';
		    for (T t : allowed) {
			    this.put(menuOpt++, t);
		    }
	    }};
	    switchMap.forEach((chr,t) -> System.out.println(chr + " - " + t.getDescription()));
	    while (true) {
		    System.out.print("> ");
		    char[] in = s.nextLine().toCharArray();
		    if (in.length == 0) return null;
		    if (switchMap.containsKey(in[0])) {
		    	return switchMap.get(in[0]);
		    }
	    }
    }
}
