package day20201031.homework;

import lombok.AllArgsConstructor;

import java.io.File;
import java.util.Optional;

import static day20201031.homework.Colors.*;

@AllArgsConstructor
public class MakeFolders {

    private final int amountOfFolders;
    private final String pathToMainFolder;

    public void start() {
        createMainFolderIfNeeded(pathToMainFolder).ifPresentOrElse(
                folder -> {
                    int counter = 0;
                    String path = folder.getAbsolutePath();
                    String alreadyExists = "";

                    while (counter++ < amountOfFolders) {
                        path += "\\Folder";
                        File innerFolder = new File(path);
                        alreadyExists = innerFolder.mkdir() ? "new Folder was created" : "Folder already exists";

                        System.out.printf(BLUE + "%-3d" + RESET + " %s: %s\n",
                                counter,
                                alreadyExists,
                                innerFolder.getAbsolutePath()
                        );
                    }

                }, () -> System.out.println("Cannot proceed")
        );
    }

    private Optional<File> createMainFolderIfNeeded(String path) {
        File folder = new File(path);

        if (folder.exists() && !folder.isDirectory()) {
            System.out.printf(RED + "%s already exists, but it's not a folder, change name!\n" + RESET, folder.getName());
            return Optional.empty();
        }

        if (folder.exists() && folder.isDirectory()) {
            System.out.printf(BLUE + "Folder %s exists... no need to create\n" + RESET, folder.getName());
            return Optional.of(folder);
        }

        return folder.mkdir() ? Optional.of(folder) : Optional.empty();
    }

}



