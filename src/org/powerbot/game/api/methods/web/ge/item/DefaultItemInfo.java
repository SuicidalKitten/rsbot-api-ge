/*
 * Filename: @(#)DefaultItemInfo.java
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

import org.powerbot.game.api.methods.web.ge.GrandExchange;

/**
 * A default implementation of the {@code ItemInfo} interface. The
 * class holds information, returned from some web resource, such as
 * an API, regarding a certain item.
 * <p/>
 * All instances created from this class are immutable. It's important
 * to note that one normally should not create instances of this class
 * oneself, but instead use the public static methods provided by the
 * {@link GrandExchange} class.
 */
public final class DefaultItemInfo implements ItemInfo {

    /** The identification value of the item being represented. */
    private final int id;

    /** The name of the item being represented, as it appears in the game. */
    private final String name;

    /** The price of the item as it once appeared in the Grand Exchange. */
    private final int price;

    /** The description of the item, which is also the examine text. */
    private final String description;

    /**
     * Creates a new {@code DefaultItemInfo} instance using the provided
     * identification value, item name, price and item description, which
     * is also the examine text of the item when being examined in game.
     *
     * @param id the identification value of the item being represented
     * @param name the name of the item, as it appears in the game
     * @param price the price of the item
     * @param description the description of the item
     * @throws IllegalArgumentException if the identification value is below
     * zero; or the price is below one; or if any {@code String} is empty
     * @throws NullPointerException if any parameter is {@code null}
     */
    public DefaultItemInfo(final int id, final String name, final int price, final String description) {
        checkArgument(id >= 0, "invalid id (%s)", id);

        checkNotNull(name, "name null");
        checkArgument(!name.isEmpty(), "name empty");

        checkArgument(price > 0, "invalid price (%s)", price);

        checkNotNull(description, "description null");
        checkArgument(!description.isEmpty(), "description empty");

        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
    }

    /** {@inheritDoc} */
    public int getId() {
        return id;
    }

    /** {@inheritDoc} */
    public String getName() {
        return name;
    }

    /** {@inheritDoc} */
    public int getPrice() {
        return price;
    }

    /** {@inheritDoc} */
    public String getDescription() {
        return description;
    }

    /** {@inheritDoc} */
    @Override
    public boolean equals(final Object o) {
        if(this == o) return true;
        if(!(o instanceof DefaultItemInfo))
            return false;

        final DefaultItemInfo i = (DefaultItemInfo)o;
        return id == i.id && price == i.price && name.equals(i.name)
                && description.equals(i.description);
    }

    /** {@inheritDoc} */
    @Override
    public int hashCode() {
        return Objects.hashCode(id, name, price, description);
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        return Objects.toStringHelper(this).add("id", id).add("name", name).
                add("price", price).add("description", description).toString();
    }
}