package json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import models.card.*;
import models.card.buffs.*;
import models.card.effects.DecreaseHP;
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
        Spell spell = spellReader();
        System.out.println(spell.getEffects().get(0).getApplyType());
    }

    public static void heroMaker() throws IOException {
        Effect effect = new Power(Integer.MAX_VALUE, 4, PowerMode.AP);
        Spell specialPower = new Spell("Dive Sefid's Spell", 0, 1,
                TargetType.HIMSELF, effect, "casts power buff 4 on himself forever");
        Hero hero = new Hero("Dive Sefid", 8000, 50, 4, -1, AttackMode.MELEE, specialPower, 2);
        saveToFile(hero);
    }

    public static void spellMaker() throws IOException {
        List<Effect> effects = new ArrayList<>();
        effects.add(new Power(3, 4, PowerMode.AP));
        effects.add(new Disarm(3));
        Spell spell = new Spell("Madness", 650, 0, TargetType.SINGLE_ALLY, effects,
                "Increase ap of an ally 4 units for 3 turns but disarmed as well.");
        saveToFile(spell);
    }

    public static Spell spellReader() throws IOException {
        String json = new String(Files.readAllBytes(Paths.get("src//json//spells//madness.json")), StandardCharsets.UTF_8);
        Gson gson = new Gson();
        Spell spell = gson.fromJson(json, Spell.class);

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
        return spell;
    }

    public static void saveToFile(Spell spell) throws IOException {
        String fileName = "src//json//spells/madness.json";

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

    public static void saveToFile(Hero hero) throws IOException {
        String fileName = "src//json//test.json";

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
}
