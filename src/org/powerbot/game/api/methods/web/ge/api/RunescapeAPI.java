/*
 * Filename: @(#)RunescapeAPI.java
 * Last Modified: 2012-07-18 13:52:24
 * License: Copyleft (ɔ) 2012. All rights reversed.
 *
 * This program is free software. It comes without any warranty, to
 * the extent permitted by applicable law. You can redistribute it
 * and/or modify it under the terms of the Do What The Fuck You Want
 * To Public License, Version 2, as published by Sam Hocevar. See
 * http://sam.zoy.org/wtfpl/COPYING for more details.
 */

package org.powerbot.game.api.methods.web.ge.api;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import com.google.common.collect.ImmutableList;
import com.google.common.primitives.Ints;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collections;
import java.util.List;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;
import org.powerbot.game.api.methods.web.ge.GrandExchange;
import org.powerbot.game.api.methods.web.ge.item.DefaultItemInfo;
import org.powerbot.game.api.methods.web.ge.item.ItemInfo;

/**
 * Provides methods for interacting with the unofficial Runescape API.
 * The unofficial API provides methods for searching the Grand Exchange,
 * retrieving details about certain items and retrieving thumbnails
 * for items. Although the API has less details about the items compared
 * to the official Grand Exchange API, it can often be faster to use and
 * is less restrictive when it comes to usage.
 * <p/>
 * Because the API is not an official API, the information may or may not
 * be up to date, or not even accurate at all. Any guarantees should be
 * written on the <a href="http://rsapi.net">official website</a>.
 * <p/>
 * Note: use the {@link GrandExchange} class to get information from the
 * unofficial Runescape API. There should be no need to use this class
 * directly and using the mentioned class will sometimes be better
 * from a performance point of view.
 *
 * @see GrandExchangeAPI
 */
public final class RunescapeAPI {

    /**
     * Convenience class for creating addresses which are requests to the
     * unofficial Runescape API. All types of requests documented in the
     * API specification are supported and may be retrieved using the
     * static methods provided by the class. There is no reason to create
     * a new instance of the class, as all members are static.
     */
    public static final class Request {

        /** The name of the scheme on which the API is publicly accessible. */
        private static final String URL_SCHEME = "http";

        /** The name of the host on which the API is publicly accessible. */
        private static final String URL_HOST = "rsapi.net";

        /** The path on the host on which the API is publicly accessible. */
        private static final String URL_PATH = "/ge/";

        /**
         * The base address which locates where the Runescape API can be found.
         * This is the concatenation of the following ordered {@code String}s:
         * {@link #URL_SCHEME}, {@code "://"}, {@link #URL_HOST}, {@link #URL_PATH}.
         */
        private static final String URL_BASE = URL_SCHEME + "://" + URL_HOST + URL_PATH;

        /**
         * The partial address for the search service; append to {@link #URL_BASE}.
         * Append the search query which should be searched for in the request. At
         * last, append {@code ".format"} to complete the address.
         */
        private static final String URL_SERVICE_SEARCH = "search/";

        /**
         * The partial address for the item service; append to {@link #URL_BASE}.
         * Append the identification values of the items in a comma-separated list.
         * At last, append {@code ".format"} to complete the address.
         */
        private static final String URL_SERVICE_ITEM = "item/";

        /**
         * The partial address for the thumbnail service; append to {@link #URL_BASE}.
         * Append the identification value of the item for which to get the thumbnail.
         */
        private static final String URL_SERVICE_THUMBNAIL = "thumbnail/";

        /**
         * Gets an address which is a request to the unofficial Runescape API,
         * requesting the result of a search using the specified {@code String}.
         * Unfortunately, there seems to be no documentation on which characters
         * are valid to use in a search query. For this reason, if the specified
         * {@code String} contains non-ascii characters, they will be escaped.
         * Note that some invalid characters, like '(' and ')' won't be escaped
         * and are not supported by the service.
         *
         * @param query the search query to search for in the search request
         * @return an address which is a request to search using the search query
         * @throws NullPointerException if the search query is {@code null}
         * @throws IllegalArgumentException if the search query is empty; or if
         * it is not possible to create an {@code URI} instance with the query
         */
        public static URL getSearchUrl(final String query) {
            checkNotNull(query, "query null");
            checkArgument(!query.isEmpty(), "query empty");

            try {
                return new URI(URL_SCHEME, URL_HOST, URL_PATH + URL_SERVICE_SEARCH
                        + query + ".format", null).toURL();
            } catch(final URISyntaxException e) {
                throw new IllegalArgumentException(e);
            } catch(final MalformedURLException e) {
                throw new AssertionError(e); // Should never happen
            }
        }

