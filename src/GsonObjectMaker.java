import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import models.card.Card;
import models.card.Hero;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GsonObjectMaker {
    public static void main(String[] args) throws IOException {

    }

    public Hero cardMaker(File file) throws FileNotFoundException {
        Scanner scanner = new Scanner(file);
        return null;
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

            List<Card> items = new ArrayList<>();
            items.add(card1);

            gson.toJson(items, isr);
        }

        System.out.println("Items written to file");
    }
}
