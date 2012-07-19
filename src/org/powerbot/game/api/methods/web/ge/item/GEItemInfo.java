/*
 * Filename: @(#)GEItemInfo.java
 * Last Modified: 2012-07-18 13:36:01
 * License: Copyleft (ɔ) 2012. All rights reversed.
 *
 * This program is free software. It comes without any warranty, to
 * the extent permitted by applicable law. You can redistribute it
 * and/or modify it under the terms of the Do What The Fuck You Want
 * To Public License, Version 2, as published by Sam Hocevar. See
 * http://sam.zoy.org/wtfpl/COPYING for more details.
 */

package org.powerbot.game.api.methods.web.ge.item;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import com.google.common.base.Objects;

import java.net.MalformedURLException;
import java.net.URL;

import org.powerbot.game.api.methods.web.ge.GrandExchange;
import org.powerbot.game.api.methods.web.ge.trend.PercentChangeTrend;
import org.powerbot.game.api.methods.web.ge.trend.PriceChangeTrend;
import org.powerbot.game.api.methods.web.ge.trend.PriceTrend;
import org.powerbot.game.api.methods.web.ge.trend.Trend;

/**
 * Holds the information returned by the Grand Exchange service regarding
 * a certain item. All information in the response is represented by the
 * class. When it comes to the various changes in prices, the class uses
 * different trend classes, all relying on the {@link Trend} enumeration.
 * <p/>
 * All instances created from this class are immutable. It's important
 * to note that one normally should not create instances of this class
 * oneself, but instead use the public static methods provided by the
 * {@link GrandExchange} class.
 *
 * @see PriceTrend
 * @see PriceChangeTrend
 * @see PercentChangeTrend
 */
public final class GEItemInfo implements ItemInfo {

    /** The identification value of the item being represented. */
    private final int id;

    /** The name of the item being represented, as it appears in the game. */
    private final String name;

    /** The category which the item belongs to according to the Grand Exchange. */
    private final Category type;

    /** The description of the item, which is also the examine text for the item. */
    private final String description;

    /** Denotes whether this item is available to members only; or all players. */
    private final boolean membersOnly;

    /** The address to where an icon image of the item can be retrieved. */
    private final URL iconUrl;

    /** The address to where a larger icon image of the item can be retrieved. */
    private final URL largeIconUrl;

    /** The address to where an icon image of the item's category can be retrieved. */
    private final URL typeIconUrl;

    /** The current price trend for the item being represented. */
    private final PriceTrend currentPriceTrend;

    /** Today's price change trend for the item being represented. */
    private final PriceChangeTrend todaysChangeTrend;

    /** The 30-day percent change trend for the item being represented. */
    private final PercentChangeTrend day30ChangeTrend;

    /** The 90-day percent change trend for the item being represented. */
    private final PercentChangeTrend day90ChangeTrend;

    /** The 180-day percent change trend for the item being represented. */
    private final PercentChangeTrend day180ChangeTrend;

