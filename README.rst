===================================
 DuckShop |--| trading with ducks!
===================================

**DuckShop** is a simple plugin for Bukkit_ that lets you create fully
automated shops with signs, with an emphasis on usability,
customizability and extensibility. And ducks.

Installation
============

Pop the JAR in your plugins folder.

You will also need an economy plugin such as iConomy_. Other plugins
should work; for a list of supported plugins go to the Register_ page.
If no economy plugins are installed, trades that do not involve money
(giving/receiving items) will still work.

You should also install `Permissions 3`_. If Permissions is not
installed, it would default to ops only for everything, which isn't very
useful.

Since version 19, DuckShop also supports LWC_ and Lockette_ for chest
protection. Users would only be able to sell from chests that they own.

Permissions Nodes
-----------------

These are **case-sensitive**, so make sure they're typed in correctly!

* ``DuckShop.use.<type>``
* ``DuckShop.create.<type>``
* ``DuckShop.break.<type>``

``<type>`` is replaced by either ``personal``, ``global`` or ``other``.
"Personal" affects their own signs. "Global" affects global signs (see
below). "Other" affects other people's personal signs. So someone with
``DuckShop.create.other`` permission would be allowed to create signs
with other people's names on them, and someone with
``DuckShop.break.other`` would be allowed to break other people's
signs.

For example, an average player would have these permissions:

* ``DuckShop.use.*``
* ``DuckShop.create.personal``
* ``DuckShop.break.personal``

Usage
=====

..

Using a Shop
------------

To use someone else's shop sign, right click on the sign.

Writing the Sign
----------------

To create a shop, place a sign in this format::

    <your name>
    <seller to buyer>
    <buyer to seller>
    <blank line>

replacing ``<your name>`` with your username and the two middle lines
with two items such as "1 sshovel" or "$52". If the username is left
blank, it will be filled in automatically. Once you've placed the sign,
it should display a helpful message.

If you have the ``DuckShop.create.global`` permission, you can create
"global" signs by replacing your name with ``[Global]``. Global signs
aren't associated with a player; they spawn items automatically rather
than drawing from a chest.

"Nothing" and "free" mean the same thing as $0, and can be used
interchangably.

For example, if Rachel, being the entrepreneurial type, wanted to sell
iron swords at $12.50 each::

    Rachel
    1 isword
    $12.50

This means Rachel will receive $12.50 and the person who clicks the sign
will receive an iron sword.

.. note::
   Floating point money values only work in version 7 or newer.

Or, if Peter, the up-and-coming master builder, wanted to buy wooden
planks at $2 for four::

    Peter
    $2
    4 plank

Peter will receive 4 planks and the sign clicker will receive $2.

Or, if Tim was feeling poor and wanted donations::

    Tim
    nothing
    $5

Or, if Violet was feeling rather kind and wanted to give free dirt::

    Violet
    64 dirt
    free

Or, if Wally the admin needed to get rid of slimes::

    [Global]
    $0.25
    1 slime

Damage Values
'''''''''''''

Since version 10, you can specify damage values by appending a number to
the item name, e.g.::

    Bob
    1 wool6
    $1

for selling pink wool. Since version 11, you can also use names such as
``pinkwool``, just like normal items.

If no damage value is specified, it will default to 0. A nice side
effect of this is that it is impossible to trade used tools or armor.

Connecting the Chest
--------------------

Unless your sign is set to ``[Global]``, where items are created out of
thin air, you would need connect your sign to a chest.

To connect a sign to a chest, type ``/duckshop link``. Then follow the
on-screen instructions to complete the link. Links are saved
automatically, so you only need to connect it once.

Chest linking uses the same permissions nodes as placing signs, so to
link your own signs, you need the ``DuckShop.create.personal``
permission.

.. note::
   If you have chest protection enabled and you've connected to a locked
   chest, it will fail only when you try to use the sign, not when you
   connect it.

Building
========

1. Clone this repository.

2. Install the latest JDK_ and Maven_.

3. In a Unix terminal, type::

       mvn clean package

   Maven, being the epic piece of software that it is, downloads all the
   required dependencies automatically.

4. After the compilation has finished, copy the newly created JAR file
   from the ``target`` directory to your CraftBukkit plugins folder and
   set it up as above.

.. _LWC: http://forums.bukkit.org/threads/967/
.. _Lockette: http://forums.bukkit.org/threads/4336/
.. _Bukkit: http://www.bukkit.org/
.. _JDK: http://www.oracle.com/technetwork/java/javase/downloads/index.html
.. _Maven: http://maven.apache.org/
.. _Register: http://forums.bukkit.org/threads/16849/
.. _Permissions 3: http://forums.bukkit.org/threads/18430/
.. _iConomy: http://forums.bukkit.org/threads/40/

.. |--| unicode:: U+2013 .. en dash
.. |---| unicode:: U+2014 .. em dash
   :trim:
