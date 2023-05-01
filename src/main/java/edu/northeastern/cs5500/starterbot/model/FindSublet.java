package edu.northeastern.cs5500.starterbot.model;

import java.awt.Color;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.dv8tion.jda.api.EmbedBuilder;

/*
 * FindSublet is a public class containing the information for a SubletListing singleton for finding a sublet.
 *
 * Contains the SubletListing instance, an embedbuilder, and methods necessary
 * to build the subletlisting instance depending on user responses to /find
 */
@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FindSublet {
    // Private constructor for the FIndSublet singleton
    private static final FindSublet instance = new FindSublet();
    // Private constant title for embed, a String
    private static final String TITLE = "Sublet listing details:";

    // SubletLisying contained in the findsublet needs to be accessible
    private SubletListing subletListing = new SubletListing();

    // Embed builder to be built as more information is stored
    private EmbedBuilder embedBuilder = new EmbedBuilder().setColor(Color.ORANGE).setTitle(TITLE);
    // Constructor to access the instance of the subletlisting
    public static FindSublet getInstance() {
        return instance;
    }
    // Function to add fields to the embed builder
    public void addField(String name, String value, boolean inline) {
        embedBuilder.addField(name, value, inline);
    }

    // Function to reset the singleton in case a new find needs to be made
    // and there is already an instance of findSublet.
    public void reset() {
        subletListing = new SubletListing();
        embedBuilder = new EmbedBuilder().setColor(Color.ORANGE).setTitle(TITLE);
    }

    // Returns a temporary embed
    public EmbedBuilder returnTempEmbed() {
        return new EmbedBuilder(this.embedBuilder);
    }
}
