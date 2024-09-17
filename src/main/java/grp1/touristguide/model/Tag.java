package grp1.touristguide.model;

public enum Tag {
    HISTORIC("Historic"),
    FAMILY_FRIENDLY("Family-Friendly"),
    NATURE("Nature"),
    ADVENTURE("Adventure"),
    CULTURAL("Cultural"),
    RELAXING("Relaxing");

    private final String displayName;

    Tag(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
