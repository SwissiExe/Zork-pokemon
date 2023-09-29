package com.bbw.pokemon;

import com.bbw.pokemon.model.ItemModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Room {
    public static final String EAST = "east";
    public static final String WEST = "west";

    private String description;

    private ItemModel[] items;
    private HashMap<String, Room> exits;

    public Room(String description, ItemModel[] itemModels) {
        this.description = description;
        this.exits = new HashMap<>();
        this.items = itemModels;
    }

    public void setExits(Room east, Room west) {
        exits.put(EAST, east);
        exits.put(WEST, west);
    }

    public List<String> nextRoomDirections() {
        return new ArrayList<>(exits.keySet());
    }

    public String shortDescription() {
        return description;
    }

    public String longDescription() {
        StringBuilder stringBuilder = new StringBuilder("You are in " + description + ".\n");
        stringBuilder.append(exitString());
        return stringBuilder.toString();
    }

    public ItemModel[] items() {
        return this.items;
    }

    private String exitString() {
        return "Exits: " + String.join(", ", exits.keySet());
    }

    public Room nextRoom(String direction) {
        if (exits.containsKey(direction)) {
            return exits.get(direction);
        } else {
            System.out.println("There is no exit in that direction.");
            return this; // Bleibe im aktuellen Raum
        }
    }
}
