===========
 Changelog
===========

..

3
=

* Changed sign format to make it more concise. The ``SignLine`` and
  ``SignVerb`` classes are redundant and have been removed.

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
