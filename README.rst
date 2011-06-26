======================================================
 SignTraderWithDucks |--| like SignTrader, but better
======================================================

**SignTraderWithDucks** is a simple plugin for Bukkit_ that lets you
create fully automated shops with signs, with an emphasis on usability,
customizability and extensibility. And ducks.

Installation
============

Pop the JAR in your plugins folder.

You will also need an economy plugin such as iConomy_. Other plugins
should work; for a list of supported plugins go to the Register_ page.

You should also install `Permissions 3`_. If Permissions is not
installed, it would default to ops only for everything, which isn't very
useful.

Permissions Nodes
-----------------

These are **case-sensitive**, so make sure they're typed in correctly!

* ``SignTrader.use.<type>``
* ``SignTrader.create.<type>``
* ``SignTrader.break.<type>``

``<type>`` is replaced by either ``personal``, ``global`` or ``other``.
"Personal" affects their own signs. "Global" affects global signs (see
below). "Other" affects other people's personal signs. So someone with
``SignTrader.create.other`` permission would be allowed to create signs
with other people's names on them, and someone with
``SignTrader.break.other`` would be allowed to break other people's
signs.

For example, an average player would have these permissions:

* ``SignTrader.use.*``
* ``SignTrader.create.personal``
* ``SignTrader.break.personal``

Usage
=====

..

Writing the Sign
----------------

To create a shop, place a sign in this format::

    <your name>
    Give <something>
    Take <something>
    <blank line>

replacing ``<your name>`` with your username and ``<something>`` with an
item such as "1 sshovel" or "$52". If you did this correctly, you should
receive a helpful message. If you didn't receive the helpful message,
break the sign and try again.

If you have the ``SignTrader.create.global`` permission, you can create
"global" signs by replacing your name with ``[Global]``. Global signs
aren't associated with a player; they spawn items automatically rather
than drawing from a chest.

For example, if Rachel, being the entrepreneurial type, wanted to sell
iron swords at $12 each::

    Rachel
    Give $12
    Take 1 isword

Or, if Peter, the up-and-coming master builder, wanted to buy wooden
planks at $2 for four::

    Peter
    Give 4 plank
    Take $2

Or, if Tim was feeling poor and wanted donations::

    Tim
    Give $5
    Take $0

Or, if Violet was feeling rather kind and wanted to give free dirt::

    Violet
    Take 64 dirt
    Give $0

.. tip::
   You can swap the order of the Give and Take lines, as long as they
   are both present.

Or, if Wally the admin needed to get rid of slimes::

    [Global]
    Give 1 slime
    Take $1

Connecting the Chest
--------------------

Unless your sign is set to ``[Global]``, where items are created out of
thin air, you would need connect your sign to a chest.

To connect a sign to a chest, type ``/signtrader link``. Then follow the
on-screen instructions to complete the link. Links are saved
automatically, so you only need to connect it once.

.. note::
   If you have chest protection enabled and you've connected to a locked
   chest, it will fail only when you try to use the sign, not when you
   connect it.

Building
========

1. Clone this repository.

2. Install the latest JDK_ and Maven_.

3. In a Unix terminal, type::

       tools/grab-dependencies.sh
       mvn package

   The first command downloads Bukkit_, Register_ and `Permissions 3`_
   and installs them in Maven's dependency system. The second command
   compiles the actual plugin.

   If you use Windows, go to a pillow factory and get stuffed.

4. After the compilation has finished, copy the newly created JAR file
   from the ``target`` directory to your CraftBukkit plugins folder and
   set it up as above.

.. _Bukkit: http://www.bukkit.org/
.. _JDK: http://www.oracle.com/technetwork/java/javase/downloads/index.html
.. _Maven: http://maven.apache.org/
.. _Register: http://forums.bukkit.org/threads/16849/
.. _Permissions 3: http://forums.bukkit.org/threads/18430/
.. _iConomy: http://forums.bukkit.org/threads/40/

.. |--| unicode:: U+2013 .. en dash
.. |---| unicode:: U+2014 .. em dash
   :trim:
