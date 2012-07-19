/*
 * Filename: @(#)GrandExchangeAPI.java
 * Last Modified: 2012-07-17 12:07:23
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

import com.google.common.base.Optional;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;
import org.powerbot.game.api.methods.web.ge.GrandExchange;
import org.powerbot.game.api.methods.web.ge.item.Category;
import org.powerbot.game.api.methods.web.ge.item.GEItemInfo;
import org.powerbot.game.api.methods.web.ge.trend.PercentChangeTrend;
import org.powerbot.game.api.methods.web.ge.trend.PriceChangeTrend;
import org.powerbot.game.api.methods.web.ge.trend.PriceTrend;
import org.powerbot.game.api.methods.web.ge.trend.Trend;

/**
 * Provides methods for interacting with the official Grand Exchange
 * APIs. There are two somewhat related APIs; the Catalogue API and
 * the Item API.  The Item API is what provides information, such as
 * pricing and trends, regarding the items available in the database.
 * The Catalogue API provides a way, similarly to browsing a catalogue,
 * to list and view the items in the Grand Exchange.
 * <p/>
 * Note: use the {@link GrandExchange} class to get information from the
 * official Grand Exchange API. There should be no need to use this class
 * directly and using the mentioned class will sometimes be better from a
 * performance point of view.
 *
 * @see RunescapeAPI
 */
public final class GrandExchangeAPI {

    /**
     * Convenience class for creating addresses which are requests to the
     * different Grand Exchange APIs. All types of requests documented in
     * the API specification are supported and may be retrieved using the
     * static methods provided by the class. There is no reason to create
     * a new instance of the class, as all members are static.
     */
    public static final class Request {

        /** The base address which locates where the Grand Exchange APIs can be found. */
        private static final String URL_BASE = "http://services.runescape.com/m=itemdb_rs/api/";

        /** The partial address for the graph service; append to {@link #URL_BASE}. */
        private static final String URL_SERVICE_GRAPH = "graph/";

        /** The partial address for the catalogue service; append to {@link #URL_BASE}. */
        private static final String URL_SERVICE_CATALOGUE = "catalogue/";

        /**
         * The partial address for the item info request; should be appended to the
         * {@link #URL_SERVICE_CATALOGUE}. Also append the identification value of
         * the item for which details are to be requested.
         */
        private static final String URL_REQUEST_DETAIL = "detail.json?item=";

        /**
         * The partial address for the category info request; should be appended to
         * the {@link #URL_SERVICE_CATALOGUE}. Also append the identification value
         * of the category for which details are to be requested.
         */
        private static final String URL_REQUEST_CATEGORY = "category.json?category=";

        /**
         * The partial address for the category price request; should be appended to
         * the {@link #URL_SERVICE_CATALOGUE}. Append the identification value of the
         * category, followed by {@code "&amp;alpha="} and the starting letter. Finally
         * append {@code "&amp;page="} followed by the page number, starting on one.
         */
        private static final String URL_REQUEST_ITEMS = "items.json?category=";

        /**
         * Gets an address which is a request to the Grand Exchange Item API,
         * requesting details of the item with the specified identification
         * value. No check is done to make sure that the specified value is
         * for a valid item, except that the value must be zero or above.
         * <p/>
         * Note: the returned request is for the service labeled as "GE Item
         * price information" in the specification for the Grand Exchange
         * Item API.
         *
         * @param itemId the identification value of the item to use
         * @return an address, which is a request, to the Grand Exchange Item API
         * @throws IllegalArgumentException if the specified value is below zero
         */
        public static URL getItemInfoUrl(final int itemId) {
            checkArgument(itemId >= 0, "invalid itemId (%s)", itemId);

            try {
                return new URL(URL_BASE + URL_SERVICE_CATALOGUE + URL_REQUEST_DETAIL + itemId);
            } catch(final MalformedURLException e) {
                throw new AssertionError(e); // Should never happen
            }
        }

        /**
         * Gets an address which is a request to the Grand Exchange Item API,
         * requesting graphing data for the item with the specified value as
         * identification value. No check is done to make sure that the value
         * is for a valid item, except that the value must be zero or above.
         * <p/>
         * Note: the returned request is for the service labeled as "Graphing
         * Data" in the specification for the Grand Exchange Item API.
         *
         * @param itemId the identification value of the item to use
         * @return an address, which is a request, to the Grand Exchange Item API
         * @throws IllegalArgumentException if the specified value is below zero
         */
        public static URL getItemGraphDataUrl(final int itemId) {
            checkArgument(itemId >= 0, "invalid itemId (%s)", itemId);

            try {
                return new URL(URL_BASE + URL_SERVICE_GRAPH + itemId + ".json");
            } catch(final MalformedURLException e) {
                throw new AssertionError(e); // Should never happen
            }
        }

