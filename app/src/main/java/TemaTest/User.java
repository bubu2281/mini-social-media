package TemaTest;

import java.io.*;
import java.util.ArrayList;

public class User {
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
}
