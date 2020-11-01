package day20201031.homework;

import lombok.AllArgsConstructor;
import lombok.NonNull;

import java.io.File;

@AllArgsConstructor
public class DeleteFolders {

    private final String pathToMainFolder;

    public void start() {
        if (isFolderAndExists(pathToMainFolder)) {
            File mainFolder = new File(pathToMainFolder);

            String[] paths = getListOfFilesByRecursion(mainFolder).split("\n");
            for (int i = paths.length-1 ; i > 1 ; i--)
                System.out.println(paths[i] + " was deleted: " + new File(paths[i]).delete());
        }
    }

    protected String getListOfFilesByRecursion(File file) {
        if (file.listFiles().length == 0) {
            return "\n" + file;
        } else {
            return "\n" + file + getListOfFilesByRecursion(file.listFiles()[0]);
        }
    }

    protected boolean isFolderAndExists(@NonNull String path) {
        File folder = new File(path);
        return folder.exists() && folder.isDirectory();
    }

}
