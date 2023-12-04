package Game.Memory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Memory {
    private final int boardRows = 3;
    private final int boardCols = 4;
    private final int boardSize = boardRows * boardCols;
    private final Random random = new Random();

    private final ArrayList<String> memoryBoard = new ArrayList<>(Arrays.asList("", "", "", "", "", "", "", "", "", "", "", ""));
    private final ArrayList<String> memoryOptions = new ArrayList<>();
    public void setupGame(){
        loadMemoryOptions();
        setupMemoryBoard();
        System.out.println(memoryBoard);
    }

    //src/main/resources/com/example/demo/Words.txt
    private void loadMemoryOptions() {
        // Đọc từ tệp Words.txt và thay thế các từ tiếng Anh bằng nghĩa tiếng Việt tương ứng
        ArrayList<String> allOptions = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("src/main/resources/Game/Words.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                allOptions.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Collections.shuffle(allOptions);

        for (int i = 0; i < 6; i++) {
            String englishWord1 = allOptions.get(i).split("\t")[0];
            String vietnameseMeaning1 = allOptions.get(i).split("\t")[1];
            memoryOptions.add(englishWord1);
            memoryOptions.add(vietnameseMeaning1);
        }
    }
    public String getOptionAtIndex(int index){
        return memoryBoard.get(index);
    }

    private void setupMemoryBoard(){
        Collections.shuffle(memoryOptions);

        for (int i = 0; i < boardSize; i++) {
            String memoryOption = memoryOptions.get(i);
            int row = i / boardCols;
            int col = i % boardCols;

            int position = row * boardCols + col;
            while (!Objects.equals(memoryBoard.get(position), "")) {
                position = random.nextInt(boardSize);
            }

            memoryBoard.set(position, memoryOption);
        }
    }

    public boolean checkTwoPositions(int firstIndex, int secondIndex){
        String firstPair = memoryBoard.get(firstIndex);
        String secondPair = memoryBoard.get(secondIndex);

        String fileName = "src/main/resources/Game/Words.txt";

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Kiểm tra xem có chứa cả hai từ và có cách nhau một dấu tab hay không
                if (line.contains(firstPair + "\t" + secondPair) || line.contains(secondPair + "\t" + firstPair)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}