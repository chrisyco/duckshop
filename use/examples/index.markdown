---
layout: default
title: Examples
---

This page contains examples of the [sign format][].

[sign format]: {{ site.baseurl }}/use/sign-format


Selling items
-------------

If Rachel, being the entrepreneurial type, wanted to sell iron swords at $12.50 each:

    Rachel
    1 isword
    $12.50

This means Rachel will receive $12.50 and the person who clicks the sign will receive an iron sword.


Buying items
------------

Or, if Peter, the up-and-coming master builder, wanted to buy wooden planks at $2 for four:

    Peter
    $2
    4 plank

Peter will receive 4 planks and the sign clicker will receive $2.


Donations
---------

Or, if Tim was feeling poor and wanted donations:

    Tim
    nothing
    $5


Freebies
--------

Or, if Violet was feeling rather kind and wanted to give free dirt:

    Violet
    64 dirt
    free


Global signs
------------

Or, if Wally the admin needed to get rid of slimes:

    [Global]
    $0.25
    1 slime


Damage values
-------------

One day, Bob found a huge pile of pink wool. He decided to sell it.

    Bob
    8 wool 6
    $1

Since version 11, you can specify the item name directly:

    Bob
    8 pinkwool
    $1