        /**
         * Gets an address which is a request to the unofficial Runescape API,
         * requesting details of the items with the specified identification
         * values. No check is done to make sure that the specified values
         * are for valid items, except that the values must zero or above.
         * Note that if any duplicates are specified, they will not be removed.
         *
         * @param itemId the first identification value to use in the request
         * @param itemIds optionally, any remaining identification values
         * @return an address which is a request for details of the items
         * @throws IllegalArgumentException if any value is below zero
         * @throws NullPointerException if {@code itemIds} is {@code null}
         */
        public static URL getItemInfoUrl(final int itemId, final int... itemIds) {
            checkArgument(itemId >= 0, "invalid itemId (%s)", itemId);
            checkNotNull(itemIds, "itemIds null");

            for(int i = 0; i < itemIds.length; i++)
                checkArgument(itemIds[i] >= 0, "invalid itemIds[%s] (%s)", i, itemIds[i]);

            final StringBuilder sb = new StringBuilder(URL_BASE).append(URL_SERVICE_ITEM).append(itemId);
            for(int id : itemIds) sb.append(',').append(id);

            try {
                return new URL(sb.append(".format").toString());
            } catch(final MalformedURLException e) {
                throw new AssertionError(e); // Should never happen
            }
        }

        /**
         * Gets an address which is a request to the unofficial Runescape API,
         * requesting details of the items with the identification values in
         * the specified list. No check is done to make sure that the specified
         * values are for valid items, except that the values must zero or above.
         * Note that if any duplicates are specified, they will not be removed.
         *
         * @param itemIds list of identification values to use in the request
         * @return an address which is a request for details of the items
         * @throws NullPointerException if the list is that of {@code null}
         * @throws IllegalArgumentException if the list is empty; or if any
         * specified identification value is below zero
         */
        public static URL getItemInfoUrl(final List<Integer> itemIds) {
            checkNotNull(itemIds, "itemIds null");
            checkArgument(!itemIds.isEmpty(), "itemIds empty");
            for(int i = 0, c = itemIds.size(); i < c; i++)
                checkArgument(itemIds.get(i) >= 0, "invalid itemIds[%s] (%s)", i, itemIds.get(i));

            final StringBuilder sb = new StringBuilder(URL_BASE).append(URL_SERVICE_ITEM).append(itemIds.get(0));
            for(int i = 1, c = itemIds.size(); i < c; i++) sb.append(',').append(itemIds.get(i));

            try {
                return new URL(sb.append(".format").toString());
            } catch(final MalformedURLException e) {
                throw new AssertionError(e); // Should never happen
            }
        }

        /**
         * Gets an address which is a request to the unofficial Runescape API,
         * requesting details of the items with the identification values in
         * the specified array. No check is done to make sure that the specified
         * values are for valid items, except that the values must zero or above.
         * Note that if any duplicates are specified, they will not be removed.
         *
         * @param itemIds array of identification values to use in the request
         * @return an address which is a request for details of the items
         * @throws NullPointerException if the array is that of {@code null}
         * @throws IllegalArgumentException if the array is empty; or if any
         * specified identification value is below zero
         */
        public static URL getItemInfoUrl(final int[] itemIds) {
            return getItemInfoUrl(Ints.asList(itemIds));
        }

        /**
         * Gets an address which is a request to the unofficial Runescape API,
         * requesting the thumbnail image of the item with the specified value
         * as identification value. No check is done to make sure that the
         * specified value is for a valid item, except that the value must
         * be zero or above.
         * <p/>
         * The returned thumbnail image will use the {@code .png} format.
         *
         * @param itemId the identification value of the item
         * @return an address which is a request for the item's thumbnail
         * @throws IllegalArgumentException if the value is below zero
         */
        public static URL getThumbnailURL(final int itemId) {
            checkArgument(itemId >= 0, "invalid itemId (%s)", itemId);

            try {
                return new URL(URL_BASE + URL_SERVICE_THUMBNAIL + itemId);
            } catch(final MalformedURLException e) {
                throw new AssertionError(e); // Should never happen
            }
        }

        /**
         * The {@code Request} class should not be instantiated, since all
         * members are static and can be accessed without creating a new
         * instance. For this reason, the method will throw an assertion
         * error to prevent any accidental invocation.
         *
         * @throws AssertionError if the constructor is invoked
         */
        private Request() { throw new AssertionError(); }
    }

    /** The different parser modes supported by the {@link #parseItems} method. */
    private enum ParserMode {

        /** Parser mode which parses the response to a item detail request. */
        ITEM_INFO,

        /** Parser mode which parses the response to a search request. */
        SEARCH
    }

