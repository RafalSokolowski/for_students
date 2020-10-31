package day20201031.homework;

public class MainHomework {

    public static final int NUMBER_OF_FOLDERS = 3;
    public static final String FOLDERS_ARE_IN = "src\\main\\resources\\HomeworkFolders";

    public static void main(String[] args) {

        // 8. Napisz mechanizm który dla n = 1000; stworzy katalog w katalogu w katalogu katalog :) +1 pkt
        new MakeFolders(NUMBER_OF_FOLDERS, FOLDERS_ARE_IN).start();

        // 9. Napisz mechanizm który w sposób rekurencyjny to usunie  +1 pkt
        new DeleteFolders(FOLDERS_ARE_IN).start();

    }
}
