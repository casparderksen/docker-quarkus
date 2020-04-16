package org.acme.util.adapter.mapstruct;

import java.util.UUID;

/**
 * Custom Mapstruct mapper between UUID and String.
 */
public class UUIDMapper {

    public String asString(UUID uuid) {
        if (uuid == null)
            return null;
        return uuid.toString();
    }

    public UUID asUUID(String uuid) {
        if (uuid == null)
            return null;
        return UUID.fromString(uuid);
    }
}
