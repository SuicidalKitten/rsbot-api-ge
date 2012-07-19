/*
 * Filename: @(#)Category.java
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

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableMap;

import java.util.Map;

/**
 * An enumeration of the categories specified in the Catalogue API, which is
 * a part of the Grand Exchange APIs. Categories are either represented by an
 * identification value or a unique name; both of which the enumeration can
 * handle. See {@link #fromId(int)} and {@link #fromName(String)} on how to
 * get a {@code Category} constant from an identification value or name.
 */
public enum Category {

    /** Category listed in the Catalogue API specification under the name 'Miscellaneous'. */
    MISCELLANEOUS(0, "Miscellaneous"),

    /** Category listed in the Catalogue API specification under the name 'Ammo'. */
    AMMO(1, "Ammo"),

    /** Category listed in the Catalogue API specification under the name 'Arrows'. */
    ARROWS(2, "Arrows"),

    /** Category listed in the Catalogue API specification under the name 'Bolts'. */
    BOLTS(3, "Bolts"),

    /** Category listed in the Catalogue API specification under the name 'Construction materials'. */
    CONSTRUCTION_MATERIALS(4, "Construction materials"),

    /** Category listed in the Catalogue API specification under the name 'Construction projects'. */
    CONSTRUCTION_PROJECTS(5, "Construction projects"),

    /** Category listed in the Catalogue API specification under the name 'Cooking ingredients'. */
    COOKING_INGREDIENTS(6, "Cooking ingredients"),

    /** Category listed in the Catalogue API specification under the name 'Costumes'. */
    COSTUMES(7, "Costumes"),

    /** Category listed in the Catalogue API specification under the name 'Crafting materials'. */
    CRAFTING_MATERIALS(8, "Crafting materials"),

    /** Category listed in the Catalogue API specification under the name 'Familiars'. */
    FAMILIARS(9, "Familiars"),

    /** Category listed in the Catalogue API specification under the name 'Farming produce'. */
    FARMING_PRODUCE(10, "Farming produce"),

    /** Category listed in the Catalogue API specification under the name 'Fletching materials'. */
    FLETCHING_MATERIALS(11, "Fletching materials"),

    /** Category listed in the Catalogue API specification under the name 'Food and drink'. */
    FOOD_AND_DRINK(12, "Food and drink"),

    /** Category listed in the Catalogue API specification under the name 'Herblore materials'. */
    HERBLORE_MATERIALS(13, "Herblore materials"),

    /** Category listed in the Catalogue API specification under the name 'Hunting equipment'. */
    HUNTING_EQUIPMENT(14, "Hunting equipment"),

    /** Category listed in the Catalogue API specification under the name 'Hunting produce'. */
    HUNTING_PRODUCE(15, "Hunting produce"),

    /** Category listed in the Catalogue API specification under the name 'Jewellery'. */
    JEWELLERY(16, "Jewellery"),

    /** Category listed in the Catalogue API specification under the name 'Mage armour'. */
    MAGE_ARMOUR(17, "Mage armour"),

    /** Category listed in the Catalogue API specification under the name 'Mage weapons'. */
    MAGE_WEAPONS(18, "Mage weapons"),

    /** Category listed in the Catalogue API specification under the name 'Melee armour - low level'. */
    MELEE_ARMOUR_LOW_LEVEL(19, "Melee armour - low level"),

    /** Category listed in the Catalogue API specification under the name 'Melee armour - mid level'. */
    MELEE_ARMOUR_MID_LEVEL(20, "Melee armour - mid level"),

    /** Category listed in the Catalogue API specification under the name 'Melee armour - high level'. */
    MELEE_ARMOUR_HIGH_LEVEL(21, "Melee armour - high level"),

    /** Category listed in the Catalogue API specification under the name 'Melee weapons - low level'. */
    MELEE_WEAPONS_LOW_LEVEL(22, "Melee weapons - low level"),

    /** Category listed in the Catalogue API specification under the name 'Melee weapons - mid level'. */
    MELEE_WEAPONS_MID_LEVEL(23, "Melee weapons - mid level"),

    /** Category listed in the Catalogue API specification under the name 'Melee weapons - high level'. */
    MELEE_WEAPONS_HIGH_LEVEL(24, "Melee weapons - high level"),

    /** Category listed in the Catalogue API specification under the name 'Mining and smithing'. */
    MINING_AND_SMITHING(25, "Mining and smithing"),

    /** Category listed in the Catalogue API specification under the name 'Potions'. */
    POTIONS(26, "Potions"),

    /** Category listed in the Catalogue API specification under the name 'Prayer armour'. */
    PRAYER_ARMOUR(27, "Prayer armour"),

