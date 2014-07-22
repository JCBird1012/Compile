package compile_inc.compile;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphObject;
import com.facebook.model.GraphObjectList;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;

import java.util.Arrays;

/**
 * Created by DUCA on 7/22/2014.
 */
public class FbLoginFragment extends Fragment {
    private static final String TAG = "FbLoginFragment";
    private UiLifecycleHelper uiHelper;
    public static GraphObjectList<GraphUser> fbObjectList;
    private View view;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        uiHelper = new UiLifecycleHelper(getActivity(), callback);
        uiHelper.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_settings, container, false);

        LoginButton authButton = (LoginButton) view.findViewById(R.id.authButton);
        authButton.setFragment(this);
        authButton.setReadPermissions(Arrays.asList("user_friends", "user_about_me",
                "read_stream", "email"));
        this.view = view;
        return view;
    }

    private void onSessionStateChange(Session session, SessionState state, Exception exception) {
        RelativeLayout fbSyncLayout = (RelativeLayout) view.findViewById(R.id.fb_sync_layout);
        if (state.isOpened()) {
            Log.i(TAG, "Logged in...");
            /* make the API call */
            new Request(
                    session,
                    "/me/friends",
                    null,
                    HttpMethod.GET,
                    new Request.Callback() {
                        public void onCompleted(Response response) {
            /* handle the result */
                            GraphObjectList<GraphUser> fbFriends = response.getGraphObject()
                                    .getPropertyAsList
                                            ("data",
                                                    GraphUser.class);
                            fbObjectList = fbFriends;
                        }
                    }
            ).executeAsync();
            fbSyncLayout.setVisibility(View.VISIBLE);
        } else if (state.isClosed()) {
            Log.i(TAG, "Logged out...");
            fbSyncLayout.setVisibility(View.GONE);
        }
    }

    private Session.StatusCallback callback = new Session.StatusCallback() {
        @Override
        public void call(Session session, SessionState state, Exception exception) {
            onSessionStateChange(session, state, exception);
        }
    };
    @Override
    public void onResume() {
        super.onResume();

        Session session = Session.getActiveSession();
        if (session != null &&
                (session.isOpened() || session.isClosed()) ) {
            onSessionStateChange(session, session.getState(), null);
        }

        uiHelper.onResume();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        uiHelper.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onPause() {
        super.onPause();
        uiHelper.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        uiHelper.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        uiHelper.onSaveInstanceState(outState);
    }
}