    /**
     * Parses items from the specified {@code JsonParser} instance. The mode
     * to use for parsing is determined by the specified {@code ParserMode}.
     * The returned list will be an immutable list containing immutable
     * {@code ItemInfo} instances, or an empty list if no matches was
     * found in the provided {@code JsonParser} instance.
     *
     * @param jp the json parser to use when parsing the items
     * @param pm the parser mode to use when parsing using the parser
     * @return an immutable list with immutable {@code ItemInfo} instances
     *         for the items in the specified json parser; or an empty
     *         immutable list if no items were found
     * @throws IOException if there was an error while reading or parsing
     */
    private static List<ItemInfo> parseItems(final JsonParser jp, final ParserMode pm) throws IOException {
        assert (jp != null && pm != null);

        final ImmutableList.Builder<ItemInfo> builder = new ImmutableList.Builder<>();
        boolean done = false;
        jp.nextToken();

        while(!done && jp.nextToken() != JsonToken.END_OBJECT) {
            final String tokenName = jp.getCurrentName();
            jp.nextToken();

            switch(tokenName) {
                case "error":
                    if(jp.getText().startsWith("No item(s)"))
                        return Collections.emptyList();
                    throw new IOException(jp.getText());
                case "items":
                    if(pm == ParserMode.ITEM_INFO) jp.nextToken();
                    while(jp.nextToken() == JsonToken.START_OBJECT) {
                        String name = null, description = null;
                        int id = 0, price = 0;

                        while(jp.nextToken() != JsonToken.END_OBJECT) {
                            final String fieldName = jp.getCurrentName();

                            jp.nextToken();
                            switch(fieldName) {
                                case "id": id = Integer.parseInt(jp.getText()); break;
                                case "name": name = jp.getText(); break;
                                case "price": price = GrandExchangeAPI.getPrice(jp.getText()); break;
                                case "description": description = jp.getText(); break;
                            }
                        }

                        builder.add(new DefaultItemInfo(id, name, price, description));
                        if(pm == ParserMode.ITEM_INFO) jp.nextToken();
                    }

                    done = true;
                    break;
            }
        }

        return builder.build();
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
     * @param jsonFactory the json factory to use when creating the parser
     * @param itemId the first identification value to use in the request
     * @param itemIds optionally, any remaining identification values
     * @return a list of {@code ItemInfo} instances for the specified items
     * @throws IOException if there was an error while reading or parsing
     * @throws IllegalArgumentException if any specified value is below zero
     * @throws NullPointerException if {@code jsonFactory} or {@code itemIds}
     * is {@code null}
     */
    public static List<ItemInfo> getItemInfo(final JsonFactory jsonFactory,
            final int itemId, final int... itemIds) throws IOException {

        checkNotNull(jsonFactory, "jsonFactory null");
        try(final JsonParser jp = jsonFactory.createJsonParser(Request.getItemInfoUrl(itemId, itemIds))) {
            return parseItems(jp, ParserMode.ITEM_INFO);
        }
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
     * @param jsonFactory the json factory to use when creating the parser
     * @param itemIds list of identification values to use in the request
     * @return a list of {@code ItemInfo} instances for the specified items
     * @throws IOException if there was an error while reading or parsing
     * @throws NullPointerException if {@code jsonFactory} or {@code itemIds}
     * is {@code null}
     * @throws IllegalArgumentException if the list is empty; or if any
     * specified identification value is below zero
     */
    public static List<ItemInfo> getItemInfo(final JsonFactory jsonFactory,
            final List<Integer> itemIds) throws IOException {

        checkNotNull(jsonFactory, "jsonFactory null");
        try(final JsonParser jp = jsonFactory.createJsonParser(Request.getItemInfoUrl(itemIds))) {
            return parseItems(jp, ParserMode.ITEM_INFO);
        }
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
     * @param jsonFactory the json factory to use when creating the parser
     * @param itemIds array of identification values to use in the request
     * @return a list of {@code ItemInfo} instances for the specified items
     * @throws IOException if there was an error while reading or parsing
     * @throws NullPointerException if {@code jsonFactory} or {@code itemIds}
     * is {@code null}
     * @throws IllegalArgumentException if the array is empty; or if any
     * specified identification value is below zero
     */
    public static List<ItemInfo> getItemInfo(final JsonFactory jsonFactory,
            final int[] itemIds) throws IOException {

        return getItemInfo(jsonFactory, Ints.asList(itemIds));
    }

    /**
     * Searches the Grand Exchange database for the specified {@code String},
     * using the unofficial Runescape API. To see what information is included
     * in the returned instance, see the {@link ItemInfo} class. If no items
     * match the provided search query, an empty list will be returned. The
     * returned list will be immutable and all instances placed in the
     * returned list will also be immutable.
     *
     * @param jsonFactory the json factory to use when creating the parser
     * @param query the search query to search for in the request
     * @return a list containing information on the matching items
     * @throws IOException if there was an error while reading or parsing
     * @throws NullPointerException if any parameter is {@code null}
     * @throws IllegalArgumentException if the search query is invalid
     */
    public static List<ItemInfo> searchItems(final JsonFactory jsonFactory,
            final String query) throws IOException {

        checkNotNull(jsonFactory, "jsonFactory null");
        try(final JsonParser jp = jsonFactory.createJsonParser(Request.getSearchUrl(query))) {
            return parseItems(jp, ParserMode.SEARCH);
        }
    }

    /**
     * The {@code RunescapeAPI} class should not be instantiated, since
     * all members are static and can be accessed without creating a new
     * instance. For this reason, the method will throw an assertion
     * error to prevent any accidental invocation.
     *
     * @throws AssertionError if the constructor is invoked
     */
    private RunescapeAPI() { throw new AssertionError(); }
}