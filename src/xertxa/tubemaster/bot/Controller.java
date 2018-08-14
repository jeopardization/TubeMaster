package xertxa.tubemaster.bot;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import xertxa.tubemaster.bot.module.Module;
import xertxa.tubemaster.bot.module.modules.ChannelBot;
import xertxa.tubemaster.bot.module.modules.VideoBot;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class Controller {
    private static VideoBot videoBot;
    private static ChannelBot channelBot;
    private static File accountList;
    private static File proxyList;
    private static ArrayList<Thread> threads = new ArrayList<>();
    @FXML
    private BorderPane main;
    @FXML
    private TextField accountListPath;
    @FXML
    private TextField proxyListPath;
    @FXML
    private TextField amount;
    @FXML
    private TextField threadAmount;
    @FXML
    private TextArea logs;
    @FXML
    private TextField videoID;
    @FXML
    private TextField channelID;
    @FXML
    private ToggleGroup videoAction;
    @FXML
    private ToggleGroup channelAction;
    @FXML
    private ToggleGroup linkType;
    @FXML
    private ToggleGroup selectedModule;

    @FXML
    private void handleShowView(ActionEvent e) {
        String view = (String) ((Node) e.getSource()).getUserData();
        loadFXML(getClass().getResource(view));
    }

    @FXML
    private void setAccountList() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("TXT Files", "*.txt"));
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            accountListPath.setText(file.getAbsolutePath());
            accountList = file;
        }
    }

    @FXML
    private void setProxyList() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("TXT Files", "*.txt"));
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            proxyListPath.setText(file.getAbsolutePath());
            proxyList = file;
        }
    }

    @FXML
    private void applyVideoBot() {
        String action = videoAction.getSelectedToggle().toString();
        if (action.contains("Like")) {
            action = "like";
        } else if (action.contains("Dislike")) {
            action = "dislike";
        }
        videoBot = new VideoBot(videoID.getText(), action);
    }

    @FXML
    private void applyChannelBot() {
        String action = channelAction.getSelectedToggle().toString();
        String type = linkType.getSelectedToggle().toString();
        if (action.contains("Subscribe")) {
            action = "subscribe";
        } else if (action.contains("Block")) {
            action = "block";
        }
        if (type.contains("User")) {
            type = "user";
        } else if (type.contains("Channel")) {
            type = "channel";
        }
        channelBot = new ChannelBot(channelID.getText(), action, type);
    }

    @FXML
    private void launch() {
        if (selectedModule.getSelectedToggle() != null) {
            String module = selectedModule.getSelectedToggle().toString();
            if (module.contains("Video")) {
                if (videoBot != null) {
                    launchModule(videoBot);
                } else {
                    log("[ERROR] VideoBot has not yet been instantiated.");
                }
            } else if (module.contains("Channel")) {
                if (channelBot != null) {
                    launchModule(channelBot);
                } else {
                    log("[ERROR] ChannelBot has not yet been instantiated.");
                }
            }
        } else {
            log("[ERROR] Module not selected.");
        }
    }

    @FXML
    private void openGitHubPage() {
        try {
            Desktop.getDesktop().browse(new URL("https://github.com/xertxa/TubeMaster").toURI());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void openDevPage() {
        try {
            Desktop.getDesktop().browse(new URL("https://github.com/xertxa").toURI());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void launchModule(Module module) {
        module.setController(this);
        if (accountList != null) {
            module.setAccounts(accountList);
            if (proxyList != null) {
                module.setProxies(proxyList);
            } else {
                log("[INFO] Proxy list is empty. Botting will be performed without proxies.");
            }
            if (amount != null) {
                int amountValue = Integer.parseInt(amount.getText());
                if (amountValue > 0) {
                    module.setAmount(amountValue);
                    if ((threadAmount != null)) {
                        int threadValue = Integer.parseInt(threadAmount.getText());
                        if (threadValue > 0) {
                            log("[INFO] Thread amount of " + threadValue + " will be used.");
                            for (int i = 0; i < threadValue; i++) {
                                threads.add(new Thread(module));
                            }
                            for (Thread thread : threads) {
                                try {
                                    thread.join();
                                    thread.start();
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                            }
                            if (module instanceof VideoBot) {
                                log("[SUCCESS] VideoBot launched with a video ID of \"" + module.target + "\" and will be " + module.action + " botted.");
                            } else {
                                log("[SUCCESS] ChannelBot launched with a channel ID of \"" + module.target + "\" and will be " + module.action + " botted.");
                            }
                        } else {
                            log("[ERROR] Thread amount cannot be negative.");
                        }
                    } else {
                        log("[ERROR] Thread amount not set.");
                    }
                } else {
                    log("[ERROR] Amount cannot be negative.");
                }
            } else {
                log("[ERROR] Amount not set.");
            }
        } else {
            log("[ERROR] Account list not set.");
        }
    }

    private void loadFXML(URL url) {
        try {
            FXMLLoader loader = new FXMLLoader(url);
            main.setCenter(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void log(String message) {
        logs.appendText(message + "\n");
    }

    public void exit() {
        System.exit(0);
    }
}
