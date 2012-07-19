/*
 * Filename: @(#)ItemInfo.java
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

/**
 * An interface describing some information regarding an item in the
 * Runescape world, including the price of the item. All items can be
 * identified using a unique identification value. Items also have a
 * name and a description. These properties are the information which
 * is represented by an {@code ItemInfo}.
 * <p/>
 * The interface is intended to describe item information which can be
 * retrieved from various public web resources, such as the official
 * Grand Exchange Item API. Of course, this does not limit the use of
 * the interface for other purposes.
 */
public interface ItemInfo {

    /**
     * Gets the unique identification value of the item which is
     * being represented by this item information instance. This
     * is a value which is greater than or equal to zero and can
     * be used to uniquely identify the item in the Grand Exchange
     * database, as well as various other contexts.
     *
     * @return the unique identification value of the item
     */
    public int getId();

    /**
     * Gets the name of the item which is being represented by this
     * item information instance. This is a {@code String} which is
     * not {@code null}, not empty and which holds the name of the
     * item as it appears in the Grand Exchange database. The name
     * should also match the name which is used in the game.
     *
     * @return the name of the item being represented
     */
    public String getName();

    /**
     * Gets the price of the item which is being represented by this
     * item information instance. The returned value is at all times
     * strictly greater than zero. The price is also the same price
     * which appears in the Grand Exchange database; assuming the
     * price has not changed since the price was retrieved.
     *
     * @return the price of the item being represented
     */
    public int getPrice();

    /**
     * Gets the description of the item being represented by this item
     * information instance. This is a {@code String} which is not that
     * of {@code null}, not empty and which holds a general description
     * regarding the item. The description should also match the examine
     * text, which is used in the game when a player chooses to examine
     * an item of this type.
     *
     * @return the description of the item being represented
     */
    public String getDescription();
}