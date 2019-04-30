package json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import controller.InputScanner;
import models.card.Effect;
import models.card.Spell;
import models.card.TargetType;
import models.card.buffs.Weakness;
import models.card.buffs.WeaknessMode;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;


public class CardMaker {
    public static void main(String[] args) throws IOException {
        Effect effect = new Weakness(10, 3, WeaknessMode.HP);
        Spell spell = new Spell("Total Disarm", 1000, 0, TargetType.SINGLE_OPP, effect, "Disarm forever.");
        saveToFile(spell);
        spell = spellMaker();
    }

    public static Spell spellMaker() throws FileNotFoundException {
        File file = new File("src//json//test.json");
        String json = InputScanner.nextLine();
        Gson gson = new Gson();
        Spell spell = gson.fromJson(json, Spell.class);
        for (int i = 0; i < spell.getEffects().size(); i++) {
            Effect effect = spell.getEffects().get(i);
            List<String> args = effect.getEffectArguments();
            switch (effect.getEffectType()) {
                case DISARM:
                    break;
                case FLAME:
                    break;
                case HOLY:
                    break;
                case POISON:
                    break;
                case POWER:
                    break;
                case STUN:
                    break;
                case WEAKNESS:
                    Weakness weakness = new Weakness(Integer.parseInt(args.get(0)),
                            Integer.parseInt(args.get(1)), WeaknessMode.valueOf(args.get(2)));
                    spell.getEffects().set(i, weakness);
                    break;
                case DECREASE_HP:
                    break;
                case INCREASE_AP:
                    break;
                case POSITIVE_DISPEL:
                    break;
            }
        }
        return spell;
    }

    public static void saveToFile(Spell obj) throws IOException {
        String fileName = "src//json//test.json";

        try (FileOutputStream fos = new FileOutputStream(fileName);
             OutputStreamWriter isr = new OutputStreamWriter(fos,
                     StandardCharsets.UTF_8)) {

            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.serializeNulls();

//            Gson gson = gsonBuilder.setPrettyPrinting().create();
            Gson gson = gsonBuilder.create();

            gson.toJson(obj, isr);
        }

        System.out.println("Items written to file");
    }
}