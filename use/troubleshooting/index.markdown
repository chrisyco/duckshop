---
layout: default
title: Troubleshooting
---

Startup and shutdown
--------------------

### If a world is renamed, it causes an "IllegalArgumentException: Invalid world name" error
Firstly, stop the server! Then delete the `plugins/DuckShop/chests.properties` file.
Start up the server again and re-link all the chests.


### Chest links aren't saved

Make sure you stop the server using the `stop` command.
If you just click the X button to close it, nothing gets saved properly and files could become corrupted.


Trading
-------

### Cannot trade damaged tools, weapons or armor

This is normal, to stop players from selling diamond pickaxes with only one use left, for example.
Check the [examples][] page for examples of valid signs.

[examples]: {{ site.baseurl }}/use/examples
