package json;

import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.YaGsonBuilder;
import models.card.*;
import models.card.buffs.*;
import models.card.effects.DecreaseHP;
import models.card.effects.IncreaseAP;
import models.card.effects.PositiveDispel;
import models.match.Cell;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CardMaker {
    public static void main(String[] args) throws IOException {
        heroMaker();
    }

    public static void heroMaker() throws IOException {
        Effect effect = new Power(Integer.MAX_VALUE, 4, PowerMode.AP);
        Spell specialPower = new Spell("Dive Sefid's Spell", 0, 1,
                TargetType.HIMSELF, effect, "casts power buff 4 on himself forever");
        Hero hero = new Hero("Dive Sefid", 8000, 50, 4, -1, AttackMode.MELEE, specialPower, 2);
        Cell cell = new Cell(hero);
        hero.setCurrentCell(cell);
        saveToFile(hero);
    }

    public static void spellMaker() throws IOException {
        List<Effect> effects = new ArrayList<>();
        //effects.add(new Power(Integer.MAX_VALUE, 8, PowerMode.AP, ApplyType.ON_ALLY));
        //effects.add(new Disarm(Integer.MAX_VALUE, ApplyType.ON_OPP));
        //effects.add(new Effect(ApplyType.ON_BOTH, EffectType.POSITIVE_DISPEL));
        //effects.add(new DecreaseHP(8,ApplyType.ON_OPP_HERO));
        //effects.add(new Flame(2));
        //effects.add(new Poison(4, ApplyType.ON_OPP));
        //effects.add(new Weakness(Integer.MAX_VALUE, Integer.MAX_VALUE, WeaknessMode.AP, ApplyType.ON_OPP));
        effects.add(new Stun(2));
        Spell spell = new Spell("Shock", 1200, 1, TargetType.SINGLE_OPP, effects,
                "stun an opp");
        saveToFile(spell);
    }

    public static void minionMaker() throws IOException {
        Effect effect = new Stun(1);
        Spell specialPower = new Spell("shamshirzane fars spell", 0, 0,
                TargetType.SINGLE_OPP, effect, "Stuns the attacked opp for one turn.");
        Minion minion = new Minion("Shamshirzane Fars", 400, 2, 6, 4,
                0, AttackMode.MELEE, specialPower, ActivationType.ON_ATTACK);
        saveToFile(minion);
    }

    private static Hero heroReader(String path) throws IOException {
        String json = new String(Files.readAllBytes(Paths.get(path)), StandardCharsets.UTF_8);
        YaGson yaGson = new YaGson();
        return yaGson.fromJson(json, Hero.class);
    }

    public static Spell spellReader(String path) throws IOException {
        String json = new String(Files.readAllBytes(Paths.get(path)), StandardCharsets.UTF_8);
        YaGson gson = new YaGson();
        return gson.fromJson(json, Spell.class);
    }

    public static Minion minionReader(String path) throws IOException {
        String json = new String(Files.readAllBytes(Paths.get(path)), StandardCharsets.UTF_8);
        YaGson gson = new YaGson();
        return gson.fromJson(json, Minion.class);
    }

    private static void saveToFile(Card card) throws IOException {
        String folder;
        if (card.getClass().equals(Spell.class))
            folder = "spells";
        else if (card.getClass().equals(Hero.class))
            folder = "heroes";
        else if (card.getClass().equals(Minion.class))
            folder = "minions";
        else
            folder = "items";

        try (FileOutputStream fos = new FileOutputStream("src//json//" + folder
                + "//" + card.getName().toLowerCase().replace(" ", "") + ".json");
             OutputStreamWriter isr = new OutputStreamWriter(fos,
                     StandardCharsets.UTF_8)) {

            YaGsonBuilder yaGsonBuilder = new YaGsonBuilder();
            yaGsonBuilder.serializeNulls();

            YaGson yaGson = yaGsonBuilder.setPrettyPrinting().create();

            yaGson.toJson(card, isr);
        }

        System.out.println("Items written to file");
    }
}
