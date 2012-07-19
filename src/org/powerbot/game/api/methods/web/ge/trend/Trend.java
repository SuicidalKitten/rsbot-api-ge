/*
 * Filename: @(#)Trend.java
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

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableMap;

import java.util.Map;

/**
 * An enumeration of the possible trends for changes/prices when dealing with
 * the Grand Exchange APIs. Trends can be either negative, meaning something
 * is decreasing; neutral, meaning something is stable; or positive, meaning
 * something is increasing. When working with the API and only receiving
 * the name of a trend, consider using using the static
 * {@link #fromName(String)} method.
 */
public enum Trend {

    /** Denotes that the item's price/change trend is negative; it is decreasing. */
    NEGATIVE(-1, "negative"),

    /** Denotes that the item's price/change trend is neutral; it is stable. */
    NEUTRAL(0, "neutral"),

    /** Denotes that the item's price/change trend is positive; it is increasing. */
    POSITIVE(1, "positive");

    /** Map containing mappings of type (trend name -> {@code Trend}). */
    private static final Map<String, Trend> MAP_FROM_NAME;

    /* Initialize the map containing mappings for trend names. */
    static {
        final ImmutableMap.Builder<String, Trend> builder = new ImmutableMap.Builder<>();
        for(final Trend trend : values())
            builder.put(trend.name, trend);
        MAP_FROM_NAME = builder.build();
    }

    /**
     * Gets the {@code Trend} enumeration constant for the trend with
     * the specified name. Note that the specified name must exactly
     * match the name of a enumeration constant, as returned by the
     * {@code getName} method. If no constant could be found for
     * the value, an absent optional instance is returned.
     *
     * @param name the name of the trend to retrieve
     * @return the trend enumeration constant with the specified name;
     *         or an absent optional instance if no such constant exists
     * @throws NullPointerException if the specified name is {@code null}
     */
    public static Optional<Trend> fromName(final String name) {
        return Optional.fromNullable(MAP_FROM_NAME.get(name));
    }

    /** The unique name of the trend as it is specified in the API. */
    private final String name;

    /** The signum of the trend; -1 for negative, 0 for neutral and 1 for positive. */
    private final int signum;

    /**
     * Creates a new {@code Trend} using the specified {@code String} as
     * the unique name of the trend. The name should match the name used
     * in the API and must also not be {@code null} and not empty. The
     * signum value denotes the signum of the trend; -1 for negative,
     * 0 for neutral and +1 for positive.
     *
     * @param signum the signum of the trend
     * @param name the unique name of the trend
     */
    private Trend(final int signum, final String name) {
        assert (signum == 0 || Math.abs(signum) == 1);
        assert (name != null && !name.isEmpty());

        this.signum = signum;
        this.name = name;
    }

    /**
     * Gets the unique name of the trend as it appears when using the Grand
     * Exchange APIs. The name may be used to identify a single trend among
     * other possible trends. If only given the name of a trend, consider
     * using the {@link #fromName(String)} method to get the associated
     * enumeration constant for the trend with the same name.
     * <p/>
     * The name is guaranteed to be a non-null, non-empty {@code String}.
     *
     * @return the unique name of the trend
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the signum of the trend which is the signum representing
     * the trend. The value returned will be -1 for a negative trend,
     * 0 for a neutral trend and +1 for a positive trend.
     *
     * @return the signum of the trend
     */
    public int getSignum() {
        return signum;
    }
}