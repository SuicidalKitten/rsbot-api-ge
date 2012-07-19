/*
 * Filename: @(#)PriceTrend.java
 * Last Modified: 2012-07-18 13:36:01
 * License: Copyleft (ɔ) 2012. All rights reversed.
 *
 * This program is free software. It comes without any warranty, to
 * the extent permitted by applicable law. You can redistribute it
 * and/or modify it under the terms of the Do What The Fuck You Want
 * To Public License, Version 2, as published by Sam Hocevar. See
 * http://sam.zoy.org/wtfpl/COPYING for more details.
 */

package org.powerbot.game.api.methods.web.ge.trend;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import com.google.common.base.Objects;

import org.powerbot.game.api.methods.web.ge.GrandExchange;
import org.powerbot.game.api.methods.web.ge.item.GEItemInfo;

/**
 * Representation of a price coupled with a {@code Trend} for an unknown
 * item in the Grand Exchange. By unknown item, it means that the class
 * does not hold any association to a certain item. The class is merely
 * a trend with a price of an item.
 * <p/>
 * All created instances from this class are immutable. It's important
 * to note that one normally should not create instances of this class
 * oneself, but instead use the public static methods provided by the
 * {@link GrandExchange} class to retrieve a {@link GEItemInfo} which
 * in turn holds a instance of the class.
 *
 * @see PercentChangeTrend
 * @see PriceChangeTrend
 */
public final class PriceTrend {

    /** The trend of the price; can be negative, neutral or positive. */
    private final Trend trend;

    /** The price of the unknown item to which the trend applies. */
    private final int price;

    /**
     * Creates a new {@code PriceTrend} using the specified {@code Trend}
     * and price. Both parameters are required; the trend cannot be that
     * of {@code null} and the price must be a value above zero.
     *
     * @param trend the trend for the price of the unknown item
     * @param price the price of the item to which the trend applies
     * @throws NullPointerException if the specified trend is {@code null}
     * @throws IllegalArgumentException if the price is below or equal to zero
     */
    public PriceTrend(final Trend trend, final int price) {
        checkNotNull(trend, "trend null");
        checkArgument(price > 0, "invalid price (%s)", price);

        this.trend = trend;
        this.price = price;
    }

    /**
     * Gets the {@code Trend} for the price of the unknown item. This
     * is the trend for the price of the item to which the trend applies.
     * The returned trend is guaranteed to never be that of {@code null}.
     *
     * @return the trend for the price of the unknown item
     */
    public Trend getTrend() {
        return trend;
    }

    /**
     * Gets the price of the item to which the price trend applies. Since
     * prices cannot be negative and also not be zero, or at least not in
     * the Grand Exchange, the returned value is guaranteed to be a
     * positive value, greater than zero.
     *
     * @return the price of the item to which the price trend applies
     */
    public int getPrice() {
        return price;
    }

    /** {@inheritDoc} */
    @Override
    public boolean equals(final Object o) {
        if(this == o) return true;
        if(!(o instanceof PriceTrend))
            return false;

        final PriceTrend p = (PriceTrend)o;
        return trend == p.trend && price == p.price;
    }

    /** {@inheritDoc} */
    @Override
    public int hashCode() {
        return Objects.hashCode(trend, price);
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        return Objects.toStringHelper(this).add("trend", trend).
                add("price", price).toString();
    }
}