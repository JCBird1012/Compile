package compile_inc.compile;

/**
 * Created by DUCA on 7/15/2014.
 */
public class ImportsFromTemboo {
    string twitterKey;
    string twitterSecret;
    //twitter
    InitializeOAuth initializeOAuthChoreo = new InitializeOAuth(session);

    // Get an InputSet object for the choreo
    InitializeOAuthInputSet initializeOAuthInputs = initializeOAuthChoreo.newInputSet();

// Set inputs
    initializeOAuthInputs.set_ConsumerSecret("");
    initializeOAuthInputs.set_ConsumerKey("");

    // Execute Choreo
    InitializeOAuthResultSet initializeOAuthResults = initializeOAuthChoreo.execute(initializeOAuthInputs);
//Generate authorization URL that user needs to go to to approve twitter usage
//Callback id + oauth token secret also generated for finalize oauth

    FinalizeOAuth finalizeOAuthChoreo = new FinalizeOAuth(session);

    // Get an InputSet object for the choreo
    FinalizeOAuthInputSet finalizeOAuthInputs = finalizeOAuthChoreo.newInputSet();

// Set inputs
    //callback id + oauth token from first step
    finalizeOAuthInputs.set_CallbackID("");
    finalizeOAuthInputs.set_OAuthTokenSecret("");
    finalizeOAuthInputs.set_ConsumerSecret("");
    finalizeOAuthInputs.set_ConsumerKey("");

    // Execute Choreo
    FinalizeOAuthResultSet finalizeOAuthResults = finalizeOAuthChoreo.execute(finalizeOAuthInputs);
//access token + access token secret generated from this step


    ListFriends listFriendsChoreo = new ListFriends(session);

    // Get an InputSet object for the choreo
    ListFriendsInputSet listFriendsInputs = listFriendsChoreo.newInputSet();

// Set inputs
//uses access token + secret from before
    listFriendsInputs.set_ScreenName("");
    listFriendsInputs.set_AccessToken("");
    listFriendsInputs.set_AccessTokenSecret("");
    listFriendsInputs.set_ConsumerSecret("");
    listFriendsInputs.set_ConsumerKey("");

    // Execute Choreo
    ListFriendsResultSet listFriendsResults = listFriendsChoreo.execute(listFriendsInputs);
//we get a friends/following list
//list is in json so we need to extract and match up the name for a contact
//then extract the id of the person's the twitter id that will be used in the next step
//probably should put ids into an array

//used to get the latest tweet from people you are following
    Show showChoreo = new Show(session);

    // Get an InputSet object for the choreo
    ShowInputSet showInputs = showChoreo.newInputSet();

// Set inputs
    showInputs.set_AccessToken("");
    showInputs.set_AccessTokenSecret("");
    showInputs.set_ConsumerSecret("");
//for user id use array that was maybe made from last step
    showInputs.set_UserId("");
    showInputs.set_ConsumerKey("");

    // Execute Choreo
    ShowResultSet showResults = showChoreo.execute(showInputs);
//Response is JSon "text" is the text of a tweet
//can also get profile image from response

}