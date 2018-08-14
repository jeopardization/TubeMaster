package xertxa.tubemaster.bot.module.modules;

import xertxa.tubemaster.bot.module.Module;

public class ChannelBot extends Module {
    private String linkType;

    public ChannelBot(String target, String action, String linkType) {
        super(target, action);
        this.linkType = linkType;
    }

    @Override
    public void run() {
        while (account < amount) {
            try {
                setDriver();
                String username = usernames.get(account);
                String password = passwords.get(account);
                login(username, password);
                switch (action) {
                    case "subscribe":
                        driver.get("https://www.youtube.com/" + linkType + "/" + target);
                        click("//button[contains(@class, 'yt-uix-button-subscribe-branded')]");
                        controller.log("[SUCCESS] Subscribed to channel using account \"" + username + "\".");
                        break;
                    case "block":
                        driver.get("https://www.youtube.com/" + linkType + "/" + target + "/about");
                        click("//button[contains(@class, 'about-action-report-user')]");
                        click("//span[contains(@data-overlay-class, 'flag-channel-overlay-0')]");
                        click("//div[contains(@class, 'flag-channel-overlay-0')]/div[1]/button[contains(@class, 'submit-flag-with-action')]");
                        controller.log("[SUCCESS] Blocked channel using account \"" + username + "\".");
                        break;
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            driver.quit();
            increment();
        }
        controller.log("[SUCCESS] Task completed. ChannelBot terminated.");
    }
}
