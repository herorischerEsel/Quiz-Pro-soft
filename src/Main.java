import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;         //
import java.util.concurrent.TimeUnit;                   //Element Zeit wird eingebaut

    public class Main {
        public static void main(String[] args) throws InterruptedException, ExecutionException {
            ArrayList<Question> questions = new ArrayList<>(9999);
            questions.addAll(List.of(
                    new Question(Set.of(4), List.of("Amsel", "Federspecht", "Meisel", "Seidenschwaenz"), "In der Wildnis gibt es ein Vogel der vergorende Beeren frisst und betrunken wird. Um welche Vogelart handelt es sich?", 10),              //Frage mit antwort als Liste wird erstellt. D
                    new Question(Set.of(3), List.of("Potsdam", "Oranienburg", "Berlin", "Spandau"), "Wie heist die Landeshauptstadt von Berlin?", 15),
                    new Question(Set.of(2), List.of("Michelangelo", "Leonardo da Vinci", "Paul Peter Rubens", "Banksy"), "Wer erschuft damals dass Gemälde Mona-Lisa ?", 20),
                    new Question(Set.of(4), List.of("Hans Christian Andersen", "Carlo Collodi", "Martin Johannes Bach", "Gebrüder Grim"), "Wer schriebt das Märchen Hänsel und Gretel?", 12),
                    new Question(Set.of(1), List.of("1923", "1911", "1931", "1947"), "Im welchem Jahr wurde das Disney Studio gegründet?", 18),
                    new Question(Set.of(3), List.of("1911", "1925", "1886", "1897"), "Im welchem Jahr entstand das erste Automobil?", 14),
                    new Question(Set.of(2), List.of("1945", "1949", "1955", "1947"), "Wann wurde die Bundesrepublik Deutschland gegründet?", 16),
                    new Question(Set.of(4), List.of("1981", "1985", "1988", "1989"), "Wann fiel Mauer zwischen der BRD und der DDR?", 12),
                    new Question(Set.of(1), List.of("1977", "1975", "1979", "1981"), "Wann kam der erste Star Wars Film raus?", 15),
                    new Question(Set.of(2, 4), List.of("Didrik Pining", "Leif Eriksson", "Sir Francis Drake", "Christopher Columbus"), "Wer endeckte Amerika als erstes", 20)));
            Collections.sort(questions);
            Quiz quiz = new Quiz(questions);
            for (Question currentQuestion : quiz.getQuestions()) {
                System.out.println(currentQuestion.getQuestionText());
                List<String> options = currentQuestion.getOptions();
                for (String option : options) {
                    System.out.println(option);
                }
                System.out.print("Bitte Antwort eingeben: ");                                                               // Eingabe erforderlich vom Nutzer
                String userInput = timedUserInput(currentQuestion.getQuestionTimer(), TimeUnit.SECONDS).get();              //
                if (currentQuestion.getCorrectOptions().contains(Integer.parseInt(userInput))) {                            // Wenn die Eingabe in der Frage/Liste uebereinstimmt
                    System.out.println("gut gemacht ");                                                                     // Dann wird das ausgegeben
                    quiz.setScore(quiz.getScore() + (int) (currentQuestion.getQuestionTimer() * (Math.E)));
                } else {                                                                                                    // Wenn die Eingabe in der Frage/Liste nicht uebereinstimmt
                    System.out.println("Du bist scheisse");                                                                 // Dann wird das ausgegeben
                }
                System.out.println("you score " + quiz.getScore());                                                         // Punkte werden addiert
            }
        }

        public static CompletableFuture<String> timedUserInput(int timeout, TimeUnit unit) {
            CompletableFuture<String> userInputFuture = new CompletableFuture<>();

            // Start a new thread for user input
            new Thread(() -> {
                Scanner scanner = new Scanner(System.in);
                String input = scanner.nextLine();
                userInputFuture.complete(input);
            }).start();

            // Schedule a timeout for the CompletableFuture
            CompletableFuture.delayedExecutor(timeout, unit)
                    .execute(() -> {
                        if (!userInputFuture.isDone()) {
                            System.out.println("Zeit ist um, Opfer");
                            userInputFuture.complete("");
                        }
                    });
            return userInputFuture;
        }
    }


