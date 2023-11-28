package TemaTest;

import java.io.*;
import java.util.ArrayList;

public class Comment implements Likeable{
    private static int lastId = 0;
    private String commentId;
    private String postId;
    private String text;
    private String username;
    public Comment() {}
    public Comment(String username, String commentId) {
        this.username = username;
        this.commentId = commentId;
        this.text = null;
        this.postId = null;
    }
    public Comment(String id, String postId, String username, String text) {
        this.commentId = id;
        this.username = username;
        this.postId = postId;
        this.text = text;
    }
    public Comment(String username, String postId, String text) {
        lastId += 1;
        this.username = username;
        this.postId = postId;
        this.text = text;
        this.commentId = Integer.toString(lastId);
    }

    public String getPostId() {
        return postId;
    }

    public String getText() {
        return text;
    }

    public String getUsername() {
        return username;
    }

    public static void setLastId(int lastId) {
        Comment.lastId = lastId;
    }


    public void createComment() {
        if (this.text.length() >= 300) {
            System.out.println(new CommandResponse("error", "Comment text length exceeded"));
            return;
        }
        //write in comments.csv
        try (FileWriter fw = new FileWriter("comments.csv", true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw)) {
            out.println(this.commentId + "," + this.postId + "," + this.username + "," + this.text);
        } catch (IOException e) {
            //empty
        }
        System.out.println(new CommandResponse("ok", "Comment added successfully"));
    }
    public void like(String likedBy, String commentId) {
        String owner = null;
        //read from comments.csv
        try (BufferedReader br = new BufferedReader(new FileReader("comments.csv"))) {
            String line;
            br.readLine();
            boolean found = false;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data[0].equals(commentId)) {
                    found = true;
                    owner = data[2];
                }
            }
            if (!found || owner.equals(likedBy)) {
                System.out.println(new CommandResponse("error", "The comment identifier to like was not valid"));
                return;
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
                if (data[0].equals(commentId) && data[1].equals(owner) && data[2].equals(likedBy)) {
                    System.out.println(new CommandResponse("error", "The comment identifier to like was not valid"));
                    return;
                }
            }
        } catch (IOException e) {
            //empty
        }
        //write in likeComments.csv
        try (FileWriter fw = new FileWriter("likeComments.csv", true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw)) {
            out.println(commentId + "," + owner + "," + likedBy);
        } catch (IOException e) {
            //empty
        }
        System.out.println(new CommandResponse("ok", "Operation executed successfully"));
    }
    public void unlike(String likedBy, String commentId) {
        //read from comments.csv
        try (BufferedReader br = new BufferedReader(new FileReader("comments.csv"))) {
            String line;
            br.readLine();
            boolean found = false;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data[0].equals(commentId)) {
                    found = true;
                }
            }
            if (!found) {
                System.out.println(new CommandResponse("error", "The comment identifier to unlike was not valid"));
                return;
            }
        } catch (IOException e) {
            //empty
        }
        //creating an array of all the liked comments
        ArrayList<String> likedComments = new ArrayList<>();
        //read from likeComments.csv
        try (BufferedReader br = new BufferedReader(new FileReader("likeComments.csv"))) {
            String line;
            br.readLine();
            boolean found = false;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (!(data[0].equals(commentId) && data[2].equals(likedBy))) {
                    likedComments.add(line);
                } else {
                    found = true;
                }
            }
            if (!found) {
                System.out.println(new CommandResponse("error", "The comment identifier to unlike was not valid"));
                return;
            }
        } catch (IOException e) {
            //empty
        }
        //write in likeComments.csv
        try (FileWriter fw = new FileWriter("likeComments.csv", true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            for (String likedComment : likedComments) {
                out.println(likedComment);
            }
        } catch (IOException e) {
            //empty
        }
        System.out.println(new CommandResponse("ok", "Operation executed successfully"));
    }
    public void deleteComment() {
        //read from comments.csv
        try (BufferedReader br = new BufferedReader(new FileReader("comments.csv"))) {
            String line;
            br.readLine();
            boolean found = false;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data[0].equals(this.commentId) && data[2].equals(this.username)) {
                    found = true;
                }
            }
            if (!found) {
                System.out.println(new CommandResponse("error", "The identifier was not valid"));
                return;
            }
        } catch (IOException e) {
            //empty
        }
        //creating an array of all comments
        ArrayList<String> comments = new ArrayList<>();
        //read from comments.csv
        try (BufferedReader br = new BufferedReader(new FileReader("comments.csv"))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (!(data[0].equals(this.commentId) && data[2].equals(this.username))) {
                    comments.add(line);
                }
            }
        } catch (IOException e) {
            //empty
        }
        //write in comments.csv
        try (FileWriter fw = new FileWriter("comments.csv", true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            for (String comment : comments) {
                out.println(comment);
            }
        } catch (IOException e) {
            //empty
        }
        System.out.println(new CommandResponse("ok", "Operation executed successfully"));
    }
}