    /** Category listed in the Catalogue API specification under the name 'Prayer materials'. */
    PRAYER_MATERIALS(28, "Prayer materials"),

    /** Category listed in the Catalogue API specification under the name 'Range armour'. */
    RANGE_ARMOUR(29, "Range armour"),

    /** Category listed in the Catalogue API specification under the name 'Range weapons'. */
    RANGE_WEAPONS(30, "Range weapons"),

    /** Category listed in the Catalogue API specification under the name 'Runecrafting'. */
    RUNECRAFTING(31, "Runecrafting"),

    /** Category listed in the Catalogue API specification under the name 'Runes, Spells and Teleports'. */
    RUNES_SPELLS_AND_TELEPORTS(32, "Runes, Spells and Teleports"),

    /** Category listed in the Catalogue API specification under the name 'Seeds'. */
    SEEDS(33, "Seeds"),

    /** Category listed in the Catalogue API specification under the name 'Summoning scrolls'. */
    SUMMONING_SCROLLS(34, "Summoning scrolls"),

    /** Category listed in the Catalogue API specification under the name 'Tools and containers'. */
    TOOLS_AND_CONTAINERS(35, "Tools and containers"),

    /** Category listed in the Catalogue API specification under the name 'Woodcutting product'. */
    WOODCUTTING_PRODUCT(36, "Woodcutting product");

    /** Map containing mappings of type (category id -> {@code Category}). */
    private static final Map<Integer, Category> MAP_FROM_ID;

    /** Map containing mappings of type (category name -> {@code Category}). */
    private static final Map<String, Category> MAP_FROM_NAME;

    /* Initialize the two maps holding identification value and name mappings. */
    static {
        final ImmutableMap.Builder<Integer, Category> builderId = new ImmutableMap.Builder<>();
        for(final Category category : values())
            builderId.put(category.id, category);
        MAP_FROM_ID = builderId.build();

        final ImmutableMap.Builder<String, Category> builderName = new ImmutableMap.Builder<>();
        for(final Category category : values())
            builderName.put(category.name, category);
        MAP_FROM_NAME = builderName.build();
    }

    /**
     * Gets the {@code Category} enumeration constant for the category with
     * the specified identification value. If no enumeration constant could
     * be found for the value, an absent optional instance will be returned.
     * Note that specifying a value below zero will always result in an
     * absent optional instance being returned.
     *
     * @param id the identification value of the category to retrieve
     * @return the category enumeration constant with the specified value
     *         as identification value; or an absent optional instance if
     *         no such constant exists
     */
    public static Optional<Category> fromId(final int id) {
        return Optional.fromNullable(MAP_FROM_ID.get(id));
    }

    /**
     * Gets the {@code Category} enumeration constant for the category with
     * the specified name. Note that the specified name must exactly match
     * the name of a enumeration constant, as returned by the {@code getName}
     * method, including the casing of the name. If no constant could be
     * found for the value, an absent optional instance is returned.
     *
     * @param name the name of the category to retrieve
     * @return the category enumeration constant with the specified name;
     *         or an absent optional instance if no such constant exists
     * @throws NullPointerException if the specified name is {@code null}
     */
    public static Optional<Category> fromName(final String name) {
        return Optional.fromNullable(MAP_FROM_NAME.get(name));
    }

    /** The unique identification value of the category; as specified in the API. */
    private final int id;

    /** The unique name of the category; as specified in the API. */
    private final String name;

    /**
     * Creates a new {@code Category} using the specified identification value
     * and name. Identification values are currently limited to a value in the
     * range of 0 and 36 (both inclusive). The name must be non-null and also
     * non-empty. Both of the specified values should always match what is
     * written in the Catalogue API specification.
     *
     * @param id the identification value of the category
     * @param name the name of the category; casing/formatting preserved
     */
    private Category(final int id, final String name) {
        assert (0 <= id && id <= 36);
        assert (name != null && !name.isEmpty());

        this.id = id;
        this.name = name;
    }

    /**
     * Gets the identification value of the category. This is the value used
     * in order to uniquely identify a single category when working with the
     * Grand Exchange APIs. To get a {@code Category} instance when only a
     * identification value is present, use {@link #fromId(int)}.
     * <p/>
     * The identification value is always greater than or equal to zero.
     *
     * @return the unique identification value of the category
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the name of the category. This is the unique name of the category,
     * which can be used to uniquely identify a single category when working
     * with the Grand Exchange APIs. Since some interaction with the API only
     * deals with category names, consider using {@link #fromName(String)} to
     * retrieve a {@code Category} instance when only the name of the category
     * is available.
     * <p/>
     * The name is guaranteed to be a non-null, non-empty {@code String}.
     *
     * @return the unique name of the category
     */
    public String getName() {
        return name;
    }
}