===========
 Changelog
===========

..

32
==

* Ported to CraftBukkit 1.2 and OddItem 1.9.1.

* Now uses Vault for economy and permissions.

* Accepts item names with digits and hyphens. (`#25`_)

* Fixed item duplication bug on CB 1.2. (`#27`_)

.. _#25: http://dev.bukkit.org/server-mods/duckshop/tickets/25/
.. _#27: http://dev.bukkit.org/server-mods/duckshop/tickets/27/

31
==

* Updated to work with LWC 4. (`#12`_)

* Works with CraftBukkit 1.0.1 (`#16`_)

* DuckShop no longer clobbers the contents of your signs.

* Changed to AGPL license.

* Added the Fill-O-Meter (tm). (`#15`_)

.. _#12: http://dev.bukkit.org/server-mods/duckshop/tickets/12/
.. _#15: http://dev.bukkit.org/server-mods/duckshop/tickets/15/
.. _#16: http://dev.bukkit.org/server-mods/duckshop/tickets/16/

30
==

* Fixed bug with names transmogrifying into beautiful but incorrect numbers with
  semicolons. The horror!

30-rc-2
=======

* Fixed NoSuchFieldException.

30-rc-1
=======

* Superperms support.

* Made permissions nodes lowercase (DuckShop.use.* -> duckshop.use.*)

* iConomy 6 support.

* Fixed conflict with other plugins that use Register.

* Fix sign ending up blank if player tries to break it.

* Uses OddItem for item names.

* Added logo.

27
==

* Fixed NullPointerException if a player clicks air.

26
==

* Added WorldGuard support.

25
==

* Fixed ArrayIndexOutOfBounds when using double chests.

* Added version command.

24
==

* Added support for double chests. If a sign is connected to one half of
  the chest, the plugin will automatically find the other half.

* Added warning when a connected chest is broken.

23
==

* Check if first line actually contains a valid username.

* Fixed build error.

22
==

* Use more specific messages if trade could not be completed -- e.g.
  "There is not enough space for 6 fish" rather than a generic "Oh noes!
  Cannot trade!"

21
==

* Fixed item names Netherrack, Green Music Disc and Gold Music Disc.

* Added unit tests.

20
==

* Added permissions check when linking signs. Now, players must have the
  appropriate ``DuckShop.create.*`` permission to link a sign.

* Global signs can no longer be connected to chests.

19
==

* Fixed crash when chest protection plugin not loaded.

* Fixed missing dependency error that stopped it from compiling.

18
==

* Implemented LWC support! Yay!

* Updated Register to support iConomy 6.

* Corrected 'soup' to 'stew' (thanks Kingbobson).

17
==

* Fixed issue #4: Cannot parse item names such as "wool3" and "mspawn50".

* Refactored chest protection and permissions system.

16
==

* Completely overhauled the chest protection module. Only Lockette is
  currently supported -- LWC support coming soon!

* Retired the Super Awesome Dependency Downloader.

15
==

* Fixed issue where signs would not work if their owner was offline.

* Plugged NullPointerException when checking if a chest is protected.
  Currently, chest protection only works if the owner of the sign is
  online.

14
==

* Fixed bug where someone without ``create`` permissions would not be
  able to place non-trading signs.

* Modified block listener to ignore cancelled events.

13
==

* Handles world names with spaces and numbers correctly.

* Improved logging messages.

12
==

* Checks if an item ID actually refers to a real item :D

* Parses and displays raw item IDs correctly.

* More helpful comments in the parsing code.

11
==

* Implemented special names for damage values, e.g. bluewool and inksac.

10
==

* Implemented damage values!

* Changed name from SignTraderWithDucks to DuckShop.

9
=

* Finally added 1.6 and 1.7 item names :D

* Removed redundant JUnit dependency.

8
=

* Modified to work even without an economy plugin. Without an economy
  plugin, signs will only support barter or giving/receiving items for
  free.

* Added support for "nothing" and "free" as aliases for $0.

7
=

* Implemented floating point money values (e.g. $1.5, $0.01).

* Split SignItem class into the two classes TangibleItem and Money to
  allow for floating point money values and integer item amounts.

* Removed "yay"s because everyone kept complaining about them :D

6
=

* Fixed item names.

5
=

* Now supports MiXedCAse item names and pluralS.

4a
==

* Fixed version number. Don't ask.

4
=

Bugfix release.

* Fixed plugin complaining when data folder already exists.

* Used Bukkit's Maven repo instead of installing it manually

3
=

* Changed sign format to make it more concise. The ``SignLine`` and
  ``SignVerb`` classes are redundant and have been removed.

* If the first line is left blank, it is automatically set to the player
  who placed the sign.

* Cleaned up sign updating code.

* Fixed dependency downloader to only download files once.

2
=

* Complete permissions overhaul. Nodes are now in the form
  ``SignTrader.<something>.<type>``.

* Got around to writing a changelog.

1
=

* Chest protection -- calls a PlayerInteractEvent on the chest before
  accessing it.

0
=

First stable version.

* Permissions support.
* Economy support via Register.
* Chest linking via the ``/signtrader link`` command.
