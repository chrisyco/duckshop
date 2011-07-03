===========
 Changelog
===========

..

8
=

* Modified to work even without an economy plugin. Without an economy
  plugin, signs will only support barter or giving/receiving items for
  free.

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
