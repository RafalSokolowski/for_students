package day20201031.homework;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class MakeFoldersTest {

    private final String FOLDERS_ARE_IN = "src\\main\\resources\\HomeworkFolders";
    private final File mainFolder = new File(FOLDERS_ARE_IN);

    private final File folder01 = new File("src\\main\\resources\\HomeworkFolders\\Folder");
    private final File folder02 = new File("src\\main\\resources\\HomeworkFolders\\Folder\\Folder");
    private final File folder03 = new File("src\\main\\resources\\HomeworkFolders\\Folder\\Folder\\Folder");
    private final File folder04 = new File("src\\main\\resources\\HomeworkFolders\\Folder\\Folder\\Folder\\Folder");
    private final File folder05 = new File("src\\main\\resources\\HomeworkFolders\\Folder\\Folder\\Folder\\Folder\\Folder");
    private final File folder06 = new File("src\\main\\resources\\HomeworkFolders\\Folder\\Folder\\Folder\\Folder\\Folder\\Folder");
    private final File folder07 = new File("src\\main\\resources\\HomeworkFolders\\Folder\\Folder\\Folder\\Folder\\Folder\\Folder\\Folder");
    private final File folder08 = new File("src\\main\\resources\\HomeworkFolders\\Folder\\Folder\\Folder\\Folder\\Folder\\Folder\\Folder\\Folder");
    private final File folder09 = new File("src\\main\\resources\\HomeworkFolders\\Folder\\Folder\\Folder\\Folder\\Folder\\Folder\\Folder\\Folder\\Folder");
    private final File folder10 = new File("src\\main\\resources\\HomeworkFolders\\Folder\\Folder\\Folder\\Folder\\Folder\\Folder\\Folder\\Folder\\Folder\\Folder");

    @Test
    public void createMainFolderIfNeededTest_CreateFailed() throws IOException {
        String FILE_ARE_IN_ALREADY_CREATED = "src\\main\\resources\\HomeworkFile";
        File mainFile = new File(FILE_ARE_IN_ALREADY_CREATED); // error, expecting Folder not File here... method should return Optional.empty()
        mainFile.createNewFile();

        Assert.assertEquals(new MakeFolders(1, FILE_ARE_IN_ALREADY_CREATED)
                .createMainFolderIfNeeded(FILE_ARE_IN_ALREADY_CREATED), Optional.empty());
    }

    @Test
    public void createMainFolderIfNeededTest_MainFolderAlreadyExists() {
        String FOLDERS_ARE_IN_ALREADY_CREATED = "src\\main\\resources\\HomeworkFoldersAlreadyCreated";
        File mainFolderAlreadyCreated = new File(FOLDERS_ARE_IN_ALREADY_CREATED);
        mainFolderAlreadyCreated.mkdir();

        Assert.assertEquals(new MakeFolders(1, FOLDERS_ARE_IN_ALREADY_CREATED)
                .createMainFolderIfNeeded(FOLDERS_ARE_IN_ALREADY_CREATED), Optional.of(mainFolderAlreadyCreated));
    }

    @Test
    public void createMainFolderIfNeededTest_CreateMainPolder() {
        Assert.assertEquals(new MakeFolders(1, FOLDERS_ARE_IN)
                .createMainFolderIfNeeded(FOLDERS_ARE_IN), Optional.of(mainFolder));
    }

    @Test
    public void startTest_ThreeFoldersCreation() {
        Assert.assertArrayEquals(new MakeFolders(3, FOLDERS_ARE_IN)
                .start(), new File[]{mainFolder, folder01, folder02, folder03});
    }

    @Test
    public void startTest_SixFoldersCreation() {
        Assert.assertArrayEquals(new MakeFolders(6, FOLDERS_ARE_IN)
                .start(), new File[]{mainFolder, folder01, folder02, folder03, folder04, folder05, folder06});
    }

    @Test
    public void startTest_TenFoldersCreation() {
        Assert.assertArrayEquals(new MakeFolders(10, FOLDERS_ARE_IN)
                .start(), new File[]{mainFolder, folder01, folder02, folder03, folder04, folder05, folder06, folder07, folder08, folder09, folder10});
    }

}
