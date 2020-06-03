package com.craftle_mod.common.resource;

/**
 * An interface to represent a resource class for Craftle.
 * <p>
 * Every Resource must contain a name and a method of acquiring the name.
 */
public interface ICraftleResource {

    public String getResourceName();
}