    /**
     * Creates a new {@code GEItemInfo} instance using the specified parameters.
     * Since information regarding all parameters are returned in a request to
     * the Grand Exchange service, they are all required to be specified. This
     * is also why the traditional way of creating an instance is used, despite
     * the large number of parameters.
     * <p/>
     * In general, no parameter can be {@code null}, no {@code String} may be
     * empty and no numeric value may be below zero. Note that URLs which are
     * represented using {@code String}s are not verified to be a http-address,
     * but an exception will be thrown if it in fact cannot be used to create a
     * new {@code URL} instance.
     *
     * @param id the identification value of the item being represented
     * @param name the name of the item being represented, as it appears in the game
     * @param type the category which the item belongs to according to the Grand Exchange
     * @param description the description of the item, which also is the examine text
     * @param membersOnly denotes whether this item is available to members only
     * @param iconUrl the address to where an icon image of the item can be retrieved
     * @param largeIconUrl the address to where a larger icon image can be retrieved
     * @param typeIconUrl the address to where an icon image of the category can be retrieved
     * @param currentPriceTrend the current price trend for the item being represented
     * @param todaysChangeTrend today's price trend for the item being represented
     * @param day30ChangeTrend the 30-day change trend for the item being represented
     * @param day90ChangeTrend the 90-day change trend for the item being represented
     * @param day180ChangeTrend the 180-day change trend for the item being represented
     * @throws NullPointerException if any specified parameter is {@code null}
     * @throws IllegalArgumentException if the identification value is below zero;
     * or if any specified {@code String} is empty
     * @throws MalformedURLException if any specified URL is not a valid {@code URL}
     */
    public GEItemInfo(final int id, final String name, final Category type, final String description,
            final boolean membersOnly, final String iconUrl, final String largeIconUrl, final String
            typeIconUrl, final PriceTrend currentPriceTrend, final PriceChangeTrend todaysChangeTrend,
            final PercentChangeTrend day30ChangeTrend, final PercentChangeTrend day90ChangeTrend,
            final PercentChangeTrend day180ChangeTrend) throws MalformedURLException {

        checkArgument(id >= 0, "invalid id (%s)", id);

        checkNotNull(name, "name null");
        checkArgument(!name.isEmpty(), "name empty");

        checkNotNull(type, "type null");

        checkNotNull(description, "description null");
        checkArgument(!description.isEmpty(), "description empty");

        checkNotNull(iconUrl, "iconUrl null");
        checkArgument(!iconUrl.isEmpty(), "iconUrl empty");

        checkNotNull(largeIconUrl, "largeIconUrl null");
        checkArgument(!largeIconUrl.isEmpty(), "largeIconUrl empty");

        checkNotNull(typeIconUrl, "typeIconUrl null");
        checkArgument(!typeIconUrl.isEmpty(), "typeIconUrl empty");

        checkNotNull(currentPriceTrend, "currentPriceTrend null");
        checkNotNull(todaysChangeTrend, "todaysChangeTrend null");
        checkNotNull(day30ChangeTrend, "day30ChangeTrend null");
        checkNotNull(day90ChangeTrend, "day90ChangeTrend null");
        checkNotNull(day180ChangeTrend, "day180ChangeTrend null");

        this.id = id;
        this.name = name;
        this.type = type;
        this.description = description;
        this.membersOnly = membersOnly;
        this.iconUrl = new URL(iconUrl);
        this.largeIconUrl = new URL(largeIconUrl);
        this.typeIconUrl = new URL(typeIconUrl);
        this.currentPriceTrend = currentPriceTrend;
        this.todaysChangeTrend = todaysChangeTrend;
        this.day30ChangeTrend = day30ChangeTrend;
        this.day90ChangeTrend = day90ChangeTrend;
        this.day180ChangeTrend = day180ChangeTrend;
    }

    /** {@inheritDoc} */
    public int getId() {
        return id;
    }

    /** {@inheritDoc} */
    public String getName() {
        return name;
    }

    /**
     * {@inheritDoc} Note that the invocation of this method will always
     * be equivalent to the the following invocation on the instance.
     * <blockquote>
     * {@code getCurrentPriceTrend().getPrice()}
     * </blockquote>
     */
    public int getPrice() {
        return currentPriceTrend.getPrice();
    }

    /** {@inheritDoc} */
    public String getDescription() {
        return description;
    }

    /**
     * Gets the category which the item belongs to according to the
     * Grand Exchange. The returned instance is guaranteed to not be
     * that of {@code null}.
     *
     * @return the category to which the item belongs
     */
    public Category getType() {
        return type;
    }

    /**
     * Gets whether the item is exclusively available to members only.
     * If that is the case, {@code true} will be returned. If the item
     * is available to all players, {@code false} will be returned.
     *
     * @return {@code true} if the item is only available to members;
     *         {@code false} if the item can be used by all players
     */
    public boolean isMembersOnly() {
        return membersOnly;
    }

    /**
     * Gets the address to where an icon image of the item can be retrieved.
     * The returned {@code URL} is guaranteed to not be {@code null}. It is
     * however not verified to actually be a valid http-address, which may
     * be necessary to check at a later date.
     *
     * @return the address to an icon image of the item
     */
    public URL getIconUrl() {
        return iconUrl;
    }

    /**
     * Gets the address to where a larger icon image of the item can be
     * retrieved. The returned {@code URL} is guaranteed to not be that
     * of {@code null}. It is however not verified to actually be a valid
     * http-address, which may be necessary to check at a later date.
     *
     * @return the address to a larger icon image of the item
     */
    public URL getLargeIconUrl() {
        return largeIconUrl;
    }

