package TemaTest;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

public class User {
    private static int numberOfUsers = 0;
    private final String username;
    private final String password;
    public User(String username, String password) {
        this.username = username.split(" ")[1].replace("'", "");
        this.password = password.split(" ")[1].replace("'", "");
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }


    public void followUserBuUsername(String userToFollow) {
        //read from users.csv
        try (BufferedReader br = new BufferedReader(new FileReader("users.csv"))) {
            String line;
            br.readLine();
            boolean found = false;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data[0].equals(userToFollow)) {
                    found = true;
                }
            }
            if (!found) {
                System.out.println(new CommandResponse("error", "The username to follow was not valid"));
                return;
            }
        } catch (IOException e) {
            //empty
        }
        //read from follow.csv
        try (BufferedReader br = new BufferedReader(new FileReader("follow.csv"))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data[0].equals(this.getUsername()) && data[1].equals(userToFollow)) {
                    System.out.println(new CommandResponse("error", "The username to follow was not valid"));
                    return;
                }
            }
        } catch (IOException e) {
            //empty
        }
        //write in follow.csv
        try (FileWriter fw = new FileWriter("follow.csv", true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            out.println(this.getUsername() + "," + userToFollow);
        } catch (IOException e) {
            //empty
        }
        System.out.println(new CommandResponse("ok", "Operation executed successfully"));
    }
    public void unfollowUserByUsername(String userToUnfollow) {
        //read from users.csv
        try (BufferedReader br = new BufferedReader(new FileReader("users.csv"))) {
            String line;
            br.readLine();
            boolean found = false;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data[0].equals(userToUnfollow)) {
                    found = true;
                }
            }
            if (!found) {
                System.out.println(new CommandResponse("error", "The username to unfollow was not valid"));
                return;
            }
        } catch (IOException e) {
            //empty
        }
        //read from follow.csv
        try (BufferedReader br = new BufferedReader(new FileReader("follow.csv"))) {
            String line;
            br.readLine();
            boolean found = false;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data[0].equals(this.getUsername()) && data[1].equals(userToUnfollow)) {
                    found = true;
                }
            }
            if (!found) {
                System.out.println(new CommandResponse("error" , "The username to unfollow was not valid"));
                return;
            }
        } catch (IOException e) {
            //empty
        }

        //creating an array of all the followers
        ArrayList<String> followers = new ArrayList<String>();
        //read from follow.csv
        try (BufferedReader br = new BufferedReader(new FileReader("follow.csv"))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (!(data[0].equals(this.getUsername()) && data[1].equals(userToUnfollow))) {
                    followers.add(line);
                }
            }
        } catch (IOException e) {
            //empty
        }
        //write in follow.csv
        try (FileWriter fw = new FileWriter("follow.csv", true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            for (int i = 0; i < followers.size(); i++) {
                out.println(followers.get(i));
            }
        } catch (IOException e) {
            //empty
        }
        System.out.println(new CommandResponse("ok", "Operation executed successfully"));
    }

    public void createUser() {
        //read from users.csv
        try (BufferedReader br = new BufferedReader(new FileReader("users.csv"))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data[0].equals(this.getUsername())) {
                    System.out.println(new CommandResponse("error", "User already exists"));
                    return;
                }
            }
        } catch (IOException e) {
            //empty
        }

        //write in users.csv
        try (FileWriter fw = new FileWriter("users.csv", true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {

            out.println(this.getUsername() + "," + this.getPassword());
        } catch (IOException e) {
            //empty
        }
        System.out.println(new CommandResponse("ok", "User created successfully"));
        numberOfUsers += 1;
    }
    public void getFollowing() {
        //array of following
        ArrayList<String> following = new ArrayList<String>();
        //read from follow.csv
        try (BufferedReader br = new BufferedReader(new FileReader("follow.csv"))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data[0].equals(this.username)) {
                    following.add(data[1]);
                }
            }
        } catch (IOException e) {
            //empty
        }
        String joined = String.join("','", following);
        System.out.println("{'status':'ok','message': ['" + joined + "']}");
    }
    public void getFollowers(String userToList) {
        //read from users.csv
        try (BufferedReader br = new BufferedReader(new FileReader("users.csv"))) {
            String line;
            br.readLine();
            boolean found = false;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data[0].equals(userToList)) {
                    found = true;
                }
            }
            if (!found) {
                System.out.println(new CommandResponse("error", "The username to list followers was not valid"));
                return;
            }
        } catch (IOException e) {
            //empty
        }
        //array of followers
        ArrayList<String> followers = new ArrayList<String>();
        //read from follow.csv
        try (BufferedReader br = new BufferedReader(new FileReader("follow.csv"))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data[1].equals(userToList)) {
                    followers.add(data[0]);
                }
            }
        } catch (IOException e) {
            //empty
        }
        String joined = String.join("','", followers);
        System.out.println("{'status':'ok','message': ['" + joined + "']}");
    }

    public void getFollowingsPosts() {
        LocalDate date = LocalDate.now();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        //creating an array of all the posts of the people the user follows
        ArrayList<Post> posts = new ArrayList<Post>();
        //read from follow.csv
        try (BufferedReader br = new BufferedReader(new FileReader("follow.csv"))) {
            String line;
            br.readLine();
            boolean found = false;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                //searching for each person that the user follows
                if (data[0].equals(this.getUsername())) {
                    String followed = data[1];

                    //read from posts.csv
                    try (BufferedReader br1 = new BufferedReader(new FileReader("posts.csv"))) {
                        String line1;
                        br1.readLine();
                        while ((line1 = br1.readLine()) != null) {
                            String[] data1 = line1.split(",");
                            if (data1[1].equals(followed)) {
                                posts.add(new Post(data1[0], data1[1], data1[2]));
                            }
                        }
                    } catch (IOException e) {
                        //empty
                    }

                }
            }
        } catch (IOException e) {
            //empty
        }
        System.out.print("{'status':'ok','message': [");
        for (int i = posts.size() - 1; i >= 0; i--) {
            System.out.print("{'post_id':'" + posts.get(i).getId() + "','post_text':'" + posts.get(i).getText() +
                    "','post_date':'" + date.format(dateFormatter) + "','username':'" + posts.get(i).getUsername() +
                    "'}");
            if (i != 0) {
                System.out.print(",");
            }
        }
        System.out.println("]}");
    }

    void getUserPosts(String userToList) {
        //read from follow.csv
        try (BufferedReader br = new BufferedReader(new FileReader("follow.csv"))) {
            String line;
            br.readLine();
            boolean found = false;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data[0].equals(this.getUsername()) && data[1].equals(userToList)) {
                    found = true;
                }
            }
            if (!found) {
                System.out.println(new CommandResponse("error", "The username to list posts was not valid"));
                return;
            }
        } catch (IOException e) {
            //empty
        }
        LocalDate date = LocalDate.now();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        //creating an array of all the posts of the userToList
        ArrayList<Post> posts = new ArrayList<Post>();
        //read from posts.csv
        try (BufferedReader br = new BufferedReader(new FileReader("posts.csv"))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data[1].equals(userToList)) {
                    posts.add(new Post(data[0], data[1], data[2]));
                }
            }
        } catch (IOException e) {
            //empty
        }
        System.out.print("{'status':'ok','message': [");
        for (int i = posts.size() - 1; i >= 0; i--) {
            System.out.print("{'post_id':'" + posts.get(i).getId() + "','post_text':'" + posts.get(i).getText() +
                    "','post_date':'" + date.format(dateFormatter) + "'}");
            if (i != 0) {
                System.out.print(",");
            }
        }
        System.out.println("]}");
    }
    public void getPostDetails(String postIdToDetail) {
        LocalDate date = LocalDate.now();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String usersPostToDetail = null;
        Post postToDetail = null;
        //read from posts.csv
        try (BufferedReader br = new BufferedReader(new FileReader("posts.csv"))) {
            String line;
            br.readLine();
            boolean found = false;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data[0].equals(postIdToDetail)) {
                    found = true;
                    usersPostToDetail = data[1];
                    postToDetail = new Post(data[0], data[1], data[2]);
                }
            }
            if (!found) {
                System.out.println(new CommandResponse("error", "The post identifier was not valid"));
                return;
            }
        } catch (IOException e) {
            //empty
        }

        if (!this.getUsername().equals(usersPostToDetail)){
            //read from follow.csv
            try (BufferedReader br = new BufferedReader(new FileReader("follow.csv"))) {
                String line;
                br.readLine();
                boolean found = false;
                while ((line = br.readLine()) != null) {
                    String[] data = line.split(",");
                    if (data[0].equals(this.getUsername()) && data[1].equals(usersPostToDetail)) {
                        found = true;
                    }
                }
                if (!found) {
                    System.out.println(new CommandResponse("error", "The post identifier was not valid"));
                    return;
                }
            } catch (IOException e) {
                //empty
            }
        }
        int numberOfLikes = 0;
        //read from likePosts.csv
        try (BufferedReader br = new BufferedReader(new FileReader("likePosts.csv"))) {
            String line;
            br.readLine();
            boolean found = false;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if(data[0].equals(postIdToDetail)) {
                    numberOfLikes += 1;
                }
            }
        } catch (IOException e) {
            //empty
        }
        System.out.print("{'status':'ok','message': [{'post_text':'" + postToDetail.getText() +
                "','post_date':'" + date.format(dateFormatter) + "','username':'" + postToDetail.getUsername() +
                "','number_of_likes':'" + numberOfLikes + "','comments': [");
        //creating an array of all the comments of the post
        ArrayList<Comment> comments = new ArrayList<Comment>();
        //read from comments.csv
        try (BufferedReader br = new BufferedReader(new FileReader("comments.csv"))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data[1].equals(postIdToDetail)) {
                    comments.add(new Comment(data[0], data[1], data[2], data[3]));
                }
            }
        } catch (IOException e) {
            //empty
        }
        for (int i = comments.size() - 1; i >= 0; i--) {
            int likesComment = 0;
            //read from likeComments.csv
            try (BufferedReader br = new BufferedReader(new FileReader("likeComments.csv"))) {
                String line;
                br.readLine();
                while ((line = br.readLine()) != null) {
                    String[] data = line.split(",");
                    if (data[0].equals(comments.get(i).getPostId())) {
                        likesComment += 1;
                    }
                }
            } catch (IOException e) {
                //empty
            }
            System.out.print("{'comment_id':'" + comments.get(i).getPostId() + "','comment_text':'" +
                    comments.get(i).getText() + "','comment_date':'" + date.format(dateFormatter) +
                    "','username':'" + comments.get(i).getUsername() + "','number_of_likes':'" + likesComment +
                    "'}");
            if (i != 0) {
                System.out.print(",");
            }
        }
        System.out.println("] }] }");
    }
    public static void getMostLikedUsers() {
        LocalDate date = LocalDate.now();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        HashMap<String, Integer> userLikes = new HashMap<String, Integer>();
        //read from users.csv
        try (BufferedReader br = new BufferedReader(new FileReader("users.csv"))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                userLikes.put(data[0], 0);
            }
        } catch (IOException e) {
            //empty
        }
        //read from likePosts.csv
        try (BufferedReader br = new BufferedReader(new FileReader("likePosts.csv"))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                userLikes.put(data[1], userLikes.get(data[1]) + 1);
            }
        } catch (IOException e) {
            //empty
        }
        //read from likeComments.csv
        try (BufferedReader br = new BufferedReader(new FileReader("likeComments.csv"))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                userLikes.put(data[1], userLikes.get(data[1]) + 1);
            }
        } catch (IOException e) {
            //empty
        }
        System.out.print("{'status':'ok','message': [");
        for (int i = 0; (i < 5); i++) {
            int max = -1;
            String maxUsername = null;
            //read from users.csv
            try (BufferedReader br = new BufferedReader(new FileReader("users.csv"))) {
                String line;
                br.readLine();
                while ((line = br.readLine()) != null) {
                    String[] data = line.split(",");
                    if (max < userLikes.get(data[0])) {
                        max = userLikes.get(data[0]);
                        maxUsername = data[0];
                    }
                }
            } catch (IOException e) {
                //empty
            }
            if (i != 0) {
                System.out.print(",");
            }
            System.out.print("{'username':'" + maxUsername + "','number_of_likes':'" + userLikes.get(maxUsername) +
                    "'}");
            userLikes.put(maxUsername, -1);
        }
    }
    public static void getMostFollowedUsers() {
        LocalDate date = LocalDate.now();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        HashMap<String, Integer> userFollows = new HashMap<String, Integer>();
        //read from users.csv
        try (BufferedReader br = new BufferedReader(new FileReader("users.csv"))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                userFollows.put(data[0], 0);
            }
        } catch (IOException e) {
            //empty
        }
        //read from follow.csv
        try (BufferedReader br = new BufferedReader(new FileReader("follow.csv"))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                userFollows.put(data[1], userFollows.get(data[1]) + 1);
            }
        } catch (IOException e) {
            //empty
        }
        System.out.print("{'status':'ok','message': [");
        for (int i = 0; (i < 5); i++) {
            int max = -1;
            String maxUsername = null;
            //read from users.csv
            try (BufferedReader br = new BufferedReader(new FileReader("users.csv"))) {
                String line;
                br.readLine();
                while ((line = br.readLine()) != null) {
                    String[] data = line.split(",");
                    if (max < userFollows.get(data[0])) {
                        max = userFollows.get(data[0]);
                        maxUsername = data[0];
                    }
                }
            } catch (IOException e) {
                //empty
            }
            if (i != 0) {
                System.out.print(",");
            }
            System.out.print("{'username':'" + maxUsername + "','number_of_followers':'" + userFollows.get(maxUsername) +
                    "'}");
            userFollows.put(maxUsername, -1);
        }
    }
}
