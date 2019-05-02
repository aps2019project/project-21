package json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import models.card.*;
import models.card.buffs.*;
import models.card.effects.DecreaseHP;
import models.card.effects.EffectType;
import models.card.effects.IncreaseAP;
import models.card.effects.PositiveDispel;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CardMaker {
    public static void main(String[] args) throws IOException {
        spellMaker();
    }

    public static void heroMaker() throws IOException {
        Effect effect = new Power(Integer.MAX_VALUE, 4, PowerMode.AP);
        Spell specialPower = new Spell("Dive Sefid's Spell", 0, 1,
                TargetType.HIMSELF, effect, "casts power buff 4 on himself forever");
        Hero hero = new Hero("Dive Sefid", 8000, 50, 4, -1, AttackMode.MELEE, specialPower, 2);
        saveToFile(hero, "src//json//heroes//divesefid.json");
    }

    public static void spellMaker() throws IOException {
        List<Effect> effects = new ArrayList<>();
        //effects.add(new Power(Integer.MAX_VALUE, 4, PowerMode.AP));
        //effects.add(new Disarm(Integer.MAX_VALUE, ApplyType.ON_OPP));
        //effects.add(new Effect(ApplyType.ON_BOTH, EffectType.POSITIVE_DISPEL));
        effects.add(new DecreaseHP(8,ApplyType.ON_OPP_HERO));
        //effects.add(new Flame(2));
        Spell spell = new Spell("Lighting Bolt", 1250, 2, TargetType.OPP_HERO, effects,
                "decrease 8 hp, opp hero");
        saveToFile(spell, "src//json//spells//lightingBolt.json");
    }

    public static void minionMaker() throws IOException {
        Effect effect = new Stun(1);
        Spell specialPower = new Spell("shamshirzane fars spell", 0, 0,
                TargetType.SINGLE_OPP, effect, "Stuns the attacked opp for one turn.");
        Minion minion = new Minion("Shamshirzane Fars", 400, 2, 6, 4,
                0, AttackMode.MELEE, specialPower, ActivationType.ON_ATTACK);
        saveToFile(minion, "src//json//minions//shmshirzanefarsi.json");
    }

    private static Hero heroReader() throws IOException {
        String json = new String(Files.readAllBytes(Paths.get("src//json//heroes//divesefid.json")), StandardCharsets.UTF_8);
        Gson gson = new Gson();
        Hero hero = gson.fromJson(json, Hero.class);
        Spell spell = hero.getSpecialPower();
        initSpell(spell);
        return hero;
    }

    public static Spell spellReader() throws IOException {
        String json = new String(Files.readAllBytes(Paths.get("src//json//spells//madness.json")), StandardCharsets.UTF_8);
        Gson gson = new Gson();
        Spell spell = gson.fromJson(json, Spell.class);
        initSpell(spell);
        return spell;
    }

    public static Minion minionReader() throws IOException {
        String json = new String(Files.readAllBytes(Paths.get("src//json//minions//shamshirzanefarsi.json")), StandardCharsets.UTF_8);
        Gson gson = new Gson();
        Minion minion = gson.fromJson(json, Minion.class);
        initSpell(minion.getSpecialPower());
        return minion;
    }

    private static void initSpell(Spell spell) {
        for (int i = 0; i < spell.getEffects().size(); i++) {
            Effect effect = spell.getEffects().get(i);
            List<String> args = effect.getEffectArguments();
            Effect newEffect = new Effect();
            switch (effect.getEffectType()) {
                case DISARM:
                    newEffect = new Disarm(Integer.parseInt(args.get(0)));
                    break;
                case FLAME:
                    newEffect = new Flame(Integer.parseInt(args.get(0)));
                    break;
                case HOLY:
                    newEffect = new Holy(Integer.parseInt(args.get(0)));
                    break;
                case POISON:
                    newEffect = new Poison(Integer.parseInt(args.get(0)));
                    break;
                case POWER:
                    newEffect = new Power(Integer.parseInt(args.get(0)),
                            Integer.parseInt(args.get(1)), PowerMode.valueOf(args.get(2)));
                    break;
                case STUN:
                    newEffect = new Stun(Integer.parseInt(args.get(0)));
                    break;
                case WEAKNESS:
                    newEffect = new Weakness(Integer.parseInt(args.get(0)),
                            Integer.parseInt(args.get(1)), WeaknessMode.valueOf(args.get(2)));
                    break;
                case DECREASE_HP:
                    newEffect = new DecreaseHP(Integer.parseInt(args.get(0)));
                    break;
                case INCREASE_AP:
                    newEffect = new IncreaseAP((Integer.parseInt(args.get(0))));
                    break;
                case POSITIVE_DISPEL:
                    newEffect = new PositiveDispel(ApplyType.valueOf(args.get(0)));
                    break;
            }
            spell.getEffects().set(i, newEffect);
        }
    }

    private static void saveToFile(Spell spell, String fileName) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(fileName);
             OutputStreamWriter isr = new OutputStreamWriter(fos,
                     StandardCharsets.UTF_8)) {

            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.serializeNulls();

            Gson gson = gsonBuilder.setPrettyPrinting().create();

            gson.toJson(spell, isr);
        }

        System.out.println("Items written to file");
    }

    private static void saveToFile(Hero hero, String fileName) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(fileName);
             OutputStreamWriter isr = new OutputStreamWriter(fos,
                     StandardCharsets.UTF_8)) {

            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.serializeNulls();

            Gson gson = gsonBuilder.setPrettyPrinting().create();

            gson.toJson(hero, isr);
        }

        System.out.println("Items written to file");
    }

    private static void saveToFile(Minion minion, String fileName) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(fileName);
             OutputStreamWriter isr = new OutputStreamWriter(fos,
                     StandardCharsets.UTF_8)) {

            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.serializeNulls();

            Gson gson = gsonBuilder.setPrettyPrinting().create();

            gson.toJson(minion, isr);
        }

        System.out.println("Items written to file");
    }
}
