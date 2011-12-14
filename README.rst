===================================
 DuckShop |--| trading with ducks!
===================================

**DuckShop** is a simple plugin for Bukkit_ that lets you create fully
automated shops with signs, with an emphasis on usability,
customizability and extensibility. And ducks.

For help on using DuckShop, check out the documentation_.

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

License
=======

DuckShop, a Bukkit plugin for creating automated shops
Copyright (C) 2011 Chris Wong

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.

.. _Bukkit: http://www.bukkit.org/
.. _Documentation: http://wiki.xlipse.net/wiki/DuckShop
.. _JDK: http://www.oracle.com/technetwork/java/javase/downloads/index.html
.. _Maven: http://maven.apache.org/

.. |--| unicode:: U+2013 .. en dash
.. |---| unicode:: U+2014 .. em dash
   :trim:
