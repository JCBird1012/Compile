package compile_inc.compile;

import com.temboo.Library.CloudMine.ObjectStorage.ObjectGet;
import com.temboo.Library.Facebook.Reading.Friends;
import com.temboo.Library.Facebook.Reading.Statuses;
import com.temboo.Library.Google.Contacts.GetAllContacts;
import com.temboo.Library.Google.OAuth.InitializeOAuth;
import com.temboo.Library.Twitter.FriendsAndFollowers.ListFriends;
import com.temboo.Library.Twitter.OAuth.FinalizeOAuth;
import com.temboo.Library.Twitter.Users.Show;
import com.temboo.core.TembooSession;

/**
 * Created by DUCA on 7/15/2014.
 */
public class ImportsFromTemboo {
//Get keys and such from cloudmine
    TembooSession session = new TembooSession("brentlee", "contacts", "2cf6355976c84b91ac9d656e9caeddef");

    ObjectGet objectGetChoreo = new ObjectGet(session);

    // Get an InputSet object for the choreo
    ObjectGet.ObjectGetInputSet objectGetInputs = objectGetChoreo.newInputSet();

// Set inputs
    objectGetInputs.set_APIKey("");
    objectGetInputs.set_ApplicationIdentifier("");

    // Execute Choreo
    ObjectGet.ObjectGetResultSet objectGetResults = objectGetChoreo.execute(objectGetInputs);

//below enter all keys and such that we will need
    string twitterKey;
    string twitterSecret;

//twitter
    InitializeOAuth initializeOAuthChoreo = new InitializeOAuth(session);

    // Get an InputSet object for the choreo
    InitializeOAuth.InitializeOAuthInputSet initializeOAuthInputs = initializeOAuthChoreo.newInputSet();

// Set inputs
    initializeOAuthInputs.set_ConsumerSecret("");
    initializeOAuthInputs.set_ConsumerKey("");

    // Execute Choreo
    InitializeOAuth.InitializeOAuthResultSet initializeOAuthResults = initializeOAuthChoreo.execute(initializeOAuthInputs);
//Generate authorization URL that user needs to go to to approve twitter usage
//Callback id + oauth token secret also generated for finalize oauth

    FinalizeOAuth finalizeOAuthChoreo = new FinalizeOAuth(session);

    // Get an InputSet object for the choreo
    FinalizeOAuth.FinalizeOAuthInputSet finalizeOAuthInputs = finalizeOAuthChoreo.newInputSet();

// Set inputs
    //callback id + oauth token from first step
    finalizeOAuthInputs.set_CallbackID("");
    finalizeOAuthInputs.set_OAuthTokenSecret("");
    finalizeOAuthInputs.set_ConsumerSecret("");
    finalizeOAuthInputs.set_ConsumerKey("");

    // Execute Choreo
    FinalizeOAuth.FinalizeOAuthResultSet finalizeOAuthResults = finalizeOAuthChoreo.execute(finalizeOAuthInputs);
//access token + access token secret generated from this step


    ListFriends listFriendsChoreo = new ListFriends(session);

    // Get an InputSet object for the choreo
    ListFriends.ListFriendsInputSet listFriendsInputs = listFriendsChoreo.newInputSet();

// Set inputs
//uses access token + secret from before
    listFriendsInputs.set_ScreenName("");
    listFriendsInputs.set_AccessToken("");
    listFriendsInputs.set_AccessTokenSecret("");
    listFriendsInputs.set_ConsumerSecret("");
    listFriendsInputs.set_ConsumerKey("");

    // Execute Choreo
    ListFriends.ListFriendsResultSet listFriendsResults = listFriendsChoreo.execute(listFriendsInputs);
//we get a friends/following list
//list is in json so we need to extract and match up the name for a contact
//then extract the id of the person's the twitter id that will be used in the next step
//probably should put ids into an array

//used to get the latest tweet from people you are following
//should probably be a loop for each id
    Show showChoreo = new Show(session);

    // Get an InputSet object for the choreo
    Show.ShowInputSet showInputs = showChoreo.newInputSet();

// Set inputs
//We use the token + secret again
    showInputs.set_AccessToken("");
    showInputs.set_AccessTokenSecret("");
    showInputs.set_ConsumerSecret("");
//for user id use array that was maybe made from last step
    showInputs.set_UserId("");
    showInputs.set_ConsumerKey("");

    // Execute Choreo
    Show.ShowResultSet showResults = showChoreo.execute(showInputs);
//Response is in JSon, "text" is the text of a tweet
//can also get profile image from response
//Twitter

//Facebook
//initialize OAuth
InitializeOAuth initializeOAuthChoreo = new InitializeOAuth(session);

    // Get an InputSet object for the choreo
    InitializeOAuth.InitializeOAuthInputSet initializeOAuthInputs = initializeOAuthChoreo.newInputSet();

// Set inputs
    initializeOAuthInputs.set_AppID("");

    // Execute Choreo
    InitializeOAuth.InitializeOAuthResultSet initializeOAuthResults = initializeOAuthChoreo.execute(initializeOAuthInputs);
//We get the authorization URL for the user and callback id for finalizing OAuth

//FinalizeOAuth
    FinalizeOAuth finalizeOAuthChoreo = new FinalizeOAuth(session);

    // Get an InputSet object for the choreo
    FinalizeOAuth.FinalizeOAuthInputSet finalizeOAuthInputs = finalizeOAuthChoreo.newInputSet();

// Set inputs
//Get this from cloudmine
    finalizeOAuthInputs.set_CallbackID("");
    finalizeOAuthInputs.set_AppSecret("");
    finalizeOAuthInputs.set_AppID("");

    // Execute Choreo
    FinalizeOAuth.FinalizeOAuthResultSet finalizeOAuthResults = finalizeOAuthChoreo.execute(finalizeOAuthInputs);

//get friends list
Friends friendsChoreo = new Friends(session);

    // Get an InputSet object for the choreo
    Friends.FriendsInputSet friendsInputs = friendsChoreo.newInputSet();

// Set inputs
    friendsInputs.set_AccessToken("");

    // Execute Choreo
    Friends.FriendsResultSet friendsResults = friendsChoreo.execute(friendsInputs);
//friends list is in JSon we can extract names + ids to put in an array fro later and match with contacts

//get latest Facebook status
//probably should loop this  through array of profile ids that maybe was made
    Statuses statusesChoreo = new Statuses(session);

    // Get an InputSet object for the choreo
    Statuses.StatusesInputSet statusesInputs = statusesChoreo.newInputSet();

// Set inputs
    statusesInputs.set_AccessToken("");
    //profile id from profile ids that was maybe made
    statusesInputs.set_ProfileID("");
    //1 for only the latest status we get 1 result
    statusesInputs.set_Limit("1");

    // Execute Choreo
    Statuses.StatusesResultSet statusesResults = statusesChoreo.execute(statusesInputs);
//we get statuses for  those in the array of ids that was maybe made

//Google stuff
    //insert OAuth here
//get all google contacts
    GetAllContacts getAllContactsChoreo = new GetAllContacts(session);

    // Get an InputSet object for the choreo
    GetAllContacts.GetAllContactsInputSet getAllContactsInputs = getAllContactsChoreo.newInputSet();

// Set inputs
    getAllContactsInputs.set_ClientID("");

    // Execute Choreo
    GetAllContacts.GetAllContactsResultSet getAllContactsResults = getAllContactsChoreo.execute(getAllContactsInputs);
    //xml file as results
}