    /**
     * Gets the address to where an icon image of the item's category can be
     * retrieved. The returned {@code URL} is guaranteed to not be that of
     * {@code null}. It is however not verified to actually be a valid http-
     * address, which may be necessary to check at a later date.
     *
     * @return the address to an icon image of the item's category
     */
    public URL getTypeIconUrl() {
        return typeIconUrl;
    }

    /**
     * Gets the current price trend for the item being represented.
     * The returned instance holds the current price of the item as
     * well as the trend for the price. The returned instance is
     * also guaranteed to not be {@code null}.
     *
     * @return the current price trend for the item
     * @see PriceTrend
     */
    public PriceTrend getCurrentPriceTrend() {
        return currentPriceTrend;
    }

    /**
     * Gets today's price change trend for the item being represented.
     * The returned instance holds the change in price, using the in
     * game money as unit, and the trend for the price change. The
     * returned instance is guaranteed to not be {@code null}.
     *
     * @return today's price change trend for the item
     * @see PriceChangeTrend
     */
    public PriceChangeTrend getTodaysChangeTrend() {
        return todaysChangeTrend;
    }

    /**
     * Gets the 30-day percent change trend for the item being represented.
     * The returned instance holds the price change in percent for the last
     * 30-day period as well as the trend for this value. The returned
     * instance is guaranteed to not be {@code null}.
     *
     * @return the 30-day percent change trend for the item
     * @see PercentChangeTrend
     */
    public PercentChangeTrend get30DayChangeTrend() {
        return day30ChangeTrend;
    }

    /**
     * Gets the 90-day percent change trend for the item being represented.
     * The returned instance holds the price change in percent for the last
     * 90-day period as well as the trend for this value. The returned
     * instance is guaranteed to not be {@code null}.
     *
     * @return the 90-day percent change trend for the item
     * @see PercentChangeTrend
     */
    public PercentChangeTrend get90DayChangeTrend() {
        return day90ChangeTrend;
    }

    /**
     * Gets the 180-day percent change trend for the item being represented.
     * The returned instance holds the price change in percent for the last
     * 180-day period as well as the trend for this value. The returned
     * instance is guaranteed to not be {@code null}.
     *
     * @return the 180-day percent change trend for the item
     * @see PercentChangeTrend
     */
    public PercentChangeTrend get180DayChangeTrend() {
        return day180ChangeTrend;
    }

    /**
     * Gets today's price change of the represented item. This method is
     * a convenience method; the result of an invocation will always be
     * equivalent of the following invocation on the instance.
     * <blockquote>
     * {@code getTodaysChangeTrend().getPriceChange()}
     * </blockquote>
     *
     * @return todays price change of the represented item
     */
    public int getPriceChange() {
        return todaysChangeTrend.getPriceChange();
    }

    /** {@inheritDoc} */
    @Override
    public int hashCode() {
        return Objects.hashCode(id, name, type, description, membersOnly, iconUrl, largeIconUrl,
                typeIconUrl, currentPriceTrend, todaysChangeTrend, day30ChangeTrend,
                day90ChangeTrend, day180ChangeTrend);
    }

    /** {@inheritDoc} */
    @Override
    public boolean equals(final Object o) {
        if(this == o) return true;
        if(!(o instanceof GEItemInfo))
            return false;

        final GEItemInfo i = (GEItemInfo)o;
        return id == i.id && name.equals(i.name) && type == i.type && description.equals(i.description) &&
                membersOnly == i.membersOnly && iconUrl.equals(i.iconUrl) && largeIconUrl.equals(i.largeIconUrl) &&
                typeIconUrl.equals(i.typeIconUrl) && currentPriceTrend.equals(i.currentPriceTrend) &&
                todaysChangeTrend.equals(i.todaysChangeTrend) && day30ChangeTrend.equals(i.day30ChangeTrend) &&
                day90ChangeTrend.equals(i.day90ChangeTrend) && day180ChangeTrend.equals(i.day180ChangeTrend);
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        return Objects.toStringHelper(this).add("id", id).add("name", name).add("type", type).
                add("description", description).add("membersOnly", membersOnly).add("iconUrl", iconUrl).
                add("largeIconUrl", largeIconUrl).add("typeIconUrl", typeIconUrl).add("currentPriceTrend",
                currentPriceTrend).add("todaysChangeTrend", todaysChangeTrend).add("30DayChangeTrend",
                day30ChangeTrend).add("90DayChangeTrend", day90ChangeTrend).add("180DayChangeTrend",
                day180ChangeTrend).toString();
    }
}