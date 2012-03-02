WolfHunt
========

WolfHunt is a Minecraft Bukkit plug-in designed by the staff at minecraft.runsafe.no to allow players to use Wolves to track down other players within the world.

This plug-in is intended for use in a survival enviroment where PvP is enabled.

Permissions
-----------

* wolfhunt.canTrack - If true the player will be allowed to use the tracking item on wolves to hunt players.
* wolfhunt.commandSpawnWolf - If true the player will be able to use the wolf spawning command. (Used for debugging)
* wolfhunt.commandGetConfig - Allows the player to get config values for WolfHunt
* wolfhunt.commandSetConfig - Allows the player to set the config values for WolfHunt

Commands
--------

* /wolfhunt spawnwolf - Spawns an untamed wolf. [Permission: wolfhunt.commandSpawnWolf]
* /wolfhunt getconfig [key] - Returns the config value of the specified key. [Permission: wolfhunt.commandGetConfig]
* /wolfhunt setconfig [key] [value] - Sets the config value of the specified key. [Permission: wolfhunt.commandSetConfig]

Configuration
--------

* preventTrackingOps [Default: true] - If true, server OPs will not be tracked by wolves.
* allowOpOverride - [Default: false] - If true, being a server OP will override all WolfHunt permissions.
* trackingItem - [Default: 287] - The item used to track players when right clicked on an owned wolf.
* trackingRadius - [Default: 200] - If a player is within this radius the wolf will simply growl to alert the player instead of giving a direction.