package TemaTest;


import java.io.*;

public class Command {
    String type;
    String[] args;
//    public Command() {}
    public Command(String[] args) {
        this.args = args;
        if (this.args != null && this.args.length > 1 && this.args[0].equals("-create-user")) {
            type = this.args[0];
        }
    }
    public void execute() {
        if (this.args != null && this.args.length >= 1) {
            if (this.args[0].equals("-cleanup-all")) {
                this.clean();
            }
            if (this.args[0].equals("-create-user")) {
                this.createUser();
            }
            if (this.args[0].equals("-create-post")) {
                this.createPost();
            }
            if (this.args[0].equals("-delete-post-by-id")) {
                this.deletePostById();
            }
            if (this.args[0].equals("-follow-user-by-username")) {
                this.followUserByUsername();
            }
            if (this.args[0].equals("-unfollow-user-by-username")) {
                this.unfollowUserByUsername();
            }
            if (this.args[0].equals("-like-post")) {
                this.likePost();
            }
            if (this.args[0].equals("-unlike-post")) {
                this.unlikePost();
            }
            if (this.args[0].equals("-comment-post")) {
                this.commentPost();
            }
            if (this.args[0].equals("-like-comment")) {
                this.likeComment();
            }
            if (this.args[0].equals("-unlike-comment")) {
                this.unlikeComment();
            }
            if (this.args[0].equals("-delete-comment-by-id")) {
                this.deleteCommentById();
            }
            if (this.args[0].equals("-get-following")) {
                this.getFollowing();
            }
            if (this.args[0].equals("-get-followers")) {
                this.getFollowers();
            }
        }
    }
    public void createUser() {
        if (this.args.length < 2 || !this.args[1].contains("-u")) {
            System.out.println(new CommandResponse("error", "Please provide username"));
            return;
        }

        if (this.args.length < 3 || !this.args[2].contains("-p")) {
            System.out.println(new CommandResponse("error", "Please provide password"));
            return;
        }

        User user = new User(this.args[1], this.args[2]);
        user.createUser();
    }

    public void createPost() {
        User user = this.verifyAuth();
        if (user == null) {
            return;
        }
        if (this.args.length < 4 || !this.args[3].contains("-text")) {
            System.out.println(new CommandResponse("error", "No text provided"));
            return;
        }
        Post post = new Post(user.getUsername(), this.args[3].split(" ",2)[1].replace("'", ""));
        if (post.getText().length() >= 300) {
            System.out.println(new CommandResponse("error", "Post text length exceeded"));
            return;
        }
        post.createPost();
    }

    public void deletePostById() {
        User user = this.verifyAuth();
        if (user == null) {
            return;
        }
        if (this.args.length < 4) {
            System.out.println(new CommandResponse("error", "No identifier was provided"));
            return;
        }
        String postId = this.args[3].split(" ")[1].replace("'", "");
        Post post = new Post(user.getUsername());
        post.setId(postId);
        post.deletePostById();
    }

    public void followUserByUsername() {
        User user = this.verifyAuth();
        if (user == null) {
            return;
        }
        if (this.args.length < 4) {
            System.out.println(new CommandResponse("error", "No username to follow was provided"));
            return;
        }
        String userToFollow = this.args[3].split(" ")[1].replace("'", "");
        user.followUserBuUsername(userToFollow);

    }

    public void unfollowUserByUsername() {
        User user = this.verifyAuth();
        if (user == null) {
            return;
        }
        if (this.args.length < 4) {
            System.out.println(new CommandResponse("error", "No username to unfollow was provided"));
            return;
        }
        String userToUnfollow = this.args[3].split(" ")[1].replace("'", "");

        user.unfollowUserByUsername(userToUnfollow);
    }

    public void likePost() {
        User user = this.verifyAuth();
        if (user == null) {
            return;
        }
        if (this.args.length < 4) {
            System.out.println(new CommandResponse("error", "No post identifier to like was provided"));
            return;
        }
        String postId = this.args[3].split(" ")[1].replace("'", "");
        Post.like(user.getUsername(), postId);
    }
    public void unlikePost() {
        User user = this.verifyAuth();
        if (user == null) {
            return;
        }
        if (this.args.length < 4) {
            System.out.println(new CommandResponse("error", "No post identifier to unlike was provided"));
            return;
        }
        String postId = this.args[3].split(" ")[1].replace("'", "");
        Post.unlike(user.getUsername(), postId);
    }

