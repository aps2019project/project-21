package json;

import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.YaGsonBuilder;
import models.Item.Collectable;
import models.Item.Usable;
import models.card.*;
import models.card.buffs.*;
import models.card.effects.*;
import models.card.TargetType;
import models.card.target_enums.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CardMaker {
    public static void main(String[] args) throws IOException {
        usableMaker();
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
        //effects.add(new PowerHP(Integer.MAX_VALUE, 8, PowerMode.AP, EffectApplyInfo.ON_ALLY));
        //effects.add(new Disarm(Integer.MAX_VALUE, EffectApplyInfo.ON_OPP));
        //effects.add(new Effect(EffectApplyInfo.ON_BOTH, EffectType.POSITIVE_DISPEL));
        //effects.add(new DecreaseHP(8,EffectApplyInfo.ON_OPP_HERO));
        //effects.add(new Flame(2));
        //effects.add(new Poison(4, EffectApplyInfo.ON_OPP));
        //effects.add(new WeaknessHP(Integer.MAX_VALUE, Integer.MAX_VALUE, WeaknessMode.AP, EffectApplyInfo.ON_OPP));
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

    static Hero heroReader(String path) throws IOException {
        String json = new String(Files.readAllBytes(Paths.get(path)), StandardCharsets.UTF_8);
        YaGson yaGson = new YaGson();
        return yaGson.fromJson(json, Hero.class);
    }

    static Spell spellReader(String path) throws IOException {
        String json = new String(Files.readAllBytes(Paths.get(path)), StandardCharsets.UTF_8);
        YaGson gson = new YaGson();
        return gson.fromJson(json, Spell.class);
    }

    static Minion minionReader(String path) throws IOException {
        String json = new String(Files.readAllBytes(Paths.get(path)), StandardCharsets.UTF_8);
        YaGson gson = new YaGson();
        return gson.fromJson(json, Minion.class);
    }

    static void saveToFile(Card card) throws IOException {
        String folder = "";
        if (card.getClass().equals(Spell.class))
            folder = "spells";
        else if (card.getClass().equals(Hero.class))
            folder = "heroes";
        else if (card.getClass().equals(Minion.class))
            folder = "minions";
        else if (card.getClass().equals(Usable.class))
            folder = "usables";
        else if (card.getClass().equals(Collectable.class))
            folder = "collectables";
        else
            System.out.println("Card class not found!");

        String path = "src//json//" + folder + "//"
                + card.getName().toLowerCase().replaceAll("\\s+", "_") + ".json";

        try (FileOutputStream fos = new FileOutputStream(path);
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
