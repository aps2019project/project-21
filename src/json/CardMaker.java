package json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import models.card.Card;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class CardMaker {
    public static void main(String[] args) throws IOException {
        Card card = cardMaker();
        System.out.println(card.getName());
        System.out.println(card.getId());

    }

    public static Card cardMaker() throws FileNotFoundException {
        File file = new File("src//arian.json");
        Scanner scanner = new Scanner(file);
        String json = scanner.nextLine();
        System.out.println(json);
        Gson gson = new Gson();
        Card card = gson.fromJson(json, Card.class);
        return card;
    }

    public static void saveToFile() throws IOException {
        String fileName = "src/arian.json";

        try (FileOutputStream fos = new FileOutputStream(fileName);
             OutputStreamWriter isr = new OutputStreamWriter(fos,
                     StandardCharsets.UTF_8)) {

            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.serializeNulls();

            Gson gson = gsonBuilder.create();

            Card card1 = new Card("arian", 11, 12);

            gson.toJson(card1, isr);
        }

        System.out.println("Items written to file");
    }
}
