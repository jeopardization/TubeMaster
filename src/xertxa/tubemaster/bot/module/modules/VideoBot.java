package xertxa.tubemaster.bot.module.modules;

import xertxa.tubemaster.bot.module.Module;

public class VideoBot extends Module {
    public VideoBot(String target, String action) {
        super(target, action);
    }

    @Override
    public void run() {
        while (account < amount) {
            try {
                setDriver();
                String username = usernames.get(account);
                String password = passwords.get(account);
                login(username, password);
                driver.get("https://www.youtube.com/watch?v=" + target);
                switch (action) {
                    case "like":
                        click("//button[contains(@class, 'like-button-renderer-like-button')]");
                        controller.log("[SUCCESS] Liked video using account \"" + username + "\".");
                        break;
                    case "dislike":
                        click("//button[contains(@class, 'like-button-renderer-dislike-button')]");
                        controller.log("[SUCCESS] Disliked video using account \"" + username + "\".");
                        break;
                }
                driver.close();
                driver.quit();
                increment();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        controller.log("[SUCCESS] Task completed. VideoBot terminated.");
    }
}
