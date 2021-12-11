package de.swa.rest.posts;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Schema(enumeration = { "#FF6633", "#00B3E6", "#E6B333", "#3366E6", "#999966", "#80B300", "#6680B3", "#66991A", "#FF1A66", "#E6331A", "#66994D", "#4D8000", "#B33300", "#66664D", "#991AFF", "#4DB3FF", "#33991A", "#4D8066", "#FF3380", "#CCCC00", "#4D80CC", "#9900B3", "#E64D66", "#4DB380", "#FF4D4D", "#FF6633", "#00B3E6", "#E6B333", "#3366E6", "#999966", "#80B300", "#6680B3", "#66991A", "#FF1A66", "#E6331A", "#66994D", "#4D8000", "#B33300", "#66664D", "#991AFF", "#4DB3FF", "#33991A", "#4D8066", "#FF3380", "#CCCC00", "#4D80CC", "#9900B3", "#E64D66", "#4DB380", "#FF4D4D", "#FF6633", "#00B3E6", "#E6B333", "#3366E6", "#999966", "#80B300", "#6680B3", "#66991A", "#FF1A66", "#E6331A", "#66994D", "#4D8000", "#B33300", "#66664D", "#991AFF", "#4DB3FF", "#33991A", "#4D8066", "#FF3380", "#CCCC00", "#4D80CC", "#9900B3", "#E64D66", "#4DB380", "#FF4D4D" })
public enum Colors {
    HEX_FF6633("#FF6633"),
    HEX_00B3E6("#00B3E6"),
    HEX_E6B333("#E6B333"),
    HEX_3366E6("#3366E6"),
    HEX_999966("#999966"),
    HEX_80B300("#80B300"),
    HEX_6680B3("#6680B3"),
    HEX_66991A("#66991A"),
    HEX_FF1A66("#FF1A66"),
    HEX_E6331A("#E6331A"),
    HEX_66994D("#66994D"),
    HEX_4D8000("#4D8000"),
    HEX_B33300("#B33300"),
    HEX_66664D("#66664D"),
    HEX_991AFF("#991AFF"),
    HEX_4DB3FF("#4DB3FF"),
    HEX_33991A("#33991A"),
    HEX_4D8066("#4D8066"),
    HEX_FF3380("#FF3380"),
    HEX_CCCC00("#CCCC00"),
    HEX_4D80CC("#4D80CC"),
    HEX_9900B3("#9900B3"),
    HEX_E64D66("#E64D66"),
    HEX_4DB380("#4DB380"),
    HEX_FF4D4D("#FF4D4D"),
    ;

    private final String color;

    Colors(final String colorRaw) {
        this.color = colorRaw;
    }

    @Override
    public String toString() {
        return color;
    }


    @JsonValue
    public String getValue() {
        return this.color;
    }

    @JsonCreator
    public static Colors create(String val) {
        Colors[] colors = Colors.values();
        for (Colors c : colors) {
            if (c.getValue().equalsIgnoreCase(val)) {
                return c;
            }
        }
        return HEX_FF6633;
    }
}