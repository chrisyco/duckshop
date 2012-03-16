---
layout: default
title: Installation
---

In short
--------

1. Download and install [DuckShop][].
2. Download and install [OddItem][].
3. Download and install [Vault][].
4. Restart your server.

That's it. :P

[DuckShop]: http://dev.bukkit.org/server-mods/duckshop/
[OddItem]: http://dev.bukkit.org/server-mods/odditem/
[Vault]: http://dev.bukkit.org/server-mods/vault/


Optional bits and pieces
------------------------

Depending on how you use it, you may need these extra plugins installed too.


### Permissions ###

Installing a permissions plugin is optional. If you don't install one, the plugin would simply give users the default permissions.

For permissions settings, see the [permissions reference]({{ site.baseurl }}/install/permissions).


### Economy ###

DuckShop uses [Vault][] for economy support. Unless your economy plugin is very obscure, it is likely it is supported.

If an economy plugin isn't installed, you can still trade items normally, but obviously money will not work.


### Chest Protection ###

Currently, [Lockette](http://forums.bukkit.org/threads/4336/) and [LWC](http://forums.bukkit.org/threads/967/) are supported.

If [WorldGuard](http://wiki.sk89q.com/wiki/WorldGuard) is installed, DuckShop will check the region's "chest-access" flag before opening the chest.

See [Chest linking]({{ site.baseurl }}/use/chest-linking) for more info.
