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

``SignTrader.use.personal``, ``SignTrader.use.global``
    Use personal and global signs, respectively.
``SignTrader.create.personal``, ``SignTrader.create.global``
    Create trading signs.
``SignTrader.break.personal``, ``SignTrader.break.global``
    Break trading signs. A player who has the ``break.personal``
    permission would be able to break other player's signs. Note that
    anyone with build rights can always break their own signs.

For example, average player would have these permissions:

* ``SignTrader.use.*``
* ``SignTrader.create.personal``

Usage
=====

Coming soon!

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