        /**
         * Gets an address which is a request to the Grand Exchange Catalogue
         * API, requesting information details about the specified category.
         * Note that the returned request is for the service labeled as
         * "Category information details" in the specification for the
         * Grand Exchange Catalogue API.
         *
         * @param category the category to which the request applies
         * @return an address, which is a request, to the Catalogue API
         * @throws NullPointerException if the category is {@code null}
         */
        public static URL getCategoryInfoUrl(final Category category) {
            checkNotNull(category, "category null");

            try {
                return new URL(URL_BASE + URL_SERVICE_CATALOGUE + URL_REQUEST_CATEGORY + category.getId());
            } catch(final MalformedURLException e) {
                throw new AssertionError(e); // Should never happen
            }
        }

        /**
         * Gets an address which is a request to the Grand Exchange Catalogue
         * API, requesting price details about the specified category, and for
         * the items starting with the specified letter. For each page, twelve
         * items are returned in the response. The first page has number one
         * and if there are more than twelve items starting with the letter
         * in the category, the items from 13 and onward will be available
         * on the following pages.
         * <p/>
         * Note: the returned request is for the service labeled as "Category
         * price details" in the specification for the  Grand Exchange
         * Catalogue API.
         *
         * @param category the category to which the request applies
         * @param alpha the first letter of the items to get in the category
         * @param page the page number to get, starting on page one
         * @return an address which is a request to the Catalogue API
         * @throws NullPointerException if the category is {@code null}
         * @throws IllegalArgumentException if the {@code alpha} character is
         * not a letter; or if the page number is zero or below
         */
        public static URL getCategoryPriceUrl(final Category category, final char alpha, final int page) {
            checkNotNull(category, "category null");
            checkArgument(Character.isLetter(alpha), "invalid alpha (%s)", alpha);
            checkArgument(page > 0, "invalid page (%s)", page);

            try {
                return new URL(URL_BASE + URL_SERVICE_CATALOGUE + URL_REQUEST_ITEMS +
                        category.getId() + "&alpha=" + alpha + "&page=" + page);
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

    /**
     * Gets the price specified in the provided {@code String} from the
     * Grand Exchange API. Prices may use suffixes such as 'k' to denote
     * the price is in thousands, 'm' for millions or 'b' for billions.
     * This method will return the actual price without any suffixes.
     *
     * @param s the string representation of the price to retrieve
     * @return the price in the specified string, without suffixes
     * @throws NumberFormatException if unable to parse the string
     */
    static int getPrice(String s) {
        assert (s != null && !s.isEmpty());
        s = s.replaceAll("[^0-9-\\.kmb]", "");

        int multiple;
        switch(s.substring(s.length() - 1)) {
            case "k": multiple = 1000; break;
            case "m": multiple = 1000000; break;
            case "b": multiple = 1000000000; break;
            default: return Integer.parseInt(s);
        }

        return (int)(Double.parseDouble(s.substring(0, s.length() - 1)) * multiple);
    }

    /**
     * Gets a {@code PriceTrend} from the specified json parser instance.
     * Assumes that the start of the object to represent as a price trend
     * has already been read and that the object's fields now can be read.
     *
     * @param jp the json parser instance to use when extracting
     * @return a price trend representation of the parsers current object
     * @throws IOException if there was an error while reading or parsing
     */
    private static PriceTrend getPriceTrend(final JsonParser jp) throws IOException {
        assert (jp != null);

        Trend trend = null;
        int price = 0;

        while(jp.nextToken() != JsonToken.END_OBJECT) {
            final String tokenName = jp.getCurrentName();

            jp.nextToken();
            switch(tokenName) {
                case "trend": trend = Trend.fromName(jp.getText()).get(); break;
                case "price": price = getPrice(jp.getText()); break;
            }
        }

        return new PriceTrend(trend, price);
    }

    /**
     * Gets a {@code PriceChangeTrend} from the specified json parser instance.
     * Assumes that the start of the object to represent as a price change trend
     * has already been read and that the object's fields now can be read.
     *
     * @param jp the json parser instance to use when extracting
     * @return a price change trend representation of the parsers current object
     * @throws IOException if there was an error while reading or parsing
     */
    private static PriceChangeTrend getPriceChangeTrend(final JsonParser jp) throws IOException {
        assert (jp != null);

        Trend trend = null;
        int price = 0;

        while(jp.nextToken() != JsonToken.END_OBJECT) {
            final String tokenName = jp.getCurrentName();

            jp.nextToken();
            switch(tokenName) {
                case "trend": trend = Trend.fromName(jp.getText()).get(); break;
                case "price": price = getPrice(jp.getText()); break;
            }
        }

        return new PriceChangeTrend(trend, price);
    }

    /**
     * Gets a {@code PercentChangeTrend} from the specified json parser instance.
     * Assumes that the start of the object to represent as a percent change trend
     * has already been read and that the object's fields now can be read.
     *
     * @param jp the json parser instance to use when extracting
     * @return a percent change trend representation of the parsers current object
     * @throws IOException if there was an error while reading or parsing
     */
    private static PercentChangeTrend getPercentChangeTrend(final JsonParser jp) throws IOException {
        assert (jp != null);

        Trend trend = null;
        double price = 0D;

        while(jp.nextToken() != JsonToken.END_OBJECT) {
            final String tokenName = jp.getCurrentName();

            jp.nextToken();
            switch(tokenName) {
                case "trend":
                    trend = Trend.fromName(jp.getText()).get();
                    break;
                case "change":
                    price = Double.parseDouble(jp.getText().replaceAll("[^0-9-\\.]", "")) / 100D;
                    break;
            }
        }

        return new PercentChangeTrend(trend, price);
    }

    /**
     * Gets a {@code GEItemInfo} instance for the specified identification
     * value for an item. This is possible by making a request to the Grand
     * Exchange Item API, requesting the details of the item. To see what
     * information is included in the returned instance, see the details
     * of the {@link GEItemInfo} class. If no item has the specified value
     * as identification value or if the item is not being tracked by the
     * Grand Exchange, an absent optional instance will be returned.
     *
     * @param jsonFactory the json factory to use when creating the parser
     * @param itemId the identification value of the item to use
     * @return the item information for the item with the specified value;
     *         or an absent optional instance if no information is available
     *         for the item
     * @throws IOException if there was an error while reading or parsing
     * @throws NullPointerException if the json factory is {@code null}
     * @throws IllegalArgumentException if the specified value is below zero
     */
    public static Optional<GEItemInfo> getItemInfo(final JsonFactory jsonFactory, final int itemId) throws IOException {
        checkNotNull(jsonFactory, "jsonFactory null");

        /* Create a new JsonParser which requests the information and reads the response. */
        try(final JsonParser jp = jsonFactory.createJsonParser(Request.getItemInfoUrl(itemId))) {

            /* Skip the first few tokens; they denote that it's an item object. */
            for(int i = 0; i < 4; i++) jp.nextToken();

            /* Initialize the variables, to hold the extracted information, with their default values. */
            String name = null, description = null, iconUrl = null, largeIconUrl = null, typeIconUrl = null;
            PercentChangeTrend day30ChangeTrend = null, day90ChangeTrend = null, day180ChangeTrend = null;
            PriceChangeTrend todaysChangeTrend = null;
            PriceTrend currentPriceTrend = null;
            boolean membersOnly = false;
            Category type = null;
            int id = 0;

            /* Go through the different tokens and extract the information. */
            while(true) {

                /* Get the name of the current token; stop when it is missing. */
                final String tokenName = jp.getCurrentName();
                if(tokenName == null)
                    break;

                /* Decide how to treat the token value depending on its name. */
                jp.nextToken();
                switch(tokenName) {
                    case "icon": iconUrl = jp.getText(); break;
                    case "icon_large": largeIconUrl = jp.getText(); break;
                    case "id": id = jp.getValueAsInt(); break;
                    case "type": type = Category.fromName(jp.getText()).get(); break;
                    case "typeIcon": typeIconUrl = jp.getText(); break;
                    case "name": name = jp.getText(); break;
                    case "description": description = jp.getText(); break;
                    case "current": currentPriceTrend = getPriceTrend(jp); break;
                    case "today": todaysChangeTrend = getPriceChangeTrend(jp); break;
                    case "day30": day30ChangeTrend = getPercentChangeTrend(jp); break;
                    case "day90": day90ChangeTrend = getPercentChangeTrend(jp); break;
                    case "day180": day180ChangeTrend = getPercentChangeTrend(jp); break;
                    case "members": membersOnly = Boolean.valueOf(jp.getText()); break;
                    default: break;
                }

                jp.nextToken();
            }

            /* Create a new GEItemInfo instance, wrapped as an optional instance, using the extracted information. */
            return Optional.of(new GEItemInfo(id, name, type, description, membersOnly, iconUrl, largeIconUrl,
                    typeIconUrl, currentPriceTrend, todaysChangeTrend, day30ChangeTrend, day90ChangeTrend,
                    day180ChangeTrend));

        } catch(final FileNotFoundException e) {
            return Optional.absent();
        }
    }

    /**
     * The {@code GrandExchangeAPI} class should not be instantiated, since
     * all members are static and can be accessed without creating a new
     * instance. For this reason, the method will throw an assertion
     * error to prevent any accidental invocation.
     *
     * @throws AssertionError if the constructor is invoked
     */
    private GrandExchangeAPI() { throw new AssertionError(); }
}