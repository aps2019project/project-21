package json;

import models.AIPlayer;
import models.Collection;
import models.Item.Collectable;
import models.Item.Usable;
import models.Player;
import models.card.Card;
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
            Player.addPlayer(initPlayers());
            AIPlayer.addAIPlayer(initAIPlayers());

            resetCollectionIDs();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void resetCollectionIDs() {
        for (Player player : Player.getPlayers()) {
            Collection collection = player.getCollection();
            for (Card card : collection.getCards())
                card.setCollectionID();
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

    private static List<Player> initPlayers() throws IOException {
        List<Player> players = new ArrayList<>();
        File path = new File("src//json//accounts");
        File[] files = path.listFiles();
        if (files == null)
            return players;
        for (File file : files)
            if (file.isFile())
                players.add(CardMaker.playerReader(file.getPath()));
        return players;
    }

    private static List<AIPlayer> initAIPlayers() throws IOException {
        List<AIPlayer> aiPlayers = new ArrayList<>();
        File path = new File("src//json//aiplayers");
        File[] files = path.listFiles();
        if (files == null)
            return aiPlayers;
        for (File file : files)
            if (file.isFile())
                aiPlayers.add(CardMaker.aiPlayerReader(file.getPath()));
        return aiPlayers;
    }
}
