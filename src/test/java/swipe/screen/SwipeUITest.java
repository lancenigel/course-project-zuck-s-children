package swipe.screen;

import account_creation.Account;
import data.persistency.ChatDataAccess;
import data.persistency.ChatDataAccessInterface;
import data.persistency.ChatDatabase;
import data.persistency.UserDatabase;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import swipe.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

class SwipeUITest {
    Account Nina;
    Account Lance;
    Account Kenji;
    SwiperPresenter testPresenter;
    SwiperInputBoundary testInteractor;
    SwiperController testController;
    LinkedList<Account> testArray;
    SwipeScreen screen;
    List<Object> chatRoomList;
    ChatDatabase chatDatabase;
    ChatDataAccess chatDataAccess;
    ChatDataAccessInterface chatDataAccessInterface;

    @BeforeEach
    void setUp() {
        chatRoomList = new ArrayList<>();
        chatDatabase = new ChatDatabase(chatRoomList);
        chatDataAccess = new ChatDataAccess();
        chatDataAccess.setChatdata(chatDatabase);
        chatDataAccessInterface = chatDataAccess;
        testPresenter = new SwiperPresenterFormatter();
        testInteractor = new SwiperInteractor(testPresenter, chatDataAccess);
        testController = new SwiperController(testInteractor);
        UserDatabase.getUserDatabase();
        Lance = new Account("Lance", "password");

        Lance.getProfile().setName("Lance");
        Lance.getProfile().setPronouns("he/him");
        Lance.getProfile().setYear("3");
        Lance.getProfile().setFieldOfStudy("Cognitive Science");
        List<String> LanceStudyStyles = new ArrayList<>();
        LanceStudyStyles.add("Quiet");
        LanceStudyStyles.add("Long");
        LanceStudyStyles.add("Visual");
        Lance.getProfile().setStudyStyles(LanceStudyStyles);
        UserDatabase.setCurrentUser(Lance);

        Kenji = new Account("Kenji", "password");

        Kenji.getProfile().setName("Kenji");
        Kenji.getProfile().setPronouns("he/him");
        Kenji.getProfile().setYear("2");
        Kenji.getProfile().setFieldOfStudy("Computer Science");
        // Kenji's study styles
        List<String> KenjiStudyStyles = new ArrayList<>();
        KenjiStudyStyles.add("Quiet");
        KenjiStudyStyles.add("Long");
        KenjiStudyStyles.add("Audio");
        Kenji.getProfile().setStudyStyles(KenjiStudyStyles);

        Nina = new Account("Nina", "password");

        Nina.getProfile().setName("Nina");
        Nina.getProfile().setPronouns("she/her");
        Nina.getProfile().setYear("3");
        Nina.getProfile().setFieldOfStudy("Computer Science");
        // Nina's study styles
        List<String> NinaStudyStyles = new ArrayList<>();
        NinaStudyStyles.add("Loud");
        NinaStudyStyles.add("Long");
        NinaStudyStyles.add("Visual");
        Nina.getProfile().setStudyStyles(NinaStudyStyles);
        UserDatabase.getAccounts().put("Nina", Nina);
        UserDatabase.getAccounts().put("Lance", Lance);
        UserDatabase.getAccounts().put("Kenji", Kenji);
        testArray = new LinkedList<>();
        testArray.add(Nina);
        testArray.add(Kenji);

        screen = new SwipeScreen(testController, testArray, 1);

    }

    @AfterEach
    void tearDown() {
        Lance = null;
        Kenji = null;
        Nina = null;
        testController = null;
        testPresenter = null;
        testInteractor = null;
        UserDatabase.setCurrentUser(null);
        UserDatabase.getAccounts().remove("Nina");
        UserDatabase.getAccounts().remove("Lance");
        UserDatabase.getAccounts().remove("Kenji");
    }

    @Test
    void testBuild() {
        SwiperUI ui = new SwiperUI();
        ui.build(testArray, testController);
        ui.build(screen);
        ui.build();

    }


}