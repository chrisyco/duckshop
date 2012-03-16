---
layout: default
title: Permissions
---

Recommended permissions
-----------------------

### Normal player ###

* `duckshop.use.*`
* `duckshop.create.personal`
* `duckshop.break.personal`


### Moderator ###

* `duckshop.break.other`


The Big Three
-------------

There are three main types of permissions in DuckShop: personal, other and global.

* **personal** means *your own sign*.
A player with `duckshop.break.personal` permission would be able to break their own signs.

* **other** means *other players' signs*.
A player with `duckshop.create.other` will be able to create shops with other player's names on them.

* **global** is for signs that are *owned by the server*.


List of permissions
-------------------

<table>
<tr>
  <th style="width: 12em">Permission</th>
  <th style="width: 20em">Description</th>
</tr>
<tr>
  <td><code>duckshop.use.<i>[type]</i></code></td>
  <td>Use a sign.</td>
</tr>
<tr>
  <td><code>duckshop.create.<i>[type]</i></code></td>
  <td>Create shops and connect chests.<br />(Both actions use the same permission.)</td>
</tr>
<tr>
  <td><code>duckshop.break.<i>[type]</i></code></td>
  <td>Break a sign.</td>
</tr>
</table>
