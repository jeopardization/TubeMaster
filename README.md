## Notice
This is still being tested. Expect bugs. Some features may be disabled/hardcoded as they haven't been tweaked yet. 

# TubeMaster
TubeMaster is a bot designed to automate the inflation of artificial YouTube stats. It is capable of liking/disliking videos and subscribing to channels using multiple accounts.

## Screenshots
![](https://i.imgur.com/GeTvHhE.png)
![](https://i.imgur.com/BGPTLNH.png)
![](https://i.imgur.com/bxwku4A.png)

## Usage
### Dashboard
| **Option** | **Description** | **Input Example** |
|-|-|-|
| **Account List** | List of accounts to be used for botting. Each account must have a username/email and password combination to sign in. They're read from a .TXT file and are separated line by line with a **username:password** format. | C:\Users\Jared\Documents\Accounts.txt |
| **Proxy List** | *N/A* | *N/A* |
| **Amount** | Number of accounts to use from the **Account List**. | 10 |
| **Threads** | Number of CPU threads to use. The more you use, the faster the botting will be. | 10 |
| **Module** | Botting module to use. Must be applied first from said module's tab. | Video Bot |
| **Logs** | Spits out details on how the botting is going. | *N/A* |

### Video Bot
| **Option** | **Description** | **Input Example** |
|-|-|-|
| **Video ID** | ID of the YouTube video you intend to bot. Should be a bunch of ambiguous characters just after the equal sign (=) near the end of the URL. | dPa2atWWVk8 |
| **Action** | Task for the bot to perform on the video. | Like |

### Channel Bot
| **Option** | **Description** | **Input Example** |
|-|-|-|
| **Channel** | ID of the YouTube channel you intend to bot. Should either be a user ID (followed by "/user/" in the URL) or a raw channel ID (followed by "/channel/" in the URL) | UC0X72NakQYxDmK4nIyhLKOA |
| **Action** | Task for the bot to perform on the channel. | Subscribe |
