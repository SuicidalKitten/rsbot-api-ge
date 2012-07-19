Grand Exchange API
==================

A Java library which can be used to retrieve information regarding items in the Runescape world.
Item information may, for example, include the item's price and its current price trend.
This is done by interacting with various publicly available Grand Exchange APIs.

Dependencies
------------

* [Google Guava](http://code.google.com/p/guava-libraries) -- version 12.0 or later is recommended.
* [Jackson JSON](http://jackson.codehaus.org) -- only `jackson-core` is required; version 1.9.8 or later recommended.

Be aware that only small parts of Guava are required to use the library. If using the library in
a live project, consider using a tool like [ProGuard](http://proguard.sourceforge.net) in order
to only keep the class files required to use the library. Note that, as of writing, the library
does not rely on any part of the public [RSBot API](https://github.com/powerbot/RSBot-API) in
order to function properly. There are, however, no guarantees that this will remain true in the future.

Support
-------

The Grand Exchange APIs currently supported and used by the API are the following.

* [Grand Exchange APIs][geapi] -- the official Grand Exchange APIs.
* [Unofficial Runescape API][rsapi] -- the unofficial Runescape API.

Usage
-----

The main class of interest for end users is the [GrandExchange][ge] class, which provides easy
to use methods for interacting with the various supported APIs. The following is a quick
explanation of the various methods found in the mentioned class.

    #getGEItemInfo(int) -- returns information on the specified item using the official API
    #getItemInfo(int) -- returns information on the specified item using the unofficial API
    #getItemInfo(..) -- returns a list of item information using the unofficial API
    #searchItems(String) -- searches for items using the specified string as query
    #getThumbnail(int) -- returns a thumbnail image of the specified item

Examples
--------

Below are some examples to give you an idea of how the API can be used in order to complete
some, more or less, common tasks.

**Example:** Print the name and price of an item, as well as today's price change and trend.

    final Optional<GEItemInfo> infoOptional = GrandExchange.getGEItemInfo(ID_ITEM);
    if(infoOptional.isPresent()) {
        final GEItemInfo itemInfo = infoOptional.get();
        final PriceChangeTrend changeTrend = itemInfo.getTodaysChangeTrend();

        System.out.println(itemInfo.getName() + " (" + itemInfo.getPrice() + "; "
                + changeTrend.getPriceChange() + ", " + changeTrend.getTrend() + ")");
    }

**Example:** Print item information from a list or array of item identification values.

    for(final ItemInfo itemInfo : GrandExchange.getItemInfo(ITEM_IDS))
        System.out.println(itemInfo);

**Example:** Find out which partyhat is the most expensive and print its information.

        ItemInfo mostExpensive = null;
        for(final ItemInfo itemInfo : GrandExchange.searchItems("partyhat")) {
            if(mostExpensive == null || itemInfo.getPrice() > mostExpensive.getPrice())
                mostExpensive = itemInfo;
        }

        System.out.println(mostExpensive);

**Example:** Store a thumbnail of an item using a specified identification value.

    ImageIO.write(GrandExchange.getThumbnail(ITEM_ID), "png", FILE_LOCATION)

Notes
-----

* Contributions are welcomed. Feel free to suggest any changes you find appropriate.
* The unofficial [Runescape API][rsapi] is generally faster to use than the official
  [Grand Exchange APIs][geapi].
* The project is licensed under the [Do What The Fuck You Want To Public License, Version 2][lic].

[ge]: https://github.com/vilon/rsbot-api-ge/tree/master/src/org/powerbot/game/api/methods/web/ge/GrandExchange.java
[geapi]: http://services.runescape.com/m=rswiki/en/Grand_Exchange_APIs
[rsapi]: http://rsapi.net
[lic]: http://sam.zoy.org/wtfpl/COPYING