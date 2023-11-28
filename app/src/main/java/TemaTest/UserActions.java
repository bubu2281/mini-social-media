package TemaTest;

abstract public class UserActions {
    abstract public void followUserByUsername(String userToFollow);
    abstract public void unfollowUserByUsername(String userToUnfollow);
    abstract public void createUser();
    abstract public void getFollowing();
    abstract public void getFollowers(String userToList);
    abstract public void getFollowingsPosts();
    abstract void getUserPosts(String userToList);
    abstract public void getPostDetails(String postIdToDetail);
}