    public void commentPost() {
        User user = this.verifyAuth();
        if (user == null) {
            return;
        }
        if (this.args.length < 4) {
            System.out.println(new CommandResponse("error", "No text provided"));
            return;
        }
        String postId = this.args[3].split(" ")[1].replace("'", "");
        String text = this.args[4].split(" ",2)[1].replace("'", "");
        Comment comment = new Comment(user.getUsername(), postId, text);
        comment.createComment();
    }

    public void likeComment() {
        User user = this.verifyAuth();
        if (user == null) {
            return;
        }
        if (this.args.length < 4) {
            System.out.println(new CommandResponse("error", "No comment identifier to like was provided"));
            return;
        }
        String commentId = this.args[3].split(" ")[1].replace("'", "");
        Comment.like(user.getUsername(), commentId);
    }

    public void unlikeComment() {
        User user = this.verifyAuth();
        if (user == null) {
            return;
        }
        if (this.args.length < 4) {
            System.out.println(new CommandResponse("error", "No comment identifier to unlike was provided"));
            return;
        }
        String commentId = this.args[3].split(" ")[1].replace("'", "");
        Comment.unlike(user.getUsername(), commentId);
    }
    public void deleteCommentById() {
        User user = this.verifyAuth();
        if (user == null) {
            return;
        }
        if (this.args.length < 4) {
            System.out.println(new CommandResponse("error", "No identifier was provided"));
            return;
        }
        String commentId = this.args[3].split(" ")[1].replace("'", "");
        Comment comment = new Comment(user.getUsername(), commentId);
        comment.deleteComment();
    }
    public void getFollowing() {
        User user = this.verifyAuth();
        if (user == null) {
            return;
        }
        user.getFollowing();
    }

    public void getFollowers() {
        User user = this.verifyAuth();
        if (user == null) {
            return;
        }
        if (this.args.length < 4) {
            System.out.println(new CommandResponse("error", "No username to list followers was provided"));
            return;
        }
        String userToList = this.args[3].split(" ")[1].replace("'", "");
        user.getFollowers(userToList);
    }

    public User verifyAuth() {
        if (this.args.length < 3 || !this.args[1].contains("-u") || !this.args[2].contains("-p")) {
            System.out.println(new CommandResponse("error", "You need to be authenticated"));
            return null;
        }
        User user = new User(this.args[1], this.args[2]);

        //read from users.csv
        try (BufferedReader br = new BufferedReader(new FileReader("users.csv"))) {
            String line;
            br.readLine();
            boolean found = false;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data[0].equals(user.getUsername()) && data[1].equals(user.getPassword())) {
                    found = true;
                }
            }
            if (!found) {
                System.out.println(new CommandResponse("error", "Login failed"));
                return null;
            }
        } catch (IOException e) {
            //empty
        }
        return user;
    }
    public void clean() {
        //clean users.csv
        try (FileWriter fw = new FileWriter("users.csv", false);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw)) {
            out.println("Username,Password");
        } catch (IOException e) {
            //empty
        }

        //clean posts.csv
        try (FileWriter fw = new FileWriter("posts.csv", false);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw)) {
            out.println("Id,Username,Text");
        } catch (IOException e) {
            //empty
        }
        Post.setLast_id(0);

        //clean follow.csv
        try (FileWriter fw = new FileWriter("follow.csv", false);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw)) {
            out.println("Follower,Followed");
        } catch (IOException e) {
            //empty
        }

        //clean likesPosts.csv
        try (FileWriter fw = new FileWriter("likePosts.csv", false);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw)) {
            out.println("Id,PostOwner,LikedBy");
        } catch (IOException e) {
            //empty
        }

        //clean likesComments.csv
        try (FileWriter fw = new FileWriter("likeComments.csv", false);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw)) {
            out.println("Id,PostOwner,LikedBy");
        } catch (IOException e) {
            //empty
        }

        //clean comments.csv
        try (FileWriter fw = new FileWriter("comments.csv", false);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw)) {
            out.println("Id,PostId,Username,Text");
        } catch (IOException e) {
            //empty
        }
        Comment.setLastId(0);
    }
}
