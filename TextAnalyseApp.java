import java.net.URL;
import java.util.Scanner;

public class TextAnalyseApp {

    public static void main(String[] args) {
        System.out.println("=== Text-Analyse-Tool gestartet ===");
        
        try {
            String zielUrl = "https://www.gutenberg.org/cache/epub/1497/pg1497.txt";
            System.out.println("Lade Daten von der URL...");
            
            String rohtext = ladeTextAusNetz(zielUrl);
            
            TextAnalysator analysator = new TextAnalysator(rohtext);
            int zeilen = analysator.ermittleZeilenAnzahl();
            int woerter = analysator.ermittleWoerterAnzahl();
            String laengstesWort = analysator.findeLaengstesWort();
            
            gebeAuswertungAus(zeilen, woerter, laengstesWort);
            
        } catch (Exception e) {
            System.err.println("Fehler beim Verarbeiten der Daten: " + e.getMessage());
        }
    }

    public static String ladeTextAusNetz(String urlString) throws Exception {
        URL url = new URL(urlString);
        Scanner scanner = new Scanner(url.openStream(), "UTF-8");
        StringBuilder inhalt = new StringBuilder();
        
        while (scanner.hasNextLine()) {
            inhalt.append(scanner.nextLine()).append("\n");
        }
        scanner.close();
        return inhalt.toString();
    }

    public static void gebeAuswertungAus(int zeilen, int woerter, String laengstesWort) {
        System.out.println("\n----------------------------------------");
        System.out.println("            ANALYSE-ERGEBNISSE            ");
        System.out.println("----------------------------------------");
        System.out.println("Zeilenanzahl : " + zeilen);
        System.out.println("Wörteranzahl : " + woerter);
        System.out.println("Längstes Wort: " + laengstesWort);
        System.out.println("----------------------------------------");
    }

    public static class TextAnalysator {
        private String text;

        public TextAnalysator(String text) {
            this.text = text != null ? text : "";
        }

        public int ermittleZeilenAnzahl() {
            if (text.isEmpty()) {
                return 0;
            }
            return text.split("\n").length;
        }

        public int ermittleWoerterAnzahl() {
            if (text.isEmpty()) {
                return 0;
            }
            String[] teile = text.split("\\s+");
            return teile.length;
        }

        public String findeLaengstesWort() {
            if (text.isEmpty()) {
                return "";
            }
            String[] teile = text.split("\\s+");
            String topWort = "";
            
            for (String wort : teile) {
                String saubererEintrag = wort.replaceAll("[^a-zA-ZÄÖÜäöüß]", "");
                if (saubererEintrag.length() > topWort.length()) {
                    topWort = saubererEintrag;
                }
            }
            return topWort;
        }
    }
}