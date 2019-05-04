package json;

import models.Item.Collectable;
import models.Item.Usable;
import models.card.Hero;
import models.card.Minion;
import models.card.Spell;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Initializer {
    public static void main() {
        try {
            Spell.addSpell(initSpells());
            Hero.addHero(initHeroes());
            Minion.addMinion(initMinions());
            Collectable.addCollectable(initCollectables());
            Usable.addUsable(initUsables());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private static List<Spell> initSpells() throws IOException {
        List<Spell> spells = new ArrayList<>();
        File path = new File("src//json//spells");
        File[] files = path.listFiles();
        if (files == null)
            return spells;
        for (File file : files)
            if (file.isFile())
                spells.add(CardMaker.spellReader(file.getPath()));
        return spells;
    }

    private static List<Hero> initHeroes() throws IOException {
        List<Hero> heroes = new ArrayList<>();
        File path = new File("src//json//heroes");
        File[] files = path.listFiles();
        if (files == null)
            return heroes;
        for (File file : files)
            if (file.isFile())
                heroes.add(CardMaker.heroReader(file.getPath()));
        return heroes;
    }

    private static List<Minion> initMinions() throws IOException {
        List<Minion> minions = new ArrayList<>();
        File path = new File("src//json//minions");
        File[] files = path.listFiles();
        if (files == null)
            return minions;
        for (File file : files)
            if (file.isFile())
                minions.add(CardMaker.minionReader(file.getPath()));
        return minions;
    }

    private static List<Usable> initUsables() throws IOException {
        List<Usable> usables = new ArrayList<>();
        File path = new File("src//json//usables");
        File[] files = path.listFiles();
        if (files == null)
            return usables;
        for (File file : files)
            if (file.isFile())
                usables.add(CardMaker.usableReader(file.getPath()));
        return usables;
    }

    private static List<Collectable> initCollectables() throws IOException {
        List<Collectable> collectables = new ArrayList<>();
        File path = new File("src//json//collectables");
        File[] files = path.listFiles();
        if (files == null)
            return collectables;
        for (File file : files)
            if (file.isFile())
                collectables.add(CardMaker.collectableReader(file.getPath()));
        return collectables;
    }
}
