package json;

import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.YaGsonBuilder;
import models.AIPlayer;
import models.Deck;
import models.Item.Collectable;
import models.Item.Usable;
import models.Player;
import models.card.Card;
import models.card.Hero;
import models.card.Minion;
import models.card.Spell;
import view.View;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class CardMaker {
    public static void main(String[] args) throws Exception {
        HeroMaker.main();
        MinionMaker.main();
        SpellMaker.main();
        UsableMaker.main();
        CollectableMaker.main();
        AIPlayerMaker.main();
    }

    static Hero heroReader(String path) {
        try {
            String json = new String(Files.readAllBytes(Paths.get(path)), StandardCharsets.UTF_8);
            YaGson yaGson = new YaGson();
            return yaGson.fromJson(json, Hero.class);
        } catch (IOException e) {
            View.printThrowable(e);
        }
        //  shouldn't reach here
        return null;
    }

    static Spell spellReader(String path) {
        try {
            String json = new String(Files.readAllBytes(Paths.get(path)), StandardCharsets.UTF_8);
            YaGson gson = new YaGson();
            return gson.fromJson(json, Spell.class);
        } catch (IOException e) {
            View.printThrowable(e);
        }
        //  shouldn't reach here
        return null;
    }

    static Minion minionReader(String path) {
        try {
            String json = new String(Files.readAllBytes(Paths.get(path)), StandardCharsets.UTF_8);
            YaGson gson = new YaGson();
            return gson.fromJson(json, Minion.class);
        } catch (IOException e) {
            View.printThrowable(e);
        }
        //  shouldn't reach here
        return null;
    }

    static Usable usableReader(String path) {
        try {
            String json = new String(Files.readAllBytes(Paths.get(path)), StandardCharsets.UTF_8);
            YaGson gson = new YaGson();
            return gson.fromJson(json, Usable.class);
        } catch (IOException e) {
            View.printThrowable(e);
        }
        //  shouldn't reach here
        return null;
    }

    static Collectable collectableReader(String path) throws IOException {
        String json = new String(Files.readAllBytes(Paths.get(path)), StandardCharsets.UTF_8);
        YaGson gson = new YaGson();
        return gson.fromJson(json, Collectable.class);
    }

    static Player playerReader(String path) throws IOException {
        String json = new String(Files.readAllBytes(Paths.get(path)), StandardCharsets.UTF_8);
        YaGson gson = new YaGson();
        return gson.fromJson(json, Player.class);
    }

    static AIPlayer aiPlayerReader(String path) throws IOException {
        String json = new String(Files.readAllBytes(Paths.get(path)), StandardCharsets.UTF_8);
        YaGson gson = new YaGson();
        return gson.fromJson(json, AIPlayer.class);
    }

    public static Deck deckReader(String filename) throws IOException {
        String path = "src//json//decks//" + filename;
        String json = new String(Files.readAllBytes(Paths.get(path)), StandardCharsets.UTF_8);
        YaGson gson = new YaGson();
        return gson.fromJson(json, Deck.class);
    }

    public static String saveToFile(Object object) {
        String folder = "";
        if (object.getClass().equals(Spell.class))
            folder = "spells";
        else if (object.getClass().equals(Hero.class))
            folder = "heroes";
        else if (object.getClass().equals(Minion.class))
            folder = "minions";
        else if (object.getClass().equals(Usable.class))
            folder = "usables";
        else if (object.getClass().equals(Collectable.class))
            folder = "collectables";
        else if (object.getClass().equals(Player.class))
            folder = "accounts";
        else if (object.getClass().equals(AIPlayer.class))
            folder = "aiplayers";
        else if (object.getClass().equals(Deck.class))
            folder = "decks";
        else
            System.out.println("Card class not found!");
        String name = "";
        if (object instanceof Card)
            name = ((Card) object).getName();
        else if (object instanceof Player)
            name = ((Player) object).getUsername();
        else if (object instanceof Deck)
            name = ((Deck) object).getName();
        String path = "src//json//" + folder + "//"
                + name.toLowerCase().replaceAll("\\s+", "_") + ".json";

        try (FileOutputStream fos = new FileOutputStream(path);
             OutputStreamWriter isr = new OutputStreamWriter(fos,
                     StandardCharsets.UTF_8)) {

            YaGsonBuilder yaGsonBuilder = new YaGsonBuilder();
            yaGsonBuilder.serializeNulls();

            YaGson yaGson = yaGsonBuilder.setPrettyPrinting().serializeSpecialFloatingPointValues().create();

            yaGson.toJson(object, isr);
        } catch (IOException e) {
            View.printThrowable(e);
        }
        System.out.println("Items written to file");
        return path;
    }

    public static <T> T deepCopy(T object, Class<T> type) {
        try {
            YaGson gson = new YaGson();
            return gson.fromJson(gson.toJson(object, type), type);
        } catch (Exception e) {
            View.printThrowable(e);
            return null;
        }
    }
}
