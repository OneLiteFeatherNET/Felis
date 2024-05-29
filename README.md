## Felis

Minestom is a framework for creating Minecraft servers in Java without using Mojang's code. As a result, the current
state of the project does not include the default Minecraft commands. This is intentional, as Minestom is designed to be
a sandbox for creating your own custom server.

The `Felis` extension is a small 'Essentials' plugin that adds some basic commands to a Minestom server, similar to those
found in the standard server implementation.

## Commands

| Command           | Alias                   | Permission                   
-------------------|-------------------------|------------------------------|
| debug             |                         | felis.command.debug          |
| fly               |                         |                              |
| fly [target]      |                         | felis.command.fly.other      |
| gamemode          | gm                      |                              |
| gamemode [target] | gm [target]             | felis.command.gamemode.other |
| info              | plugins, extensions, pl | felis.command.info           |
| kick              | k                       | felis.command.kick           |
| kill              |                         |                              |
| kill [target]     |                         | felis.command.kill.other     |
| nightvision       | nv                      |                              |
| speed             | s                       |
| stop              |                         | felis.command.stopo          
| teleport          | tp                      |                              |
| teleport [target] | tp                      | felis.command.teleport.other 