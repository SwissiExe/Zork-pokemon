package com.bbw.pokemon;

import com.bbw.pokemon.model.AttackModel;
import com.bbw.pokemon.model.EndBossModel;
import com.bbw.pokemon.model.ItemModel;
import com.bbw.pokemon.model.PokemonModel;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private Parser parser;
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    private boolean endbossFightActive = false;
    private List<ItemModel> inventory = new ArrayList<>();
    private static final int MAX_INVENTORY_SIZE = 4;
    private Room currentRoom;
    private int effectPower = 0;
    private int Round = 0;
    private PokemonModel starterPokemon;
    private AttackModel enbossAttack = new AttackModel("girll", 20, 1000);
    public PokemonModel[] pokemonslist = {
            new PokemonModel(1, "Pikachu", 60, 150, 50, 120, new AttackModel[]{new AttackModel("Donnerschlag", 15, 3), new AttackModel("Donnerblitz", 10, 2)}),
            new PokemonModel(2, "Charizard", 78, 84, 58, 100, new AttackModel[]{new AttackModel("Feuersturm", 20, 2), new AttackModel("Läuterfeuer", 10, 4)}),
            new PokemonModel(3, "Lapras", 170, 55, 80, 60, new AttackModel[]{new AttackModel("Whirlpool", 7,6), new AttackModel("Taucher", 8, 4)})
    };

    public EndBossModel endboss = new EndBossModel("bon-chan", 490, 50, 60, 90,  new AttackModel[]{new AttackModel("side eye", 4, 1000), new AttackModel("blabla", 10, 100)});
    private Room start, Endboss, Schatzkammer, Waffenraum, Gefängnis;



    public Game() {
        parser = new Parser(System.in);
        start = new Room("Start", new ItemModel[]{new ItemModel("Trank", 10, 1, "trank"), new ItemModel("Attackbuff", 10, 2, "Abuff")});
        Endboss = new Room("EndBoss", new ItemModel[]{new ItemModel("s", 9, 1, "trank"), new ItemModel("s", 10, 2 , "trank")});
        Schatzkammer = new Room("Schatzkammer", new ItemModel[]{new ItemModel("Grosser Trank", 15, 1 ,"trank"), new ItemModel("Defense Buff", 10, 2 , "Dbuff")});
        Waffenraum = new Room("Waffenraum", new ItemModel[]{new ItemModel("sandwich", 9, 1, "trank"), new ItemModel("grosser Trank", 10, 2 , "trank")});
        Gefängnis = new Room("Gefängnis",  new ItemModel[]{new ItemModel("kleiner Trank", 5, 1, "trank"), new ItemModel("Overload", 0, 2 , "effect"),new ItemModel("Attackbuff", 15, 3, "Abuff")});

        start.setExits(Schatzkammer, null);
        Schatzkammer.setExits(Waffenraum, start);
        Waffenraum.setExits(Gefängnis, Schatzkammer);
        Gefängnis.setExits(Endboss, Waffenraum);
        Endboss.setExits(null, null);

        currentRoom = start; // start game outside
    }


    public void play() {
        printStarterPokemon();
        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
        boolean finished = false;
        while (!finished) {

            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    private void printWelcome() {
        System.out.println();
        System.out.println("   _______    ______    __   ___  _______  ___      ___     ______    _____  ___  ");
        System.out.println("  |   __ \"\\  /    \" \\  |/\"| /  \")/\"     \"||\"  \\    /\"  |   /    \" \\  (\\\"   \\|\"  \\  ");
        System.out.println("  (. |__) :)//\\ ____  \\ (: |/   /(: ______) \\   \\  //   |  // ____  \\ |.\\\\   \\    | ");
        System.out.println("  |:  ____//  /    ) :)|    __/  \\/    |   /\\\\  \\/.    | /  /    ) :)|: \\.   \\\\  | ");
        System.out.println("  (|  /   (: (____/ // (// _  \\  // ___)_ |: \\.        |(: (____/ // |.  \\    \\. | ");
        System.out.println(" /|__/ \\   \\        /  |: | \\  \\(:      \"||.  \\    /:  | \\        /  |    \\    \\ | ");
        System.out.println("(_______)   \\\"_____/   (__|  \\__)\\_______)|___|\\__/|___|  \\\"_____/    \\___|\\____\\) ");
        System.out.println("                                                                                   ");
        System.out.println("new Pokemon Game");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
    }

    public void addItemToInventory(ItemModel item) {
        if (inventory.size() < MAX_INVENTORY_SIZE) {
            inventory.add(item);
            System.out.println("Du hast " + item.getName() + " zu deinem Inventar hinzugefügt.");
        } else {
            System.out.println("Dein Inventar ist voll. Du kannst kein weiteres Item tragen.");
        }
    }

    public void useItemFromInventory(Command command) {
        if (command.hasSecondWord()) {
            String itemName = command.getSecondWord();
            ItemModel itemToRemove = null;

            for (ItemModel item : inventory) {
                if (item.getId() == Integer.parseInt(itemName)) {
                    if(item.getKategorie().equals("trank")) {
                        starterPokemon.setHp(starterPokemon.getHp() + item.getBuff());
                        System.out.println("Dein Pokémon wurde um " + item.getBuff() + " HP geheilt.");
                    }
                    else if (item.getKategorie().equals("Abuff")) {
                        starterPokemon.setAttack((starterPokemon.getAttack()) + item.getBuff());
                        System.out.println("Dein Pokémon wurde um " + item.getBuff() + " verstärkt");
                    }
                    else if (item.getKategorie().equals("Dbuff")) {
                        starterPokemon.setDefense((starterPokemon.getDefense()) + item.getBuff());
                        System.out.println("Dein Pokémon hat seine Defense um " + item.getBuff() + " verstärkt");
                    }
                    else if (item.getKategorie().equals("effect")) {
                        this.effectPower = 2;
                    }


                    itemToRemove = item;
                    break;
                }
            }

            if (itemToRemove != null) {
                inventory.remove(itemToRemove);
            } else {
                System.out.println("Du besitzt kein Item namens '" + itemName + "' in deinem Inventar.");
            }
        } else {
            System.out.println("Welches Item möchtest du verwenden? Gib den Namen des Items nach 'use' ein.");
        }
    }

    private boolean processCommand(Command command) {
        if (command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("help")) {
            printHelp();

        } else if (commandWord.equals("go")) {
            if(currentRoom == Gefängnis && command.getSecondWord().equals("east")) {
                endbossfight();
            }
            goRoom(command);

        }
        else if (commandWord.equals("location")) {
            showlocation();
        }
        else if (commandWord.equals("map")) {
            showmap();
        }
        else if (commandWord.equals("bag")) {
            bag();
        }
        else if (commandWord.equals("items")) {
            showitems();
        }
        else if (commandWord.equals("removeitem")) {
            removeItems(command);
        }
        else if (commandWord.equals("select") && starterPokemon == null) {
            selectStarter(command);
        }
        else if (commandWord.equals("select") && starterPokemon != null) {
            selectitem(command);
        }
        else if (commandWord.equals("pinfo")) {
            infoStarter(command);
        }
        else if (commandWord.equals("quit")) {
            if (command.hasSecondWord()) {
                System.out.println("Quit what?");
            } else {
                return true;
            }
        }
        return false;
    }


    private void printHelp() {
        System.out.println("this are all the Commands");
        System.out.println("");
        System.out.println("navigation commands ");
        System.out.println("----------------------------");
        System.out.println("location | zeigt dir die Map sowie die aktuelle Position an");
        System.out.println("map | zeigt die gesamte map an");
        System.out.println("go {room} | geht in den raum");
        System.out.println("");
        System.out.println("items | show all Posions that you can sellect");
        System.out.println("");
        System.out.println("pokemon commands");
        System.out.println("----------------------------");
        System.out.println("pinfp  {id}| show stats from a pokemon");
        System.out.println("");
        System.out.println("inventory commands");
        System.out.println("----------------------------");
        System.out.println("bag | zeigt deinen Rucksack an");
        System.out.println("removeitem {id} | löscht ein item von deinem inventory");
        System.out.println("");
        System.out.println(parser.showCommands());
    }

    private void goRoom(Command command) {
        if (!command.hasSecondWord()) {
            System.out.println("Go where?");
        } else {

            String direction = command.getSecondWord();

            Room nextRoom = currentRoom.nextRoom(direction);
            if (nextRoom == null)
                System.out.println("There is no door!");
            else {
                currentRoom = nextRoom;
                System.out.println(currentRoom.longDescription());
            }
        }
    }
    public void printStarterPokemon() {
        System.out.println("Professor Eich: Ah, guten Tag! Du musst, nicht wahr? Ich habe gehört, du möchtest ein Pokémon haben, um deine Reise zu beginnen.");
        System.out.println(" ");
        System.out.println("Spieler: Ja, das stimmt, Professor Eich. Ich bin bereit, mein erstes Pokémon zu wählen!");
        System.out.println(" ");
        System.out.println("Professor Eich: Sehr gut. Hier sind 4 Pokémon zur Auswahl.");
        System.out.println("");
        System.out.println("select your starter Pokemon");
        System.out.println("pinfo | start von einem pokemon");
        System.out.println("select + id | select the starter Pokemon");
        for (PokemonModel p  : pokemonslist) {
            System.out.println("[" +p.getId() + "] " + p.getName());
        }

    }
    private void selectitem(Command command) {
        for (ItemModel p: currentRoom.items()) {
            if(p.getId() == Integer.parseInt(command.getSecondWord())) {
                addItemToInventory(p);
            }
        }
        System.out.println(" ");
    }

    private void removeItems(Command command) {
        try {
            int num =  Integer.parseInt(command.getSecondWord());
            for (ItemModel item: inventory) {
                if(item.getId() == num) {
                    System.out.println("Du hast " + item.getName() + " aus dienem Inventar entfehrnt.");
                    inventory.remove(item);
                }
            }
        } catch (Exception ex)
        {

        }



    }

    private void showmap() {
        System.out.println("------ the Map ------");
        System.out.println("            .:::::                                                          .:::::                                 *_________*");
        System.out.println("                                                       .:::::                                                    |-|--=====-----|");
        System.out.println("---------------            ----------------             ----------------             ---------------           |-                -|");
        System.out.println("|             |------------|              |------------|               |------------|              |-----------|                   -|");
        System.out.println("|    Start                   Schatzkammer                  Waffenraum                   Gefängnis                     " + ANSI_RED +"Endboss"+ANSI_RESET+"        |");
        System.out.println("|             |------------|              |------------|               |------------|              |-----------|                   -|");
        System.out.println("---------------            ----------------            ----------------             ---------------            |-                -|");
        System.out.println("                         .:::::                                                                                  |-|--=====--|---|");
        System.out.println("                                                            .:::::                                                 *_________*");
    }

    public void selectStarter(Command command) {
        String direction = command.getSecondWord();
        for (PokemonModel p: pokemonslist) {
            String s = Integer.toString(p.getId());
            if(direction.equals(s)) {
                starterPokemon = new PokemonModel(p.getId(), p.getName(),p.getHp(),p.getAttack(),p.getDefense(),p.getSpeed(), p.getAttacks());
            }
        }
        System.out.println(" ");

        System.out.println("---------------------------");
        System.out.println(" ");
        System.out.println("your Starter is: " + starterPokemon.getName());
        System.out.println("Spieler: Das ist eine schwierige Entscheidung, Professor Eich. Ich denke, ich werde... "+starterPokemon.getName() +" wählen!");
        System.out.println("");
        System.out.println("Professor Eich: Hervorragende Wahl, spieler! "+ starterPokemon.getName() +" ist ein mutiges Pokémon. Hier ist dein erstes Pokéball und dein Pokédex.");
        System.out.println(" Vergiss nicht, dein Abenteuer zu genießen und vorsichtig zu sein. Möge deine Reise erfolgreich sein!");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {

        }
        printWelcome();
        System.out.println("your now in the " + start.shortDescription());
        System.out.println("your commands: ");
        System.out.println("--------------------");
        System.out.println("items  | Show all items in the room ");
        System.out.println("select {itemid} | add a item to the inventory");
        System.out.println("removeitem {itemid} | removed a item from a^the inventory");
        System.out.println("map | shows a map of your location");
        System.out.println("bag | shows your bag");
        System.out.println("go east");
        System.out.println("go west");
        System.out.println("--------------------");
        currentRoom = start;
    }

    public void infoStarter(Command command) {
        String direction = command.getSecondWord();
        for (PokemonModel p: pokemonslist) {
            String s = Integer.toString(p.getId());
            if(direction.equals(s)) {
                System.out.println("---------------------------");
                System.out.println(" ");
                System.out.println("Stats von " + p.getName());
                System.out.println("HP: " + p.getHp());
                System.out.println("Attack: " + p.getAttack());
                System.out.println("Defense: " + p.getDefense());
                System.out.println("Speed: " + p.getSpeed());
                System.out.println(" ");
                System.out.println("---------------------------");
             for (AttackModel attcks: p.getAttacks()) {
                 System.out.println("attack: " + attcks.getName());
             }
                System.out.println("---------------------------");
                System.out.println(" ");
            }
        }
    }

    private void bag() {
        System.out.println("your bag");
        System.out.println("----------------");
        System.out.println("items: " + inventory.size());
        System.out.println(" ");
        for (ItemModel items: inventory) {
            System.out.println(items.getName() + " " + items.getBuff());
        }

    }

    private void showitems() {
        Room current = currentRoom;

        System.out.println("items in this room: ");

        for (ItemModel item : current.items())
        {
            System.out.println( "[" +item.getId()+ "] " +item.getName() + "   Buff: " + item.getBuff());
        }

    }

    public void showlocation() {
            Room current = start;
        System.out.println(" ");
        System.out.println("Karte:");
        System.out.print("[labor]");


        while (current != null) {
            if (current == currentRoom) {
                System.out.print(" - [X]");
            } else {
                System.out.print(" - [" + current.shortDescription() + "]");
            }
            current = current.nextRoom("east");
        }

        System.out.println(" ");


    }

    public void endbossfight() {
        if (!endbossFightActive) {
            System.out.print("\033[H\033[2J");
            System.out.flush();
            System.out.println("    ..  ...  .:     \n" +
                    "   . .  ==- .:...   \n" +
                    "     ...-==:.::--::.\n" +
                    "....::--+=++-----:. \n" +
                    "  .:-----+==-....   \n" +
                    "   --  -+===+.      \n" +
                    "    :::++++**+      \n" +
                    "    :-==*-*-*=+.    \n" +
                    "   .=+*-+-#:*-+=.   \n" +
                    "    *#*=*-#=++#+-.  \n" +
                    "   -#%+:*###:=#+-:  \n" +
                    "   +*#-*####+=*+::: \n" +
                    "  .**==#####*-**::: \n" +
                    "  :-*-*###*##-+*::::\n" +
                    "  -:++*##**##+=*--: \n" +
                    "  -:-.:-==-: :-.-:  \n" +
                    "  :-:        .-.    \n" +
                    " .::          ::.   \n");
            try {
            System.out.println(ANSI_RED +endboss.getName() + ANSI_RESET + " ist erschienen");
            Thread.sleep(1500); // Pause für 2 Sekunden
            System.out.println( "Endlich sehe ich meinen Rivalen");
            Thread.sleep(1500); // Pause für 2 Sekunden
            System.out.println( "Mach dich bereit !!!");

            Thread.sleep(1500); // Pause für 2 Sekunden
            } catch (InterruptedException e) {
                // Behandlung von möglichen Unterbrechungen
            }
            System.out.println( "Der Kampf beginnt!");

            endbossFightActive = true;
        }

        while (endbossFightActive && starterPokemon.getHp() > 0 && endboss.getHp() > 0) {
            System.out.println(" ");
            System.out.println("Round: " + this.Round++);
            System.out.println("-------------------------");
            System.out.println("    " + starterPokemon.getName() + " HP: " + starterPokemon.getHp());
            System.out.println("    " + ANSI_RED + endboss.getName()+ ANSI_RESET + " HP: " + endboss.getHp());
            System.out.println("-------------------------");
            System.out.println(" ");

            System.out.println("Was möchtest du tun?");
            System.out.println("------------------------");
            System.out.println("1. Attack");
            System.out.println("2. item");
            System.out.println("------------------------");


            Command command = parser.getCommand();
            String commandWord = command.getCommandWord();


            if (command.getCommandWord() != null) {
                if (commandWord.equals("attack")) {
                    System.out.println("your attack: ");
                    System.out.println("-------------------");
                    for (AttackModel attack : starterPokemon.getAttacks()) {

                        System.out.println(attack.getName() + "   Count: " + attack.getAttackCount());

                    }
                    System.out.println("-------------------");
                    System.out.println("attack {attackName}");
                    Command commands = parser.getCommand();
                    String attackName = commands.getSecondWord();
                    boolean validAttack = false;

                    for (AttackModel attack : starterPokemon.getAttacks()) {
                        if (attack.getName().equalsIgnoreCase(attackName)) {
                            validAttack = true;
                            if(attack.getAttackCount() > 0) {
                                int damageDealt = attack.getAttackBuff() + starterPokemon.getAttack();
                                attack.setAttackCount(attack.getAttackCount() - 1);
                                System.out.println(starterPokemon.getName() + " setzt " + attack.getName() + " ein und fügt " + damageDealt + " Schaden zu!");
                                endboss.setHp(endboss.getHp() - damageDealt);
                                break;
                            } else {
                                System.out.println("Diese Attacke kann"+ ANSI_RED +" nicht"+ ANSI_RESET +" mehr eingesetzt werden");
                                this.effectPower = 1;
                            }

                        }
                    }

                    if (!validAttack) {
                        System.out.println(ANSI_RED + "Ungültige Attacke! Versuche es erneut." + ANSI_RESET);

                    }
                } else if (commandWord.equals("item")) {
                    System.out.println("your Items:");
                    System.out.println("--------------------");
                    int num = 1;
                    for (ItemModel item : inventory)
                    {
                        item.setId(num++);
                        System.out.println("[" + item.getId() + "] " + item.getName());
                    }
                    System.out.println("--------------------");
                    System.out.println("item + {itemname}");
                    Command commands = parser.getCommand();
                    for (ItemModel itemModel: inventory) {

                    }
                    useItemFromInventory(commands);
                }
            }

            if (endboss.getHp() <= 0) {

                try {
                    System.out.println("The ");
                    Thread.sleep(500);
                    System.out.println("   ▄████████ ███▄▄▄▄   ████████▄  ");
                    Thread.sleep(400);
                    System.out.println("  ███    ███ ███▀▀▀██▄ ███   ▀███ ");
                    Thread.sleep(400);
                    System.out.println("  ███    █▀  ███   ███ ███    ███ ");
                    Thread.sleep(400);
                    System.out.println(" ▄███▄▄▄     ███   ███ ███    ███ ");
                    Thread.sleep(400);
                    System.out.println("▀▀███▀▀▀     ███   ███ ███    ███ ");
                    Thread.sleep(400);
                    System.out.println("  ███    █▄  ███   ███ ███    ███ ");
                    Thread.sleep(400);
                    System.out.println("  ███    ███ ███   ███ ███   ▄███ ");
                    Thread.sleep(400);
                    System.out.println("  ██████████  ▀█   █▀  ████████▀  ");





                    Thread.sleep(500);
                    System.out.println();
                    System.out.println("   _______    ______    __   ___  _______  ___      ___     ______    _____  ___  ");
                    Thread.sleep(400);
                    System.out.println("  |   __ \"\\  /    \" \\  |/\"| /  \")/\"     \"||\"  \\    /\"  |   /    \" \\  (\\\"   \\|\"  \\  ");
                    Thread.sleep(400);
                    System.out.println("  (. |__) :)//\\ ____  \\ (: |/   /(: ______) \\   \\  //   |  // ____  \\ |.\\\\   \\    | ");
                    Thread.sleep(400);
                    System.out.println("  |:  ____//  /    ) :)|    __/  \\/    |   /\\\\  \\/.    | /  /    ) :)|: \\.   \\\\  | ");
                    Thread.sleep(400);
                    System.out.println("  (|  /   (: (____/ // (// _  \\  // ___)_ |: \\.        |(: (____/ // |.  \\    \\. | ");
                    Thread.sleep(400);
                    System.out.println(" /|__/ \\   \\        /  |: | \\  \\(:      \"||.  \\    /:  | \\        /  |    \\    \\ | ");
                    Thread.sleep(400);
                    System.out.println("(_______)   \\\"_____/   (__|  \\__)\\_______)|___|\\__/|___|  \\\"_____/    \\___|\\____\\) ");
                    Thread.sleep(400);
                    System.out.println("                                                                                   ");
                } catch (InterruptedException e) {

                }
                System.out.println("Du hast " +ANSI_RED+ endboss.getName() +ANSI_RESET+ " besiegt!");
                System.out.println("Made by Joel Furter And Kenta Weibel");
                System.out.println(" ");
                System.exit(1);
                endbossFightActive = false;
            } else if (this.effectPower != 0) {

                this.effectPower -= 1;
            }
            else{
                int enemyDamage = enbossAttack.getAttackBuff();
                starterPokemon.setHp(starterPokemon.getHp() - enemyDamage);
                System.out.println(endboss.getName() + " setzt " + enbossAttack.getName() + " ein und fügt " + enemyDamage + " Schaden zu!");
                System.out.println(starterPokemon.getName() + " hat nur noch " + starterPokemon.getHp() + " HP");

                if (starterPokemon.getHp() <= 0) {
                    System.out.println(starterPokemon.getName() + " wurde besiegt. Das Spiel ist vorbei.");
                    System.out.println(" __              ___     __        ___  __  \n" +
                            "/ _`  /\\   |\\/| |__     /  \\ \\  / |__  |__) \n" +
                            "\\__> /~~\\  |  | |___    \\__/  \\/  |___ |  \\ \n" +
                            "                                            ");
                    System.exit(1);
                    endbossFightActive = false;
                }
            }
        }
    }


}
