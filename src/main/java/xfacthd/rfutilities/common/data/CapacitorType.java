package xfacthd.rfutilities.common.data;

import java.util.Locale;

public enum CapacitorType
{
    THERMAL_BASIC       ("Basic Capacitor (Thermal Series)",        "thermal",  1_000_000,  2_000,   1_000),
    THERMAL_HARDENED    ("Hardened Capacitor (Thermal Series)",     "thermal",  4_000_000,  8_000,   4_000),
    THERMAL_REINFORCED  ("Reinforced Capacitor (Thermal Series)",   "thermal",  9_000_000, 18_000,   9_000),
    THERMAL_SIGNALUM    ("Signalum Capacitor (Thermal Series)",     "thermal", 16_000_000, 32_000,  16_000),
    THERMAL_RESONANT    ("Resonant Capacitor (Thermal Series)",     "thermal", 25_000_000, 50_000,  25_000),
    THERMAL_CREATIVE    ("Creative Capacitor (Thermal Series)",     "thermal",         -1,      0, 250_000),
    ENDERIO_BASIC       ("Basic Capacitor (EnderIO)",               "enderio", 0, 0, 0), //TODO: find proper values
    ENDERIO_DOUBLE      ("Double Capacitor (EnderIO)",              "enderio", 0, 0, 0), //TODO: find proper values
    ENDERIO_OCTADIC     ("Octadic Capacitor (EnderIO)",             "enderio", 0, 0, 0); //TODO: find proper values

    private final String name = toString().toLowerCase(Locale.ROOT);
    private final String regName = "capacitor_" + name;
    private final String friendlyName;
    private final String compatMod;
    private final boolean creative;
    private final int defaultCapacity;
    private final int defaultInput;
    private final int defaultOutput;

    CapacitorType(String friendlyName, String compatMod, int defaultCapacity, int defaultInput, int defaultOutput)
    {
        this.friendlyName = friendlyName;
        this.compatMod = compatMod;
        this.creative = defaultCapacity == -1;
        this.defaultCapacity = defaultCapacity;
        this.defaultInput = defaultInput;
        this.defaultOutput = defaultOutput;
    }

    public String getName() { return name; }

    public String getRegistryName() { return regName; }

    public String getFriendlyName() { return friendlyName; }

    public String getCompatMod() { return compatMod; }

    public boolean isCreative() { return creative; }

    public int getDefaultCapacity() { return defaultCapacity; }

    public int getDefaultInput() { return defaultInput; }

    public int getDefaultOutput() { return defaultOutput; }
}
