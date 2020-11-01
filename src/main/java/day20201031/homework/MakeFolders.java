package day20201031.homework;

import lombok.AllArgsConstructor;

import java.io.File;
import java.util.Arrays;
import java.util.Optional;

import static day20201031.homework.Colors.*;

@AllArgsConstructor
public class MakeFolders {

    private final int amountOfFolders;
    private final String pathToMainFolder;

    // returned type needed for tests purposes.
    public File[] start() {
        File[] folders = new File[amountOfFolders + 1];

        createMainFolderIfNeeded(pathToMainFolder).ifPresentOrElse(
                folder -> {
                    int counter = 0;
                    String path = folder.getPath();
                    String alreadyExists = "";
                    folders[counter] = folder;

                    while (counter++ < amountOfFolders) {
                        path += "\\Folder";
                        File innerFolder = new File(path);
                        folders[counter] = innerFolder;
                        alreadyExists = innerFolder.mkdir() ? "new Folder was created" : "Folder already exists";

                        System.out.printf(BLUE + "%-3d" + RESET + " %s: %s\n",
                                counter,
                                alreadyExists,
                                innerFolder.getAbsolutePath()
                        );
                    }

                }, () -> System.out.println("Cannot proceed")
        );
        return folders;
    }

    protected Optional<File> createMainFolderIfNeeded(String path) {
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



