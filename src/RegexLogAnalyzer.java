import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class RegexLogAnalyzer {


    //case A(B|C|D)*E :)

    public Integer[][] stateSwitch = {
            {1, null, null, null, null},
            {null, 1, 1, 1, 2},
            {null, null, null, null, null}};

    HashMap<String, Integer> sessions = new HashMap<>();
    HashMap<String, Integer> actions = new HashMap<>();
    List<String> finished = new ArrayList<>();


    public RegexLogAnalyzer() {
        actions.put("login", 0);
        actions.put("edit", 1);
        actions.put("delete", 2);
        actions.put("list", 3);
        actions.put("logout", 4);
    }

    public void listActiveSesssions() {

        HashMap<String, Integer> actives = new HashMap<>();
        for (Map.Entry<String, Integer> entry : sessions.entrySet()) {
            String key = entry.getKey();
            Integer value = entry.getValue();
            if (value == 1) System.out.println("Session with id " + key + " is active");
        }

    }

    public void parseLine(String s) {

        s = s.replaceAll("\\s+", "");
        String[] split = s.split(",");
        String id = split[0] + split[1];
        Integer action = actions.get(split[2]);

        Integer state = sessions.get(id);

        if (state == null && action == 0) {
            sessions.put(id, 1);
            System.out.println("Login on id: " + id);

        }
        if (state != null) {
            if (stateSwitch[state][action] != null) {
                sessions.put(id, stateSwitch[state][action]);
                System.out.println("Switched state on id: " + id + " from " + state + " to " + stateSwitch[state][action]);
                if (sessions.get(id) == 2) {
                    sessions.remove(id);
                    finished.add(id);
                    System.out.println("Logged out user id: " + id);
                }
            }
        } else if(sessions.get(id) == null){
            System.out.println("Error in action for session: " + id + " trying action " + action + " while having state " + state);
        }

    }

    public void readLog(String path) {
        try {
            File myObj = new File(path);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                parseLine(data);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }


    }


    public static void main(String[] args) {

        RegexLogAnalyzer r = new RegexLogAnalyzer();
        r.readLog("src/log.txt");
        System.out.println();
        System.out.println("Sessions stuck: ");
        r.listActiveSesssions();
        System.out.println();

        r.finished.stream().forEach(s -> System.out.println("Finished session: " + s));



    }


}
