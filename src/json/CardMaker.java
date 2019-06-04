package json;

import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.YaGsonBuilder;
import models.AIPlayer;
import models.Item.Collectable;
import models.Item.Usable;
import models.Player;
import models.card.*;
import view.View;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CardMaker {
    public static void main(String[] args) {
        HeroMaker.main();
        MinionMaker.main();
        SpellMaker.main();
        UsableMaker.main();
        CollectableMaker.main();
//        AIPlayerMaker.main();
    }

    private static void usableMaker() throws IOException {
//        Effect effect = new GiveEffect(12, EffectName.HOLY);
//        Usable usable = new Usable("Kamane Demool", 30000, TargetType.NOT_MELEE_HERO, effect,
//                "disarms the enemy for one turn on attack.(only for ranged and hybrid hero.)");
//        saveToFile(usable);
    }

    private static void collectableMaker() throws IOException {

    }

    private static void heroMaker() throws IOException {
//        Effect effect = new PowerHP(Integer.MAX_VALUE, 4, PowerMode.AP);
//        Spell specialPower = new Spell("Dive Sefid's Spell", 0, 1,
//                TargetType.HIMSELF, effect, "casts power buff 4 on himself forever");
//        Hero hero = new Hero("Dive Sefid", 8000, 50, 4, -1, AttackMode.MELEE, specialPower, 2);
//        saveToFile(hero);
    }

    private static void spellMaker() throws IOException {
        List<Effect> effects = new ArrayList<>();
        //effects.add(new PowerHP(Integer.MAX_VALUE, 8, PowerMode.AP, ApplyType.ON_ALLY));
        //effects.add(new Disarm(Integer.MAX_VALUE, ApplyType.ON_OPP));
        //effects.add(new Effect(ApplyType.ON_BOTH, EffectType.POSITIVE_DISPEL));
        //effects.add(new DecreaseHP(8,ApplyType.ON_OPP_HERO));
        //effects.add(new Flame(2));
        //effects.add(new Poison(4, ApplyType.ON_OPP));
        //effects.add(new WeaknessHP(Integer.MAX_VALUE, Integer.MAX_VALUE, WeaknessMode.AP, ApplyType.ON_OPP));
//        effects.add(new Stun(2));
//        TargetType targetType = new TargetType(RandomOrNot.NOT_RANDOM, TargetAttackerRange.ALL_THREE,
//                CellType.SINGLE_CELL, HeroOrMinion.BOTH, OppOrAlly.OPP);
//        Spell spell = new Spell("Shock", 1200, 1, targetType, effects,
//                "stun an opp");
//        saveToFile(spell);
    }

    private static void minionMaker() throws IOException {
//        Effect effect = new Stun(1);
//        Spell specialPower = new Spell("shamshirzane fars spell", 0, 0,
//                TargetType.SINGLE_OPP, effect, "Stuns the attacked opp for one turn.");
//        Minion minion = new Minion("Shamshirzane Fars", 400, 2, 6, 4,
//                0, AttackMode.MELEE, specialPower);
//        saveToFile(minion);
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

    public static void saveToFile(Object object) {
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
        else
            System.out.println("Card class not found!");
        String name = "";
        if (object instanceof Card)
            name = ((Card) object).getName();
        else if (object instanceof Player)
            name = ((Player) object).getUsername();
        String path = "src//json//" + folder + "//"
                + name.toLowerCase().replaceAll("\\s+", "_") + ".json";

        try (FileOutputStream fos = new FileOutputStream(path);
             OutputStreamWriter isr = new OutputStreamWriter(fos,
                     StandardCharsets.UTF_8)) {

            YaGsonBuilder yaGsonBuilder = new YaGsonBuilder();
            yaGsonBuilder.serializeNulls();

            YaGson yaGson = yaGsonBuilder.setPrettyPrinting().create();

            yaGson.toJson(object, isr);
        } catch (IOException e) {
            View.printThrowable(e);
        }

        System.out.println("Items written to file");
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
