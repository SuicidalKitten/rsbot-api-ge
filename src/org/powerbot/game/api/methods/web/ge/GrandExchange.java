/*
 * Filename: @(#)GrandExchange.java
 * Last Modified: 2012-07-18 13:52:24
 * License: Copyleft (ɔ) 2012. All rights reversed.
 *
 * This program is free software. It comes without any warranty, to
 * the extent permitted by applicable law. You can redistribute it
 * and/or modify it under the terms of the Do What The Fuck You Want
 * To Public License, Version 2, as published by Sam Hocevar. See
 * http://sam.zoy.org/wtfpl/COPYING for more details.
 */

package org.powerbot.game.api.methods.web.ge;

import com.google.common.base.Optional;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import javax.imageio.ImageIO;

import org.codehaus.jackson.JsonFactory;
import org.powerbot.game.api.methods.web.ge.api.GrandExchangeAPI;
import org.powerbot.game.api.methods.web.ge.api.RunescapeAPI;
import org.powerbot.game.api.methods.web.ge.item.GEItemInfo;
import org.powerbot.game.api.methods.web.ge.item.ItemInfo;

/**
 * The main class for interacting with different Grand Exchange APIs.
 * If one is interested in knowing which API will be used when using
 * a certain method, it is stated in the description for that method.
 * Operations currently supported is retrieval of item information,
 * free text search for items and retrieving thumbnails of items.
 * <p/>
 * Note that all members in the class are static, so there is no need
 * to create a new instance of the class. For more information on the
 * the APIs, see {@link GrandExchangeAPI} and {@link RunescapeAPI}.
 */
public final class GrandExchange {

    /** The {@code JsonFactory} used to create json parser instances. */
    private static final JsonFactory JSON_FACTORY = new JsonFactory();

    /**
     * Gets a {@code GEItemInfo} instance for the specified identification
     * value for an item. This is possible by making a request to the Grand
     * Exchange Item API, requesting the details of the item. To see what
     * information is included in the returned instance, see the details
     * of the {@link GEItemInfo} class. If no item has the specified value
     * as identification value or if the item is not being tracked by the
     * Grand Exchange, an absent optional instance will be returned.
     *
     * @param itemId the identification value of the item to use
     * @return the item information for the item with the specified value;
     *         or an absent optional instance if no information is available
     *         for the item
     * @throws IOException if there was an error while reading or parsing
     * @throws IllegalArgumentException if the specified value is below zero
     * @see #getItemInfo(int)
     */
    public static Optional<GEItemInfo> getGEItemInfo(final int itemId) throws IOException {
        return GrandExchangeAPI.getItemInfo(JSON_FACTORY, itemId);
    }

    /**
     * Gets an {@code ItemInfo} instance for the specified identification
     * value for an item. This is possible by making a request to unofficial
     * Runescape API, requesting the details of the item. To see what
     * information is included in the returned instance, see the details
     * of the {@link ItemInfo} class. If no item has the specified value
     * as identification value or if the item is not being tracked by the
     * Grand Exchange, an absent optional instance will be returned.
     * <p/>
     * The returned {@code ItemInfo} instance, if present, will be immutable.
     *
     * @param itemId the identification value of the item to use
     * @return the item information for the item with the specified value;
     *         or an absent optional instance if no information is available
     *         for the item
     * @throws IOException if there was an error while reading or parsing
     * @throws IllegalArgumentException if the specified value is below zero
     * @see #getItemInfo(int, int...)
     * @see #getGEItemInfo(int)
     */
    public static Optional<ItemInfo> getItemInfo(final int itemId) throws IOException {
        final List<ItemInfo> list = RunescapeAPI.getItemInfo(JSON_FACTORY, itemId);
        return list.isEmpty() ? Optional.<ItemInfo>absent() : Optional.of(list.get(0));
    }

    /**
     * Gets a list of {@code ItemInfo} instances for the specified identification
     * values. This is possible by making a request to the unofficial Runescape
     * API, requesting the details of the items. To see what information is
     * included in the returned instances, see the {@link ItemInfo} class.
     * <p/>
     * If no items have any of the specified values as identification value or
     * if none of the items are tracked by the Grand Exchange, an empty list
     * will be returned. This also means that any such invalid values will
     * be ignored by the API, and only valid items will be returned in the
     * response. The returned list will be immutable and all instances
     * placed in the returned list will also be immutable.
     *
     * @param itemId the first identification value to use in the request
     * @param itemIds optionally, any remaining identification values
     * @return a list of {@code ItemInfo} instances for the specified items
     * @throws IOException if there was an error while reading or parsing
     * @throws IllegalArgumentException if any specified value is below zero
     * @throws NullPointerException if {@code itemIds} is {@code null}
     */
    public static List<ItemInfo> getItemInfo(final int itemId, final int... itemIds) throws IOException {
        return RunescapeAPI.getItemInfo(JSON_FACTORY, itemId, itemIds);
    }

