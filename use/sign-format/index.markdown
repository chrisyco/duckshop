---
layout: default
title: Sign format
---

Signs are the core of [DuckShop][]. Shops are created by writing specially crafted text on a sign.

[DuckShop]: {{ site.baseurl }}


Anatomy of a sign
-----------------

![Example of a valid sign](sign-example.png)

All signs have these four lines:

1. Either the sign owner, or the word `[Global]` if it is a server sign (see below). If you leave this blank, it will be filled in with your name.
2. What the owner gives to the clicker.
3. What the clicker gives to the owner.
4. A blank line. Once the sign is placed, DuckShop will fill this in with a [status message][].

A player trades with the owner by right clicking it.

[status message]: {{ site.baseurl }}/use/status-messages


### Owners and clickers

The **sign owner** is the player whose name is written on the sign. If chest protection is enabled, the chest is accessed using the sign owner's name.

The **sign clicker** is the player who clicks the sign, and ends up trading with it.


Items and money
---------------

Money is simply written with a dollar sign and a number, e.g. `$0.25` or `$23`.

Items are written using the number, then an item name, e.g. `5 diamonds`.

`Nothing` and `free` mean the same thing as `$0`, and can be used interchangeably.


Damage values
-------------

You can specify damage values by appending a space, then a number to the item name, e.g. `wool 6` for pink wool. You can also use `pinkwool` directly. If you don't specify a damage value, it will default to zero.

**Note**: You cannot sell damaged tools. This is to stop players from selling diamond pickaxes with one use left, for example.


Server-owned ("global") signs
-----------------------------

*Server-owned* or *global* signs aren't associated with a player; they spawn items automatically rather than drawing from a chest. If you have the `duckshop.create.global` [permission][], you can create a global sign by using `[Global]` as the sign owner.

[permission]: {{ site.baseurl }}/install/permissions


See also
--------

* [Examples][] for examples of signs.

[examples]: {{ site.baseurl }}/use/examples
