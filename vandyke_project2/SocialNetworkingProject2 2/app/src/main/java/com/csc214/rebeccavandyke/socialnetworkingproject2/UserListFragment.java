package com.csc214.rebeccavandyke.socialnetworkingproject2;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Fragment;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.csc214.rebeccavandyke.socialnetworkingproject2.model.Post;
import com.csc214.rebeccavandyke.socialnetworkingproject2.model.User;
import com.csc214.rebeccavandyke.socialnetworkingproject2.model.UserCollection;

import org.w3c.dom.Text;

import java.util.List;

/*
* Rebecca Van Dyke
* rvandyke@u.rochester.edu
* CSC 214 Project 2
* TA: Julian Weiss
*/

public class UserListFragment extends Fragment {
    private static final String TAG = "UserListFragment";

    private static final String ARG_USERNAME = "username";

    private RecyclerView mRecyclerView;
    private UserCollection mUserCollection;
    private User mActiveUser;
    protected UserAdapter mUserAdapter;

    public UserListFragment() {
        // Required empty public constructor
    } //UserListFragment()

    public static UserListFragment newInstance(String activeUser){
        Bundle args = new Bundle();
        args.putString(ARG_USERNAME, activeUser);
        UserListFragment fragment = new UserListFragment();
        fragment.setArguments(args);
        Log.d(TAG, "newInstance() called with user " + activeUser);
        return fragment;
    } //newInstance()

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_list, container, false);

        mUserCollection = UserCollection.get(getContext());
        mRecyclerView = (RecyclerView)view.findViewById(R.id.user_list_recycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        Bundle args = getArguments();
        String activeUsername = args.getString(ARG_USERNAME);
        mActiveUser = UserCollection.get(getContext()).getUser(activeUsername);
        Log.d(TAG, "UserListFragment created for user " + mActiveUser.getUsername());
        updateUI();

        return view;
    } //onCreateView()

    @Override
    public void onResume(){
        super.onResume();
        updateUI();
    } //onResume()

    public void updateUsersFavorited(List<String> usersFavorited){
        mActiveUser.setUsersFavorited(usersFavorited);
        mUserCollection.updateUser(mActiveUser);
        updateUI();
    }

    public void updateUI(){
        List<User> userList = mUserCollection.getUserList();
        if(mUserAdapter == null){
            mUserAdapter = new UserListFragment.UserAdapter(userList, mActiveUser.getUsersFavorited());
            mRecyclerView.setAdapter(mUserAdapter);
        }
        else{
            mUserAdapter.update(userList, mActiveUser.getUsersFavorited());
        }
    } //updateUI()


    private class UserAdapter extends RecyclerView.Adapter<UserViewHolder>{
        private static final String TAG = "UserAdapter";
        private List<User> mUserList;
        private List<String> mUsersFavorited;

        public UserAdapter(List<User> userList, List<String> usersFavorited){
            mUserList = userList;
            mUsersFavorited = usersFavorited;
            String log = "";
            for(String s:usersFavorited){
                log = log + s + ", ";
            }
            Log.d(TAG, "usersFavorited are " + log);
        } //UserAdapter()

        public void update(List<User> users, List<String> usersFavorited){
            mUserList = users;
            mUsersFavorited = usersFavorited;
            String log = "";
            for(String s:usersFavorited){
                log = log + s + ", ";
            }
            Log.d(TAG, "usersFavorited are " + log);
            notifyDataSetChanged();
        } //update()

        public void favorite(String username){
            mUsersFavorited.add(username);
            updateUsersFavorited(mUsersFavorited);
        } //favorite()

        public void unfavorite(String username){
            mUsersFavorited.remove(username);
            updateUsersFavorited(mUsersFavorited);
        } //unfavorite()

        @Override
        public int getItemCount(){
            Log.d(TAG, "getItemCount() called");
            return mUserList.size();
        } //getItemCount()

        @Override
        public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
            Log.d(TAG, "onCreateViewHolder() called");
            return new UserViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.user_view_holder, parent, false));
        } //onCreateViewHolder()

        @Override
        public void onBindViewHolder(UserViewHolder holder, int position){
            User user = mUserList.get(position);
            Log.d(TAG, "onBindViewHolder() called on position " + position);
            holder.bind(user);
            if(mUsersFavorited.contains(user.getUsername())){
                holder.mFavorited.setChecked(true);
                holder.itemView.setBackgroundColor(Color.parseColor("#ff9c99"));
            }
            else{
                holder.mFavorited.setChecked(false);
                holder.itemView.setBackgroundColor(Color.parseColor("#fffae6"));
            }

            holder.mFavorited.setChecked(mUsersFavorited.contains(user.getUsername()));
        } //onBindViewHolder()

    } //end classUserAdapter()

    private class UserViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private final TextView mUsername;
        private final TextView mFullName;
        private final TextView mHometown;
        private final TextView mBio;
        private final Button mViewProfile;
        public final CheckBox mFavorited;

        private User mUser;

        public UserViewHolder(View itemView){
            super(itemView);
            mUsername = (TextView)itemView.findViewById(R.id.text_view_username);
            mFullName = (TextView)itemView.findViewById(R.id.text_view_full_name);
            mHometown = (TextView)itemView.findViewById(R.id.text_view_hometown);
            mBio = (TextView)itemView.findViewById(R.id.text_view_bio);
            mViewProfile = (Button)itemView.findViewById(R.id.button_view_profile);
            mFavorited = (CheckBox)itemView.findViewById(R.id.check_box_favorited);

            mFavorited.setOnClickListener(this);
            mViewProfile.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    ProfileDialog viewProfile = ProfileDialog.newInstance(mUser);
                    viewProfile.show(getFragmentManager(), "profile");
                }
            });
        } //UserViewHolder()

        public void bind(User user){
            mUser = user;
            mUsername.setText(user.getUsername());
            mFullName.setText(user.getFullName());
            mHometown.setText(user.getHometown());
            mBio.setText(user.getBio());
        } //bind()

        @Override
        public void onClick(View v){
            if(v.getId() == mFavorited.getId()){
                if(mFavorited.isChecked()){
                    mUserAdapter.favorite(mUsername.getText().toString());
                }
                else{
                    mUserAdapter.unfavorite(mUsername.getText().toString());
                }
            }
        } //onClick

    } //end class UserViewHolder

} //end class UserListFragment
