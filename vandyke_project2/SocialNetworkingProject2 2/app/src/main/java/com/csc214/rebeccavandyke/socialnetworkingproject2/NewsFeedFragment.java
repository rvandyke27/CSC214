package com.csc214.rebeccavandyke.socialnetworkingproject2;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.text.DateFormat;
import android.media.Image;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.csc214.rebeccavandyke.socialnetworkingproject2.model.Post;
import com.csc214.rebeccavandyke.socialnetworkingproject2.model.PostCollection;
import com.csc214.rebeccavandyke.socialnetworkingproject2.model.User;
import com.csc214.rebeccavandyke.socialnetworkingproject2.model.UserCollection;

import java.util.List;

/*
* Rebecca Van Dyke
* rvandyke@u.rochester.edu
* CSC 214 Project 2
* TA: Julian Weiss
*/

public class NewsFeedFragment extends Fragment {
    private static final String TAG = "NewsFeedFragment";

    private static String ARG_USERNAME = "username";

    private RecyclerView mRecyclerView;
    private PostCollection mPostCollection;
    private User mActiveUser;
    private PostAdapter mAdapter;


    public NewsFeedFragment() {
        // Required empty public constructor
    } //NewsFeedFragment()

    public static NewsFeedFragment newInstance(String activeUser){
        Bundle args = new Bundle();
        args.putString(ARG_USERNAME, activeUser);
        NewsFeedFragment fragment = new NewsFeedFragment();
        fragment.setArguments(args);
        Log.d(TAG, "newInstance() called with user " + activeUser);
        return fragment;
    } //newInstance()


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_feed, container, false);

        mPostCollection = PostCollection.get(getContext());
        mRecyclerView = (RecyclerView)view.findViewById(R.id.recycler_view_newsfeed);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        Bundle args = getArguments();
        String activeUsername = args.getString(ARG_USERNAME);
        mActiveUser = UserCollection.get(getContext()).getUser(activeUsername);
        Log.d(TAG, "NewsFeedFragment created for user " + mActiveUser.getUsername());
        updateUI();

        return view;
    } //onCreateView()

    @Override
    public void onResume(){
        super.onResume();
        mActiveUser = UserCollection.get(getContext()).getUser(mActiveUser.getUsername());
        updateUI();
    } //onResume()

    public void updateUI(){
        List<Post> newsFeed = mPostCollection.getNewsFeed(mActiveUser);
        if(mAdapter == null){
            mAdapter = new PostAdapter(newsFeed);
            mRecyclerView.setAdapter(mAdapter);
        }
        else{
            mAdapter.setPosts(newsFeed);
        }
    } //updateUI()

    private class PostAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
        private static final String TAG = "PostAdapter";
        private List<Post> mNewsFeed;

        public PostAdapter(List<Post> newsFeed){
            mNewsFeed = newsFeed;
        }

        @Override
        public int getItemCount() {
            Log.d(TAG, "getItemCount() called");
            return mNewsFeed.size();
        } //getItemCount()

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
            switch(viewType){
                case 0: //text only
                    return new PostViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.post_view_holder_type0, parent, false));
                case 1: //contains image
                    return new ImagePostViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.post_view_holder_type1, parent, false));
            }
            Log.d(TAG, "onCreateViewHolder() called, type=" + viewType);
            return new PostViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.post_view_holder_type1, parent, false));
        } //onCreateViewHolder()

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position){
            Post post = mNewsFeed.get(position);
            Log.d(TAG, "onBindViewHolder() called on position " + position);
            if(post.containsImage()){
                ImagePostViewHolder imageHolder = (ImagePostViewHolder) holder;
                imageHolder.bind(post);
            }
            else{
                PostViewHolder postHolder = (PostViewHolder) holder;
                postHolder.bind(post);
            }
        } //onBindViewHolder();

        public void setPosts(List<Post> posts){
            mNewsFeed  = posts;
            notifyDataSetChanged();
        } //setPosts()

        @Override
        public int getItemViewType(int position) {
            if(mNewsFeed.get(position).containsImage()){
                return 1;
            }
            else{
                return 0;
            }
        } //getItemViewType()

    } //end class TeamAdapter()

    private class PostViewHolder extends RecyclerView.ViewHolder{
        private final TextView mUsername;
        private final TextView mPostText;
        private final TextView mTimeStamp;

        public PostViewHolder(View itemView) {
            super(itemView);
            mUsername = (TextView) itemView.findViewById(R.id.text_view_poster);
            mPostText = (TextView) itemView.findViewById(R.id.text_view_post_text);
            mTimeStamp = (TextView) itemView.findViewById(R.id.text_view_timestamp);
        } //PostViewHolder()

        public void bind(Post post){
            mUsername.setText(post.getUser());
            mPostText.setText(post.getText());
            mTimeStamp.setText(post.getStringTimestamp());
        } //bind()
    } //end class PostViewHolder

    private class ImagePostViewHolder extends PostViewHolder{
        private final ImageView mImage;

        public ImagePostViewHolder(View itemView){
            super(itemView);
            mImage = (ImageView)itemView.findViewById(R.id.image_view_post);
        } //ImagePostViewHolder()

        public void bind(Post post){
            super.bind(post);
            Bitmap img = ImageHelper.getScaledBitmap(post.getImagePath(), mImage);
            mImage.setScaleType(ImageView.ScaleType.FIT_CENTER);
            mImage.setImageBitmap(img);
        } //bind()

    } //end class ImagePostViewHolder()


} //end class NewsFeedFragment()
