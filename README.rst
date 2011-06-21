======================================================
 SignTraderWithDucks |--| like SignTrader, but better
======================================================

**SignTraderWithDucks** is a simple plugin for Bukkit_ that lets you
create fully automated shops with signs, with an emphasis on usability,
customizability and extensibility. And ducks.

Installation
============

The plugin is currently under development, so please don't use it :)

Building
========

1. Clone this repository.

2. Install the latest JDK_ and Maven_.

3. In a Unix terminal, type::

       tools/grab-dependencies.sh
       mvn package

   The first command downloads Bukkit_ and Register_ and installs it in
   Maven's plugin system. The second command compiles the actual plugin.

   If you use Windows, go to a pillow factory and get stuffed.

4. After the compilation has finished, copy the newly created JAR file
   from the ``target`` directory to your CraftBukkit plugins folder.

Usage
=====

Coming soon!

.. _Bukkit: http://www.bukkit.org/
.. _JDK: http://www.oracle.com/technetwork/java/javase/downloads/index.html
.. _Maven: http://maven.apache.org/
.. _Register: http://forums.bukkit.org/threads/16849/

.. |--| unicode:: U+2013 .. en dash
.. |---| unicode:: U+2014 .. em dash
   :trim:
