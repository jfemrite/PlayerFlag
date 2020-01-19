# PlayerFlag
Custom Plugin Request (FREE SOURCECODE)

**Supports 1.8-1.15**

Name: PlayerFlag

Version: 1.0.0

Author: BlueJ

** **

Allows staff to flag players with messages. This simple but useful plugin manages potential cheaters in your server and gives staff an extra step-up in communication. This stores information even when the server is offline, and no database required!

```
config.yml

messages:
  # When staff joins, this message displays. default: '&c[PLAYER FLAG] &eThere is currently &a{totalflags} active player flags. &7Use /playerflag for more information'
  staff-join: '&eThere is currently &a{totalflags} active player flags. &7Use /playerflag for more information'
  notification: '&c{player} &eflagged {target} &7: {message}'

# PERMISSIONS
# playerflag.cmd - gives general access to do /playerflag
# playerflag.notify - when a new playerflag is created, they are notified. And when a player joins with this permission, a message is displayed
# playerflag.create - can create a new playerflag by /playerflag create <name> <message>
# playerflag.remove - can remove a playerflag by /playerflag remove <name>
```
