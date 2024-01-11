# LavaNotify
A notify plugin for Velocity. Simple but works.

![Logo](/LavaNotify-Logo.png)

## Feature
* Support both MiniMessage and legacy style code (included HEX color)
```html
<red><bold>I'm red and bold.</bold></red> &r&fI'm white! <reset><color:#f082ff>More</color> &#123456colors!
```
* Support send to all players or a single server.
* Notify display to Chat, ActionBar, BossBar and Title.

## Command

`/notify <type> <channel> ...`

* `<type>`: Can be `actionbar`, `bossbar`, `chat`, `title` and `broadcast`.
  * `broadcast` will send a chat message with `<!>` prefix.
  * `bossbar` now only will send a blue bar with 10s.
* `<channel>`: filter players to send.
  * `all` send to all players in this Velocity server.
  * `server` send to specified server.

### Send a title

```
/notify title <channel> [title] [subtitle] [fadeIn] [stay] [fadeOut]
/notify title server lobby "This is a <green>title</green>!" "This is a subtitle." 500 1000 1000
```

* `title` and `subtitile`: Single or double quotation (`'` / `"`) can be used to separate different lines of a title.
* `fadeIn`, `stay` and `fadeOut`:  Using milliseconds. Default are 500, 3000, 500.

### Alias for broadcast

`/broadcast`, `/alert`, `/bc` are some aliases of `/notify broadcast all`.