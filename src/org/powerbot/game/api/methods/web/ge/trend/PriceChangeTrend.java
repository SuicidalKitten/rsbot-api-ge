/*
 * Filename: @(#)PriceChangeTrend.java
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

import static com.google.common.base.Preconditions.checkNotNull;

import com.google.common.base.Objects;

import org.powerbot.game.api.methods.web.ge.GrandExchange;
import org.powerbot.game.api.methods.web.ge.item.GEItemInfo;

/**
 * Representation of a price change, in actual game money, coupled with a
 * {@code Trend} for an unknown item in the Grand Exchange. By unknown item,
 * it means that the class does not hold any association to a certain item.
 * The class is merely a trend with a change of the item's price.
 * <p/>
 * All created instances from this class are immutable. It's important
 * to note that one normally should not create instances of this class
 * oneself, but instead use the public static methods provided by the
 * {@link GrandExchange} class to retrieve a {@link GEItemInfo} which
 * in turn holds a instance of the class.
 *
 * @see PercentChangeTrend
 * @see PriceTrend
 */
public final class PriceChangeTrend {

    /** The trend of the price change; can be negative, neutral or positive. */
    private final Trend trend;

    /** The price change of the unknown item to which the trend applies. */
    private final int priceChange;

    /**
     * Creates a new {@code PriceTrend} using the specified {@code Trend}
     * and price change. Both parameters are required; the trend cannot be
     * that of {@code null} but there are no restrictions on the change
     * in price.
     *
     * @param trend the trend for the price change of the unknown item
     * @param priceChange the change in price of the item
     * @throws NullPointerException if the specified trend is {@code null}
     */
    public PriceChangeTrend(final Trend trend, final int priceChange) {
        checkNotNull(trend, "trend null");
        this.trend = trend;
        this.priceChange = priceChange;
    }

    /**
     * Gets the {@code Trend} for the price change of the unknown item.
     * This is the trend for the price change of the item to which the
     * trend applies. The returned trend is guaranteed to never be that
     * of {@code null}.
     *
     * @return the trend for the price change of the unknown item
     */
    public Trend getTrend() {
        return trend;
    }

    /**
     * Gets the price change of the item to which the price trend applies.
     * Since price changes can have any sign and very large magnitudes, no
     * restrictions are applied on the change in price.
     *
     * @return the price change of the item to which the price trend applies
     */
    public int getPriceChange() {
        return priceChange;
    }

    /** {@inheritDoc} */
    @Override
    public boolean equals(final Object o) {
        if(this == o) return true;
        if(!(o instanceof PriceChangeTrend))
            return false;

        final PriceChangeTrend p = (PriceChangeTrend)o;
        return trend == p.trend && priceChange == p.priceChange;
    }

    /** {@inheritDoc} */
    @Override
    public int hashCode() {
        return Objects.hashCode(trend, priceChange);
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        return Objects.toStringHelper(this).add("trend", trend).
                add("priceChange", priceChange).toString();
    }
}