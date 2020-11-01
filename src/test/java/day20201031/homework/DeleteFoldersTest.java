package day20201031.homework;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;

public class DeleteFoldersTest {

    private final String FOLDERS_ARE_IN = "src\\main\\resources\\HomeworkFolders";
    private final File mainFolder = new File(FOLDERS_ARE_IN);

    private final File folder10 = new File("src\\main\\resources\\HomeworkFolders\\Folder\\Folder\\Folder\\Folder\\Folder\\Folder\\Folder\\Folder\\Folder\\Folder");
    private final File folder02 = new File("src\\main\\resources\\HomeworkFolders\\Folder\\Folder");

    private final String folders02 =
            "\n" +
            "src\\main\\resources\\HomeworkFolders\n" +
            "src\\main\\resources\\HomeworkFolders\\Folder\n" +
            "src\\main\\resources\\HomeworkFolders\\Folder\\Folder";

    private final String folders10 =
        "\n" +
        "src\\main\\resources\\HomeworkFolders\n" +
        "src\\main\\resources\\HomeworkFolders\\Folder\n" +
        "src\\main\\resources\\HomeworkFolders\\Folder\\Folder\n" +
        "src\\main\\resources\\HomeworkFolders\\Folder\\Folder\\Folder\n" +
        "src\\main\\resources\\HomeworkFolders\\Folder\\Folder\\Folder\\Folder\n" +
        "src\\main\\resources\\HomeworkFolders\\Folder\\Folder\\Folder\\Folder\\Folder\n" +
        "src\\main\\resources\\HomeworkFolders\\Folder\\Folder\\Folder\\Folder\\Folder\\Folder\n" +
        "src\\main\\resources\\HomeworkFolders\\Folder\\Folder\\Folder\\Folder\\Folder\\Folder\\Folder\n" +
        "src\\main\\resources\\HomeworkFolders\\Folder\\Folder\\Folder\\Folder\\Folder\\Folder\\Folder\\Folder\n" +
        "src\\main\\resources\\HomeworkFolders\\Folder\\Folder\\Folder\\Folder\\Folder\\Folder\\Folder\\Folder\\Folder\n" +
        "src\\main\\resources\\HomeworkFolders\\Folder\\Folder\\Folder\\Folder\\Folder\\Folder\\Folder\\Folder\\Folder\\Folder";

    @Test
    public void isFolderExistsTest() {
        mainFolder.mkdir();
        Assert.assertFalse(new DeleteFolders(FOLDERS_ARE_IN).isFolderAndExists(FOLDERS_ARE_IN + "\\NotExists"));
        Assert.assertTrue(new DeleteFolders(FOLDERS_ARE_IN).isFolderAndExists(FOLDERS_ARE_IN));
    }

//    @Test
//    public void getListOfFilesByRecursionTest_TwoFiles() {
//        folder02.mkdirs();
//        Assert.assertEquals(new DeleteFolders(FOLDERS_ARE_IN).getListOfFilesByRecursion(mainFolder), folders02);
//    }

    @Test
    public void getListOfFilesByRecursionTest_TenFiles() {
        folder10.mkdirs();
        Assert.assertEquals(new DeleteFolders(FOLDERS_ARE_IN).getListOfFilesByRecursion(mainFolder), folders10);
    }

}
