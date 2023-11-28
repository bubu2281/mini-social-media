package TemaTest;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Post implements Likeable{

    private static int last_id = 0;
    private final String username;
    private String id;
    private final String text;

    public Post(String username) {
        this.username = username;
        this.text = null;
        this.id = null;
    }

    public Post(String username, String text) {
        Post.last_id += 1;
        this.username = username;
        this.text = text;
        this.id = Integer.toString(last_id);
    }
    public Post(String id, String username, String text) {
        this.username = username;
        this.text = text;
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public String getUsername() {
        return username;
    }

    public String getId() {
        return id;
    }
    public static void setLast_id(int last_id) {
        Post.last_id = last_id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public void deletePostById() {//read from posts.csv
        try (BufferedReader br = new BufferedReader(new FileReader("posts.csv"))) {
            String line;
            br.readLine();
            boolean found = false;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if(data[0].equals(this.getId()) && data[1].equals(this.getUsername())) {
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

        //creating an array of all posts
        ArrayList<String> posts = new ArrayList<String>();

        //read from posts.csv
        try (BufferedReader br = new BufferedReader(new FileReader("posts.csv"))) {
            String line;
            br.readLine();
            boolean found = false;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if(!data[0].equals(this.getId())) {
                    posts.add(line);
                }
            }
        } catch (IOException e) {
            //empty
        }

        //write in posts.csv
        try (FileWriter fw = new FileWriter("posts.csv", true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            for (int i = 0; i < posts.size(); i++) {
                out.println(posts.get(i));
            }
        } catch (IOException e) {
            //empty
        }
        System.out.println(new CommandResponse("ok", "Post deleted successfully"));
    }

    public void createPost() {
        //write in posts.csv
        try (FileWriter fw = new FileWriter("posts.csv", true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {

            out.println(this.getId()+ "," + this.getUsername() + "," + this.getText());
        } catch (IOException e) {
            //empty
        }
        System.out.println(new CommandResponse("ok", "Post added successfully"));
    }
    public static void like(String likedBy, String postId) {
        String owner = null;
        //read from posts.csv
        try (BufferedReader br = new BufferedReader(new FileReader("posts.csv"))) {
            String line;
            br.readLine();
            boolean found = false;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data[0].equals(postId)) {
                    found = true;
                    owner = data[1];
                }
            }
            if (!found) {
                System.out.println(new CommandResponse("error", "The post identifier to like was not valid"));
                return;
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
                if (data[0].equals(postId) && data[1].equals(owner) && data[2].equals(likedBy)) {
                    System.out.println(new CommandResponse("error", "The post identifier to like was not valid"));
                    return;
                }
                if (data[1].equals(likedBy)) {
                    System.out.println(new CommandResponse("error", "The post identifier to like was not valid"));
                    return;
                }
            }
        } catch (IOException e) {
            //empty
        }
        //write in likePosts.csv
        try (FileWriter fw = new FileWriter("likePosts.csv", true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            out.println(postId + "," + owner + "," + likedBy);
        } catch (IOException e) {
            //empty
        }
        System.out.println(new CommandResponse("ok", "Operation executed successfully"));
    }
    public static void unlike(String unlikedBy, String postId) {
        String owner = null;
        //read from posts.csv
        try (BufferedReader br = new BufferedReader(new FileReader("posts.csv"))) {
            String line;
            br.readLine();
            boolean found = false;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data[0].equals(postId)) {
                    found = true;
                    owner = data[1];
                }
            }
            if (!found) {
                System.out.println(new CommandResponse("error", "The post identifier to unlike was not valid"));
                return;
            }
        } catch (IOException e) {
            //empty
        }

        //creating an array of all the likes of all the posts
        ArrayList<String> likesPosts = new ArrayList<String>();
        //read from likePosts.csv
        try (BufferedReader br = new BufferedReader(new FileReader("likePosts.csv"))) {
            String line;
            br.readLine();
            boolean found = false;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (!(data[0].equals(postId) && data[2].equals(unlikedBy))) {
                    likesPosts.add(line);
                } else {
                    found = true;
                }
            }
            if (!found) {
                System.out.println(new CommandResponse("error", "The post identifier to unlike was not valid"));
                return;
            }
        } catch (IOException e) {
            //empty
        }

        //write in likePosts.csv
        try (FileWriter fw = new FileWriter("likePosts.csv", true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw)) {
            for(int i = 0; i < likesPosts.size(); i++) {
                out.println(likesPosts.get(i));
            }
        } catch (IOException e) {
            //empty
        }
        System.out.println(new CommandResponse("ok", "Operation executed successfully"));
    }
    public static void getMostLikedPosts() {
        LocalDate date = LocalDate.now();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        int[] frequencyArray = new int[last_id + 1];
        //read from likePosts.csv
        try (BufferedReader br = new BufferedReader(new FileReader("likePosts.csv"))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                frequencyArray[Integer.parseInt(data[0])] += 1;
            }
        } catch (IOException e) {
            //empty
        }
        System.out.print("{'status':'ok','message': [");

        for (int i = 0; (i < 5); i++) {
            int max = -1;
            int maxId = -1;
            for(int j = 1; j < frequencyArray.length; j++) {
                if (max < frequencyArray[j]) {
                    max = frequencyArray[j];
                    maxId = j;
                }
            }
            Post maxPost = null;
            //read from posts.csv
            try (BufferedReader br = new BufferedReader(new FileReader("posts.csv"))) {
                String line;
                br.readLine();
                while ((line = br.readLine()) != null) {
                    String[] data = line.split(",");
                    if (data[0].equals(Integer.toString(maxId))) {
                        maxPost = new Post(data[0], data[1], data[2]);
                    }
                }
            } catch (IOException e) {
                //empty
            }
            if (maxPost == null) {
                return;
            }
            if (i != 0) {
                System.out.print(",");
            }
            System.out.print("{'post_id':'" + maxPost.getId() + "','post_text':'" +
                    maxPost.getText() + "','post_date':'" + date.format(dateFormatter) +
                    "','username':'" + maxPost.getUsername() + "','number_of_likes':'" + frequencyArray[maxId] +
                    "'}");
            frequencyArray[maxId] = -1;
        }
    }

}