    /**
     * Gets a list of {@code ItemInfo} instances for the identification values
     * in the specified list. This is possible by making a request to the
     * unofficial Runescape API, requesting the details of the items. To
     * see what information is included in the returned instances, see
     * the {@link ItemInfo} class.
     * <p/>
     * If no items have any of the specified values as identification value or
     * if none of the items are tracked by the Grand Exchange, an empty list
     * will be returned. This also means that any such invalid values will
     * be ignored by the API, and only valid items will be returned in the
     * response. The returned list will be immutable and all instances
     * placed in the returned list will also be immutable.
     *
     * @param itemIds list of identification values to use in the request
     * @return a list of {@code ItemInfo} instances for the specified items
     * @throws IOException if there was an error while reading or parsing
     * @throws NullPointerException if {@code itemIds} is {@code null}
     * @throws IllegalArgumentException if the list is empty; or if any
     * specified identification value is below zero
     */
    public static List<ItemInfo> getItemInfo(final List<Integer> itemIds) throws IOException {
        return RunescapeAPI.getItemInfo(JSON_FACTORY, itemIds);
    }

    /**
     * Gets a list of {@code ItemInfo} instances for the identification values
     * in the specified array. This is possible by making a request to the
     * unofficial Runescape API, requesting the details of the items. To
     * see what information is included in the returned instances, see
     * the {@link ItemInfo} class.
     * <p/>
     * If no items have any of the specified values as identification value or
     * if none of the items are tracked by the Grand Exchange, an empty list
     * will be returned. This also means that any such invalid values will
     * be ignored by the API, and only valid items will be returned in the
     * response. The returned list will be immutable and all instances
     * placed in the returned list will also be immutable.
     *
     * @param itemIds array of identification values to use in the request
     * @return a list of {@code ItemInfo} instances for the specified items
     * @throws IOException if there was an error while reading or parsing
     * @throws NullPointerException if {@code itemIds} is {@code null}
     * @throws IllegalArgumentException if the array is empty; or if any
     * specified identification value is below zero
     */
    public static List<ItemInfo> getItemInfo(final int[] itemIds) throws IOException {
        return RunescapeAPI.getItemInfo(JSON_FACTORY, itemIds);
    }

    /**
     * Searches the Grand Exchange database for the specified {@code String},
     * using the unofficial Runescape API. To see what information is included
     * in the returned instance, see the {@link ItemInfo} class. If no items
     * match the provided search query, an empty list will be returned. The
     * returned list will be immutable and all instances placed in the
     * returned list will also be immutable.
     *
     * @param query the search query to search for in the request
     * @return a list containing information on the matching items
     * @throws IOException if there was an error while reading or parsing
     * @throws NullPointerException if the search query is {@code null}
     * @throws IllegalArgumentException if the search query is invalid
     */
    public static List<ItemInfo> searchItems(final String query) throws IOException {
        return RunescapeAPI.searchItems(JSON_FACTORY, query);
    }

    /**
     * Gets a thumbnail of the item with the specified identification value.
     * Even though the specified value is for an invalid item or for an item
     * not currently tracked by the Grand Exchange, a placeholder image will
     * be returned. See an <a href="http://rsapi.net/ge/thumbnail/-1">
     * example image</a> for how the placeholder image looks.
     * <p/>
     * Note: this method uses the unofficial {@link RunescapeAPI}.
     *
     * @param itemId the identification value of the item
     * @return a thumbnail image of the item with the specified value
     * @throws IOException if there was an error while reading the image
     * @throws IllegalArgumentException if the value is below zero
     */
    public static BufferedImage getThumbnail(final int itemId) throws IOException {
        return ImageIO.read(RunescapeAPI.Request.getThumbnailURL(itemId));
    }

    /**
     * The {@code GrandExchange} class should not be instantiated, since
     * all members are static and can be accessed without creating a new
     * instance. For this reason, the method will throw an assertion
     * error to prevent any accidental invocation.
     *
     * @throws AssertionError if the constructor is invoked
     */
    private GrandExchange() { throw new AssertionError(); }
}