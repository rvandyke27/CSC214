package com.csc214.rvandyke.assignment6application.model;

import android.content.Context;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Rebecca Van Dyke
 * rvandyke@u.rochester.edu
 * CSC 214 Assignment 8.5
 * TA:Julian Weiss
 */

public class Roster {
    private static Roster sRoster;
    private Context mAppContext;
    private Map<UUID, BhangraTeam> roster;

    private Roster(Context appContext){
        mAppContext = appContext;
        roster = new HashMap<>();
        //create list of teams
        addTeam("Rochester Bhangra", "Rochester, NY", "Over the past few years, Rochester Bhangra has established itself as a respected collegiate team on the bhangra circuit. We're known for our unique style as well as our unparalleled diversity.", 1998);
        addTeam("Shaan Mutiyaaran Di", "New York, NY", "Shaan Mutiyaaran Di is an all-female bhangra team based out of New York City. The team is a collaboration in the truest sense of the word: its dancers are alumni of collegiate teams spanning from Chicago to New York, that represent styles from the ultra-modern to the truly traditional.", 2008);
        addTeam("First Class Bhangra", "Pittsburgh, PA", "Over the years, we have all witnessed the evolution of bhangra as not only a means of entertainment, but as a graceful and beautiful form of art. We at First Class Bhangra desire to emulate this tradition. Fully engaged in our representation of not only a dance, but an artistic and unique component of our culture, we hope to inspire the community as it once inspired us as kids. " ,2009);
        addTeam("Duniya De Rang", "Online Collaboration", "Special collaboration team of competitive Bhangra dancers across the United States featuring unparalleled diversity.", 2017);
        addTeam("Shaan Punjab Dee", "Brampton, OT", "Traditional folk bhangra team on the rise from Brampton", 2013);
        addTeam("D.C. Bhangra Crew", "Washington, D.C.", "D.C. Bhangra Crew (DCBC) is an all-female Bhangra dance team representing the D.C. Metropolitan area. DCBC consists of first and second-generation students and individuals who seek to further explore the rich and vibrant culture of Punjab through the lens of performing arts. We aim to be an example of equality within a circuit that is traditionally male-dominated.", 2008);
        addTeam("Fauj Bhangra", "Boston, MA", "FAUJ was founded when ex-captains of various competitive bhangra teams joined forces to not only increase exposure of this Punjabi folk dance to the local Boston community but to leave a mark on the international competitive dance circuit as well. The collective dancing experience on FAUJ is immense and it has culminated in a distinct \"FAUJ\" identity. \"Fauj\", in Hindi/Punjabi, means \"army\" and we are just that - we are an army of brothers united by our passion for bhangra.", 2010);
        addTeam("Virginia Tech Bhangra", "Blacksburg, VA", "Virginia Tech Bhangra (VT Bhangra) strives to promote the culture of Punjab throughout the humble Blacksburg, Virginia community. This co-ed Bhangra team has adopted the mission of spreading the expression of Punjabi dance through numerous on-campus events and competitions. The team brings traditional dance moves and high energy to each performance, remaining true to the Hokie Nation.", 2006);
        addTeam("Da Real Punjabiz", "San Diego, CA", "Da Real Punjabiz stormed the bhangra scene and quickly became one of the most revered co-ed teams in California. Known for bringing fusion and creativity to its performances, DRP has become an icon of originality all over the country. In the 15 years since DRP was formed, the team has performed at numerous competitions all over the Unites States and Canada ", 1999);
        addTeam("Ministry of Bhangra", "Chicago, IL", "Independent co-ed bhangra team based out of Chicago, IL. ", 2012);
        addTeam("Buckeye Bhangra", "Columbus, OH", "Buckeye Bhangra, a dance group at The Ohio State University, strives to learn and present Bhangra, a lively form of folk music and dance, of the region of Punjab, India. We perform on campus as well as several competitions across the country.", 2010);
        addTeam("UNC Bhangra Elite", "Chapel Hill, NC", "Founded in 1996, Bhangra Elite has grown every year since its formation. Each year we want to outdo ourselves and this year is no different. ", 1996);
        addTeam("Tarey Panj Daryavan De", "Washington, D.C.", "Priding themselves in their strong cultural roots, tradition, and heritage, the gabhroos of Tarey Panj Daryavan De strive to leave their cultural mark in arenas across the great states. Formed in 2011, while the team is relatively young, a majority of veteran dancers have been compititively dancing for several years. The mission of T52D is to continue serving the South East Asian community in the D.C. Metropolitan area through a commitment to entertain, engage, and encourage through Bhangra.", 2011);
        addTeam("Virginia School of Bhangra", "Manassas, VA", "The Virginia School of Bhangra is a premier Punjabi folk dance academy located in Manassas, Virginia. The instructors have numerous years of experience in competitive collegiate Bhangra and now wish to instill the same values of culture, heritage, and tradition they learned while dancing.", 2014);
        addTeam("Cornell Bhangra", "Ithaca, NY", "Founded in 1997, Cornell Bhangra's goal is to promote awareness of Punjabi dance and culture in the community and across the nation. Bhangra is a folk dance originating in the state of Punjab in Northern India and Pakistan that celebrates the arrival of spring and everyday culture/life in Punjab.", 1997);
        addTeam("Anakh E Gabroo", "New York, NY", "Formed in 2007, Anakh-E-Gabroo (AEG) is an all-male independent Bhangra Team representing the tri-state area. Over the years, AEG has proven to be a strong proponent of community unity, Punjabi culture, and above all, the dance form of Bhangra.", 2007);
        addTeam("Alamo City Bhangra", "San Antonio, TX", "We want to promote the Punjabi folk dancing commonly known as Bhangra throughout UTSA and the San Antonio area. Also, to represent UTSA and its students at Bhangra competitions/performances and keeping Bhangra alive in Texas.", 2012);
        addTeam("Cal Bhangra", "Berkeley, CA", "Cal Bhangra was formed to promote awareness of the Punjabi Culture at UC Berkeley through the expression of traditional, folk Punjabi dance. By creating a group full of young, passionate Berkeley students, Cal Bhangra hopes to keep Bhangra alive at Cal.", 2010);
        addTeam("CMU Bhangra", "Pittsburgh, PA", "The purpose of CMU Bhangra is to provide opportunity and instruction for aspiring dancers. We seek to encourage, advance, and promote the art of Bhangra dancing (Punjabi folk dance) through various performances and competitions, on and off campus, and through instructive workshops.", 2010);
        addTeam("Columbia University Bhangra", "New York, NY", "Established in 2002, Columbia Bhangra is a competitive collegiate team that dances Bhangra- a dance form native to Punjab, India. By combining traditional and contemporary choreography and music, the team has been able to achieve an iconic style and energy. CU Bhangra has been an active force within the Bhangra circuit and Columbia community for many years, primarily due to its commitment to create fun and entertaining routines that foster an appreciation for Bhangra in both its dancers and audiences alike.", 2002);
    } //Roster()

    public static Roster get(Context c){
        if(sRoster == null){
            sRoster = new Roster(c);
        }
        return sRoster;
    } //get()

    public ArrayList<BhangraTeam> getRoster(){
        ArrayList<BhangraTeam> teams = new ArrayList<>(roster.size());
        teams.addAll(roster.values());
        Collections.sort(teams);
        return teams;
    } //getRoster()

    public BhangraTeam getTeam(UUID id){
        return roster.get(id);
    }

    private void addTeam(String name, String town, String description, int founding){
        BhangraTeam team = new BhangraTeam(name, town, description, founding);
        roster.put(team.getId(), team);
    } //getTeam()
} //end class Roster
