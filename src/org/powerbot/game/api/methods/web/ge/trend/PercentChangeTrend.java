/*
 * Filename: @(#)PercentChangeTrend.java
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
 * Representation of a price change in percent coupled with a {@code Trend}
 * for an unknown item in the Grand Exchange. By unknown item, it means that
 * the class does not hold any association to a certain item. The class is
 * merely a trend with a change in percent of the item's price.
 * <p/>
 * All created instances from this class are immutable. It's important
 * to note that one normally should not create instances of this class
 * oneself, but instead use the public static methods provided by the
 * {@link GrandExchange} class to retrieve a {@link GEItemInfo} which
 * in turn holds a instance of the class.
 *
 * @see PriceChangeTrend
 * @see PriceTrend
 */
public final class PercentChangeTrend {

    /** The trend of the price change percentage; negative, neutral or positive. */
    private final Trend trend;

    /** The price change in percent; magnitude or sign is not limited. */
    private final double percentChange;

    /**
     * Creates a new {@code PercentChangeTrend} using the specified change in
     * percent for an item's price and the specified {@code Trend}. Note that
     * percentage values should be specified so that a value of one is equal
     * to 100 %. In other words, specifying a value of 100 will be equivalent
     * of specifying 10 000 %.
     *
     * @param trend the trend for the the price change percentage
     * @param percentChange the price change in percent for an item
     * @throws NullPointerException if the specified trend is {@code null}
     */
    public PercentChangeTrend(final Trend trend, final double percentChange) {
        checkNotNull(trend, "trend null");
        this.trend = trend;
        this.percentChange = percentChange;
    }

    /**
     * Gets the {@code Trend} for the price change percentage. The
     * returned trend is guaranteed to never be that of {@code null}.
     *
     * @return the trend for the price change percentage
     */
    public Trend getTrend() {
        return trend;
    }

    /**
     * Gets the change in percent for an item's price. The returned
     * value will be a percentage so that a value of one will be the
     * same as 100 % and a value of 100 will be equivalent to the
     * amount of 10 000 %.
     *
     * @return the change in percent for an item's price
     */
    public double getPercentChange() {
        return percentChange;
    }

    /** {@inheritDoc} */
    @Override
    public boolean equals(final Object o) {
        if(this == o) return true;
        if(!(o instanceof PercentChangeTrend))
            return false;

        final PercentChangeTrend p = (PercentChangeTrend)o;
        return trend == p.trend && Double.compare(percentChange, p.percentChange) == 0;
    }

    /** {@inheritDoc} */
    @Override
    public int hashCode() {
        return Objects.hashCode(trend, percentChange);
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        return Objects.toStringHelper(this).add("trend", trend).
                add("percentChange", percentChange).toString();
    }
}