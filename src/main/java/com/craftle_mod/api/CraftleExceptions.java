package com.craftle_mod.api;

public class CraftleExceptions {

    public static class CraftleTileEntityException extends Exception {

        public CraftleTileEntityException(String message) {
            super(message);
        }
    }

    public static class CraftleContainerException extends Exception {

        public CraftleContainerException(String s) {
            super(s);
        }
    }
}
