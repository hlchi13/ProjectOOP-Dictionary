package Game.MultipleChoice;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Questions {
    private List<String> questions = new ArrayList<>();
    private List<List<String>> choices = new ArrayList<>();
    private List<String> correctAns = new ArrayList<>();

    public void setQuestions(List<String> questions) {
        this.questions = questions;
    }

    public List<List<String>> getChoices() {
        return choices;
    }

    public List<String> getQuestions() {
        return questions;
    }

    public List<String> getCorrectAns() {
        return correctAns;
    }

    public void getFormFile() {
        try {
            FileReader fileReader = new FileReader("src/main/resources/game/questions.txt");
            BufferedReader br = new BufferedReader(fileReader);
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("|")) {
                    questions.add(line.replace("|", ""));
                }
                else if (line.startsWith("#")) {
                    correctAns.add(line.replace("#", ""));
                } else {
                    String[] all = line.split("#");
                    List<String> ansPerQues = Arrays.stream(all).toList();
                    choices.add(ansPerQues);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Questions q = new Questions();
        q.getFormFile();
        List<String> que = q.getQuestions();
        List<String> correctAns1 = q.getCorrectAns();
        List<List<String>> ans = q.getChoices();
        Scanner sc = new Scanner(System.in);
        System.out.println("Nhap cau hoi");
        int idx = sc.nextInt();
        System.out.println(que.get(idx));
        for (String s : ans.get(idx)) {
            System.out.println(s);
        }
        System.out.println(correctAns1.get(idx));
        int correctIdx = sc.nextInt();
        System.out.println("Correct " + ans.get(idx).get(correctIdx));
        if (ans.get(idx).get(correctIdx).compareTo(correctAns1.get(idx)) == 0) {
            System.out.println("Yes");
        } else {
            System.out.println("NO");
        }
    }
}
